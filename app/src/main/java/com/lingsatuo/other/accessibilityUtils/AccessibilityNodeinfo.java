package com.lingsatuo.other.accessibilityUtils;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lingsatuo.service.CreateJSAccessibilityService;

/**
 * Created by 15176 on 2017/9/25.
 */

public class AccessibilityNodeinfo{
    private AccessibilityEvent accessibilityEvent;
    private NodeInfo nodeInfo;
    private static AccessibilityNodeinfo instance;
    public static AccessibilityNodeinfo getAni (){
        if (instance==null) {
            instance = new AccessibilityNodeinfo();
        }
        return instance;
    }
    public AccessibilityNodeinfo setAccessibilityEvent(AccessibilityEvent accessibilityEvent){
        this.accessibilityEvent = accessibilityEvent;
        if (getSource()!=null) {
            nodeInfo = new NodeInfo(getSource());
        }
        return this;
    }
    public AccessibilityNodeInfo getSource(){
        return this.accessibilityEvent.getSource();
    }

    public NodeInfo getSimpleSource(){
        return this.nodeInfo;
    }
    public CreateJSAccessibilityService getService(){
        return CreateJSAccessibilityService.getInstance();
    }
    public AccessibilityEvent getEvent(){
        return this.accessibilityEvent;
    }
    public int getEventType(){
        return getEvent().getEventType();
    }
    public CharSequence getClassName(){
        return getEvent().getClassName();
    }
}
