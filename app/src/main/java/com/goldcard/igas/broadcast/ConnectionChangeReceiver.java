package com.goldcard.igas.broadcast;

import android.content.Context;
import android.content.Intent;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.util.DialogUtils;

/**
 * 网络连接监听广播
 */
public class ConnectionChangeReceiver extends BaseBroadcastReceiver {

    @Override
    protected void onBroadcastReceived(Context context, Intent intent) {
        AppContext appContext = (AppContext) context.getApplicationContext ();
        if (appContext.isNetworkConnected ()) {
            DialogUtils.showToastTwo(appContext, "网络已连接", false);
        } else {
            DialogUtils.showToastTwo(appContext,"网络已断开",false);
        }

    }
}
