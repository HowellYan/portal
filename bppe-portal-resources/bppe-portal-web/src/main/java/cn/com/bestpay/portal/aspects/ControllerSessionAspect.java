package cn.com.bestpay.portal.aspects;

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
            logger.info("yes================================================================");
            return point.proceed();
        } else {
            logger.info("no================================================================");
            proceed = "{'code':'999999','content':'请重新登陆'}";
            point.proceed();
            return proceed;
        }



    }
}
