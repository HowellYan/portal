package cn.com.bestpay.portal.exception;

import cn.com.bestpay.portal.resp.ParentResp;
import net.sf.json.JSONObject;

/**
 * Created by Howell on 19/10/16.
 */
public class PortalException extends Exception {
    private String errCode;

    private String errReason;

    private PortalError portalError;

    private ParentResp parentResp;

    public PortalException(PortalError portalError) {
        super("[" + portalError.getCode() + "]" + portalError.getReason());
        this.errCode = portalError.getCode();
        this.errReason = portalError.getReason();
        this.portalError = portalError;
        this.parentResp = new  ParentResp();
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrReason(String errReason) {
        this.errReason = errReason;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrReason() {
        return errReason;
    }

    public ParentResp getParentResp(){
        parentResp.setCode(errCode);
        parentResp.setContent(errReason);
        return parentResp;
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errCode",errCode);
        jsonObject.put("errReason",errReason);
        return jsonObject.toString();
    }
}
