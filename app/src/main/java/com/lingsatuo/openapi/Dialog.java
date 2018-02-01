package com.lingsatuo.openapi;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lingsatuo.callbackapi.ScriptLoading;
import com.lingsatuo.createjs.R;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.Utils;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class Dialog {
    private boolean dismiss ;
    static boolean dialog = true;
    public Dialog(){
       final ScriptLoading scriptLoading = ScriptTool.getInstance().getScriptLoadingListener();
        if (scriptLoading!=null&&dialog){
            ScriptTool.getInstance().getApp().runOnUiThread(() -> {
                scriptLoading.finish();
                dialog = false;
                ScriptTool.getInstance().removeListener();
            });
        }
    }
    public void alert(Activity context , String title, String message){
        if (Utils.runOnUIThread()){
            throw new RuntimeException("阻塞Dialog不能在主线程调用，dialogs.alert(context,mes)");
        }
        dismiss = false;
        context.runOnUiThread(() -> {
                   AlertDialog.Builder ab = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(context.getApplicationContext().getResources().getString(R.string.s_11),null);
            AlertDialog ad = ab.create();
            ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            ad.setOnDismissListener(dialogInterface -> {
                dismiss = true;
            });
            ab.show();
        });
        while(!dismiss)
            try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
                dismiss = true;
            }
    }

    boolean mreturn = false;
    public boolean confirmAlert(Activity context , String title, String message){
        mreturn = false;
        if (Utils.runOnUIThread()){
            throw new RuntimeException("阻塞Dialog不能在主线程调用，dialogs.alert(context,mes)");
        }
        dismiss = false;
        context.runOnUiThread(() -> {
            AlertDialog.Builder ab = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(context.getApplicationContext().getResources().getString(R.string.s_11), (dialogInterface, i) -> {
                        mreturn = true;
                        dismiss = true;
                    })
                    .setNegativeButton(context.getApplicationContext().getResources().getString(R.string.s_12),(dialogInterface, i) -> {
                        mreturn = false;
                        dismiss = true;
                    });
            AlertDialog ad = ab.create();
            ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            ab.show();
        });
        while(!dismiss)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                dismiss = true;
            }
            return mreturn;
    }



    public Dialog(int object){}
    public A LoadingDialog(Activity activity){
        return new A(activity);
    }
    public class B{
        public boolean show = false;
        public CharSequence tiitle = "";
        public CharSequence message = "";
        public boolean canClose = false;
        public boolean canCloseOut = false;
        public Activity activity;
        public AlertDialog ad ;
        public ProgressBar pb ;
        public TextView prob;
        public TextView mess;
        public int max = 100;
        public int now = 0;
        public boolean WindowType = false;
        public int color = Color.WHITE;
    }
    public class A{
        public B b;
        public A(Activity activity){
            b = new B();
            b.activity = activity;
        }
        public A showProgrss(boolean show){
            b.activity.runOnUiThread(() -> {
                b.show = show;
                if (show && b.ad != null) {
                    LinearLayout linearLayout = b.ad.getWindow().findViewById(R.id.llprogress);
                    linearLayout.setVisibility(View.VISIBLE);
                    b.pb = (ProgressBar) linearLayout.getChildAt(0);
                    b.prob = (TextView) linearLayout.getChildAt(1);
                    b.prob.setTextColor(b.color);
                } else if (!show && b.ad != null) {
                    LinearLayout linearLayout = b.ad.getWindow().findViewById(R.id.llprogress);
                    linearLayout.setVisibility(View.GONE);
                    b.pb = (ProgressBar) linearLayout.getChildAt(0);
                    b.prob = (TextView) linearLayout.getChildAt(1);
                    b.prob.setTextColor(b.color);
                }
            });
            return this;
        }
        public A setTile(CharSequence title){
            b.tiitle = title;
            return this;
        }
        public A setMessage(CharSequence message){
            b.message = message;
            if (b.mess!=null) {
                b.activity.runOnUiThread(() -> {
                    b.mess.setText(message);
                });
            }
            return  this;
        }
        public A setWindowType(boolean type){
            b.WindowType = type;
            return this;
        }
        public A canClose(boolean close){
            b.canClose = close;
            return this;
        }
        public A canCloseOut(boolean close){
            b.canCloseOut = close;
            return this;
        }
        public A setMessageColor(int color){
            b.color = color;
            return this;
        }
        public A setMax(int max){
            b.max = max;
            if (b.show) {
                b.activity.runOnUiThread(() -> {
                    b.pb.setMax(max);
                });
            }
            return this;
        }
        public A setProgress(int progress){
            b.now = progress;
            if (b.show) {
                b.activity.runOnUiThread(() -> {
                    b.prob.setText(progress + "/" + b.max);
                    b.pb.setProgress(progress);
                });
            }
            return this;
        }
        public void dismiss(){
            if (b.ad!=null){
                b.activity.runOnUiThread(() -> {
                    b.ad.dismiss();
                });
            }
        }
        public AlertDialog getDialog(){
            return b.ad;
        }
        public A show(){
            if (b.ad==null){
                b.activity.runOnUiThread(() -> {
                    AlertDialog.Builder ab  = new AlertDialog.Builder(b.activity)
                            .setTitle(b.tiitle)
                            .setView(R.layout.load)
                            .setPositiveButton(b.activity.getResources().getString(R.string.s_11),null)
                            .setNegativeButton(b.activity.getResources().getString(R.string.s_12),null)
                            .setCancelable(b.canClose);
                    b.ad = ab.create();
                    if (b.WindowType)
                        b.ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    b.ad.show();
                    showProgrss(b.show);
                    b.mess = b.ad.getWindow().findViewById(R.id.message);
                    b.mess.setText(b.message);
                    b.mess.setTextColor(b.color);
                });
            }
            return this;
        }
    }
}
