package com.zwx.instalment.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zwx.instalment.app.base.mvp.BaseMFragment;
import com.zwx.instalment.app.base.mvp.BaseModel;
import com.zwx.instalment.app.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Description: <BaseMvpFragment><br>
 * Author:      lizhilong<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvpFragment<M extends BaseModel,V,P extends BasePresenter<M,V>> extends BaseMFragment {
   @Inject
    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectPresenter();
        if(mPresenter != null){
            mPresenter.injectLifecycle(mActivity);
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detach();
        }
        super.onDestroy();
    }
    public abstract void injectPresenter();
}
