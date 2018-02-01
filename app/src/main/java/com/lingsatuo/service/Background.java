package com.lingsatuo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class Background extends Service {
    private static Background inthis;


    @Override
    public void onCreate() {
        if (inthis == null) {
            super.onCreate();
            inthis = this;
        } else {
            return;
        }
    }

    public static boolean isStarted() {
        return inthis == null ? false : true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
