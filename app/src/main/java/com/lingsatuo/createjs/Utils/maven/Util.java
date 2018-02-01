package com.lingsatuo.createjs.Utils.maven;

import android.content.Context;
import android.os.Environment;

import com.lingsatuo.adapter.Maven;
import com.lingsatuo.error.CreateJSException;
import com.lingsatuo.utils.Libs;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Util {
    private Context context;

    private static List<Libs> list = new ArrayList<>();

    public List<Maven> getMaven(Context context) {
        this.context = context;
        List<File> paths = new ArrayList<>();
        File[] js_maven = new File(Utils.getLibsPath()).listFiles();
        File[] user_maven = new File(new File(Utils.getLibsPath()).getParent() + "/user_libs").listFiles();
        if (js_maven != null)
            for (File file : js_maven) {
                if (!file.isFile())
                    paths.add(file);
            }
        if (user_maven != null)
            for (File file : user_maven) {
                if (!file.isFile())
                    paths.add(file);
            }
        return getMaven(paths);
    }

    private List<Maven> getMaven(List<File> paths) {
        List<Maven> mavens = new ArrayList<>();
        if (paths == null) return mavens;
        for (File path : paths) {
            Maven maven = new Maven();
            maven.setTile(path.getName());
            maven.setContext(context);
            maven.setPath(path.getPath());

            if (new File(path.getPath() + "/libs.propres").exists()) {
                if (path.getParentFile().getName().equals("js_libs")) {
                    maven.setType(Maven.TYPE_JS_MAVEN);
                } else {
                    maven.setType(Maven.TYPE_USER_MAVEN);
                }
            } else {
                maven.setType(Maven.TYPE_ERROR_MAVEN);
            }
            mavens.add(maven);
        }
        return mavens;
    }

    public static List<Libs> getMaven(MavenProject project, boolean deleteold) throws Exception {
        if (deleteold){
            list = new ArrayList<>();
        }
        Libs lib2 = new Libs();
        lib2.name = new File(project._getRootDir()).getName();
        lib2.uri = "true";//添加error字段
        list.add(lib2);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Utils.readStringFromFile(project._getRootDir()+"/libs.propres"));
        } catch (JSONException e) {
            throw new CreateJSException(e + "\n at " + project._getRootDir()+"/libs.propres");
        }
        JSONArray libs = null;
        try {
            libs = jsonObject.getJSONArray("libs");
        } catch (JSONException e) {
        }
        if (libs != null) {
            for (int a = 0; a < libs.length(); a++) {
                JSONObject obj = libs.getJSONObject(a);
                String maven_name = null;
                try {
                    maven_name = obj.getString("name");
                } catch (JSONException E) {
                }
                if (maven_name == null) return list;
                String path = Utils.getLibsPropresPath(maven_name);//库文件
                if (maven_name.equals(new File(project._getRootDir()).getName())) {
                    throw new CreateJSException("A library cannot depend on itself  :  " + maven_name);
                }






                String down = Environment.getExternalStorageDirectory() + "/.CreateJS/download";
                File file = new File(path);//库配置文件
                final File file1 = new File(down + "/" + maven_name + ".zip");
                String maven_uri = null;
                try {
                    maven_uri = obj.getString("uri");
                } catch (JSONException e) {
                }
                if (!file.exists()) {  //库不存在
                    String n_p = Utils.getLibsUserPropresPath(new File(file.getParent()).getName());
                    if (new File(n_p).exists()) {
                        //如果user_libs里存在
                        Libs lib = new Libs();
                        lib.name = maven_name;
                        lib.uri = "true";//添加error字段
                        list.add(lib);
                        getMaven(new MavenProject(new File(n_p).getParent()), false);//再次遍历
                    } else {
                        if (!file1.exists() || !file1.isFile()) { //库对应的压缩包不存在
                            Libs lib = new Libs();
                            lib.name = maven_name;
                            if (maven_uri == null) {//下载地址不存在
                                lib.uri = "false";//添加error字段
                            } else {
                                lib.uri = maven_uri;//添加下载地址
                            }
                            list.add(lib);
                        } else {//对应的压缩包存在
                            Libs lib = new Libs();
                            lib.name = maven_name;
                            lib.uri = "true";//添加error字段
                            list.add(lib);
                            ZipUtils.loadZip(file1.getPath(), Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + Utils.getFileNameNoEx(file1.getName()));
                        }
                    }
                } else {//库存在
                    getMaven(new MavenProject(file.getParent()), false);//再次遍历
                }
            }
        }
        return list;
    }
}
