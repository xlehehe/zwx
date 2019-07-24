package com.zwx.scan.app.feature.shop;

import com.zwx.scan.app.feature.ptmanage.PtContract;
import com.zwx.scan.app.feature.ptmanage.PtPresenter;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :  商城
 * version: 1.0
 **/
@Module
public class ShopModule {
    private  ShopContract.View view;

    public ShopModule(ShopContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public ShopContract.Presenter providePresenter(){
        return new ShopPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public ShopContract.View provideShopContractView(){
        return view;
    }

}
