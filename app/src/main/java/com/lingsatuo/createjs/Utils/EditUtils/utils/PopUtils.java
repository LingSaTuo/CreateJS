package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.compiler.project.AndroidProject;
import com.lingsatuo.compiler.project.FileProject;
import com.lingsatuo.compiler.project.JSProject;
import com.lingsatuo.compiler.project.ThemeProject;
import com.lingsatuo.compiler.project.ZIPProject;
import com.lingsatuo.createjs.R;
import com.lingsatuo.utils.Utils;

import java.io.File;
import java.io.IOException;

import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/11/30.
 */

public class PopUtils {
    private AlertDialog alertDialog;
    private Activity activity;
    private File file;
    private Back back;

    Project project = null;

    public enum MODE {
        DATA,
        PROJECT,
        THEME,
        SAVE,
        ZIP,
        JS,
        DELETE,
        RENAME,
        APP_V7
    }

    public PopUtils(Activity activity, MODE mode, File file, Back back) {
        this.back = back;
        this.activity = activity;
        this.file = file;
        switch (mode) {
            case DATA:
                data();
                break;
            case THEME:
                theme();
                break;
            case ZIP:
                zip();
                break;
            case JS:
                js();
                break;
            case DELETE:
                delete();
                break;
            case RENAME:
                rename();
                break;
            case APP_V7:
                app_v7();
                break;
        }
    }

