package com.lingsatuo.listener;

import android.view.accessibility.AccessibilityEvent;

import com.lingsatuo.other.accessibilityUtils.AccessibilityNodeinfo;

/**
 * Created by 15176 on 2017/9/25.
 */

public interface OnAccessibilityEvent {
    void OnChange(AccessibilityNodeinfo info);
}
