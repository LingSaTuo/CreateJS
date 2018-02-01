package com.lingsatuo.error;

/**
 * Created by 15176 on 2017/6/28.
 */

public class CreateJSIOException extends CreateJSException {
    private int id = 0;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public CreateJSIOException() {
        super();
    }

    public CreateJSIOException(String message) {
        super(message);
    }

    public CreateJSIOException(String message, Throwable cause) {
        super(message,cause);
    }

    public CreateJSIOException(Throwable cause) {
        super(cause);
    }
}
