package com.lingsatuo.CreateRunApplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kingsatuo.view.LogView;
import com.lingsatuo.createjs.R;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/8/16.
 */

public class RunModpkg extends BaseMain {


    private static String path;
    String path2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setActivity(this);
        setContentView(R.layout.log);
        setSupportActionBar(findViewById(R.id.toolbar));
        setRootView(findViewById(R.id.logroot));
        path = getIntent().getStringExtra("path");
        String name = Utils.getFileNameNoEx(new File(path).getName());
        path2 = Environment.getExternalStorageDirectory()+"/.CreateJS/cache";
        try {
            ZipUtils.loadZip(path,path2 );
            File[] files = new File(path2+"/script").listFiles();
            List<String> list = new ArrayList<>();
            for(File f : files){
                if (f.getName().endsWith("js")||f.getName().endsWith("JS")){
                    list.add(f.getPath());
                }
            }
            String[] paths = new String[list.size()];
            for(int a  = 0 ; a < paths.length;a++){
                paths[a] = list.get(a);
            }
            setPaths(paths);
        } catch (Exception e) { sendMessage("LoadModpkgErr",Utils.getErr(e), Color.RED); }
    }

    @Override
    public void onBackPressed() {
        try {
            Utils.deleteFolder(path2);
            new File(path2).mkdirs();
        } catch (Exception e) { }
        super.onBackPressed();
    }
}
