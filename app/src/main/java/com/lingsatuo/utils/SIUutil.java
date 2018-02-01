package com.lingsatuo.utils;

import android.app.Activity;

import com.lingsatuo.callbackapi.Function;
import com.lingsatuo.listener.OnDownload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 15176 on 2017/9/12.
 */

public class SIUutil extends Utils {

    private byte[] readIS(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer))!=-1){
            bos.write(buffer,0,len);
        }
        is.close();
        return bos.toByteArray();
    }
    public void getString(Activity si , String path, OnLoadingUriString listener) {
        new Thread(() -> {
            URL url = null;
            try {
                url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                byte[] data = readIS(connection.getInputStream());
                String str = new String(data, "UTF-8");
                if (listener!=null){
                    si.runOnUiThread(() -> {
                        listener.onLoadingSuccess(str);
                    });
                }
            } catch (IOException e) {
                if (listener!=null){
                    si.runOnUiThread(() -> {
                        listener.onLoadingFaild(e);
                    });
                }
            }
        }).start();
    }

    public void getString(Activity si , String path, Function function,int dex){
        this.getString(si, path, new OnLoadingUriString() {
            @Override
            public void onLoadingSuccess(String str) {
                if (function!=null){
                    function.T(str,"1");
                }
            }

            @Override
            public void onLoadingFaild(Throwable e) {
                if (function!=null){
                    function.T(e.getMessage(),"-1");
                }
            }
        });
    }

    int downloadSize = 0;
    boolean finish = false;
    public void Download(Activity activity, String downloaduri, String savepath, OnDownload onDownload) {
        downloadSize = 0;
        finish = false;
        new Thread(() -> {
            URL url = null;
            try {
                url = new URL(downloaduri);
                URLConnection urlConnection = url.openConnection();
                int contentLength = urlConnection.getContentLength();
                InputStream inputStream = urlConnection.getInputStream();
                File file = new File(savepath).getParentFile();
                if (!file.exists()) {
                    file.mkdirs();
                }
                File download = new File(savepath);
                if (download.exists()) {
                    download.delete();
                }
                download.createNewFile();


                byte[] buff = new byte[1024];
                int length = 0;
                OutputStream outputStream = new FileOutputStream(download);
                new Thread(() -> {
                    while(!finish){
                        try {
                            Thread.sleep(50);
                            activity.runOnUiThread(() -> {
                                onDownload.T(downloadSize, contentLength, false, null);
                            });
                        } catch (InterruptedException e) { }

                    }
                    if (downloadSize==contentLength||contentLength==-1)
                    activity.runOnUiThread(() -> {
                        onDownload.T(contentLength, contentLength, true, null);
                    });
                }).start();
                while ((length = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, length);
                    downloadSize += length;
                }
                outputStream.close();
                inputStream.close();
                Thread.sleep(1000);
                finish = true;
            } catch (Exception e) {
                activity.runOnUiThread(() -> {
                    finish = true;
                    onDownload.T(-1, -1, false, e);
                });
            }
        }).start();
    }
}
