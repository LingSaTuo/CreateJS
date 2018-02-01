package com.kingsatuo.Console;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lingsatuo.Dialog.ShowErr;
import com.lingsatuo.Dialog.WindowDialog;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.*;
import com.lingsatuo.window.Data;
import com.lingsatuo.window.MWindowManager;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/2.
 */

public class Window implements View.OnClickListener {
    private static MWindowManager mWindowManager;

    File file = new File(ScriptTool.LogErrPath);
    private boolean isWraning = false;
    public Window(MWindowManager mWindowManager) {
        this.mWindowManager = mWindowManager;
    }

    public void setData() {
        Data data = new Data();
        this.mWindowManager.setData(data);
        ImageView iv = new ImageView(this.mWindowManager.getContext());
        data.VIEW = iv;
        data.VIEW.setBackgroundResource(R.mipmap.window);
        mWindowManager.getData().VIEW.setId(R.id.mwindow);
        mWindowManager.getData().VIEW.setOnClickListener(this);
        this.mWindowManager.createLayoutParams();
        this.mWindowManager.getLayoutParams().width = com.lingsatuo.utils.Utils.dip2px(mWindowManager.getContext(), 26);
        this.mWindowManager.getLayoutParams().height = com.lingsatuo.utils.Utils.dip2px(mWindowManager.getContext(), 26);
        this.mWindowManager.show();
        listener();
    }

    public static MWindowManager getMWindowManager() {
        return mWindowManager;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mwindow:
                if (!isWraning) {
                    WindowDialog wd = new WindowDialog(mWindowManager.getContext());
                    wd.create();
                    wd.show();
                }else{
                    try {
                        new ShowErr(mWindowManager.getContext(), com.lingsatuo.utils.Utils.readStringFromFile(ScriptTool.LogErrPath));
                    } catch (CreateJSIOException e) {
                    } finally {
                       if (file.isFile()&&file.exists()){
                           file.delete();
                       }
                    }
                }
                break;
        }
    }


    private void listener(){
        Timer timer = new Timer();
        Handler handler = new Handler(message -> {
            if (!file.isFile()||!file.exists()) {
                if (!isWraning)return true;
                Window.getMWindowManager().getData().VIEW.setBackgroundResource(R.mipmap.window);
                setWraning(false);
                return true;
            }
            else{
                if (isWraning)return true;
                Toast.makeText(mWindowManager.getContext(),mWindowManager.getContext().getResources().getString(R.string.s_54),1).show();
                Window.getMWindowManager().getData().VIEW.setBackgroundResource(R.mipmap.warning);
                setWraning(true);
            }
            return true;
        });
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (Window.getMWindowManager()!=null)
                    handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }
    protected boolean isWraning(){
        return this.isWraning;
    }
    protected void setWraning(boolean wraning){
        this.isWraning = wraning;
    }
}
