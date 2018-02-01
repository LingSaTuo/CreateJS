package com.lingsatuo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

import com.lingsatuo.listener.OnAccessibilityEvent;
import com.lingsatuo.listener.OnKeyEventListener;
import com.lingsatuo.other.accessibilityUtils.AccessibilityNodeinfo;
import com.lingsatuo.other.accessibilityUtils.NodeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 15176 on 2017/6/28.
 */

public class CreateJSService {
    public static CreateJSAccessibilityService getInstance(){
        return CreateJSAccessibilityService.getInstance();
    }
    public static NodeInfo getInfo(){
        return AccessibilityNodeinfo.getAni().getSimpleSource();
    }
    public static void addNodeInfoListener(OnAccessibilityEvent onAccessibilityEvent){
        CreateJSAccessibilityService.getInstance().addEventListener(onAccessibilityEvent);
    }
    public static void removeNodeInfoListener(OnAccessibilityEvent onAccessibilityEvent){
        CreateJSAccessibilityService.getInstance().removeEventListener(onAccessibilityEvent);
    }
    public static void removeAllNodeInfoListener(){
        CreateJSAccessibilityService.getInstance().removeAllEventListener();
    }
    public static void addKeyEventListener(OnKeyEventListener onAccessibilityEvent){
        CreateJSAccessibilityService.getInstance().addKeyEventListener(onAccessibilityEvent);
    }
    public static void removeKeyEventListener(OnKeyEventListener onAccessibilityEvent){
        CreateJSAccessibilityService.getInstance().removeKeyEventListener(onAccessibilityEvent);
    }
    public static void removeAllKeyEventListener(){
        CreateJSAccessibilityService.getInstance().removeAllEventListener();
    }
    public static AccessibilityEvent getEvent(){
        return CreateJSAccessibilityService.getInstance().getEvent();
    }
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static CharSequence getClassName(){
        return getEvent().getClassName();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static List<NodeInfo> getViewsByText(String text){
        List<NodeInfo> list = getRootInfo().findViewByText(text);
        List<NodeInfo> mlist = Collections.synchronizedList(new ArrayList<>());
        return getText(mlist,list,text);
    }
    private static List<NodeInfo> getText(List<NodeInfo> mlist,List<NodeInfo> list,String text){
        for (NodeInfo nodeInfo:list) {
            int count = nodeInfo.getChildCount();
            if (count == 0) {
                list.add(nodeInfo);
            } else {
                List<NodeInfo> infoList = nodeInfo.findViewByText(text);
                    getText(mlist,infoList, text);
            }
        }
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static List<NodeInfo> getViewsById(String text){
        List<NodeInfo> list = getRootInfo().findViewById(text);
        List<NodeInfo> mlist = Collections.synchronizedList(new ArrayList<>());
        return getId(mlist,list,text);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static List<NodeInfo> getId(List<NodeInfo> mlist,List<NodeInfo> list, String text){

        for (NodeInfo nodeInfo:list) {
            int count = nodeInfo.getChildCount();
            if (count == 0) {
                mlist.add(nodeInfo);
            } else {
                List<NodeInfo> infoList = nodeInfo.findViewById(text);
                getText(mlist,infoList, text);
            }
        }
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static NodeInfo getRootInfo(){
        return new NodeInfo(CreateJSAccessibilityService.getInstance().getRootInActiveWindow());
    }
}
