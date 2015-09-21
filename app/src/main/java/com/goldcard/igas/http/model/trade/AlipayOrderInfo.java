package com.goldcard.igas.http.model.trade;

import java.io.Serializable;

/**
 * Created by jk on 15/9/19.
 */
public class AlipayOrderInfo implements Serializable{

    private static final long serialVersionUID = 2283610976050813241L;

    private String payBatchNum;

    private String transactionBatchNum;

    private String patternBatchNum;

    private String accountId;

    private String amount;

    private String postPackage;

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

    public String getPatternBatchNum() {
        return patternBatchNum;
    }

    public void setPatternBatchNum(String patternBatchNum) {
        this.patternBatchNum = patternBatchNum;
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

    public String getPostPackage() {
        return postPackage;
    }

    public void setPostPackage(String postPackage) {
        this.postPackage = postPackage;
    }
}
