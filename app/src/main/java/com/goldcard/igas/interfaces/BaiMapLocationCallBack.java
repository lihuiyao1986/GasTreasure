package com.goldcard.igas.interfaces;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;

/**
 * 百度定位回调
 * @since 2015-5-20
 * @author liys
 * @version 1.0.0
 */
public interface BaiMapLocationCallBack {
    /**
     * 收到百度地图定位的回调
     * @param location
     * @param client
     */
    public void onLocationReceived(BDLocation location, LocationClient client);
}
