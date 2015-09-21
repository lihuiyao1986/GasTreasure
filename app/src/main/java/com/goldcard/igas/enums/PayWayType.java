package com.goldcard.igas.enums;

import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/19.
 */
public enum PayWayType{

    ALIPAY("10001","支付宝"),WEBCHAT("10002","微信支付"),LLPAY("10003","连连支付"),UNKNOW("-1","未知");

    private String paycode;

    private String paydesc;

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getPaydesc() {
        return paydesc;
    }

    public void setPaydesc(String paydesc) {
        this.paydesc = paydesc;
    }

    PayWayType(String paycode,String paydesc){
        this.paycode = paycode;
        this.paydesc = paydesc;
    }

    /**
     * 获取枚举类型
     * @param rawValue
     * @return
     */
    public static PayWayType fromRaw(String rawValue){
        if ("10001".equals(StringUtils.trimNull(rawValue))){
            return ALIPAY;
        }else if ("10002".equals(StringUtils.trimNull(rawValue))){
            return WEBCHAT;
        }else if ("10003".equals(StringUtils.trimNull(rawValue))){
            return LLPAY;
        }else{
            return UNKNOW;
        }
    }
}
