package com.lingsatuo.Dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;


import com.lingsatuo.createjs.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 15176 on 2017/6/29.
 */

public class Libs_Message {
    public Libs_Message(Activity activity, StringBuilder message){
        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.s_20))
                .setMessage(message.toString())
                .setPositiveButton(activity.getResources().getString(R.string.s_16),null)
                .show()
                .setCanceledOnTouchOutside(false);
    }
}
