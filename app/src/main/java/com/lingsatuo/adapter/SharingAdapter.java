package com.lingsatuo.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.kingsatuo.view.Frament.SharingPullList;
import com.kingsatuo.view.Frament.SharingUp;
import com.lingsatuo.createjs.R;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SharingAdapter extends ViewPagerAdapterd {
    public SharingAdapter(FragmentManager fm, Activity context) {
        super(fm, context);
        setTitle(R.array.item);
        addData(new SharingPullList());
        addData(new SharingUp());
    }
}
