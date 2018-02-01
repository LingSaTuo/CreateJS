package com.lingsatuo.createjs.Utils.EditUtils.utils;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import com.kingsatuo.view.JSEditor;
import com.lingsatuo.adapter.FileSelectorAdapter;
import com.lingsatuo.adapter.utils.FileSelectorUtils;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.Utils.EditUtils.EditUtils;
import com.lingsatuo.createjs.Utils.EditUtils.PopWindow.Pop;

import java.io.File;

import codeedit.lingsatuo.com.project.Back;

/**
 * Created by Administrator on 2017/12/16.
 */

public class OnLong implements AdapterView.OnItemLongClickListener {
    private MAIN activity;
    private JSEditor jsEditor;
    private FileSelectorAdapter adapter;
    private DrawerLayout drawerLayout;
    private EditUtils editUtils;
    private File file;
    private Back back;
    public OnLong(FileSelectorAdapter adapter, JSEditor jsEditor, MAIN activity, DrawerLayout drawerLayout, EditUtils editUtils){
        this.activity = activity;
        this.adapter = adapter;
        this.drawerLayout = drawerLayout;
        this.jsEditor = jsEditor;
        this.editUtils = editUtils;
        this.drawerLayout =drawerLayout;
        back = (project, e, tag) -> {
            if (tag.equals("DEL")){

            }else if (tag.equals("RENAME")){

            }
            if (file==null)return;
            adapter.setData(FileSelectorUtils.getData(file.getParentFile()));
            adapter.notifyDataSetChanged();
            editUtils.setDrawerFile(file.getParentFile());
        };
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        file = (File) adapter.getItem(i);
        if (i==0&&adapter.isProject()){
            new Pop(activity, view, file,back).showDele(Pop.POP.PROJECT);
        }else{
            new Pop(activity, view, file,back).showDele(Pop.POP.FILE);
        }
        return true;
    }
}
