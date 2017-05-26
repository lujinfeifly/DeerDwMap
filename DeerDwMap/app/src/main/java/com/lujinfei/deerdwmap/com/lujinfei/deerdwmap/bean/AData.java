package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class AData {
    private boolean no_more;
    private int count;
    private List<Path> rows;

    public AData() {
        no_more = true;
        count = 0;
        rows = new ArrayList<Path>();
    }

    public AData(String data) throws JSONException {

        JSONObject json = new JSONObject(data);
        no_more = json.getBoolean("no_more");
        count = json.getInt("count");
        rows = new ArrayList<Path>();


        JSONArray jsonArray = json.optJSONArray("rows");
        if (jsonArray != null && jsonArray.length() > 0) {
            for(int i=0;i<jsonArray.length();i++) {
                try {
                    Path path = new Path(jsonArray.get(i).toString());
                    rows.add(path);
                }catch(JSONException ex) {

                }
            }
        }
    }

    public boolean isNo_more() {
        return no_more;
    }

    public void setNo_more(boolean no_more) {
        this.no_more = no_more;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Path> getRows() {
        return rows;
    }

    public void setRows(List<Path> rows) {
        this.rows = rows;
    }
}
