package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.components.DialogListener;

/**
 * Created by lujinfei on 2016/4/14.
 */
public class IISharkView extends View {
    protected DialogListener dialogListener;

    public IISharkView(Context context) {
        super(context);
    }

    public IISharkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IISharkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(DialogListener l) {
        dialogListener = l;
    }

    public void setFinished() {

    }

    public void setError() {

    }

}
