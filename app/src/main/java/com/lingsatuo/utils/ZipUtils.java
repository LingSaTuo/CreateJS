package com.lingsatuo.utils;

import codeedit.lingsatuo.com.compiler.CompilerUtils;

/**
 * Created by 15176 on 2017/6/29.
 */

public class ZipUtils extends CompilerUtils{
    public void unpack(String zip, String to) throws Exception {
        super.unpack(zip,to);
    }

    public void compressZip(String zip, String from) throws Exception {
        super.compressZip(zip,from);
    }

    public static void compress(String save, String from) throws Exception {
        try {
            CompilerUtils.compress(save, from);
        }catch (Exception e){
            throw e;
        }
    }

    public static void loadZip(String unZipfile, String destFile) throws Exception {
            loadZip(unZipfile, destFile,true);
    }
    public static void loadZip(String unZipfile, String destFile,boolean rewrite) throws Exception {
        // unZipfileName需要解压的zip文件名
        try {
            CompilerUtils.loadZip(unZipfile, destFile,rewrite);
        }catch (Exception e){
            throw e;
        }
    }
}
