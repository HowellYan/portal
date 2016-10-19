package cn.com.bestpay.portal.controller.api.lib.js;

import cn.com.bestpay.portal.pojo.ViewModel.HeaderMenuModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        JSONArray jsonArray = new JSONArray();

        HeaderMenuModel headerMenuModel = new HeaderMenuModel();
            headerMenuModel.setMenuID("0");
            headerMenuModel.setMenuName("首页");
            headerMenuModel.setMenuUrl("#Index");
        jsonArray.put(new JSONObject(headerMenuModel));
            headerMenuModel.setMenuID("1");
            headerMenuModel.setMenuName("交费易");
            headerMenuModel.setMenuUrl("#Pay");
        jsonArray.put(new JSONObject(headerMenuModel));
            headerMenuModel.setMenuID("2");
            headerMenuModel.setMenuName("企业理财");
            headerMenuModel.setMenuUrl("#Finances");
        jsonArray.put(new JSONObject(headerMenuModel));
            headerMenuModel.setMenuID("3");
            headerMenuModel.setMenuName("交易查询");
            headerMenuModel.setMenuUrl("#Inquiry");
        jsonArray.put(new JSONObject(headerMenuModel));
            headerMenuModel.setMenuID("4");
            headerMenuModel.setMenuName("账户管理");
            headerMenuModel.setMenuUrl("#Account");
        jsonArray.put(new JSONObject(headerMenuModel));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("HeaderMenuArray",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put("HeaderMenuArray", jsonObject.toString());
        return new ModelAndView("/lib/js/bestpay/bestpay.global", map);
    }

}
