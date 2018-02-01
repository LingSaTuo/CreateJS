package com.lingsatuo.createjs.Utils.OnClick.Pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.lingsatuo.adapter.Maven;
import com.lingsatuo.createjs.ProtocolReader;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Utils.OnClick.Pop.PopUtils.Utils;

import java.io.File;

/**
 * Created by Administrator on 2017/9/28.
 */

public class PopWindow {
    private Activity context;
    private View view;

    private Maven maven;
    public PopWindow(Activity context, View view, Maven maven) {
        this.context = context;
        this.maven = maven;
        this.view = view;
    }

    public void setView() {
        PopupMenu pm = new PopupMenu(this.context,this.view);
        pm.getMenuInflater().inflate(R.menu.pop_maven_js,pm.getMenu());
        pm.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.run:

                    break;
                case R.id.update:
                    new Utils(maven,context).update();
                    break;
                case R.id.backups:
                    new Utils(maven,context).backups();
                    break;
                case R.id.delete:
                    new Utils(maven,context).delete();
                    break;
                case R.id.api:
                    File f = new File(maven.getPath()+"/API/api.html");
                    if (!f.exists()||!f.isFile()){
                        Toast.makeText(context,"该库没有内置API文档",1).show();
                        return false;
                    }
                    Intent intent = new Intent(context,ProtocolReader.class);
                    intent.putExtra("path",f.getPath());
                    context.startActivity(intent);
                    break;
            }
            return false;
        });
        pm.show();
    }
}
