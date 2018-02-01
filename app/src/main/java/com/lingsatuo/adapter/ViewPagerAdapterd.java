package com.lingsatuo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */

public class ViewPagerAdapterd extends FragmentPagerAdapter {
    private List<Fragment> views = new ArrayList<>();
    private Context context;
    private int id;
    public ViewPagerAdapterd(FragmentManager fm, Activity context) {
        super(fm);
        this.context = context;
    }
    public void addData(Fragment fragment){
        views.add(fragment);
    }
    public void setTitle(int id){
        this.id = id;
    }
    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position<getCount()){
            return context.getResources().getStringArray(id)[position];
        }
        return super.getPageTitle(position);
    }
}