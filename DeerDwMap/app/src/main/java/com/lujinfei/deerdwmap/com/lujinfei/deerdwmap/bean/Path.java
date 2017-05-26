package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class Path {
    private PathMsg table;
    private boolean modifiable;

    public Path(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        modifiable = json.getBoolean("modifiable");

        table = new PathMsg(json.getString("table"));
    }

    public PathMsg getTable() {
        return table;
    }

    public void setTable(PathMsg table) {
        this.table = table;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
}
