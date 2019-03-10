package top.wingcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wingcloud.domain.Gender;
import top.wingcloud.service.IReportService;
import top.wingcloud.service.SocketioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表展示服务控制器
 */
@RestController
public class ReportController {

    private static Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private IReportService iReportService;
    @Autowired
    private SocketioService socketioService;

    private static Long lastmoney = 0l; //记录上一次的总销售额

    private Map sex_map = new HashMap(); //男女性别使用的map
    private List<Gender> sex_list = new ArrayList<>();//男女性别使用的list


    /**
     * 男女比例报表
     * @param record
     */
    @GetMapping("/pushMessage")
    @KafkaListener(topics = "ShopMsgSink")
    public void pushMessage(ConsumerRecord<?, ?> record){
        /**
         * 如果map中有数据则清空
         * list也清空
         */
        if (sex_map.size() == 1){
            sex_map.clear();
            sex_list.clear();
        }

        //获取kafka中的监听数据，并处理为json
        String value = (String) record.value();
        JSONObject jsonObject = JSON.parseObject(value);

        //将数据写入list
        //因为前台报表需要男女比例，所以需要list为2
        //第一次数据可能为脏数据
        sex_list.add(new Gender(jsonObject.getString("usergender"),jsonObject.getLong("count")));
        if (sex_list.size() == 2){
            sex_map.put("list",sex_list);
            //向客户端发送消息,第一个参数与socket.on接收的参数相同
            socketioService.pushArr("connect_msg", sex_map);
        }
    }




    /**
     * 获得总销售额
     * 每隔2秒
     * @Async 可多线程
     */
//    @Async
//    @Scheduled(cron = "0/2 * * * * *")
//    public Long test(){
//        logger.info("test");
//        System.err.println(iReportService.getMoney());
//        Long currentmoney = iReportService.getMoney();
//        Double growthrate = (double)(currentmoney - lastmoney) / (double)lastmoney;
//        System.err.println(growthrate);
//        lastmoney = currentmoney;
//        return iReportService.getMoney();
//    }

}
