package com.lingsatuo.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.kingsatuo.view.Frament.ExampleFragment;
import com.kingsatuo.view.Frament.LocaltionMavenFrament;
import com.kingsatuo.view.Frament.MavenFrament;
import com.kingsatuo.view.Frament.UserUpMaven;
import com.lingsatuo.createjs.R;

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
        UserUpMaven user = new UserUpMaven();
        addData(mf);
        addData(user);
        addData(lm);
        addData(ef);
    }

}
