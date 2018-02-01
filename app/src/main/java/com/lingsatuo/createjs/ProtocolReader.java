package com.lingsatuo.createjs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lingsatuo.Dialog.Dialog;

/**
 * Created by 15176 on 2017/9/16.
 */

public class ProtocolReader extends MAIN {
   //
    WebView wb ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apihelper);
        setSupportActionBar(findViewById(R.id.toolbar));
        wb = findViewById(R.id.webView);
        WebSettings ws = wb.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        String path = getIntent().getStringExtra("path");
        wb.loadUrl("file:///"+path);
        wb.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                new Dialog(ProtocolReader.this,"加载失败",(String)error.getDescription(),"确定",null);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (wb.canGoBack()){
            wb.goBack();
            return;
        }
        super.onBackPressed();
    }
}
