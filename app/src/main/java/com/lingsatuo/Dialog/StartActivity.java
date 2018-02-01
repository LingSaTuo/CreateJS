package com.lingsatuo.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.lingsatuo.createjs.R;


/**
 * Created by 15176 on 2017/6/28.
 */

public class StartActivity {
    public StartActivity (Activity activity, Intent i , DialogInterface.OnClickListener listener){
        activity.runOnUiThread(() -> {
           AlertDialog.Builder ab =  new AlertDialog.Builder(activity)
                    .setTitle(activity.getResources().getString(R.string.s_9))
                    .setMessage(activity.getResources().getString(R.string.s_10))
                    .setPositiveButton(activity.getResources().getString(R.string.s_11),listener)
                    .setNegativeButton(activity.getResources().getString(R.string.s_12),null);
            AlertDialog ad = ab.create();
            ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            ab.show();
        });
    }
}