    private void data() {
        EditText editText = new EditText(activity);
        editText.setSingleLine(true);
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_83)
                .setView(R.layout.ll)
                .setPositiveButton(R.string.s_86, null)
                .setNeutralButton(R.string.s_12, null)
                .setNegativeButton(R.string.s_87, null);
        alertDialog = ab.create();
        alertDialog.show();
        ((LinearLayout) alertDialog.getWindow().findViewById(R.id.ll)).addView(editText);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            String name = editText.getText().toString();
            String path = file.getPath() + "/" + name;
            try {
                new File(path).createNewFile();
                alertDialog.dismiss();
            } catch (IOException e) {
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.getButton(DialogInterface.BUTTON2).setOnClickListener(view -> {
            String name = editText.getText().toString();
            String path = file.getPath() + "/" + name;
            if (new File(path).mkdirs())
                alertDialog.dismiss();
            else {
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setOnDismissListener(dialogInterface -> {
            String name = editText.getText().toString();
            String path = file.getPath() + "/" + name;
            project = new FileProject(path);
            if (back != null) {
                back.T(project, null, "");
            }
        });
    }

    private void js() {
        EditText editText = new EditText(activity);
        editText.setSingleLine(true);
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_83)
                .setView(R.layout.ll)
                .setPositiveButton(R.string.s_11, null)
                .setNegativeButton(R.string.s_12, null);
        alertDialog = ab.create();
        alertDialog.show();
        ((LinearLayout) alertDialog.getWindow().findViewById(R.id.ll)).addView(editText);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            try {
                String name = editText.getText().toString();
                String path = file.getPath() + "/" + name;
                project = new JSProject(path, this.back);
                project.setName(name);
                project.setMainPath("main/" + name + ".js");
                project.createNewOne(activity.getApplicationContext(), this.back);
                alertDialog.dismiss();
            } catch (Exception e) {
                if (this.back != null) {
                    this.back.T(project, e, "");
                    e.printStackTrace();
                }
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void theme() {
        EditText editText = new EditText(activity);
        editText.setSingleLine(true);
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_83)
                .setView(R.layout.ll)
                .setPositiveButton(R.string.s_11, null)
                .setNegativeButton(R.string.s_12, null);
        alertDialog = ab.create();
        alertDialog.show();
        ((LinearLayout) alertDialog.getWindow().findViewById(R.id.ll)).addView(editText);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            try {
                String name = editText.getText().toString();
                String path = file.getPath() + "/" + name;
                project = new ThemeProject(path, this.back);
                project.setName(name);
                project.setMainPath("main/" + name + ".ct");
                project.createNewOne(activity.getApplicationContext(), this.back);
                alertDialog.dismiss();
            } catch (Exception e) {
                if (this.back != null) {
                    this.back.T(project, e, "");
                    e.printStackTrace();
                }
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void zip() {
        EditText editText = new EditText(activity);
        editText.setSingleLine(true);
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_83)
                .setView(R.layout.ll)
                .setPositiveButton(R.string.s_11, null)
                .setNegativeButton(R.string.s_12, null);
        alertDialog = ab.create();
        alertDialog.show();
        ((LinearLayout) alertDialog.getWindow().findViewById(R.id.ll)).addView(editText);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            try {
                String name = editText.getText().toString();
                String path = file.getPath() + "/" + name;
                project = new ZIPProject(path, this.back);
                project.setName(name);
                project.setMainPath("main/" + "script/" + name + ".js");
                project.setOutPutPath("build/" + name + ".zip");
                project._setOutPutPath(project._getRootDir() + "/build/" + name + ".zip");
                project.createNewOne(activity.getApplicationContext(), this.back);
                alertDialog.dismiss();
            } catch (Exception e) {
                if (this.back != null) {
                    this.back.T(project, e, "");
                    e.printStackTrace();
                }
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void app_v7() {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_83)
                .setView(R.layout.new_app)
                .setPositiveButton(R.string.s_11, null)
                .setNegativeButton(R.string.s_12, null);
        alertDialog = ab.create();
        alertDialog.show();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        EditText appname = alertDialog.getWindow().findViewById(R.id.appname);
        EditText packagename = alertDialog.getWindow().findViewById(R.id.package_name);
        appname.setFocusable(true);
        packagename.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            try {
                String name = appname.getText().toString();
                String path = file.getPath() + "/" + name;
                String Package = packagename.getText().toString();

                if (name.equals("")){
                    if (this.back != null) {
                        this.back.T(project, new IllegalProjectException("App name exception"), "");
                        return;
                    }
                }
                if (Package.split("\\.").length<2){
                    if (this.back != null) {
                        this.back.T(project, new IllegalProjectException("Package name exception"), "");
                        return;
                    }
                }



                project = new AndroidProject(path, this.back,activity);
                project.setName(name);
                project.setMainPath("main/" + "script/main.js");
                project.setOutPutPath("build/" + name + ".apk");
                project._setOutPutPath(project._getRootDir() + "/build/" + name + ".apk");
                project.createNewOne(activity.getApplicationContext(), this.back);

                ((AndroidProject)project).setAppName(name);
                ((AndroidProject)project).setPackagename(Package);
                ((AndroidProject)project).rebuildMainTxt();
                alertDialog.dismiss();
            } catch (Exception e) {
                if (this.back != null) {
                    this.back.T(project, e, "");
                    e.printStackTrace();
                }
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
    }





    public void delete() {
        new Dialog(activity,
                activity.getApplicationContext().getResources().getString(R.string.s_97),
                activity.getApplicationContext().getResources().getString(R.string.s_101) + file.getPath(),
                activity.getApplicationContext().getResources().getString(R.string.s_11), (dialogInterface, i) -> {
            try {
                Utils.deleteFolder(file.getPath());
                if (back != null) {
                    back.T(null, null, "DEL");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void rename() {
        EditText editText = new EditText(activity);
        editText.setText(file.getName());
        editText.setSingleLine(true);
        AlertDialog.Builder ab = new AlertDialog.Builder(activity)
                .setTitle(R.string.s_100)
                .setView(R.layout.ll)
                .setPositiveButton(R.string.s_11, null)
                .setNegativeButton(R.string.s_12, null);
        alertDialog = ab.create();
        alertDialog.show();
        ((LinearLayout) alertDialog.getWindow().findViewById(R.id.ll)).addView(editText);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.setFocusable(true);
        alertDialog.getButton(DialogInterface.BUTTON1).setOnClickListener(view -> {
            try {
                String name = editText.getText().toString();
                String path = file.getParent() + "/" + name;
                file.renameTo(new File(path));
                alertDialog.dismiss();
                if (this.back != null) {
                    this.back.T(null, null, "RENAME");
                }
            } catch (Exception e) {
                Toast.makeText(activity, R.string.na_2, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
