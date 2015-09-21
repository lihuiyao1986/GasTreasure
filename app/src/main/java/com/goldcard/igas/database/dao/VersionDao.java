package com.goldcard.igas.database.dao;


import android.content.Context;

import com.goldcard.igas.database.AppDataBase;
import com.goldcard.igas.database.model.VersionInfoDaoModel;

import java.util.List;


/**
 * 
* @ClassName: VersionDao
* @author fuxianwei 
* @date 2015-4-10 下午3:48:25 
*
 */
public class VersionDao extends BaseDao {

    private static VersionDao dao;

    /**
     * 私有的构造函数
     * @param context
     */
    private VersionDao(Context context){
        super(context);
    }

    /**
     * 获取实例
     * @param context
     * @return
     */
    public static VersionDao getInstance(Context context){
        if(dao==null){
            dao = new VersionDao(context);
        }
        return dao;
    }

    /*
     * 保存版本信息
     */
    public void saveVersion(VersionInfoDaoModel version){
        if (null == version) return;
        deleteVersion ();
        AppDataBase.newInstance(context).database ().save (version);
    }

    /*
     * 清空数据库版本信息
     */
    public void deleteVersion(){
        AppDataBase.newInstance (context).database ().deleteAll (VersionInfoDaoModel.class);
    }

    /*
     * 获取最新版本信息
     */
    public VersionInfoDaoModel getVersion(){
        List<VersionInfoDaoModel> list = AppDataBase.newInstance (context).database ().findAll (VersionInfoDaoModel.class);
        if (list == null || list.isEmpty ()) return null;
        return list.get (0);
    }
}
