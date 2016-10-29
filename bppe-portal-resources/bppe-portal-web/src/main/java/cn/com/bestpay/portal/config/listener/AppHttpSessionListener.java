package cn.com.bestpay.portal.config.listener;

import cn.com.bestpay.portal.config.filter.tool.GetSpeedList;
import cn.com.bestpay.portal.config.filter.tool.SpeedIimitation;
import cn.com.bestpay.portal.filter.SpeedModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Howell on 28/10/16.
 */
@WebListener
public class AppHttpSessionListener implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(AppHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("HttpSession初始化");
        HttpSession session = httpSessionEvent.getSession();
        for(SpeedModel speedModel : GetSpeedList.speedModelSet) {
            new SpeedIimitation().setSpeedIimitation(session, speedModel);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("HttpSession销毁");
    }

}
