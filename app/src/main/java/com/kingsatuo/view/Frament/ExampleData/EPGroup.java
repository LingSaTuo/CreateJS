package com.kingsatuo.view.Frament.ExampleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class EPGroup {
    private List<EPData> list = new ArrayList<>();
    private EPData data = new EPData();
    public List<EPData> getPlayer(){
        return this.list;
    }
    public void setName(String name){
        data.title = name;
    }
    public void setIcon(int icon){
        data.icon = icon;
    }
    public EPData getData(){
        return this.data;
    }
    public void add(EPData data){
        this.list.add(data);
    }
    public int getCount(){
        return this.list.size();
    }
    public EPData getCount(int dex){
        return this.list.get(dex);
    }
}
