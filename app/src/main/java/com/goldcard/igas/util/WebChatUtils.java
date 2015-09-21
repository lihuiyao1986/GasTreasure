package com.goldcard.igas.util;

import android.content.Context;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppConstant;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信工具类
 */
public class WebChatUtils {

    /**
     * 获取微信支付对应的API
     * @param ctx
     * @return
     */
    public static IWXAPI webchatAPI(Context ctx){
        IWXAPI msgApi = WXAPIFactory.createWXAPI(ctx,null);
        msgApi.registerApp(AppConstant.WEIXIN_APP_ID);
        return msgApi;
    }

    /**
     * 发送支付请求
     * @param ctx
     * @param reqParams
     */
    public static void sendPay(Context ctx,PayReq reqParams,Boolean showIfNotInstall){
        IWXAPI api = webchatAPI(ctx);
        if (api.isWXAppInstalled()){
            api.sendReq(reqParams);
        }else{
            if (showIfNotInstall){
                DialogUtils.showToastTwo(ctx,ctx.getResources().getString(R.string.common_nowebchat_install),false);
            }
        }
    }

}
