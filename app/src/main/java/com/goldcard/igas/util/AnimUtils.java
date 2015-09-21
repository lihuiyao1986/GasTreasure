package com.goldcard.igas.util;

import android.app.ActivityManager;
import android.content.Context;

import com.goldcard.igas.R;

import java.util.List;

/**
 * 动画工具类
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class AnimUtils {


    /**
     * 动画效果,进入
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void inAnim(){
        leftInTranslateAnim ();
    }

    /**
     * 动画效果,退出
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void outAnim(){
        rightOutTranslateAnim ();
    }

    /**
     * 左右移动动画效果:左进
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void leftInTranslateAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 左右移动动画效果:右出
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void rightOutTranslateAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 上下移动动画效果:上进
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void upInTranslateAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.in_from_top, R.anim.push_static);
    }

    /**
     *@Description: 从下进入
     *@Author: 张聪
     *@Since: 2015-1-31下午2:57:00
     */
    public static void bottomInTranslateAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.in_from_bottom, R.anim.push_static);
    }

    /**
     * 上下移动动画效果:下出
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void downOutTranslateAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.push_static, R.anim.push_down_out);
    }

    /**
     * 淡入淡出动画效果
     * @author liys
     * @date 2015-1-30 上午11:41:06
     */
    public static void fadeAlphaAnim(){
        AppUtils.getActivity ().overridePendingTransition (R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Check service state
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context,String className){
        ActivityManager am = (ActivityManager) context.getSystemService (Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices (30);
        for ( ActivityManager.RunningServiceInfo info : list ) {
            if (info.service.getClassName ().equals (className)) { return true; }
        }
        return false;
    }

    /**
     * 将dp转换成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将像素转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
