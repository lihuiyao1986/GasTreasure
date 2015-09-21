/**
 * @Title: AppException.java
 * @Package com.clt.runman.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com
 * @date 2015年4月13日 上午10:01:27
 * @Copyright:Copyright (c)
 * @Company:whty李焱生
 * @version V1.0
 */
package com.goldcard.igas.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;

import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.core.AppManager;
import com.goldcard.igas.util.CommonUtils;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:应用异常类
 * @Author:李焱生
 * @Since:2015年4月13日上午10:01:27
 * @version 1.0.0
 */
public class AppException extends Exception implements UncaughtExceptionHandler {

    private static final long               serialVersionUID = 6906280090680214131L;

    /** 异常处理的handler **/
    private UncaughtExceptionHandler defaultHandler;

    /** 应用上下文 **/
    private AppContext context;

    private AppException(Context context) {
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler ();
        this.context = (AppContext) context;
    }

    /**
     *@Description: 处理全局未catch的异常
     *@Author: 李焱生
     *@Since: 2015年4月13日上午10:01:27
     *@param thread
     *@param ex
     */
    @Override
    public void uncaughtException(Thread thread,Throwable ex){
        if (!handleException (ex) && defaultHandler != null) {
            defaultHandler.uncaughtException (thread, ex);
        }
    }

    /**
     *@Description: 是否处理异常
     *@Author: 李焱生
     *@Since: 2015年4月13日上午10:09:42
     *@param ex
     *@return
     */
    private boolean handleException(final Throwable ex){
        if (ex == null) { return false; }
        if (context == null) { return false; }
        final String crashReport = getCrashReport (context, ex);
        new Thread () {
            public void run(){
                Looper.prepare ();
                // 如果是debug模式的话，保存日志
                if (CommonUtils.isDebug(context)) {saveErrorLog (ex);}
                MobclickAgent.reportError (context, ex);
                TestinAgent.uploadException (context, crashReport, ex);
                //CommonUtils.sendAppCrashReport (context, "亲，应用崩溃了！");
                AppManager.getAppManager().AppExit (context);
                Looper.loop ();
            }
        }.start ();
        return true;
    }

    /**
     * 保存异常日志
     * @param excp
     */
    public void saveErrorLog(Throwable excp){
        //保存的错误信息
        String errorInfo = getCrashReport (context, excp);
        //保存的目录
        String errorFileDir = CommonUtils.errorLogDir(context);
        //保存的文件名称
        String errorFileName = CommonUtils.errorLogFileName ();
        File errorDir = new File (errorFileDir);
        if (!errorDir.exists ()) {
            errorDir.mkdirs ();
        }
        File file = new File (errorDir,errorFileName);
        FileOutputStream out = null;
        BufferedOutputStream bout = null;
        try {
            if (!file.exists ()) {
                file.createNewFile ();
            }
            out = new FileOutputStream (file,true);
            bout = new BufferedOutputStream (out);
            bout.write (errorInfo.getBytes (), 0, errorInfo.length ());
            bout.flush ();
        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            if (out != null) {
                try {
                    out.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (bout != null) {
                try {
                    bout.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }

    /**
     * 获取APP崩溃异常报告
     * @param ex
     * @return
     */
    private String getCrashReport(Context context,Throwable ex){
        PackageInfo pinfo = ((AppContext) context.getApplicationContext ()).getPackageInfo ();
        StringBuffer exceptionStr = new StringBuffer ();
        exceptionStr.append ("异常发生时间: " + new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss").format (new Date ()) + "\n");
        exceptionStr.append ("Version: " + pinfo.versionName + " (" + pinfo.versionCode + ")\n");
        exceptionStr.append ("API Level: " + android.os.Build.VERSION.SDK_INT + "\n");
        exceptionStr.append ("Android: " + android.os.Build.VERSION.RELEASE + " (" + android.os.Build.MODEL + ")\n\n\n");
        exceptionStr.append ("异常信息: \n");
        exceptionStr.append ("Exception: " + ex.getMessage () + "\n\n\n");
        exceptionStr.append ("堆栈信息: \n\n\n");
        StackTraceElement[] elements = ex.getStackTrace ();
        for ( int i = 0 ; i < elements.length ; i++ ) {
            exceptionStr.append (elements[i].getClassName () + ":" + elements[i].getMethodName () + ":" + elements[i].getLineNumber () + "\n");
        }
        return exceptionStr.toString ();
    }

    /**
     * 获取APP异常崩溃处理对象
     * @param context
     * @return
     */
    public static final AppException getAppExceptionHandler(Context context){
        return new AppException (context.getApplicationContext ());
    }
}
