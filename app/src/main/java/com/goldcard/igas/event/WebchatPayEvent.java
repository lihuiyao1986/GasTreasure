package com.goldcard.igas.event;

import com.tencent.mm.sdk.modelbase.BaseResp;

import java.io.Serializable;

/**
 * 微信支付回调
 */
public class WebchatPayEvent implements Serializable {

    private static final long serialVersionUID = -7305074547897759724L;

    private BaseResp resp;

    public BaseResp getResp() {
        return resp;
    }

    public void setResp(BaseResp resp) {
        this.resp = resp;
    }
}
