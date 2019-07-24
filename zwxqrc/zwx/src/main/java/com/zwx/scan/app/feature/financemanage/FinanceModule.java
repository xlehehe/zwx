package com.zwx.scan.app.feature.financemanage;

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
public class FinanceModule {

    private  FinanceContract.View view;

    public FinanceModule(FinanceContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public FinanceContract.Presenter providePresenter(){
        return new FinancePresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public FinanceContract.View provideFinanceContractView(){
        return view;
    }
}
