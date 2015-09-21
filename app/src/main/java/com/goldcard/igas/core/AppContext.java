package com.goldcard.igas.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.goldcard.igas.enums.LoadImageUrlType;
import com.goldcard.igas.exception.AppException;
import com.goldcard.igas.getui.GeTuiUtil;
import com.goldcard.igas.interfaces.BaiMapLocationCallBack;
import com.goldcard.igas.util.CommonUtils;
import com.goldcard.igas.util.MyLog;
import com.goldcard.igas.util.StringUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

/**
 * Created by jk on 15/9/14.
 */
public class AppContext extends Application{

    /** 百度地图定位的client **/
    public LocationClient          mLocationClient;
    /** 获取定位结果的回调接口 **/
    public MyLocationListener      mMyLocationListener;
    /** 百度定位结果回调接口 **/
    private BaiMapLocationCallBack locationCallBack;
    /** 应用上下文对象 **/
    private static AppContext instance;


    /**
     * 获取应用上下文对象的方法
     * @return
     */
    public static final AppContext getInstance() {
        return instance;
    }


    @Override
    public void onCreate(){
        // 1.调用父类方法
        super.onCreate();
        // 2.获取实体类对象
        instance = this;
        // 3.初始化一些参数
        init();
    }

    /**
     * 初始化Application
     */
    private void init(){
        // 1.初始化百度地图的定位client
        mLocationClient = new LocationClient (this.getApplicationContext ());
        mMyLocationListener = new MyLocationListener ();
        mLocationClient.registerLocationListener (mMyLocationListener);

        // 3.启动个推
        GeTuiUtil.initialize(getApplicationContext());

        // 4.设置是否是调试模式
        MyLog.isDeBug = CommonUtils.isDebug(getApplicationContext());

        // 5.注册异常处理机制
        Thread.setDefaultUncaughtExceptionHandler (AppException.getAppExceptionHandler(this));

        // 6.初始化图片异步加载的配置
        initImageLoader(this.getApplicationContext());

        // 7.在使用百度SDK各组间之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
    }

    /**
     * 初始化图片异步加载器
     * @param context
     */
    public static void initImageLoader(Context context)
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 加载图片
     * @param url
     * @param options
     * @param imageview
     */
    public static void displayImg(String url,DisplayImageOptions options,ImageView imageview,LoadImageUrlType type)
    {
        if(options == null)
        {
            options = new DisplayImageOptions.Builder()
                    //.showImageOnLoading(R.drawable.loading)
                    //.showImageOnFail(R.drawable.fail)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        ImageLoader.getInstance().displayImage(getLoadImgUrl(url,type), imageview, options);
    }

    /**
     * 获取待加载的图片url
     * @param url
     * @param type
     * @return
     */
    public static String getLoadImgUrl(String url, LoadImageUrlType type)
    {
        String imgUrl = url;
        if(type == LoadImageUrlType.ASSET)
        {
            imgUrl = ImageDownloader.Scheme.ASSETS.wrap(url);
        }
        else if(type == LoadImageUrlType.SDCARD)
        {
            imgUrl = ImageDownloader.Scheme.FILE.wrap(url);
        }
        else if(type == LoadImageUrlType.DRAWABLE)
        {
            imgUrl = ImageDownloader.Scheme.DRAWABLE.wrap(url);
        }
        return imgUrl;
    }


    /**
     * 开始定位
     *
     * @param clientOption
     * @param callback
     */
    public void startLocation(LocationClientOption clientOption,BaiMapLocationCallBack callback){
        locationCallBack = callback;
        if (clientOption == null) {
            clientOption = new LocationClientOption ();
            clientOption.setOpenGps (true);// 打开gps
            clientOption.setLocationMode (LocationClientOption.LocationMode.Hight_Accuracy);
            clientOption.setCoorType ("bd09ll");
            clientOption.setScanSpan (1000);
            clientOption.setIsNeedAddress (true);
            clientOption.setAddrType ("all");
        }
        mLocationClient.setLocOption (clientOption);
        mLocationClient.start();
    }



    /**
     * 定位结果回调接口类
     * @author yanshengli
     * @since 2015-1-30
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            if (locationCallBack != null) {
                locationCallBack.onLocationReceived (location, mLocationClient);
            }
            mLocationClient.stop ();
        }
    }


    /**
     * 是否是第一次启动App
     *
     * @return
     */
    public boolean isFristStart(){
        String firstUseFlag = CommonUtils.getFirstUseFlag (getApplicationContext ());
        if (StringUtils.isEmpty(firstUseFlag)) {
            CommonUtils.saveFirstUseFlag (getApplicationContext ());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected(){
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService (Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo ();
            if (info != null) {
                for ( int i = 0 ; i < info.length ; i++ ) {
                    if (info[i].getState () == NetworkInfo.State.CONNECTED) { return true; }
                }
            }
        }
        return false;
    }

    /**
     * 获取App安装包信息
     * @return
     */
    public PackageInfo getPackageInfo(){
        PackageInfo info = null;
        try {
            info = getPackageManager ().getPackageInfo (getPackageName (), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace (System.err);
        }
        if (info == null) info = new PackageInfo ();
        return info;
    }

    /**
     *
     *@Description: 获取应用的versionName
     *@Author: 李焱生
     *@Since: 2015年6月1日下午6:25:37
     *@return
     */
    public String appVersionName(){
        String versionName = "";
        try {
            versionName = getPackageManager ().getPackageInfo (getPackageName (), PackageManager.GET_META_DATA).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
            versionName = "";
        }
        return versionName;
    }


    /**
     *
     *@Description: 获取应用的versionCode
     *@Author: 李焱生
     *@Since: 2015年6月1日下午6:27:23
     *@return
     */
    public int appVersionCode(){
        int versionCode = 1;
        try {
            versionCode = getPackageManager ().getPackageInfo (getPackageName (), PackageManager.GET_META_DATA).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        return versionCode;
    }



}
