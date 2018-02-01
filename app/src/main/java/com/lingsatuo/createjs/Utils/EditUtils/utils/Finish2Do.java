package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.compiler.project.ProjectUtils;
import com.lingsatuo.createjs.LoadingActivity;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Files;

import java.io.File;

import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Finish2Do {
    private Activity activity;
    private File file;
    private Project project;

    public Finish2Do(Activity activity, File file) {
        this.file = file;
        this.activity = activity;
    }

    public Finish2Do(Activity activity, Project project) {
        this.activity = activity;
        this.project = ProjectUtils.getInstance(project._getRootDir()).build(project.getBack(),activity);
    }

    public void run() {
        String ends = new Files().getExtension(file.getPath()).toLowerCase();
        String js = ".js".toLowerCase();
        String ct = ".ct".toLowerCase();
        if (ends.equals(js)) {
            runjs();
        } else if (ends.equals(ct)) {
            runct();
        }
    }

    public void runProject() {
        if (project == null) {
            new Dialog(activity, getString(R.string.na_4), getString(R.string.project_error) + "----Project", getString(R.string.s_11), null);
            return;
        }
            project.Compiler(true);
    }

    private void runjs() {
        if (file == null) {
            new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2), getString(R.string.s_11), null);
        } else {
            //todo
            Intent intent = new Intent(activity.getApplicationContext(), LoadingActivity.class);
            intent.putExtra("path", file.getPath());
            activity.startActivity(intent);
        }
    }

    private void runct() {
        if (file == null) {
            new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2), getString(R.string.s_11), null);
        } else {
            try {
                new Theme(file.getPath());
                Toast.makeText(activity, getString(R.string.s_82), Toast.LENGTH_SHORT).show();
            } catch (CreateJSIOException e) {
                new Dialog(activity, getString(R.string.na_4), e.getMessage(), getString(R.string.s_11), null);
            }
        }
    }

    public String getString(int id) {
        return activity.getApplicationContext().getResources().getString(id);
    }

}
