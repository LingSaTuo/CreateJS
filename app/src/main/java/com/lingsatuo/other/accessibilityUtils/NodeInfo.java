package com.lingsatuo.other.accessibilityUtils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/25.
 */

public class NodeInfo {
    public static String DEF_PACK = "";
    private AccessibilityNodeInfo accessibilityNodeinfo;

    public NodeInfo(AccessibilityNodeInfo accessibilityNodeinfo){
        this.accessibilityNodeinfo = accessibilityNodeinfo;
    }

    public AccessibilityNodeInfo getNodeInfo(){
        return this.accessibilityNodeinfo;
    }
    public List<NodeInfo> findViewByText(String text){
        List<AccessibilityNodeInfo> list = getNodeInfo().findAccessibilityNodeInfosByText(text);
        return get(list);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<NodeInfo> findViewById(String viewId){
        List<AccessibilityNodeInfo> list = getNodeInfo().findAccessibilityNodeInfosByViewId(viewId);
        return get(list);
    }
    public CharSequence getPackageName(){
        return getNodeInfo().getPackageName();
    }

    public NodeInfo getParent(){
        if (getNodeInfo().getParent()==null) return null;
        return new NodeInfo(getNodeInfo().getParent());
    }
    public NodeInfo getChild(int index){
        if (getNodeInfo().getChild(index)==null) return null;
        return new NodeInfo(getNodeInfo().getChild(index));
    }
    public int getChildCount(){
        return getNodeInfo().getChildCount();
    }

    public CharSequence getText(){
        return getNodeInfo().getText();
    }
   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
   public String getViewName(){
       return getNodeInfo().getViewIdResourceName();
   }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public WindowInfo getWindow(){
       if (getNodeInfo().getWindow()==null) return null;
       return new WindowInfo(getNodeInfo().getWindow());
   }

   public boolean isChecked(){
       return getNodeInfo().isChecked();
   }
    public boolean isClickable(){
        return getNodeInfo().isClickable();
    }
   public String toString(){
       return getNodeInfo().toString();
   }

   public void recycle(){
       getNodeInfo().recycle();
   }
   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
   public void refresh(){
       getNodeInfo().refresh();
   }
   public boolean performAction(int a){
       return getNodeInfo().performAction(a);
   }
   public boolean click(){
       return performAction(AccessibilityNodeInfo.ACTION_CLICK);
   }
   public boolean copy(){
       return performAction(AccessibilityNodeInfo.ACTION_COPY);
   }
   public boolean cut(){
       return performAction(AccessibilityNodeInfo.ACTION_CUT);
   }
    public boolean longClick(){
        return performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }
    public boolean paste(){
        return performAction(AccessibilityNodeInfo.ACTION_PASTE);
    }
    public boolean scrollUp(){
        return performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }
    public boolean scrollDown(){
        return performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }
   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
   public NodeInfo findFocus(int focus){
       if (getNodeInfo().findFocus(focus)==null) return null;
       return new NodeInfo(getNodeInfo().findFocus(focus));
   }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public NodeInfo focusSearch(int direction){
        if (getNodeInfo().focusSearch(direction)==null) return null;
        return new NodeInfo(getNodeInfo().focusSearch(direction));
    }


    private  List<NodeInfo> get(List<AccessibilityNodeInfo> a){
        List<NodeInfo> nodeInfos = new ArrayList<>();
        for (AccessibilityNodeInfo accessibilityNodeInfo : a){
            if (accessibilityNodeInfo!=null)
            nodeInfos.add(new NodeInfo(accessibilityNodeInfo));
        }
        return nodeInfos;
    }
}
