package com.zwx.scan.app.feature.cateringinfomanage;

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
public class CateringInfoModule {

    private  CateringInfoContract.View view;

    public CateringInfoModule(CateringInfoContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public CateringInfoContract.Presenter providePresenter(){
        return new CateringInfoPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public CateringInfoContract.View provideGiveCouponContractView(){
        return view;
    }
}
