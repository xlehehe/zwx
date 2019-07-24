package com.zwx.scan.app.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zwx.library.utils.BarUtils;
import com.zwx.scan.app.R;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2018/11/28
 * desc   :
 * version: 1.0
 **/
public abstract class BaseFragmentActivity<T> extends BaseStatusBarFragmentActivity {

    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected T presenter;//如果当前页面逻辑简单, Presenter 可以为 null



    protected  int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true,getResources().getColor(R.color.white));
        setContentView(getLayoutId());
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
    }

    protected abstract int getLayoutId();
    /**
     * desc    Dagger 注入
     */
//    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initData();



}
