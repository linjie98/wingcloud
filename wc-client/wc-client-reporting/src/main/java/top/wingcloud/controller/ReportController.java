package top.wingcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import top.wingcloud.domain.GroundMoney;
import top.wingcloud.domain.MaporCloudChartpojo;
import top.wingcloud.domain.ShopRoll;
import top.wingcloud.service.IReportService;
import top.wingcloud.service.SocketioService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表展示服务控制器
 */
@RestController
@Api("报表展示接口")
public class ReportController {

    private static Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private IReportService iReportService;

    private static Long lastmoney = 0l; //记录上一次的总销售额

    private Map sex_map = new HashMap(); //男女性别使用的map
    private List<Gender> sex_list = new ArrayList<>();//男女性别使用的list

    private Map mapchart_map = new HashMap();//地图报表map
    private List<MaporCloudChartpojo> mapchart_list = new ArrayList<>();//地图报表list

    private Map cloudchart_map = new HashMap();//词云报表map
    private List<MaporCloudChartpojo> cloudchart_list = new ArrayList<>();//词云报表list

    private Map shoproll_map = new HashMap();//订单滚动流map
    private List<ShopRoll> shoproll_list = new ArrayList<>();//订单滚动流list

    private Map grow_map = new HashMap(); //环比增长速度map
    private List<GroundMoney> grow_list = new ArrayList<>();//环比增长速度list


    /**
     * 男女比例报表
     *
     * @param record
     */
    @ApiOperation(value = "/getsexmsg", notes = "获取男女比例信息接口")
    @GetMapping("/getsexmsg")
    @KafkaListener(topics = "sextopic")
    public void sexChart(ConsumerRecord<?, ?> record) {
        /**
         * 如果map中有数据则清空
         * list也清空
         */
        if (sex_map.size() == 1) {
            sex_map.clear();
            sex_list.clear();
        }
        SocketioService socketioService = new SocketioService();
        //获取kafka中的监听数据，并处理为json
        String value = (String) record.value();
        JSONObject jsonObject = JSON.parseObject(value);

        //将数据写入list
        //因为前台报表需要男女比例，所以需要list为2
        //第一次数据可能为脏数据
        sex_list.add(new Gender(jsonObject.getString("usergender"), jsonObject.getLong("count")));
        System.err.println(sex_list);
        if (sex_list.size() == 2) {
            sex_map.put("list", sex_list);
            logger.info("【男女比例】向前台发送数据：" + sex_map);
            //向客户端发送消息,第一个参数与socket.on接收的参数相同
            socketioService.pushArr("connect_msg", sex_map);
        }
    }

    /**
     * 地图报表
     *
     * @param record
     */
    @ApiOperation(value = "/getmapmsg", notes = "获取地图分布信息接口")
    @GetMapping("/getmapmsg")
    @KafkaListener(topics = "mapcharttopic")
    public void mapChart(ConsumerRecord<?, ?> record) {
        if (mapchart_map.size() == 1) {
            mapchart_map.clear();
            mapchart_list.clear();
        }

        SocketioService socketioService = new SocketioService();
        //获取kafka中的监听数据 并处理为json
        String value = (String) record.value();
        JSONObject jsonObject = JSON.parseObject(value);
        mapchart_list.add(new MaporCloudChartpojo(jsonObject.getString("userarea"), jsonObject.getLong("count")));
        mapchart_map.put("list", mapchart_list);
        //System.err.println(mapchart_map);
        logger.info("【地图分布】发送给前台的数据：" + mapchart_map);
        socketioService.pushArr("connect_msg_map", mapchart_map);
    }


    /**
     * 词云
     */
    @ApiOperation(value = "/getwordcloudmsg", notes = "获取词云信息信息接口")
    @GetMapping("/getwordcloudmsg")
    @KafkaListener(topics = "wordcloudtopic")
    public void cloudChart(ConsumerRecord<?, ?> record) {
        if (cloudchart_map.size() == 1) {
            cloudchart_map.clear();
            cloudchart_list.clear();
        }
        SocketioService socketioService = new SocketioService();
        //获取kafka中的监听数据 并处理为json
        String value = (String) record.value();
        JSONObject jsonObject = JSON.parseObject(value);
        cloudchart_list.add(new MaporCloudChartpojo(jsonObject.getString("shoptype"), jsonObject.getLong("count")));
        cloudchart_map.put("list", cloudchart_list);
        logger.info("【词云】发送到前台的数据 ：" + cloudchart_map);
        socketioService.pushArr("connect_msg_cloud", cloudchart_map);

    }


    /**
     * 获得总销售额环比增长速度
     * 每隔2秒
     *
     * @Async 可多线程
     */
    @ApiOperation(value = "/getgrowmsg", notes = "获取总销售额环比增长速度信息接口")
    @GetMapping("/getgrowmsg")
    @Async
    @Scheduled(cron = "0/5 * * * * *")
    public void growmoney() {

        if (grow_map.size() == 1) {
            grow_map.clear();
            grow_list.clear();
        }

        Double growthrate = 0.0;
        logger.info("获取总销售额：" + iReportService.getMoney());
        Long currently = iReportService.getMoney();

        logger.info("上一次的总销售额：" + lastmoney);

        //获得当前时间
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳


        SocketioService socketioService = new SocketioService();

        /**
         * lastmoney = 0是第一次的情况，这里为了避开第一次计算分母为0的情况
         */
        if (lastmoney != 0) {
            growthrate = (currently.doubleValue() - lastmoney.doubleValue()) / lastmoney.doubleValue();
            System.err.println("100前" + growthrate);
            growthrate = growthrate * 100.0;
            System.err.println("100后" + growthrate);
            BigDecimal bg = new BigDecimal(growthrate);
            growthrate = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            growthrate = 100.0;
        }

        logger.info("环比增长速度：" + growthrate);
        lastmoney = currently;

        logger.info("上一次的总销售额变更：" + lastmoney);

        grow_list.add(new GroundMoney(date, growthrate));

        if (grow_list.size() == 3) {
            grow_map.put("list", grow_list);
            logger.info("【环比增长速度】向前台发送数据：" + grow_map);
            socketioService.pushArr("connect_msg_grow", grow_map);
        }

    }

    /**
     * 总销售额
     */
    @ApiOperation(value = "/getallmoneymsg", notes = "获取总销售额接口")
    @GetMapping("/getallmoneymsg")
    @Async
    @Scheduled(cron = "0/5 * * * * *")
    public void allmoney() {
        SocketioService socketioService = new SocketioService();
        socketioService.pushArr("connect_msg_allmoney", iReportService.getMoney());
    }


    /**
     * 订单数据滚动流
     */
    @ApiOperation(value = "/getshoprollmsg", notes = "获取订单数据信息接口")
    @GetMapping("/getshoprollmsg")
    @KafkaListener(topics = "shoptopic")
    public void shoprollChart(ConsumerRecord<?, ?> record) {
        if (shoproll_map.size() == 1) {
            shoproll_map.clear();
            shoproll_list.clear();
        }
        SocketioService socketioService = new SocketioService();
        //获取kafka中的监听数据 并处理为json
        String value = (String) record.value();
        JSONObject jsonObject = JSON.parseObject(value);
        shoproll_list.add(new ShopRoll(jsonObject.getString("shopid"),jsonObject.getString("shopname"),jsonObject.getString("shoptype")));
        shoproll_map.put("list", shoproll_list);
        logger.info("【订单数据滚动流】发送到前台的数据 ：" + shoproll_map);
        socketioService.pushArr("connect_msg_roll", shoproll_map);

    }


}
