package com.lingsatuo.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.lingsatuo.createjs.Utils.DataD;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Permission {
    Activity context;
    String[] p;

    public Permission(Activity context) {
        this.context = context;
    }

    public void Call(String[] str) {
        ArrayList<String> perm = new ArrayList<String>();

        for (int a = 0; a < str.length; a++) {
            int permissionCheck = ContextCompat.checkSelfPermission(context,
                    str[a]);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                perm.add(str[a]);
            }
        }
        if (perm.size() > 0 && perm.get(0) != null) {
            p = new String[perm.size()];
            for (int a = 0; a < p.length; a++) {
                p[a] = perm.get(a);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(context, p, 123);
            } else {
                show();
            }
        }
    }


    public void CheckPermission() {
        Call(DataD.Permission);
    }

    public void show() {
        if (p != null && p.length > 0) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + context.getPackageName())); // 根据包名打开对应的设置界面
                    context.startActivity(intent);
                }
            } catch (Exception e) {

                Toast.makeText(context, "Error", 1).show();
            }
        }
    }
}
