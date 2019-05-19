package com.lujinfei.deerdwmap;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;

import java.util.LinkedList;
import java.util.List;

public class AMapActivitiy extends BaseActivity {

    private MapView mapView = null;
    private AMap aMap = null;

    private List<Marker> markers = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取消标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_amap);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);                // 此方法必须重写
        aMap = mapView.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);



        LatLng latLng = new LatLng(39.906901,116.397972);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));

        marker.setDraggable(true);



//        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
//        long duration = 1000L;
//        animation.setDuration(duration);
//        animation.setInterpolator(new LinearInterpolator());
//
//        marker.setAnimation(animation);
//        marker.startAnimation();

        AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {

            // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub

            }

            // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub

            }

            // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub

            }
        };
        // 绑定marker拖拽事件
        aMap.setOnMarkerDragListener(markerDragListener);

        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("11111", "onMarkerClick: sfvfvfvfvfvffvf");
                return true;
            }


        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);


        AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                arg0.setTitle("infowindow clicked");
            }
        };

        //绑定信息窗点击事件
        aMap.setOnInfoWindowClickListener(listener);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        markers = null;
        mapView.onDestroy();
        super.onDestroy();
    }
}
