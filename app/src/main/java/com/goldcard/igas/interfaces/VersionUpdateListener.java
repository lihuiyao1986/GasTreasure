package com.goldcard.igas.interfaces;

import com.goldcard.igas.database.model.VersionInfoDaoModel;

/**
 * 版本更新对应的监听器接口
 * @author liys
 * @since 2015-6-25
 * @version 1.0.0
 */
public interface VersionUpdateListener {

    /**
     * 找到新版本
     */
    public void onNewVersionFound(VersionInfoDaoModel version);

    /**
     * 已是最新版本
     */
    public void onIsAlreadyNewVersion(VersionInfoDaoModel version);


}
