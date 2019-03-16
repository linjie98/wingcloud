package top.wingcloud.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 从配置文件中获取微服务信息
 */
@PropertySource({"classpath:service.properties"})
@Component
public class ServiceMsgConfig {

    @Value("${service1}")
    private String service_name1;
    @Value("${service1.port}")
    private String service_name1_port;

    @Value("${service2}")
    private String service_name2;
    @Value("${service2.port}")
    private String service_name2_port;

    @Value("${service3}")
    private String service_name3;
    @Value("${service3.port}")
    private String service_name3_port;

    public String getService_name1() {
        return service_name1;
    }

    public String getService_name2() {
        return service_name2;
    }

    public String getService_name3() {
        return service_name3;
    }

    public String getService_name1_port() {
        return service_name1_port;
    }

    public String getService_name2_port() {
        return service_name2_port;
    }

    public String getService_name3_port() {
        return service_name3_port;
    }
}
