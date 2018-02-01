package com.lingsatuo.CreateRunApplication;

import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kingsatuo.view.LogView;
import com.lingsatuo.createjs.R;
import com.lingsatuo.utils.Utils;

import java.io.FileReader;

/**
 * Created by 15176 on 2017/8/16.
 */

public class RunJS extends BaseMain {

    String path = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setActivity(this);
        setContentView(R.layout.log);
        setSupportActionBar(findViewById(R.id.toolbar));
        setRootView(findViewById(R.id.logroot));
        path = getIntent().getStringExtra("path");
        setPaths(new String[]{path});
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}