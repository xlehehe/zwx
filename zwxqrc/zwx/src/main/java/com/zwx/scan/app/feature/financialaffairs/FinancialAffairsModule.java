package com.zwx.scan.app.feature.financialaffairs;

import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  财务中心 模块
 * version: 1.0
 **/
@Module
public class FinancialAffairsModule {
    private  FinancialAffairsContract.View view;

    public FinancialAffairsModule(FinancialAffairsContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public FinancialAffairsContract.Presenter providePresenter(){
        return new FinancialAffairsPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public FinancialAffairsContract.View provideView(){
        return view;
    }

}
