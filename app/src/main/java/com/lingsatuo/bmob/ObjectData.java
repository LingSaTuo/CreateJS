package com.lingsatuo.bmob;

import com.lingsatuo.bmob.file.ObjectFile;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/17.
 */

public class ObjectData extends UserInfo {
    private String title;
    private String str;
    private String time;
    private BmobFile file;
    private String path;
    public void setTitle(String title){
        this.title = title;
    }
    public void setFile(ObjectFile file){
        this.file = file.getFile();
    }
    public BmobFile getFile(){
        return this.file;
    }
    public void setStr(String str){
        this.str = str;
    }
    public String getTitle(){
        return this.title;
    }
    public String getStr(){
        return this.str;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public String getPath(){
        return this.path;
    }
    public void setPath(String path){
        this.path = path;
    }

}
