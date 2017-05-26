package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class PathMsg {
    private String city;
    private String country;
    private String created_at;
    private boolean current_sync;
    private double distance;
    private String favorite;
    private File file;
    private int id;
    private double latitude;
    private double longitude;
    private String name;
    private int remote_id;
    private String state;
    private String update_at;
    private int user_id;
    private String provider;
    private String provider_display;
    private String distance_display;
    private boolean shareable;

    public PathMsg(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        city = json.getString("city");
        country = json.getString("country");
        created_at = json.getString("created_at");
        try {
            current_sync = json.getBoolean("current_sync");
        } catch (JSONException ex) {
            current_sync = false;
        }

//        distance = json.getDouble("distance");
//        favorite = json.getString("favorite");
        id = json.getInt("id");
        latitude = json.getDouble("latitude");
        longitude = json.getDouble("longitude");
        name = json.getString("name");
        remote_id = json.getInt("remote_id");
        state = json.getString("state");
        update_at = json.getString("updated_at");
        user_id = json.getInt("user_id");
        provider = json.getString("provider");
        provider_display = json.getString("provider_display");
        distance_display = json.getString("distance_display");
//        shareable = json.getBoolean("shareable");
        try {
            file = new File(json.getString("file"));
        } catch(Exception ex) {

        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isCurrent_sync() {
        return current_sync;
    }

    public void setCurrent_sync(boolean current_sync) {
        this.current_sync = current_sync;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemote_id() {
        return remote_id;
    }

    public void setRemote_id(int remote_id) {
        this.remote_id = remote_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider_display() {
        return provider_display;
    }

    public void setProvider_display(String provider_display) {
        this.provider_display = provider_display;
    }

    public String getDistance_display() {
        return distance_display;
    }

    public void setDistance_display(String distance_display) {
        this.distance_display = distance_display;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }
}
