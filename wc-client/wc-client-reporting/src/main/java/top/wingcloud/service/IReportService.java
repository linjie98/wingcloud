package top.wingcloud.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

/**
 * 报表展示服务接口
 */
public interface IReportService {
    /**
     * 获取总销售额
     * @return
     */
    public Long getMoney();

    /**
     * 获得地图数据
     * @param record
     * @return
     */
//    public Map getMapChart(ConsumerRecord<?, ?> record);
}
