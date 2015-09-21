package com.goldcard.igas.database.model;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 版本信息对应的实体类
 * @author liys
 * @since 2015-6-25
 * @version 1.0.0
 */
public class VersionInfoDaoModel extends BaseDaoModel{

    @Id
    private long              id;
    /**
     * 版本号
     */
    private int               versionCode;
    /**
     * 版本名
     */
    private String            versionName;
    /**
     * 更新日志
     */
    private String            versionContent;
    /**
     * apk下载地址
     */
    private String            versionUrl;
    /**
     * 是否强制更新0不强制,1强制
     */
    private int               versionForce;

    /*
     * 更新时间
     */
    private String            createTime;

    public int getVersionCode(){
        return versionCode;
    }

    public void setVersionCode(int versionCode){
        this.versionCode = versionCode;
    }

    public String getVersionName(){
        return versionName;
    }

    public void setVersionName(String versionName){
        this.versionName = versionName;
    }

    public String getVersionContent(){
        return versionContent;
    }

    public void setVersionContent(String versionContent){
        this.versionContent = versionContent;
    }

    public String getVersionUrl(){
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl){
        this.versionUrl = versionUrl;
    }

    public int getVersionForce(){
        return versionForce;
    }

    public void setVersionForce(int versionForce){
        this.versionForce = versionForce;
    }

    public String getCreateTime(){
        return createTime;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
}
