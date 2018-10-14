package top.wingcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: linjie
 * @description:
 * @create: 2018/10/12 23:00
 */
@EnableEurekaClient
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args){
        SpringApplication.run(UserApplication.class,args);
    }
}
