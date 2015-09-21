package com.goldcard.igas.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * 所有IntentService的父类
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class BaseIntentService extends IntentService {

    protected final  String TAG = this.getClass().getName();

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    /**
     * 构造函数
     * @param name
     */
    public BaseIntentService(String name) {
        super(name);
    }
}
