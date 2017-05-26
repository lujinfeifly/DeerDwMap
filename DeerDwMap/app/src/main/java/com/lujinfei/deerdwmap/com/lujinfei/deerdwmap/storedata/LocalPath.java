package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.storedata;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujinfei on 2016/4/8.
 */
public class LocalPath {

    public static List<File> getSixFootGbx(Context context) {
        return getGbx(context, "/6foots/export/");
    }

    /**
     * 获取外部存储的文件
     * @param context
     * @param localurl 如 /6foots/export
     */
    public static List<File> getGbx(Context context, String localurl) {
        String externalUrl = Environment.getExternalStorageDirectory().getAbsolutePath();
        String wholeUrl = externalUrl + localurl;
        File file = new File(wholeUrl);
        return getAllFiles(file, "gpx");
    }


    private static List<File> getAllFiles(File root, String tail){
        List<File> ret = new ArrayList<File>();
        File files[] = root.listFiles();
//        Log.i("file", "eeeeeeeeeeeeeeee" + root.list()[0]);
        if(files != null){
            for (File f : files){
                if(f.isFile() && f.getName().endsWith("."+tail)) {
                    Log.i("file", f.getName());
                    ret.add(f);
                }
            }
        }
        return ret;
    }
}
