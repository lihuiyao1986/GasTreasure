package com.goldcard.igas.http.model.BaiDu;

import java.io.Serializable;
import java.util.List;

/**
 * 百度地址联想查询api
 * @since 2015-5-30
 * @author liys
 * @version 1.0.0
 */
public class PlaceAPlRespBean implements Serializable{

    private static final long serialVersionUID = -2974142039843624220L;

    private String status;

    private String message;

    private List<PlaceAPlBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PlaceAPlBean> getResults() {
        return results;
    }

    public void setResults(List<PlaceAPlBean> results) {
        this.results = results;
    }
}
