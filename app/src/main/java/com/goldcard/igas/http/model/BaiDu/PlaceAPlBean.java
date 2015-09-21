package com.goldcard.igas.http.model.BaiDu;

import java.io.Serializable;

/**
 * Created by yanshengli on 15-5-30.
 */
public class PlaceAPlBean implements Serializable {

    private static final long serialVersionUID = -5899550033834432723L;

    private String name;

    private String address;

    private String street_id;

    private String telephone;

    private String detail;

    private String uid;

    private BaiduLocBean location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BaiduLocBean getLocation() {
        return location;
    }

    public void setLocation(BaiduLocBean location) {
        this.location = location;
    }
}
