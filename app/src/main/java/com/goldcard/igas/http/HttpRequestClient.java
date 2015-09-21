package com.goldcard.igas.http;

import android.content.Context;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.interfaces.DownloadCallBack;
import com.goldcard.igas.interfaces.HttpRequestClientCallback;
import com.goldcard.igas.util.DateUtils;
import com.goldcard.igas.util.JsonUtils;
import com.goldcard.igas.util.MD5;
import com.goldcard.igas.util.MyLog;
import com.goldcard.igas.util.StringUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理http请求的方法
 * @author yanshengli
 * @since 2015-1-29
 */
public class HttpRequestClient {

    /** 日志TAG **/
    private static final String TAG                            = "HttpRequestClient";

    /** 业务请求成功的代码 **/
    private static final String http_req_business_success_code = "100000";

    /** http post请求  **/
    public static final String  HTTP_POST_REQ                  = "POST";

    /** http get请求  **/
    public static final String  HTTP_GET_REQ                   = "GET";

    /** 网络连接不可用的错误码 **/
    private static final String http_req_no_network_avail_code = "100";

    /** 网络连接不可用的错误提示 **/
    private static final String http_req_no_netwokr_avail_msg  = "网络连接已断开";

    /**
     * 发送异步的http请求
     * @param params
     * @param url
     */
    public static void doHttpRequest(Context ctx,List<NameValueBean> params,String url,final Class<? extends BaseRespBean> clazz,final int reqTag,
            final HttpRequestClientCallback callback,String httpReqMethod){

        // 1.检查网络连接
        if (callback != null && !callback.isNetworkAvailable ()) {
            callback.httpRespFail (http_req_no_network_avail_code, http_req_no_netwokr_avail_msg,null, reqTag);
            return;
        }

        // 2.参数
        final AjaxParams reqParams = packPostParams (ctx, params, url);

        // 3.发送http请求
        FinalHttp fh = new FinalHttp();
        List<NameValueBean> headers = reqHeaders();
        for (NameValueBean head : headers){
            fh.addHeader(StringUtils.trimNull(head.getName()),StringUtils.trimNull(head.getValue()));
        }
        
        // 4.设置超时时间
        String timeout = StringUtils.trimNull(ctx.getResources().getString(R.string.http_time_out), "30");
        fh.configTimeout(Integer.parseInt(timeout) * 1000);

        // 5.设置请求回调
        AjaxCallBack<String> ajaxCallback = new AjaxCallBack<String> () {

            @Override
            public void onLoading(long count,long current){
                super.onLoading (count, current);
                if (callback != null) {
                    callback.httpOnloading (count, current, reqTag);
                }
            }

            @Override
            public void onSuccess(String result){
                super.onSuccess (result);
                MyLog.d(TAG, "http请求的参数：" + reqParams.toString() + "," + "http请求的响应结果：" + result);
                if (callback != null) {
                    Class<? extends BaseRespBean> resultClazz = clazz == null ? BaseRespBean.class : clazz;
                    BaseRespBean resultBean = JsonUtils.parseObject (result, resultClazz);
                    String errorcode = StringUtils.trimNull (resultBean.getResponseCode());
                    String errorMsg = StringUtils.trimNull (resultBean.getMessage(), "系统繁忙，请稍后重试");
                    if (http_req_business_success_code.equalsIgnoreCase (errorcode)) {
                        callback.httpRespSuccess (resultBean, reqTag, resultClazz);
                    } else {
                        callback.httpRespFail (errorcode, errorMsg,resultBean,reqTag);
                    }
                }
            }

            @Override
            public AjaxCallBack<String> progress(boolean progress,int rate){
                if (callback != null) {
                    callback.httpOnProgress (progress, rate, reqTag);
                }
                return super.progress (progress, rate);
            }

            @Override
            public void onStart(){
                super.onStart ();
                if (callback != null) {
                    callback.httpReqBegin (reqTag);
                }
            }

            @Override
            public void onFailure(Throwable t,int errorNo,String strMsg){
                super.onFailure (t, errorNo, strMsg);
                if (callback != null) {
                    callback.httpRespFail (String.valueOf (errorNo), StringUtils.trimNull (strMsg, "系统繁忙，请稍后重试"),null, reqTag);
                }
            }
        };

        // 6.发送请求
        httpReqMethod = StringUtils.trimNull (httpReqMethod, HTTP_POST_REQ);
        if (httpReqMethod.equals (HTTP_POST_REQ)) {
            fh.post (reqUrl (url, ctx), reqParams, ajaxCallback);
        } else {
            fh.get (reqUrl (url, ctx), reqParams, ajaxCallback);
        }
    }

