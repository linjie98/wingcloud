package top.wingcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wingcloud.domain.MapChartpojo;
import top.wingcloud.mapper.ReportMapper;
import top.wingcloud.service.IReportService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据展示服务接口实现类
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ReportServiceimpl implements IReportService {

    @Autowired
    private ReportMapper reportMapper;

//    private Map mapChartdata = new HashMap();
//    private List listChartdata = new ArrayList();
    /**
     * 获取总销售额
     * @return
     */
    @Override
    public Long getMoney() {
        Long money = reportMapper.selectMoney();
        return money;
    }


//    /**
//     * 获得地图数据
//     * @param record
//     * @return
//     */
//    @Override
//    public Map getMapChart(ConsumerRecord<?, ?> record) {
//        if (mapChartdata.size() == 1){
//            mapChartdata.clear();
//            listChartdata.clear();
//            System.err.println("map清楚");
//        }
//
//        //获取kafka中的监听数据，并处理为json
//        String value = (String) record.value();
//        JSONObject jsonObject = JSON.parseObject(value);
//        listChartdata.add(new MapChartpojo(jsonObject.getString("userarea"),jsonObject.getLong("count")));
//        System.err.println(listChartdata);
//        mapChartdata.put("list",listChartdata);
//        System.err.println(mapChartdata);
//        return mapChartdata;
//    }
}
