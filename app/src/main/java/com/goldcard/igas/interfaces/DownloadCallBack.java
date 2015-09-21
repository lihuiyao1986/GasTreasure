package com.goldcard.igas.interfaces;

import java.io.File;

/**
 * 文件下载对应的回调接口
 */
public interface DownloadCallBack {

    // 下载正在进行
    public void onDownloadLoading(long count, long current);

    // 下载完成
    public void onDownloadFinished(File file);

    // 检测网络是否可用
    public boolean isNetworkAvailable();

    // 下载遇到问题
    public void onDownloadError(String errorMsg, String errorcode);

    // 下载开始
    public void onDownloadStart();

}
