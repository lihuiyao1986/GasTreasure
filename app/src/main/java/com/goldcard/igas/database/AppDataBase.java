package com.goldcard.igas.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goldcard.igas.R;
import com.goldcard.igas.util.CommonUtils;
import com.goldcard.igas.util.StringUtils;

import net.tsz.afinal.FinalDb;


/**
 * 操作数据库的实体类
 * @author yanshengli
 *
 */
public class AppDataBase{

    /**
     * 实体类对象
     */
    private static AppDataBase instance;

    /**
     * 数据文件名
     */
    private static final String   defaultDBName = "database.sql";

    /**
     * 数据库对象
     */
    private FinalDb               finalDB;

    /**
     * 私有的构造方法
     */
    private AppDataBase(Context context) {
        // 1.数据库文件名
        String dbName = context.getResources ().getString (R.string.FinalDB_DBName);
        if (StringUtils.isEmpty(dbName)) {
            dbName = defaultDBName;
        }

        // 2.是否是debug模式
        boolean isDebug = CommonUtils.isDebug(context);
        
        // 4.数据库版本
        String version = StringUtils.trimNull (context.getResources ().getString (R.string.FinalDB_Version), "1");
   
        // 3.创建对象
        finalDB = FinalDb.create(context, dbName, isDebug, Integer.parseInt(version), new FinalDb.DbUpdateListener() {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (oldVersion < newVersion) {
                    dropDb(db);
                }
            }
        });
    }
    
    /**
     * 删除所有数据表
     */
    public void dropDb(SQLiteDatabase db){
        Cursor cursor = db.rawQuery ("SELECT name FROM sqlite_master WHERE type ='table' AND name != 'sqlite_sequence'", null);
        if (cursor != null) {
            while (cursor.moveToNext ()) {
                db.execSQL ("DROP TABLE " + cursor.getString (0));
            }
        }
        if (cursor != null) {
            cursor.close ();
            cursor = null;
        }
    }

    /**
     * 获取数据库对象
     * @return
     */
    public FinalDb database(){
        return finalDB;
    }

    /**
     * 获取数据库对象
     * @return
     */
    public static AppDataBase newInstance(Context context){
        if (instance == null) {
            instance = new AppDataBase (context);
        }
        return instance;
    }
}
