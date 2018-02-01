package com.lingsatuo.createjs.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.App;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.utils.SIUutil;
import com.lingsatuo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/23.
 */

public class Update {
    private Dialog.A da;

    public Update(Activity activity, View view) {
        check(activity, view);
    }

    private void check(Activity activity, View view) {
        new SIUutil().getString(activity, "http://createjs-1253269015.coscd.myqcloud.com/update/update.json", p -> {
            if (p[1].equals("1")) {
                try {
                    JSONObject jsonObject = new JSONObject(p[0]);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    String uri = jsonObject.getString("downloaduri");
                    int now = new App(activity).getVersionCode();
                    if (code > now) {
                        Snackbar.make(view, R.string.s_105, Snackbar.LENGTH_LONG).setAction(R.string.s_106, view1 -> {
                            down(activity, message, uri);
                        }).show();
                    } else {
                        Snackbar.make(view, R.string.s_103, Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Snackbar.make(view, R.string.s_103, Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(view, R.string.s_103, Snackbar.LENGTH_LONG).show();
            }
        }, 1);
    }

    private void down(Activity activity, String message, String uri) {
        da = new Dialog(0).LoadingDialog(activity)
                .setMessage(message)
                .setTile(activity.getApplication()
                        .getString(R.string.s_106))
                .setMessageColor(Color.WHITE)
                .canCloseOut(false)
                .canClose(false)
                .show();
        da.getDialog().getButton(DialogInterface.BUTTON2).setText( activity.getApplication()
                .getString(R.string.s_12));
        da.getDialog().getButton(DialogInterface.BUTTON2).setOnClickListener(view -> da.dismiss());


        da.getDialog().getButton(DialogInterface.BUTTON1).setText( activity.getApplication()
                .getString(R.string.s_106));
        da.getDialog().getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            da.getDialog().getButton(DialogInterface.BUTTON1).setEnabled(false);
            da.getDialog().getButton(DialogInterface.BUTTON2).setEnabled(false);
            da.showProgrss(true);
            download(activity,uri);
        });
    }

    @SuppressLint("SetTextI18n")
    private void download(Activity activity, String uri) {
        final String save = Utils.getSD() + "/.CreateJS/download/update.apk";
        new Download(activity, uri,save , (progress, max, finish, e) -> {
            if (e != null) {
                da.setMessage(e.getMessage());
            } else {
                da.setMax(max);
                da.setProgress((int) progress);
                da.getDialog().getButton(DialogInterface.BUTTON1).setText(((int)(progress/max))*100+"%");
            }
            if (finish) {
                da.getDialog().getButton(DialogInterface.BUTTON1).setEnabled(true);
                da.getDialog().getButton(DialogInterface.BUTTON2).setEnabled(true);
                da.showProgrss(false);

                da.getDialog().getButton(DialogInterface.BUTTON1).setText( activity.getApplication()
                        .getString(R.string.s_107));
                da.getDialog().getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
                    App.installApk(activity.getApplicationContext(),save);
                });
            }
        });
    }
}
