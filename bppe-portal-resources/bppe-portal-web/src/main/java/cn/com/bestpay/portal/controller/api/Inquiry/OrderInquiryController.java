package cn.com.bestpay.portal.controller.api.Inquiry;

import cn.com.bestpay.portal.View.HbsViewResolver;
import cn.com.bestpay.portal.resp.ParentResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Howell on 6/11/16.
 */
@Controller
public class OrderInquiryController {
    private static Logger logger = LoggerFactory.getLogger(OrderInquiryController.class);

    @Autowired
    HbsViewResolver hbsResolver;

    private String modelList = "";

    @RequestMapping(value = "/api/inquiry/orderInquiry", method = RequestMethod.POST)
    @ResponseBody
    public ParentResp RestOrderInquiry(HttpServletRequest request, HttpSession session) {
        modelList = request.getParameter("StartTime");

        ParentResp parentResp = new ParentResp();
        parentResp.setCode("000000");
        parentResp.setContent("成功");

        return parentResp;
    }


    @RequestMapping(value = "/Inquiry/orderInquiry/orderInquiryData.hbs", method = RequestMethod.GET)
    public ModelAndView setOrderInquiryData(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView modelAndView = null;
        map.put("_DATA_", "sdsasda");
        try {
            View view = hbsResolver.resolveViewName("/Inquiry/orderInquiry/orderInquiryData", Locale.CHINA);
            modelAndView = new ModelAndView(view, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage().toString());
        }

        return modelAndView;
    }
}
