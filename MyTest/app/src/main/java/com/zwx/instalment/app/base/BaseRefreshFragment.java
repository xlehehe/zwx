package com.zwx.instalment.app.base;

import android.view.View;

import com.zwx.instalment.app.base.mvp.BaseModel;
import com.zwx.instalment.app.base.mvp.BaseRefreshPresenter;
import com.zwx.instalment.app.base.mvp.BaseRefreshView;
import com.zwx.instalment.app.utils.LogUtils;
import com.zwx.instalment.app.widget.refresh.BaseRefreshLayout;
import com.zwx.instalment.app.widget.refresh.DaisyRefreshLayout;

/**
 * Description: <下拉刷新、上拉加载更多的Fragment><br>
 * Author:      mxdl<br>
 * Date:        2018/2/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshFragment<M extends BaseModel,V extends BaseRefreshView<T>,P extends BaseRefreshPresenter<M,V,T>,T> extends BaseMvpFragment<M,V,P> implements BaseRefreshView<T> {
    protected DaisyRefreshLayout mRefreshLayout;

    @Override
    public void initCommonView(View view) {
        super.initCommonView(view);
        initRefreshView(view);
    }

    public void initRefreshView(View view) {
        mRefreshLayout = view.findViewById(onBindRreshLayout());
        mRefreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshEvent();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onLoadMoreEvent();
            }
        });
        mRefreshLayout.setOnAutoLoadListener(new BaseRefreshLayout.OnAutoLoadListener() {
            @Override
            public void onAutoLoad() {
                onAutoLoadEvent();
            }
        });
    }
    protected abstract int onBindRreshLayout();
    @Override
    public void enableRefresh(boolean b) {
        mRefreshLayout.setEnableRefresh(b);
    }

    @Override
    public void enableLoadMore(boolean b) {
        mRefreshLayout.setEnableLoadMore(b);
    }

    @Override
    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopLoadMore() {
        mRefreshLayout.setLoadMore(false);
    }

    @Override
    public void autoLoadData() {
        LogUtils.v("MYTAG","autoLoadData start...");
        if(mRefreshLayout != null){
            LogUtils.v("MYTAG","autoLoadData1 start...");
            mRefreshLayout.autoRefresh();
        }
    }
}
