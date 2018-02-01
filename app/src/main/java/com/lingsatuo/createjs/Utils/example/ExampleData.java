package com.lingsatuo.createjs.Utils.example;

import android.content.Context;

import com.kingsatuo.view.Frament.ExampleData.EPData;
import com.kingsatuo.view.Frament.ExampleData.EPGroup;
import com.lingsatuo.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ExampleData {
    private Context context;
    public List<EPGroup> getData(Context context){
        this.context = context;
        List<EPGroup> list = new ArrayList<>();
        String[] _list = getList("example");
        for (String title:_list){
            EPGroup epGroup = new EPGroup();
            epGroup.setName(title);
            String[] __list = getList("example/"+title);
            for (String item:__list ){
                EPData epData = new EPData();
                epData.title = Utils.getFileNameNoEx(item);
                epData.path="file:///android_asset/example/"+title+"/"+item;
                epGroup.add(epData);
                System.out.println(item);//-------------------------------------------------
            }
            list.add(epGroup);
        }
        return list;
    }
    private String[] getList(String path){
        try {
            return context.getAssets().list(path);
        } catch (IOException e) {
            return new String[]{};
        }
    }
}
