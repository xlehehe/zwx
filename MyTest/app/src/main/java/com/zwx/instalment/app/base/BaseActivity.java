package com.zwx.instalment.app.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.mvp.BaseView;
import com.zwx.instalment.app.widget.tablayout.utils.BarUtils;


import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * @author lizhilong
 * desc 实现基础类
 * time 2018/8/15
 * */
public abstract class BaseActivity<T> extends BaseStatusBarActivity  {
    protected final String TAG = this.getClass().getSimpleName();



    protected  int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
//        setViewStatusBar();
        setImmersiveStatusBar(false,getResources().getColor(R.color.status_color));
        setContentView(getLayoutId());
        EventBus.getDefault().register(this);
        //绑定到butterknife
        ButterKnife.bind(this);

        initView();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * //去标题栏和状态栏
     * */
    protected void setViewStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View mStatusBarTintView = new View(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight());
            mStatusBarTintView.setLayoutParams(params);
            mStatusBarTintView.setBackgroundColor(getResources().getColor(R.color.white));
            mStatusBarTintView.setVisibility(View.GONE);
//            mStatusBarTintView.setFitsSystemWindows(true);

            decorView.addView(mStatusBarTintView);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        if (presenter != null)
//            presenter.unSubscribe();//释放资源
//        this.presenter = null;
    }

    protected abstract int getLayoutId();
    /**
     * desc    Dagger 注入
     */
//    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initData();

}
