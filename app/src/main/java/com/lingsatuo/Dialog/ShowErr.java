package com.lingsatuo.Dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.lingsatuo.createjs.R;


/**
 * Created by 15176 on 2017/6/28.
 */

public class ShowErr {
    public ShowErr(Activity activity,String err){
        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.s_15))
                .setMessage(err)
                .setPositiveButton(activity.getResources().getString(R.string.s_16),null)
                .show()
                .setCanceledOnTouchOutside(false);
    }
    public ShowErr(Context activity, String err){
       AlertDialog.Builder ab =  new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.s_15))
                .setMessage(err)
                .setPositiveButton(activity.getResources().getString(R.string.s_16),null);
        AlertDialog ad = ab.create();
        ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                ad.show();
                ad.setCanceledOnTouchOutside(false);
    }
}
