package cn.com.bestpay.portal.config.listener;

import cn.com.bestpay.portal.config.filter.tool.SetVersion;
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
public class InitListener  implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(InitListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("----------启动成功-----------");
        SystemProperty systemProperty = new SystemProperty();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
