package com.lingsatuo.error;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by 15176 on 2017/6/28.
 */

public class CreateJSException extends Exception {
    public CreateJSException() {
        super();
    }

    public CreateJSException(String message) {
        super(message);
    }

    public CreateJSException(String message, Throwable cause) {
        super(message,cause);
    }

    public CreateJSException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected CreateJSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
       super(message, cause, enableSuppression, writableStackTrace);
    }
}
