package com.goldcard.igas.http.model.BaiDu;

import java.io.Serializable;

/**
 * Created by yanshengli on 15-5-30.
 */
public class BaiduLocBean implements Serializable {

    private static final long serialVersionUID = 9049791817453445145L;

    private double lat;

    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
