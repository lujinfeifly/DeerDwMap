package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lujinfei.deerdwmap.R;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.view.DrawHookView;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.view.IISharkView;

/**
 * Created by lujinfei on 2016/4/14.
 */
public class ShowEndDialog extends Dialog implements DialogListener{
    private Context context;
    private IISharkView sharkView;
    private TextView tipTextView;

    public ShowEndDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ShowEndDialog(Context context, int themeResId, String msg) {
        super(context, themeResId);
        this.context = context;
        initView(msg);
    }

    protected ShowEndDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initView(String msg) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_layout_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

        sharkView = (IISharkView) v.findViewById(R.id.imgok);
        sharkView.setListener(this);

        tipTextView.setText(msg); // 设置加载信息

        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }

    public void setFinished() {
        sharkView.setFinished();
        tipTextView.setText(R.string.tip_success);
    }

    public void setError() {
        sharkView.setError();
        tipTextView.setText(R.string.tip_fail);
    }

    @Override
    public void onColse() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);

    }
}
