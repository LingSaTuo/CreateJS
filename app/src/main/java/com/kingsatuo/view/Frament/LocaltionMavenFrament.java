package com.kingsatuo.view.Frament;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingsatuo.adapter.MavenAdapter;
import com.lingsatuo.createjs.R;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LocaltionMavenFrament extends Fragment {
    public static MavenAdapter ma;
    private Activity activity;
    public void setActivity(Activity activity){
        this.activity =activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.localtion_maven,container,false);
        RecyclerView page_maven = view.findViewById(R.id.recyclerview);
        if(activity==null){
            android.os.Process.killProcess(Process.myPid());
        }
        ma = new MavenAdapter(activity);
        page_maven.setLayoutManager(new GridLayoutManager(activity,3));
        page_maven.setAdapter(ma);
        ma.addDate(new com.lingsatuo.createjs.Utils.maven.Util().getMaven(activity));
        ma.notifyDataSetChanged();
        return view;
    }
}
