package com.lingsatuo.createjs.Utils;

import android.content.Context;
import android.content.Intent;

import com.lingsatuo.CreateRunApplication.RunJS;
import com.lingsatuo.CreateRunApplication.RunModpkg;
import com.lingsatuo.openapi.Files;

import java.io.File;

/**
 * Created by Administrator on 2017/10/16.
 */

public class DataD {
    private File f;
    static final  int MODE_JS = 0,MODE_MODPAKG = 1;
    public DataD(File f){
        this.f = f;
    }
    private int getLoadingMode(){
        switch (getEnd()){
            case ".js":
                return MODE_JS;
            case ".modpkg":
                return MODE_MODPAKG;
            case ".zip":
                return MODE_MODPAKG;
            default:return MODE_MODPAKG;
        }
    }

    private String getEnd(){
       return new Files().getExtension(f.getName()).toLowerCase();
    }

    public void startActivity(Context context){

        Intent i;
        switch (getLoadingMode()){
            case MODE_JS:
                i = new Intent(context, RunJS.class);
                break;
            case MODE_MODPAKG:
                i = new Intent(context, RunModpkg.class);
                break;
            default:
                i = new Intent(context, RunJS.class);
                break;
        }
        i.putExtra("path", f.getPath());
        context.startActivity(i);
    }
    public static String[] Permission = new String[]{
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_PHONE_STATE",
            "android.permission.CALL_PHONE",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.USE_SIP",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "com.android.voicemail.permission.ADD_VOICEMAIL",
            "android.permission.READ_CALENDAR",
            "android.permission.WRITE_CALENDAR",
            "android.permission.CAMERA",
            "android.permission.BODY_SENSORS",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.SEND_SMS",
            "android.permission.READ_CELL_BROADCASTS"};

}
