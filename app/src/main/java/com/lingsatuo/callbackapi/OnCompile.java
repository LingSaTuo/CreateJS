package com.lingsatuo.callbackapi;

import org.mozilla.javascript.RhinoException;

/**
 * Created by 15176 on 2017/9/19.
 */

public abstract class OnCompile {
    public abstract void Success();
    public abstract void Failure(Throwable e);
}
