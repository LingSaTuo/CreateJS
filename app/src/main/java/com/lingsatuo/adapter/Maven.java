package com.lingsatuo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.lingsatuo.createjs.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Maven {
    public static int TYPE_JS_MAVEN = 0;
    public static int TYPE_USER_MAVEN = 1;
    public static int TYPE_ERROR_MAVEN = 2;
    private CharSequence title = "MAVEN";
    private Drawable type;
    private Context context;
    private int TYPE = 3;
    private String path;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setTile(CharSequence title) {
        this.title = title;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public void setType(int type) {
        if (type < 0 || type > 2) {
            type = 2;
        }
        this.TYPE = type;
        switch (type) {
            case 0:
                this.type = context.getResources().getDrawable(R.drawable.js_maven);
                break;
            case 1:
                this.type = context.getResources().getDrawable(R.drawable.user_maven);
                break;
            case 2:
                this.type = context.getResources().getDrawable(R.drawable.error_maven);
                break;
        }
    }

    public Drawable getType() {
        return this.type;
    }

    public int getTYPE() {
        return this.TYPE;
    }

}
