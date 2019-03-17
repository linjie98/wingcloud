package top.wingcloud.stream;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchemaWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wingcloud.stream.apply.MapChartApply;
import top.wingcloud.stream.apply.SexApply;
import top.wingcloud.stream.apply.WordCloudApply;
import top.wingcloud.stream.flatmap.MoneyFlatmap;
import top.wingcloud.stream.flatmap.ShopFlatmap;
import top.wingcloud.stream.map.MapChartMap;
import top.wingcloud.stream.map.SexMap;
import top.wingcloud.stream.map.WordCloudMap;
import top.wingcloud.util.WCMysqlSink;
import top.wingcloud.util.WCRedisSource;
import top.wingcloud.watermark.WCWatermark;

import java.util.HashMap;
import java.util.Properties;

/**
 * wingcloud实时计算任务
 * 1、男女比例task
 * 2、地图分布task
 * 3、实时销售额task
 * 4、类别词云task
 * 5、实时订单数据task
 */
public class wingcloudJOB {
    private static Logger logger = LoggerFactory.getLogger(wingcloudJOB.class);

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //使用eventtime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        /**
         * 配置checkpoint
         */
        env.enableCheckpointing(60000);//checkpoint周期，每隔1分钟启动一个检查点
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);//设置模式语义为EXACTLY_ONCE（默认）
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(30000);//设置checkpoint最小时间间隔，也就是说checkpoint时间间隔至少30秒
        env.getCheckpointConfig().setCheckpointTimeout(10000);//设置checkpoint超时时间，也就是检查点必须在60000ms(1分钟)内完成，否则会被丢弃
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);//同一时间，只允许进行一个检查点
        //RETAIN_ON_CANCELLATION：一旦Flink处理程序被cancel后，会保留Checkpoint数据，以便根据实际需要恢复到指定的Checkpoint
        //DELETE_ON_CANCELLATION：一旦Flink处理程序被cancel后，会删除Checkpoint数据，只有job执行失败的时候才会保存checkpoint
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        /**
         * 获取数据源
         * kafka source
         */
        //指定topic source
        String sourcetopic = "testsource";
        Properties source_properties = new Properties();
        source_properties.setProperty("bootstrap.servers", "192.168.43.201:9092");
        source_properties.setProperty("group.id", "con1");

        FlinkKafkaConsumer011<String> consumer011 = new FlinkKafkaConsumer011<>(sourcetopic, new SimpleStringSchema(), source_properties);

        //获取kafka中的数据
        DataStreamSource<String> kafkasourcedata = env.addSource(consumer011);


        /**
         * 销售额Redis source
         */
        DataStream<HashMap<String, String>> money_redissourcedata = env.addSource(new WCRedisSource("wc_id_money")).broadcast();
        /**
         * 实时订单数据Redis source (获取id : 商品名称)
         */
        DataStream<HashMap<String, String>> shop_redissourcedata = env.addSource(new WCRedisSource("wc_id_name")).broadcast();


        ////////////////////////////////////
        /**
         * 1、男女比例处理
         */
        //对source数据进行处理,获取tuple2<时间,性别>
        DataStream<Tuple2<Long, String>> sexmapdata = kafkasourcedata.map(new SexMap());

        //窗口操作
        SingleOutputStreamOperator<String> sexapplydata = sexmapdata.assignTimestampsAndWatermarks(new WCWatermark())
                .keyBy(1)
                .window(TumblingEventTimeWindows.of(Time.seconds(30)))
                .allowedLateness(Time.seconds(10))
                .apply(new SexApply());


        ////////////////////////////////////


        /**
         * 2、地图分布数据处理
         */
        //对source数据进行map处理
        SingleOutputStreamOperator<Tuple2<Long, String>> mapchartmapdata = kafkasourcedata.map(new MapChartMap());

        //窗口操作
        SingleOutputStreamOperator<String> mapchartapplydata = mapchartmapdata.assignTimestampsAndWatermarks(new WCWatermark())
                .keyBy(1)
                .window(TumblingEventTimeWindows.of(Time.seconds(30)))
                .allowedLateness(Time.seconds(10))
                .apply(new MapChartApply());


        ////////////////////////////////////


        /**
         * 3、实时销售额
         */
        SingleOutputStreamOperator<String> moneydata = kafkasourcedata.connect(money_redissourcedata).flatMap(new MoneyFlatmap());


        ////////////////////////////////////

        /**
         * 4、词云(商品类别)
         */
        //对source进行map处理
        SingleOutputStreamOperator<Tuple2<Long, String>> wordcloudmapdata = kafkasourcedata.map(new WordCloudMap());

        //窗口操作
        SingleOutputStreamOperator<String> wordcloudapplydata = wordcloudmapdata.assignTimestampsAndWatermarks(new WCWatermark())
                .keyBy(1)
                .window(TumblingEventTimeWindows.of(Time.seconds(30)))
                .allowedLateness(Time.seconds(10))
                .apply(new WordCloudApply());


        ////////////////////////////////////


        /**
         * 5、实时订单数据
         * 商品id  商品名称  商品类别
         */
        SingleOutputStreamOperator<String> shopdata = kafkasourcedata.connect(shop_redissourcedata).flatMap(new ShopFlatmap());


        ////////////////////////////////////


        /**
         * 定义sink kafka topic
         */
        String sex_topic = "sextopic";
        String mapchart_topic = "mapcharttopic";
        String wordcloud_topic = "wordcloudtopic";
        String shop_topic = "shoptopic";

        /**
         * kafka 配置
         */
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.43.201:9092");
        //设置事务超时时间15分分钟，kafka事务超时时间默认15分钟，而FlinkKafkaProducer011事务超时时间默认1小时，所以将其1小时改为15分钟(其事务超时时间不能大于kafka的，不然会报错)
        properties.setProperty("transaction.timeout.ms", 60000 * 15 + "");

        /**
         * 获取kafka 生产者FlinkKafkaProducer011
         */
        FlinkKafkaProducer011<String> sex_FlinkKafkaProducer = new FlinkKafkaProducer011<>(sex_topic, new KeyedSerializationSchemaWrapper<String>(new SimpleStringSchema()), properties, FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);
        FlinkKafkaProducer011<String> mapchart_FlinkKafkaProducer = new FlinkKafkaProducer011<>(mapchart_topic, new KeyedSerializationSchemaWrapper<String>(new SimpleStringSchema()), properties, FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);
        FlinkKafkaProducer011<String> wordcloud_FlinkKafkaProducer = new FlinkKafkaProducer011<>(wordcloud_topic, new KeyedSerializationSchemaWrapper<String>(new SimpleStringSchema()), properties, FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);
        FlinkKafkaProducer011<String> shop_FlinkKafkaProducer = new FlinkKafkaProducer011<>(shop_topic, new KeyedSerializationSchemaWrapper<String>(new SimpleStringSchema()), properties, FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);


        /**
         * Sink
         */
        sexapplydata.addSink(sex_FlinkKafkaProducer);
        mapchartapplydata.addSink(mapchart_FlinkKafkaProducer);
        wordcloudapplydata.addSink(wordcloud_FlinkKafkaProducer);
        shopdata.addSink(shop_FlinkKafkaProducer);
        moneydata.addSink(new WCMysqlSink()); // 销售额sink到mysql


        env.execute("wingcloud job running!");

    }

}
