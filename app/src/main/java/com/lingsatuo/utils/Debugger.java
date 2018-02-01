package com.lingsatuo.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableScript;

/**
 * Created by 15176 on 2017/7/3.
 */

public class Debugger implements org.mozilla.javascript.debug.Debugger {
    @Override
    public void handleCompilationDone(Context context, DebuggableScript debuggableScript, String s) {
    }

    @Override
    public DebugFrame getFrame(Context context, DebuggableScript debuggableScript) {
        return null;
    }
}
