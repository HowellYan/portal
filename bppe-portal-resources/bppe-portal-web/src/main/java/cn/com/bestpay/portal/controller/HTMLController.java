package cn.com.bestpay.portal.controller;

import cn.com.bestpay.portal.Model.AppcenterModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@Controller
public class HTMLController extends BaseController{
    /**
     * 应用版本
     * @return project.version
     */

    @RequestMapping(value = "/{First}.html", method = RequestMethod.GET)
    public ModelAndView First(@PathVariable("First")String First) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "Hi!");
        return new ModelAndView(First, map);
    }

    @RequestMapping(value = "/{First}/{Second}.html", method = RequestMethod.GET)
    public ModelAndView Second(@PathVariable("First")String First, @PathVariable("Second")String Second) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "'Hi',首页!");
        List<AppcenterModel> modelList = new ArrayList<>();
        AppcenterModel appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("1");
        appcenterModel.setAppName("yang");
        appcenterModel.setAppUrl("123123");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("2");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        modelList.add(appcenterModel);
        appcenterModel = new AppcenterModel();
        appcenterModel.setAppId("3");
        appcenterModel.setAppName("tang");
        appcenterModel.setAppUrl("123123");
        modelList.add(appcenterModel);
        map.put("_DATA_", modelList);
        return new ModelAndView(First+"/"+Second, map);
    }

    @RequestMapping(value = "/{First}/{Second}/{Third}.html", method = RequestMethod.GET)
    public ModelAndView Third(@PathVariable("First")String First,
                             @PathVariable("Second")String Second,
                             @PathVariable("Third")String Third) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "Hi!");
        return new ModelAndView(First+"/"+Second+"/"+Third, map);
    }
}
