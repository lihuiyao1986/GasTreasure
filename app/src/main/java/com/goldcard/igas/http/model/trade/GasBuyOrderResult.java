package com.goldcard.igas.http.model.trade;

import java.io.Serializable;

/**
 * Created by jk on 15/9/18.
 */
public class GasBuyOrderResult implements Serializable{

    private static final long serialVersionUID = 6498871340670017162L;

    private String transactionBatchNum;
    private String userId;
    private String transactionDescribe;
    private String transactionObject;
    private String transactionObjectDescribe;
    private String businessCode;
    private String extension;
    private String remark;
    private String createTime;
    private String lastModifyTime;

    public String getTransactionBatchNum() {
        return transactionBatchNum;
    }

    public void setTransactionBatchNum(String transactionBatchNum) {
        this.transactionBatchNum = transactionBatchNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionDescribe() {
        return transactionDescribe;
    }

    public void setTransactionDescribe(String transactionDescribe) {
        this.transactionDescribe = transactionDescribe;
    }

    public String getTransactionObject() {
        return transactionObject;
    }

    public void setTransactionObject(String transactionObject) {
        this.transactionObject = transactionObject;
    }

    public String getTransactionObjectDescribe() {
        return transactionObjectDescribe;
    }

    public void setTransactionObjectDescribe(String transactionObjectDescribe) {
        this.transactionObjectDescribe = transactionObjectDescribe;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
