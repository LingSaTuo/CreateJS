package com.lingsatuo.compiler.project;

import com.lingsatuo.openapi.Files;

import java.io.File;
import java.io.IOException;

import codeedit.lingsatuo.com.compiler.onCompiler;
import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/13.
 */

public class JSProject extends Project {
    private Back back;
    public JSProject(String rootDir, Back back) {
        super(rootDir);
        setType(TYPE.JS);
        this.back = back;
        setBack(back);
    }
    @Override
    public boolean onStart() {
        return super.onStart();
    }

    @Override
    public void onStop(IllegalProjectException e) {
        super.onStop(e);
    }

    @Override
    public void onErr(IllegalProjectException e) {
        super.onErr(e);
    }

    @Override
    public boolean onWaring(IllegalProjectException e) {
        return super.onWaring(e);
    }

    @Override
    public void onFinish() {
        try {
            new Files().copy(_getRootDir()+"/"+getMainPath(),_getOutPath());
            if (this.back!=null)
                this.back.T(this,null,"run");
        } catch (IOException e) {
            if (this.back!=null)
                this.back.T(this,e,"");
        }
        super.onFinish();
    }

    @Override
    public void onFinally() {
        if (this.back!=null)
            this.back.T(this,null,"finally");
    }

    @Override
    public boolean onCompilering(onCompiler project) throws IllegalProjectException {
        Log(project.getPath());
        if (project.getPath().equals(new File(_getRootDir()+"/"+getMainPath()).getPath()))
            return super.onCompilering(project);
        if (new File(project.getPath()).isDirectory())return false;
        else return false;
    }

}
