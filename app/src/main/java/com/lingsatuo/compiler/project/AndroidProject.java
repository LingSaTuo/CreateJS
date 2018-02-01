package com.lingsatuo.compiler.project;

import android.app.Activity;

import com.kellinwood.security.zipsigner.ZipSigner;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.openapi.Download;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;
import com.zzzmode.apkeditor.ApkEditor;
import com.zzzmode.apkeditor.apksigner.KeyHelper;

import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import codeedit.lingsatuo.com.compiler.onCompiler;
import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Back;
import codeedit.lingsatuo.com.project.FileUtils;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2018/1/1.
 */

public class AndroidProject extends Project {
    private Activity activity;
    private Dialog.A da;
    private String packagename = "com.lingsatuo.myapp";
    private String appName="MyApp";
    private int versionCode = 1;
    private String MainText = _getRootDir()+"/main/Main.txt";
    private String versionName = "1.0";
    private List<String> permission = new ArrayList<>();

    public AndroidProject(String rootDir, Back back, Activity activity) {
        super(rootDir);
        setType(TYPE.ANDROID);
        setBack(back);
        this.activity = activity;
        da = new Dialog(0).LoadingDialog(activity).setMessage(activity.getApplicationContext().getString(R.string.s_110))
                .setTile(activity.getApplicationContext().getString(R.string.s_109));
    }
    public void setAppName(String name){
        this.appName = name;
    }
    public String getAppName(){
        return this.appName;
    }
    public int getVersionCode(){
        return this.versionCode;
    }
    public String getVersionName(){
        return this.versionName;
    }
    public void setVersionCode(int code){
        this.versionCode = code;
    }
    public void setVersionName(String name){
        this.versionName = name;
    }

    public void setPackagename(String name) {
        this.packagename = name;
    }

    public String getPackagename() {
        return this.packagename;
    }

    public AndroidProject addPermission(String permission) {
        this.permission.add(permission);
        return this;
    }

    public List<String> getPermission() {
        return this.permission;
    }

    public void clearPermission() {
        this.permission.clear();
    }

    boolean finished = false;

    @Override
    public boolean onStart() {
        finished = false;
        try {
            clearPermission();
            JSONObject jsonObject = new JSONObject(Utils.readStringFromFile(MainText));
            setPackagename(jsonObject.getString("package"));
            setAppName(jsonObject.getString("appname"));
            setVersionCode(jsonObject.getInt("versioncode"));
            setVersionName(jsonObject.getString("versionname"));
            try{
                JSONArray jsonArray = jsonObject.getJSONArray("permission");
                for (int aa = 0;aa<jsonArray.length();aa++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(aa);
                    addPermission(jsonObject1.getString(""));
                }
            }catch (JSONException e){}
        } catch (Exception e) {
            e.printStackTrace();
            onErr(new IllegalProjectException(e.getMessage()));
            return false;
        }
        return super.onStart();
    }

