package com.kingsatuo.Console;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.kingsatuo.view.LogView;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public class Utils {
     private WindowManager.LayoutParams paramss;
    public  WindowManager.LayoutParams getWMLayoutParams(){
        return paramss;
    }
    public  void setParams(WindowManager.LayoutParams params){
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // 设置flag

        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.gravity = Gravity.LEFT|Gravity.TOP;
        paramss = params;
    }
    public void log(Activity activity,CharSequence log, String color, LogView logView){
        activity.runOnUiThread(() -> {
            logView.addLog(log,color);
        });
    }
}
