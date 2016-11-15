package cn.com.bestpay.portal.controller;


import cn.com.bestpay.portal.SecurityScript.SecurityHTML;
import cn.com.bestpay.portal.View.HbsViewResolver;
import cn.com.bestpay.portal.pojo.AppcenterModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@Controller
public class HTMLController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(HTMLController.class);

    @Autowired
    SecurityHTML securityHTML;

    @RequestMapping(value = "/{First}.html", method = RequestMethod.GET)
    public ModelAndView First(@PathVariable("First")String First) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "Hi!");
        return new ModelAndView(First, map);
    }

    @RequestMapping(value = "/{First}/{Second}.html", method = RequestMethod.GET)
    public ModelAndView Second(@PathVariable("First")String First,
                               @PathVariable("Second")String Second,
                               HttpServletRequest request
                               ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "'Hi',首页!");
        List<AppcenterModel> modelList = new ArrayList<>();
        AppcenterModel appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("1");
        appcenterModel.setAppName("yang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("true");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("2");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("false");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("3");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        appcenterModel.setIsTrue("true");
        modelList.add(appcenterModel);
        map.put("_DATA_", modelList);

        return new ModelAndView(First+"/"+Second, map);
    }


    /**
     * 安全密码控件
     * @return
     */
    @RequestMapping(value = "/webCommon/SecurityPassword/{First}/{Second}.hbs", method = RequestMethod.GET)
    @ResponseBody
    public String getWebCommonView(@PathVariable("First")String First, @PathVariable("Second")String Second, HttpServletRequest request) {
        logger.info("getWebCommonView file :"+Second);
        //return new ModelAndView("forward:/webCommon/SecurityPassword/"+First+"/"+Second+".jsp");

        String retHTML = securityHTML.getSecurityHTML("loginpwd",
                "loginpwd",
                "loginpwd",
                "login-rd",
                "login-pwd-ses-key",
                false,
                "pwdmark:0",
                request
        );

        return retHTML;
    }



}
