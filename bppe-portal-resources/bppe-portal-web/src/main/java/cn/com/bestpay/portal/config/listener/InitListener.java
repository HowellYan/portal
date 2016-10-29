package cn.com.bestpay.portal.config.listener;

import cn.com.bestpay.portal.config.property.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * Created by yfzx_gd_yanghh on 2016/9/29.
 */
@WebListener
public class InitListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(InitListener.class);


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("ServletContex初始化");
        SystemProperty systemProperty = new SystemProperty();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("ServletContex销毁");
    }


}
