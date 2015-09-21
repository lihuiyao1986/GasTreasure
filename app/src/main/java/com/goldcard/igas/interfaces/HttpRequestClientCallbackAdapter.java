package com.goldcard.igas.interfaces;


import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * HttpRequestClientCallback适配器类
 * @author yanshengli
 * @since 2015-1-29
 */
public class HttpRequestClientCallbackAdapter implements HttpRequestClientCallback {

    @Override
    public void httpRespSuccess(BaseRespBean result,int reqTag,Class<?> clazz){

    }

    @Override
    public void httpRespFail(String errorcode,String errorMsg,BaseRespBean result,int reqTag){

    }

    @Override
    public void httpReqBegin(int reqTag){

    }

    @Override
    public void httpOnProgress(boolean progress,int rate,int reqTag){

    }

    @Override
    public void httpOnloading(long count,long current,int reqTag){

    }

    @Override
    public boolean isNetworkAvailable(){
        return true;
    }

}
