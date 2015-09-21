package com.goldcard.igas.ui.activity.common;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.goldcard.igas.R;
import com.goldcard.igas.enums.PayWayType;
import com.goldcard.igas.event.AlipayPayResult;
import com.goldcard.igas.event.AlipayResultEvent;
import com.goldcard.igas.event.WebchatPayEvent;
import com.goldcard.igas.http.NameValueBean;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.http.model.trade.AlipayOrderResp;
import com.goldcard.igas.http.model.trade.GasBuyOrderRespBean;
import com.goldcard.igas.http.model.trade.LLPayOrderResp;
import com.goldcard.igas.http.model.trade.WebchatPayOrderResp;
import com.goldcard.igas.llpay.PayOrder;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.goldcard.igas.ui.fragment.NavBarOneFragment;
import com.goldcard.igas.ui.model.NavBarOneModel;
import com.goldcard.igas.util.AnimUtils;
import com.goldcard.igas.util.CommonUtils;
import com.goldcard.igas.util.DialogUtils;
import com.goldcard.igas.util.PictureUtils;
import com.goldcard.igas.util.QRCodeUtils;
import com.goldcard.igas.util.WebChatUtils;
import com.tencent.mm.sdk.modelpay.PayReq;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 我的二维码页面
 * @author liys
 * @since 2015-6-18
 * @version 1.0.0
 */
public class MyQRcodeActivity extends BaseFragmentActivity implements NavBarOneFragment.NavBarOneFragmentCallBack {

    private ImageView qrcodeImage;

    private final static int gasBuyTag = 100;

    private final static int webchatPayTag = 200;

    private final static int alipayTag = 300;

    private final static int llpayTag = 400;

    @Override
    protected void onUIViewInit() {

        // 1.布局文件
        setContentView(R.layout.my_qrcode_page_layout);

        // 2.设置导航
        NavBarOneModel navBarInfo = new NavBarOneModel();
        navBarInfo.setTitle(getResources().getString(R.string.my_qrcode_title));
        navBarInfo.setRightBtnTxt("上传");
        NavBarOneFragment navBarOneFragment = NavBarOneFragment.newInstance(navBarInfo);
        navBarOneFragment.setCallBack(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_bar,navBarOneFragment).commit();

        // 3.图片
        qrcodeImage = (ImageView)findViewById(R.id.my_qrcode_image);

    }

    @Override
    protected void onListenerRegister() {
        EventBus.getDefault().register(this);
    }

    /**
     *
     * @param event
     */
    public void onEventMainThread(WebchatPayEvent event) {
        Log.d("harvic", "onEventMainThread收到了消息：" + event.getResp().errStr);
    }

    /***
     *
     * @param event
     */
    public void onEventMainThread(AlipayResultEvent event){
         DialogUtils.showToastTwo(this, event.getResult().getResult(), false);
    }


    @Override
    protected void onDataInit() {
        List<NameValueBean> params = new ArrayList<NameValueBean>();
        params.add(new NameValueBean("author","李炎sheng"));
        params.add(new NameValueBean("url","http://www.baidu.com"));
        params.add(new NameValueBean("clientId","1233"));
        boolean result =  QRCodeUtils.createQRImage(params, CommonUtils.dip2px(this, 200),CommonUtils.dip2px(this,200),null, CommonUtils.userQRcodeImgPath(this),CommonUtils.userQRcodeImgFilename("123"));
        if(result){
            File file = new File(CommonUtils.userQRcodeImgPath(this),CommonUtils.userQRcodeImgFilename("123"));
            qrcodeImage.setImageBitmap(PictureUtils.getBitmapFromFile(file, 200, 200));
        }else {
            DialogUtils.showToastTwo(this, "生成二维码失败", false);
        }
    }

    @Override
    public void onLeftBtnClicked(NavBarOneFragment fragment, Button leftBtn) {
        finish();
        AnimUtils.outAnim();
    }

    @Override
    public void onRightBtnClicked(NavBarOneFragment fragment, Button rightBtn) {
          List<NameValueBean> params = new ArrayList<NameValueBean>();
          params.add(new NameValueBean("companyCode","10001001"));
          params.add(new NameValueBean("amount","0.01"));
          params.add(new NameValueBean("userId", "7f420385-3e39-4146-af05-20d36fce9632"));
          params.add(new NameValueBean("meterType", "3"));
          params.add(new NameValueBean("archivesCode", "057101000621"));
          String url = getResources().getString(R.string.GasBuyOrderUrl);
          post(url, params, GasBuyOrderRespBean.class, gasBuyTag, "正在下单...");
    }

    @Override
    protected void onHttpFailHandle(String errorcode, String errorMsg, BaseRespBean result, int reqTag) {
        super.onHttpFailHandle(errorcode, errorMsg, result, reqTag);
    }

