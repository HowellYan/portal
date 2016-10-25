package cn.com.bestpay.portal.controller.api;

import cn.com.bestpay.portal.SecurityPassword.impl.Password;
import cn.com.bestpay.portal.controller.BaseController;
import cn.com.bestpay.portal.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by yfzx_gd_yanghh on 2016/10/15.
 */
@Controller
public class LoginController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public LoginService loginService;


    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ModelAndView userLogin(HttpServletRequest request, HttpSession session) {
        String loginpwd = request.getParameter("loginpwd");
        String machineNetwork = request.getParameter("machineNetwork");
        String machineCPU = request.getParameter("machineCPU");
        String machineDisk = request.getParameter("machineDisk");
        try {
            loginpwd = Password.decode("login-pwd-ses-key", loginpwd, Password.SESSION_SCOPE, request, null);
            machineNetwork = Password.decode("login-pwd-ses-key", machineNetwork, Password.SESSION_SCOPE, request, null);
            machineCPU = Password.decode("login-pwd-ses-key", machineCPU, Password.SESSION_SCOPE, request, null);
            machineDisk = Password.decode("login-pwd-ses-key", machineDisk, Password.SESSION_SCOPE, request, null);

            logger.info("loginpwd:"+loginpwd);
            logger.info("machineNetwork:"+machineNetwork);
            logger.info("machineCPU:"+machineCPU);
            logger.info("machineDisk:"+machineDisk);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        if(loginService.userLogin(request, session)){
             return new ModelAndView("redirect:/Index/main.html");
         } else {
             return new ModelAndView("redirect:/Login/main.html");
         }

    }

}
