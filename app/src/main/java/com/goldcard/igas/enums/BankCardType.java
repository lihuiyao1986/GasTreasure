package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/19.
 */
public enum BankCardType {

    CREDIT("3","信用卡"),DEBIT("2","借记卡"),UNKNOW("-1","未知");

    private String code;
    private String desc;
    BankCardType(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取枚举类型
     * @param rawValue
     * @return
     */
    public static BankCardType fromRaw(String rawValue){
        if ("3".equals(StringUtils.trimNull(rawValue))){
            return CREDIT;
        }else if ("2".equals(StringUtils.trimNull(rawValue))){
            return DEBIT;
        }else{
            return UNKNOW;
        }
    }

}
