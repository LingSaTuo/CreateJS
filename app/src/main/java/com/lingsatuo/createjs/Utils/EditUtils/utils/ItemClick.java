package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.kingsatuo.view.JSEditor;
import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.adapter.FileSelectorAdapter;
import com.lingsatuo.adapter.utils.FileSelectorUtils;
import com.lingsatuo.compiler.project.AndroidProject;
import com.lingsatuo.compiler.project.ProjectUtils;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.EditUtils.EditUtils;
import com.lingsatuo.createjs.Utils.EditUtils.PopWindow.Pop;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.utils.Utils;
import com.myopicmobile.textwarrior.common.LanguageJavascript;
import com.myopicmobile.textwarrior.common.LanguageNonProg;
import com.myopicmobile.textwarrior.common.Lexer;
import com.myopicmobile.textwarrior.common.ThemeLanguage;
import com.myopicmobile.textwarrior.language.AndroidLanguage;

import java.io.File;

import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ItemClick implements AdapterView.OnItemClickListener {

    private MAIN activity;
    private JSEditor jsEditor;
    private FileSelectorAdapter adapter;
    private DrawerLayout drawerLayout;
    private EditUtils editUtils;
    public ItemClick(FileSelectorAdapter adapter, JSEditor jsEditor, MAIN activity, DrawerLayout drawerLayout, EditUtils editUtils){
        this.activity = activity;
        this.adapter = adapter;
        this.drawerLayout = drawerLayout;
        this.jsEditor = jsEditor;
        this.editUtils = editUtils;
        this.drawerLayout =drawerLayout;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File file = (File) adapter.getItem(i);
        if (i==0&&adapter.isProject()){
            Project project = ProjectUtils.getInstance(file.getPath()).build(new NEW_PROJECT(jsEditor,adapter,activity),activity);
            if (project==null){
                new Dialog(activity, getString(R.string.na_4), getString(R.string.project_error)+"----Project", getString(R.string.s_11), null);
            }
            drawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if (!file.isFile()) {
            adapter.setData(FileSelectorUtils.getData(file));
            adapter.notifyDataSetChanged();
            this.editUtils.setDrawerFile(file);
            return;
        } else {
            if (new Files().getExtension(file.getPath()).equals(".js".toLowerCase())) {
                jsEditor.open(file.getPath(),new Edit(jsEditor,activity,file,view));
                drawerLayout.closeDrawer(Gravity.START);
                if (EditUtils.last_project instanceof AndroidProject && EditUtils.last_project.inProject(file.getPath())){
                    jsEditor.setLanguage(AndroidLanguage.getInstance());
                    Lexer.setLanguage(AndroidLanguage.getInstance());
                }else {
                    jsEditor.setLanguage(LanguageJavascript.getInstance());
                    Lexer.setLanguage(LanguageJavascript.getInstance());
                }
                Lexer.setEnable(true);
                Lexer.clear();
                jsEditor.setOpenedFile(file.getPath());
                drawerLayout.closeDrawer(Gravity.START);
            }
            else if (new Files().getExtension(file.getPath()).equals(".txt".toLowerCase())) {
                jsEditor.open(file.getPath(),new Edit(jsEditor,activity,file,view));
                drawerLayout.closeDrawer(Gravity.START);
                jsEditor.setLanguage(LanguageNonProg.getInstance());
                Lexer.setEnable(false);
                Lexer.clear();
                jsEditor.setOpenedFile(file.getPath());
                drawerLayout.closeDrawer(Gravity.START);
            }
            else if (new Files().getExtension(file.getPath()).equals(".xml".toLowerCase())) {
                jsEditor.open(file.getPath(),new Edit(jsEditor,activity,file,view));
                drawerLayout.closeDrawer(Gravity.START);
                jsEditor.setLanguage(LanguageNonProg.getInstance());
                Lexer.setEnable(false);
                Lexer.clear();
                jsEditor.setOpenedFile(file.getPath());
                drawerLayout.closeDrawer(Gravity.START);
            }
            else if (new Files().getExtension(file.getPath()).equals(".propres".toLowerCase())) {
                jsEditor.open(file.getPath(),new Edit(jsEditor,activity,file,view));
                drawerLayout.closeDrawer(Gravity.START);
                jsEditor.setLanguage(LanguageNonProg.getInstance());
                Lexer.setEnable(false);
                Lexer.clear();
                jsEditor.setOpenedFile(file.getPath());
                drawerLayout.closeDrawer(Gravity.START);
            }
            else if (new Files().getExtension(file.getPath()).equals(".ct".toLowerCase())) {
                new Pop(activity, view, file,null).show(obj -> {
                    String key = (String) obj;
                    switch (key) {
                        case "edit":
                            jsEditor.setLanguage(ThemeLanguage.getInstance());
                            Lexer.setEnable(false);
                            Lexer.clear();
                            jsEditor.open(file.getPath(), new Edit(jsEditor,activity,file,view));
                            break;
                        case "view":
                            try {
                                jsEditor.setColorScheme(new Theme(file.getPath()));
                                Toast.makeText(activity, getString(R.string.s_82), Toast.LENGTH_SHORT).show();
                            } catch (CreateJSIOException e) {
                                new Dialog(activity, getString(R.string.na_4), e.getMessage(), getString(R.string.s_11), null);
                            }
                            break;
                        case "default":
                            try {
                                jsEditor.setColorScheme(new Theme(file.getPath()));
                                new Files().copy(file, new File(Utils.getSD() + "/.CreateJS/data/theme.ct"));
                                Utils.saveDateToApp(activity, "edit_theme", Utils.getSD() + "/.CreateJS/data/theme.ct");
                                Toast.makeText(activity, getString(R.string.s_82), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                new Dialog(activity, getString(R.string.na_4), e.getMessage(), getString(R.string.s_11), null);
                            }
                            break;
                    }
                    drawerLayout.closeDrawer(Gravity.START);
                    return false;
                });
            }
        }
    }
    public String getString(int id) {
        return activity.getApplicationContext().getResources().getString(id);
    }
}
