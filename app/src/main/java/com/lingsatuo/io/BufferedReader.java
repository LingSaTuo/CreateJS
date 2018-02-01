package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */

import java.io.IOException;
import java.io.Reader;
import java.util.stream.Stream;

public class BufferedReader extends java.io.BufferedReader {
    public BufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    public BufferedReader(Reader in) {
        super(in);
    }

    public int read() throws IOException {
        return 0;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    public String readLine() throws IOException {
        return "Safe Mode";
    }

    public long skip(long n) throws IOException {
       return super.skip(n);
    }

    public boolean ready() throws IOException {
        return super.ready();
    }

    public boolean markSupported() {
        return super.markSupported();
    }

    public void mark(int readAheadLimit) throws IOException {
        super.mark(readAheadLimit);
    }

    public void reset() throws IOException {
        super.reset();
    }

    public void close() throws IOException {
        super.close();
    }

    public Stream<String> lines() {
       return super.lines();
    }
}