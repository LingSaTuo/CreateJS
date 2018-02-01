package com.lingsatuo.compiler.project;

import com.lingsatuo.openapi.Files;

import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

import codeedit.lingsatuo.com.compiler.onCompiler;
import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ZIPProject extends Project {
    private Back back;
    public ZIPProject(String rootDir, Back back) {
        super(rootDir);
        setType(TYPE.ZIP);
        setBack(back);
        this.back = back;
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
            new Files().removeDir(_getBuildDir());
            new File(_getBuildDir()).mkdirs();
            new Files().copy(_getRootDir()+"/main",_getRootDir()+"/build/"+getName());
            wirte();
            if (this.back!=null)
                this.back.T(this,null,"run");
        } catch (Exception e) {
            e.printStackTrace();
            this.back.T(this,e,"");
        }
        super.onFinish();
    }
    @Override
    public boolean onCompilering(onCompiler project) throws IllegalProjectException {
        Log(project.getPath());
        return super.onCompilering(project);
    }

    @Override
    public void onFinally() {
        if (this.back!=null)
            this.back.T(this,null,"finally");
    }

    private void wirte()throws Exception{
        org.apache.tools.ant.Project prj = new org.apache.tools.ant.Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(_getOutPath()));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(new File(_getRootDir()+"/build/"+getName()+"/main"));
        zip.addFileset(fileSet);
        zip.execute();
    }
}
