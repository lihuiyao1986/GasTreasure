package com.goldcard.igas.ui.model;

import java.io.Serializable;

/**
 * 查看图片的fragment实体对象
 * @author liys
 * @since  2015-6-8
 * @version 1.0.0
 */
public class PickCheckModel implements Serializable{

    private static final long serialVersionUID = -2453233562491654980L;

    /** 本地图片 **/
    public static final int pic_type_local = 0;

    /** 网络图片 **/
    public static final int pic_type_net = 1;

    /** 图片类型 **/
    private int picType;

    /** 本地图片集合对象 **/
    private int[] localPics;

    /** 网络图片类型 **/
    private String[] netPics;

    /** 当前的索引 **/
    private int currentIndex;

    public int getPicType() {
        return picType;
    }

    public void setPicType(int picType) {
        this.picType = picType;
    }

    public int[] getLocalPics() {
        return localPics;
    }

    public void setLocalPics(int[] localPics) {
        this.localPics = localPics;
    }

    public String[] getNetPics() {
        return netPics;
    }

    public void setNetPics(String[] netPics) {
        this.netPics = netPics;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
