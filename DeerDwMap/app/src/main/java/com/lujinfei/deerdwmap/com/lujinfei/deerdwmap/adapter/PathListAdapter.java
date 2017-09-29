package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lujinfei.deerdwmap.R;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean.Path;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class PathListAdapter extends CommonAdapter<Path> {

    private List<Path> dataList;
    private int selectPosition;

    public PathListAdapter(Context context, List<Path> mDatas) {
        super(context, mDatas);
        dataList = mDatas;
    }

    public void setData(List<Path> mDatas) {
        dataList = mDatas;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewItem viewItem;
        if (convertView == null) {
            viewItem = new ViewItem();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.path_item_path, null);
            viewItem.ivSelected = (ImageView)convertView.findViewById(R.id.iv_selected);
            viewItem.ivSelected.setVisibility(View.VISIBLE);
            viewItem.tvAccount = (TextView)convertView.findViewById(R.id.tv_logincn);
            viewItem.tvNote = (TextView)convertView.findViewById(R.id.tv_unbind);
            viewItem.tvNote.setVisibility(View.GONE);

            viewItem.tvMiles = (TextView)convertView.findViewById(R.id.tv_miles);
            viewItem.tvLocation = (TextView)convertView.findViewById(R.id.tv_location);
            convertView.setTag(viewItem);
        } else {
            viewItem = (ViewItem)convertView.getTag();
        }

        Path bean = dataList.get(position);
        viewItem.tvAccount.setText(bean.getTable().getName());

        String distanceMales = bean.getTable().getDistance_display();
        BigDecimal dis = new BigDecimal(distanceMales);
        String displayDis = "";

        switch(bean.getTable().getDistanceUnit()) {
            case 1:
                displayDis = dis.setScale(2, RoundingMode.HALF_UP).toPlainString() + " mi";
                break;
            case 2:
                dis = dis.multiply(new BigDecimal("1.61"));
                displayDis = dis.setScale(2, RoundingMode.HALF_UP).toPlainString() + " km";
                break;
        }

        viewItem.tvMiles.setText(displayDis);
        viewItem.tvLocation.setText(bean.getTable().getCity());
//        Log.i("sddd", bean.getTable().getName() + bean.getTable().isCurrent_sync());
        if(bean.getTable().isCurrent_sync()) {
            viewItem.ivSelected.setVisibility(View.VISIBLE);
            viewItem.ivSelected.setImageResource(R.drawable.star_full);
        }else {
            viewItem.ivSelected.setVisibility(View.INVISIBLE);
            viewItem.ivSelected.setImageResource(R.drawable.star_empty);
        }

        return convertView;
    }

    public static class ViewItem {
        public ImageView ivSelected;
        public TextView tvAccount;
        public TextView tvNote;

        public TextView tvMiles;
        public TextView tvLocation;
    }
}
