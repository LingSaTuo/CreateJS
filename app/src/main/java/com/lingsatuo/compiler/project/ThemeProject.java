package com.lingsatuo.compiler.project;

import java.io.File;

import codeedit.lingsatuo.com.compiler.onCompiler;
import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ThemeProject extends Project {
    private Back back;
    public ThemeProject(String rootDir, Back back) {
        super(rootDir);
        setType(TYPE.THEME);
        setBack(back);
        this.back = back;
    }
    public boolean onStart() {
        return super.onStart();
    }

    public void onStop(IllegalProjectException e) {
        super.onStop(e);
    }

    public void onErr(IllegalProjectException e) {
        super.onErr(e);
    }

    public boolean onWaring(IllegalProjectException e) {
        return super.onWaring(e);
    }

    public void onFinish() {
        if (this.back!=null)
            this.back.T(this,null,"");
        super.onFinish();
    }

    public boolean onCompilering(onCompiler project) throws IllegalProjectException {
        Log(project.getPath());
        if (project.getPath().equals(new File(_getRootDir()+"/"+getMainPath()).getPath()))
            return super.onCompilering(project);
        else return false;
    }
}
