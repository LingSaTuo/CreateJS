package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.kingsatuo.view.JSEditor;
import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.adapter.FileSelectorAdapter;
import com.lingsatuo.adapter.utils.FileSelectorUtils;
import com.lingsatuo.compiler.project.AndroidProject;
import com.lingsatuo.compiler.project.FileProject;
import com.lingsatuo.compiler.project.JSProject;
import com.lingsatuo.compiler.project.ThemeProject;
import com.lingsatuo.compiler.project.ZIPProject;
import com.lingsatuo.createjs.LoadingActivity;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.EditUtils.EditUtils;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.App;
import com.lingsatuo.rhino.RhinoAndroidHelper;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.Utils;
import com.myopicmobile.textwarrior.common.LanguageJavascript;
import com.myopicmobile.textwarrior.common.Lexer;
import com.myopicmobile.textwarrior.common.ThemeLanguage;
import com.myopicmobile.textwarrior.language.AndroidLanguage;

import org.mozilla.javascript.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/14.
 */

public class NEW_PROJECT implements Back {
    private JSEditor jsEditor;
    private FileSelectorAdapter adapter;
    private Activity activity;
    public NEW_PROJECT(JSEditor jsEditor,FileSelectorAdapter adapter,Activity activity){
        this.activity = activity;
        this.adapter = adapter;
        this.jsEditor = jsEditor;
    }
    @Override
    public void T(Project project, Exception e,String tag) {
        activity.runOnUiThread(new A(project,e,tag));
    }
    public String getString(int id) {
        return activity.getApplicationContext().getResources().getString(id);
    }

    private class A implements Runnable{
        private Project project;
        private Exception e;
        private String tag;
        public A(Project project, Exception e,String tag){
                this.project = project;
                this.e = e;
                this.tag = tag;
        }
        @Override
        public void run() {
            if (e!=null){
                new Dialog(activity, getString(R.string.na_4), e.getMessage(), getString(R.string.s_11), null);
                return;
            }
            if (tag.equals("run")){ //编译开始
                String out = project._getOutPath();
                File f = new File(out);
                if (f==null||f.isDirectory()||!f.exists()){
                    new Dialog(activity, getString(R.string.na_4), "output --- "+out +"  is null or Directory", getString(R.string.s_11), null);
                    return;
                }
                runBuild(project);
                return;
            }else if(tag.equals("start")){//开始运行
                String out = project._getOutPath();
                if (project instanceof ThemeProject){
                    try {
                        new Theme(out);
                        Toast.makeText(activity,getString(R.string.s_82),Toast.LENGTH_SHORT).show();
                    } catch (CreateJSIOException e1) {
                        new Dialog(activity, getString(R.string.na_4), e1.getMessage(), getString(R.string.s_11), null);
                    }
                }else if(project instanceof AndroidProject){
                    App.installApk(activity.getApplicationContext(),project._getOutPath());
                }else{
                    Intent intent = new Intent(activity.getApplicationContext(), LoadingActivity.class);
                    intent.putExtra("path", out);
                    activity.startActivity(intent);
                }
            }else {
                call(project);
            }
        }
    }
    private void call(Project project){
        if (project instanceof FileProject){
            File f = new File(project._getRootDir()).getParentFile();
            adapter.setData(FileSelectorUtils.getData(f));
            EditUtils.setOpenFile(f);
            adapter.notifyDataSetChanged();
            return;
        }
        File f = new File(project._getRootDir());
        EditUtils.setOpenFile(f);
        adapter.setData(FileSelectorUtils.getData(new File(project._getRootDir())));
        adapter.notifyDataSetChanged();
        if (jsEditor.getOpenedFile()==null||!project.inProject(jsEditor.getOpenedFile().getPath()))
        jsEditor.open(project._getRootDir()+"/"+project.getMainPath());
        EditUtils.last_project = project;
        if (project instanceof ZIPProject){
            jsEditor.setLanguage(LanguageJavascript.getInstance());
            Lexer.setLanguage(LanguageJavascript.getInstance());
            Lexer.setEnable(true);
            Lexer.clear();
        }else if(project instanceof ThemeProject){
            jsEditor.setLanguage(ThemeLanguage.getInstance());
            Lexer.setEnable(false);
            Lexer.clear();
        }else if(project instanceof JSProject){
            jsEditor.setLanguage(LanguageJavascript.getInstance());
            Lexer.setLanguage(LanguageJavascript.getInstance());
            Lexer.setEnable(true);
            Lexer.clear();
        }else if (project instanceof AndroidProject){
            jsEditor.setLanguage(AndroidLanguage.getInstance());
            Lexer.setLanguage(AndroidLanguage.getInstance());
            Lexer.setEnable(true);
            Lexer.clear();
        }
    }
    private void runBuild(Project project){
        List<String> build = new ArrayList<>();
        build.add(project._getBuildJ());
        ScriptTool scriptTool = ScriptTool.getInstance().Instance(activity);
        try {
            scriptTool.create(null, new RhinoAndroidHelper(activity).enterContext(),build);
            scriptTool.setOnScriptExcption(e -> new Dialog(activity, getString(R.string.na_4), "Build.js --- Run\n\n"+e.getMessage()+"\n\n"+ Utils.getErr(e), getString(R.string.s_11), null));
            scriptTool.setObj2JS("Project",project);
            scriptTool.setObj2JS("Activity",activity);
            scriptTool.execution();
            scriptTool.run();
            Context.exit();
        } catch (Exception e1) {
            new Dialog(activity, getString(R.string.na_4), "Build.js --- Build\n\n"+e1.getMessage()+"\n\n"+ Utils.getErr(e1), getString(R.string.s_11), null);
        }
    }
}

