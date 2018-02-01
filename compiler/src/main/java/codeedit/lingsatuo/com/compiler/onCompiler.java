package codeedit.lingsatuo.com.compiler;

/**
 * Created by Administrator on 2017/12/7.
 */

public class onCompiler {
    private final int id;
    private final String title;
    public onCompiler(String title,int id){
        this.id = id;
        this.title = title;
    }
    public String getPath(){
        return this.title;
    }
    public int getProgress(){
        return this.id;
    }
}