    @Override
    protected void onHttpSuccHandle(BaseRespBean data, int reqTag) {
        super.onHttpSuccHandle(data, reqTag);
        switch (reqTag){
            case gasBuyTag:
                handleGasBuySucc(data);
                break;
            case webchatPayTag:
                handleWebchatOrderSucc(data);
                break;
            case alipayTag:
                handleAlipayOrderSucc(data);
                break;
            case llpayTag:
                handleLLPayOrderSucc(data);
            default:
                break;
        }
    }

    private void handleGasBuySucc(BaseRespBean result){
        GasBuyOrderRespBean respBean = (GasBuyOrderRespBean)result;
        //placeWebchatPay(respBean);
        placeAlipayPay(respBean);
    }

    /**
     * 支付宝下单
     * @param result
     */
    private void placeAlipayPay(GasBuyOrderRespBean result){
        List<NameValueBean> params = new ArrayList<NameValueBean>();
        params.add(new NameValueBean("transactionBatchNum",result.getResult().getTransactionBatchNum()));
        params.add(new NameValueBean("payType", PayWayType.ALIPAY.getPaycode()));
        String url = getResources().getString(R.string.AlipayOrderUrl);
        post(url, params, AlipayOrderResp.class, alipayTag, "正在提交申请...");
    }

    /**
     * 连连支付下单
     * @param result
     */
    private void placeLLPay(GasBuyOrderRespBean result){
        List<NameValueBean> params = new ArrayList<NameValueBean>();
        params.add(new NameValueBean("transactionBatchNum",result.getResult().getTransactionBatchNum()));
        params.add(new NameValueBean("accountId", "22222222222"));
        params.add(new NameValueBean("busiPartner", "101001"));
        params.add(new NameValueBean("payChannel", PayWayType.LLPAY.getPaycode()));
        params.add(new NameValueBean("userId", ""));
        params.add(new NameValueBean("card_mobile", "13606603642"));
        params.add(new NameValueBean("id_no", "360428198611172714"));
        params.add(new NameValueBean("bankId", ""));
        params.add(new NameValueBean("bankCardNo", ""));
        params.add(new NameValueBean("bankAccountName", "李焱生"));
        params.add(new NameValueBean("frmsWareCategory", "1002"));
        String url = getResources().getString(R.string.LLPayOrderUrl);
        post(url, params, LLPayOrderResp.class, llpayTag, "正在提交申请...");
    }

    /**
     * 连连支付下单成功
     * @param data
     */
    private void handleLLPayOrderSucc(BaseRespBean data){

    }

    /**
     * 支付宝下单成功
     * @param result
     */
    private void handleAlipayOrderSucc(BaseRespBean result){
        AlipayOrderResp respBean = (AlipayOrderResp)result;
        doAlipay(respBean.getResult().getPostPackage());
    }

    private void handleWebchatOrderSucc(BaseRespBean result){
        WebchatPayOrderResp respBean = (WebchatPayOrderResp)result;
        PayReq request = new PayReq();
        request.appId = respBean.getResult().getMapPackage().getAppid();
        request.partnerId = respBean.getResult().getMapPackage().getPartnerid();
        request.prepayId= respBean.getResult().getMapPackage().getPrepayid();
        request.packageValue = respBean.getResult().getMapPackage().getWebchatPackage();
        request.nonceStr= respBean.getResult().getMapPackage().getNoncestr();
        request.timeStamp= respBean.getResult().getMapPackage().getTimestamp();
        request.sign= respBean.getResult().getMapPackage().getSign();
        WebChatUtils.sendPay(this, request, true);
    }

    /**
     * 微信支付下单
     * @param respBean
     */
    private void placeWebchatPay(GasBuyOrderRespBean respBean){
        List<NameValueBean> params = new ArrayList<NameValueBean>();
        params.add(new NameValueBean("transactionBatchNum",respBean.getResult().getTransactionBatchNum()));
        params.add(new NameValueBean("payType",PayWayType.WEBCHAT.getPaycode()));
        params.add(new NameValueBean("terminalIp", "127.0.0.1"));
        params.add(new NameValueBean("tranType", "APP"));
        String url = getResources().getString(R.string.WebchatPayOrderUrl);
        post(url, params, WebchatPayOrderResp.class, webchatPayTag, "正在提交申请...");
    }

    @Override
    public void onTitleClicked(NavBarOneFragment fragment, TextView title) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 支付宝支付
     * @param payPackage
     */
    public void doAlipay(final String payPackage){
        Thread payThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MyQRcodeActivity.this);
                String payResultStr = alipay.pay(payPackage);
                AlipayPayResult alipayPayResult = new AlipayPayResult(payResultStr);
                AlipayResultEvent event = new AlipayResultEvent();
                event.setResult(alipayPayResult);
                EventBus.getDefault().post(event);
            }
        });
        payThread.start();
    }

    /**
     * 连连支付
     * @param order
     */
    public void doLLPay(PayOrder order){

    }
}
