package com.goldcard.igas.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jk on 15/9/14.
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

    protected final String TAG = this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        onBroadcastReceived(context,intent);
    }

    /**
     * 收到广播的回调
     * @param context
     * @param intent
     */
    protected abstract void onBroadcastReceived(Context context, Intent intent);
}
