package com.goldcard.igas.database;

import android.content.Context;

import com.goldcard.igas.database.model.BaseDaoModel;

/**
 * 操作数据库的dao
 * @author yanshengli
 * @version 1.0.0
 * @since 2015-5-20
 *
 */
public class BaseDao<T extends BaseDaoModel> {

    protected Context context;

    protected BaseDao(Context context){
        this.context = context;
    }

    /**
     * 保存实体对象
     * @param daoModel
     */
    public void save(T daoModel){
        AppDataBase.newInstance(context).database().save(daoModel);
    }
}
