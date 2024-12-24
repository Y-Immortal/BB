package edu.wschina.b66.API;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import edu.wschina.b66.Util.HttpUtil;

public class LoginApi {
    public static void login(Context context, String username, String password, HttpUtil.Callback<ResponseMo> callback){
        Map<String,Object> formDta = new HashMap<>();
        formDta.put("username",username);
        formDta.put("password",password);
        HttpUtil.postObject(context,"/auth/login",formDta, false, ResponseMo.class,callback);
    }
    public static class ResponseMo {
        public int code;
        public String msg;
        public String data;
    }

}
