package com.lingsatuo.createjs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.lingsatuo.Dialog.Libs_Message;
import com.lingsatuo.Dialog.Nl;
import com.lingsatuo.Dialog.ShowErr;
import com.lingsatuo.createjs.Utils.DataD;
import com.lingsatuo.createjs.Utils.maven.MavenProject;
import com.lingsatuo.createjs.Utils.maven.Util;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.io.IOUtils;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.utils.CheckLibsVersion;
import com.lingsatuo.utils.Libs;
import com.lingsatuo.utils.OnUpdateListener;
import com.lingsatuo.utils.UriUtils;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LoadingActivity extends MAIN {
    List<Switch> checkboxs;
    List<AppCompatSpinner> spinners;
    List<AppCompatEditText> editTexts;
    static String lib = "";
    List<Libs> list;
    static LoadingActivity inthis;
    CollapsingToolbarLayout ctl;
    static Dialog.A da;
    static DataD dd;

    static String path = "";
    static String last_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            toolbar.getLayoutParams().height = getAppBarHeight();
//            toolbar.setPadding(toolbar.getPaddingLeft(),
//                    getStatusBarHeight(),
//                    toolbar.getPaddingRight(),
//                    toolbar.getPaddingBottom());
            //透明
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        super.onResume();
        try {
            path = getPath();
        } catch (Throwable throwable) {
            Toast.makeText(this, "读取错误", 1).show();
            return;
        }
        File f = new File(path);
        if (!f.exists()) {
            Toast.makeText(this, "读取错误", 1).show();
            return;
        }
        dd = new DataD(new File(path));
        inthis = this;
        try {
            setView();
        } catch (JSONException e) {
        } catch (CreateJSIOException e) {
            new Nl(LoadingActivity.this, e.toString(), 0);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        ctl = findViewById(R.id.toolbar_layout);
        fab.setOnClickListener(view -> {
            list = new ArrayList<>();
            MavenProject project;
            try {
                saveDate();
                showloading();
                if (!lib.equals("*" + inthis.getResources().getString(R.string.s_34) + "*")) {
                    String path2 = Utils.getLibsPropresPath(lib);
                    if (new File(path2).exists()) {
                        project = new MavenProject(new File(path2).getParent());
                        list = Util.getMaven(project, true);
                    } else {
                        project = new MavenProject(new File(Utils.getLibsUserPropresPath(lib)).getParent());
                        list = Util.getMaven(project, true);
                    }
                    checkDown();
                }else{
                    checkDown();
                }
            } catch (Exception e) {
                new Nl(LoadingActivity.this, e);
                da.dismiss();
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void setView() throws JSONException, CreateJSIOException {
        Utils.setPath();
        File file = new File(Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/");
        File[] files = file.listFiles();
        final List<String> libs = new ArrayList<>();
        libs.add("*" + inthis.getResources().getString(R.string.s_34) + "*");
        for (File f : files) {
            if (!f.isFile()) {
                if (new File(f.getPath() + "/libs.propres").exists()) {
                    libs.add(f.getName());
                }
            }
        }

        File file2 = new File(Environment.getExternalStorageDirectory() + "/.CreateJS/libs/user_libs/");
        File[] files2 = file2.listFiles();
        for (File f : files2) {
            if (!f.isFile()) {
                if (new File(f.getPath() + "/libs.propres").exists()) {
                    if (libs.contains(f.getName()))
                        throw new CreateJSIOException("Name of library is not unique : " + f.getName());
                    libs.add(f.getName());
                }
            }
        }
        checkboxs = new ArrayList<>();
        checkboxs.add(findViewById(R.id.orientation));
        checkboxs.add(findViewById(R.id.hide_status_bar));
        checkboxs.add(findViewById(R.id.log_mask));
        spinners = new ArrayList<>();
        spinners.add(findViewById(R.id.libs));
        spinners.add(findViewById(R.id.engine));
        editTexts = new ArrayList<>();
        editTexts.add(findViewById(R.id.package_name));
        editTexts.add(findViewById(R.id.memsize));
        spinners.get(0).setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, libs));
        spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lib = libs.get(i);
                if (!last_name.equals(lib)) {
                    new CheckLibsVersion(LoadingActivity.this, lib, new OnUpdateListener() {
                        @Override
                        public void onUpdate(int code, String message, String uri) {
                            new AlertDialog.Builder(LoadingActivity.this)
                                    .setTitle("可更新的库  " + lib)
                                    .setMessage(message)
                                    .setPositiveButton("下载", (dialogInterface, i1) -> {
                                        showloading();
                                        Handler handler = new DownloadHandler(LoadingActivity.this);
                                        List<String> name = new ArrayList<>();
                                        List<String> uril = new ArrayList<>();
                                        name.add(lib + ".zip");
                                        uril.add(uri);
                                        download(uril, name, handler, true);
                                    })
                                    .setNegativeButton("取消", null).show();
                        }

                        @Override
                        public void UnUpdate(int code) {
                            if (code == -1) {
                                new Libs_Message(LoadingActivity.this, new StringBuilder("当前库版本为不可用版本" + lib + "，请前往CreateJS主页面重新下载"));
                            }
                        }
                    });
                }
                last_name = lib;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            String settings = Utils.readDateFromApp(this, "settings");
            JSONObject obj = new JSONObject(settings);
            obj.getString("memsize");
            dothings(obj);
            setSpinnerSelectedByValue(spinners.get(0), obj.getString("lib"));
        } catch (CreateJSIOException e) {
            JSONObject jobj = new JSONObject();
            jobj.put("libs", "MCModPE");
            jobj.put("engine", "Rhino");
            jobj.put("log_mask", false);
            jobj.put("orientation", "vertical");
            jobj.put("hide_status_bar", false);
            jobj.put("package_name", this.getPackageName());
            jobj.put("memsize", "4096");
            Utils.saveDateToApp(this, "settings", jobj.toString());
            dothings(jobj);
        }
    }

    private void dothings(JSONObject object) throws JSONException {
        checkboxs.get(0).setChecked(!object.getString("orientation").equals("vertical"));
        checkboxs.get(1).setChecked(object.getBoolean("hide_status_bar"));
        checkboxs.get(2).setChecked(object.getBoolean("log_mask"));
        editTexts.get(0).setText(object.getString("package_name"));
        editTexts.get(1).setText(object.getString("memsize"));
    }

    private void saveDate() throws JSONException, CreateJSIOException {
        JSONObject jobj = new JSONObject();
        jobj.put("libs", "MCModPE");
        jobj.put("engine", "Rhino");
        jobj.put("log_mask", checkboxs.get(2).isChecked());
        jobj.put("orientation", checkboxs.get(0).isChecked() == true ? "horizontal" : "vertical");
        jobj.put("hide_status_bar", checkboxs.get(1).isChecked());
        jobj.put("package_name", editTexts.get(0).getText().toString());
        jobj.put("memsize", editTexts.get(1).getText().toString());
        jobj.put("lib", lib);
        Utils.saveDateToApp(this, "settings", jobj.toString());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            ctl.setTitle(new File(path).getName());
        } catch (Exception e) {
        }
        File f = new File(ScriptTool.LogErrPath);
        if (f.exists()) {
            try {
                new ShowErr(this, Utils.readStringFromFile(ScriptTool.LogErrPath));
            } catch (CreateJSIOException e) {
            } finally {
                f.delete();
            }
        }
    }
//
//    private void loadLibs(String libs_prpres) throws Exception {
//
//        Libs lib2 = new Libs();
//        lib2.name = new File(new File(libs_prpres).getParent()).getName();
//        lib2.uri = "true";//添加error字段
//        libses.add(lib2);
//        JSONObject jsonObject;
//        try {
//            jsonObject = new JSONObject(Utils.readStringFromFile(libs_prpres));
//        } catch (JSONException e) {
//            throw new CreateJSException(e + "\n at " + libs_prpres);
//        }
//        JSONArray libs = null;
//        try {
//            libs = jsonObject.getJSONArray("libs");
//        } catch (JSONException e) {
//        }
//        if (libs != null) {
//            for (int a = 0; a < libs.length(); a++) {
//                JSONObject obj = libs.getJSONObject(a);
//                String maven_name = null;
//                try {
//                    maven_name = obj.getString("name");
//                } catch (JSONException E) {
//                }
//                if (maven_name == null) return;
//                String path = Utils.getLibsPropresPath(maven_name);//库文件
//                if (path.equals(libs_prpres)) {
//                    throw new CreateJSException("A library cannot depend on itself  :  " + maven_name);
//                } else if (Utils.getLibsUserPropresPath(maven_name).equals(libs_prpres)) {
//                    throw new CreateJSException("A library cannot depend on itself  :  " + maven_name);
//                }
//                String down = Environment.getExternalStorageDirectory() + "/.CreateJS/download";
//                File file = new File(path);//库配置文件
//                final File file1 = new File(down + "/" + maven_name + ".zip");
//                String maven_uri = null;
//                try {
//                    maven_uri = obj.getString("uri");
//                } catch (JSONException e) {
//                }
//                if (!file.exists()) {//库不存在
//                    String n_p = Utils.getLibsUserPropresPath(new File(file.getParent()).getName());
//                    if (new File(n_p).exists()) {
//                        //如果user_libs里存在
//                        Libs lib = new Libs();
//                        lib.name = maven_name;
//                        lib.uri = "true";//添加error字段
//                        libses.add(lib);
//                        loadLibs(n_p);//再次遍历
//                    } else {
//                        if (!file1.exists() || !file1.isFile()) {//库对应的压缩包不存在
//                            Libs lib = new Libs();
//                            lib.name = maven_name;
//                            if (maven_uri == null) {//下载地址不存在
//                                lib.uri = "false";//添加error字段
//                            } else {
//                                lib.uri = maven_uri;//添加下载地址
//                            }
//                            libses.add(lib);
//                        } else {//对应的压缩包存在
//                            Libs lib = new Libs();
//                            lib.name = maven_name;
//                            lib.uri = "true";//添加error字段
//                            libses.add(lib);
//                            ZipUtils.loadZip(file1.getPath(), Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + Utils.getFileNameNoEx(file1.getName()));
//                        }
//                    }
//                } else {//库存在
//                    loadLibs(file.getPath());//再次遍历
//                }
//            }
//        }
//    }

    public static List<String> getMavens() throws CreateJSIOException {
        if (name2 == null) return null;
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = name2.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        name2.clear();
        name2.addAll(newList);
        List<String> maven = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int a = name2.size() - 1; a >= 0; a--) {
            maven.add(name2.get(a));
            sb.append(name2.get(a) + "\n");
        }
        sb.append("*" + inthis.getResources().getString(R.string.s_34) + "*\n");
        Utils.saveDateToApp(inthis, "maven", sb.toString());
        return maven;
    }

    static List<String> name;
    public static List<String> name2;

    private void checkDown() {
        name = new ArrayList<>();
        name2 = new ArrayList<>();
        List<String> uri = new ArrayList<>();
        for (Libs lib : list) {
            if (!lib.uri.equals("true") && !lib.uri.equals("false")) {
                name.add(lib.name + ".zip");
                uri.add(lib.uri);
            }
            name2.add(lib.name);
        }
        Handler handler = new DownloadHandler(LoadingActivity.this);
        download(uri, name, handler, false);
    }

    static List<String> names;

    private void download(List<String> uri, List<String> name, Handler handler, boolean update) {
        names = name;
        UriUtils.download(uri, handler, name, update);
    }

    public static class DownloadHandler extends Handler {
        public final WeakReference<LoadingActivity> weakRefActivity;

        public DownloadHandler(LoadingActivity mainActivity) {
            weakRefActivity = new WeakReference<>(mainActivity);
        }

        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            LoadingActivity activity = weakRefActivity.get();
            switch (msg.what) {

                case 100:
                    try {
                        ZipUtils.loadZip(Environment.getExternalStorageDirectory() + "/.CreateJS/download/" + lib + ".zip", Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + lib);
                    } catch (Exception e) {
                        Toast.makeText(inthis, "更新失败", 1).show();
                    }
                    Toast.makeText(inthis, "更新完成", 1).show();
                    da.dismiss();
                    break;
                case 0:
                    int progress = (int) msg.obj;
                    da.setProgress(progress);
                    break;
                case -1:
                    da.setMessage("下载失败\n" + msg.obj);
                    break;
                case -100:
                    da.setMessage("下载失败\n" + msg.obj);
                    break;
                case -2:
                    da.dismiss();
                    try {
                        name2 = getMavens();
                        dd.startActivity(inthis);
                    } catch (CreateJSIOException e) {
                        new ShowErr(inthis, e.toString());
                    }
                    break;
                case 1:
                    new Thread(() -> {
                        try {
                            for (int aa = 0; aa < names.size(); aa++) {
                                ZipUtils.loadZip(Environment.getExternalStorageDirectory() + "/.CreateJS/download/" + names.get(aa), Environment.getExternalStorageDirectory() + "/.CreateJS/libs/js_libs/" + Utils.getFileNameNoEx(names.get(aa)));
                            }
                        } catch (Exception e) {
                            Message msg1 = new Message();
                            msg1.what = 0;
                            msg1.obj = e.toString();
                        } finally {
                            inthis.runOnUiThread(() -> {
                                da.dismiss();
                                try {
                                    name2 = getMavens();
                                    dd.startActivity(inthis);
                                } catch (CreateJSIOException e) {
                                    new ShowErr(inthis, e.toString());
                                }
                            });
                        }
                    }).start();
                    break;
            }
        }
    }

    private void showloading() {
        da = new Dialog(0).LoadingDialog(inthis);
        da.showProgrss(true)
                .canClose(false)
                .canCloseOut(false)
                .setTile(inthis.getString(R.string.s_25))
                .setMessage(inthis.getString(R.string.s_25))
                .setWindowType(true)
                .show();
    }

    private void setSpinnerSelectedByValue(AppCompatSpinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        int k = adapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(adapter.getItem(i).toString())) {
                spinner.setSelection(i, true);
                break;
            }
        }
    }


    @SuppressLint("WrongConstant")
    private String getPath() throws Throwable {
        Intent intent = getIntent();
        if (intent == null)
            return ""; //pass hint

        String action = intent.getAction();
        // action == null if change theme
        if (action == null || Intent.ACTION_MAIN.equals(action)) {
            String path = intent.getStringExtra("path");
            return path == null ? "" : path;
        }

        if (Intent.ACTION_VIEW.equals(action) || Intent.ACTION_EDIT.equals(action)) {
            if (intent.getScheme().equals("content")) {
                try {
                    InputStream attachment = getContentResolver().openInputStream(intent.getData());
                    String text = IOUtils.toString(attachment);
                    return text;
                } catch (Exception e) {
                    Toast.makeText(this, this.getResources().getString(R.string.cannt_open_external_file_x), 0).show();
                } catch (OutOfMemoryError e) {
                    Toast.makeText(this, this.getResources().getString(R.string.out_of_memory_error), 0).show();
                }
                return "";
            } else if (intent.getScheme().equals("file")) {
                Uri mUri = intent.getData();
                String file = mUri != null ? mUri.getPath() : null;
                if (!TextUtils.isEmpty(file)) {
                    return file;
                }
            }

        } else if (Intent.ACTION_SEND.equals(action) && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            CharSequence text = extras.getCharSequence(Intent.EXTRA_TEXT);

            if (text != null) {
                return text.toString();
            } else {
                Object stream = extras.get(Intent.EXTRA_STREAM);
                if (stream != null && stream instanceof Uri) {
                    return (((Uri) stream).getPath());
                }
            }
        }

        return "";
    }
}
