package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */
import java.io.IOException;
import java.io.InputStream;

public class FilterInputStream extends java.io.FilterInputStream {
    protected volatile InputStream in;

    protected FilterInputStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        return super.read();
    }

    public int read(byte[] b) throws IOException {
        return super.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    public int available() throws IOException {
        return super.available();
    }

    public void close() throws IOException {
        super.close();
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
}
