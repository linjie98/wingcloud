package top.wingcloud.stream.flatmap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;

/**
 * 实时销售额的flatmap
 * <IN1, IN2, OUT>
 */
public class MoneyFlatmap implements CoFlatMapFunction<String, HashMap<String, String>, String> {
    //redis key:value -> shopid:money
    private HashMap<String, String> mapdata = new HashMap<String, String>();

    @Override
    public void flatMap1(String value, Collector<String> out) throws Exception {
        JSONObject jsonObject = JSON.parseObject(value);
        String shopid = jsonObject.getString("shopid");
        String money = mapdata.get(shopid);
        System.err.println("商品id : "+shopid);
        System.err.println("销售额 : "+money);
        out.collect(money);
    }

    @Override
    public void flatMap2(HashMap<String, String> value, Collector<String> out) throws Exception {
        mapdata = value;
    }
}
