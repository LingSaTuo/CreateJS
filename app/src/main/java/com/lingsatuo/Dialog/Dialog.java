package com.lingsatuo.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.lingsatuo.openapi.Download;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Dialog {
    public  Dialog(Activity activity, String title, String message, String button,DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(button,listener)
                .setNegativeButton("取消",null)
                .show();
    }
}
