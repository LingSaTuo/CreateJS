package com.lingsatuo.service;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.lingsatuo.callbackapi.ToBeContinue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 15176 on 2017/9/19.
 */

public class CreateJSAccessibilityService extends AccessibilityService {
    public static AccessibilityEvent accessibilityEvent;
    private static Map<String,List<ToBeContinue>> onAccessibilityEvents = new HashMap<>();
    private static Map<String,List<ToBeContinue>> onKeyEventListeners = new HashMap<>();

    Set<Map.Entry<String, List<ToBeContinue>>> access_set;
    Iterator<Map.Entry<String, List<ToBeContinue>>> access_iterator ;
    Set<Map.Entry<String, List<ToBeContinue>>> key_set;
    Iterator<Map.Entry<String, List<ToBeContinue>>> key_iterator ;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        access_set = onAccessibilityEvents.entrySet();
        access_iterator = access_set.iterator();
        while (access_iterator.hasNext()) {
            Map.Entry<String, List<ToBeContinue>> entry = access_iterator.next();
            List<ToBeContinue> access_list = entry.getValue();
            for (ToBeContinue toBeContinue: access_list){
                if (toBeContinue!=null){
                    toBeContinue.T2(accessibilityEvent);
                }
            }
        }
        this.accessibilityEvent = accessibilityEvent;
    }

    @Override
    public void onInterrupt() {
        access_set = onAccessibilityEvents.entrySet();
        access_iterator = access_set.iterator();
        while (access_iterator.hasNext()) {
            Map.Entry<String, List<ToBeContinue>> entry = access_iterator.next();
            List<ToBeContinue> access_list = entry.getValue();
            for (ToBeContinue toBeContinue: access_list){
                if (toBeContinue!=null){
                    toBeContinue.T2(accessibilityEvent);
                }
            }
        }
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }
    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        key_set = onKeyEventListeners.entrySet();
        key_iterator = key_set.iterator();
        while (key_iterator.hasNext()) {
            Map.Entry<String, List<ToBeContinue>> entry = key_iterator.next();
            List<ToBeContinue> access_list = entry.getValue();
            for (ToBeContinue toBeContinue: access_list){
                if (toBeContinue!=null){
                    toBeContinue.T2(event);
                }
            }
        }
        return super.onKeyEvent(event);
    }
    public static void addAccEventListener(String tag,ToBeContinue litener){
        if (!onAccessibilityEvents.containsKey(tag)){
            List<ToBeContinue> list = new ArrayList<>();
            list.add(litener);
            onAccessibilityEvents.put(tag,list);
            addAccEventListener(tag,litener);
            return;
        }
        List<ToBeContinue> list = onAccessibilityEvents.get(tag);
        list.add(litener);
    }
    public static void addKeyEventListener(String tag,ToBeContinue litener){
        if (!onKeyEventListeners.containsKey(tag)){
            List<ToBeContinue> list = new ArrayList<>();
            list.add(litener);
            onKeyEventListeners.put(tag,list);
            addKeyEventListener(tag,litener);
            return;
        }
        List<ToBeContinue> list = onKeyEventListeners.get(tag);
        list.add(litener);
    }
    public static void removeAccEventListener(String tag,ToBeContinue toBeContinue){
        List list = onAccessibilityEvents.get(tag);
        if (list == null)return;
        for (int a = 0 ; a < list.size();a++){
            if (list.get(a).equals(toBeContinue)){
                list.set(a,null);
            }
        }
    }
    public static void removeKeyEventListener(String tag,ToBeContinue toBeContinue){
        List list = onKeyEventListeners.get(tag);
        if (list == null)return;
        for (int a = 0 ; a < list.size();a++){
            if (list.get(a).equals(toBeContinue)){
                list.set(a,null);
            }
        }
    }
    public static void removeAllAccEventListener(String tag){
        onKeyEventListeners.put(tag,new ArrayList<ToBeContinue>());
    }
    public static void removeAllKeyEventListener(String tag){
        onKeyEventListeners.put(tag,new ArrayList<ToBeContinue>());
    }
}
