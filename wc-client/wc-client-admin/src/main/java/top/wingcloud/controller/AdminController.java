package top.wingcloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.wingcloud.config.ServiceMsgConfig;
import top.wingcloud.domain.ServiceMsg;
//import top.wingcloud.config.ServiceMsgConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理服务控制器
 */
@RestController
@Api("后台管理接口")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ServiceMsgConfig serviceMsgConfig;

    /**
     * 获取微服务名称和占用端口号
     * @return
     */
    @RequestMapping(value = "/getserviceport")
    @ApiOperation(value = "/getserviceport", notes = "获取服务信息接口")
    public Map getService() {
        HashMap servicemap = new HashMap();
        List servicelist = new ArrayList();

        servicelist.add(new ServiceMsg(serviceMsgConfig.getService_name1(),serviceMsgConfig.getService_name1_port()));
        servicelist.add(new ServiceMsg(serviceMsgConfig.getService_name2(),serviceMsgConfig.getService_name2_port()));
        servicelist.add(new ServiceMsg(serviceMsgConfig.getService_name3(),serviceMsgConfig.getService_name3_port()));

        servicemap.put("list",servicelist);
        logger.info("获取到服务名称和端口信息--》"+servicemap);
        return servicemap;
    }
}
