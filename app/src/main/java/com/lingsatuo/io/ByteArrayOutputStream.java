package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class ByteArrayOutputStream extends java.io.ByteArrayOutputStream {
    protected byte[] buf = null;
    protected int count;

    public ByteArrayOutputStream() {
        super();
    }

    public ByteArrayOutputStream(int size) {
       super(size);
    }

    public synchronized void write(int b) {

    }

    public synchronized void write(byte[] b, int off, int len) {
    }

    public synchronized void writeTo(OutputStream out) throws IOException {
    }

    public synchronized void reset() {
        super.reset();
    }

    public synchronized byte[] toByteArray() {
        return super.toByteArray();
    }

    public synchronized int size() {
        return super.size();
    }

    public synchronized String toString() {
       return "Safe Mode"+super.toString();
    }

    public synchronized String toString(String charsetName) throws UnsupportedEncodingException {
        return "Safe Mode"+super.toString(charsetName);
    }

    /** @deprecated */
    @Deprecated
    public synchronized String toString(int hibyte) {
        return "Safe Mode"+super.toString(hibyte);
    }

    public void close() throws IOException {
        super.close();
    }
}
