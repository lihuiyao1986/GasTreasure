package com.goldcard.igas.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldcard.igas.R;
import com.goldcard.igas.util.StringUtils;

/**
 * 第一种类型的加载框
 * @since 2015-6-11
 * @author liys
 * @version 1.0.0
 */
public class LoadingOneDialog extends Dialog{

    public LoadingOneDialog(Context context) {
        super(context);
    }

    public LoadingOneDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context
     *            上下文
     * @param message
     *            提示
     * @param cancelable
     *            是否按返回键取消
     * @param cancelListener
     *            按下返回键监听
     * @return
     */
    public static LoadingOneDialog makeLoading(Context context, String message,boolean cancelable, OnCancelListener cancelListener) {
        LoadingOneDialog dialog = new LoadingOneDialog(context, R.style.loading_one_dialog_style);
        dialog.setTitle("");
        dialog.setContentView(R.layout.loading_one_dialog_layout);
        if (StringUtils.isEmpty(message)) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.findViewById(R.id.loading_one_dl_ll).getBackground().setAlpha(200);
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}
