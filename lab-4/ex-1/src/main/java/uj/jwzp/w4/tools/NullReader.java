package uj.jwzp.w4.tools;

import java.io.Reader;

public class NullReader extends Reader {

    private final static NullReader INSTANCE = new NullReader();

    public static Reader nullReader() {
        return INSTANCE;
    }

    private NullReader() {}

    @Override
    public int read(char[] cbuf, int off, int len) {
        return 0;
    }

    @Override
    public void close() {
    }
}
