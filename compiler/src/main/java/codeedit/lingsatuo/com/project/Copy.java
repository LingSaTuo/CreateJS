package codeedit.lingsatuo.com.project;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/12/13.
 */

public class Copy {
    private Context context;
    private Project project;
    public Copy(Context context,Project project){
        this.project = project;
        this.context = context;
    }
    public void createTree(Back back) throws IOException {
        if (new File(project._getRootDir()).exists())throw new IOException(project._getRootDir()+" existing ");
        switch (project.getType()){
            case JS:
                jstree();
                jsfile();
                break;
            case ZIP:
                ziptree();
                zipfile();
                break;
            case THEME:
                themetree();
                themefile();
                break;
            case ANDROID:
                apptree();
                appfile();
                break;
        }
        if (back!=null){
            back.T(project,null,"");
        }
    }
    //================================   JS  ====================
    private void jstree() throws IOException {
        String[] list = new String[]{"build","main"};
        File f = new File(project._getRootDir());
        if (!f.exists()){
            f.mkdirs();
        }else if (f.isFile()){
            throw new IOException(f.getPath()+"  is file");
        }
        for (String s: list){
            File file = new File(project._getRootDir()+"/"+s);
            if (file.isFile()){
                throw new IOException(f.getPath()+"  is file");
            }else{
                file.mkdirs();
            }
        }
    }
    private void jsfile() throws IOException {
        //创建build.js
        File file = new File(project._getBuildJ());
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        write(file,getAssets("project/js/build.js"));
        //创建build.txt
        File file2 = new File(project._getBuildT());
        if (file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        String str = getAssets("project/js/build.txt");
        str = str.replaceAll("\\{PROJECT\\}","js");
        write(file2,str);

        //创建main.js
        File file3 = new File(project._getRootDir()+"/"+project.getMainPath());
        if (file3.exists()){
            file3.delete();
        }
        file3.createNewFile();
        str = getAssets("project/js/main/main.js");
        str = str.replaceAll("\\{PROJECT\\}","js");
        write(file3,str);
        File file4 = new File(project._getRootDir()+"/.nomedia");
        write(file4,"");
        File file5 = new File(project._getRootDir()+"/open.js");
        if (file5.exists()){
            file5.delete();
        }
        file5.createNewFile();
        write(file5,getAssets("project/js/open.js"));
    }


    //================================   ZIP ====================
    private void ziptree() throws IOException {
        String[] list = new String[]{"build","main","main/script"};
        File f = new File(project._getRootDir());
        if (!f.exists()){
            f.mkdirs();
        }else if (f.isFile()){
            throw new IOException(f.getPath()+"  is file");
        }
        for (String s: list){
            File file = new File(project._getRootDir()+"/"+s);
            if (file.isFile()){
                throw new IOException(f.getPath()+"  is file");
            }else{
                file.mkdirs();
            }
        }
    }
    private void zipfile() throws IOException {
        //创建build.js
        File file = new File(project._getBuildJ());
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        write(file,getAssets("project/zip/build.js"));

        //创建build.txt
        File file2 = new File(project._getBuildT());
        if (file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        String str = getAssets("project/zip/build.txt");
        str = str.replaceAll("\\{PROJECT\\}","zip");
        write(file2,str);

        //创建main.js
        File file3 = new File(project._getRootDir()+"/"+project.getMainPath());
        if (file3.exists()){
            file3.delete();
        }
        file3.createNewFile();
        str = getAssets("project/zip/main/script/main.js");
        str = str.replaceAll("\\{PROJECT\\}","zip");
        write(file3,str);
        File file4 = new File(project._getRootDir()+"/.nomedia");
        write(file4,"");
        File file5 = new File(project._getRootDir()+"/open.js");
        if (file5.exists()){
            file5.delete();
        }
        file5.createNewFile();
        write(file5,getAssets("project/zip/open.js"));
    }


    //================================   Android ====================
    private void apptree() throws IOException {
        String[] list = new String[]{"build","main","main/script","main/res","main/super","main/res/drawable"
                ,"main/res/layout","main/res/values","main/super/res","main/super/res/drawable","main/super/res/mipmap"};

        File f = new File(project._getRootDir());
        if (!f.exists()){
            f.mkdirs();
        }else if (f.isFile()){
            throw new IOException(f.getPath()+"  is file");
        }
        for (String s: list){
            File file = new File(project._getRootDir()+"/"+s);
            if (file.isFile()){
                throw new IOException(f.getPath()+"  is file");
            }else{
                file.mkdirs();
            }
        }
    }
    private void appfile() throws IOException {
        //创建build.js
        File file = new File(project._getBuildJ());
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        write(file,getAssets("project/zip/build.js"));

        //创建build.txt
        File file2 = new File(project._getBuildT());
        if (file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        String str = getAssets("project/android/build.txt");
        str = str.replaceAll("\\{PROJECT\\}","App");
        write(file2,str);

        //创建main.js
        File file3 = new File(project._getRootDir()+"/"+project.getMainPath());
        if (file3.exists()){
            file3.delete();
        }
        file3.createNewFile();
        str = getAssets("project/android/main/script/main.js");
        str = str.replaceAll("\\{PROJECT\\}","zip");
        write(file3,str);
        File file4 = new File(project._getRootDir()+"/.nomedia");
        write(file4,"");
        FileUtils.getInstance(context).copyAssetsToDst(context,"project/android/main",project._getRootDir()+"/main");
        File file5 = new File(project._getRootDir()+"/open.js");
        if (file5.exists()){
            file5.delete();
        }
        file5.createNewFile();
        write(file5,getAssets("project/zip/open.js"));
    }







    //================================   theme ====================
    private void themetree() throws IOException {
        String[] list = new String[]{"build","main"};
        File f = new File(project._getRootDir());
        if (!f.exists()){
            f.mkdirs();
        }else if (f.isFile()){
            throw new IOException(f.getPath()+"  is file");
        }
        for (String s: list){
            File file = new File(project._getRootDir()+"/"+s);
            if (file.isFile()){
                throw new IOException(f.getPath()+"  is file");
            }else{
                file.mkdirs();
            }
        }
    }
    private void themefile() throws IOException {
        //创建build.js
        File file = new File(project._getBuildJ());
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        write(file,getAssets("project/theme/build.js"));

        //创建build.txt
        File file2 = new File(project._getBuildT());
        if (file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        String str = getAssets("project/theme/build.txt");
        str = str.replaceAll("\\{PROJECT\\}","theme");
        write(file2,str);

        //创建main.ct
        File file3 = new File(project._getRootDir()+"/"+project.getMainPath());
        if (file3.exists()){
            file3.delete();
        }
        file3.createNewFile();
        str = getAssets("project/theme/main/main.ct");
        str = str.replaceAll("\\{PROJECT\\}","theme");
        write(file3,str);
        File file4 = new File(project._getRootDir()+"/.nomedia");
        write(file4,"");
        File file5 = new File(project._getRootDir()+"/open.js");
        if (file5.exists()){
            file5.delete();
        }
        file5.createNewFile();
        write(file5,getAssets("project/theme/open.js"));
    }
















    private void write(File file,String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(str.getBytes());
        fos.close();
    }
    private String getAssets(String name) throws IOException {
        InputStream is = context.getAssets().open(name);
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        isr.close();
        br.close();
        if (sb.length()>0)
            sb.deleteCharAt(sb.length() - 1);
        String str = sb.toString();
        if (project.getMainPath()==null||project.getName()==null||project.getOutPutPath()==null){
            throw new IOException("MainPath is null or Name or Out is null");
        }
        str = str.replaceAll("\\{NAME\\}",project.getName())
                .replaceAll("\\{OUT\\}",project.getOutPutPath())
                .replaceAll("\\{MAIN\\}",project.getMainPath());
        return str;
    }
}
