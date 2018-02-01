package codeedit.lingsatuo.com.project;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import codeedit.lingsatuo.com.compiler.Compiler;
import codeedit.lingsatuo.com.compiler.onCompiler;
import codeedit.lingsatuo.com.exception.IllegalProjectException;

/**
 * Created by Administrator on 2017/12/3.
 */

public class Project {
    public enum TYPE{
        JS,THEME,ZIP,ANDROID
    }
    private String rootDir;
    private String name;
    private String main;
    private String _out;
    private String buildj;
    private String buildt;
    private String out;
    protected List<File> finish;
    private TYPE type;
    private boolean debug = true;
    private Back back;
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    public Project(String rootDir){//传入工程根目录
        this.rootDir = rootDir;
        _setBuildJ(rootDir+"/build.js");
        _setBuildT(rootDir+"/build.txt");
        this.name = getRootName();
        finish = new ArrayList<>();
    }
    public void _setBuildT(String buildT){
        this.buildt = buildT;
    }
    public String _getBuildT(){
        return this.buildt;
    }
    public void _setBuildJ(String build){
        this.buildj = build;
    }
    public String _getBuildJ(){
        return this.buildj;
    }
    public void setName(String name){//设置工程的名字
        this.name = name;
    }
    public String getName(){//获取工程的名字
        return this.name;
    }
    public void setOutPutPath(String path){
        this.out = path;
        _setOutPutPath(_getRootDir()+"/"+path);
    }
    public String getOutPutPath(){
        return this.out;
    }
    public void setMainPath(String path){//设置工程相对RootDir入口
        this.main = path;
        setOutPutPath("build/"+path);
    }
    public String getMainPath(){//获取工程相对RootDir入口
        return this.main;
    }

    public void _setOutPutPath(String out){//设置工程出口
        this._out = out;
    }
    public String _getOutPath(){//获取工程出口
        return this._out;
    }

    public Back getBack(){
        return this.back;
    }
    public String _getRootDir(){
        return this.rootDir;
    }
    public String _getBuildDir(){
        return _getRootDir()+"/build";
    }

    public void Compiler(boolean run)throws IllegalProjectException{
        Compiler.getInstance(this).start(run);
    }
    public void setType(TYPE type){
        this.type = type;
    }
    public TYPE getType(){
        return this.type;
    }
    public void createNewOne(Context context,Back back) throws IOException {
        this.back = back;
       new Copy(context,this).createTree(back);
    }
    public void setBack(Back back){
        this.back = back;
    }
    public void reBuild(){
        try {
            Compiler(false);
            this.back.T(this,null,"");
        }catch (IllegalProjectException e){
            if (this.back!=null){
                this.back.T(this,e,"");
            }
        }
    }
    private String getRootName(){
        String filename = rootDir;
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return "";
    }
    public void Log(IllegalProjectException e) {
        if (debug)
        Log.e("Compiler","Error id :" +e.getId()+ "   Message  : "+e.getMessage());
    }
    public void start(){
        if (getBack()!=null){
            getBack().T(this,null,"start");
        }
    }
    public void Log(String e) {
            Log.e("Compiler",e);
    }
    public boolean onStart() {
        finish = new ArrayList<>();
        return true;
    }

    public void onStop(IllegalProjectException e) {
        Log(e);
    }

    public void onErr(IllegalProjectException e) {
        Log(e);
    }

    public boolean onWaring(IllegalProjectException e) {
        Log(e);
        return true;
    }

    public void onFinish() {

    }
    public void onFinally(){
        onFinish();
    }

    public boolean onCompilering(onCompiler project) throws IllegalProjectException {
        Log.i("onCompiler","path : "+ project.getPath()+"  id  "+  project.getProgress());
        IllegalProjectException illegalProjectException = null;
        File f = new File(project.getPath());
        if (!f.exists()){
            illegalProjectException = new IllegalProjectException("File : "+project.getPath() +" is not exists");
            illegalProjectException.setId(2000);
            throw illegalProjectException;
        }
        if (project.getPath().equals(new File(_getBuildJ())))return false;
        if (project.getPath().equals(new File(_getBuildT())))return false;
        if (project.getPath().equals(_getOutPath()))return false;
        finish.add(f);
        if (!f.isFile()) return false;
        return true;
    }
    public boolean inProject(String path){
        File file = new File(path);
        File root = new File(_getRootDir());
        while((file = file.getParentFile())!=null){
            if (file.getPath().equals(root.getPath()))return true;
        }
        return false;
    }
}
