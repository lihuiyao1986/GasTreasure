package com.goldcard.igas.ui.model;

import com.goldcard.igas.R;

import java.io.Serializable;

/**
 * 导航栏信息
 * @author liys
 * @since 2015-5-21
 * @version 1.0.0
 */
public class NavBarOneModel implements Serializable {

    private static final long serialVersionUID = -5132537140664213048L;

    // 文本
    public static final int  type_txt = 0;

    // 图片
    public static final  int type_icon = 1;

    // 左按钮文本
    private String leftBtnTxt;

    // 右按钮文本
    private String rightBtnTxt;

    // 左按钮类型 0-－文本 1-－图片
    private int leftType = NavBarOneModel.type_icon;

    // 右按钮类型 0-－文本 1-－图片
    private int rightType = NavBarOneModel.type_txt;

    // 左按钮图片
    private int leftBtnIcon = R.mipmap.nav_back;

    // 右按钮图片
    private int rightBtnIcon;

    // 标题
    private String title;

    public String getLeftBtnTxt() {
        return leftBtnTxt;
    }

    public void setLeftBtnTxt(String leftBtnTxt) {
        this.leftBtnTxt = leftBtnTxt;
    }

    public String getRightBtnTxt() {
        return rightBtnTxt;
    }

    public void setRightBtnTxt(String rightBtnTxt) {
        this.rightBtnTxt = rightBtnTxt;
    }

    public int getLeftBtnIcon() {
        return leftBtnIcon;
    }

    public void setLeftBtnIcon(int leftBtnIcon) {
        this.leftBtnIcon = leftBtnIcon;
    }

    public int getRightBtnIcon() {
        return rightBtnIcon;
    }

    public void setRightBtnIcon(int rightBtnIcon) {
        this.rightBtnIcon = rightBtnIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }
}
