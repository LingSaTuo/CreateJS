package com.lingsatuo.createjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lingsatuo.Dialog.Protocol;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.callbackapi.ToBeContinue;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.error.CreateJSRuntime;
import com.lingsatuo.openapi.App;
import com.lingsatuo.service.Background;
import com.lingsatuo.service.SubService;
import com.lingsatuo.utils.Permission;
import com.lingsatuo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 15176 on 2017/9/16.
 */

public class MAIN extends AppCompatActivity {
    private Activity inthis;
    private static Map<String,List<FunctionCallBACK>> callback = new HashMap();
    private static Map<String,List<ToBeContinue>> tobecontinue = new HashMap();
    static {
        try {
            String str = Utils.readStringFromFile(SubService.path1);
            Utils.saveToFile(SubService.path2,"-0");
            if (str.equals("0")||str.equals("1")){}else {
                Utils.saveToFile(SubService.path1, "-0");
            }
        } catch (CreateJSIOException e) {
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            new Permission(this).CheckPermission();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this,this.getResources().getString(R.string.s_45),1).show();
            } else {
                //TODO do something you need
            }
        }

        Utils.setPath();
            int myPid;
            try {
                Utils.readDateFromApp(this, "protocol");
            } catch (CreateJSIOException e) {
                new Protocol(this).show();
            }
        com.applicationExc.App.init(this);
        if(IsForeground(this) == false)
        {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE) ;
            am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
        }
    }

    @Override
    protected void onResume() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.setSubtitle(this.getResources().getString(R.string.s_35)+"    "+new App(this).getVersionCode());
        if (!Background.isStarted()){
            startService(new Intent(this,Background.class));
            startService(new Intent(this,SubService.class));
        }
        super.onResume();
    }


    public boolean IsForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    public void setInthis(Activity activity){
        this.inthis = activity;
    }
    public Activity getInstance(){
        return this.inthis;
    }
    public A addCallBack(String key,FunctionCallBACK callBack){
        if (!callback.containsKey(key)){
            List<FunctionCallBACK> callBACK = new ArrayList<>();
            callBACK.add(callBack);
            callback.put(key,callBACK);
        }else{
            callback.get(key).add(callBack);
        }
        return new A(this,key);
    }
    public void callBack(String key,Object[] object){
        if (!callback.containsKey(key))return;
        List<FunctionCallBACK> callBACKS = callback.get(key);
        for (FunctionCallBACK callBACK : callBACKS){
            if (callBACK!=null){
                callBACK.T(object);
            }
        }
    }
    public boolean callOnChange(String key,Object[] obj){
        if (!tobecontinue.containsKey(key))return true;
        List<ToBeContinue> callBACKS = tobecontinue.get(key);
        for (ToBeContinue callBACK : callBACKS){
            if (callBACK!=null){
                boolean b = callBACK.T2(obj);
                return b;
            }
            else return true;
        }
        return true;
    }
    public void removeCallBack(String key,FunctionCallBACK callBACK){
        if (!callback.containsKey(key))return;
        List<FunctionCallBACK> callBACKS = callback.get(key);
        for (int a = 0;a<callBACKS.size();a++){
            if (callBACKS.get(a)==callBACK){
                callBACKS.set(a,null);
                break;
            }
        }
    }
    public void clearCallBack(String key){
        if (!callback.containsKey(key))return;
        callback.get(key).clear();
    }
    public A addOnChange(String key,ToBeContinue toBeContinue){
        if (!tobecontinue.containsKey(key)){
            List<ToBeContinue> callBACK = new ArrayList<>();
            callBACK.add(toBeContinue);
            tobecontinue.put(key,callBACK);
        }else{
            tobecontinue.get(key).add(toBeContinue);
        }
        return new A(this,key);
    }
    public void clearOnChange(String key){
        if (!tobecontinue.containsKey(key))return;
        tobecontinue.get(key).clear();
    }

    public void removeOnChange(String key,ToBeContinue callBACK){
        if (!tobecontinue.containsKey(key))return;
        List<ToBeContinue> callBACKS = tobecontinue.get(key);
        for (int a = 0;a<callBACKS.size();a++){
            if (callBACKS.get(a)==callBACK){
                callBACKS.set(a,null);
                break;
            }
        }
    }
    public class A{
        private Activity activity;
        private String TAG;
        public A(Activity activity,String TAG){
            this.activity = activity;
            this.TAG = TAG;
        }
        public void startNewOne(){
            Intent i = new Intent(this.activity,ActivityForJs.class);
            i.putExtra("TAG",this.TAG);
            if (this.TAG==null) {
                CreateJSRuntime cjs = new CreateJSRuntime(this.activity.getApplicationContext().getResources().getString(R.string.s_93));
                cjs.setId(1002);
                throw cjs;
            }
            this.activity.startActivity(i);
        }
        public void start(){
            Intent i = new Intent(this.activity,Sharing.class);
            i.putExtra("TAG",this.TAG);
            if (this.TAG==null) {
                CreateJSRuntime cjs = new CreateJSRuntime(this.activity.getApplicationContext().getResources().getString(R.string.s_93));
                cjs.setId(1002);
                throw cjs;
            }
            this.activity.startActivity(i);
        }
    }
}
