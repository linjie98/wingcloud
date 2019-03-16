package top.wingcloud.watermark;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * watermark
 */
public class WCWatermark implements AssignerWithPeriodicWatermarks<Tuple2<Long,String>> {

    Long currentMaxTimestamp = 0L;
    final Long maxOutOfOrderness = 10000L;//最大乱序时间10s

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimestamp-maxOutOfOrderness);
    }

    @Override
    public long extractTimestamp(Tuple2<Long,String> element, long previousElementTimestamp) {
        Long timestamp = element.f0;
        currentMaxTimestamp = Math.max(timestamp,currentMaxTimestamp);
        return timestamp;
    }
}
