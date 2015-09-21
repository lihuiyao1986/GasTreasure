/**
 * @Title: BaseFragmentPagerAdapter.java
 * @Package com.clt.runman.adapter
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com
 * @date 2015年4月10日 下午2:28:26
 * @Copyright:Copyright (c)
 * @Company:whty李焱生
 * @version V1.0
 */
package com.goldcard.igas.ui.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 *@Description:基础的fragmentPagerAdapter
 *@Author:李焱生
 *@Since:2015年4月10日下午2:28:26  
 */
public abstract class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    protected List<T> dataList;
    
    protected Map<Integer,T> fragmentMaps = new HashMap<Integer,T>();

    public BaseFragmentPagerAdapter(FragmentManager fm, List<T> dataList) {
        super (fm);
        this.dataList = dataList;
    }

    /**
     *@Author: 李焱生
     *@Since: 2015年4月10日下午2:28:51
     *@param position
     *@return
     */
    @Override
    public Fragment getItem(int position){
        T fragment = dataList.get (position);
        fragmentMaps.put (Integer.valueOf (position), fragment);
        return fragment;
    }

    /**
     *@Author: 李焱生
     *@Since: 2015年4月10日下午2:28:51
     *@return
     */
    @Override
    public int getCount(){
        return dataList.size ();
    }
    
    /**
     *@Author: 李焱生
     *@Since: 2015年4月23日下午2:12:49
     *@param container
     *@param position
     *@param object
     */
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        super.destroyItem (container, position, object);
        fragmentMaps.remove (Integer.valueOf (position));
    }
    
    /**
     *@Description: 根据index获取对应的fragment
     *@Author: 李焱生
     *@Since: 2015年4月23日下午2:14:38
     *@param index
     *@return
     */
    public T getFragmentByIndex(int index)
    {
        return fragmentMaps.get (Integer.valueOf (index));
    }

}
