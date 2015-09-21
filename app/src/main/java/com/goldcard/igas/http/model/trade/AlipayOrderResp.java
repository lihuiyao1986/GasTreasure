package com.goldcard.igas.http.model.trade;

import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * 支付宝下单响应结果
 */
public class AlipayOrderResp extends BaseRespBean {

    private AlipayOrderInfo result;

    public AlipayOrderInfo getResult() {
        return result;
    }

    public void setResult(AlipayOrderInfo result) {
        this.result = result;
    }
}
