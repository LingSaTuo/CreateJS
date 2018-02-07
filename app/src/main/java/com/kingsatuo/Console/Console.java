package com.kingsatuo.Console;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.kingsatuo.view.LogView;
import com.lingsatuo.createjs.R;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public class Console extends Utils{
    private  final String LOG_TAG = "WindowUtils";
    private  View mView = null;
    private  WindowManager mWindowManager = null;
    private  Context mContext = null;
    private Activity activity;
    private View view;
    private  Boolean isShown = false;
    private LogView lv;

    public void setData(Activity activity){

        this.activity = activity;
        if (isShown) {
            return;
        }
        Context context = activity;
        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = (int) (mWindowManager.getDefaultDisplay().getWidth()*0.6);
        params.height = (int) (mWindowManager.getDefaultDisplay().getHeight()*0.3);
        setParams(params);
        FPSView = this;
        view = mView;
    }
    public void showPopupWindow() {
        activity.runOnUiThread(() -> {
            if (mWindowManager!=null&&!isShown)
            mWindowManager.addView(mView, getWMLayoutParams());
            isShown = true;
        });
    }


    private static Console FPSView;
    private  View setUpView(Context context) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.console, null);
        lv = view.findViewById(R.id.logroot);
        return view;
    }

    /**
     * 隐藏弹出框
     */
    public  void hidePopupWindow() {
        activity.runOnUiThread(() -> {
            if (isShown && null != mView) {
                mWindowManager.removeView(mView);
                isShown = false;
            }
        });
    }
    public static Console getConsole(){
        return FPSView;
    }
    private LogView getView(){
        return lv;
    }
    public void log(CharSequence log,String color){
        super.log(activity,log,color,lv);
    }
}
