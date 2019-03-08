package top.wingcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wingcloud.service.SocketioService;

import java.util.HashMap;
import java.util.Map;

/**
 * 报表展示服务控制器
 */
@RestController
public class ReportController {
    @GetMapping("/pushMessage")
    @KafkaListener(topics = "ShopMsgSink")
    public void pushMessage(ConsumerRecord<?, ?> record){
        SocketioService socketio = new SocketioService();
        String value = (String) record.value();
        Map map = new HashMap();

        JSONObject jsonObject = JSON.parseObject(value);
        map.put("shopid",jsonObject.getString("shopid"));
        map.put("shopname",jsonObject.getString("shopname"));

        //向客户端发送消息,第一个参数与socket.on接收的参数相同
        socketio.pushArr("connect_msg", map);
    }
}
