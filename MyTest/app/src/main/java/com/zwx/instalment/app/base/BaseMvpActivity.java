package com.zwx.instalment.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zwx.instalment.app.base.mvp.BaseMActivity;
import com.zwx.instalment.app.base.mvp.BaseModel;
import com.zwx.instalment.app.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Description: <BaseMvpActivity><br>
 * Author:      lizhilong<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvpActivity<M extends BaseModel,V,P extends BasePresenter<M,V>> extends BaseMActivity {
    @Inject
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectPresenter();
        if(mPresenter != null){
            mPresenter.injectLifecycle(this);
        }
        super.onCreate(savedInstanceState);

    }
    public abstract void injectPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detach();
        }
    }
}
