package com.lingsatuo.script.require;


import com.lingsatuo.openapi.Files;

import org.mozilla.javascript.commonjs.module.provider.ModuleSource;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 15176 on 2017/9/24.
 */

public class ModuleSourceProvider extends UrlModuleSourceProvider {
    List<String> items;
    List<String> item2;
    private List<URI> list;
public ModuleSourceProvider(List<URI> list){
    super(list,null);
}
private List<String> getItems(String path){
    return Arrays.asList(
            new Files().listDir(
                    path,
                    name ->
                        name.endsWith(".js") && new File(path + "/" + name).isFile()
            ));
}
    @Override
    protected ModuleSource loadFromPrivilegedLocations(String moduleId, Object validator) throws IOException, URISyntaxException {
       String mMod = moduleId;
        if (!mMod.endsWith(".js")){
            mMod+=".js";
        }
        return super.loadFromPrivilegedLocations(mMod, validator);
    }
    private ModuleSource getModuleSource(String path,String name,Object validator)throws IOException, URISyntaxException{
        return new ModuleSource(new InputStreamReader(
                new FileInputStream(path+"/"+name)),null,URI.create(name),URI.create(path),validator);

    }
}
