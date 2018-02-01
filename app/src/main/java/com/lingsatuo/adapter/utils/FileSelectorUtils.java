package com.lingsatuo.adapter.utils;

import com.lingsatuo.callbackapi.listDir;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FileSelectorUtils {
    public static List<File> getData(File file) {
        return getData(file, null);
    }

    public static List<File> getData(File file, listDir listDir) {
        if (file == null || !file.canRead())
            return new ArrayList<>();
        List<File> list = new ArrayList<>();
        if (file.isFile()) {
            return new ArrayList<>();
        } else {
            File[] files1 = file.listFiles();
            if (files1 == null) return new ArrayList<>();
            else {
                if (listDir == null) {
                    for (File f : files1) {
                        list.add(f);
                    }
                } else {
                    for (File f : files1) {
                        if (listDir.T(f.getPath())) {
                            list.add(f);
                        }
                    }
                }
            }
        }
        return getGroup(list);
    }

    public static List<File> getGroup(List<File> list) {
        List<File> floders = new ArrayList<>();
        List<File> files = new ArrayList<>();
        for (File file : list) {
            if (file.isFile()) {
                files.add(file);
            } else {
                floders.add(file);
            }
        }
        Collections.sort(floders, (file, t1) -> file.getName().toLowerCase().compareTo(t1.getName().toLowerCase()));
        Collections.sort(files, (file, t1) -> file.getName().toLowerCase().compareTo(t1.getName().toLowerCase()));
        floders.addAll(files);
        return floders;
    }
}