    /**
     * http request请求
     * @param params
     * @param url
     * @param clazz
     * @param reqTag
     * @param httpReqMethod
     */
    public static void doHttpRequest(Context ctx,List<NameValueBean> params,String url,final Class<? extends BaseRespBean> clazz,final int reqTag,
            AjaxCallBack<String> ajaxCallback,String httpReqMethod){

        // 1.参数
        AjaxParams reqParams = packPostParams(ctx, params, url);
        MyLog.d(TAG, "http请求的参数：" + reqParams.toString());

        // 2.发送http请求
        FinalHttp fh = new FinalHttp ();
        List<NameValueBean> headers = reqHeaders();
        for (NameValueBean head : headers){
            fh.addHeader(StringUtils.trimNull(head.getName()),StringUtils.trimNull(head.getValue()));
        }

        // 3.发送请求
        httpReqMethod = StringUtils.trimNull (httpReqMethod, HTTP_POST_REQ);
        if (httpReqMethod.equals (HTTP_POST_REQ)) {
            fh.post (reqUrl (url, ctx), reqParams, ajaxCallback);
        } else {
            fh.get (reqUrl (url, ctx), reqParams, ajaxCallback);
        }
    }

    /**
     * 图片上传接口
     * @param ctx
     * @param params
     * @param url
     * @param clazz
     * @param reqTag
     * @param callback
     */
    public static void uploadFiles(Context ctx,AjaxParams params,String url,final Class<? extends BaseRespBean> clazz,final int reqTag,
            final HttpRequestClientCallback callback){

        // 1.检查网络连接
        if (callback != null && !callback.isNetworkAvailable ()) {
            callback.httpRespFail (http_req_no_network_avail_code, http_req_no_netwokr_avail_msg,null, reqTag);
            return;
        }

        // 2.参数
        final AjaxParams reqParams = packUploadFileParams(ctx, params, url);

        // 3.发送http请求
        FinalHttp fh = new FinalHttp ();
        List<NameValueBean> headers = reqHeaders();
        for (NameValueBean head : headers){
            fh.addHeader(StringUtils.trimNull(head.getName()),StringUtils.trimNull(head.getValue()));
        }
        
        // 4.设置超时时间
        String timeout = StringUtils.trimNull (ctx.getResources ().getString (R.string.http_time_out),"30");
        fh.configTimeout (Integer.parseInt (timeout) * 1000);

        // 5.设置请求回调
        AjaxCallBack<String> ajaxCallback = new AjaxCallBack<String> () {

            @Override
            public void onLoading(long count,long current){
                super.onLoading (count, current);
                if (callback != null) {
                    callback.httpOnloading (count, current, reqTag);
                }
            }

            @Override
            public void onSuccess(String result){
                super.onSuccess (result);
                MyLog.d (TAG, "http请求的参数：" + reqParams.toString () + "," + "http请求的响应结果：" + result);
                if (callback != null) {
                    Class<? extends BaseRespBean> resultClazz = clazz == null ? BaseRespBean.class : clazz;
                    BaseRespBean resultBean = JsonUtils.parseObject (result, resultClazz);
                    String errorcode = StringUtils.trimNull (resultBean.getResponseCode());
                    String errorMsg = StringUtils.trimNull (resultBean.getMessage(), "系统繁忙，请稍后重试");
                    if (http_req_business_success_code.equalsIgnoreCase (errorcode)) {
                        callback.httpRespSuccess (resultBean, reqTag, resultClazz);
                    } else {
                        callback.httpRespFail (errorcode, errorMsg,resultBean,reqTag);
                    }
                }
            }

            @Override
            public AjaxCallBack<String> progress(boolean progress,int rate){
                if (callback != null) {
                    callback.httpOnProgress (progress, rate, reqTag);
                }
                return super.progress (progress, rate);
            }

            @Override
            public void onStart(){
                super.onStart ();
                if (callback != null) {
                    callback.httpReqBegin (reqTag);
                }
            }

            @Override
            public void onFailure(Throwable t,int errorNo,String strMsg){
                super.onFailure (t, errorNo, strMsg);
                if (callback != null) {
                    callback.httpRespFail (String.valueOf (errorNo), StringUtils.trimNull (strMsg, "未知错误"),null, reqTag);
                }
            }
        };
        fh.post (reqUrl (url, ctx), reqParams, ajaxCallback);
    }

