package com.goldcard.igas.http.model.trade;

import java.io.Serializable;

/**
 * Created by jk on 15/9/21.
 */
public class LLPayOrderPayInfo implements Serializable {

    private static final long serialVersionUID = -9086556726412819930L;

    private String oid_partner;

    private String no_order;

    private String busi_partner;

    private String sign_type;

    private String notify_url;

    private String name_goods;

    private String acct_name;

    private String money_order;

    private String card_no;

    private String risk_item;

    private String sign;

    private String valid_order;

    private String user_id;

    private String dt_order;

    private String flag_modify;

    public String getOid_partner() {
        return oid_partner;
    }

    public void setOid_partner(String oid_partner) {
        this.oid_partner = oid_partner;
    }

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
    }

    public String getBusi_partner() {
        return busi_partner;
    }

    public void setBusi_partner(String busi_partner) {
        this.busi_partner = busi_partner;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getName_goods() {
        return name_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public String getAcct_name() {
        return acct_name;
    }

    public void setAcct_name(String acct_name) {
        this.acct_name = acct_name;
    }

    public String getMoney_order() {
        return money_order;
    }

    public void setMoney_order(String money_order) {
        this.money_order = money_order;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getRisk_item() {
        return risk_item;
    }

    public void setRisk_item(String risk_item) {
        this.risk_item = risk_item;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getValid_order() {
        return valid_order;
    }

    public void setValid_order(String valid_order) {
        this.valid_order = valid_order;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDt_order() {
        return dt_order;
    }

    public void setDt_order(String dt_order) {
        this.dt_order = dt_order;
    }

    public String getFlag_modify() {
        return flag_modify;
    }

    public void setFlag_modify(String flag_modify) {
        this.flag_modify = flag_modify;
    }
}
