package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends java.io.ByteArrayInputStream {
    protected byte[] buf = null;
    protected int count;
    protected int mark;
    protected int pos;

    public ByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    public ByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public synchronized int read() {
        return 0;
    }

    public synchronized int read(byte[] b, int off, int len) {
        return 0;
    }

    public synchronized long skip(long n) {
       return super.skip(n);
    }

    public synchronized int available() {
       return super.available();
    }

    public boolean markSupported() {
      return super.markSupported();
    }

    public void mark(int readAheadLimit) {
        super.mark(readAheadLimit);
    }

    public synchronized void reset() {
       super.reset();
    }

    public void close() throws IOException {
        super.close();
    }
}
