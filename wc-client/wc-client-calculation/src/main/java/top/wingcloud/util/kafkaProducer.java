package top.wingcloud.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 *
 * {"userid":"1","usergender":"m","userage":"26","userarea":"温州","shopid":"002"}
 * 模拟生成数据写入kafka
 *
 * by:linjie
 */
public class kafkaProducer {

    static Long userid = 1L;
    static String shoptype = null;

    public static void main(String[] args) throws Exception{
        Properties prop = new Properties();
        //指定kafka broker地址
        prop.put("bootstrap.servers", "192.168.43.201:9092");
        //指定key value的序列化方式
        prop.put("key.serializer", StringSerializer.class.getName());
        prop.put("value.serializer", StringSerializer.class.getName());

        //指定topic名称
        String topic = "testsource";

        //创建producer链接
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(prop);

        //
        //生产消息

        while(true){
            String message = "{\"userid\":\""+getUserId()+"\",\"usergender\":\""+getUserGender()+"\",\"userage\":\""+getUserAge()+"\",\"userarea\":\""+getUserArea()+"\",\"shopid\":\""+getShopId()+"\",\"shoptype\":\""+shoptype+"\",\"shoptime\":\""+getShopTime()+"\"}";
            System.out.println(message);
            producer.send(new ProducerRecord<String, String>(topic,message));
            Thread.sleep(2000);
        }
        //关闭链接
        //producer.close();
    }

    /**
     * 获取用户id
     * id从1一直递增
     * @return
     */
    public static Long getUserId(){
        return userid++;
    }

    /**
     * 获取用户性别
     * m:男
     * w:女
     * @return
     */
    public static String getUserGender(){
        String[] types = {"m","w"};
        Random random = new Random();
        int index = random.nextInt(types.length);
        return types[index];
    }

    /**
     * 获取用户年龄
     * @return
     */
    public static Integer getUserAge(){
        Random random = new Random();
        return random.nextInt(45)+15;
    }

    /**
     * 获取用户的下单省份
     * @return
     */
    public static String getUserArea(){
//        String[] types = {"海门","鄂尔多斯","招远","舟山","齐齐哈尔","盐城","赤峰","青岛","乳山","金昌","泉州","莱西","日照","胶南",
//        "南通","拉萨","云浮","梅州","文登","上海","攀枝花","威海","承德","厦门","汕尾","潮州","丹东","太仓","曲靖","烟台","福州","瓦房店","即墨","抚顺","玉溪",
//        "张家口","阳泉","莱州","湖州","汕头","昆山","宁波","湛江","揭阳","荣成","连云港","葫芦岛","常熟","东莞","河源","淮安","泰州",
//        "南宁","营口","惠州","江阴","蓬莱","韶关","嘉峪关","广州","延安","太原","清远","中山","昆明","寿光","盘锦","长治","深圳","珠海",
//        "宿迁","咸阳","铜川","平度","佛山","海口","江门","章丘","肇庆","大连","临汾","吴江","石嘴山","沈阳","苏州","茂名","嘉兴","长春",
//        "胶州","银川","张家港","三门峡","锦州","南昌","柳州","三亚","自贡","吉林","阳江","泸州","西宁","宜宾","呼和浩特","成都","大同",
//        "镇江","桂林","张家界","宜兴","北海","西安","金坛","东营","牡丹江","遵义","绍兴","扬州","常州","潍坊","重庆","台州","南京","滨州",
//        "贵阳","无锡","本溪","克拉玛依","渭南","马鞍山","宝鸡","焦作","句容","北京","徐州","衡水","包头","绵阳","乌鲁木齐","枣庄","杭州",
//        "淄博","鞍山","溧阳","库尔勒","安阳","开封","济南","德阳","温州","九江","邯郸","临安","兰州","沧州","临沂","南充","天津",
//        "富阳","泰安","诸暨","郑州","哈尔滨","聊城","芜湖","唐山","平顶山","邢台","德州","济宁","荆州","宜昌","义乌","丽水","洛阳","秦皇岛",
//        "株洲","石家庄","莱芜","常德","保定","湘潭","金华","岳阳","长沙","衢州","廊坊","菏泽","合肥","武汉","大庆"};
        String[] types = {"海门","鄂尔多斯","招远","舟山","齐齐哈尔","盐城","赤峰","青岛","乳山","金昌"};
        Random random = new Random();
        int index = random.nextInt(types.length);
        return types[index];
    }

    /**
     * 获取用户购买的商品id
     * 需要在计算中从redis找到相对应id的商品名称、商品所属品类、商品价格
     * @return
     */
    public static String getShopId(){
        String[] types = {"001","002","003","004","005","006","007","008","009","010"};
        Random random = new Random();
        int index = random.nextInt(types.length);
        shoptype = getShopType(index);
        return types[index];
    }

    /**
     * 获取订单下单时间
     * @return
     */
    public static String getShopTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }


    public static String getShopType(int index){
        String[] types = {"美妆","女装","男装","箱包","童装","母婴","宠物食品","鞋靴","医药","书籍"};
        return types[index];
    }

}