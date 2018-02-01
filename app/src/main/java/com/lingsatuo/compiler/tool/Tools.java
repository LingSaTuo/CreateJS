package com.lingsatuo.compiler.tool;

import android.app.Activity;
import android.content.Intent;

import com.lingsatuo.createjs.LoadingActivity;

import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/7.
 */

public class Tools {
    private static Tools tools;
    private Project project;
    private Activity activity;
    private String path;
    private Intent i;

    public static Tools getInstance() {
        if (tools == null) {
            tools = new Tools();
        }
        return tools;
    }

    public static Tools getInstance(Project project, Activity activity) {
        getInstance();
        tools.setProject(project);
        tools.setActivity(activity);
        return tools;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getPath(){
        return this.path;
    }
    public void start() {
        i = new Intent(activity, LoadingActivity.class);
//        if (project == null) return;
//        if (project instanceof JSProject) {
//            i = new Intent(activity, RunJS.class);
//        } else if (project instanceof ZIPProject) {
//            i = new Intent(activity, RunModpkg.class);
//        } else {
//            i = new Intent(activity, RunJS.class);
//        }
        i.putExtra("path", getPath());
        activity.startActivity(i);
    }
}
