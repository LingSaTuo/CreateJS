package com.lingsatuo.createjs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationExc.App;
import com.applicationExc.app_is_cjs;
import com.kingsatuo.Console.Window;
import com.lingsatuo.adapter.MainPagerAdapter;
import com.lingsatuo.callbackapi.Function;
import com.lingsatuo.createjs.Utils.EditUtils.EditUtils;
import com.lingsatuo.createjs.Utils.SharingUtils.SharingUtils;
import com.lingsatuo.createjs.Utils.Update;
import com.lingsatuo.service.SubService;
import com.lingsatuo.utils.SIUutil;
import com.lingsatuo.window.MWindowManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/27.
 */

public class CreativeShop extends MAIN implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    DrawerLayout dl;
    MWindowManager mWindowManager;
    ImageView iv;
    NavigationView nv;
    boolean avoid = true,mt = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        MainPagerAdapter mpa = new MainPagerAdapter(getSupportFragmentManager(),this);
        ViewPager vp = findViewById(R.id.viewpager);
        vp.setAdapter(mpa);
        ((TabLayout)findViewById(R.id.maintallayout)).setupWithViewPager(vp);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
        {
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        dl = findViewById(R.id.new_main_draw);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,dl,findViewById(R.id.toolbar),
                R.string.app_name,R.string.app_name);
        dl.setDrawerListener(toggle);
        try {
            avoid = app_is_cjs.isCjs();
            dl.addDrawerListener(this);
            mt = true;
        } catch (Exception e) {
            if (!avoid){
                if (!(e instanceof ClassNotFoundException))
                dl.addDrawerListener(this);
            }
        }
        toggle.syncState();
        nv = findViewById(R.id.main_nv);
        iv = nv.getHeaderView(0).findViewById(R.id.window);
        iv.setOnClickListener(this);
        mWindowManager = MWindowManager.instance(this);
        nv.setNavigationItemSelectedListener(this);
        findViewById(R.id.exit).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.creatework,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.api:
                Intent i = new Intent(this,ProtocolReader.class);
                i.putExtra("path","android_asset/api/Home_Page.html");
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(Gravity.START)){
            dl.closeDrawer(Gravity.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.window:
                if (mWindowManager==null||mWindowManager.getLayoutParams()==null){
                    new Window(mWindowManager).setData();
                    iv.setBackgroundResource(R.mipmap.dismiss);
                }else {
                    if (mWindowManager.isShow()) {
                        mWindowManager.dismiss();
                        iv.setBackgroundResource(R.mipmap.show);
                    } else {
                        mWindowManager.show();
                        iv.setBackgroundResource(R.mipmap.dismiss);
                    }
                }
                break;
            case R.id.exit:
                App.exitApp(p -> {
                    Toast.makeText(CreativeShop.this,p[0],1).show();
                });
                break;
            case R.id.update:
                Snackbar.make(view,R.string.s_104,Snackbar.LENGTH_LONG).show();
                new Update(this,view);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_item:
                App.exitScript(p -> {
                Toast.makeText(CreativeShop.this,p[0],1).show(); try {
                        CreativeShop.this.startService(new Intent(CreativeShop.this, SubService.class));
                    }catch (Exception e){
                        Toast.makeText(CreativeShop.this,CreativeShop.this.getResources().getString(R.string.na_2),1).show();
                    }
            });
                break;
            case R.id.sharing_algorithm:
                new SharingUtils(this).startActivity();
                break;
            case R.id.start_working:
                new EditUtils(this).startActivity();
                break;
        }
        return false;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        TextView tv = nv.getHeaderView(0).findViewById(R.id.my_message);
        tv.setVisibility(View.VISIBLE);
        if (!mt) {
            tv.setText(app_is_cjs.getMessage(this.getApplicationContext()));
        }else{
            tv.setTextSize(10);
            tv.setText(this.getResources().getString(R.string.e_0));
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
