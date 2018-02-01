package com.applicationExc;

import android.content.Context;

import com.lingsatuo.createjs.R;
import com.lingsatuo.error.CreateJSMD5NSA;
import com.lingsatuo.error.CreateJSMD5UEE;
import com.lingsatuo.utils.md5;

import java.util.Random;

/**
 * Created by Administrator on 2017/11/7.
 */

public class app_is_cjs {
    static String my_id_is = "97bd121e6ab7520ae995e0031ec8051a";
    static String my_bmob_id = "b5e1438bfa50c897a236bd5b92f9906e";
    static String mt = "Y2MuYmlubXQuc2lnbmF0dXJlLlBtc0hvb2tBcHBsaWNhdGlvbg==";
    static String[] arr;
    public static String getMy_bmob_id(){
        return my_bmob_id;
    }
    public static boolean isCjs() throws CreateJSMD5NSA, CreateJSMD5UEE, ClassNotFoundException {
        Class.forName(md5.CreateMD5(mt));
        if (!my_id_is.equals(App.my_id_is)){
            return false;
        }
        return true;
    }
    public static String my_id_is(){
        return App.my_id_is;
    }
    public static String true_id_is(){
        return my_id_is;
    }
    public static String getMessage(Context context){
        if (arr==null){
            arr = context.getResources().getStringArray(R.array.cjs);
        }

        Random random = new Random();
        String mess = arr[random.nextInt(arr.length-1)];
        return mess;
    }
}
