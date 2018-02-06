package com.lingsatuo.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingsatuo.adapter.FileSelectorAdapter;
import com.lingsatuo.adapter.utils.FileSelectorUtils;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.callbackapi.ToBeContinue;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.Files;

import java.io.File;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FileSelector implements AdapterView.OnItemClickListener {
    private final Context context;
    private AlertDialog.Builder ab;
    private AlertDialog ad;
    private FunctionCallBACK callBACK;
    private ToBeContinue callBACK1;
    private File mfile;
    private FileSelectorAdapter adapter;
    public FileSelector(File mfile, Context context, FunctionCallBACK callBACK){
        this(mfile,context,callBACK,null);
    }

    public FileSelector(File mfile, Context context, FunctionCallBACK callBACK,ToBeContinue callBACK1){
        this.mfile = mfile;
        this.context = context;
        this.callBACK = callBACK;
        this.callBACK1 = callBACK1;
        show();
    }
    private void show(){
        ab = new AlertDialog.Builder(context)
                .setView(R.layout.file_selector)
                .setPositiveButton(context.getResources().getString(R.string.s_11), (dialogInterface, i) -> {

                })
                .setNegativeButton(context.getResources().getString(R.string.s_12),null)
                .setNeutralButton(context.getResources().getString(R.string.s_66),null);
        ad = ab.create();
        //ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        ad.show();
        ad.getButton(DialogInterface.BUTTON3).setOnClickListener(view -> {
            if (mfile==null)mfile=new File(new Files().getSdcardPath());
            mfile = mfile.getParentFile();
            adapter.setData(FileSelectorUtils.getData(mfile,name -> {
                if (callBACK1==null)return true;
                return callBACK1.T2(new File(name));
            }));
            adapter.notifyDataSetChanged();
        });
        ListView listView = ad.getWindow().findViewById(R.id.file_selector);
        listView.setOnItemClickListener(this);
        adapter = new FileSelectorAdapter(context);
        adapter.setData(FileSelectorUtils.getData(mfile, name -> {
            if (callBACK1==null)return true;
            return callBACK1.T2(new File(name));
        }));
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File file = (File) adapter.getItem(i);
        if (!file.isFile()){
            adapter.setData(FileSelectorUtils.getData(file,name -> {
                if (callBACK1==null)return true;
                return callBACK1.T2(new File(name));
            }));
            adapter.notifyDataSetChanged();
            mfile = file;
            return;
        }
        if (callBACK!=null){
            ad.dismiss();
            callBACK.T(file);
        }
    }
}
