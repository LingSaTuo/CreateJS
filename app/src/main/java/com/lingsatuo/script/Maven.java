package com.lingsatuo.script;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/7.
 */
/**
 * 给每个库添加一个tag 并对应库的路径
 * 获取时，调用Maven.getPath(TAG) String;
 * */
public class Maven {
    private static Map<String,String> mavens = new HashMap<>();
    public static String getPath(String TAG){
        return mavens.get(TAG);
    }
    public static void setPath(String key,String path){
        if (!mavens.containsKey(key)){
            mavens.put(key,path);
        }
    }
    public static void clear(){
        mavens = new HashMap<>();
    }
}
