package cn.com.bestpay.portal.config.listener;

import cn.com.bestpay.portal.config.filter.tool.GetSpeedList;
import cn.com.bestpay.portal.config.filter.tool.SpeedIimitation;
import cn.com.bestpay.portal.filter.SpeedModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Howell on 28/10/16.
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(HttpSessionListenerImpl.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("HttpSession初始化");
        ServletContext app = httpSessionEvent.getSession().getServletContext();
        HttpSession session = httpSessionEvent.getSession();
        int count = 0;
        if(app.getAttribute("onLineCount") != null){
            count = Integer.parseInt(app.getAttribute("onLineCount").toString());
            count++;
            app.setAttribute("onLineCount", count);
        } else {
            app.setAttribute("onLineCount", count);
        }
        GetSpeedList getSpeedList = new GetSpeedList();
        for(SpeedModel speedModel : getSpeedList.getSpeedModelSet()) {
            new SpeedIimitation().setSpeedIimitation(session, speedModel);
        }
        logger.info("session.getId:"+session.getId());
        logger.info("在线人数：" + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("HttpSession销毁");
        ServletContext app = httpSessionEvent.getSession().getServletContext();
        if(app.getAttribute("onLineCount") != null){
            int count = Integer.parseInt(app.getAttribute("onLineCount").toString());
            count--;
            app.setAttribute("onLineCount", count);
            logger.info("在线人数：" + count);
        }
    }

}
