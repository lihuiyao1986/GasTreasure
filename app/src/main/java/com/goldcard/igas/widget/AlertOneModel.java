package com.goldcard.igas.widget;

import java.io.Serializable;

/**
 * Created by yanshengli on 15-6-9.
 */
public class AlertOneModel implements Serializable {

    private static final long serialVersionUID = -1220004808799710322L;

    private String sureBtnTitle;

    private String cancelBtnTitle;

    private String content;

    private boolean cancalTouchOutside = true;

    private JKAlertDialogue.JKAlertDialogueCallBack callBack;

    public String getSureBtnTitle() {
        return sureBtnTitle;
    }

    public void setSureBtnTitle(String sureBtnTitle) {
        this.sureBtnTitle = sureBtnTitle;
    }

    public String getCancelBtnTitle() {
        return cancelBtnTitle;
    }

    public void setCancelBtnTitle(String cancelBtnTitle) {
        this.cancelBtnTitle = cancelBtnTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JKAlertDialogue.JKAlertDialogueCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(JKAlertDialogue.JKAlertDialogueCallBack callBack) {
        this.callBack = callBack;
    }

    public boolean isCancalTouchOutside() {
        return cancalTouchOutside;
    }

    public void setCancalTouchOutside(boolean cancalTouchOutside) {
        this.cancalTouchOutside = cancalTouchOutside;
    }
}
