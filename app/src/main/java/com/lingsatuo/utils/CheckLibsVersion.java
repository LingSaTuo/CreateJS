package com.lingsatuo.utils;

import android.app.Activity;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.lingsatuo.error.CreateJSException;
import com.lingsatuo.error.CreateJSIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 15176 on 2017/8/6.
 */

public class CheckLibsVersion {

    String[] libss = new String[]{"ModPE","ModPE_Dark"};
    Activity activity ;
    public CheckLibsVersion(Activity activity , String libs,OnUpdateListener listener){
        try {
            this.activity = activity;
            getVersion(libs,listener);
        } catch (CreateJSException e) {
            Log.e("OnUpDate",e.toString());
        }
    }
    private void getVersion(String libs ,OnUpdateListener listener) throws CreateJSException {
        File file = new File(Environment.getExternalStorageDirectory()+"/.CreateJS/libs/js_libs/"+libs+"/libs.propres");
                if (file.exists()){
                    getVersionCode(file.getPath(),listener);
        }

        File file2 = new File(Environment.getExternalStorageDirectory()+"/.CreateJS/libs/user_libs/"+libs+"/libs.propres");

                if (file2.exists()){
                    getVersionCode(file.getPath(),listener);
                }
    }
    private byte[] readIS(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer))!=-1){
            bos.write(buffer,0,len);
        }
        is.close();
        return bos.toByteArray();
    }
    private String getString(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        byte[] data = readIS(connection.getInputStream());
        return new String(data,"UTF-8");
    }
    boolean getLibsIsMe(String lib,String[] libs){
        for (String s : libs){
            if (s.equals(lib))return true;
        }
        return false;
    }
    private void getVersionCode(String path , OnUpdateListener listener) throws CreateJSException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Utils.readStringFromFile(path));
            JSONArray jsonArray = jsonObject.getJSONArray("versioncode");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            int code = jsonObject1.getInt("code");
            String url = jsonObject1.getString("updateuri");

            new Thread(() -> {
                try {
                    String str = getString(url);
                    JSONObject obj = new JSONObject(str);
                    int code2 = obj.getInt("versioncode");
                    String mess = obj.getString("message");
                    String uri2 = obj.getString("downloaduri");
                    Looper.prepare();
                    if (listener != null) {
                        activity.runOnUiThread(() -> {
                            if (code2 > code) {
                                listener.onUpdate(code2, mess, uri2);
                            } else {
                                listener.UnUpdate(code);
                            }

                        });
                    }
                    Looper.loop();
                } catch (Exception e) {Log.e("OnUpDate",Utils.getErr(e));}
            }).start();
        }catch (JSONException e){
            if (getLibsIsMe(new File(new File(path).getParent()).getName(),libss)){
                if (listener!=null){
                    listener.UnUpdate(-1);
                }
            }
            throw  new CreateJSException(e +"\n at "+path);
        }
    }
}
