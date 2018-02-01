package com.lingsatuo.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.lingsatuo.createjs.R;


/**
 * Created by 15176 on 2017/7/1.
 */

public class Nl {
    public Nl(Activity activity, String message){
        new AlertDialog.Builder(activity)
                .setTitle("构建时错误")
                .setMessage("循环的依赖"+"\n"+message)
                .setPositiveButton(activity.getResources().getString(R.string.s_16),null)
                .show()
                .setCanceledOnTouchOutside(false);
    }

    public Nl(Activity activity, Exception e){
        new AlertDialog.Builder(activity)
                .setTitle("构建时错误")
                .setMessage("可能需要重新打开CreateJS"+"\n"+e.toString())
                .setPositiveButton(activity.getResources().getString(R.string.s_16),null)
                .show()
                .setCanceledOnTouchOutside(false);
    }
    public Nl(Activity activity, String message,int a){
        new AlertDialog.Builder(activity)
                .setTitle("构建时错误")
                .setMessage("重名的库 "+"\n"+message)
                .setPositiveButton(activity.getResources().getString(R.string.s_33), (dialogInterface, i) -> {
                    activity.finish();
                })
                .show()
                .setCanceledOnTouchOutside(false);
    }
}
