package com.lingsatuo.bmob;

import com.lingsatuo.bmob.file.ObjectFile;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/2/6.
 */

public class UserMaven extends ObjectData {
    private String Usertitle;
    private String Userstr;
    private String Usertime;
    private BmobFile Userfile;
    private String Userpath;
    public void setTitle(String title){
        this.Usertitle = title;
    }
    public void setFile(ObjectFile file){
        this.Userfile = file.getFile();
    }
    public BmobFile getFile(){
        return this.Userfile;
    }
    public void setStr(String str){
        this.Userstr = str;
    }
    public String getTitle(){
        return this.Usertitle;
    }
    public String getStr(){
        return this.Userstr;
    }
    public void setTime(String time){
        this.Usertime = time;
    }
    public String getTime(){
        return this.Usertime;
    }
    public String getPath(){
        return this.Userpath;
    }
    public void setPath(String path){
        this.Userpath = path;
    }
}