package com.goldcard.igas.interfaces;

import com.goldcard.igas.widget.TwoBtnAlertDialog;

/**
 * 带两个按钮的警告框的回调
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public interface TwoBtnAlertDialogCallback {
    /**
     * 取消按钮被点击
     * @param dialog
     */
    public void onCancelBtnClicked(TwoBtnAlertDialog dialog);

    /**
     * 确定按钮被点击
     * @param dialog
     */
    public void onSureBtnClicked(TwoBtnAlertDialog dialog);
}
