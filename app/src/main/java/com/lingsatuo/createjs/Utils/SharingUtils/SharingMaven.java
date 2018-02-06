package com.lingsatuo.createjs.Utils.SharingUtils;

import android.os.Process;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lingsatuo.Dialog.FileSelector;
import com.lingsatuo.bmob.DataInfo;
import com.lingsatuo.bmob.UserMaven;
import com.lingsatuo.bmob.file.ObjectFile;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.openapi.App;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Files;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/2/6.
 */

public class SharingMaven implements View.OnClickListener {
    private MAIN activity;
    TextView textView;
    EditText title, introduction;
    Dialog.A dialog;
    public SharingMaven(MAIN activity){
        this.activity = activity;
    }
    public void startActivity(){
        this.activity.clearCallBack("Maven");
        this.activity.clearOnChange("Maven");
        this.activity.addCallBack("Maven", new SharingMaven.A()).start();
    }

    @Override
    public void onClick(View view) {

        if(activity==null){
            android.os.Process.killProcess(Process.myPid());
        }
        switch (view.getId()) {
            case R.id.select:
                new FileSelector(new File(new Files().getSD()), activity, object -> {
                    File file = (File) object;
                    textView.setText(file.getPath());
                },obj -> {
                    if (((File)obj).isFile()) {
                        if (!new Files().getExtension(((File) obj).getName()).equals(".zip".toLowerCase()))
                            return false;
                    }
                    return true;
                });
                break;
            case R.id.up:
                dialog = new Dialog(1).LoadingDialog(activity);
                dialog.setMessage(activity.getResources().getString(R.string.s_69))
                        .canClose(false)
                        .canCloseOut(false)
                        .showProgrss(false)
                        .setTile(activity.getResources().getString(R.string.s_57))
                        .setWindowType(true)
                        .show();
                String t = title.getText().toString();
                t = t.replaceAll(" ", "");
                if (t == "") {
                    dialog.dismiss();
                    Snackbar.make(view, R.string.s_67, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String mes = introduction.getText().toString();
                BmobFile bmobFile = new BmobFile(new File(textView.getText().toString()));
                ObjectFile file = new ObjectFile(bmobFile);
                UserMaven objectData = new UserMaven();
                objectData.setFile(file);
                objectData.setTitle(t);
                objectData.setStr(mes);
                new DataInfo<UserMaven>().createNewOne(objectData).upFile(p -> {
                    if (!p[2].equals("")) {
                        Snackbar.make(view, p[2], Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, activity.getResources().getString(R.string.s_68), Snackbar.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
                break;
        }
    }


    public class A implements FunctionCallBACK {

        @Override
        public void T(Object object) {
            Object[] objects = (Object[]) object;
            String TAG = (String) objects[0];
            switch (TAG){
                case "onCreate":
                    activity = (MAIN) objects[1];
                    setContentView(activity, R.layout.sharing_maven);
                    break;
                case "onResume":
                    activity.getSupportActionBar().setTitle(R.string.s_57);
                    activity.getSupportActionBar().setSubtitle(activity.getResources().getString(R.string.s_35)+"    "+new App(activity).getVersionCode());
                    break;
            }
        }
    }

    private void setContentView(MAIN activity,int Rid){
        activity.setContentView(Rid);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Button button = activity.findViewById(R.id.select);
        button.setOnClickListener(this);
        textView = activity.findViewById(R.id.path);
        activity.findViewById(R.id.up).setOnClickListener(this);
        title = activity.findViewById(R.id.title);
        introduction = activity.findViewById(R.id.messgae);
    }
}
