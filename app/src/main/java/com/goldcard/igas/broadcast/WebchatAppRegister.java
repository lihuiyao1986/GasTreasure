package com.goldcard.igas.broadcast;

import android.content.Context;
import android.content.Intent;

import com.goldcard.igas.util.WebChatUtils;

/**
 * 微信回调广播信息
 */
public class WebchatAppRegister extends BaseBroadcastReceiver {

    @Override
    protected void onBroadcastReceived(Context context, Intent intent) {
        WebChatUtils.webchatAPI(context);
    }
}
