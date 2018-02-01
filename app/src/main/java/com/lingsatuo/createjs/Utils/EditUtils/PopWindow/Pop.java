package com.lingsatuo.createjs.Utils.EditUtils.PopWindow;

import android.app.Activity;
import android.view.View;
import android.widget.PopupMenu;

import com.lingsatuo.callbackapi.ToBeContinue;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.EditUtils.utils.PopUtils;

import java.io.File;

import codeedit.lingsatuo.com.project.Back;

/**
 * Created by Administrator on 2017/11/30.
 */

public class Pop {
    private Activity context;
    private View view;
    private File file;
    public enum POP{
        FILE,PROJECT
    }
    private Back back;
    public Pop(Activity context, View view, File file, Back back) {
        this.context = context;
        this.file = file;
        this.view = view;
        this.back = back;
    }

    public Pop setView() {
        PopupMenu pm = new PopupMenu(this.context,this.view);
        pm.getMenuInflater().inflate(R.menu.edit_new_data,pm.getMenu());
        pm.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.new_data:
                    new PopUtils(context, PopUtils.MODE.DATA,this.file,back);
                    break;
                case R.id.new_js:
                    new PopUtils(context, PopUtils.MODE.JS,this.file,back);
                    break;
                case R.id.new_theme:
                    new PopUtils(context, PopUtils.MODE.THEME,this.file,back);
                    break;
                case R.id.new_zip:
                    new PopUtils(context, PopUtils.MODE.ZIP,this.file,back);
                    break;
                case R.id.new_app_v7:
                    new PopUtils(context, PopUtils.MODE.APP_V7,this.file,back);
                    break;
            }
            return false;
        });
        pm.show();
        return this;
    }

    public Pop show(ToBeContinue toBeContinue) {
        PopupMenu pm = new PopupMenu(this.context,this.view);
        pm.getMenuInflater().inflate(R.menu.ct_menu,pm.getMenu());
        pm.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.ct_edit:
                    toBeContinue.T2("edit");
                    break;
                case R.id.ct_view:
                    toBeContinue.T2("view");
                    break;
                case R.id.ct_default:
                    toBeContinue.T2("default");
                    break;
            }
            return false;
        });
        pm.show();
        return this;
    }

    public Pop showDele(POP pop) {
        PopupMenu pm = new PopupMenu(this.context,this.view);
        if (pop==POP.FILE) {
            pm.getMenuInflater().inflate(R.menu.edit_draw_longclick, pm.getMenu());
        }else if (pop==POP.PROJECT){
            pm.getMenuInflater().inflate(R.menu.edit_project, pm.getMenu());
        }
        pm.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.delete_file://删除文件
                    new PopUtils(context, PopUtils.MODE.DELETE,this.file,back);
                    break;
                case R.id.rename://重命名
                    new PopUtils(context, PopUtils.MODE.RENAME,this.file,back);
                    break;
                case R.id.delete_project://删除工程
                    new PopUtils(context, PopUtils.MODE.DELETE,this.file,back);
                    break;
            }
            return false;
        });
        pm.show();
        return this;
    }
}
