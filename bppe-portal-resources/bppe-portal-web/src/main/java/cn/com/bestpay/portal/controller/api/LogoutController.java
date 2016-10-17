package cn.com.bestpay.portal.controller.api;

import cn.com.bestpay.portal.Model.UtilsModel.UserInfoModel;
import cn.com.bestpay.portal.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by yfzx_gd_yanghh on 2016/10/17.
 */
@Controller
public class LogoutController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public ModelAndView userLogin( HttpSession session) {
        session.removeAttribute("userSession");
        Enumeration<String> enm = session.getAttributeNames();
        while (enm.hasMoreElements()) {
            session.removeAttribute(enm.nextElement());
        }
        session.invalidate();
        session = null;
        return new ModelAndView("redirect:/Login/main.html");
    }
}
