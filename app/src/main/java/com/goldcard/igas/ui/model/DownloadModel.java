package com.goldcard.igas.ui.model;

import java.io.Serializable;

/**
 * 下载对应的对话框
 * @author lys
 * @since 2015-6-24
 * @version 1.0.0
 */
public class DownloadModel implements Serializable {

    private static final long serialVersionUID = -3637736023897700577L;

    // 下载url
    private String url;

    // 保存本地的文件路径
    private String localFilePath;

    // 保存本地的文件名称
    private String localFileName;

    // 是否可以点击外面取消
    private boolean cancelTouchOutside;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public void setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
    }

    public boolean isCancelTouchOutside() {
        return cancelTouchOutside;
    }

    public void setCancelTouchOutside(boolean cancelTouchOutside) {
        this.cancelTouchOutside = cancelTouchOutside;
    }
}