    public void monStart() {
        da = new Dialog(0).LoadingDialog(activity)
                .setMessage(activity.getApplicationContext().getString(R.string.s_26))
                .showProgrss(true)
                .setTile(activity.getApplicationContext().getString(R.string.s_109));
        da.show();
        try {
            String apk = Utils.getSD() + "/.CreateJS/data/Project/APP_V7.cip";
            if (new File(apk).exists()) {
                da.setMessage(activity.getApplicationContext().getString(R.string.s_112));
                print("----loadZip---");
                ZipUtils.loadZip(apk, _getBuildDir() + "/" + getName(), true);
                print("----loadZipFinish---");
                finished = true;
            } else {
                new Download(activity, "http://createjs-1253269015.coscd.myqcloud.com/project/APP_V7.cip", apk, (progress, max, finish, e) -> {
                    if (e != null) {
                        da.setMessage(e.getMessage());
                        da.showProgrss(false);
                    } else {
                        da.setMax(max);
                        da.setProgress((int) progress);
                    }
                    if (finish) {
                        try {
                            da.setMessage(activity.getApplicationContext().getString(R.string.s_112));
                            ZipUtils.loadZip(apk, _getBuildDir() + "/" + getName(), true);
                            finished = true;
                        } catch (Exception e1) {
                            onErr(new IllegalProjectException(e.getMessage()));
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            finished = true;
            onErr(new IllegalProjectException(e.getMessage()));
        }
        while(!finished);
    }

    @Override
    public void onErr(IllegalProjectException e) {
        da.setMessage(e.getMessage()+"\nid :"+e.getId()).
        showProgrss(false).show();
        super.onErr(e);
    }

    @Override
    public boolean onCompilering(onCompiler project) throws IllegalProjectException {
        da.setMessage(project.getPath());
        return super.onCompilering(project);
    }

    @Override
    public void onFinish() {
        new Thread(() -> {
            try {
                try {
                    da.dismiss();
                    monStart();
                    da = da.setMessage(activity.getApplicationContext().getString(R.string.s_110))
                            .setTile(activity.getApplicationContext().getString(R.string.s_109));
                    da.show();
                    makeBuild();
                    boolean b = buildAXML();
                    if (b) {
                        if (getBack() != null)
                            activity.runOnUiThread(() -> getBack().T(this, null, "run"));
                        da.dismiss();
                    } else {
                        da.setMessage("Build apk or SignApk error")
                                .setTile(activity.getApplicationContext().getString(R.string.s_110));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   onErr(new IllegalProjectException(e.getMessage()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                onErr(new IllegalProjectException(e.getMessage()));
            }
        }).start();
        super.onFinish();
    }

    @Override
    public void onFinally() {
        da.dismiss();
        if (getBack() != null)
            getBack().T(this, null, "finally");
    }

    private void makeBuild() throws IOException {
        da = da.setMessage(activity.getApplicationContext().getString(R.string.s_113))
                .setTile(activity.getApplicationContext().getString(R.string.s_109));
        print("----makeBuild---");
        new Files().copy(_getRootDir() + "/main/res", _getBuildDir() + "/" + getName() + "/assets");
        new Files().copy(_getRootDir() + "/main/script", _getBuildDir() + "/" + getName() + "/assets");
        new Files().copy(_getRootDir() + "/main/super/res", _getBuildDir() + "/" + getName());
        if (!new File(_getBuildDir() + "/" + getName() + "/assets/android/ez4android.js").exists()) {
            FileUtils.getInstance(activity.getApplicationContext()).copyAssetsToSD("android", _getBuildDir() + "/" + getName() + "/assets/android");
        }
    }


    private void wirte() throws Exception {
        da = da.setMessage(activity.getApplicationContext().getString(R.string.s_114))
                .setTile(activity.getApplicationContext().getString(R.string.s_109));
        print("----write---");
        String name = new Files().getNameWithOutExtension(_getOutPath()) + "_signer.apk";
        String path = new File(_getOutPath()).getParent() + "/" + name;//输出的apk路径
        org.apache.tools.ant.Project prj = new org.apache.tools.ant.Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(_getOutPath()));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(new File(_getRootDir() + "/build/" + getName()));
        zip.addFileset(fileSet);
        zip.execute();
        ZipSigner signer=new ZipSigner();
        da.setMessage(activity.getApplicationContext().getString(R.string.s_111))
                .setTile(activity.getApplicationContext().getString(R.string.s_110));
        signer.setKeymode("testkey");
        final File napk=new File(path);
        if (napk.exists()) napk.delete();
        napk.createNewFile();
        signer.signZip(_getOutPath(), napk.getPath());
        new File(_getOutPath()).delete();//删除需要被签名的文件
        new File(path).renameTo(new File(_getOutPath()));//签名完成的文件改名到未签名文件名字
        print("----writeFinish---");
        da = da.setMessage(activity.getApplicationContext().getString(R.string.s_115))
                .setTile(activity.getApplicationContext().getString(R.string.s_109));
    }

    private boolean buildAXML() throws Exception {
        print("----build---");
        File main = new File(_getRootDir() + "/main/super/Manifest.xml");
        File appmain = new File(_getBuildDir() + "/" + getName() + "/AndroidManifest.xml");
        new Files().copy(main, appmain);
        ApkEditor editor = new ApkEditor(KeyHelper.privateKey, KeyHelper.sigPrefix);
        //editor.setAXml(_getBuildDir() + "/"  + getName() +  "/AndroidManifest.xml");
        editor.clearPermission();
        editor.setInAXml(main.getAbsolutePath());
        editor.setOutAXml(appmain.getAbsolutePath());
        editor.setAppName(getAppName());//设置要修改的app名称
        editor.setPackagename(getPackagename());//设置包名
        editor.setVersionCode(getVersionCode());//设置版本
        editor.setVersionName(getVersionName());//设置版本名称
        for (String s:permission){
            editor.addPermission(s);//新增权限
        }
        boolean b = editor.createAXML();
        wirte();
        return b;
    }
    public void rebuildMainTxt(){
        try {
            String s = Utils.readStringFromFile(MainText);
            s = s.replaceAll("\\{PACKAGE\\}",getPackagename())
                    .replaceAll("\\{APP\\}",getAppName())
                    .replaceAll("\\{CODE\\}",getVersionCode()+"")
                    .replaceAll("\\{NAME\\}",getVersionName());
            Utils.saveToFile(MainText,s);
        } catch (CreateJSIOException e) {
            e.printStackTrace();
            onErr(new IllegalProjectException(e.getMessage()));
        }
    }
    private static void print(String message){
        System.out.println("AndroidProject----"+message);
    }
}
