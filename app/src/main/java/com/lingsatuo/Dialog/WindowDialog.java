package com.lingsatuo.Dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.applicationExc.App;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.service.SubService;
import com.lingsatuo.utils.Utils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Administrator on 2017/11/2.
 */

public class WindowDialog implements View.OnClickListener {
    private Context context;
    private AlertDialog alertDialog;
    public WindowDialog(Context context) {
        this.context = context;
    }

    public WindowDialog create() {
        AlertDialog.Builder ab = new AlertDialog.Builder(context)
                .setView(R.layout.window_layout);
        alertDialog = ab.create();
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return this;
    }

    public Window getWindow() {
        return alertDialog.getWindow();
    }

    public AlertDialog show() {
        alertDialog.show();
        alertDialog.getWindow().findViewById(R.id.clear).setOnClickListener(this);
        alertDialog.getWindow().findViewById(R.id.show_log).setOnClickListener(this);
        alertDialog.getWindow().findViewById(R.id.dismiss_log).setOnClickListener(this);
        alertDialog.getWindow().findViewById(R.id.exit).setOnClickListener(this);
        try {
            str = Utils.readStringFromFile(SubService.path2);
        } catch (CreateJSIOException e) {
        }
        switch (str){
            case "0":
                getWindow().findViewById(R.id.show_log).setVisibility(View.GONE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.VISIBLE);
                break;
            case "1":
                getWindow().findViewById(R.id.show_log).setVisibility(View.VISIBLE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.GONE);
                break;
            case "-3":
                getWindow().findViewById(R.id.show_log).setVisibility(View.GONE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.VISIBLE);
                break;
        }
        return alertDialog;
    }
    private String getClip() {
        ClipboardManager myClipboard = (ClipboardManager) this.context.getSystemService(CLIPBOARD_SERVICE);

        ClipData abc = myClipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String text = item.getText().toString();
        if (text == null) text = "";
        return text;
    }

    String str = "";
    Handler handler = new Handler(message -> {
        if (getWindow()==null)return false;
        if (getWindow().findViewById(R.id.show_log)==null)return false;
        switch (message.what){
            case 0:
                getWindow().findViewById(R.id.show_log).setVisibility(View.GONE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.VISIBLE);
                break;
            case 1:
                getWindow().findViewById(R.id.show_log).setVisibility(View.VISIBLE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.GONE);
                break;
            case -1:
                Toast.makeText(context,context.getResources().getString(R.string.na_1),1).show();
                getWindow().findViewById(R.id.show_log).setVisibility(View.VISIBLE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.GONE);
                try {
                    context.startService(new Intent(context, SubService.class));
                }catch (Exception e){
                    Toast.makeText(context,context.getResources().getString(R.string.na_2),1).show();
                }
                break;
            case -3:
                Toast.makeText(context,context.getResources().getString(R.string.na_3),1).show();
                getWindow().findViewById(R.id.show_log).setVisibility(View.GONE);
                getWindow().findViewById(R.id.dismiss_log).setVisibility(View.VISIBLE);
                break;

                case -30000:
                    Toast.makeText(context,context.getResources().getString(R.string.na_2)+"  id - "+str,1).show();
                    getWindow().findViewById(R.id.show_log).setVisibility(View.VISIBLE);
                    getWindow().findViewById(R.id.dismiss_log).setVisibility(View.GONE);
                    break;
        }
        return true;
    });
    @Override
    public void onClick(View view) {
        try {
            str = Utils.readStringFromFile(SubService.path2);
        } catch (CreateJSIOException e) {
        }
        switch (view.getId()) {
            case R.id.clear:
                ScriptTool.getInstance().stopAll();
                break;
            case R.id.show_log:
                try {
                    Utils.saveToFile(SubService.path1, "0");
                    Utils.saveToFile(SubService.path2,"1");
                } catch (CreateJSIOException e) {
                }
                break;
            case R.id.dismiss_log:
                try {
                    Utils.saveToFile(SubService.path1, "1");
                    Utils.saveToFile(SubService.path2,"0");
                } catch (CreateJSIOException e) {
                }
                break;

            case R.id.exit:
                App.exitApp((back)->{
                    if (!back.equals("true"))
                        Toast.makeText(context,back[0],1).show();
                });
                break;
        }
        new Thread(() -> {
            try {
                Thread.sleep(600);
                try {
                    str = Utils.readStringFromFile(SubService.path2);
                } catch (CreateJSIOException e) {
                }
                switch (str){
                    case "0":
                        handler.sendEmptyMessage(0);
                        break;
                    case "1":
                        handler.sendEmptyMessage(1);
                        break;
                    case "-1":
                        handler.sendEmptyMessage(-1);
                        break;
                    case "-3":
                        handler.sendEmptyMessage(-3);
                        break;
                    default:
                        handler.sendEmptyMessage(-30000);
                        break;
                }
            } catch (InterruptedException e) {

            }
        }).start();
    }
}
