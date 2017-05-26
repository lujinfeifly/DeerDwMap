package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class File {
    private String url;

    public File(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        url = json.getString("url");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
