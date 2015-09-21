package com.goldcard.igas.ui.activity.main;



import android.widget.Button;
import android.widget.TextView;

import com.goldcard.igas.R;
import com.goldcard.igas.http.model.base.BaseRespBean;
import com.goldcard.igas.ui.activity.base.BaseFragmentActivity;
import com.goldcard.igas.ui.fragment.NavBarOneFragment;
import com.goldcard.igas.ui.model.NavBarOneModel;

/**
 * Created by jk on 15/9/15.
 */
public class MainActivity extends BaseFragmentActivity implements NavBarOneFragment.NavBarOneFragmentCallBack {

    @Override
    protected void onUIViewInit() {

        // 1. 布局文件
        setContentView(R.layout.my_qrcode_page_layout);

        // 2.设置导航
        NavBarOneModel navBarInfo = new NavBarOneModel();
        navBarInfo.setTitle(getResources().getString(R.string.scan_title));
        navBarInfo.setRightType(NavBarOneModel.type_txt);
        navBarInfo.setRightBtnTxt(getResources().getString(R.string.scan_album));
        NavBarOneFragment navBarOneFragment = NavBarOneFragment.newInstance(navBarInfo);
        navBarOneFragment.setCallBack(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_bar, navBarOneFragment).commit();
    }

    @Override
    protected void onListenerRegister() {

    }

    @Override
    protected void onDataInit() {

    }

    @Override
    public void onLeftBtnClicked(NavBarOneFragment fragment, Button leftBtn) {

    }

    @Override
    public void onRightBtnClicked(NavBarOneFragment fragment, Button rightBtn) {
         String url = getResources().getString(R.string.GetVersionUrl);
         post(url, null);
    }

    @Override
    protected void onHttpFailHandle(String errorcode, String errorMsg,BaseRespBean respBean, int reqTag) {
        super.onHttpFailHandle(errorcode, errorMsg,respBean, reqTag);
    }

    @Override
    protected void onHttpSuccHandle(BaseRespBean data, int reqTag) {
        super.onHttpSuccHandle(data, reqTag);
    }

    @Override
    public void onTitleClicked(NavBarOneFragment fragment, TextView title) {

    }
}
