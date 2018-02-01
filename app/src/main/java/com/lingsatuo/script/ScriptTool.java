package com.lingsatuo.script;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;

import com.lingsatuo.CreateRunApplication.BaseMain;
import com.lingsatuo.callbackapi.OnCompile;
import com.lingsatuo.callbackapi.ScriptLoading;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.rhino.RhinoAndroidHelper;
import com.lingsatuo.script.require.ModuleSourceProvider;
import com.lingsatuo.utils.Obj2JS;
import com.lingsatuo.utils.Utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/19.
 */

public class ScriptTool extends ScriptRun{
    public static final String LogErrPath  = Environment.getExternalStorageDirectory()+"/.CreateJS/error/runtimeerr.log";;
    private Activity context;
    private static ScriptTool scriptTool;
    private Scriptable scriptable;
    private org.mozilla.javascript.Context ctx;
    private List<Obj2JS> obj2JSes;
    private List<Script> scripts;
    private StringBuilder sb = new StringBuilder();
    private List<URI> list;
    private List<String> paths;
    public static ScriptTool getInstance(){
        if (scriptTool==null) {
            scriptTool =  new ScriptTool();
        }
            return scriptTool;
    }
    private ScriptTool(){
        obj2JSes = new ArrayList<>();
        scripts = new ArrayList<>();
    }
    public void clearScript(){
        obj2JSes = new ArrayList<>();
        scripts = new ArrayList<>();
    }
    public ScriptTool Instance(Activity context){
        clearScript();
        this.context = context;
        ScriptRun();
        return scriptTool;
    }
    public void create(List<String> paths) throws CreateJSIOException {
        create(null,null,paths);
    }
    public void create(Scriptable scope, org.mozilla.javascript.Context context,List<String> paths) throws CreateJSIOException {
        this.paths = paths;
        refresh();
        if (context==null){
            if (Build.VERSION.SDK_INT>19) {
                this.ctx = new RhinoAndroidHelper(this.context).enterContext();
            }else{
                this.ctx = Context.enter();
            }
        }else{
            this.ctx = context;
            this.scriptable = scope;
        }
//        if (getScope()!=null)return;
        this.ctx.setOptimizationLevel(-1);
        if (Build.VERSION.SDK_INT>19) {
            this.ctx.setLanguageVersion(org.mozilla.javascript.Context.VERSION_ES6);
            ImporterTopLevel importerTopLevel = new ImporterTopLevel();
            this.scriptable = this.ctx.initStandardObjects(importerTopLevel, false);
        }else{
            this.scriptable = this.ctx.initStandardObjects();
        }
    }

    public void refresh() throws CreateJSIOException {
        for (String path:this.paths){
            Script script = new Script();
            script.bufferedReader = Utils.getBufferedReaderFromPath(path);
            script.name = path;
            scripts.add(script);
        }
    }

    public List<URI> getAllScript(List<String> paths) throws CreateJSIOException{
        return this.list;
    }

    private void buildRequire(){
        if (Build.VERSION.SDK_INT<=19) return;
        ModuleSourceProvider moduleSourceProvider = new ModuleSourceProvider(this.list);
        new RequireBuilder()
                .setModuleScriptProvider(new SoftCachingModuleScriptProvider(moduleSourceProvider))
                .setSandboxed(true)
                .createRequire(getContext(),getScope())
                .install(getScope());
    }

    public Scriptable getScope(){
        return this.scriptable;
    }
    public org.mozilla.javascript.Context getContext(){
        return this.ctx;
    }
    public void setObj2JS(String str,Object obj){
        Obj2JS obj1 = new Obj2JS();
        obj1.obj = obj;
        obj1.str = str;
        for(Obj2JS obj2JS:obj2JSes){
            if (obj2JS.str.equals(str))return;
        }
        obj2JSes.add(obj1);
    }
    public List<Obj2JS> getObj2JSes(){
        return this.obj2JSes;
    }
    @Override
    protected void compileReader(Reader reader, OnCompile onCompile) {
        super.compileReader(reader, onCompile);
    }

    @Override
    protected void compileString(String script, OnCompile onCompile) {
        super.compileString(script, onCompile);
    }
    public void run() throws IOException {
        run(null);
    }
    public void run(ScriptLoading scriptLoading){
        try {
            if (this.context!=null)
            getContext().evaluateString(getScope(),Utils.getAssets(this.context,"modules/importer.js"),"importer",1,null);
        } catch (IOException e) {
            if (getApp() instanceof BaseMain)
            ((BaseMain)ScriptTool.getInstance().getApp()).sendMessage("",e.toString());
        }
        run(scripts,scriptLoading);
    }
        final void run(List<Script>  entities,ScriptLoading scriptLoading) {
            this.list = new ArrayList<>();
            this.list.add(new File(ScriptCompile.DEFAULT_PATH).toURI());
            this.list.add(new File(this.paths.get(this.paths.size()-1)).getParentFile().toURI());
            buildRequire();
        super.runScript(entities,scriptLoading);
    }

    @Override
    public void execution() {
        super.execution();
    }

    public Activity getApp(){
        return this.context;
    }

    public StringBuilder getMavenMessage(){
        return this.sb;
    }
    public StringBuilder rebuildMaven(){
        return this.sb = new StringBuilder();
    }
    public void stopAll(){
        com.applicationExc.App.exitScript(null);
    }
    public void stop(){
        stopAll();
    }
    public int getScriptCount(){
        return this.scripts.size();
    }
}
