package cn.com.bestpay.webcontent.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by susie on 2016/9/13.
 */
@Controller
@RequestMapping("/")
public class Index {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getUserInfoByCode() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "ypf");
        return new ModelAndView("index",map);
    }
}
