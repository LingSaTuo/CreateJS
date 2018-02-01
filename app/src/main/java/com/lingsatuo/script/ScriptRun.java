package com.lingsatuo.script;

import android.os.Environment;

import com.lingsatuo.CreateRunApplication.BaseMain;
import com.lingsatuo.callbackapi.ScriptLoading;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSException;
import com.lingsatuo.listener.OnScriptException;
import com.lingsatuo.utils.Obj2JS;
import com.lingsatuo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15176 on 2017/9/19.
 */

public class ScriptRun extends ScriptCompile {
    private OnScriptException onScriptException;
    private static ScriptRun currentRun;
    private ScriptLoading scriptLoading;

    public static ScriptRun getCurrentRun() {
        return currentRun;
    }

    void ScriptRun() {
        currentRun = this;
    }

    protected void execution() {
        List<Obj2JS> obj2JSes = ScriptTool.getInstance().getObj2JSes();
        execution(obj2JSes);
    }

    public void execution(List<Obj2JS> o) {
        for (Obj2JS obj : o) {
            ScriptableObject.putProperty(ScriptTool.getInstance().getScope(), obj.str, Context.javaToJS(obj.obj, ScriptTool.getInstance().getScope()));
        }
    }

    public void operation(String[] paths) {
        List<Script> scripts = new ArrayList<>();
        for (String s : paths) {
            Script script = new Script();
            script.name = s;
            try {
                script.bufferedReader = Utils.getBufferedReaderFromPath(s);
                scripts.add(script);
            } catch (CreateJSException e) {
                if (ScriptTool.getInstance().getApp() instanceof BaseMain)
                    ((BaseMain) ScriptTool.getInstance().getApp()).sendMessage("错误", e.getMessage(), "#ff0000");
                return;
            }
        }

        runScript(scripts, null);
    }

    public void removeListener() {
        this.scriptLoading = null;
    }

    public ScriptLoading getScriptLoadingListener() {
        return this.scriptLoading;
    }

    protected void runScript(List<Script> scriptes, ScriptLoading scriptLoading) {
        this.scriptLoading = scriptLoading;
        int a = 0;
        for (Script entity : scriptes) {
            final int aa = a;
            a++;
            try {
                ScriptTool.getInstance().getApp().runOnUiThread(() -> {
                    if (this.scriptLoading != null) {
                        this.scriptLoading.onLoading(aa, entity.name);
                    }
                });
                ScriptTool.getInstance().getContext().evaluateReader(
                        ScriptTool.getInstance().getScope(), entity.bufferedReader, new File(entity.name).getName(), 1, null);
                entity.bufferedReader.close();
            } catch (RhinoException e) {
                ScriptTool.getInstance().getApp().runOnUiThread(() -> {
                    if (onScriptException != null) {
                        onScriptException.Failure(new Exception(e.getMessage() +"\nat : "+ e.lineSource()+" . \nScript path :\n   " + entity.name + "\n"));
                    }
                });
            } catch (Exception e) {
                ScriptTool.getInstance().getApp().runOnUiThread(() -> {
                    if (onScriptException != null) {
                        onScriptException.Failure(new Exception(e.getMessage() + " . \nScript path :\n   " + entity.name + "\n"));
                    }
                });
            }
        }
        ScriptTool.getInstance().getApp().runOnUiThread(() -> {
            if (this.scriptLoading != null)
                this.scriptLoading.finish();

            if (ScriptTool.getInstance().getApp() instanceof BaseMain) {
                BaseMain bm = (BaseMain) ScriptTool.getInstance().getApp();
                bm.getAllFunction();
            }
        });
    }


    public void setOnScriptExcption(OnScriptException onScriptExcption) {
        this.onScriptException = onScriptExcption;
    }


    public void loadMaven() throws CreateJSException, JSONException {
        StringBuilder sb_maven = new StringBuilder();
        ScriptTool.getInstance().rebuildMaven();
        String str = "";
            str = Utils.readDateFromApp(ScriptTool.getInstance().getApp(), "maven");
        sb_maven.append("[");
        if (str == null || str == "") {
            onScriptException.Failure(new Throwable("加载时错误,库依赖加载失败，请重试，并检查库是否合法。"));
            return;
        }
        ;
        for (String maven : str.split("\n")) {
            if (maven.equals("*" + ScriptTool.getInstance().getApp().getResources().getString(R.string.s_34) + "*")) {
                break;
            }
            String path = Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + maven;
            if (!new File(path + "/libs.propres").exists() || !new File(path + "/libs.propres").isFile()) {
                path = Environment.getExternalStorageDirectory() + "/.CreateJS/libs/user_libs/" + maven;
                if (!new File(path + "/libs.propres").exists() || !new File(path + "/libs.propres").isFile()) {
                    throw new CreateJSException("The damaged library : " + maven);
                }
            }
            JSONObject object = new JSONObject(Utils.readStringFromFile(path + "/libs.propres"));
            JSONArray jses = null;
            try {
                jses = object.getJSONArray("include");
            } catch (JSONException e) {
                throw new JSONException(e.getMessage() + ".  Maven is empty   (Maven :" + maven + " )");
            }
            try {
                JSONArray jsess = object.getJSONArray("info");
                ScriptTool.getInstance().getMavenMessage().append(maven + "\n----------\n" + "开发者 ：" + jsess.getJSONObject(0).getString("The_creator"));
                ScriptTool.getInstance().getMavenMessage().append("\n修正者 ：" + jsess.getJSONObject(0).getString("Correction"));
                ScriptTool.getInstance().getMavenMessage().append("\n服务平台 ：" + jsess.getJSONObject(0).getString("for"));
                ScriptTool.getInstance().getMavenMessage().append("\n版本  ：" + jsess.getJSONObject(0).getString("version"));
                ScriptTool.getInstance().getMavenMessage().append("\n版权  ：" + jsess.getJSONObject(0).getString("Copyright") + "\n\n");
            } catch (Exception e) {
            }
            for (int js_a = 0; js_a < jses.length(); js_a++) {
                String namee = "";
                try {
                    JSONObject include = jses.getJSONObject(js_a);
                    namee = include.getString("name");
                    if (!new File(path + "/" + namee).exists() || !new File(path + "/" + namee).isFile())
                        throw new CreateJSException();
                    List<String> s = new ArrayList<String>();
                    s.add(path + "/" + namee);
                    ScriptTool.getInstance().create(s);
                } catch (CreateJSException e) {
                    throw new CreateJSException("The damaged library : " + maven + ".\n  Because unable to find  " + namee + "   , but " + namee + " exists in the manifest file, please check");
                }
            }
            sb_maven.append("\"" + maven + "\",");
        }
        sb_maven.deleteCharAt(sb_maven.length() - 1);
        sb_maven.append("]");
        BaseMain.inthis.sb_maven = sb_maven;
    }
}
