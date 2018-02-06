package com.lingsatuo.openapi;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.webkit.MimeTypeMap;

import com.lingsatuo.service.CreateJSAccessibilityService;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class App {
    private Activity activity;

    public App(Activity activity) {
        this.activity = activity;
    }

    public void viewFile(String path) {
        if (path == null) {
            throw new NullPointerException("一个null的路径不能用于该方法");
        }
        path = "file://" + path;
        String ext = new Files().getExtension(path);
        this.activity.startActivity(new Intent("android.intent.action.VIEW").setDataAndType(Uri.parse(path), TextUtils.isEmpty(ext) ? "*/*" : MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)).addFlags(Intent.FLAG_RECEIVER_FOREGROUND));

    }

    public void uninstall(String packageName) {
        activity.startActivity(new Intent("android.intent.action.DELETE",
                Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_RECEIVER_FOREGROUND));
    }

    public void editFile(String path) {
        if (path == null) {
            throw new NullPointerException("一个null的路径不能用于该方法");
        }
        path = "file://" + path;
        String ext = new Files().getExtension(path);
        this.activity.startActivity(new Intent("android.intent.action.EDIT")
                .setDataAndType(Uri.parse(path),
                        TextUtils.isEmpty(ext) ? "*/*" : MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext))
                .addFlags(Intent.FLAG_RECEIVER_FOREGROUND));
    }

    public void openUrl(String url) {
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        this.activity.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(url)).addFlags(Intent.FLAG_RECEIVER_FOREGROUND));
    }

    public String getAppName(String packageName) {
        String str = null;
        PackageManager packageManager = this.activity.getPackageManager();
        try {
            CharSequence appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0));
            if (appName != null) {
                str = appName.toString();
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return str;
    }

    public String getPackageName(String appName) {
        PackageManager packageManager = this.activity.getPackageManager();
        for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(PackageManager.GET_META_DATA)) {
            if (packageManager.getApplicationLabel(applicationInfo).toString().equals(appName)) {
                return applicationInfo.processName;
            }
        }
        return null;
    }

    public boolean launchPackage(String packageName) {
        try {
            this.activity.startActivity(this.activity.getPackageManager().getLaunchIntentForPackage(packageName).addFlags(Intent.FLAG_RECEIVER_FOREGROUND));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean launchApp(String appName) {
        return launchPackage(getPackageName(appName));
    }

    public boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    public String[] getAllHomes() {
        List<String> name = getHomes();
        String[] names = new String[name.size()];
        for (int a = 0; a < name.size(); a++) {
            names[a] = name.get(a);
        }
        return names;
    }

    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = activity.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    public CharSequence getCurrentPkgName() {
        AccessibilityEvent event =  CreateJSAccessibilityService.accessibilityEvent;
        CharSequence name = event.getPackageName();
        return name==null?"null":name;
    }

    public String getCurrentPkgName(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List appList = am.getRunningAppProcesses();
        List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo app : processes) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Integer state = null;
                try {
                    state = field.getInt(app);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (state != null && state == START_TASK_TO_FRONT) {
                    currentInfo = app;
                    break;
                }
            }
        }
        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }

    public int getVersionCode() {
        int versionCode = 0;
        try {
// 获取软件版本号，对应AndroidManifest.xml下的versionCode
            versionCode = activity.getPackageManager().
                    getPackageInfo("com.lingsatuo.createjs", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void installApk(Context context,String  path) {
        File apkfile = new File(path);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(context, "com.lingsatuo.createjs.fileprovider", apkfile);
            //与manifest中定义的provider中的authorities="com.xinchuang.buynow.fileprovider"保持一致
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(i);
}

}
