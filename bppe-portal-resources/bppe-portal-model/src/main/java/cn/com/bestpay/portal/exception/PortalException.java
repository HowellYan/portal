package cn.com.bestpay.portal.exception;

/**
 * Created by Howell on 19/10/16.
 */
public class PortalException extends Exception {
    private String errCode;

    private String errReason;

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
}
