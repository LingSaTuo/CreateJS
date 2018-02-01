package com.lingsatuo.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.lingsatuo.listener.OnAccessibilityEvent;
import com.lingsatuo.listener.OnKeyEventListener;
import com.lingsatuo.other.accessibilityUtils.AccessibilityNodeinfo;
import com.lingsatuo.other.accessibilityUtils.NodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/19.
 */

public class CreateJSAccessibilityService extends AccessibilityService {
    private static AccessibilityEvent accessibilityEvent;
    private Context context;
    private static CreateJSAccessibilityService instance;
    static AccessibilityNodeinfo accessibilityNodeinfo;
    public List<OnAccessibilityEvent> onAccessibilityEvent = new ArrayList<>();
    public List<OnKeyEventListener> OnKeyEventListeners = new ArrayList<>();
    public static OnAccessibilityEvent monAccessibilityEvent;
    public static OnKeyEventListener monKeyEventListener;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityNodeinfo = AccessibilityNodeinfo.getAni().setAccessibilityEvent(accessibilityEvent);

        if (monAccessibilityEvent!=null){
            monAccessibilityEvent.OnChange(accessibilityNodeinfo);
        }

        for (OnAccessibilityEvent event : onAccessibilityEvent){
            if (event!=null&&accessibilityEvent!=null)
            event.OnChange(accessibilityNodeinfo);
        }
        this.accessibilityEvent = accessibilityEvent;
    }

    public static CreateJSAccessibilityService getInstance(){
        return instance;
    }
    @Override
    public void onInterrupt() {
        for (OnAccessibilityEvent ev : onAccessibilityEvent){
            if (ev!=null)
                ev.OnChange(accessibilityNodeinfo);
        }
    }
    public void addEventListener(OnAccessibilityEvent eventChangeListener){
        this.onAccessibilityEvent.add(eventChangeListener);
    }
    public void removeEventListener(OnAccessibilityEvent event){
        for (int a  = 0;a<onAccessibilityEvent.size();a++){
            if (event==onAccessibilityEvent.get(a)){
                onAccessibilityEvent.set(a,null);
            }
        }
    }
    public void removeAllEventListener(){
        onAccessibilityEvent = new ArrayList<>();
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }
    public void addKeyEventListener(OnKeyEventListener listener){
        OnKeyEventListeners.add(listener);
    }
    public void removeKeyEventListener(OnKeyEventListener listener){
        for (int a = 0; a< OnKeyEventListeners.size(); a++){
            if (listener== OnKeyEventListeners.get(a))
            OnKeyEventListeners.set(a,null);
        }
    }
    public void removeAllKeyEventListener(){
        OnKeyEventListeners = new ArrayList<>();
    }
    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (monKeyEventListener!=null){
        monKeyEventListener.T(event);
    }
        for (OnKeyEventListener OnKeyEventListener : OnKeyEventListeners){
            if (OnKeyEventListener !=null){
                OnKeyEventListener.T(event);
            }
        }
        return super.onKeyEvent(event);
    }
    public static NodeInfo getInfo(){
        return accessibilityNodeinfo.getSimpleSource();
    }

    public static AccessibilityEvent getEvent(){
        return accessibilityNodeinfo.getEvent();
    }
    public static void setToast(AccessibilityEvent accessibilityEvent){

    }
}
