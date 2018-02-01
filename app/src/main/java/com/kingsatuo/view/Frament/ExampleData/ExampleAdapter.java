package com.kingsatuo.view.Frament.ExampleData;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.example.ExampleData;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ExampleAdapter extends BaseExpandableListAdapter {
    Context context;
    LayoutInflater li;
    List<EPGroup> list;
    public ExampleAdapter(Context context){
        this.context = context;
        li = LayoutInflater.from(context);
        setData(new ExampleData().getData(context));
    }

    public void setData(List<EPGroup> list){
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return this.list==null?0:this.list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.list==null?0:this.list.get(groupPosition).getCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list==null?0:this.list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.list==null?0:this.list.get(groupPosition).getCount(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = li.inflate(R.layout.exampleadapter_group,null);
        }else{

        }
        AppCompatTextView item_1 = convertView.findViewById(R.id.example_title);
        item_1.setText(this.list.get(groupPosition).getData().title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = li.inflate(R.layout.exampleadapter_group,null);
        }else{

        }
        AppCompatTextView item_1 = convertView.findViewById(R.id.example_title);
        EPData data = this.list.get(groupPosition).getCount(childPosition);
        item_1.setText(data.title);
        if (data.icon!=0){
         ImageView imageView =  convertView.findViewById(R.id.example_icon);
         imageView.setBackgroundResource(data.icon);
         imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}