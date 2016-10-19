package cn.com.bestpay.portal.aspects;

import cn.com.bestpay.portal.exception.PortalError;
import cn.com.bestpay.portal.exception.PortalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by Howell on 19/10/16.
 */
@Component
@Aspect
public class ControllerSessionAspect {
    private static Logger logger = LoggerFactory.getLogger(ControllerSessionAspect.class);

    @Autowired
    HttpSession session;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void cutRestController() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {

    }
    @Around("cutRestController()")
    public Object verificationSession(ProceedingJoinPoint point) throws Throwable{
        Object proceed = null;
        if(session.getAttribute("userSession") != null){
            return point.proceed();
        } else {
            return  new PortalException(PortalError.Logout_msg).getParentResp();
        }
    }
}
