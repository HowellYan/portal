package cn.com.bestpay.portal.service;


import cn.com.bestpay.portal.SecurityPassword.impl.Password;
import cn.com.bestpay.portal.pojo.UtilsModel.UserInfoModel;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.bestpay.service.bcs.Bcs90001Service;
import com.bestpay.service.cms.Cms0006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Howell on 17/10/16.
 */
@Service
public class LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);


//    @Autowired
//    private Bcs90001Service bcs90001Service;
//
//    @Autowired
//    private Cms0006Service cms0006Service;


    public boolean userLogin(HttpServletRequest request ,HttpSession session){
        String userName = request.getParameter("username");
        String password = request.getParameter("loginpwd");
        String machineNetwork = request.getParameter("machineNetwork");
        String machineCPU = request.getParameter("machineCPU");
        String machineDisk = request.getParameter("machineDisk");
        try {
            password = Password.decode("login-pwd-ses-key", password, Password.SESSION_SCOPE, request, null);
            machineNetwork = Password.decode("login-pwd-ses-key", machineNetwork, Password.SESSION_SCOPE, request, null);
            machineCPU = Password.decode("login-pwd-ses-key", machineCPU, Password.SESSION_SCOPE, request, null);
            machineDisk = Password.decode("login-pwd-ses-key", machineDisk, Password.SESSION_SCOPE, request, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }
        logger.info("machineNetwork:"+machineNetwork);
        logger.info("machineCPU:"+machineCPU);
        logger.info("machineDisk:"+machineDisk);
        logger.info("username:"+userName);
        logger.info("password:"+password);
        if(userName.equals("123456") && password.equals("123456")){
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setStaffCode(userName);
            userInfoModel.setMachineNetwork(machineNetwork);
            userInfoModel.setMachineCPU(machineCPU);
            userInfoModel.setMachineDisk(machineDisk);

            session.setAttribute("userSession",userInfoModel);
            return true;
        }



        return false;
    }
}
