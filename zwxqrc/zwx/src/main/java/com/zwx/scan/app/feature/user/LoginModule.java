package com.zwx.scan.app.feature.user;


import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilogn
 *         2018/8/15
 */
@Module
public class LoginModule {

    private  LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public LoginContract.Presenter providePresenter(){
        return new LoginPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public LoginContract.View provideLoginContractView(){
        return view;
    }
}
