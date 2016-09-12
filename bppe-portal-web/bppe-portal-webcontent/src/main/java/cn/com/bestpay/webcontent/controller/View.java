package cn.com.bestpay.webcontent.controller;


import cn.com.bestpay.common.model.Paging;
import cn.com.bestpay.webcontent.model.AppcenterModel;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Howell on 16/1/27.
 */
@Controller
@RequestMapping("/")
public class View {
    @Autowired
    public ViewRender viewRender;

    /**
     * 一级域名页面渲染入口
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String oneView(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
        Map<String, Object> params = Maps.newHashMap();
        ((Map)params).put("_ID_", "HIHI!");
        return viewRender.view("","/"+name,request,response,params,false);
    }

    /**
     * 二级域名页面渲染入口
     */
    @RequestMapping(value = "/{name}/index", method = RequestMethod.GET)
    @ResponseBody
    public String twoView(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
        Map<String, Object> params = Maps.newHashMap();
        Paging<AppcenterModel> paging= new Paging<>();

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


        ((Map)params).put("_DATA_", modelList);
        String html =  viewRender.view("/index","/layout/"+name,request,response,params,true);
        return html;
    }

    /**
     * 三级域名页面渲染入口
     */
    @RequestMapping(value = "/MyLove/appcenter/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String otherView(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
        Map<String, Object> params = Maps.newHashMap();

        String html =  viewRender.view(name,"/layout/MyLove/appcenter/",request,response,params,true);
        return html;
    }
}

