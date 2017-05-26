package com.lujinfei.deerdwmap;

import android.app.Application;

import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.httpfunc.HttpFunc;

import deer.milu.freejava.http.HttpRet;

/**
 * Created by lujinfei on 2016/4/7.
 */
public class DeerApplication extends Application {

    private HttpFunc httpFunc;

    @Override
    public void onCreate() {
        super.onCreate();
        httpFunc = new HttpFunc();
    }

    public HttpFunc getHttpFunc() {
        return httpFunc;
    }
}
