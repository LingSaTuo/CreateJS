package codeedit.lingsatuo.com.compiler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.File;

import codeedit.lingsatuo.com.exception.IllegalProjectException;
import codeedit.lingsatuo.com.project.Project;

/**
 * Created by Administrator on 2017/12/3.
 */

public class Compiler {
    private final Project project;
    private boolean run = true;

    private Compiler(Project project) throws IllegalProjectException {
        this.project = project;
        try {
            checkProject();
        } catch (IllegalProjectException e) {
            throw e;
        }
    }

    public static Compiler getInstance(Project project) throws IllegalProjectException {
        try {
            return new Compiler(project);
        } catch (IllegalProjectException e) {
            throw e;
        }
    }

    private void checkProject() throws IllegalProjectException {
        IllegalProjectException illegalProjectException;
        if (project == null) {
            illegalProjectException = new IllegalProjectException("The project is unable to compile for empty ");
            illegalProjectException.setId(1001);
            throw illegalProjectException;
        }
        if (project._getRootDir() == null) {
            illegalProjectException = new IllegalProjectException("The project root directory is empty and unable to compile ");
            illegalProjectException.setId(1002);
            throw illegalProjectException;
        }
    }

    public void start(boolean run) {
        this.run = run;
        new Thread(new run()).start();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    IllegalProjectException illegalProjectException = (IllegalProjectException) msg.obj;
                    project.onWaring(illegalProjectException);
                    project.onStop(illegalProjectException);
                    break;
                case 1:
                    IllegalProjectException illegalProjectException1 = (IllegalProjectException) msg.obj;
                    project.onErr(illegalProjectException1);
                    project.onStop(illegalProjectException1);
                    break;
                case 2:
                    IllegalProjectException e = (IllegalProjectException) msg.obj;
                    project.onErr(e);
                    project.onStop(e);
                    break;
                case 3:
                    IllegalProjectException illegalProjectException2 = (IllegalProjectException) msg.obj;
                    project.onWaring(illegalProjectException2);
                    break;
                case 4:
                    project.onFinish();
                    break;
                case 5:
                    project.onFinally();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private class run implements Runnable {
        @Override
        public void run() {
            IllegalProjectException illegalProjectException;
            if (!project.onStart()) {
                illegalProjectException = new IllegalProjectException("OnStart reverts back to false, and the compilation is interrupted");
                illegalProjectException.setId(1003);
                Message message = new Message();
                message.what = 0;
                message.obj = illegalProjectException;
                handler.sendMessage(message);
                return;
            }
            File[] file = new File(project._getRootDir()).listFiles();
            if (file == null || file.length < 1) {
                illegalProjectException = new IllegalProjectException("A reasonable project is a folder, not a file, not an empty folder");
                illegalProjectException.setId(1004);
                Message message = new Message();
                message.what = 1;
                message.obj = illegalProjectException;
                handler.sendMessage(message);
                return;
            }
            int id = -1;
            for (File f : file) {
                id++;
                boolean con = false;
                try {
                    con = project.onCompilering(new onCompiler(f.getPath(), id));
                } catch (IllegalProjectException e) {
                    Message message = new Message();
                    message.what = 2;
                    message.obj = e;
                    handler.sendMessage(message);
                    return;
                }
                if (!con) {
                    illegalProjectException = new IllegalProjectException("onCompilering reverts back to false, and the compilation is skip");
                    illegalProjectException.setId(1005);
                    Message message = new Message();
                    message.what = 3;
                    message.obj = illegalProjectException;
                    handler.sendMessage(message);
                }
            }
            if (run)handler.sendEmptyMessage(4);
            else
                handler.sendEmptyMessage(5);
        }
    }
}
