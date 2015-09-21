package com.goldcard.igas.others;


import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

import com.goldcard.igas.interfaces.CountTimerCallBack;

/**
 * 自定义的计时器
 * @author yanshengli
 * @since 2015-2-27
 */
public class AppCountTimer extends CountDownTimer {

    private Handler                  mHandler;
    private CountTimerCallBack callback;
    public static final int          IN_RUNNING  = 1001;
    public static int                END_RUNNING = 1002;

    /**
     * 构造函数
     * @param totalSecond
     * @param intervalSecond
     */
    public AppCountTimer(long totalSecond, long intervalSecond) {
        super (totalSecond * 1000, intervalSecond * 1000);
        mHandler = getHandler ();
    }

    /**
     * 获取handler
     * @return
     */
    private Handler getHandler(){
        return new Handler () {

            @Override
            public void handleMessage(Message msg){
                super.handleMessage (msg);
                if (msg.what == AppCountTimer.IN_RUNNING) {
                    // 正在倒计时
                    if (callback != null) {
                        long remainSecond = (Long) msg.obj;
                        callback.timerCountRunning (remainSecond);
                    }
                } else if (msg.what == AppCountTimer.END_RUNNING) {
                    // 完成倒计时
                    if (callback != null) {
                        callback.timerFinished ();
                    }
                }
            }
        };
    }

    @Override
    public void onTick(long millisUntilFinished){
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage ();
            msg.what = IN_RUNNING;
            msg.obj = millisUntilFinished / 1000;
            mHandler.sendMessage (msg);
        }
    }

    @Override
    public void onFinish(){
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage ();
            msg.what = END_RUNNING;
            mHandler.sendMessage (msg);
        }
    }

    public CountTimerCallBack getCallback(){
        return callback;
    }

    public void setCallback(CountTimerCallBack callback){
        this.callback = callback;
    }

}
