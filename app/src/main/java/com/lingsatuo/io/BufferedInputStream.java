package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends java.io.BufferedInputStream {
    protected volatile byte[] buf = null;
    protected int count;
    protected int marklimit;
    protected int markpos;
    protected int pos;

    public BufferedInputStream(InputStream in) {
        super(in);
    }

    public BufferedInputStream(InputStream in, int size) {
        super(in,size);
    }

    public synchronized int read() throws IOException {
        return 0;
    }

    public synchronized int read(byte[] b, int off, int len) throws IOException {
        return 0;
    }

    public synchronized long skip(long n) throws IOException {
       return 0;
    }

    public synchronized int available() throws IOException {
        return 0;
    }

    public synchronized void mark(int readlimit) {
        super.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        super.reset();
    }

    public boolean markSupported() {
       return super.markSupported();
    }

    public void close() throws IOException {
        super.close();
    }
}