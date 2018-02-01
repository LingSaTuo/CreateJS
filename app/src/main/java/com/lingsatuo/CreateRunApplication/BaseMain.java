package com.lingsatuo.CreateRunApplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingsatuo.Console.Console;
import com.kingsatuo.view.LogView;
import com.lingsatuo.Dialog.Libs_Message;
import com.lingsatuo.callbackapi.ScriptLoading;
import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSException;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Dialog;
import com.lingsatuo.script.ScriptTool;
import com.lingsatuo.service.SubService;
import com.lingsatuo.utils.SIUutil;
import com.lingsatuo.utils.Utils;
import com.lingsatuo.utils.ZipUtils;

import org.json.JSONException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 15176 on 2017/9/19.
 */

public class BaseMain extends Main {
    private String name = "";
    public static BaseMain inthis;

    public StringBuilder sb_maven = new StringBuilder();
    private List<String> paths;

    public static long memsize = 2048;
    private boolean isRun = false;
    private boolean issetTheme = false;
    private Handler call;
    ScriptTool scriptTool;
    private static LogView ll;
    private Dialog.A da;
    private Looper main;
    private Handler handler;
    List<String> functs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = Thread.currentThread().getName();
        inthis = this;
        main = Looper.getMainLooper();
        new Handler(main).post(() -> {
            while (true) {
                try {
                    Looper.loop();
                } catch (Throwable e) {
                    try {
                        sendErr(e, true);
                    } catch (Throwable throwable) {
                        try {
                            Utils.saveToFile(ScriptTool.LogErrPath, Utils.getErr(throwable));
                        } catch (CreateJSIOException e1) {
                        } finally {
                            Process.killProcess(Process.myPid());
                        }
                    }
                }
            }
        });
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            if (thread.getName().equals(name)) {
                try {
                    Utils.saveToFile(ScriptTool.LogErrPath, Utils.getErr(throwable));
                } catch (CreateJSIOException e) {
                } finally {
                    Process.killProcess(Process.myPid());
                }
            } else {
                if (Build.VERSION.SDK_INT < 20) {
                    if (throwable.getMessage() == null) {
                        sendMessage("Thread", throwable.getMessage() + inthis.getApplicationContext().getString(R.string.s_err), Color.RED);
                    } else {
                        if (throwable.getMessage().replaceAll(" ", "").equals("org/mozilla/javascript/NativeObject"))
                            sendMessage("Thread", throwable.getMessage() + inthis.getApplicationContext().getString(R.string.s_err), Color.RED);
                        else {
                            sendErr(throwable, false       );
                        }
                    }
                } else {
                    sendErr(throwable, false);
                }
                if (da != null) {
                    da.dismiss();
                }
            }
        });
    }

    protected void setRootView(LogView ll) {
        this.ll = ll;
        this.ll.setDefColor(this.getResources().getColor(R.color.s));
    }


    @Override
    public String getPackageName() {
        return super.getPackageName();
    }

    public void setTheme(int a) {
        if (!issetTheme) {
            super.setTheme(R.style.AppTheme_DarkActionBar);
            issetTheme = true;
        } else {
            sendMessage("CreateJSsetThemeError", "不被允许的操作 ?.setTheme(int)", "#ff0000");
        }
    }

    @Override
    public void startActivity(Intent i) {
        super.startActivity(i);
    }

    public static void sendMessage(CharSequence type, CharSequence message) {
        sendMessage(type, message, null);
    }

    public static void sendMessage(final CharSequence type, final CharSequence message, final String color) {
        int colorr = Color.BLACK;
        try {
            colorr = Color.parseColor(color);
            if (Console.getConsole() != null) {
                Console.getConsole().log(("<" + type + ">" + "  " + message + "\n"), color);
            }
        } catch (Exception e) {
        } finally {
            sendMessage(type, message, colorr);
        }
    }

    public static void sendMessage(final CharSequence type, final CharSequence message, final int color) {
        inthis.runOnUiThread(() -> {
            if (type instanceof String && message instanceof String){
                String sv = ("<" + type + ">" + "  " + message + "\n");
                if (color != 0)
                    try {
                        ll.addLog(sv, color);
                    } catch (Exception e) {
                        sendMessage("CreateJSColorError", e.getMessage(), "#ff0000");
                    }
            }else{
                ll.write(type);
                ll.write(message);
            }

        });
    }

    private void showloading() {
        da = new Dialog(0).LoadingDialog(inthis);
        da.showProgrss(true)
                .canClose(false)
                .setMessageColor(Color.BLACK)
                .canCloseOut(false)
                .setTile(inthis.getString(R.string.s_21))
                .setMessage(inthis.getString(R.string.s_23))
                .setWindowType(false)
                .show();
    }

    private void setFunction_call(String[] valus) throws CreateJSException {
        Function function1 = null;
        if (valus[0].split("!")[0].equals("loadingfinish")) {
            try {
                function1 = (Function) scriptTool
                        .getScope().get("loadingfinish", scriptTool.getScope());
                function1.call(scriptTool.getContext(),
                        scriptTool.getScope(),
                        scriptTool.getScope(), valus[0].split("!")[0].split(","));
            } catch (Exception e) {
            } finally {
                return;
            }
        }
        try {
            function1 = (Function) scriptTool
                    .getScope().get("_function", scriptTool.getScope());
            function1.call(scriptTool.getContext(),
                    scriptTool.getScope(),
                    scriptTool.getScope(), valus);
        } catch (Exception e) {
            throw new CreateJSException(e.getMessage());
        }
    }


    private void sendErr(Throwable e, boolean main) {
        String tag = main ? "UIThread" : "Thread";
        if (e instanceof RhinoException) {
            RhinoException re = (RhinoException) e;
            sendMessage(tag, re.getMessage() + "\nat :" + re.lineSource() + "\nScript :" + ((RhinoException) e).sourceName(),Color.RED)      ;
        } else {
            sendMessage(tag, Utils.getErr(e), Color.RED);
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            if (paths == null) return;
            if (!isRun) {
                if (da == null) showloading();
                new Handler().postDelayed(() -> new Thread(Thread.currentThread().getThreadGroup(), () -> {
                    try {
                        run();
                        Looper.prepare();
                        call = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                Bundle bd = msg.getData();
                                try {
                                    setFunction_call(bd.getStringArray("str"));
                                } catch (CreateJSException e) {
                                    inthis.sendMessage("CreateJSException", e.getMessage() + "\n" + inthis.getApplicationContext().getResources().getString(R.string.s_56), Color.RED);
                                }
                            }
                        };

                        Looper.loop();
                    } catch (CreateJSIOException e) {
                        sendMessage("加载时错误", e.getMessage(), Color.RED);
                    } catch (JSONException e) {
                        sendMessage("加载时错误", e.getMessage(), "#ff0000");
                    } catch (CreateJSException e) {
                        sendMessage("加载时错误", e.getMessage(), "#ff0000");
                    } finally {
                        if (da != null) {
                            da.dismiss();
                        }
                    }
                }, "CreateJS", memsize < 100 ? 1024 * 2 : memsize).start(), 10);
                isRun = true;
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void setPaths(String[] path) {
        if (path == null) return;
        paths = new ArrayList<>();
        for (String s : path) {
            paths.add(s);
        }
    }

    @Override
    public void setObj2JS(String str, Object obj) {
        super.setObj2JS(str, obj);
    }

    void run() throws CreateJSException, JSONException {
        scriptTool = ScriptTool.getInstance().Instance(this);

        setObj2JS("libs_utils", Utils.getInstance());
        setObj2JS("libs_siutils", new SIUutil());
        setObj2JS("libs_zip", new ZipUtils());
        setObj2JS("libs_inthis", inthis);
        scriptTool.loadMaven();
        da.setMax(scriptTool.getScriptCount());
        scriptTool.create(paths);
        scriptTool.execution();
        scriptTool.setOnScriptExcption(e -> {
            sendMessage("错误", e.getMessage(), "#FF0000");
            if (da != null)
                da.dismiss();
        });
        scriptTool.run(new ScriptLoading() {
            @Override
            public void onLoading(int count, String name) {
                da.setMessage(name);
                da.setProgress(count);
            }

            @Override
            public void finish() {
                Message msgs = new Message();
                Bundle bd = new Bundle();
                bd.putStringArray("str", new String[]{"loadingfinish!" + sb_maven.toString()});
                msgs.setData(bd);
                try {
                    Utils.saveToFile(SubService.path2, "-2");
                } catch (CreateJSIOException e) {
                }
                if (call != null) {
                    call.sendMessage(msgs);
                }
                if (da != null)
                    da.dismiss();
            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.log, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                ll.clear();
                break;
            case R.id.function_call:
                call();
                break;
            case R.id.libs_message:
                new Libs_Message(this, scriptTool.getMavenMessage());
                break;
            case R.id.autoscroll:
                ll.setAutoScroll(ll.getAutoScroll() ? false : true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static LogView getLogView() {
        return ll;
    }

    @Override
    public int getAppBuildVersion() {
        return super.getAppBuildVersion();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getAllFunction() {
        Object[] ids = scriptTool.getScope().getIds();
        for (Object one : ids) {
            if (scriptTool.getScope().get(one.toString(), scriptTool.getScope()) instanceof Function) {
                functs.add(one.toString());
            }
        }
        Collections.sort(functs);
    }

    private void call() {
        LinearLayout ll = new LinearLayout(inthis);
        ll.setPadding(Utils.dip2px(inthis, 18), Utils.dip2px(inthis, 18), Utils.dip2px(inthis, 18), Utils.dip2px(inthis, 18));
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(inthis);
        tv.setText(inthis.getResources().getString(R.string.s_18));
        AppCompatAutoCompleteTextView function_name = new AppCompatAutoCompleteTextView(inthis);
        function_name.setAdapter(new ArrayAdapter<>(inthis, android.R.layout.simple_dropdown_item_1line, functs));
        function_name.setSingleLine();
        function_name.setThreshold(0);

        TextView tv2 = new TextView(inthis);
        tv2.setText(inthis.getResources().getString(R.string.s_19));
        final AppCompatEditText values = new AppCompatEditText(inthis);
        function_name.setSingleLine();

        ll.addView(tv);
        ll.addView(function_name);
        ll.addView(tv2);
        ll.addView(values);
        new AlertDialog.Builder(inthis)
                .setTitle(inthis.getResources().getString(R.string.s_17))
                .setView(ll)
                .setPositiveButton(inthis.getResources().getString(R.string.s_11), (dialogInterface, i) -> {
                    String str = function_name.getText().toString() + "!" + values.getText().toString();
                    Message msg = new Message();
                    Bundle bd = new Bundle();
                    bd.putStringArray("str", new String[]{str});
                    msg.setData(bd);
                    if (call != null) {
                        call.sendMessage(msg);
                    }
                })
                .setNegativeButton(inthis.getResources().getString(R.string.s_12), null)
                .show();
    }
}
