package com.lingsatuo.adapter;

import android.content.Context;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applicationExc.App;
import com.lingsatuo.createjs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/14.
 */

public class SIListViewAdapter extends BaseAdapter{
    private Context context;
    private List<LibsMessage> dates ;
    public int LOAD = 0;
    private List<View> items;
    private LayoutInflater llf;
    public SIListViewAdapter(Context context){
        dates = new ArrayList<>();
        items = new ArrayList<>();
        this.context = context;
        if (context==null)
            android.os.Process.killProcess(Process.myPid());
        llf = LayoutInflater.from(context);
        dates.add(null);
        items.add(null);
    }
    public void addDate(String str) throws JSONException {
        JSONObject jsonObject = new JSONObject(str);
        for (int a = 0 ; a < 20 ;a ++){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(a + "");
                LibsMessage lm = new LibsMessage();
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                lm.name = jsonObject1.getString("name");
                lm.author = jsonObject1.getString("author");
                lm.describe_url = jsonObject1.getString("describe_url");
                if (a==0){
                    dates.set(dates.size()-1,lm);
                    items.set(items.size()-1,null);
                }else {
                    dates.add(lm);
                    items.add(null);
                }
            }catch (Exception e){
                break;
            }
        }
        dates.add(null);
        items.add(null);
    }
    @Override
    public int getCount() {
        return dates==null?0:dates.size();
    }

    @Override
    public Object getItem(int i) {
        return dates.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LibsMessage lm = dates.get(i);
        View contentView;
        if (lm==null) {
            view = llf.inflate(R.layout.si_last,null);
            TextView tv = view.findViewById(R.id.loading);
            switch (LOAD){
                case 0:
                    tv.setText("点击加载更多");
                    break;
                case 1:
                    tv.setText("加载中。。。");
                    break;
                case 2:
                    tv.setText("没有更多了。");
                    break;
            }
        }else{
            contentView = items.get(i);
            if (contentView!=null){
                return contentView;
            }
            view = llf.inflate(R.layout.si_item,null);
            TextView title = view.findViewById(R.id.title);
            title.setText(lm.name);
            TextView describe = view.findViewById(R.id.author);
            describe.setText(lm.author);
            items.set(i,view);
        }
        return view;
    }
}
