package com.zzzmode.apkeditor;


import com.zzzmode.apkeditor.axmleditor.decode.AXMLDoc;
import com.zzzmode.apkeditor.axmleditor.decode.BTagNode;
import com.zzzmode.apkeditor.axmleditor.decode.BXMLNode;
import com.zzzmode.apkeditor.axmleditor.decode.StringBlock;
import com.zzzmode.apkeditor.axmleditor.editor.ApplicationInfoEditor;
import com.zzzmode.apkeditor.axmleditor.editor.PackageInfoEditor;
import com.zzzmode.apkeditor.axmleditor.editor.PermissionEditor;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 15/9/11.
 */
public class ApkEditor {

    private static final String WORK_DIR;

    static {
        String dir = android.os.Environment.getExternalStorageDirectory() + "/.CreateJS/cache";
        WORK_DIR = dir;
    }

    private static String A_XML = WORK_DIR + "/AndroidManifest.xml";
    private String tudo_axml;

    public void setInAXml(String xmlpath) {
        tudo_axml = xmlpath;
    }

    public void setOutAXml(String outAXml) {
        A_XML = outAXml;
    }

    public ApkEditor(String privateKey, String sigPrefix) {
        File file = new File(WORK_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.privateKey = privateKey;
        this.sigPrefix = sigPrefix;
    }

    private String privateKey;
    private String sigPrefix;

    private String appName;
    private String appIcon;
    private String packagename = "com.lingsatuo.myapp";
    private int versionCode = 0;
    private String versionName = "1";
    private List<String> permission = new ArrayList<>();

    public void setVersionCode(int code) {
        this.versionCode = code;
    }

    public void setVersionName(String name) {
        this.versionName = name;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public void setPackagename(String name) {
        this.packagename = name;
    }

    public String getPackagename() {
        return this.packagename;
    }

    public ApkEditor addPermission(String permission) {
        this.permission.add(permission);
        return this;
    }

    public List<String> getPermission() {
        return this.permission;
    }

    public void clearPermission() {
        this.permission.clear();
    }


    public String getAppName() {
        return appName;
    }

    public String getAppIcon() {
        return appIcon;
    }


    public boolean createAXML() throws Exception {
        File onefile = new File(tudo_axml);
        File twofile = new File(A_XML);
        AXMLDoc xml = new AXMLDoc();
        xml.parse(new FileInputStream(onefile));
        buildXml(xml);
        StringBlock block = xml.getStringBlock();
        BTagNode node = (BTagNode) xml.getManifestNode();


        node = node.getChildByName(block, "application");//获取application节点
        node.setAttribute(block,"debug","true");
        BTagNode mynode = null;//定义 储存任意节点
        BTagNode main = null;//存储入口节点
        boolean btag0 = false, btag1 = false;
        a:
        for (BXMLNode one : node.getChildren()) {//获取application节点下所有子节点
            mynode = (BTagNode) one;
            if (mynode.getName(block).equals("activity")) {//如果是activity节点
                mynode = mynode.getChildByName(block, "intent-filter");
                if (mynode == null) continue;
                b:
                for (BXMLNode two : mynode.getChildren()) {//获取intent-filter下所有节点
                    mynode = (BTagNode) two;
                    if (mynode.getName(block).equals("action")) {//如果是action节点
                        if (mynode.getAttrStringForKey(block, "name").equals("android.intent.action.MAIN"))
                            btag0 = true;
                    }
                    if (mynode.getName(block).equals("category")) {
                        if (mynode.getAttrStringForKey(block, "name").equals("android.intent.category.LAUNCHER")) {
                            btag1 = true;
                        }
                    }
                    if (btag0 && btag1) {
                        main = (BTagNode) one;
                        break a;
                    }
                }
            }
        }
        if (main == null) {
            throw new XmlPullParserException("AndroidManifest.xml exception (Not Found main activity )!");
        }
        main.setAttribute(block,"label",getAppName());
        xml.build(new FileOutputStream(twofile));
        xml.release();
        return true;
    }

    private void buildXml(AXMLDoc doc) {
        ApplicationInfoEditor applicationInfoEditor = new ApplicationInfoEditor(doc);
        applicationInfoEditor.setEditorInfo(new ApplicationInfoEditor.EditorInfo(getAppName(), false));
        applicationInfoEditor.commit();

        PackageInfoEditor packageInfoEditor = new PackageInfoEditor(doc);
        packageInfoEditor.setEditorInfo(new PackageInfoEditor.EditorInfo(getVersionCode(), getVersionName(), getPackagename()));
        packageInfoEditor.commit();


        PermissionEditor permissionEditor = new PermissionEditor(doc);
        PermissionEditor.EditorInfo permissioninfo = new PermissionEditor.EditorInfo();
        for (String p:getPermission()){
            if (p.contains(".")){
                permissioninfo.with(new PermissionEditor.PermissionOpera(p).add());
            }else{
                permissioninfo.with(new PermissionEditor.PermissionOpera("android.permission."+p).add());
            }
        }
        permissionEditor.setEditorInfo(permissioninfo );
        permissionEditor.commit();
//
//        MetaDataEditor metaDataEditor = new MetaDataEditor(doc);
//        metaDataEditor.setEditorInfo(new MetaDataEditor.EditorInfo("UMENG_CHANNEL", "apkeditor"));
//        metaDataEditor.commit();

    }

    public static File getWorkDir() {
        return new File(WORK_DIR);
    }

}
