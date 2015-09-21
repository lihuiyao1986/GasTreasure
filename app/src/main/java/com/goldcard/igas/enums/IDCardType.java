package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * 证件类型
 */
public enum IDCardType {

    PASSPORT("2","护照"),IDCARD("1","身份证"),UNKNOW("-1","未知");
    private String code;
    private String desc;
    IDCardType(String code,String desc){
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
    public static IDCardType fromRaw(String rawValue){
        if ("2".equals(StringUtils.trimNull(rawValue))){
            return PASSPORT;
        }else if ("1".equals(StringUtils.trimNull(rawValue))){
            return IDCARD;
        }else{
            return UNKNOW;
        }
    }

}
