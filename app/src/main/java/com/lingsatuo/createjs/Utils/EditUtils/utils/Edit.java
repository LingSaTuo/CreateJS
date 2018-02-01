package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.kingsatuo.view.JSEditor;
import com.lingsatuo.createjs.R;
import com.myopicmobile.textwarrior.common.To;

import java.io.File;

/**
 * Created by Administrator on 2017/12/7.
 */

public class Edit implements To {
    private JSEditor jsEditor;
    private Activity activity;
    private File file;
    private View view;
    public Edit(JSEditor jsEditor, Activity activity, File file, View view){
        this.activity = activity;
        this.file = file;
        this.jsEditor = jsEditor;
        this.view = view;
    }
    @Override
    public boolean T(Object obj) {
        String s = (String) obj;
        if(s.equals("fail")){
            activity.runOnUiThread(() -> {
                Snackbar.make(view,this.activity.getApplicationContext().getString(R.string.s_94),Snackbar.LENGTH_LONG)
                        .setAction(this.activity.getApplicationContext().getString(R.string.s_95), view1 -> {
                    jsEditor.setEdited(false);
                    jsEditor.open(file.getPath(),this);
                }).show();
            });
        }
        return false;
    }
}
