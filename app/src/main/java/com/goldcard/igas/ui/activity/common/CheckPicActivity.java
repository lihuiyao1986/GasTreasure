package com.goldcard.igas.ui.activity.common;

import android.widget.FrameLayout;

import com.goldcard.igas.R;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.goldcard.igas.ui.fragment.PicCheckFragment;
import com.goldcard.igas.ui.model.PickCheckModel;
import com.goldcard.igas.util.AnimUtils;
import com.goldcard.igas.widget.TouchImageView;

/**
 * 浏览图片页面
 */
public class CheckPicActivity extends BaseFragmentActivity implements PicCheckFragment.PicCheckFragmentCallback {

    private FrameLayout container;

    @Override
    protected void onUIViewInit() {
        // 1.布局文件
        setContentView(R.layout.pic_check_layout);
        // 2.容器
        container = (FrameLayout)findViewById(R.id.pic_check_container);
    }

    @Override
    protected void onListenerRegister() {

    }

    @Override
    protected void onDataInit() {
        PickCheckModel model = (PickCheckModel)getIntent().getExtras().getSerializable("pics");
        PicCheckFragment fragment = PicCheckFragment.newInstance(model);
        fragment.setCallback(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.pic_check_container,fragment).commit();
    }

    @Override
    public void onImageTaped(TouchImageView imageView, int index) {
        finish();
        AnimUtils.outAnim();
    }

}
