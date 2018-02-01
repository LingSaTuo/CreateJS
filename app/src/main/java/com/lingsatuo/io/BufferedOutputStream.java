package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */


import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends java.io.BufferedOutputStream {
    protected byte[] buf = null;
    protected int count;

    public BufferedOutputStream(OutputStream out) {
        super(out);
    }

    public BufferedOutputStream(OutputStream out, int size) {
        super(out,size);
    }

    public synchronized void write(int b) throws IOException {
    }

    public synchronized void write(byte[] b, int off, int len) throws IOException {
    }

    public synchronized void flush() throws IOException {
        super.flush();
    }
}
