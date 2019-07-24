package com.zwx.scan.app.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zwx.library.utils.BarUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.feature.AppContext;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * @author lizhilong
 * desc 实现基础类
 * time 2018/8/15
 * */
public abstract class BaseActivity<T> extends BaseStatusBarActivity{
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected T presenter;//如果当前页面逻辑简单, Presenter 可以为 null



    protected  int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setImmersiveStatusBar(true,getResources().getColor(R.color.white));
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
        //全屏设置
       /* this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//            window.setStatusBarColor(Color.TRANSPARENT);

            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View mStatusBarTintView = new View(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight());
            mStatusBarTintView.setLayoutParams(params);
            mStatusBarTintView.setBackgroundColor(getResources().getColor(R.color.grayBlue));
            mStatusBarTintView.setVisibility(View.GONE);
            mStatusBarTintView.setFitsSystemWindows(true);
            decorView.addView(mStatusBarTintView);

        }*/
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
//        if (presenter != null)
//            presenter.unSubscribe();//释放资源
//        this.presenter = null;

//        AppContext.getRefWatcher().watch(this);
    }

    protected abstract int getLayoutId();
    /**
     * desc    Dagger 注入
     */
//    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initData();

}
