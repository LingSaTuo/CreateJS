package com.applicationExc;

import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;

/**
 * Created by Administrator on 2017/10/13.
 */

public class BugActivity extends MAIN {
    String bug="?@%#^$&*%(......";
    AppCompatTextView bugview ;
    Button exit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
                try {
                    Utils.saveToFile(Environment.getExternalStorageDirectory()+"/.CreateJS/log/runtimeerr.log",Utils.getErr(throwable));
                } catch (CreateJSIOException e) {}finally {
                    Process.killProcess(Process.myPid());
                }
        });
        bugview = (AppCompatTextView) findViewById(R.id.bug);
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(view -> {
            App.finish();
        });
        new Thread(() -> {
            try {
                bug = Utils.readStringFromFile(Utils.getSD()+"/.CreateJS/log/Exception.log");
            } catch (CreateJSIOException e) {
                bug = BugActivity.this.getIntent().getStringExtra("bug");
            }finally {
                BugActivity.this.runOnUiThread(() -> {
                    bugview.setText(bug);
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.finish();
    }
}
