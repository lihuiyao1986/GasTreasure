package com.goldcard.igas.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.view.Display;
import android.view.WindowManager;

/**
 * 应用对应的工具类
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class AppUtils {


    private static Context  context;
    private static Activity act;

    public static Activity getActivity(){
        return act;
    }

    public static final void setActivity(Activity acti){
        act = acti;
        context = acti;
    }

    public static final Context getContext(){
        if (context != null) { return context; }
        return act;
    }

    public static final void setContext(Context ctx){
        context = ctx;
    }

}
