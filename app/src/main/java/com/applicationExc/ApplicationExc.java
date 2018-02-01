package com.applicationExc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Process;

import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.error.CreateJSMD5NSA;
import com.lingsatuo.error.CreateJSMD5UEE;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.md5;

/**
 * Created by Administrator on 2017/10/13.
 */

public class ApplicationExc implements Thread.UncaughtExceptionHandler {
    private static ApplicationExc applicationExc;
    private static Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Context context;

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String bug = Utils.getErr(throwable);
        Intent i = new Intent(context, BugActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            Utils.saveToFile(Utils.getSD() + "/.CreateJS/log/Exception.log", bug);
        } catch (CreateJSIOException e) {
            i.putExtra("bug", bug);
        } finally {
            context.startActivity(i);
            android.os.Process.killProcess(Process.myPid());
        }
    }

    public static ApplicationExc getInstance() {
        if (applicationExc == null) {
            applicationExc = new ApplicationExc();
        }
        return applicationExc;
    }

    public void init(Context context) {
        if (uncaughtExceptionHandler != null) return;
        this.context = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
