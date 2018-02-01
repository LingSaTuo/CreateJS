package com.lingsatuo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kingsatuo.view.Frament.ExampleFragment;
import com.kingsatuo.view.Frament.LocaltionMavenFrament;
import com.kingsatuo.view.Frament.MavenFrament;
import com.lingsatuo.createjs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class MainPagerAdapter extends ViewPagerAdapterd {
    public MainPagerAdapter(FragmentManager fm,Activity context) {
        super(fm,context);
        setTitle(R.array.tabitem);
        LocaltionMavenFrament lm = new LocaltionMavenFrament();
        lm.setActivity(context);
        MavenFrament mf = new MavenFrament();
        mf.setActivity(context);
        ExampleFragment ef = new ExampleFragment();
        addData(mf);
        addData(lm);
        addData(ef);
    }

}
