package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * 性别类型对应的枚举
 */
public enum SexType{
    MAN("1","男"),WOMAN("2","女"),UNKNOW("-1","未知");
    private String code;
    private String desc;
    SexType(String code,String desc){
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
    public static SexType fromRaw(String rawValue){
        if ("1".equals(StringUtils.trimNull(rawValue))){
            return MAN;
        }else if ("2".equals(StringUtils.trimNull(rawValue))){
            return WOMAN;
        }else{
            return UNKNOW;
        }
    }
}
