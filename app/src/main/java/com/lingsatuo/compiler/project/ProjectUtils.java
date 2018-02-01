package com.lingsatuo.compiler.project;

import android.app.Activity;

import com.lingsatuo.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ProjectUtils {
    private String rootPath;
    private Map<String, String> build;

    private ProjectUtils(String rootPath) {
        this.rootPath = rootPath;
        this.build = new HashMap<>();
    }

    public static ProjectUtils getInstance(String rootPath) {
        return new ProjectUtils(rootPath);
    }

    public Project build(Back back, Activity activity) {
        Project project = null;
        try {
            getBuild();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String type = this.build.get("project");
        String name = this.build.get("name");
        String main = this.build.get("main");
        String out = this.build.get("out");
        switch (type) {
            case "js":
                project = new JSProject(this.rootPath, back);
                break;
            case "theme":
                project = new ThemeProject(this.rootPath, back);
                break;
            case "zip":
                project = new ZIPProject(this.rootPath, back);
                break;
            case "App":
                project = new AndroidProject(this.rootPath, back,activity);
                break;
            default:
                project = null;
                break;
        }
        if (project!=null){
            project.setMainPath(main);
            project.setName(name);
            project.setOutPutPath(out);
            project._setBuildJ(rootPath + "/" + "build.js");
            project._setBuildT(rootPath + "/" + "build.txt");
            project.reBuild();
        }
        return project;
    }

    private Map<String, String> getBuild() throws Exception {
        String build = rootPath + "/" + "build.txt";
        this.build.put("rootDir", rootPath);

        String str = Utils.readStringFromFile(build);
        String[] key = str.split("\n");
        int line = 0;
        for (int a = 0; a < key.length; a++) {
            line++;
            key[a] = key[a].replaceAll(" ", "");
            key[a] = key[a].replaceAll("\n", "");
            key[a] = key[a].replaceAll("\t", "");
            key[a] = key[a].replaceAll("\r", "");
            key[a] = key[a].replaceAll(";", "");
            if (key[a].indexOf("//") == 0) continue;
            if (key[a].equals("")) continue;
            String[] values = key[a].split("=");
            if (values.length < 2)
                values = key[a].split(":");
            if (values.length < 2) {
                throw new Exception();
            }
            this.build.put(values[0], values[1]);
        }
        return this.build;
    }
}