    /**
     * 处理图片上传请求
     * @param ctx
     * @param ajaxParams
     * @param url
     * @return
     */
    private static AjaxParams packUploadFileParams(Context ctx,AjaxParams ajaxParams,String url){
        // 1.组装参数
        if (ajaxParams == null) {
            ajaxParams = new AjaxParams ();
        }
        // 2.返回结果
        return ajaxParams;
    }

    /**
     * 获取请求url
     * @param url
     * @return
     */
    public static String reqUrl(String url,Context ctx){
        String reqUrl = null;
        if (url.startsWith ("http://") || url.startsWith ("https://")) {
            reqUrl = url;
        } else {
            reqUrl = StringUtils.trimNull (ctx.getResources ().getString (R.string.base_api_url)) + url;
        }
        return reqUrl;
    }

    /**
     * 发送异步的http请求
     * @param params
     * @param url
     */
    public static void doPost(Context ctx,List<NameValueBean> params,String url,final int reqTag,final HttpRequestClientCallback callback){
        doHttpRequest (ctx, params, url, null, reqTag, callback, HTTP_POST_REQ);
    }

    /**
     * 发送http POST请求--重载
     * @param params
     * @param url
     * @param clazz
     * @param reqTag
     * @param callback
     */
    public static void doPost(Context ctx,List<NameValueBean> params,String url,final Class<? extends BaseRespBean> clazz,final int reqTag,
            final HttpRequestClientCallback callback){
        doHttpRequest (ctx, params, url, clazz, reqTag, callback, HTTP_POST_REQ);
    }

    /**
     * 组装post请求参数
     * @param params
     * @param url
     * @return
     */
    private static AjaxParams packPostParams(Context ctx,List<NameValueBean> params,String url){

        // 1.传入的参数为空
        if (params == null || params.isEmpty ()) {
            params = new ArrayList<NameValueBean> ();
        }

        // 2.组装参数
        AjaxParams ajaxParams = new AjaxParams ();
        for ( NameValueBean nameValuePair : params ) {
            ajaxParams.put (StringUtils.trimNull (nameValuePair.getName ()), StringUtils.trimNull (nameValuePair.getValue ()));
        }

        // 3.返回结果
        return ajaxParams;
    }

    /**
     * 发送http GET请求
     * @param params
     * @param url
     * @param clazz
     * @param reqTag
     * @param callback
     */
    public static void doGet(Context ctx,List<NameValueBean> params,String url,final Class<? extends BaseRespBean> clazz,final int reqTag,
            final HttpRequestClientCallback callback){
        doHttpRequest (ctx, params, url, clazz, reqTag, callback, HTTP_GET_REQ);
    }


