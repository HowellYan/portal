package cn.com.bestpay.portal.aspects;

import cn.com.bestpay.portal.SecurityPassword.impl.Password;
import cn.com.bestpay.portal.config.filter.tool.SpeedIimitation;
import cn.com.bestpay.portal.exception.PortalError;
import cn.com.bestpay.portal.exception.PortalException;
import cn.com.bestpay.portal.pojo.UtilsModel.UserInfoModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void cutRestController() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {

    }

    @Pointcut("execution(public * speed*(..))")
    public void cutSpeed(){

    }

    /**
     * 接口防刷切面，请以speed 为开头命名Controller的方法
     * @param point
     * @param request
     * @return
     * @throws Throwable
     */
    @Around(" cutSpeed() && allMethod() && args(..,request)")
    public Object speedIimitation(ProceedingJoinPoint point, HttpServletRequest request) throws Throwable{
        session = request.getSession();
        String requestURI = request.getRequestURI();
        String method = request.getMethod().toLowerCase();
        SpeedIimitation speedIimitation = new SpeedIimitation();
        if(!speedIimitation.speedIimitationAction(session,requestURI,method)) {
            long waitTime = speedIimitation.getWaitTime(session,requestURI,method);
            PortalError portalError = PortalError.Speed_msg;
            portalError.setReason("请求次数过多！,请"+waitTime+"分钟再试！");
            return new PortalException(portalError).toJson();
        } else {
            return point.proceed();
        }
    }

    @Around("cutRestController()")
    public Object verificationSession(ProceedingJoinPoint point) throws Throwable{
        Object proceed = null;
        if(session.getAttribute("userSession") != null){
            return point.proceed();
        } else {
            return new PortalException(PortalError.Logout_msg).getParentResp();
        }
    }

    @Around("cutRestController() && allMethod() && args(..,request)")
    public Object verificationDeviceInfo(ProceedingJoinPoint point, HttpServletRequest request) throws Throwable{

        String servletRequest = (String) point.getArgs()[0];
        JSONObject jsonObject = new JSONObject(servletRequest);

        String MachineNetwork = Password.decode("SecurityHTML_Index_Key", jsonObject.getString("MachineNetwork"), Password.SESSION_SCOPE, request, null);
        String MachineDisk = Password.decode("SecurityHTML_Index_Key", jsonObject.getString("MachineDisk"), Password.SESSION_SCOPE, request, null);
        String MachineCPU = Password.decode("SecurityHTML_Index_Key", jsonObject.getString("MachineCPU"), Password.SESSION_SCOPE, request, null);
        logger.info(MachineNetwork);
        logger.info(MachineDisk);
        logger.info(MachineCPU);
        if(session.getAttribute("userSession") != null){
            UserInfoModel userInfoModel = (UserInfoModel)session.getAttribute("userSession");
            String loginMachineNetwork = userInfoModel.getMachineNetwork();
            String loginMachineCPU = userInfoModel.getMachineCPU();
            String loginMachineDisk = userInfoModel.getMachineDisk();

            if(!loginMachineNetwork.equals(MachineNetwork) ||
                    !loginMachineCPU.equals(MachineCPU) ||
                    !loginMachineDisk.equals(MachineDisk)){
                return new PortalException(PortalError.Device_msg).getParentResp();
            }
            session.removeAttribute("SecurityHTML_Index_rd");
            session.removeAttribute("SecurityHTML_Index_Key");
            return point.proceed();
        } else {
            return new PortalException(PortalError.Logout_msg).getParentResp();
        }
    }

}
