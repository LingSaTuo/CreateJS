package com.lingsatuo.bmob;


import com.lingsatuo.callbackapi.Function;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 15176 on 2017/6/29.
 */

public final class DataInfo<T extends ObjectData>  {
    public A createNewOne(T objectData){
        return new A(objectData);
    }
    public class A{
        private final T objectData;
        public A(T objectData){
            this.objectData = objectData;
        }

        public void upFile(Function callback){
            objectData.getFile().upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e!=null){
                        callback.T(new String[]{"save","   ","Failed   :"+e.getErrorCode()+"   "+e.getMessage()});
                    }else{
                        save(callback);
                    }
                }
            });
        }

        public void save(Function callback){
            objectData.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (callback!=null){
                        if (e!=null){
                            callback.T(new String[]{"save","   ",e.getErrorCode()+"   "+e.getMessage()});
                        }else{
                            callback.T(new String[]{"save",s,""});
                        }
                    }
                }
            });
        }

        public void delete(Function callback){
            objectData.delete(new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (callback!=null){
                        if (e!=null){
                            callback.T(new String[]{"delete","   ",e.getErrorCode()+"   "+ e.getMessage()});
                        }else{
                            callback.T(new String[]{"delete","",""});
                        }
                    }
                }
            });
        }

        public void update(String id ,Function callback){
            objectData.update(id, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (callback!=null){
                        if (e!=null){
                            callback.T(new String[]{"update","  ",e.getErrorCode()+""+e.getMessage()});
                        }else{
                            callback.T(new String[]{"update","",""});
                        }
                    }
                }

            });
        }
    }
}
