package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lujinfei.deerdwmap.R;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.bean.Path;

import java.io.File;
import java.util.List;

/**
 * Created by lujinfei on 2016/4/5.
 */
public class GbxListAdapter extends CommonAdapter<File> {

    private List<File> dataList;
    private int selectPosition;

    public GbxListAdapter(Context context, List<File> mDatas) {
        super(context, mDatas);
        dataList = mDatas;
    }

    public void setData(List<File> mDatas) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_path, null);
            viewItem.ivHead = (ImageView)convertView.findViewById(R.id.iv_head);
            viewItem.ivHead.setVisibility(View.VISIBLE);
            viewItem.tvAccount = (TextView)convertView.findViewById(R.id.tv_logincn);
            viewItem.tvNote = (TextView)convertView.findViewById(R.id.tv_unbind);
            viewItem.tvNote.setVisibility(View.GONE);
            convertView.setTag(viewItem);
        } else {
            viewItem = (ViewItem)convertView.getTag();
        }

        File bean = dataList.get(position);
        viewItem.tvAccount.setText(bean.getName());
//        Log.i("sddd", bean.getTable().getName() + bean.getTable().isCurrent_sync());

        return convertView;
    }

    public static class ViewItem {
        public ImageView ivHead;
        public TextView tvAccount;
        public TextView tvNote;
    }
}
