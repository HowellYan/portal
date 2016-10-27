package cn.com.bestpay.portal.controller.api.lib.js;

import cn.com.bestpay.portal.SecurityScript.SecurityHTML;
import cn.com.bestpay.portal.pojo.UtilsModel.UserInfoModel;
import cn.com.bestpay.portal.pojo.ViewModel.HeaderMenuModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
@Controller
public class JSController {

    @Autowired
    HttpSession session;

    @Autowired
    SecurityHTML securityHTML;
    /**
     * 应用版本
     * @return project.version
     */
    @RequestMapping(value = "/lib/js/bestpay/bestpay.global.js", method = RequestMethod.GET)
    public ModelAndView globalJS(HttpServletRequest request) {
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

        try {
            if(session.getAttribute("userSession") != null) {
                jsonObject.put("UserInfoModel", new JSONObject((UserInfoModel) session.getAttribute("userSession")));
            } else {
                jsonObject.put("UserInfoModel", new JSONObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put("injections", jsonObject.toString());
        return new ModelAndView("/lib/js/bestpay/bestpay.global", map);
    }

    @RequestMapping(value = "/api/security/random", method = RequestMethod.POST)
    @ResponseBody
    public String getRandom(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            String RD = securityHTML.getSecurityRD("index",
                    "",
                    "SecurityHTML_Index",
                    "SecurityHTML_Index_rd",
                    "SecurityHTML_Index_Key",
                    false,
                    "pwdmark:0",
                    request
            );
            jsonObject.put("SecurityScriptRD", RD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
