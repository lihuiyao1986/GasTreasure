package com.goldcard.igas.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.core.AppManager;
import com.goldcard.igas.enums.SwipeDirect;
import com.goldcard.igas.http.HttpRequestClient;
import com.goldcard.igas.http.NameValueBean;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.interfaces.HttpRequestClientCallbackAdapter;
import com.goldcard.igas.ui.activity.common.CheckPicActivity;
import com.goldcard.igas.ui.model.PickCheckModel;
import com.goldcard.igas.util.AnimUtils;
import com.goldcard.igas.util.AppUtils;
import com.goldcard.igas.util.DialogUtils;
import com.goldcard.igas.util.GestureUtils;
import com.goldcard.igas.widget.AlertOneModel;
import com.goldcard.igas.widget.JKAlertDialogue;
import com.umeng.analytics.MobclickAgent;

import net.tsz.afinal.http.AjaxParams;

import java.util.List;

/**
 * 所有activity的父类
 * @author liys
 * @since  2015-5-20
 * @version 1.0.0
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    /** 手势 **/
    protected GestureDetector gestureDetector;

    /** 屏幕对象 **/
    protected GestureUtils.Screen screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1.调用父类的方法
        super.onCreate (savedInstanceState);
        // 2.设置activity
        AppUtils.setActivity(this);
        // 3.添加activity到堆栈中
        AppManager.getAppManager().addActivity (this);
        // 4.禁止横屏
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 5.禁止锁屏
        //getWindow ().setFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 6.添加友盟统计
        MobclickAgent.setDebugMode (true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig (this);
        // 7.初始化视图
        onUIViewInit();
        // 9.注册事件
        onListenerRegister();
        // 10.初始化数据
        onDataInit();
        // 11.获取屏幕对象
        screen = GestureUtils.getScreenPix (this);
        // 12.手势
        gestureDetector = new GestureDetector (this,onGestureListener);

    }

    /** 手势监听器对象 **/
    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener () {
        @Override
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
            float x = e2.getX () - e1.getX ();
            float y = e2.getY () - e1.getY ();
            // 限制必须得划过屏幕的1/3才能算划过
            float x_limit = screen.widthPixels / 3;
            float y_limit = screen.heightPixels / 3;
            float x_abs = Math.abs (x);
            float y_abs = Math.abs (y);
            if (x_abs >= y_abs) {
                if (x > x_limit || x < -x_limit) {
                    if (x > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (y > y_limit || y < -y_limit) {
                    if (y > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                }
            }
            return true;
        }
    };


    /**
     *手势向右划--子类覆盖
     */
    protected void onSwipeRight(){}

    /**
     * 手势向左划--子类覆盖
     */
    protected void onSwipeLeft(){}

    /**
     * 手势向上划--子类覆盖
     */
    protected void onSwipeDown(){}

    /**
     * 手势向上划--子类覆盖
     */
    protected void onSwipeUp(){}


    /***
     * 初始化UIView
     */
    protected abstract void onUIViewInit();

    /**
     * 注册事件监听器
     */
    protected abstract void onListenerRegister();

    /**
     * 初始化页面数据
     */
    protected abstract void onDataInit();

    /**
     * 获取日志的tag
     * @return
     */
    public String logTag(){
        return this.getClass ().getName ();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        AppUtils.setActivity(this);
    }

    @Override
    public void onResume(){
        super.onResume ();
        AppUtils.setActivity(this);
        MobclickAgent.onPageStart(logTag());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        MobclickAgent.onPageEnd(logTag());
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed(){
        DialogUtils.closeAlertDialog();
        super.onBackPressed();
        AnimUtils.outAnim();
    }

    @Override
    public void finish(){
        DialogUtils.closeAlertDialog();
        AnimUtils.outAnim();
        super.finish();
    }

    /**
     *
     * 跳转到对应的页面
     * @param clazz
     * @param bundle
     * @param direct
     */
    public void skipToTargetPage(Class<? extends Activity> clazz,Bundle bundle,SwipeDirect direct)
    {
        Intent intent = new Intent();
        intent.setClass (AppContext.getInstance(), clazz);
        if (bundle == null) {
            bundle = new Bundle ();
        }
        intent.putExtras (bundle);
        this.startActivity(intent);
        switch (direct) {
            case DIRECT_UP:
                AnimUtils.bottomInTranslateAnim();
                break;
            case DIRECT_DOWN:
                AnimUtils.upInTranslateAnim ();
                break;
            case DIRECT_LEFT:
                AnimUtils.inAnim ();
                break;
            case DIRECT_RIGHT:
                AnimUtils.outAnim ();
                break;
            default:
                AnimUtils.inAnim ();
                break;
        }
    }


    public final void post(String url,final Class<? extends BaseRespBean> clazz){
        post(url, null, clazz, 0, true);
    }

    public final void post(String url,final Class<? extends BaseRespBean> clazz,String progressTipInfo){
        post(url, null, clazz, 0, true, progressTipInfo);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz){
        post(url, params, clazz, 0, true);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,String progressTipInfo){
        post(url, params, clazz, 0, true, progressTipInfo);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,final int reqTag){
        post(url, params, clazz, reqTag, true);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,final int reqTag,String progressTipInfo){
        post(url, params, clazz, reqTag, true, progressTipInfo);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,final int reqTag,boolean showProgress){
        post(url, params, clazz, reqTag, showProgress, "正在加载");
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,final int reqTag,boolean showProgress,
                           String progressTipInfo){
        if (showProgress) {
            DialogUtils.loading(this, progressTipInfo);
        }
        HttpRequestClient.doPost(AppContext.getInstance(), params, url, clazz, reqTag, new HttpRequestClientCallbackAdapter() {
            @Override
            public void httpRespSuccess(BaseRespBean result, int reqTag, Class<?> clazz) {
                super.httpRespSuccess(result, reqTag, clazz);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode, String errorMsg, BaseRespBean result, int reqTag) {
                super.httpRespFail(errorcode, errorMsg,result, reqTag);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespFail(errorcode, errorMsg,result,reqTag);
            }

            @Override
            public boolean isNetworkAvailable() {
                return AppContext.getInstance().isNetworkConnected();
            }
        });
    }

    public final void uploadFile(String url,final Class<? extends BaseRespBean> classz){
        upload (url, null, classz, 0, true);
    }

    public final void uploadFile(String url,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        upload (url, null, classz, 0, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz){
        upload (url, params, classz, 0, true);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        upload (url, params, classz, 0, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag){
        upload(url, params, classz, reqTag, true);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,String progressTipInfo){
        upload(url, params, classz, reqTag, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress){
        upload(url, params, classz, reqTag, showProgress, "正在加载");
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress,
                             String progressTipInfo){
        if (showProgress) {
            DialogUtils.loading(this, progressTipInfo);
        }
        HttpRequestClient.uploadFiles(AppContext.getInstance(), params, url, classz, reqTag, new HttpRequestClientCallbackAdapter() {

            @Override
            public void httpRespSuccess(BaseRespBean result, int reqTag, Class<?> clazz) {
                super.httpRespSuccess(result, reqTag, clazz);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode, String errorMsg,BaseRespBean result, int reqTag) {
                super.httpRespFail(errorcode, errorMsg,result,reqTag);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespFail(errorcode, errorMsg,result,reqTag);
            }

            @Override
            public boolean isNetworkAvailable() {
                return AppContext.getInstance().isNetworkConnected();
            }
        });
    }

    public final void get(String url,final Class<? extends BaseRespBean> classz){
        get(url, null, classz, 0, true);
    }

    public final void get (String url,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        get(url, null, classz, 0, true, progressTipInfo);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz){
        get(url, params, classz, 0, true);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        get(url, params, classz, 0, true, progressTipInfo);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz,final int reqTag){
        get(url, params, classz, reqTag, true);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz,final int reqTag,String progressTipInfo){
        get(url, params, classz, reqTag, true, progressTipInfo);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress){
        get(url, params, classz, reqTag, showProgress, null);
    }

    public final void get(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress,
                          String progressTipInfo){
        if (showProgress) {
            DialogUtils.loading(this, progressTipInfo);
        }
        HttpRequestClient.doGet (AppContext.getInstance(), params, url, classz, reqTag, new HttpRequestClientCallbackAdapter () {

            @Override
            public void httpRespSuccess(BaseRespBean result,int reqTag,Class<?> clazz){
                super.httpRespSuccess (result, reqTag, clazz);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode,String errorMsg,BaseRespBean result,int reqTag){
                super.httpRespFail(errorcode, errorMsg, result, reqTag);
                DialogUtils.closeLoading();
                BaseFragmentActivity.this.onHttpRespFail(errorcode, errorMsg, result, reqTag);
            }

            @Override
            public boolean isNetworkAvailable(){
                return AppContext.getInstance().isNetworkConnected ();
            }
        });
    }

    /**
     * 请求成功回调
     * @param result
     * @param reqTag
     * @param clazz
     */
    public void onHttpRespSuccess(BaseRespBean result,int reqTag,Class<?> clazz){
        onHttpSuccHandle(result, reqTag);
    }

    /**
     * 请求失败
     * @param errorcode
     * @param errorMsg
     * @param reqTag
     */
    public void onHttpRespFail(String errorcode,String errorMsg,BaseRespBean result,int reqTag){
        onHttpFailHandle(errorcode, errorMsg,result,reqTag);
    }

    // 等待子类覆盖
    protected void onHttpSuccHandle(BaseRespBean data,int reqTag){}

    /**
     * 是否请求正常，但返回码不正常的情况
     * @param errorcode
     * @param errorMsg
     * @param reqTag
     */
    protected void onHttpFailHandle(String errorcode,String errorMsg,BaseRespBean result,int reqTag){
        // 转义错误描述
        errorMsg = onErrorMsgConvert(errorcode, errorMsg, reqTag);
        // 显示错误提示
        if (onShowDialogHttpError(reqTag)) {
            DialogUtils.showToastTwo (AppContext.getInstance(), errorMsg + "[" + errorcode + "]",false);
        }
    }

    /**
     *@Description: 转义错误描述
     *@Author: 李焱生
     *@Since: 2015年4月3日上午10:09:55
     *@param errorcode
     *@param errorMsg
     *@param reqTag
     *@return
     */
    protected String onErrorMsgConvert(String errorcode,String errorMsg,int reqTag){
        return errorMsg;
    }

    /**
     * 请求错误之后，是否显示alertView
     * @param reqTag
     * @return
     */
    protected boolean onShowDialogHttpError(int reqTag){
        return true;
    }


    /**
     * 跳转到查看照片的页面
     */
    protected void skipToCheckPicPage(PickCheckModel model){
        Bundle bundle = new Bundle();
        bundle.putSerializable("pics",model);
        skipToTargetPage(CheckPicActivity.class, bundle, SwipeDirect.DIRECT_LEFT);
    }

    /**
     * 显示对话框
     * @param msg
     * @param callback
     */
    protected void showAlertDialogue(String msg, JKAlertDialogue.JKAlertDialogueCallBack callback){
        showAlertDialogue("取消", "确认", msg, callback);
    }

    /**
     * 显示对话框
     * @param cancelTitle
     * @param sureTitle
     * @param msg
     * @param callback
     */
    protected void showAlertDialogue(String cancelTitle,String sureTitle,String msg,JKAlertDialogue.JKAlertDialogueCallBack callback){
        showAlertDialogue(cancelTitle, sureTitle, msg, true, callback);
    }

    /**
     * 显示对话框
     * @param cancelTitle
     * @param sureTitle
     * @param msg
     * @param callback
     */
    protected void showAlertDialogue(String cancelTitle,String sureTitle,String msg,boolean cancelable,JKAlertDialogue.JKAlertDialogueCallBack callback){
        AlertOneModel model = new AlertOneModel();
        model.setSureBtnTitle(sureTitle);
        model.setCancelBtnTitle(cancelTitle);
        model.setContent(msg);
        model.setCallBack(callback);
        model.setCancalTouchOutside(cancelable);
        JKAlertDialogue.newInstance(model).show(getSupportFragmentManager(), "alertDialog");
    }
}
