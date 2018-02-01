package com.lingsatuo.utils;

import com.lingsatuo.error.CreateJSFileNotFoundException;
import com.lingsatuo.error.CreateJSIOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by 15176 on 2017/6/28.
 */

public class Proprety {

    public static class PropretyUtils{
        private static Properties properties = new Properties();
        private static String filepath;
        public PropretyUtils setData(String path) throws CreateJSIOException {
            try {
                properties = new Properties();
                filepath = path;
                File file = new File(filepath);
                if(!file.exists()) file.createNewFile();
                return pcreateProprety(path);
            } catch (IOException e) {
                throw new CreateJSIOException(e);
            }
        }
        private PropretyUtils pcreateProprety(String path) throws IOException {
            InputStream inputStream = new FileInputStream(new File(path));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);
            return this;
        }

        public String getKeyValue(String key){
            return properties.getProperty(key);
        }
        public  PropretyUtils writePropreties(String keyname,String keyvalue) throws CreateJSIOException {
            try {
                return pwritePropreties(keyname, keyvalue);
            } catch (IOException e) {
                throw new CreateJSIOException(e);
            }
        }
        private PropretyUtils  pwritePropreties(String keyname,String keyvalue) throws IOException {
            OutputStream os = new FileOutputStream(filepath);
            properties.setProperty(keyname,keyvalue);
            properties.store(os,"Update "+keyname+"");
            return this;
        }
    }
}
