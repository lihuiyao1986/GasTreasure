package com.goldcard.igas.http.model.trade;

import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * Created by jk on 15/9/18.
 */
public class GasBuyOrderRespBean extends BaseRespBean {

    private GasBuyOrderResult result;

    public GasBuyOrderResult getResult() {
        return result;
    }

    public void setResult(GasBuyOrderResult result) {
        this.result = result;
    }
}
