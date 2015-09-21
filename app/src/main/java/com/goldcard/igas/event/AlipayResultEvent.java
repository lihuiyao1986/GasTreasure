package com.goldcard.igas.event;

import java.io.Serializable;

/**
 * 支付宝结果回调事件
 */
public class AlipayResultEvent implements Serializable {

    private static final long serialVersionUID = -7232385666698010040L;

    private AlipayPayResult result;

    public AlipayPayResult getResult() {
        return result;
    }

    public void setResult(AlipayPayResult result) {
        this.result = result;
    }
}
