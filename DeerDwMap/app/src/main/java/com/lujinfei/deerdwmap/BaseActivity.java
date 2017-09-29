package com.lujinfei.deerdwmap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.components.ShowEndDialog;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.httpfunc.HttpFunc;
import com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.view.DrawHookView;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by lujinfei on 2016/4/12.
 */
public class BaseActivity  extends AppCompatActivity {

    public HttpFunc httpFunc;
    private ShowEndDialog loading;
    private DrawHookView imgvOk;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpFunc = ((DeerApplication)getApplication()).getHttpFunc();
    }

    protected void showOperDialog(int showDefault, String title, String Message,
                                String nagativeName, String positiveName,
                                DialogInterface.OnClickListener negativeListener,
                                DialogInterface.OnClickListener positiveListener) {
        int type = AlertDialog.BUTTON_POSITIVE;
        if(showDefault == 0) {
            type = AlertDialog.BUTTON_NEGATIVE;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this,type);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setNegativeButton(nagativeName,negativeListener);
        builder.setPositiveButton(positiveName,positiveListener);
        builder.show();
    }

    public void showLoading(String msg) {
        cancleLoading();
        loading = createLoadingDialog(this, msg);
        loading.show();
    }

    public void cancleLoading() {
        if (loading != null) {
            loading.setFinished();
        }
    }

    public void cancelLoadingWithError() {
        if (loading != null) {
            loading.setError();
        }
    }

    protected ShowEndDialog createLoadingDialog(Context context, String msg) {

        ShowEndDialog loadingDialog = new ShowEndDialog(context, R.style.loading_dialog, msg);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消

        return loadingDialog;
    }
}
