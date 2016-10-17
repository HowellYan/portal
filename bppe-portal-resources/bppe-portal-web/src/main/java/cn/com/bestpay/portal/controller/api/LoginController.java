package cn.com.bestpay.portal.controller.api;

import cn.com.bestpay.portal.Model.UtilsModel.UserInfoModel;
import cn.com.bestpay.portal.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yfzx_gd_yanghh on 2016/10/15.
 */
@Controller
public class LoginController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ModelAndView userLogin(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("username:"+userName);
        logger.info("password:"+password);
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setStaffCode(userName);
        session.setAttribute("userSession",userInfoModel);
        return new ModelAndView("redirect:/Index/main.html");
    }

}
