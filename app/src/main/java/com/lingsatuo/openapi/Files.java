package com.lingsatuo.openapi;

import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

import com.lingsatuo.callbackapi.listDir;
import com.lingsatuo.error.CreateJSIOException;
import com.lingsatuo.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class Files extends Utils {
    public void copy(String from,String to) throws IOException {
        File f = new File(from);
        if (f.isDirectory()) {
             to = new File(to + "/" + new File(from).getName()).getPath();
        }
        copy(new File(from),new File(to));
    }
    public boolean move(String from,String to){
        return true;
    }
    public boolean rename(String path,String name){
        String f = new File(path).getParent();
        return new File(path).renameTo(new File(f+"/"+name));
    }


    public void copy(File from,File to) throws IOException {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        FileChannel in = null;
        FileChannel out = null;
        if(from.isFile()){
            if (!to.getParentFile().exists())to.getParentFile().mkdirs();
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0,in.size(),out);
            fis.close();
            in.close();
            fos.close();
            out.close();
        }else{
            if(!to.exists()){
                to.mkdirs();
            }
            for(File file:from.listFiles()){
                copy(file,new File(to.getPath()+"/"+file.getName()));
            }
        }
    }

    public String getNameWithOutExtension(String path){
        return super.getFileNameNoEnd(new File(path).getName());
    }

    public boolean remove(String path){
        return new File(path).delete();
    }
    public boolean removeDir(String path){
        try {
            super.deleteFile(path);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public String getSdcardPath(){
        return Environment.getExternalStorageDirectory().getPath();
    }

    public String getExtension(String filename){
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if (dot<0||dot==filename.length()-1)return "";
            return filename.substring(dot).toLowerCase();
        }
        return "";
    }
    public String[] listDir(String path){
       return listDir(path,null);
    }
    public String[] listDir(String path,listDir listDir){
        File f = new File(path);
        if (f.isFile()) return null;
        List<String> strings = new ArrayList<>();
        File[] files = f.listFiles();
        for (File file : files) {
            if (listDir!=null) {
                if (listDir.T(file.getName())) {
                    strings.add(file.getName());
                }
            }else{
                strings.add(file.getName());
            }
        }
        String[] strs = new String[strings.size()];
        for (int a = 0;a<strings.size();a++){
            strs[a] = strings.get(a);
        }
        return strs;
    }

    public String readFile(String path,String encoding) throws FileNotFoundException {
        return readFile(new File(path),encoding);
    }
    public String readFile(File file,String encoding) throws FileNotFoundException {
        return readFile(new FileInputStream(file),encoding);
    }
    public String readFile(InputStream is,String encoding){

        if (encoding==null||encoding=="")encoding= Charset.defaultCharset().name();
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return new String(bytes,encoding);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD_MR1)
    public  String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }
    public String file2Base64(String path,String to) throws IOException, CreateJSIOException {
        return super.base64(path,to);
    }
    public void base642File(String encode,String to) throws IOException {
        super.base64ToFile(encode, to);
    }
}
