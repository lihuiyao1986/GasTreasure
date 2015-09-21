package com.goldcard.igas.http.model.trade;

import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * 连连支付下单响应结果bean
 */
public class LLPayOrderResp extends BaseRespBean {

    private LLPayOrderInfo reuslt;

    public LLPayOrderInfo getReuslt() {
        return reuslt;
    }

    public void setReuslt(LLPayOrderInfo reuslt) {
        this.reuslt = reuslt;
    }
}
