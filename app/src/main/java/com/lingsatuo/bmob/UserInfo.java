package com.lingsatuo.bmob;

import com.lingsatuo.error.CreateJSMD5NSA;
import com.lingsatuo.error.CreateJSMD5UEE;
import com.lingsatuo.utils.md5;

import cn.bmob.v3.BmobObject;


/**
 * Created by 15176 on 2017/6/29.
 */

public class UserInfo extends BmobObject{
    private String userid;
    private String userPassworld;
    public UserInfo(){}
    public UserInfo createId(String id){
        try {
            this.userid = md5.CreateMD5(id);
        } catch (CreateJSMD5NSA createJSMD5NSA) {
            this.userid = null;
        } catch (CreateJSMD5UEE createJSMD5UEE) {
            this.userid = null;
        }
        return this;
    }
    public UserInfo createPassWorld(String passworld){
        try {
            this.userPassworld = md5.CreateMD5(passworld);
        } catch (CreateJSMD5NSA createJSMD5NSA) {
            this.userPassworld = null;
        } catch (CreateJSMD5UEE createJSMD5UEE) {
            this.userPassworld = null;
        }
        return this;
    }
    public A getUserInfo() {
            return new A();
    }
    public final class A{
        public String getId(){
            return userid;
        }
        public String getPassworld(){
            return userPassworld;
        }
    }
}
