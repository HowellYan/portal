package cn.com.bestpay.portal.SecurityScript;

import cn.com.bestpay.portal.SecurityPassword.impl.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by susie on 2016/10/25.
 */
@Service
public class SecurityHTML  {
    private static Logger logger = LoggerFactory.getLogger(SecurityHTML.class);

    @Autowired
    HttpSession session;

    public String getSecurityHTML(String id, String clazz, String name, String rdName, String sessionKey, boolean rdFromSession, String params,
            HttpServletRequest request){
        try {
            Password.initPwdImpl("cn.com.bestpay.portal.SecurityPassword.impl.PassGuardCtrl",request);
            return Password.writePWDInput(id, clazz, name, rdName, sessionKey,rdFromSession,request,params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        return null;
    }

    public String getSecurityRD(String id, String clazz, String name, String rdName, String sessionKey, boolean rdFromSession, String params,
                                  HttpServletRequest request){
        try {
            getSecurityHTML(id, clazz, name, rdName, sessionKey,rdFromSession,params,request);
            return session.getAttribute(rdName).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        return null;
    }

}
