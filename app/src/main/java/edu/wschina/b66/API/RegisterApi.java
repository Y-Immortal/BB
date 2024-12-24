package edu.wschina.b66.API;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.wschina.b66.Util.HttpUtil;

public class RegisterApi {

    public static void register(Context context, String email, String username, String password, HttpUtil.Callback<ResponseMo> callback){
        Map<String,Object> formDta = new HashMap<>();
        formDta.put("email",email);
        formDta.put("username",username);
        formDta.put("password",password);
        HttpUtil.postFormData(context,"/auth/register",formDta, false, ResponseMo.class,callback);
    }
    public static class ResponseMo{
        public int code;
        public String msg;
        public String token;
    }

}
