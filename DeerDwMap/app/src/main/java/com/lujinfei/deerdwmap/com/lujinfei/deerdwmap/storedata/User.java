package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lujinfei on 2016/4/6.
 */
public class User {

    private static String ssd = "i7y2378y7g7y7&%^&$$YGYGDe4";

    public static void setName(Context context, String name, String pwd){
        SharedPreferences sp =context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER", name);
        editor.putString("PWD", pwd /*MString.parseByte2HexStr(MEncrypt.AESencrypt(pwd, ssd))*/);
        editor.commit();
    }

    public static void setSession(Context context, String session) {
        SharedPreferences sp =context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("SESSION", session);
        editor.commit();
    }

    public static String getSession(Context context) {
        SharedPreferences sp =context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getString("SESSION", null);
    }

    public static String getName(Context context) {
        SharedPreferences sp =context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getString("USER", null);
    }

    public static String getPwd(Context context) {
        SharedPreferences sp =context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        String asc = sp.getString("PWD", null);
//        if(asc !=null) {
//            byte[] bytes = MString.parseHexStr2Byte(asc);
//            return MEncrypt.AESdecrypt(bytes, ssd);
//        }
        return asc;
    }
    public static Integer getSwitchDistance(Context context) {
        SharedPreferences sp =context.getSharedPreferences("PROPERTIRS", Activity.MODE_PRIVATE);
        String ret = sp.getString("DISUNIS", null);
        return "km".equals(ret)?2:1;
    }

    public static void setSwitchDistance(Context context, int value) {
        SharedPreferences sp =context.getSharedPreferences("PROPERTIRS", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String set = "";
        switch (value) {
            case 1:
                set = "males";
                break;
            case 2:
                set = "km";
                break;
        }
        editor.putString("DISUNIS", set);
        editor.commit();
    }
}
