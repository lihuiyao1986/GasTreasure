package com.goldcard.igas.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppConstant;
import com.goldcard.igas.core.AppContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 工具类
 * @author liys
 * @version 1.0.0
 * @since  2015-5-20
 */
public class CommonUtils {

    private static String androidId = null;

    /**
     * 获取用户的uuid
     * @param context
     * @return
     */
    public static final String userUUID(Context context)
    {
        return "";
    }

    /**
     * 获取随机的uuid
     * @return
     */
    public static final String uuid()
    {
        return UUID.randomUUID().toString ().replaceAll ("-", "").toUpperCase ();
    }


    /**
     *判断当前应用程序处于前台还是后台
     */
    public static final boolean isApplicationBroughtToBackground(final Context context){
        ActivityManager am = (ActivityManager) context.getSystemService (Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks (1);
        if (!tasks.isEmpty ()) {
            ComponentName topActivity = tasks.get (0).topActivity;
            if (!topActivity.getPackageName ().equals (context.getPackageName ())) { return true; }
        }
        return false;
    }

    /**
     *
     *@Description: 错误日志文件名称
     *@Author: 李焱生
     *@Since: 2015年5月8日上午10:00:27
     *@return
     */
    public static final String errorLogFileName(){
        return "errorlog_" + new SimpleDateFormat("yyyyMMdd").format (new Date()) + ".txt";
    }


    /**
     * 获取存放拍照的的目录路径
     * @param context
     * @return
     */
    public static final String errorLogDir(Context context){
        if (SDCardUtils.checkSDCardAvailable ()) {
            return Environment.getExternalStorageDirectory() + File.separator + AppConstant.APP_NAME + File.separator + "logs" + File.separator;
        } else {
            return context.getFilesDir ().getAbsolutePath () + File.separator +  AppConstant.APP_NAME + File.separator + "logs" + File.separator;
        }
    }

    /**
     * 获取用户二维码图片的存放路径
     * @param context
     * @return
     */
    public static final String userQRcodeImgPath(Context context){
        if (SDCardUtils.checkSDCardAvailable ()) {
            return Environment.getExternalStorageDirectory() + File.separator + AppConstant.APP_NAME + File.separator + "images" + File.separator;
        } else {
            return context.getFilesDir ().getAbsolutePath () + File.separator +  AppConstant.APP_NAME + File.separator + "images" + File.separator;
        }
    }

    /**
     * 用户对应的二维码文件名
     * @return
     */
    public static final String userQRcodeImgFilename(String userId){
        return "user_qrcode_" + StringUtils.trimNull(userId,"default") + ".png";
    }


    /**
     * apk下载之后保存的目录
     * @param context
     * @return
     */
    public static final String apkDownloadDir(Context context){
        if (SDCardUtils.checkSDCardAvailable ()) {
            return Environment.getExternalStorageDirectory() + File.separator + AppConstant.APP_NAME + File.separator + "apk" + File.separator;
        } else {
            return context.getFilesDir ().getAbsolutePath () + File.separator +  AppConstant.APP_NAME + File.separator + "apk" + File.separator;
        }
    }


    /**
     * apk下载之后的文件名
     * @return
     */
    public static final String apkFileName(){
        return "gasTreasure.apk";
    }


    /**
     *
     *@Description: 判断是否是调试模式
     *@Author: 李焱生
     *@Since: 2015年5月8日上午9:45:41
     *@param ctx
     *@return
     */
    public static final boolean isDebug(Context ctx){
        String isDebug = ctx.getResources ().getString (R.string.isDebug);
        isDebug = StringUtils.trimNull (isDebug, "0");
        return "1".equals (isDebug);
    }

    /**
     *@Description: 保存信息到SharedPreferences
     *@Author: 李焱生
     *@Since: 2015年1月24日下午5:16:11
     *@param context 上下文对象
     *@param key 键
     *@return
     */
    public static final String getSharedPreferences(Context context,String key){
        SharedPreferences tokenSharedPreferences = context.getSharedPreferences (AppConstant.sharePreferenceFileName, Activity.MODE_PRIVATE);
        return tokenSharedPreferences.getString (key, "");
    }

    /**
     *@Description: 从SharedPreferences中取信息
     *@Author: 李焱生
     *@Since: 2015年1月24日下午5:16:46
     *@param context 上下文对象
     *@param key 键
     *@param value 值
     */
    public static final void saveSharedPreferences(Context context,String key,String value){
        SharedPreferences tokenSharedPreferences = context.getSharedPreferences (AppConstant.sharePreferenceFileName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenSharedPreferences.edit ();
        editor.putString (key, value);
        editor.commit ();
    }

    /**
     * 获取是否是第一使用app的标识
     * @param context
     * @return
     */
    public static final String getFirstUseFlag(Context context){
        return getSharedPreferences (context, AppConstant.FIRST_USE_FLAG);
    }

    /**
     * 保存第一使用的标识
     * @param context
     */
    public static final void saveFirstUseFlag(Context context){
        saveSharedPreferences (context, AppConstant.FIRST_USE_FLAG, "true");
    }


    /**
     *@Description: 获取请求对应的UserAgent
     *@Author: 李焱生
     *@Since: 2015年1月22日上午10:43:29
     *@param activity
     *@return
     */
    public static final String useragent(Activity activity){

        // 获取手机的分辨率
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager ().getDefaultDisplay ().getMetrics (mDisplayMetrics);
        int screenWidth = mDisplayMetrics.widthPixels;
        int screenHeight = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;

        // 获取手机的型号信息
        TelephonyManager phoneMgr = (TelephonyManager) activity.getSystemService (Context.TELEPHONY_SERVICE);
        String mobileType = "";
        if (phoneMgr != null) {
            mobileType = Build.MODEL;// 手机型号
        }

        // 获取手机的网络状态信息
        ConnectivityManager conMan = (ConnectivityManager) activity.getSystemService (Context.CONNECTIVITY_SERVICE);
        String netType = "UNKNOW";
        if (conMan != null) {
            NetworkInfo info = conMan.getActiveNetworkInfo ();
            if (info != null && info.isConnected ()) {
                netType = info.getTypeName ();
            }
        }

        // 获取版本名称和版本号
        int versionCode = 0;
        String versionName = "";
        try {
            PackageManager packagemanager = activity.getPackageManager ();
            String packageName = activity.getPackageName ();
            PackageInfo packageInfo = packagemanager.getPackageInfo (packageName, 8192);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            versionCode = 0;
            versionName = "0";
        }
        if (versionName != null) {
            int idx = versionName.indexOf (" ");
            if (idx != -1) {
                versionName = versionName.substring (0, idx);
            }
            idx = versionName.indexOf ("(");
            if (idx != -1) {
                versionName = versionName.substring (0, idx);
            }
        }

        // 手机操作系统的版本号
        String osType = Build.VERSION.RELEASE;

        // 手机唯一串
        String uuid = MD5.md5 (getAndroidId());

        // 组装userAgent数据
        String userAgent = "android_" + osType + "_" + versionCode + "#" + versionName + "_" + screenWidth + "*" + screenHeight + "*" + density + "_"
                + mobileType + "_" + uuid + "_" + netType;
        return userAgent;
    }


    /**
     * ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数
     * 缺点：当设备被wipe后该数改变
     * @return
     */
    public static final String getAndroidId(){
        if (androidId != null && androidId.length () > 0) { return androidId; }
        String sep = "#&#";
        try {
            /*
             * Build.BOARD // 主板 Build.BRAND // android系统定制商 Build.CPU_ABI // cpu指令集 Build.DEVICE // 设备参数 Build.DISPLAY // 显示屏参数 Build.FINGERPRINT // 硬件名称 Build.HOST Build.ID // 修订版本列表
             * Build.MANUFACTURER // 硬件制造商 Build.MODEL // 版本 Build.PRODUCT // 手机制造商 Build.TAGS // 描述build的标签 Build.TIME Build.TYPE // builder类型 Build.USER
             */
            String aid = Settings.Secure.getString(AppContext.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
            String info = Build.BOARD + sep + Build.BRAND + sep + Build.CPU_ABI + sep + Build.DEVICE + sep + Build.DISPLAY + sep + Build.HOST + sep + Build.ID
                    + sep + Build.MANUFACTURER + sep + Build.MODEL + sep + Build.PRODUCT + sep + Build.TAGS + sep + Build.TYPE + sep + Build.USER;
            aid = aid + sep + info;
            aid = SecurityUtils.encrypt (aid, null);
            androidId = aid.replace ("_", "-");
            return androidId;
        } catch (Exception e) {}

        return androidId;
    }

    /**
     * 保存个信的clientId
     * @param clientId
     * @param context
     */
    public static void saveGexinClientId(String clientId,Context context){
        if (null != context && !StringUtils.isEmpty (clientId)) {
            saveSharedPreferences (context, AppConstant.GEXIN_CLIENTID, StringUtils.trimNull (clientId));
        }
    }

    /**
     * 获取个信的clientId
     * @param context
     * @return
     */
    public static String getGeXinClientId(Context context){
        return getSharedPreferences (context, AppConstant.GEXIN_CLIENTID);
    }

    /**
     * 将dp转换成px
     * @param context
     * @param dpValue
     * @return
     */
    public final static int dip2px(Context context,float dpValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将像素转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public final static int px2dip(Context context,float pxValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
