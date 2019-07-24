package com.zwx.scan.app.feature.verification;

import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilong
 * @version 1.0
 * @desc
 * @time 2018/10/16
 */
@Module
public class VerificationModule {

    private  VerificationContract.View view;

    public VerificationModule(VerificationContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public VerificationContract.Presenter providePresenter(){
        return new VerificationPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public VerificationContract.View provideLoginContractView(){
        return view;
    }
}
