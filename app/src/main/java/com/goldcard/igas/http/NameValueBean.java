package com.goldcard.igas.http;

import java.io.Serializable;

/**
 * Created by jk on 15/9/14.
 */
public class NameValueBean implements Serializable {

    private String name;

    private String value;

    public  NameValueBean(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
