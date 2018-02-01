package com.lingsatuo.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FileSelectorAdapter extends BaseAdapter {
    private final Context activity;
    private LayoutInflater llf;
    private List<File> data;
    private boolean project = false;
    private boolean isProject = false;

    public FileSelectorAdapter(Context activity) {
        this.activity = activity;
        llf = LayoutInflater.from(activity);
    }
    public void setProject(boolean show){
        this.project = show;
    }
    public void setData(List<File> data) {
        List<File> list = new ArrayList<>();
        for (File f : data) {
            if (f.getName().toLowerCase().equals("build.txt")&&project) {
                list.add(f.getParentFile());
                isProject = true;
                break;
            }else{
                isProject = false;
            }
        }
        list.addAll(data);
        this.data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public boolean isProject(){
        return this.isProject;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = llf.inflate(R.layout.exampleadapter_group, null);
        ImageView icon = view.findViewById(R.id.example_icon);
        icon.setVisibility(View.VISIBLE);
        AppCompatTextView title = view.findViewById(R.id.example_title);
        File file = data.get(i);
        if (isProject&&i==0) {
            icon.setImageResource(R.mipmap.open);
            title.setText(activity.getResources().getString(R.string.open));
            return view;
        }
        if (!file.isFile()) {
            icon.setImageResource(R.mipmap.folder);
        } else {
            if (new Files().getExtension(file.getName()).equals(".js".toLowerCase())) {
                icon.setImageResource(R.mipmap.js);
            } else {
                icon.setImageResource(R.mipmap.file);
            }
        }
        title.setText(file.getName());
        return view;
    }
}
