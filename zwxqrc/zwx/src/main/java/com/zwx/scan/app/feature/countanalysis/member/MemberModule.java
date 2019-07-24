package com.zwx.scan.app.feature.countanalysis.member;

import com.zwx.scan.app.feature.home.HomeContract;
import com.zwx.scan.app.feature.home.HomePresenter;
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
public class MemberModule {

    private  MemberContract.View view;

    public MemberModule(MemberContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public MemberContract.Presenter providePresenter(){
        return new MemberPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public MemberContract.View provideHomeContractView(){
        return view;
    }
}
