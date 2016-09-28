package cn.com.bestpay.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@Controller
public class HTMLController {
    /**
     * 应用版本
     * @return project.version
     */

    @RequestMapping("/")
    @ResponseBody
    public  String state(){
        return "online_H5 Start state";
    }

    @RequestMapping(value = "/**/*.html", method = RequestMethod.GET)
    public ModelAndView main() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "ypf");
        return new ModelAndView("index/main",map);
    }
}
