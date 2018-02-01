package com.lingsatuo.openapi;


import com.lingsatuo.listener.Events;
import com.lingsatuo.listener.OnKeyEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 15176 on 2017/9/25.
 */

public class Event {
    private Map<String,Events> listener  = new HashMap<>();
    public void onKeyDown(String key_name, OnKeyEventListener eventlistener){
        Events events = listener.get(key_name);
        if (events==null){
            events = new Events();
            listener.put(key_name,events);
        }
        events.addDown(eventlistener);
    }

    public void onKeyUp(String key_name, OnKeyEventListener eventlistener){
        Events events = listener.get(key_name);
        if (events==null){
            events = new Events();
            listener.put(key_name,events);
        }
        events.addUp(eventlistener);
    }
    public void removeAllListener(String key){
        listener.put(key,new Events());
    }
}
