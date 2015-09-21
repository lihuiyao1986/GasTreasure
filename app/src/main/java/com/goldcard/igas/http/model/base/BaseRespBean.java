package com.goldcard.igas.http.model.base;

import java.io.Serializable;

/**
 * Created by yanshengli on 15-5-4.
 */
public class BaseRespBean implements Serializable {

    private static final long serialVersionUID = 2828612594273173474L;

    // 响应码
    private String responseCode;

    // 响应描述
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
