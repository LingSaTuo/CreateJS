package com.lingsatuo.error;

/**
 * Created by Administrator on 2017/12/7.
 */

public class CreateJSRuntime extends RuntimeException {
    private int id = 0;
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public CreateJSRuntime(String mess){
        super(mess);
    }
}
