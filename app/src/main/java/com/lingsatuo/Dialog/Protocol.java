package com.lingsatuo.Dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;

/**
 * Created by 15176 on 2017/9/16.
 */

public class Protocol {
    private Activity activity;
    public Protocol(Activity app){
        activity = app;
    }
    public void show(){
        WebView wb = new WebView(activity);
        AlertDialog ad = new AlertDialog.Builder(activity)
                .setTitle("用户协议")
                .setView(wb)
                .setPositiveButton("同意并继续", (dialogInterface, i) -> {
                    try {
                        Utils.saveDateToApp(activity,"protocol","");
                    } catch (CreateJSIOException e) { }
                })
                .setNegativeButton("退出", (dialogInterface, i) -> {
                    activity.finish();
                })
                .show();
        ad.getButton(DialogInterface.BUTTON1).setEnabled(false);
        wb.loadUrl("file:///android_asset/protocol/Protocol.html");
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                ad.getButton(DialogInterface.BUTTON1).setEnabled(true);
                super.onPageFinished(view, url);
            }
        });
                ad.setCancelable(false);
                ad.setCanceledOnTouchOutside(false);

    }
}
