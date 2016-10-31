package cn.com.bestpay.portal.exception;

import cn.com.bestpay.portal.resp.ParentResp;
import net.sf.json.JSONObject;

/**
 * Created by Howell on 19/10/16.
 */
public class PortalException extends Exception {
    private String code;

    private String content;

    private PortalError portalError;

    private ParentResp parentResp;

    public PortalException(PortalError portalError) {
        super("[" + portalError.getCode() + "]" + portalError.getReason());
        this.code = portalError.getCode();
        this.content = portalError.getReason();
        this.portalError = portalError;
        this.parentResp = new  ParentResp();
    }

    public void setErrCode(String errCode) {
        this.code = errCode;
    }

    public void setErrReason(String content) {
        this.content = content;
    }

    public String getErrCode() {
        return code;
    }

    public String getErrReason() {
        return content;
    }

    public ParentResp getParentResp(){
        parentResp.setCode(code);
        parentResp.setContent(content);
        return parentResp;
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("content",content);
        return jsonObject.toString();
    }
}
