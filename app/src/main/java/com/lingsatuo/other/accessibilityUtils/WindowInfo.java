package com.lingsatuo.other.accessibilityUtils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityWindowInfo;

/**
 * Created by 15176 on 2017/9/25.
 */

public class WindowInfo {
    private AccessibilityWindowInfo nodeInfo;
    public WindowInfo(AccessibilityWindowInfo info){
        this.nodeInfo = info;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AccessibilityWindowInfo getWindowInfo(){
        return nodeInfo;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NodeInfo getRoot(){
        return new NodeInfo(nodeInfo.getRoot());
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WindowInfo getChild(int index){
        return new WindowInfo(nodeInfo.getChild(index));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getChildCount(){
        return nodeInfo.getChildCount();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getId(){
        return nodeInfo.getId();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isActive(){
        return nodeInfo.isActive();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean isFocused(){
        return nodeInfo.isFocused();
    }
}
