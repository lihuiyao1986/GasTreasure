package com.goldcard.igas.http.model.trade;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by jk on 15/9/18.
 */
public class WebchatPayOrderResultInfo implements Serializable {
    private static final long serialVersionUID = 1972642082828469664L;
    private String prepayid;
    private String sign;
    private String partnerid;
    private String appid;
    private String timestamp;
    private String noncestr;
    @JSONField(name = "package")
    private String webchatPackage;

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getWebchatPackage() {
        return webchatPackage;
    }

    public void setWebchatPackage(String webchatPackage) {
        this.webchatPackage = webchatPackage;
    }
}
