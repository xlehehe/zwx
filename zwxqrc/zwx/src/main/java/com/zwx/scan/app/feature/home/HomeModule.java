package com.zwx.scan.app.feature.home;

import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 **/
@Module
public class HomeModule {

    private  HomeContract.View view;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public HomeContract.Presenter providePresenter(){
        return new HomePresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public HomeContract.View provideHomeContractView(){
        return view;
    }
}
