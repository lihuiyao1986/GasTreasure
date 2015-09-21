package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/19.
 */
public enum  BusinessType {
    IOTCHARGE("1000002","物联网表充值"),ICCHARGE("1000001","IC卡表充值"),UNKNOW("9999999","未知");

    private String code;
    private String desc;
    BusinessType(String code,String desc){
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
    public static BusinessType fromRaw(String rawValue){
        if ("1000002".equals(StringUtils.trimNull(rawValue))){
            return IOTCHARGE;
        }else if ("1000001".equals(StringUtils.trimNull(rawValue))){
            return ICCHARGE;
        }else{
            return UNKNOW;
        }
    }


}
