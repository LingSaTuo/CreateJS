package com.lingsatuo.io;

/**
 * Created by 15176 on 2017/6/28.
 */

import java.io.IOException;
import java.io.Writer;

public class BufferedWriter extends java.io.BufferedWriter {
    public BufferedWriter(Writer out) {
        super(out);
    }

    public BufferedWriter(Writer out, int sz) {
        super(out, sz);
    }

    public void write(int c) throws IOException {
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
    }

    public void write(String s, int off, int len) throws IOException {
    }

    public void newLine() throws IOException {
        super.newLine();
    }

    public void flush() throws IOException {
        super.flush();
    }

    public void close() throws IOException {
        super.close();
    }
}
