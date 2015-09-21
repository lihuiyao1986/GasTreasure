package com.goldcard.igas.interfaces;

import java.io.File;

/**
 * Created by yanshengli on 15-6-24.
 */
public class DownloadCallBackAdapter implements DownloadCallBack {

    @Override
    public void onDownloadLoading(long count, long current) {}

    @Override
    public void onDownloadFinished(File file) {}

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    @Override
    public void onDownloadError(String errorMsg, String errorcode) {
        
    }

    @Override
    public void onDownloadStart() {}
}
