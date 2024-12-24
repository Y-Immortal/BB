package edu.wschina.b66.API;

import android.content.Context;

import java.util.List;

import edu.wschina.b66.Util.HttpUtil;

public class PictureApi {

    public static void picture(Context context, HttpUtil.Callback<ResponseMo> callback){
        HttpUtil.get(context,"/picture/all?pageSize=30&pageNumber=1",null, true,ResponseMo.class,callback);
    }
    public class ResponseMo{
        public int code;
        public String msg;
        public Data data;
    }

    public static class ResultPicture {

        public int id;
        public String url;

    }

    public class Data{
        public List<ResultPicture> list;
        public int total;
    }

}
