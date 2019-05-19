package com.lujinfei.deerdwmap;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class AMapActivitiy extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);

        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);                // 此方法必须重写
        AMap aMap = mapView.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
    }
}
