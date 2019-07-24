package com.zwx.scan.app.feature.member;


import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilogn
 *         2018/8/15
 */
@Module
public class MemberManageModule {

    private  MemberManageContract.View view;

    public MemberManageModule(MemberManageContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public MemberManageContract.Presenter providePresenter(){
        return new MemberManagePresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public MemberManageContract.View provideMemberManageContractView(){
        return view;
    }
}
