package com.kingsatuo.view.Frament;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lingsatuo.adapter.SIListViewAdapter;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.ItemClick.Maven;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MavenFrament extends Fragment {
    private Activity activity;
    public void setActivity(Activity activity){
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maven_list, container, false);
        if(getContext()==null){
            android.os.Process.killProcess(Process.myPid());
        }
        SIListViewAdapter si = new SIListViewAdapter(activity);
        ListView lv = view.findViewById(R.id.silistview);
        lv.setAdapter(si);
        Maven maven = new Maven(si, activity);
        lv.setOnItemClickListener(maven);
        return view;
    }
}
