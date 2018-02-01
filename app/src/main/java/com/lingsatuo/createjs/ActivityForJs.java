package com.lingsatuo.createjs;

/**
 * Created by Administrator on 2017/11/24.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by Administrator on 2017/11/17.
 */

public class ActivityForJs extends MAIN {
    private  MAIN inthis;
    private String TAG;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInthis(this);
        TAG = getIntent().getStringExtra("TAG");
        inthis = this;
        callBack(TAG,new Object[]{"onCreate",inthis,""});
    }

    @Override
    protected void onResume() {
        if (callOnChange(TAG,new Object[]{"onResume",null,""}))
            super.onResume();
        callBack(TAG,new Object[]{"onResume",null,""});
    }

    @Override
    protected void onRestart() {
        if (callOnChange(TAG,new Object[]{"onRestart",null,""}))
            super.onRestart();
        callBack(TAG,new Object[]{"onRestart",null,""});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        callBack(TAG,new Object[]{"onCreateOptionsMenu",menu,""});
        return callOnChange(TAG,new Object[]{"onCreateOptionsMenu",menu,""});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        callBack(TAG,new Object[]{"onOptionsItemSelected",item,""});
        return callOnChange(TAG,new Object[]{"onOptionsItemSelected",item,""});
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        callBack(TAG,new Object[]{"onPrepareOptionsMenu",menu,""});
        return callOnChange(TAG,new Object[]{"onPrepareOptionsMenu",menu,""});
    }

    @Override
    public void onBackPressed() {
        if (callOnChange(TAG,new Object[]{"onBackPressed",null,""}))
            super.onBackPressed();
        callBack(TAG,new Object[]{"onBackPressed",null,""});
    }

    public void superOnBackPressed() {
        super.onBackPressed();
        callBack(TAG,new Object[]{"onBackPressed",null,""});
    }
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        callBack(TAG,new Object[]{"onOptionsMenuClosed",null,""});
        if (callOnChange(TAG,new Object[]{"onOptionsMenuClosed",menu,""}))
            super.onOptionsMenuClosed(menu);
    }

    @Override
    protected void onDestroy() {
        callBack(TAG,new Object[]{"onDestroy",null,""});
        if (callOnChange(TAG,new Object[]{"onDestroy",null,""}))
            super.onDestroy();
    }

    @Override
    protected void onStop() {
        callBack(TAG,new Object[]{"onStop",null,""});
        if (callOnChange(TAG,new Object[]{"onStop",null,""}))
            super.onStop();
    }

    @Override
    protected void onStart() {
        callBack(TAG,new Object[]{"onStart",null,""});
        if (callOnChange(TAG,new Object[]{"onStart",null,""}))
            super.onStart();
    }
}
