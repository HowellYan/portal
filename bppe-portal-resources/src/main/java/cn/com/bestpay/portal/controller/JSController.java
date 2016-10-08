package cn.com.bestpay.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
public class JSController {
    /**
     * 应用版本
     * @return project.version
     */

    @RequestMapping(value = "/lib/js/bestpay/bestpay.global.js", method = RequestMethod.GET)
    public ModelAndView globalJS() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("HeaderMenuArray", "[" +
                "        {" +
                "            \"menuID\":\"0\"," +
                "            \"menuName\":\"首页\"," +
                "            \"menuUrl\":\"#Index\"," +
                "            \"template\":\"\"," +
                "            \"menuTip\":\"\"" +
                "        },{" +
                "            \"menuID\":\"1\"," +
                "            \"menuName\":\"交费易\"," +
                "            \"menuUrl\":\"#Pay\"," +
                "            \"template\":\"\"," +
                "            \"menuTip\":\"\"" +
                "        },{" +
                "            \"menuID\":\"2\"," +
                "            \"menuName\":\"企业理财\"," +
                "            \"menuUrl\":\"#Finances\"," +
                "            \"template\":\"\"," +
                "            \"menuTip\":\"\"," +
                "            \"skipping\":\"true\"" +
                "        },{" +
                "            \"menuID\":\"3\"," +
                "            \"menuName\":\"交易查询\"," +
                "            \"menuUrl\":\"#Inquiry\"," +
                "            \"template\":\"\"," +
                "            \"menuTip\":\"\"" +
                "        },{" +
                "            \"menuID\":\"4\"," +
                "            \"menuName\":\"账户管理\"," +
                "            \"menuUrl\":\"#Account\"," +
                "            \"template\":\"\"," +
                "            \"menuTip\":\"\"" +
                "        }" +
                "    ]");
        return new ModelAndView("/lib/js/bestpay/bestpay.global", map);
    }

}
