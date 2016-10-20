package cn.com.bestpay.portal.controller.api.index;

import cn.com.bestpay.portal.controller.BaseController;
import cn.com.bestpay.portal.pojo.UtilsModel.UserInfoModel;
import cn.com.bestpay.portal.resp.ParentResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Howell on 6/10/16.
 */
@RestController
public class IndexController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/api/index/main",method = RequestMethod.POST)
    @ResponseBody
    public ParentResp main(@RequestBody String body){
        logger.info("RequestBody:"+body);

        ParentResp parentResp = new ParentResp();
        parentResp.setCode("000000");
        parentResp.setContent("成功");

        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setCustName("成功");
        session.setAttribute("userSession",userInfoModel);

        //JSONObject.fromObject("{\"code\":\"000000\",\"content\":\"成功\"}");
        return parentResp;
    }
}
