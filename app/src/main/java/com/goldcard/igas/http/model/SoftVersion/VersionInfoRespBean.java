package com.goldcard.igas.http.model.SoftVersion;

import com.goldcard.igas.database.model.VersionInfoDaoModel;
import com.goldcard.igas.http.model.base.BaseRespBean;

/**
 * 版本更新信息响应类
 * @author liys
 * @since 2015-6-25
 * @version 1.0.0
 */
public class VersionInfoRespBean  extends BaseRespBean{

    private static final long serialVersionUID = -8341726045016605935L;

    private VersionInfoDaoModel softVersion;

    public VersionInfoDaoModel getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(VersionInfoDaoModel softVersion) {
        this.softVersion = softVersion;
    }
}
