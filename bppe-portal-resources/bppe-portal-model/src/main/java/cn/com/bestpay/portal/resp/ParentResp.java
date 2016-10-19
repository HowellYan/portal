package cn.com.bestpay.portal.resp;

import cn.com.bestpay.portal.exception.PortalError;
import cn.com.bestpay.portal.exception.PortalException;

/**
 * Created by Howell on 19/10/16.
 */
public class ParentResp {
    private String code = "000000";
    private String content = "成功";
    private String sign;

    public ParentResp(String code, String content) {
        super();
        this.code = code;
        this.content = content;
    }

    public ParentResp(PortalException e) {
        super();
        if (e == null) {
            return;
        }
        this.code = e.getErrCode();
        this.content = e.getErrReason();
    }

    public ParentResp() {
    }

    public ParentResp(PortalError e) {
        this.code = e.getCode();
        this.content = e.getReason();
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
