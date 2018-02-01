package com.lingsatuo.openapi;

import android.app.Activity;
import android.content.Context;

import com.lingsatuo.callbackapi.Function;
import com.lingsatuo.listener.OnDownload;
import com.lingsatuo.utils.SIUutil;

/**
 * Created by Administrator on 2017/9/26.
 */

public class Download extends SIUutil{
    public Download(Activity activity,String downloaduri, String savepath, OnDownload onDownload){
        super.Download(activity,downloaduri,savepath,onDownload);
    }
}
