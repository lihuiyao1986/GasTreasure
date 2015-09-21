package com.goldcard.igas.http.model.trade;

import java.io.Serializable;

/**
 * Created by jk on 15/9/18.
 */
public class WebchatPayOrderResult implements Serializable {

    private static final long serialVersionUID = -4221886558937350275L;

    private String payBatchNum;

    private String transactionBatchNum;

    private String accountId;

    private String amount;

    private WebchatPayOrderResultInfo mapPackage;

    public String getPayBatchNum() {
        return payBatchNum;
    }

    public void setPayBatchNum(String payBatchNum) {
        this.payBatchNum = payBatchNum;
    }

    public String getTransactionBatchNum() {
        return transactionBatchNum;
    }

    public void setTransactionBatchNum(String transactionBatchNum) {
        this.transactionBatchNum = transactionBatchNum;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public WebchatPayOrderResultInfo getMapPackage() {
        return mapPackage;
    }

    public void setMapPackage(WebchatPayOrderResultInfo mapPackage) {
        this.mapPackage = mapPackage;
    }
}
