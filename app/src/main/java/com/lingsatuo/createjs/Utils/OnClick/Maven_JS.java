package com.lingsatuo.createjs.Utils.OnClick;

import android.app.Activity;
import android.view.View;

import com.lingsatuo.adapter.Maven;
import com.lingsatuo.createjs.Utils.OnClick.Pop.PopWindow;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Maven_JS implements View.OnClickListener {
    private Maven maven;
    private Activity activity;
    public Maven_JS(Maven maven, Activity activity){
        this.activity = activity;
        this.maven = maven;
    }
    @Override
    public void onClick(View view) {
        new PopWindow(activity,view,maven).setView();
    }
}
