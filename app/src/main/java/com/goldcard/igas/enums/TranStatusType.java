package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/19.
 */
public enum TranStatusType {
    UNPAY("0","未支付"),FINISHED("1","交易完成"),CANCELED("2","交易取消"),PAYEDUNFINISHED("3","支付完成未执行"),UNKNOW("9999","未知");
    private String code;
    private String desc;
    TranStatusType(String code,String desc){
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
    public static TranStatusType fromRaw(String rawValue){
        if ("0".equals(StringUtils.trimNull(rawValue))){
            return UNPAY;
        }else if ("1".equals(StringUtils.trimNull(rawValue))){
            return FINISHED;
        }else if("2".equals(StringUtils.trimNull(rawValue))){
            return CANCELED;
        }else if("3".equals(StringUtils.trimNull(rawValue))){
            return PAYEDUNFINISHED;
        }else{
            return UNKNOW;
        }
    }

}
