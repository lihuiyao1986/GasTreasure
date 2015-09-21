package com.goldcard.igas.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goldcard.igas.R;
import com.goldcard.igas.ui.model.NavBarOneModel;
import com.goldcard.igas.util.StringUtils;

/**
 * 导航栏
 * @author liys
 * @since 2015-5-21
 * @version 1.0.0
 */
public class NavBarOneFragment extends BaseFragment implements View.OnClickListener{


    private NavBarOneFragmentCallBack callBack;

    /** 左导航按钮 **/
    private Button leftBtn;

    /** 右导航按钮 **/
    private Button rightBtn;

    /** 导航中间的标题 **/
    private TextView centerTv;

    /** 根视图 **/
    private View rootView;

    /** 导航信息 **/
    private NavBarOneModel navInfo;

    /**
     * 获取方法
     * @param navInfo
     * @return
     */
    public static NavBarOneFragment newInstance(NavBarOneModel navInfo){
        NavBarOneFragment fragment = new NavBarOneFragment ();
        Bundle bundle = new Bundle ();
        bundle.putSerializable("navInfo", navInfo);
        fragment.setArguments (bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navInfo = (NavBarOneModel)getArguments().get("navInfo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.nav_bar_one,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // 1.调用父类的方法
        super.onViewCreated(view, savedInstanceState);

        // 2.初始化视图组件
        initUIView();

        // 3.注册事件监听器
        registerListener();

        // 4.初始化数据
        initData();
    }


    /**
     * 初始化数据
     */
    private void initData()
    {
        if(navInfo.getLeftType() == NavBarOneModel.type_icon){
            Drawable leftDrawable = getResources().getDrawable(navInfo.getLeftBtnIcon());
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            leftBtn.setCompoundDrawables(leftDrawable,null,null,null);
        }else{
            leftBtn.setText(StringUtils.trimNull(navInfo.getLeftBtnTxt()));
        }

        if(navInfo.getRightType() == NavBarOneModel.type_icon)
        {
            Drawable rightDrawable = getResources().getDrawable(navInfo.getRightBtnIcon());
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            rightBtn.setCompoundDrawables(null,null,rightDrawable,null);
        }else{
            rightBtn.setText(StringUtils.trimNull(navInfo.getRightBtnTxt()));
        }
        centerTv.setText(StringUtils.trimNull(navInfo.getTitle()));
    }


    /**
     * 注册事件监听
     */
    private void registerListener()
    {
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        centerTv.setOnClickListener(this);
    }


    /**
     * 初始化视图组件
     */
    private void initUIView(){
        leftBtn = (Button)rootView.findViewById(R.id.left_nav_bar);
        rightBtn = (Button)rootView.findViewById(R.id.right_nav_bar);
        centerTv = (TextView)rootView.findViewById(R.id.nav_bar_one_center_tv);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.left_nav_bar:
                if(callBack!=null){ callBack.onLeftBtnClicked(this,leftBtn);}
                break;
            case R.id.right_nav_bar:
                if(callBack!=null){ callBack.onRightBtnClicked(this,rightBtn);}
                break;
            case R.id.nav_bar_one_center_tv:
                if(callBack!=null){ callBack.onTitleClicked(this,centerTv);}
                break;
            default:
                break;
        }

    }

    public NavBarOneFragmentCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(NavBarOneFragmentCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 回调接口
     */
    public interface NavBarOneFragmentCallBack
    {

        public void onLeftBtnClicked(NavBarOneFragment fragment, Button leftBtn);

        public void onRightBtnClicked(NavBarOneFragment fragment, Button rightBtn);

        public void onTitleClicked(NavBarOneFragment fragment, TextView title);

    }
}
