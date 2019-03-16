package top.wingcloud.stream.flatmap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;

/**
 * 实时订单数据的flatmap
 * <IN1, IN2, OUT>
 */
public class ShopFlatmap implements CoFlatMapFunction<String, HashMap<String, String>, String> {
    //redis key:value -> shopid:shopname
    private HashMap<String, String> mapdata = new HashMap<String, String>();

    @Override
    public void flatMap1(String value, Collector<String> out) throws Exception {
        JSONObject jsonObject = JSON.parseObject(value);
        String shopid = jsonObject.getString("shopid");
        String shoptype = jsonObject.getString("shoptype");
        String shopname = mapdata.get(shopid);
        String resstring = "{\"shopid\":\""+shopid+"\",\"shopname\":\""+shopname+"\",\"shoptype\":\""+shoptype+"\"}";
        System.err.println("【实时订单数据】: "+resstring);
        out.collect(resstring);
    }

    @Override
    public void flatMap2(HashMap<String, String> value, Collector<String> out) throws Exception {
        mapdata = value;
    }
}
