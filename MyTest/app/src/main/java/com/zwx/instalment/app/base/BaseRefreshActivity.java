package com.zwx.instalment.app.base;


import com.zwx.instalment.app.base.mvp.BaseModel;
import com.zwx.instalment.app.base.mvp.BaseRefreshPresenter;
import com.zwx.instalment.app.base.mvp.BaseRefreshView;
import com.zwx.instalment.app.widget.refresh.BaseRefreshLayout;
import com.zwx.instalment.app.widget.refresh.DaisyRefreshLayout;

/**
 * Description: <下拉刷新、上拉加载更多的Activity><br>
 * Author:      mxdl<br>
 * Date:        2018/2/26<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshActivity<M extends BaseModel, V extends BaseRefreshView<T>, P extends BaseRefreshPresenter<M, V, T>, T> extends BaseMvpActivity<M, V, P> implements BaseRefreshView<T> {
    protected DaisyRefreshLayout mRefreshLayout;

  /*  @Override
    protected void initCommonView() {
        super.initCommonView();
        initRefreshView();
    }*/

    public void initRefreshView() {
        mRefreshLayout = findViewById(onBindRreshLayout());
        // 下拉刷新
        mRefreshLayout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshEvent();
            }
        });
        // 上拉加载
        mRefreshLayout.setOnLoadMoreListener(new BaseRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onLoadMoreEvent();
            }
        });
        // 自动加载
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
        mRefreshLayout.autoRefresh();
    }
}
