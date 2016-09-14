package cn.com.bestpay.webcontent.controller.api;

import cn.com.bestpay.webcontent.model.AppcenterModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by susie on 2016/9/13.
 */
@Controller
@RequestMapping("/")
public class Index {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "ypf");


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

        return new ModelAndView("index",map);
    }
    @RequestMapping(value = "/js/index", method = RequestMethod.GET)
    public ModelAndView indexJS() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "ypf");
        return new ModelAndView("js/index",map);
    }
}
