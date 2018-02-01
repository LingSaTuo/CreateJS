package com.lingsatuo.error;

import java.security.NoSuchAlgorithmException;

/**
 * Created by 15176 on 2017/6/29.
 */

public class CreateJSMD5NSA extends NoSuchAlgorithmException {
    public CreateJSMD5NSA() {
        super();
    }

    public CreateJSMD5NSA(String msg) {
        super(msg);
    }

    public CreateJSMD5NSA(String message, Throwable cause) {
        super(message,cause);
    }

    public CreateJSMD5NSA(Throwable cause) {
        super(cause);
    }
}
