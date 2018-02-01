package com.lingsatuo.createjs.Utils.SharingReader;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kingsatuo.view.CodeView;
import com.lingsatuo.bmob.ObjectData;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.createjs.LoadingActivity;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;

import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;

/**
 * Created by Administrator on 2017/11/18.
 */

public class SharingReaderUtils implements View.OnClickListener {
    private MAIN activity;
    private ObjectData file;
    private CollapsingToolbarLayout ctl;
    private TextView textView;
    private CodeView codeView;

    private String string;
    public SharingReaderUtils(MAIN activity, ObjectData file){
        this.activity = activity;
        this.file = file;
    }
    public void startActivity(){
        this.activity.clearCallBack("SharingReader");
        this.activity.clearOnChange("SharingReader");
        this.activity.addCallBack("SharingReader", new A()).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                if (file==null||file.getPath()==null)return;
                Intent i = new Intent(activity,LoadingActivity.class);
                i.putExtra("path",file.getPath());
                activity.startActivity(i);
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
                    setContentView(activity, R.layout.sharing_reader);
                    break;
                case "onResume":
                    ctl.setTitle(file.getTitle());
                    textView.setText(file.getStr());
                    break;
            }
        }
    }

    private void setContentView(MAIN activity,int Rid){
        activity.setContentView(Rid);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.findViewById(R.id.fab).setOnClickListener(this);
        ctl = activity.findViewById(R.id.toolbar_layout);
        textView = activity.findViewById(R.id.mes);
        codeView = new CodeView();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.codeview,codeView)
                .commit();
        new Thread(() -> {
            try {
                    string = Utils.readStringFromFile(file.getPath());
            } catch (CreateJSIOException e) {
                string = e.getMessage();
            }
            activity.runOnUiThread(()->{
                io.github.kbiakov.codeview.CodeView mCodeView = codeView.getCodeView();
                mCodeView.updateOptions(options -> {
                    options.withFont(Font.Consolas)
                            .withCode(string)
                            .withTheme(ColorTheme.MONOKAI)
                            .withLanguage("js");
                    return null;
                });
            });
        }).start();
    }
}
