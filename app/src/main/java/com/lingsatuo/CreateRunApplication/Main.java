package com.lingsatuo.CreateRunApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lingsatuo.Dialog.StartActivity;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.App;
import com.lingsatuo.other.accessibilityUtils.Accessibility;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 15176 on 2017/9/19.
 */

public class Main extends MAIN {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.applicationExc.App.init(this);
    }

    public String getPackageName(){
        try {
            String na = Utils.readDateFromApp(this,"settings");
            return new JSONObject(na).getString("package_name");
        } catch (CreateJSIOException e) {
            return "com.lingsatuo.createjs";
        } catch (JSONException e) {
            return "com.lingsatuo.createjs";
        }
    }
    public void startActivity(final Intent i ){
        new StartActivity(this, i, (dialogInterface, witch) -> Main.super.startActivity(i));
    }
    public void setObj2JS(String str,Object obj){
        ScriptTool.getInstance().setObj2JS(str,obj);
    }

    public int getAppBuildVersion(){
        return new App(this).getVersionCode();
    }


    public void startAS(){
         Accessibility.isServiceEnabled(this,true);
    }
    public void useEZ(){
        ScriptTool.getInstance().getContext().evaluateString(ScriptTool.getInstance().getScope(),
                "const CApp = com.lingsatuo.openapi.App;" +
                        "const CDialog = com.lingsatuo.openapi.Dialog;" +
                        "const CDownload = com.lingsatuo.openapi.Download;" +
                        "const CFiles = com.lingsatuo.openapi.Files;" +
                        "const CObject = com.lingsatuo.openapi.Object;" +
                        "const CScriptTool = com.lingsatuo.script.ScriptTool;" +
                        "const CActivity = com.lingsatuo.createjs.ActivityForJs;" +
                        "const CR = com.lingsatuo.createjs.R;" +
                        "const Jsoup = org.jsoup.Jsoup",
                "EZ",
                1,null);
    }
}
