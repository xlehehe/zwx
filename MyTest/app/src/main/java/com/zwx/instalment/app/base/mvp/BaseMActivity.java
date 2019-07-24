package com.zwx.instalment.app.base.mvp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zwx.instalment.app.base.BaseStatusBarActivity;
import com.zwx.instalment.app.widget.NetErrorView;
import com.zwx.instalment.app.widget.NoDataView;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: <BaseActivity><br>
 * Author:      mxdl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMActivity extends RxAppCompatActivity implements BaseView {
    protected static final String TAG = BaseMActivity.class.getSimpleName();
    protected TextView mTxtTitle;
    protected Toolbar mToolbar;
    protected NetErrorView mNetErrorView;
    protected NoDataView mNoDataView;
//    protected LoadingInitView mLoadingInitView;
//    protected LoadingTransView mLoadingTransView;
/*    private ViewStub mViewStubToolbar;
    private ViewStub mViewStubContent;
    private ViewStub mViewStubInitLoading;
    private ViewStub mViewStubTransLoading;
    private ViewStub mViewStubNoData;
    private ViewStub mViewStubError;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
        EventBus.getDefault().register(this);
//        ActivityManager.getInstance().addActivity(this);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        ActivityManager.getInstance().finishActivity(this);
    }




    @Override
    public void finishActivity() {
        finish();
    }



    public void showInitLoadView() {
        showInitLoadView(true);
    }

    public void hideInitLoadView() {
        showInitLoadView(false);
    }

    @Override
    public void showTransLoadingView() {
        showTransLoadingView(true);
    }

    @Override
    public void hideTransLoadingView() {
        showTransLoadingView(false);
    }

    public void showNoDataView() {
        showNoDataView(true);
    }

    public void showNoDataView(int resid) {
        showNoDataView(true, resid);
    }

    public void hideNoDataView() {
        showNoDataView(false);
    }

    public void hideNetWorkErrView() {
        showNetWorkErrView(false);
    }

    public void showNetWorkErrView() {
        showNetWorkErrView(true);
    }

    private void showInitLoadView(boolean show) {
        /*if (mLoadingInitView == null) {
            View view = mViewStubInitLoading.inflate();
            mLoadingInitView = view.findViewById(R.id.view_init_loading);
        }
        mLoadingInitView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingInitView.loading(show);*/
    }


    private void showNetWorkErrView(boolean show) {
        /*if (mNetErrorView == null) {
            View view = mViewStubError.inflate();
            mNetErrorView = view.findViewById(R.id.view_net_error);
            mNetErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.checkNetToast()) {
                        return;
                    }
                    hideNetWorkErrView();
                    initData();
                }
            });
        }
        mNetErrorView.setVisibility(show ? View.VISIBLE : View.GONE);*/
    }


    private void showNoDataView(boolean show) {
       /* if (mNoDataView == null) {
            View view = mViewStubNoData.inflate();
            mNoDataView = view.findViewById(R.id.view_no_data);
        }
        mNoDataView.setVisibility(show ? View.VISIBLE : View.GONE);*/
    }

    private void showNoDataView(boolean show, int resid) {
        showNoDataView(show);
        if (show) {
            mNoDataView.setNoDataView(resid);
        }
    }

    private void showTransLoadingView(boolean show) {
       /* if (mLoadingTransView == null) {
            View view = mViewStubTransLoading.inflate();
            mLoadingTransView = view.findViewById(R.id.view_trans_loading);
        }
        mLoadingTransView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingTransView.loading(show);*/
    }

 /*   @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(BaseActivityEvent<T> event) {
    }*/

    @Override
    public Context getContext() {
        return this;
    }
}
