package com.lingsatuo.bmob.file;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/18.
 */

public class ObjectFile extends BmobObject {
    private final BmobFile file;
    private String name;
    public ObjectFile(BmobFile file){
        this.file = file;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getFile() {
        return file;
    }
}
