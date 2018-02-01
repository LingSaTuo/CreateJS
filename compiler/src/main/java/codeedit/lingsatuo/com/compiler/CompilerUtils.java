package codeedit.lingsatuo.com.compiler;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

/**
 * Created by 15176 on 2017/6/29.
 */

public class CompilerUtils {
    public void unpack(String zip, String to) throws Exception {
        loadZip(zip, to);
    }

    public void compressZip(String zip, String from) throws Exception {
        compress(zip, from);
    }

    public static void compress(String save, String from) throws Exception {
        File zipFile = new File(save);
        File srcdir = new File(from);
        if (!srcdir.exists())
            throw new RuntimeException(from + "not found");
            Project prj = new Project();
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(zipFile);
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            fileSet.setDir(srcdir);
            //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
            //fileSet.setExcludes(...); 排除哪些文件或文件夹
            zip.addFileset(fileSet);

            zip.execute();
    }

    public static void loadZip(String unZipfile, String destFile) throws Exception {
        loadZip(unZipfile,destFile,true);
    }


    public static void loadZip(String unZipfile, String destFile,boolean rewrite) throws Exception {
        // unZipfileName需要解压的zip文件名
        FileOutputStream fileOut;
        File file;
        InputStream inputStream;
        ZipFile zipFile;
        ZipOutputStream zipOut;
        //压缩Zip
        int bufSize = 1024 * 4;
        //size of bytes
        byte[] buf = new byte[bufSize];
        //生成一个zip的文件
        zipFile = new ZipFile(unZipfile);

        if (new File(destFile).exists()) {
            deleteFolder(destFile);
        }
        //遍历zipFile中所有的实体，并把他们解压出来
        for (
                Enumeration<ZipEntry> entries = zipFile.getEntries(); entries
                .hasMoreElements(); ) {
            ZipEntry entry = entries.nextElement();
            //生成他们解压后的一个文件
            file = new File(destFile + File.separator + entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                if (file.exists()&&rewrite)continue;
                // 如果指定文件的目录不存在,则创建之.
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                //获取出该压缩实体的输入流
                inputStream = zipFile.getInputStream(entry);
                fileOut = new FileOutputStream(file);
                int length = 0;
                //将实体写到本地文件中去
                while ((length = inputStream.read(buf)) > 0) {
                    fileOut.write(buf, 0, length);
                }
                fileOut.close();
                inputStream.close();
            }
        }
        zipFile.close();
    }



    public static void deleteFolder(String ff) throws Exception {
        File file = new File(ff);
        if (file.isFile()) {
            file.delete();
        } else if (!file.exists() || !file.isDirectory()) {
            return;
        } else {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else {
                    deleteFolder(f.getPath());
                }
            }
            file.delete();
        }
    }
}
