package com.lingsatuo.io;

import android.os.Environment;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

/**
 * Created by 15176 on 2017/6/28.
 */

public class File extends java.io.File {
    public static final String pathSeparator = null;
    public static final char pathSeparatorChar = 0;
    public static final String separator = null;
    public static final char separatorChar = 0;
    private String pathname;
    public File(String pathname) {
        super(pathname);
        this.pathname = pathname;
    }

    public File(String parent, String child) {
        super(parent,child);
    }

    public File(File parent, String child) {
        super(parent,child);
    }

    public File(URI uri) {
        super(uri);
    }

    public String getName() {
        return "Safe Mode";
    }

    public String getParent() {
        return Environment.getExternalStorageDirectory()+"/Safe Mode";
    }

    public java.io.File getParentFile() {
        java .io.File file = new  java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode");
        file.mkdirs();
        return file;
    }

    public String getPath() {
       return Environment.getExternalStorageDirectory()+"/Safe Mode";
    }

    public boolean isAbsolute() {
        return false;
    }

    public String getAbsolutePath() {
        return Environment.getExternalStorageDirectory()+"/Safe Mode";
    }

    public java.io.File getAbsoluteFile() {
       java .io.File file = new  java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode");
        file.mkdirs();
        return file;
    }

    public String getCanonicalPath() throws IOException {
        return Environment.getExternalStorageDirectory()+"/Safe Mode";
    }

    public java.io.File getCanonicalFile() throws IOException {
        java .io.File file = new  java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode");
        file.mkdirs();
        return file;
    }

    /** @deprecated */
    @Deprecated
    public URL toURL() throws MalformedURLException {
        return super.toURL();
    }

    public URI toURI() {
        return super.toURI();
    }

    public boolean canRead() {
        return false;
    }

    public boolean canWrite() {
        return false;
    }

    public boolean exists() {
        return true;
    }

    public boolean isDirectory() {
        return false;
    }

    public boolean isFile() {
        return super.isFile();
    }

    public boolean isHidden() {
        return super.isHidden();
    }

    public long lastModified() {
        return super.lastModified();
    }

    public long length() {
        return super.length();
    }

    public boolean createNewFile() throws IOException {
        return false;
    }

    public boolean delete() {
        return false;
    }

    public void deleteOnExit() {

    }

    public String[] list() {
        return new String[]{"Safe Mode"};
    }

    public String[] list(FilenameFilter filter) {
        return new String[]{"Safe Mode"};
    }

    public java.io.File[] listFiles() {
        return new java.io.File[]{new java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode")};
    }

    public java.io.File[] listFiles(FilenameFilter filter) {
        return new java.io.File[]{new java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode")};
    }

    public java.io.File[] listFiles(FileFilter filter) {
        return new java.io.File[]{new java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode")};
    }

    public boolean mkdir() {
        return false;
    }

    public boolean mkdirs() {
        return false;
    }

    public boolean renameTo(java.io.File dest) {
       return false;
    }

    public boolean setLastModified(long time) {
        return false;
    }

    public boolean setReadOnly() {
        return false;
    }

    public boolean setWritable(boolean writable, boolean ownerOnly) {
        return false;
    }

    public boolean setWritable(boolean writable) {
       return false;
    }

    public boolean setReadable(boolean readable, boolean ownerOnly) {
        return false;
    }

    public boolean setReadable(boolean readable) {
        return false;
    }

    public boolean setExecutable(boolean executable, boolean ownerOnly) {
        return false;
    }

    public boolean setExecutable(boolean executable) {
        return false;
    }

    public boolean canExecute() {
        return super.canExecute();
    }

    public static java.io.File[] listRoots() {
        return new java.io.File[]{new java.io.File(Environment.getExternalStorageDirectory()+"/.Safe Mode")};
    }

    public long getTotalSpace() {
        return super.getTotalSpace();
    }

    public long getFreeSpace() {
        return super.getFreeSpace();
    }

    public long getUsableSpace() {
        return super.getUsableSpace();
    }

    public static java.io.File createTempFile(String prefix, String suffix, java.io.File directory) throws IOException {
        return java.io.File.createTempFile(prefix, suffix, directory);
    }

    public static java.io.File createTempFile(String prefix, String suffix) throws IOException {
        return java.io.File.createTempFile(prefix, suffix);
    }

    public int compareTo(java.io.File pathname) {
        return -1;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return -1;
    }

    public String toString() {
        return super.toString();
    }

    public Path toPath() {
        return super.toPath();
    }
}
