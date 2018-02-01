package com.lingsatuo.createjs.Utils.ItemClick;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lingsatuo.adapter.LibsMessage;
import com.lingsatuo.adapter.SIListViewAdapter;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.utils.OnLoadingUriString;
import com.lingsatuo.utils.SIUutil;
import com.lingsatuo.utils.ZipUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/27.
 */

public class Maven implements AdapterView.OnItemClickListener {
    private SIListViewAdapter siListViewAdapter;
    private Activity context;
    private int itempage = 0;
    public Maven(SIListViewAdapter siListViewAdapter, Activity context){
        this.siListViewAdapter = siListViewAdapter;
        this.context = context;
        update();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LibsMessage lm = (LibsMessage) siListViewAdapter.getItem(i);
            if (i==siListViewAdapter.getCount()-1){
                if (siListViewAdapter.LOAD==0){
                    itempage++;
                    siListViewAdapter.LOAD=1;
                    siListViewAdapter.notifyDataSetChanged();
                    update();
                }
            }else{
                DownLoad(lm.describe_url);
        }
    }


    private void update(){
        new Thread(() -> {
            String uri = "http://createjs-1253269015.coscd.myqcloud.com/all"+itempage+".json";
            new SIUutil().getString(context, uri, new OnLoadingUriString() {
                @Override
                public void onLoadingSuccess(String str) {
                    try {
                        siListViewAdapter.addDate(str);
                        siListViewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLoadingFaild(Throwable e) {
                    siListViewAdapter.LOAD = 2;
                    siListViewAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    private void DownLoad(String uri){
        new Thread(() -> {
            new SIUutil().getString(context, uri, new OnLoadingUriString() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onLoadingSuccess(String str) {
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        show(jsonObject.getString("title"),jsonObject.getString("size")+"\n"+jsonObject.getString("describe"),jsonObject.getString("downloaduri"));
                    } catch (JSONException e) {
                        Toast.makeText(context,"获取信息失败 "+e.toString(),1).show();
                    }

                }

                @Override
                public void onLoadingFaild(Throwable e) {
                    Toast.makeText(context,"获取信息失败 "+e.toString(),1).show();
                }
            });
        }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void show(String title, String mess, String path){
        AlertDialog ad ;
        ad =  new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(mess)
                .setPositiveButton("下载", null)
                .setNegativeButton("取消",null)
                .setNeutralButton("复制直链", null).show();
        ad.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            ad.dismiss();
            Dialog.A da = show();
                        new Download(context, path, new Files().getSdcardPath() + "/.CreateJS/download/" + title+".zip", (progress, max, finish, e) -> {
                            da.setMessage(path).setMax(max*100).setProgress((int)(progress*100));
                            if (e!=null){
                                da.setMessage(e.getMessage());
                            }
                            if (finish) {
                                new Thread(() -> {
                                    try {
                                        ZipUtils.loadZip(Environment.getExternalStorageDirectory()+"/.CreateJS/download/"+title+".zip",Environment.getExternalStorageDirectory()+"/.CreateJS/libs/js_libs/"+(title.split("_")[0]));
                                        da.setMessage(context.getResources().getString(R.string.s_31));
                                    } catch (Exception e1) {
                                        da.setMessage(e1.getMessage());
                                    }
                                }).start();
                            }
                        });
        });
        ad.getButton(DialogInterface.BUTTON3).setOnClickListener(view -> {
            ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setText(path);
            Toast.makeText(context,"已复制永久直链  "+path,1).show();
        });
    }
    public Dialog.A show(){
        Dialog.A da = new Dialog(0).LoadingDialog(context)
                .canClose(false)
                .canCloseOut(false)
                .setMax(100)
                .showProgrss(true)
                .setTile(context.getString(R.string.s_27))
                .show();
        return da;
    }
}
