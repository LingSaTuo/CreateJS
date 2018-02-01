package com.lingsatuo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kingsatuo.Console.Console;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/2.
 */

public class SubService extends Service {

    private static SubService inthis;
    /*
    * path1 @0  发出显示悬浮窗
    *       @1  发出隐藏悬浮窗
    *       @-1 发出终止运行
    *       @-2 接受运行状态
    *
    * path2 @0 发出已显示
    *       @1 发出已隐藏
    *       @-1 发出终止运行
    *       @-2 发出已新建
    * */
    public static final String path1 = Utils.getSD()+"/.CreateJS/log/run1.log";
    public static final String path2 = Utils.getSD()+"/.CreateJS/log/run2.log";

    @Override
    public void onCreate() {
        if (inthis == null) {
            super.onCreate();
            inthis = this;
            SR();
        } else {
            return;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static boolean isStarted() {
        return inthis == null ? false : true;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    String str = "";
    void SR(){
        Timer timer = new Timer();
        Handler handler = new Handler(message -> {
            try{
                str = Utils.readStringFromFile(path1);
            } catch (CreateJSIOException e) {
            }
            switch (str){
                case "0"://接收到显示悬浮窗信号
                    if (Console.getConsole()==null){
                        if (ScriptTool.getInstance().getApp() ==null){
                            try{
                                Utils.saveToFile(path2,"-3");
                            } catch (CreateJSIOException e) {
                                e.printStackTrace();}
                            return false;
                        }
                        Console console =  new Console();
                        console.setData(ScriptTool.getInstance().getApp());
                        console.showPopupWindow();
                    }else{
                        Console.getConsole().showPopupWindow();
                    }
                    try{
                        Utils.saveToFile(path2,"0");
                    } catch (CreateJSIOException e) {
                        e.printStackTrace();}
                    break;
                case "1"://接收到隐藏悬浮窗信号
                    if (Console.getConsole()!=null)
                        Console.getConsole().hidePopupWindow();
                    try{
                        Utils.saveToFile(path2,"1");
                    } catch (CreateJSIOException e) {
                        e.printStackTrace(); }
                    break;
                case "-1"://接收到终止运行信号
                    try{
                        Utils.saveToFile(path2,"-1");
                        Utils.saveToFile(path1,"-999");
                    } catch (CreateJSIOException e) {
                        e.printStackTrace();
                    }
                    if (ScriptTool.getInstance().getApp() !=null) { try {
                        Utils.deleteFolder(new Files().getSdcardPath()+"/.CreateJS/cache");
                        new java.io.File(new Files().getSdcardPath()+"/.CreateJS/cache").mkdirs();
                    } catch (Exception e) { }
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                    break;

                    default:
                        break;
            }
            return true;
        });
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

}
