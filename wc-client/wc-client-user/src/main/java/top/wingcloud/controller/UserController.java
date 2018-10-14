package top.wingcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: linjie
 * @description: 用户服务请求处理控制器
 * @create: 2018/10/13 09:16
 */
@RestController
public class UserController {
    @Value("${server.port}")
    private int port;

    @RequestMapping("index")
    public String index(){
        return "Hello World!"+port;
    }
}
