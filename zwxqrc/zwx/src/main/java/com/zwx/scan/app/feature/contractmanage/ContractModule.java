package com.zwx.scan.app.feature.contractmanage;


import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilogn
 *         2018/8/15
 */
@Module
public class ContractModule {

    private  ContractContract.View view;

    public ContractModule(ContractContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public ContractContract.Presenter providePresenter(){
        return new ContractPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public ContractContract.View provideLoginContractView(){
        return view;
    }
}
