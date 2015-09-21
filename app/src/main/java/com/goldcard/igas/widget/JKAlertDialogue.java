package com.goldcard.igas.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.goldcard.igas.R;
import com.goldcard.igas.core.AppContext;
import com.goldcard.igas.util.CommonUtils;
import com.goldcard.igas.util.GestureUtils;
import com.goldcard.igas.util.StringUtils;

/**
 * 对话框
 * @author liys
 * @since 2015-6-9
 * @version 1.0.0
 */
public class JKAlertDialogue extends DialogFragment implements View.OnClickListener {

    // 内容
    private TextView contentTv;

    // 取消按钮
    private Button cancelBtn;

    // 确认按钮
    private Button sureBtn;

    // 携带的参数
    private AlertOneModel model;

    // 回调
    private JKAlertDialogueCallBack callBack;

    // 分割线
    private View seperatorLine;

    // dialogParams
    private final static String paramFlag = "dialogParams";


    /**
     * 获取实体的方法
     * @param model
     * @return
     */
    public static JKAlertDialogue newInstance(AlertOneModel model){
        JKAlertDialogue dialogue = new JKAlertDialogue();
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
            model = (AlertOneModel)getArguments ().getSerializable(paramFlag);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        GestureUtils.Screen screen = GestureUtils.getScreenPix(AppContext.getInstance());
        window.setLayout(screen.widthPixels * 8/10, CommonUtils.dip2px(AppContext.getInstance(), 150));
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate (R.layout.alert_dialogue_one_layout, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated (view, savedInstanceState);
        contentTv = (TextView) view.findViewById (R.id.alert_one_view_content);
        cancelBtn = (Button)view.findViewById(R.id.alert_one_cancel_btn);
        sureBtn = (Button)view.findViewById(R.id.alert_one_confirm_btn);
        seperatorLine = (View)view.findViewById(R.id.alert_one_btn_seperator_line);
        cancelBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        callBack  = model.getCallBack();
        String cancelBtnTitle = StringUtils.trimNull(model.getCancelBtnTitle());
        String sureBtnTitle = StringUtils.trimNull(model.getSureBtnTitle());
        String content = StringUtils.trimNull(model.getContent());
        if(StringUtils.isEmpty(cancelBtnTitle) || StringUtils.isEmpty(sureBtnTitle)){
            seperatorLine.setVisibility(View.GONE);
        } else{
            seperatorLine.setVisibility(View.VISIBLE);
        }
        if(StringUtils.isEmpty(cancelBtnTitle)){
            cancelBtn.setVisibility(View.GONE);
        }else{
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setText(cancelBtnTitle);
        }
        if(StringUtils.isEmpty(sureBtnTitle)){
            sureBtn.setVisibility(View.GONE);
        }else{
            sureBtn.setVisibility(View.VISIBLE);
            sureBtn.setText(sureBtnTitle);
        }
        contentTv.setText(content);
        getDialog().setCancelable(model.isCancalTouchOutside());
        getDialog().setCanceledOnTouchOutside(model.isCancalTouchOutside());
    }

    /**
     * 接口回调
     */
    public interface  JKAlertDialogueCallBack{

        public void onCancelBtnClicked();

        public void onSureBtnClicked();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.alert_one_cancel_btn:
                if(callBack!=null){
                    callBack.onCancelBtnClicked();
                }
                dismiss();
                break;
            case R.id.alert_one_confirm_btn:
                if (callBack!=null){
                    callBack.onSureBtnClicked();
                }
                dismiss();
                break;
            default:
                break;
        }
    }
}
