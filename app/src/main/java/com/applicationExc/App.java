package com.applicationExc;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.lingsatuo.callbackapi.Function;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.error.CreateJSMD5NSA;
import com.lingsatuo.error.CreateJSMD5UEE;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.service.SubService;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.md5;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/10/13.
 */

public final class App extends MultiDexApplication {
    private static List<AppCompatActivity> list = new ArrayList<>();
    static Function listener;


    @Override
    protected final void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public final void onCreate() {
        super.onCreate();aVoid();
        ApplicationExc.getInstance().init(this);
        Bmob.initialize(this, app_is_cjs.getMy_bmob_id());
    }
    public static String my_id_is;
    public static void init(AppCompatActivity compatActivity){
        list.add(compatActivity);
    }
    public static void finish(){
        for (AppCompatActivity a:list){
            if (a!=null){
                a.finish();
            }
        }
    }
    private void aVoid(){
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            StringBuilder sb = new StringBuilder();
            for (Signature signature:signatures){
                sb.append(signature.toCharsString());
            }
            if (App.my_id_is==null)
                App.my_id_is = md5.CreateMD5(sb.toString());
        } catch (PackageManager.NameNotFoundException e) {
        } catch (CreateJSMD5NSA createJSMD5NSA) {
        } catch (CreateJSMD5UEE createJSMD5UEE) {
        }
    }
    static String str ;
    static Handler handler = new Handler((message -> {
        switch (message.what){
            case -1:
                android.os.Process.killProcess(Process.myPid());
                break;

            case 0:
                if (listener!=null)
                listener.T("true");
                break;
                default:
                    if (listener!=null){
                        listener.T("There has been a mistake. Please try again! id :  "+message.what);
                    }
                    break;
        }
        return true;
    }));
    public final static void exitApp(Function lis){
        ScriptTool.getInstance().stopAll();
        listener = lis;
        new Thread(() -> {
            try {
                Thread.sleep(600);
                try {
                    str = Utils.readStringFromFile(SubService.path2);
                } catch (CreateJSIOException e) {
                }
                switch (str){
                    case "-1":
                        handler.sendEmptyMessage(-1);
                     break;
                    default:
                        handler.sendEmptyMessage(-200);
                        break;
                }
            } catch (InterruptedException e) {
                handler.sendEmptyMessage(-300);
            }
        }).start();
    }
    public static void exitScript(Function lis){
        try {
            Utils.saveToFile(SubService.path1, "-1");
        } catch (CreateJSIOException e) {
        }
        listener = lis;
        new Thread(() -> {
            try {
                Thread.sleep(600);
                try {
                    str = Utils.readStringFromFile(SubService.path2);
                } catch (CreateJSIOException e) {
                }
                switch (str){
                    case "-1":
                        handler.sendEmptyMessage(0);
                        break;
                    default:
                        handler.sendEmptyMessage(-100);
                        break;
                }
            } catch (InterruptedException e) {
                handler.sendEmptyMessage(-310);
            }
        }).start();
    }
}
