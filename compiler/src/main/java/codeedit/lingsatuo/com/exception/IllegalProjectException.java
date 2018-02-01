package codeedit.lingsatuo.com.exception;

/**
 * Created by Administrator on 2017/12/3.
 */

public class IllegalProjectException extends IllegalStateException{
    private int id = 0;
    public IllegalProjectException(int id){
        super();
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int s){
        this.id = s;
    }

    public IllegalProjectException(String s) {
        super(s);
    }

    public IllegalProjectException(String message, Throwable cause) {
        super(message,cause);
    }

    public IllegalProjectException(Throwable cause) {
        super(cause);
    }
}
