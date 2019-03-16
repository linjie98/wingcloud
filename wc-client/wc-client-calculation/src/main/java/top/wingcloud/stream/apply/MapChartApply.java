package top.wingcloud.stream.apply;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * 地图分布窗口的apply
 * IN, OUT, KEY, W extends Window
 */
public class MapChartApply implements WindowFunction<Tuple2<Long, String>, String, Tuple, TimeWindow> {
    @Override
    public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<Long, String>> input, Collector<String> out) throws Exception {
        //获取分组字段
        String userarea = tuple.getField(0).toString();

        Iterator<Tuple2<Long, String>> it = input.iterator();

        //计数器
        Long count = 0L;
        while (it.hasNext()) {
            Tuple2<Long, String> next = it.next();
            count++;
        }
        System.err.println(Thread.currentThread().getId()+"【地图分布】window触发了=数据条数："+count);
        //组装结果
        //Tuple2<String, Long> res = new Tuple2<>(usergender, count);
        String res = "{\"userarea\":\""+userarea+"\",\"count\":\""+count+"\"}";
        System.err.println("【地图分布】窗口数据 : "+ res);
        out.collect(res);
    }
}
