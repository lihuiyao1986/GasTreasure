package com.goldcard.igas.http.model.trade;

import java.io.Serializable;

/**
 * Created by jk on 15/9/21.
 */
public class LLPayOrderInfo implements Serializable {

    private static final long serialVersionUID = 2517198014101230243L;

    private String accountId;

    private String bankCardNum;

    private String transactionBatchNum;

    private String amount;

    private String payCardMode;

    private String payBatchNum;

    private String payType;

    private String postPackage;

    private LLPayOrderPayInfo mapPackage;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getTransactionBatchNum() {
        return transactionBatchNum;
    }

    public void setTransactionBatchNum(String transactionBatchNum) {
        this.transactionBatchNum = transactionBatchNum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayCardMode() {
        return payCardMode;
    }

    public void setPayCardMode(String payCardMode) {
        this.payCardMode = payCardMode;
    }

    public String getPayBatchNum() {
        return payBatchNum;
    }

    public void setPayBatchNum(String payBatchNum) {
        this.payBatchNum = payBatchNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPostPackage() {
        return postPackage;
    }

    public void setPostPackage(String postPackage) {
        this.postPackage = postPackage;
    }

    public LLPayOrderPayInfo getMapPackage() {
        return mapPackage;
    }

    public void setMapPackage(LLPayOrderPayInfo mapPackage) {
        this.mapPackage = mapPackage;
    }
}
