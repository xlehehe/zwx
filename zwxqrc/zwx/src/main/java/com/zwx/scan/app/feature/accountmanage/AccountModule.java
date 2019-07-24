package com.zwx.scan.app.feature.accountmanage;


import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilogn
 *         2018/8/15
 */
@Module
public class AccountModule {

    private  AccountContract.View view;

    public AccountModule(AccountContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public AccountContract.Presenter providePresenter(){
        return new AccountPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public AccountContract.View provideLoginContractView(){
        return view;
    }
}
