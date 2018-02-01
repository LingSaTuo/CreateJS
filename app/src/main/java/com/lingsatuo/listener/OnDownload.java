package com.lingsatuo.listener;

/**
 * Created by Administrator on 2017/9/26.
 */

public interface OnDownload {
    void T(double progress,int max,boolean finish,Throwable e);
}
