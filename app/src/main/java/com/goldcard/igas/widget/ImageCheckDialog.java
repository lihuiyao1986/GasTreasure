package com.goldcard.igas.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.ui.adapter.AppBasePagerAdapter;
import com.goldcard.igas.ui.model.ImageCheckDialogModel;
import com.goldcard.igas.ui.model.PickCheckModel;
import com.goldcard.igas.util.GestureUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看图片的对话框
 * @author liys
 * @since 2015-6-15
 * @version 1.0.0
 */
public class ImageCheckDialog extends DialogFragment implements ViewPager.OnPageChangeListener{

    private final static String paramFlag = "dialogParams";

    private ImageCheckDialogModel model;

    private View rootView;

    private ViewPager viewPager;

    private LinearLayout indicator;

    private List<View> views;

    private ImageView[] indicators;

    private AppBasePagerAdapter pagerAdapter;

    private int currentIndex;

    private ImageCheckDialogCallback callback;

    /**
     * 获取实体的方法
     * @param model
     * @return
     */
    public static ImageCheckDialog newInstance(ImageCheckDialogModel model){
        ImageCheckDialog dialogue = new ImageCheckDialog();
        Bundle bundle = new Bundle ();
        bundle.putSerializable(paramFlag, model);
        dialogue.setArguments (bundle);
        return dialogue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setStyle (STYLE_NO_TITLE, R.style.mydialog);
        if (getArguments () != null) {
            model = (ImageCheckDialogModel)getArguments ().getSerializable(paramFlag);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        GestureUtils.Screen screen = GestureUtils.getScreenPix(AppContext.getInstance());
        window.setLayout(screen.widthPixels, screen.heightPixels);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        rootView = inflater.inflate (R.layout.image_check_dial_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){

        // 1.调用父类的方法
        super.onViewCreated (view, savedInstanceState);

        // 2.初始化视图组件
        initUIView();

        // 3.注册事件监听器
        registerListener();

        // 4.初始化数据
        initData();
    }


    /**
     * 注册事件监听
     */
    private void registerListener()
    {
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 初始化视图组件
     */
    private void initUIView(){
        viewPager = (ViewPager)rootView.findViewById(R.id.pic_check_view_pager);
        indicator = (LinearLayout)rootView.findViewById(R.id.pic_check_indicator);
    }

    public ImageCheckDialogCallback getCallback() {
        return callback;
    }

    public void setCallback(ImageCheckDialogCallback callback) {
        this.callback = callback;
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        currentIndex = model.getCurrentIndex();
        views = new ArrayList<View>();
        int picType = model.getPicType();
        int indicatorNums = 0;
        if(picType == PickCheckModel.pic_type_local){
            indicatorNums = model.getLocalPics().length;
        }else{
            indicatorNums = model.getNetPics().length;
        }
        if(indicatorNums>9){
            indicatorNums = 9;
        }
        indicators = new ImageView[indicatorNums];
        for ( int i = 0 ; i < indicatorNums ; i++ ) {
            // 循环加入图片
            final TouchImageView imageView = new TouchImageView(AppContext.getInstance());
            final int touchImageViewIndex = i;
            imageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if(callback !=null){callback.onImageTaped(imageView,touchImageViewIndex);}
                    return false;
                }
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return false;
                }
            });
            if(picType== PickCheckModel.pic_type_local){
                imageView.setBackgroundResource (model.getLocalPics()[i]);
            }else{
                String url = model.getNetPics()[i];
                ImageLoader.getInstance().displayImage(url,imageView);
            }
            views.add (imageView);
            // 循环加入指示器
            indicators[i] = new ImageView (AppContext.getInstance());
            indicators[i].setBackgroundResource (R.mipmap.indicators_default);
            if (i == currentIndex) {
                indicators[i].setBackgroundResource (R.mipmap.indicators_now);
            }
            indicator.addView (indicators[i]);
        }
        if(indicatorNums ==1){
            indicator.setVisibility(View.GONE);
        }
        pagerAdapter = new AppBasePagerAdapter (views);
        viewPager.setAdapter (pagerAdapter); // 设置适配器
        viewPager.setCurrentItem(currentIndex);
        viewPager.getBackground().setAlpha(100);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        currentIndex = i;
        // 更改指示器图片
        for ( int index = 0 ; index < indicators.length ; index++ ) {
            if(currentIndex == index){
                indicators[index].setBackgroundResource (R.mipmap.indicators_now);
            }else{
                indicators[index].setBackgroundResource (R.mipmap.indicators_default);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     *
     */
    public interface ImageCheckDialogCallback{
        public void onImageTaped(TouchImageView imageView, int index);
    }
}
