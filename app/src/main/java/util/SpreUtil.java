package util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by acer on 2017/7/20.
 */

public class SpreUtil {
    public static boolean getBoolean(Context context,String key,Boolean defValue){
        SharedPreferences sp = context.getSharedPreferences("cinfig", Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    public static void putBoolean(Context context,String key,Boolean value){
        SharedPreferences sp = context.getSharedPreferences("cinfig", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).apply();
    }
    public static String getBoolean(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences("cinfig", Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("cinfig", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }
}
