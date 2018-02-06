package com.lingsatuo.bmob.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.callbackapi.FunctionCallBACK;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/11/17.
 */

public class BmobUtils {
    private static String lastTime="2070-12-31 23:59:59";
    public static void get(int page, FunctionCallBACK callback){
        BmobQuery<ObjectData> query = new BmobQuery<>();
        query.order("-createdAt");
        if (page!=0) {
            query.setSkip(page*20+1);
        }else{
            query.setSkip(0);
        }
        query.setLimit(20);
        query(query,callback);
    }
    private static void query(BmobQuery<ObjectData> query, FunctionCallBACK callBACK){
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                callBACK.T(msg.obj);
                super.handleMessage(msg);
            }
        };
        query.findObjects( new FindListener<ObjectData>() {
            @Override
            public void done(List<ObjectData> list, BmobException e) {
                if (callBACK!=null){
                    Message message = new Message();
                    message.what=0;
                    if (e!=null)
                        message.obj = e;
                    else
                        message.obj = list;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
////