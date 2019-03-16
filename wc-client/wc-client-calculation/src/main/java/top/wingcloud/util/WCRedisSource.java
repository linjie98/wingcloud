package top.wingcloud.util;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;

/**
 * 作用：将redis作为source，获取redis中的数据
 *
 * redis中保存 店铺id和店铺名的关系
 *
 * hset test 001 a  (test表示大key ，001表示店铺id ，a表示店铺名称)
 *
 * hset/hget 存储的是一个数据对象，相当于在学校塞入学生的时候，确定好了班级，查找的时候，先找到班级再找学生。
 *
 * 对于大量数据而言 hset/hget 要优于 set/get。
 *
 * by：linjie
 */
public class WCRedisSource extends RichParallelSourceFunction<HashMap<String, String>> {

    private static Logger logger = LoggerFactory.getLogger(WCRedisSource.class);
    //设置每次取数据的休眠时间常量
    private final Long SLEEP_MILLION = 50000L;
    private boolean isRunning = true;
    private Jedis jedis = null;
    private String redis_hkey;

    public WCRedisSource(String redis_hkey) {
        this.redis_hkey = redis_hkey;
    }

    /**
     * 建立redis连接
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.jedis = new Jedis("192.168.43.93", 6379);
    }

    /**
     * 允许任务
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext<HashMap<String, String>> ctx) throws Exception {

        //存储redis取出来的数据
        HashMap<String, String> shops = new HashMap<String, String>();
        //取出Redis中的相关数据
        while (isRunning){
            try {
                //获取redis数据前，先清楚hashmap中的数据
                shops.clear();
                shops = (HashMap<String, String>) jedis.hgetAll(redis_hkey);
                //Map<String,String>
                if (shops.size()>0){
                    System.out.println(shops.size());
                    System.out.println(shops);
                    ctx.collect(shops);
                }else {
                    logger.warn("获取Redis数据为空");
                }

                Thread.sleep(SLEEP_MILLION);
            }catch (JedisConnectionException e){
                logger.error("Redis连接异常,正在尝试重新连接",e.getCause());
                jedis = new Jedis("localhost", 6379);
            }catch (Exception e){
                logger.error("Redis数据源获取异常",e.getCause());
            }
        }
    }

    /**
     * 关闭任务
     */
    @Override
    public void cancel() {
        isRunning = false;
        //关闭任务，redis也关闭
        if (jedis != null){
            jedis.close();
        }
    }


}
