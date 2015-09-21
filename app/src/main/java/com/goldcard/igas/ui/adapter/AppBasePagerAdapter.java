package com.goldcard.igas.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *@Author:李焱生
 *@Since:2015年1月21日上午9:10:46
 */
public class AppBasePagerAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<View> ();

    public AppBasePagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public boolean isViewFromObject(View arg0,Object arg1){
        return arg0 == arg1;
    }

    @Override
    public int getCount(){
        return views.size ();
    }

    @Override
    public void destroyItem(View container,int position,Object object){
        ((ViewPager) container).removeView (views.get (position));
    }

    @Override
    public Object instantiateItem(View container,int position){
        ((ViewPager) container).addView (views.get (position));
        return views.get (position);
    }
}
