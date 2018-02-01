package com.lingsatuo.createjs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kingsatuo.view.CodeView;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;

import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


/**
 * Created by Administrator on 2017/11/5.
 */

public class CodeReader extends MAIN {
    CodeView codeView;
    String string;
    String path = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codereader);
        setSupportActionBar(findViewById(R.id.toolbar));
        codeView = new CodeView();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.codeview,codeView)
                .commit();
        path = CodeReader.this.getIntent().getStringExtra("path");
        new Thread(() -> {
            try {
                if (path.startsWith("file:///android_asset")){
                    string =  Utils.getAssetsString(CodeReader.this.getApplicationContext(),path.replaceAll("file:///android_asset/",""));
                }else {
                    string = Utils.readStringFromFile(path);
                }
            } catch (CreateJSIOException e) {
                string = e.getMessage();
            }
            CodeReader.this.runOnUiThread(()->{
                io.github.kbiakov.codeview.CodeView mCodeView = codeView.getCodeView();
                mCodeView.updateOptions(options -> {
                    options.withFont(Font.Consolas)
                            .withCode(string)
                            .withTheme(ColorTheme.MONOKAI)
                            .withLanguage("js");
                    return null;
                });
            });
        }).start();
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.codereader,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.run:
                Intent i = new Intent(this,LoadingActivity.class);
                if (path.startsWith("file:///android_asset")){
                    try {
                        String _path = Utils.getSD()+"/.CreateJS/cache/"+Utils.getRandom(6)+".js";
                        Utils.saveToFile(_path,string);
                        path = _path;
                    } catch (CreateJSIOException e) {
                        Toast.makeText(this,this.getResources().getString(R.string.s_52),1).show();
                        return true;
                    }
                }
                i.putExtra("path",path);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
