package top.wingcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author: linjie
 * @description:
 * @create: 2018/10/11 16:04
 */
@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
public class GateWayApplication {
    public static void main(String[] args){
        SpringApplication.run(GateWayApplication.class,args);
    }
}
