package com.goldcard.igas.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 所有Service的父类
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class BaseService extends Service{

    protected final  String TAG = this.getClass().getName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
