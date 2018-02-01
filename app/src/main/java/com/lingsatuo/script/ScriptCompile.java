package com.lingsatuo.script;

import com.lingsatuo.CreateRunApplication.BaseMain;
import com.lingsatuo.callbackapi.OnCompile;
import com.lingsatuo.openapi.Files;
import com.mojang.minecraftpe.MainActivity;

import org.mozilla.javascript.RhinoException;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by 15176 on 2017/9/19.
 */

public class ScriptCompile{
    public final static String DEFAULT_PATH = new Files().getSdcardPath()+"/.CreateJS/require";
    protected void compileReader(Reader reader , OnCompile onCompile){
        Thread thread = new Thread(() -> {
            try {
                ScriptTool.getInstance().getContext().compileReader(reader,"<java.io.Reader>Code Compile",1,null);
                send(onCompile,null);
            } catch (Throwable e) {
                send(onCompile,e);
            }
        });
        thread.start();}
    protected void compileString(String script , OnCompile onCompile){
        Thread thread = new Thread(() -> {
            try {
                ScriptTool.getInstance().getContext().compileString(script,"<java.lang.String>Code Compile",1,null);
                send(onCompile,null);
            } catch (Throwable e) {
                send(onCompile,e);
            }
        });
        thread.start(); }
    protected void send(OnCompile oc,Throwable e){
        if (oc==null)return;
        ScriptTool.getInstance().getApp().runOnUiThread(() -> {
            if (e==null) {
                oc.Success();
                return;
            }
            oc.Failure(e);
        });
    }
}
