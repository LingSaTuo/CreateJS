package com.lingsatuo.utils;

import com.lingsatuo.error.CreateJSMD5NSA;
import com.lingsatuo.error.CreateJSMD5UEE;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 15176 on 2017/6/29.
 */

public class md5 {
    public static String CreateMD5(String content) throws CreateJSMD5NSA, CreateJSMD5UEE {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new CreateJSMD5NSA("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new CreateJSMD5UEE("UnsupportedEncodingException "+e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
