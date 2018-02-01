package com.lingsatuo.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by 15176 on 2017/6/29.
 */

public class UriUtils {
//    public static String getUrl(String path) throws IOException {
//        URL url = new_data URL(path);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(5000);
//        InputStream is = conn.getInputStream();
//        byte[] data = readIS(is);
//        String html = new_data String(data,"GBK");
//        return html;
//    }
    public static InputStream getUrl(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        InputStream is = conn.getInputStream();
        return is;
    }
    static byte[] readIS(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer))!=-1){
            bos.write(buffer,0,len);
        }
        is.close();
        return  bos.toByteArray();
    }
    public static void download(final List<String> mUrl, final Handler mHandler, final List<String> name,boolean update) {
        if (mUrl.size()<1) {
            mHandler.sendEmptyMessage(-2);
            return;
        }
        new Thread(() -> {
            try {
                for (int a = 0 ;a < mUrl.size();a++) {
                    URL url = new URL(mUrl.get(a));
                    URLConnection urlConnection = url.openConnection();
                    int contentLength = urlConnection.getContentLength(); //下载文件大小
                    InputStream inputStream = urlConnection.getInputStream();
                    String downloadFolderName = Environment.getExternalStorageDirectory() + "/.CreateJS/download/";
                    File file = new File(downloadFolderName);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String fileName = downloadFolderName + name.get(a);
                    File download = new File(fileName);
                    if (download.exists()) {
                        download.delete();
                    }
                    int downloadSize = 0;
                    byte[] buff = new byte[1024];
                    int length = 0;
                    OutputStream outputStream = new FileOutputStream(download);
                    while ((length = inputStream.read(buff)) != -1) {
                        outputStream.write(buff, 0, length);
                        downloadSize += length;
                        int progress = (downloadSize * 100 / contentLength) < 0 ? (downloadSize * 100 / contentLength) : 233333;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        msg.obj = progress;
                        mHandler.sendMessage(msg);
                    }
                    outputStream.close();
                    inputStream.close();
                    Message msg = mHandler.obtainMessage();
                    if (!update)
                        msg.what = 1;
                    else
                        msg.what = 100;
                    msg.obj = name.get(a);
                    mHandler.sendMessage(msg);
                }
            } catch (IOException e) {
                Message msg = mHandler.obtainMessage();
                if (!update)
                    msg.what = -1;
                else
                    msg.what = -100;
                msg.obj = e.toString();
                mHandler.sendMessage(msg);
            }

        }).start();
    }
}
