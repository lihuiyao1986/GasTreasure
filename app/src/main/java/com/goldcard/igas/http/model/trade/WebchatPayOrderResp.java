package com.goldcard.igas.http.model.trade;

import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * Created by jk on 15/9/18.
 */
public class WebchatPayOrderResp extends BaseRespBean {

    private WebchatPayOrderResult result;

    public WebchatPayOrderResult getResult() {
        return result;
    }

    public void setResult(WebchatPayOrderResult result) {
        this.result = result;
    }
}
