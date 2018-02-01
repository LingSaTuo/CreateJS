package com.lingsatuo.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingsatuo.adapter.Holder.MavenHolder;
import com.lingsatuo.createjs.Utils.OnClick.Maven_ERROR;
import com.lingsatuo.createjs.Utils.OnClick.Maven_JS;
import com.lingsatuo.createjs.Utils.OnClick.Maven_USER;
import com.lingsatuo.createjs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MavenAdapter extends RecyclerView.Adapter<MavenHolder> {
    private List<Maven> mavens = new ArrayList<>();
    private Activity context;
    private LayoutInflater lif;
    public MavenAdapter(Activity context){
        this.context = context;
        lif = LayoutInflater.from(context);
    }
    public void addDate(List<Maven> mavens){
        for (Maven maven: mavens){
            this.mavens.add(maven);
        }
    }


    @Override
    public MavenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MavenHolder holder = new MavenHolder(lif.inflate(R.layout.js_maven_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MavenHolder holder, int position) {
        Maven maven = this.mavens.get(position);
        switch (maven.getTYPE()){
            case 0:
                holder.title.setText(maven.getTitle());
                holder.title_error.setVisibility(View.GONE);
                holder.title_local.setVisibility(View.GONE);
                holder.title.setOnClickListener(new Maven_JS(maven,context));
                break;
            case 1:
                holder.title_local.setText(maven.getTitle());
                holder.title_error.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                holder.title_local.setOnClickListener(new Maven_JS(maven,context));
                break;
            case 2:
                holder.title_error.setText(maven.getTitle());
                holder.title_local.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                holder.title_error.setOnClickListener(new Maven_ERROR(maven,context));
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mavens.size();
    }
}
