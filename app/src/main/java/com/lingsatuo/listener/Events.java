package com.lingsatuo.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/25.
 */

public class Events {
    private List<OnKeyEventListener> downlisteners = new ArrayList<>();
    private List<OnKeyEventListener> uplisteners = new ArrayList<>();
    public void addDown(OnKeyEventListener onKeyEventListener){
        downlisteners.add(onKeyEventListener);
    }
    public List<OnKeyEventListener> getDown(){
        return downlisteners;
    }
    public void addUp(OnKeyEventListener onKeyEventListener){
        uplisteners.add(onKeyEventListener);
    }
    public List<OnKeyEventListener> getUp(){
        return uplisteners;
    }
}
