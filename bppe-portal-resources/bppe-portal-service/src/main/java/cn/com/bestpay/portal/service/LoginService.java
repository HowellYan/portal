package cn.com.bestpay.portal.service;

import cn.com.bestpay.portal.model.UtilsModel.UserInfoModel;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Howell on 17/10/16.
 */
@Service
public class LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public boolean userLogin(HttpServletRequest request ,HttpSession session){
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("username:"+userName);
        logger.info("password:"+password);
        if(userName.equals("123456") && password.equals("123456")){
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setStaffCode(userName);
            session.setAttribute("userSession",userInfoModel);
            return true;
        }
        return false;
    }
}
