package com.lingsatuo.createjs.Utils.OnClick;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.adapter.Maven;
import com.lingsatuo.utils.Utils;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Maven_ERROR implements View.OnClickListener {
    private Maven maven;
    private Activity activity;
    public Maven_ERROR(Maven maven, Activity context) {
        this.maven = maven;
        this.activity = context;
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(view,"不是合法的库",Snackbar.LENGTH_LONG).setAction("更多", view1 -> {
            new Dialog(activity, "错误信息", "这不是一个可识别的库，但它在库文件目录下，CreateJS不认为它可用", "删除", (dialogInterface, i) -> {
                try {
                    Utils.deleteFolder(maven.getPath());
                } catch (Exception e) {}
            });
        }).show();
    }
}
