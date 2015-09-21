package com.goldcard.igas.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goldcard.igas.R;

/**
 * 第一种类型的toast
 * @author liys
 * @since 2015-6-10
 * @version 1.0.0
 */
public class ToastTwo extends Toast {

    /** 播放器 **/
    private MediaPlayer mPlayer;

    /** 是否播放 **/
    private boolean     isSound;

    public ToastTwo(Context context) {
        super(context);
    }

    /**
     * 构造函数-2
     * @param context
     * @param isSound
     */
    public ToastTwo(Context context, boolean isSound) {
        super (context);
        this.isSound = isSound;
        mPlayer = MediaPlayer.create (context, R.raw.toastone);
        mPlayer.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
            @Override
            public void onCompletion(MediaPlayer mp){
                mp.release ();
            }
        });
    }

    /**
     * 显示
     */
    @Override
    public void show(){
        super.show ();
        if (isSound) {
            mPlayer.start ();
        }
    }

    /**
     * 设置是否播放声音
     */
    public void setIsSound(boolean isSound){
        this.isSound = isSound;
    }

    /**
     * 获取控件实例
     * @param context
     * @param message 提示消息
     * @param isSound 是否播放声音
     * @return
     */
    public static ToastTwo makeText(Context context,String message,int duration,boolean isSound){
        ToastTwo result = new ToastTwo(context,isSound);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics dm = context.getResources ().getDisplayMetrics ();
        View view = inflate.inflate (R.layout.toast_two_layout, null);
        view.setMinimumWidth (dm.widthPixels);// 设置控件最小宽度为手机屏幕宽度

        // 1.消息
        TextView messageTV = (TextView) view.findViewById (R.id.toast_two_msg);
        messageTV.setText (message);

        // 2.设置视图
        result.setView (view);

        // 3.设置显示的时常
        result.setDuration (duration);

        // 4.显示最顶部
        result.setGravity (Gravity.CENTER, 0, 0);

        // 5.设置透明度
        result.getView().getBackground().setAlpha(120);

        // 6.返回结果
        return result;
    }
}
