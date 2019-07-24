package com.zwx.scan.app.feature.ptmanage;

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
public class PtModule {

    private  PtContract.View view;

    public PtModule(PtContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public PtContract.Presenter providePresenter(){
        return new PtPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public PtContract.View providePtContractView(){
        return view;
    }
}
