package top.wingcloud.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import top.wingcloud.service.SocketioService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Socketio监听器
 *
 * by：linjie
 */
@Configuration
@WebListener
public class ReportSocketioListen implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(ReportSocketioListen.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //启动Socketio服务
        SocketioService socketio = new SocketioService();
        logger.info("启动Socketio服务");
        socketio.startServer();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //关闭Socketio服务
        SocketioService socketio = new SocketioService();
        logger.info("关闭Socketio服务");
        socketio.stopSocketio();
    }

}