    /**
     * 请求头信息
     * @return
     */
    public static List<NameValueBean> reqHeaders() {
        // 名称
        String name = "admin";
        // 密码
        String pwd = "admin";
        // 时间
        String timeStap = DateUtils.getNowTimeStamp();
        // 签名
        String sign = reqHeaderSign(name,pwd,timeStap);
        // 用户代理
        String UserAgent = "IResource-Android";
        // 结果
        List<NameValueBean> list = new ArrayList<NameValueBean>();
        list.add(new NameValueBean("name",name));
        list.add(new NameValueBean("pwd",pwd));
        list.add(new NameValueBean("timeStap",timeStap));
        list.add(new NameValueBean("sign",sign));
        list.add(new NameValueBean("User-Agent",UserAgent));
        return list;
    }

    /**
     * 请求头签名
     * @param name
     * @param pwd
     * @param timeStap
     * @return
     */
    public static String reqHeaderSign(String name,String pwd,String timeStap){
        if(StringUtils.isEmpty(name)|| StringUtils.isEmpty(pwd)){
            return "";
        }
        String[] sortedParam = sortByHashCode(name, pwd);
        StringBuilder unEncyKey = new StringBuilder();
        for(String p : sortedParam){
            unEncyKey.append(p);
        }

        //对排序后的参数做MD5加密，生成KEY
        String KEY = MD5.md5(unEncyKey.toString());

        //对参数进行字典排序
        Arrays.sort(sortedParam);

        //将KEY插入参数的第二位
        StringBuilder unEncySign = new StringBuilder();
        unEncySign.append(sortedParam[0]).append(KEY).append(sortedParam[1]).append(timeStap);

        //最后对组装的字符串进行MD5加密
        return MD5.md5(unEncySign.toString());
    }

    /**
     * 排序
     * @param name
     * @param pwd
     * @return
     */
    private static String[] sortByHashCode(String name,String pwd){
        int nameHash = name.hashCode();
        int pwdHash = pwd.hashCode();
        if(nameHash > pwdHash){
            return new String[]{name,pwd};
        }
        else if(nameHash < pwdHash){
            return new String[]{pwd,name};
        }
        else{
            return new String[]{name,pwd};
        }
    }


    /**
     * 下载文件
     * @param url
     * @param continueDownload
     * @param localFilePath
     * @param localFileName
     */
    public static void downloadFile(String url,boolean continueDownload,String localFilePath,String localFileName, final DownloadCallBack callBack){

        String errorcode = AppContext.getInstance().getResources().getString(R.string.download_error_code);

        if(StringUtils.isEmpty(url) && callBack !=null){
            callBack.onDownloadError(AppContext.getInstance().getResources().getString(R.string.download_url_null),errorcode);
            return ;
        }

        if(StringUtils.isEmpty(localFilePath) && callBack !=null){
            callBack.onDownloadError(AppContext.getInstance().getResources().getString(R.string.download_local_path_null),errorcode);
            return ;
        }

        if(StringUtils.isEmpty(localFileName) && callBack !=null){
            callBack.onDownloadError(AppContext.getInstance().getResources().getString(R.string.download_local_filename_null),errorcode);
            return ;
        }

        if(callBack !=null && !callBack.isNetworkAvailable()){
            callBack.onDownloadError(AppContext.getInstance().getResources().getString(R.string.download_network_off),errorcode);
        }

        FinalHttp fh = new FinalHttp();
        File fileDir = new File(localFilePath);
        if(!fileDir.exists()){fileDir.mkdirs();}
        String fileFullName = localFilePath + File.separator + localFileName;

        //调用download方法开始下载
        fh.download(url,fileFullName,continueDownload,new AjaxCallBack<File>() {

            @Override
            public void onStart() {
                super.onStart();
                if(callBack !=null){
                    callBack.onDownloadStart();
                }
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                if(callBack !=null){
                    callBack.onDownloadLoading(count, current);
                }
            }

            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                if(callBack !=null) {
                    callBack.onDownloadFinished(file);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if(callBack !=null) {
                    callBack.onDownloadError(strMsg,String.valueOf(errorNo));
                }
            }
        });
    }

}
