package com.lingsatuo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.os.Looper;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;

import com.lingsatuo.CreateRunApplication.BaseMain;
import com.lingsatuo.error.CreateJSException;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.script.ScriptTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

/**
 * Created by 15176 on 2017/6/28.
 */

public class Utils {
    public static Utils getInstance() {
        return new Utils();
    }

    public void saveDateToApp(String name, String str) throws CreateJSIOException {
        saveDateToApp(ScriptTool.getInstance().getApp(), name, str);
    }

    public static String getSD() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static void saveDateToApp(Context context, String name, String str) throws CreateJSIOException {
        try {
            psaveDateToApp(context, name, str);
        } catch (IOException e) {
            throw new CreateJSIOException(e);
        }
    }

    public String readDateFromApp(String name) throws CreateJSIOException {
        return readDateFromApp(ScriptTool.getInstance().getApp(), name);
    }

    public static String readDateFromApp(Context context, String name) throws CreateJSIOException {
        try {
            return preadDateFromApp(context, name);
        } catch (IOException e) {
            CreateJSIOException exception = new CreateJSIOException(e);
            exception.setId(1);
            throw exception;
        }
    }

    public static void psaveDateToApp(Context context, String name, String str) throws IOException {
        File file = new File(context.getFilesDir().toString() + "/" + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = null;
        fos = new FileOutputStream(file);
        fos.write(str.getBytes());
        fos.close();
    }

    public static String preadDateFromApp(Context context, String name) throws IOException {
        FileInputStream fis = context.openFileInput(name);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        isr.close();
        fis.close();
        if (sb.length()>0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String preadStringFromFile(String path) throws IOException {
        StringBuilder SB = new StringBuilder();
        if (path==null) throw new FileNotFoundException("File Not Found ! PLZ Check the path of null");

        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File Not Found ! PLZ Check the path  :" + path);
        }
        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            SB.append(line + "\n");
        }
        isr.close();
        br.close();
        is.close();
        if (SB.length()>0)
        SB.deleteCharAt(SB.length() - 1);
        return SB.toString();
    }

    public static BufferedReader pgetBufferedReader(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File Not Found ! PLZ Check the path  :" + path);
        }
        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        return br;
    }

    public BufferedReader getBufferedReader(String path) throws CreateJSIOException {
        return getBufferedReaderFromPath(path);
    }

    public static BufferedReader getBufferedReaderFromPath(String path) throws CreateJSIOException {
        try {
            return pgetBufferedReader(path);
        } catch (IOException e) {
            throw new CreateJSIOException(e);
        }
    }

    public String readFile(String str) throws CreateJSIOException {
        return readStringFromFile(str);
    }

    public static String readStringFromFile(String path) throws CreateJSIOException {
        try {
            return preadStringFromFile(path);
        } catch (IOException e) {
            CreateJSIOException exception = new CreateJSIOException(e.getMessage());
            exception.setId(1);
            throw exception;
        }
    }

    public static void setActivity(Activity activity) {
        try {
            String settings = Utils.readDateFromApp(activity, "settings");
            JSONObject jobj = new JSONObject(settings);
            if (!jobj.getString("orientation").equals("vertical")) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            if (jobj.getBoolean("hide_status_bar")) {
                Window window = activity.getWindow();
                int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
                window.setFlags(flag, flag);
            }

            if (ScriptTool.getInstance().getApp() instanceof BaseMain)
            ((BaseMain) ScriptTool.getInstance().getApp()).memsize = Long.parseLong(jobj.getString("memsize"));
        } catch (CreateJSIOException e) {
        } catch (JSONException e) {
        }
    }

    public static String getLibsPropresPath(String libs) {
        return Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + libs + "/libs.propres";
    }

    public static String getLibsUserPropresPath(String libs) {
        return Environment.getExternalStorageDirectory() + "/.CreateJS/libs/user_libs/" + libs + "/libs.propres";
    }

    public static String getErr(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String getErr(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String getLibsPath() {
        String root = Environment.getExternalStorageDirectory() + "/.CreateJS/" +
                "libs/js_libs";
        return root;
    }

    public static void setPath() {
        String root = Environment.getExternalStorageDirectory() + "/.CreateJS/";
        String[] files = {
                "libs",
                "libs/user_libs",
                "libs/js_libs",
                "error",
                "data",
                "backups",
                "log",
                "cache",
                "download",
                "require"
        };
        for (String file : files) {
            File file1 = new File(root + file);
            if (!file1.exists()) {
                file1.mkdirs();
            }
        }
    }

    public static int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    private static void psaveToFile(String path, String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(path));
        fos.write(str.getBytes());
        fos.close();
    }

    public void saveFile(String path, String str) throws CreateJSIOException {
        saveToFile(path, str);
    }

    public static void saveToFile(String path, String str) throws CreateJSIOException {
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            psaveToFile(path, str);
        } catch (IOException e) {
            throw new CreateJSIOException(e);
        }
    }

    public static String getAssets(Context context, String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(context.getAssets().open(path), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        isr.close();
        br.close();
        if (sb.length()>0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void deleteFile(String ff) throws Exception {
        deleteFolder(ff);
    }

    public static void deleteFolder(String ff) throws Exception {
        File file = new File(ff);
        if (file.isFile()) {
            file.delete();
        } else if (!file.exists() || !file.isDirectory()) {
            throw new CreateJSException("File not found ! PLZ Check the path!");
        } else {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else {
                    deleteFolder(f.getPath());
                }
            }
            file.delete();
        }
    }

    public String getFileNameNoEnd(String filename) {
        return getFileNameNoEx(filename);
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static boolean runOnUIThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

    public static String base64(String path, String to) throws IOException, CreateJSIOException {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        String str = Base64.encodeToString(buffer, Base64.DEFAULT);
        if (to != null)
            saveToFile(to, str);
        return str;
    }

    public static void base64ToFile(String encode, String to) throws IOException {
        byte[] buffer = Base64.decode(encode, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(to);
        out.write(buffer);
        out.close();
    }

    public static String getRandom(int count) {
        final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int ii = 0; ii < count; ii++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    public static String getAssetsString(Context context, String FilePath) throws CreateJSIOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(context.getAssets().open(FilePath), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            br.close();
            if (sb.length()>0)
                sb.deleteCharAt(sb.length() - 1);
        } catch (IOException e) {
            throw new CreateJSIOException(e.getMessage());
        }
        return sb.toString();
    }

}
