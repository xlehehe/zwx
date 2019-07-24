package com.zwx.scan.app.feature.couponmanage;

import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
@Module
public class GiveCouponModule {

    private  GiveCouponContract.View view;

    public GiveCouponModule(GiveCouponContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public GiveCouponContract.Presenter providePresenter(){
        return new GiveCouponPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public GiveCouponContract.View provideGiveCouponContractView(){
        return view;
    }
}
