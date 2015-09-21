package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/19.
 */
public enum GasType {

    METERIOT("3","物联网表"),ICCARD("2","IC卡表"),MECHINECARD("1","机械表"),UNKNOW("-1","未知");

    private String code;
    private String desc;
    GasType(String code,String desc){
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
    public static GasType fromRaw(String rawValue){
        if ("3".equals(StringUtils.trimNull(rawValue))){
            return METERIOT;
        }else if ("2".equals(StringUtils.trimNull(rawValue))){
            return ICCARD;
        }else if ("1".equals(StringUtils.trimNull(rawValue))){
            return MECHINECARD;
        }else{
            return UNKNOW;
        }
    }

}
