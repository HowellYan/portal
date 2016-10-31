package cn.com.bestpay.portal.controller.api.lib.js;

import cn.com.bestpay.portal.SecurityScript.SecurityHTML;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Howell on 27/10/16.
 */
@Controller
public class SecurityRandomController {

    @Autowired
    HttpSession session;

    @Autowired
    SecurityHTML securityHTML;

    @RequestMapping(value = "/api/security/random", method = RequestMethod.POST, produces="application/json;charset=utf-8", headers = "content-type=application/json; charset=utf-8")
    @ResponseBody
    public String speedGetRandom(HttpServletRequest request) {
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
