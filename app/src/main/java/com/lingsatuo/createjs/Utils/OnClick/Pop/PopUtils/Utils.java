package com.lingsatuo.createjs.Utils.OnClick.Pop.PopUtils;


import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.kingsatuo.view.Frament.LocaltionMavenFrament;
import com.lingsatuo.adapter.Maven;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.utils.CheckLibsVersion;
import com.lingsatuo.utils.OnUpdateListener;
import com.lingsatuo.utils.UriUtils;
import com.lingsatuo.utils.ZipUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Utils {
    private Maven maven;
    private Activity activity;
    private Dialog.A da;
    public Utils(Maven maven, Activity activity){
        this.activity = activity;
        this.maven = maven;
    }
    public void run(){

    }
    public void update(){
        new CheckLibsVersion(activity, maven.getTitle().toString(), new OnUpdateListener() {
            @Override
            public void onUpdate(int code, String message, String uri) {
                showdown(maven.getTitle().toString(),message,uri);
            }

            @Override
            public void UnUpdate(int code) {
                Toast.makeText(activity,"无更新",0).show();
            }
        });
    }
    public void backups(){
        showDialog();
        new Thread(() -> {
            try {
                ZipUtils.compress(com.lingsatuo.utils.Utils.getSD()+"/.CreateJS/backups/"+maven.getTitle()+
                        ".zip",maven.getPath());
                da.dismiss();
                activity.runOnUiThread(() -> {
                    Toast.makeText(activity,"备份完成"+com.lingsatuo.utils.Utils.getSD()+"/.CreateJS/backups/"+maven.getTitle()+
                            ".zip",0).show();
                });
            } catch (Exception e) {
                da.setMessage(e.getMessage());
            }
        }).start();
    }
    public void delete(){
        showDialog();
        new Thread(() -> {
            try {
                com.lingsatuo.utils.Utils.deleteFolder(maven.getPath());
                da.dismiss();
                activity.runOnUiThread(() -> {
                    Toast.makeText(activity,"删除完成",0).show();
                    LocaltionMavenFrament.ma.notifyDataSetChanged();
                });
            } catch (Exception e) {
                da.setMessage(e.getMessage());
            }
        }).start();

    }
    private void showDialog(){
        da = new Dialog(0).LoadingDialog(activity);
        da.setMessage(activity.getResources().getString(R.string.s_44))
                .setTile(maven.getTitle())
                .canClose(false)
                .setWindowType(true)
                .showProgrss(false)
                .canCloseOut(false)
                .show();
    }
    private void showdown(String lib,String message,String uri){
        new AlertDialog.Builder(activity)
                .setTitle("可更新的库  " + lib)
                .setMessage(message)
                .setPositiveButton("下载", (dialogInterface, i1) -> {
                    showDialog();
                    new Download(activity, uri, new Files().getSdcardPath() + "/.CreateJS/download/" + lib+".zip", (progress, max, finish, e) -> {
                        da.setMessage(uri).setMax(max*100).setProgress((int)(progress*100));
                        if (e!=null){
                            da.setMessage(e.getMessage());
                        }
                        if (finish) {
                            new Thread(() -> {
                                try {
                                    ZipUtils.loadZip(Environment.getExternalStorageDirectory()+"/.CreateJS/download/"+lib+".zip",Environment.getExternalStorageDirectory()+"/.CreateJS/libs/js_libs/"+(lib.split("_")[0]));
                                    da.setMessage(activity.getResources().getString(R.string.s_31));
                                } catch (Exception e1) {
                                    da.setMessage(e1.getMessage());
                                }
                            }).start();
                        }
                    });
                })
                .setNegativeButton("取消", null).show();
    }
}
