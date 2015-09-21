package com.goldcard.igas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 应用的adapter
 * @author  liys
 * @since  2015-5-20
 * @version  1.0.0
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {

    protected Context context;//运行上下文
    protected List<T> listData;//数据集合
    protected LayoutInflater listContainer;//视图容器
    protected int itemViewResource;//自定义项视图源id

    /**
     * 构造函数
     * @param context
     * @param data
     * @param resource
     */
    public AppBaseAdapter(Context context, List<T> data,int resource)
    {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
        this.itemViewResource = resource;
        this.listData = data;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public T getItem(int index) {
        return listData.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

}
