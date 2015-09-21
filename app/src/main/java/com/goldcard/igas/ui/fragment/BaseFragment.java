package com.goldcard.igas.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.enums.SwipeDirect;
import com.goldcard.igas.http.HttpRequestClient;
import com.goldcard.igas.http.NameValueBean;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.interfaces.HttpRequestClientCallbackAdapter;
import com.goldcard.igas.util.AnimUtils;
import com.goldcard.igas.util.DialogUtils;

import net.tsz.afinal.http.AjaxParams;

import java.util.List;

/**
 * 所有fragment的父类
 * @since 2015-5-20
 * @version 1.0.0
 */
public class BaseFragment extends Fragment{


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume ();
    }

    @Override
    public void onPause(){
        super.onPause ();
    }

    @Override
    public void onDestroy(){
        super.onDestroy ();
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
        this.startActivity (intent);
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
        post(url, params, clazz, reqTag, showProgress, null);
    }

    public final void post(String url,List<NameValueBean> params,final Class<? extends BaseRespBean> clazz,final int reqTag,boolean showProgress,
                          String progressTipInfo){
        if (showProgress) {
            DialogUtils.showProgressDialog(progressTipInfo);
        }
        HttpRequestClient.doPost(AppContext.getInstance(), params, url, clazz, reqTag, new HttpRequestClientCallbackAdapter() {
            @Override
            public void httpRespSuccess(BaseRespBean result, int reqTag, Class<?> clazz) {
                super.httpRespSuccess(result, reqTag, clazz);
                DialogUtils.closeProgressDialogue();
                BaseFragment.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode, String errorMsg, BaseRespBean result, int reqTag) {
                super.httpRespFail(errorcode, errorMsg, result, reqTag);
                DialogUtils.closeProgressDialogue();
                BaseFragment.this.onHttpRespFail(errorcode, errorMsg, result, reqTag);
            }

            @Override
            public boolean isNetworkAvailable() {
                return AppContext.getInstance().isNetworkConnected();
            }
        });
    }

    public final void uploadFile(String url,final Class<? extends BaseRespBean> classz){
        upload(url, null, classz, 0, true);
    }

    public final void uploadFile(String url,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        upload(url, null, classz, 0, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz){
        upload(url, params, classz, 0, true);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,String progressTipInfo){
        upload(url, params, classz, 0, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag){
        upload(url, params, classz, reqTag, true);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,String progressTipInfo){
        upload(url, params, classz, reqTag, true, progressTipInfo);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress){
        upload(url, params, classz, reqTag, showProgress, null);
    }

    public final void upload(String url,AjaxParams params,final Class<? extends BaseRespBean> classz,final int reqTag,boolean showProgress,
                              String progressTipInfo){
        if (showProgress) {
            DialogUtils.showProgressDialog (progressTipInfo);
        }
        HttpRequestClient.uploadFiles (AppContext.getInstance(), params, url, classz, reqTag, new HttpRequestClientCallbackAdapter () {

            @Override
            public void httpRespSuccess(BaseRespBean result,int reqTag,Class<?> clazz){
                super.httpRespSuccess (result, reqTag, clazz);
                BaseFragment.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode,String errorMsg,BaseRespBean result, int reqTag){
                super.httpRespFail (errorcode, errorMsg,result, reqTag);
                BaseFragment.this.onHttpRespFail(errorcode, errorMsg,result, reqTag);
            }

            @Override
            public boolean isNetworkAvailable(){
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
            DialogUtils.showProgressDialog (progressTipInfo);
        }
        HttpRequestClient.doGet (AppContext.getInstance(), params, url, classz, reqTag, new HttpRequestClientCallbackAdapter () {

            @Override
            public void httpRespSuccess(BaseRespBean result,int reqTag,Class<?> clazz){
                super.httpRespSuccess (result, reqTag, clazz);
                DialogUtils.closeAlertDialog();
                BaseFragment.this.onHttpRespSuccess(result, reqTag, clazz);
            }

            @Override
            public void httpRespFail(String errorcode,String errorMsg,BaseRespBean result,int reqTag){
                super.httpRespFail (errorcode, errorMsg,result, reqTag);
                DialogUtils.closeAlertDialog();
                BaseFragment.this.onHttpRespFail(errorcode, errorMsg,result, reqTag);
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
        onHttpFailHandle(errorcode, errorMsg,result, reqTag);
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
            DialogUtils.showToast (AppContext.getInstance(), errorMsg + "[" + errorcode + "]");
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

}
