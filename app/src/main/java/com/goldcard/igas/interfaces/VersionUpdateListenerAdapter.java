package com.goldcard.igas.interfaces;

import com.goldcard.igas.database.model.VersionInfoDaoModel;

/**
 * 版本更新监听器
 * @author liys
 * @since 2015-6-25
 * @version 1.0.0
 */
public class VersionUpdateListenerAdapter implements VersionUpdateListener {


    @Override
    public void onNewVersionFound(VersionInfoDaoModel version) {

    }

    @Override
    public void onIsAlreadyNewVersion(VersionInfoDaoModel version) {

    }
}
