package top.wingcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: linjie
 * @description: wingcloud服务注册中心
 * @create: 2018/10/11 15:32
 */
@EnableEurekaServer
@SpringBootApplication
public class
RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class,args);
    }
}
