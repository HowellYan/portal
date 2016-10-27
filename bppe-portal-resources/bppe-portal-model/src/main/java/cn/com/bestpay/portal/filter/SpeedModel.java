package cn.com.bestpay.portal.filter;

import java.io.Serializable;

/**
 * Created by Howell on 27/10/16.
 */
public class SpeedModel implements Serializable {
    private String RequestURI;
    private String Methods;
    /**
     * 速率个数
     */
    private int SpeedNumber;
    /**
     * 速率时间(分)
     */
    private long SpeedTime;
    /**
     * 限制时间(分)
     */
    private int LimitTime;
    /**
     * 累计个数
     */
    private int AccumulationNumber = 0;

    /**
     * 起始时间
     */
    private String StratTime;

    public String getRequestURI() {
        return RequestURI;
    }

    public void setRequestURI(String requestURI) {
        RequestURI = requestURI;
    }

    public String getMethods() {
        return Methods;
    }

    public void setMethods(String methods) {
        Methods = methods;
    }

    public int getSpeedNumber() {
        return SpeedNumber;
    }

    public void setSpeedNumber(int speedNumber) {
        SpeedNumber = speedNumber;
    }

    public long getSpeedTime() {
        return SpeedTime;
    }

    public void setSpeedTime(long speedTime) {
        SpeedTime = speedTime;
    }

    public int getLimitTime() {
        return LimitTime;
    }

    public void setLimitTime(int limitTime) {
        LimitTime = limitTime;
    }

    public int getAccumulationNumber() {
        return AccumulationNumber;
    }

    public void setAccumulationNumber(int accumulationNumber) {
        AccumulationNumber = accumulationNumber;
    }

    public String getStratTime() {
        return StratTime;
    }

    public void setStratTime(String stratTime) {
        StratTime = stratTime;
    }
}
