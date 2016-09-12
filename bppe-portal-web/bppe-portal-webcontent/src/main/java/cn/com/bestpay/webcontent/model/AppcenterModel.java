package cn.com.bestpay.webcontent.model;

import java.io.Serializable;

/**
 * Created by Howell on 16/2/2.
 */
public class AppcenterModel implements Serializable {
    String appName;
    String appId;
    String appUrl;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }
}
