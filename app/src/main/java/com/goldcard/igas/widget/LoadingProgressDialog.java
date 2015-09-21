package com.goldcard.igas.widget;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldcard.igas.R;

/**
 * 加载对话框
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class LoadingProgressDialog extends ProgressDialog{

    private AnimationDrawable mAnimation;
    private Context           mContext;
    private ImageView         mImageView;
    private String            mLoadingTip;
    private TextView          mLoadingTv;
    private int               mResid;

    /**
     * 构造方法-1
     * @param context
     * @param content
     * @param id
     */
    public LoadingProgressDialog(Context context, String content, int id) {
        super (context);
        this.mContext = context;
        this.mLoadingTip = content;
        this.mResid = id;
        setCanceledOnTouchOutside (true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // 调用父类的方法
        super.onCreate (savedInstanceState);
        // 初始化视图
        initView ();
        // 初始化数据
        initData ();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mImageView.setBackgroundResource (mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground ();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post (new Runnable () {
            @Override
            public void run(){
                mAnimation.start ();
            }
        });
        mLoadingTv.setText (mLoadingTip);
    }

    public void setContent(String str){
        mLoadingTv.setText (str);
    }


    /**
     * 初始化视图
     */
    private void initView(){
        setContentView (R.layout.progress_dialog);
        mLoadingTv = (TextView) findViewById (R.id.loadingTv);
        mImageView = (ImageView) findViewById (R.id.loadingIv);
    }
}
