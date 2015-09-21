package com.goldcard.igas.core;

import android.app.Activity;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Stack;

/**
 * @Description:应用管理器类
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager      instance;

    private AppManager() {}

    /**
     * 单一实例
     */
    public static AppManager getAppManager(){
        if (instance == null) {
            instance = new AppManager ();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity){
        if (activityStack == null) {
            activityStack = new Stack<Activity> ();
        }
        activityStack.add (activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        Activity activity = activityStack.lastElement ();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity = activityStack.lastElement ();
        finishActivity (activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity){
        if (activity != null) {
            activityStack.remove (activity);
            activity.finish ();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        for ( Activity activity : activityStack ) {
            if (activity.getClass ().equals (cls)) {
                finishActivity (activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for ( int i = 0 , size = activityStack.size () ; i < size ; i++ ) {
            if (null != activityStack.get (i)) {
                Activity activity = activityStack.get (i);
                if (!activity.isFinishing ()) {
                    activity.finish ();
                }
            }
        }
        activityStack.clear ();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context){
        try {
            finishAllActivity ();
            MobclickAgent.onKillProcess(context);
            android.os.Process.killProcess (android.os.Process.myPid ());
            System.exit(0);
        } catch (Exception e) {}
    }

}