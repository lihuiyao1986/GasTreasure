package com.goldcard.igas.others;


import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.database.dao.VersionDao;
import com.goldcard.igas.database.model.VersionInfoDaoModel;
import com.goldcard.igas.http.HttpRequestClient;
import com.goldcard.igas.http.model.SoftVersion.VersionInfoRespBean;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.interfaces.HttpRequestClientCallbackAdapter;
import com.goldcard.igas.interfaces.VersionUpdateListener;
import com.goldcard.igas.R;

/**
 * 版本在线更新
 * @author liys
 * @since 2015-6-25
 * @version 1.0.0
 */
public class UpdateChecker {

    // 下载的URL
    private String downloadUrl;

    // 版本更新对应的监听器
    private VersionUpdateListener versionUpdateListener;


    /**
     * 构造函数-1
     */
    public UpdateChecker(){
        this(null);
    }

    /**
     * 构造函数-2
     * @param versionUpdateListener
     */
    public UpdateChecker(VersionUpdateListener versionUpdateListener){
        this.versionUpdateListener = versionUpdateListener;
        this.downloadUrl = AppContext.getInstance().getResources().getString(R.string.version_update_url);
    }

    /**
     * 检测更新
     */
    public void checkUpdate(){

        HttpRequestClient.doPost(AppContext.getInstance(),null,this.downloadUrl, VersionInfoRespBean.class,0,new HttpRequestClientCallbackAdapter(){

            @Override
            public void httpRespSuccess(BaseRespBean result, int reqTag, Class<?> clazz) {
                super.httpRespSuccess(result, reqTag, clazz);
                VersionInfoRespBean respBean = (VersionInfoRespBean)result;
                if(respBean!=null && respBean.getSoftVersion() !=null){
                    VersionDao.getInstance(AppContext.getInstance()).saveVersion(respBean.getSoftVersion());
                    if(isLastVersion()){
                        if(versionUpdateListener!=null){
                            versionUpdateListener.onNewVersionFound(respBean.getSoftVersion());
                        }
                    }else{
                        if(versionUpdateListener!=null){
                            versionUpdateListener.onIsAlreadyNewVersion(respBean.getSoftVersion());
                        }
                    }
                }
            }

            @Override
            public boolean isNetworkAvailable(){
                return AppContext.getInstance().isNetworkConnected();
            }
        });
    }


    /**
     * 判断是否是最新版本
     * @return
     */
    public boolean isLastVersion(){
        VersionInfoDaoModel version = VersionDao.getInstance(AppContext.getInstance()).getVersion();
        return AppContext.getInstance().appVersionCode () < version.getVersionCode ();
    }


    public VersionUpdateListener getVersionUpdateListener() {
        return versionUpdateListener;
    }

    public void setVersionUpdateListener(VersionUpdateListener versionUpdateListener) {
        this.versionUpdateListener = versionUpdateListener;
    }



}
