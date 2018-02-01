package com.lingsatuo.other.accessibilityUtils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.lingsatuo.createjs.R;
import com.lingsatuo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class Accessibility {

    static boolean pase = false;

    static AlertDialog ad = null;
    public static boolean isServiceEnabled(Activity context, boolean showdialog) {
      List<String> accessibilityServices = getAllAccessibilityServices(context);
        for (String info : accessibilityServices) {
            if (info.contains("com.lingsatuo.service.CreateJSAccessibilityService")) {
                return true;
            }
        }
        if (showdialog)
        showDialog(context);
        return false;
    }
    public static List<String> getAllAccessibilityServices(Activity context){
        AccessibilityManager accessibilityManager =    (AccessibilityManager)context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<String> services = new ArrayList<>();
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(
                        AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            services.add(info.getId());
        }
        return services;
    }
    private static void showDialog(Activity context){
        if (Utils.runOnUIThread()){
            throw new RuntimeException("阻塞Dialog不能在主线程调用,Accessibility.isServiceEnabled");
        }
        pase = false;
        context.runOnUiThread(() -> {
           ad = new AlertDialog.Builder(context)
                    .setTitle(context.getResources().getString(R.string.s_37))
                    .setMessage(context.getResources().getString(R.string.s_38))
                    .setPositiveButton(context.getString(R.string.s_11), null)
                    .setNegativeButton(context.getResources().getString(R.string.s_12), null)
                   .show();
            ad.getButton(DialogInterface.BUTTON1).setOnClickListener((i) -> {
                try {
                    Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    context.startActivity(accessibleIntent);
                } catch (Exception e) {
                    Toast.makeText(context, context.getResources().getString(R.string.s_39), Toast.LENGTH_LONG).show();
                }
            });
        });
        while (!pase) {
            if (isServiceEnabled(context, false)) {
                ad.dismiss();
                pase = true;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                pase = true;
            }
        }
    }
}
