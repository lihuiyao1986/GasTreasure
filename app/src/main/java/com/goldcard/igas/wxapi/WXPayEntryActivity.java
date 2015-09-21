package com.goldcard.igas.wxapi;


import android.content.Intent;

import com.goldcard.igas.core.AppConstant;
import com.goldcard.igas.event.WebchatPayEvent;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.goldcard.igas.util.MyLog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

/**
 * 微信的回调页面
 */
public class WXPayEntryActivity extends BaseFragmentActivity implements IWXAPIEventHandler{

	private IWXAPI api;

	@Override
	protected void onUIViewInit() {

	}

	@Override
	protected void onListenerRegister() {

	}

	@Override
	protected void onDataInit() {
		api = WXAPIFactory.createWXAPI(this, AppConstant.WEIXIN_APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		MyLog.d(logTag(), "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			WebchatPayEvent event = new WebchatPayEvent();
			event.setResp(resp);
			EventBus.getDefault().post(event);
			finish();
		}
	}
}