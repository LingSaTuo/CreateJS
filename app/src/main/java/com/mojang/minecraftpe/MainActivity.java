package com.mojang.minecraftpe;

import android.app.Activity;

import com.lingsatuo.CreateRunApplication.BaseMain;

/**
 * Created by 15176 on 2017/6/28.
 */

public class MainActivity {
    public static class currentMainActivity{
        public static BaseMain get(){
            return BaseMain.inthis;
        }
    }
}
