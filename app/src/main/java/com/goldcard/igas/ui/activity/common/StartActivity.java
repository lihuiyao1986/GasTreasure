package com.goldcard.igas.ui.activity.common;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.enums.SwipeDirect;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.testin.agent.TestinAgent;

/**
 * 启动页面
 * @author liys
 * @since  2015-5-20
 * @version 1.0.0
 */
public class StartActivity extends BaseFragmentActivity {

    /** 启动页面的图片 **/
    private ImageView start_icon_img;

    /** 微信api **/
    private IWXAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 1.调用父类的方法
        super.onCreate(savedInstanceState);

        // 2.添加testIn模块
        TestinAgent.init(this);
    }

    @Override
    protected void onUIViewInit() {
        // 1.设置布局
        setContentView(R.layout.start_page_layout);
        // 2.启动页的图片
        start_icon_img = (ImageView)findViewById(R.id.start_icon_img);
    }

    @Override
    protected void onListenerRegister() {

    }

    @Override
    protected void onDataInit() {
        Animation animation = AnimationUtils.loadAnimation(AppContext.getInstance(), R.anim.start_icon_anim);
        start_icon_img.setAnimation(animation);
        start_icon_img.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AppContext.getInstance().isFristStart()){
                    skipToTargetPage(MyQRcodeActivity.class,null, SwipeDirect.DIRECT_RIGHT);
                }else {
                    skipToTargetPage(MyQRcodeActivity.class,null, SwipeDirect.DIRECT_RIGHT);
                }
                finish();
            }
        },1000);
    }
}
