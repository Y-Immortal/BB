package edu.wschina.b66.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    private static final String NAME = "shared-preference";

    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();;
    }

}
