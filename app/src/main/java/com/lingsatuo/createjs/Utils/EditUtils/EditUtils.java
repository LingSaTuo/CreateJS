package com.lingsatuo.createjs.Utils.EditUtils;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kingsatuo.view.JSEditor;
import com.lingsatuo.Dialog.Dialog;
import com.lingsatuo.adapter.FileSelectorAdapter;
import com.lingsatuo.adapter.utils.FileSelectorUtils;
import com.lingsatuo.callbackapi.FunctionCallBACK;
import com.lingsatuo.callbackapi.ToBeContinue;
import com.lingsatuo.createjs.MAIN;
import com.lingsatuo.createjs.R;
import com.lingsatuo.createjs.Sharing;
import com.lingsatuo.createjs.Utils.EditUtils.PopWindow.Pop;
import com.lingsatuo.createjs.Utils.EditUtils.utils.Finish2Do;
import com.lingsatuo.createjs.Utils.EditUtils.utils.ItemClick;
import com.lingsatuo.createjs.Utils.EditUtils.utils.NEW_PROJECT;
import com.lingsatuo.createjs.Utils.EditUtils.utils.OnLong;
import com.lingsatuo.createjs.Utils.EditUtils.utils.Theme;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.openapi.Files;
import com.lingsatuo.utils.Utils;
import com.myopicmobile.textwarrior.common.LanguageJavascript;
import com.myopicmobile.textwarrior.common.Lexer;

import java.io.File;

import codeedit.lingsatuo.com.project.Project;


/**
 * Created by Administrator on 2017/11/19.
 */

public class EditUtils implements View.OnClickListener {

    private MAIN activity;
    private JSEditor jsEditor;
    private FileSelectorAdapter adapter;
    private ListView lv;
    private static File mfile = new File(new Files().getSdcardPath());
    private final static String TAG = "Edit";
    private DrawerLayout drawerLayout;
    private File mFile;
    public static Project last_project;
    public NEW_PROJECT new_project;

    public EditUtils(MAIN activity) {
        this.activity = activity;
    }

    public void startActivity() {
        this.activity.clearCallBack(TAG);
        this.activity.clearOnChange(TAG);
        this.activity.addCallBack(TAG, new A());
        activity.addOnChange(TAG, new A()).start();
    }

    public void setDrawerFile(File file) {
        mfile = file;
    }

    public static void setOpenFile(File file) {
        mfile = file;
    }

