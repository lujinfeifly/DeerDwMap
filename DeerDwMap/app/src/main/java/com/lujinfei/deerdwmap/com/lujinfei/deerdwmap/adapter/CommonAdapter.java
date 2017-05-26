package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.listener.CYOnClickListener;

import java.util.List;

/**
 * Created by lujinfei on 2016/4/5.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    public CYOnClickListener listener;

    public CommonAdapter(Context context, List<T> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public void setImage(Bitmap url, int nDefaultId, ImageView iv) {
        iv.setImageBitmap(url);
    }


    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    //添加事件点击
    public void setCYClickListener(CYOnClickListener listener) {
        this.listener = listener;
    }

    protected class  ListenerClick implements View.OnClickListener{
        private int position;
        public ListenerClick(int pos){
            position = pos;
        }

        @Override
        public void onClick(View view) {
            listener.CYClickListener(view.getId(), position);
        }

    }
}
