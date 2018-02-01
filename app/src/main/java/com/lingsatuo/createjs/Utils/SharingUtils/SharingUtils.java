package com.lingsatuo.createjs.Utils.SharingUtils;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.lingsatuo.adapter.SharingAdapter;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.App;

/**
 * Created by Administrator on 2017/11/17.
 */

public class SharingUtils {
    private MAIN activity;
    public SharingUtils(MAIN activity){
        this.activity = activity;
    }
    public void startActivity(){
        this.activity.clearCallBack("Sharing");
        this.activity.clearOnChange("Sharing");
        this.activity.addCallBack("Sharing", new A()).start();
    }


    public class A implements FunctionCallBACK {

        @Override
        public void T(Object object) {
            Object[] objects = (Object[]) object;
            String TAG = (String) objects[0];
            switch (TAG){
                case "onCreate":
                    activity = (MAIN) objects[1];
                    setContentView(activity,R.layout.sharing_layout);
                    break;
                case "onResume":
                    activity.getSupportActionBar().setTitle(R.string.s_57);
                    activity.getSupportActionBar().setSubtitle(activity.getResources().getString(R.string.s_35)+"    "+new App(activity).getVersionCode());
                    break;
            }
        }
    }

    private void setContentView(MAIN activity,int Rid){
        activity.setContentView(Rid);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        SharingAdapter mpa = new SharingAdapter(activity.getSupportFragmentManager(),activity);
        ViewPager vp = activity.findViewById(R.id.viewpager);
        vp.setAdapter(mpa);
        ((TabLayout)activity.findViewById(R.id.maintallayout)).setupWithViewPager(vp);
    }

}