    public NEW_PROJECT getBack() {
        return this.new_project;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.last_page:
                if (mfile == null) mfile = new File(new Files().getSdcardPath());
                mfile = mfile.getParentFile();
                adapter.setData(FileSelectorUtils.getData(mfile));
                adapter.notifyDataSetChanged();
                return;
            case R.id.new_data:
                new_project = new NEW_PROJECT(jsEditor, adapter, activity);
                new Pop(activity, view, mfile, new_project).setView();
                return;
        }
        if (view instanceof TextView) {
            jsEditor.paste(((TextView) view).getText().toString());
        }
    }

    public class A implements FunctionCallBACK, ToBeContinue {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void T(Object object) {
            Object[] objects = (Object[]) object;
            String TAG = (String) objects[0];
            switch (TAG) {
                case "onCreate":
                    activity = (MAIN) objects[1];
                    setContentView(R.layout.edit_activity);
                    activity.getSupportActionBar().setTitle(R.string.activity_4);
                    break;
                case "onResume":
                    onResume();
                    break;
            }
        }

        @Override
        public boolean T2(Object obj) {
            Object[] objects = (Object[]) obj;
            String TAG = (String) objects[0];
            switch (TAG) {
                case "onBackPressed":
                    if (drawerLayout.isDrawerOpen(Gravity.START)) {
                        drawerLayout.closeDrawer(Gravity.START);
                        return false;
                    }
                    if (jsEditor.isEdited()) {
                        Snackbar.make(jsEditor, getString(R.string.s_78), Snackbar.LENGTH_LONG).setAction(R.string.s_33, view -> {
                            Sharing sharing = (Sharing) activity.getInstance();
                            sharing.superOnBackPressed();
                        }).show();
                        return false;
                    } else {
                        return true;
                    }

                case "onPrepareOptionsMenu":
                    Menu menu = (Menu) objects[1];
                    createMenu(menu);
                    return true;
                case "onOptionsItemSelected":
                    MenuItem item = (MenuItem) objects[1];
                    onOptionsItemSelected(item);
                    return true;
            }
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setContentView(int Rid) {
        Lexer.setLanguage(LanguageJavascript.getInstance());
        activity.setContentView(Rid);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        jsEditor = activity.findViewById(R.id.jseditor);
        jsEditor.setOpenedFile(Utils.getSD() + "/.CreateJS/cache/临时文件.js");
        lv = activity.findViewById(R.id.edit_file_select);
        activity.findViewById(R.id.last_page).setOnClickListener(this);
        activity.findViewById(R.id.new_data).setOnClickListener(this);
        drawerLayout = activity.findViewById(R.id.edit_dawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        jsEditor.setDark(true);
        LinearLayout ll = activity.findViewById(R.id.operators);
        char[] chars = jsEditor.getOperators();
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(-2, -2, 1);
        pm.setMargins(1, 0, 1, 0);
        for (char c : chars) {
            TextView tv = new TextView(activity.getApplicationContext());
            tv.setText(String.valueOf(c) + "");
            int[] attrs = new int[]{android.R.attr.selectableItemBackground};

            TypedArray a = activity.getTheme().obtainStyledAttributes(attrs);
            Drawable d = a.getDrawable(0);
            tv.setBackground(d);
            tv.setClickable(true);
            tv.setOnClickListener(this);
            tv.setPadding(40, 20, 40, 20);
            tv.setTextSize(0, (int) (tv.getTextSize() * 1.6));
            tv.setTextColor(Color.WHITE);
            ll.addView(tv, pm);
        }
        ll.measure(0, 0);
        adapter = new FileSelectorAdapter(activity);
        adapter.setProject(true);
        adapter.setData(FileSelectorUtils.getData(new File(Utils.getSD())));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemClick(adapter, jsEditor, activity, drawerLayout, this));
        lv.setOnItemLongClickListener(new OnLong(adapter, jsEditor, activity, drawerLayout, this));
        try {
            String s = Utils.readDateFromApp(activity, "edit_theme");
            jsEditor.setColorScheme(new Theme(s));
        } catch (CreateJSIOException e) {
            if (e.getId() != 1)
                new Dialog(activity, getString(R.string.na_4), e.getMessage(), getString(R.string.s_11), null);
            jsEditor.setDark(true);
        }
    }

    private void onResume() {
        jsEditor.setCallback(object -> {
            String back = (String) object;
            switch (back) {
                case "open_ok":
                    mFile = jsEditor.getOpenedFile();
                    if (mFile != null)
                        activity.getSupportActionBar().setTitle(jsEditor.getOpenedFile().getName());
                    break;

                case "open_fail":
                    new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2) + "----OPEN", getString(R.string.s_11), null);
                    break;

                case "save_ok":
                    Toast.makeText(activity, getString(R.string.s_82), Toast.LENGTH_SHORT).show();
                    jsEditor.setEdited(false);
                    break;

                case "save_fail":
                    new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2) + "----SAVE", getString(R.string.s_11), null);
                    break;
            }
        });
    }

    public String getString(int id) {
        return activity.getApplicationContext().getResources().getString(id);
    }

    private void createMenu(Menu menu) {
        menu.clear();
        activity.getMenuInflater().inflate(R.menu.edit_menu, menu);
    }

    private void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.run:
                File file = jsEditor.getOpenedFile();
                if (file == null) {
                    new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2) + "----RUN", getString(R.string.s_11), null);
                } else {
                    jsEditor.save(file.getAbsolutePath(), obj -> {
                        String s = (String) obj;
                        switch (s) {
                            case "ok":
                                if (last_project != null) {
                                    if (last_project.inProject(file.getPath())) {
                                        activity.runOnUiThread(() -> {
                                            new Finish2Do(activity, last_project).runProject();
                                        });
                                    } else {
                                        activity.runOnUiThread(() -> {
                                            new Finish2Do(activity, file).run();
                                            Toast.makeText(activity, getString(R.string.project_waring), Toast.LENGTH_LONG).show();
                                        });
                                    }
                                } else {
                                    activity.runOnUiThread(() -> new Finish2Do(activity, file).run());
                                }
                                break;
                            case "fail":
                                activity.runOnUiThread(() -> new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2) + "----SAVE", getString(R.string.s_11), null));
                                break;
                        }
                        return false;
                    });
                }
                break;
            case R.id.undo:
                jsEditor.undo();
                break;
            case R.id.redo:
                jsEditor.redo();
                break;
            case R.id.save:
                file = jsEditor.getOpenedFile();
                if (file == null) {
                    new Dialog(activity, getString(R.string.na_4), getString(R.string.na_2) + "----SAVE", getString(R.string.s_11), null);
                } else {
                    jsEditor.save(file.getAbsolutePath());
                }
                break;
        }
    }
}
