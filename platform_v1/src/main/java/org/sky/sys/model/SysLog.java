package org.sky.sys.model;

import java.util.Date;

public class SysLog {
    private String id;

    private String userCode;

    private Date optTime;

    private String ip;

    private String url;

    private String optName;

    private String optMethod;

    private String optParam;

    private Short lastTime;

    private String optResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getOptMethod() {
        return optMethod;
    }

    public void setOptMethod(String optMethod) {
        this.optMethod = optMethod;
    }

    public String getOptParam() {
        return optParam;
    }

    public void setOptParam(String optParam) {
        this.optParam = optParam;
    }

    public Short getLastTime() {
        return lastTime;
    }

    public void setLastTime(Short lastTime) {
        this.lastTime = lastTime;
    }

    public String getOptResult() {
        return optResult;
    }

    public void setOptResult(String optResult) {
        this.optResult = optResult;
    }
}