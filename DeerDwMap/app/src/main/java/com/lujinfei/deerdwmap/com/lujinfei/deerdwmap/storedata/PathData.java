package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lujinfei on 2016/4/6.
 */
public class PathData {

    public static void setName(Context context, String path){
        SharedPreferences sp =context.getSharedPreferences("PATH", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PATH", path);
        editor.commit();
    }

    public static String getName(Context context) {
        SharedPreferences sp =context.getSharedPreferences("PATH", Activity.MODE_PRIVATE);
        return sp.getString("PATH", null);
    }
}
