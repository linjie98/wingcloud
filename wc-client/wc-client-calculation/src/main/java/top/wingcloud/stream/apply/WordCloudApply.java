package top.wingcloud.stream.apply;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * 词云apply
 *
 */
public class WordCloudApply implements WindowFunction<Tuple2<Long, String>, String, Tuple, TimeWindow> {
    @Override
    public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<Long, String>> input, Collector<String> out) throws Exception {
        String shoptype = tuple.getField(0).toString();

        Iterator<Tuple2<Long, String>> it = input.iterator();

        //计数器
        Long count = 0L;
        while (it.hasNext()) {
            Tuple2<Long, String> next = it.next();
            count++;
        }
        System.err.println(Thread.currentThread().getId() + "【词云】window触发了，数据条数：" + count);
        //组装结果
        Tuple2<String, Long> res = new Tuple2<>(shoptype, count);
        String resstring = "{\"shoptype\":\"" + shoptype + "\",\"count\":\"" + count + "\"}";
        System.err.println("【词云】窗口数据 : "+ res);
        out.collect(resstring);
    }
}
