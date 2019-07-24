package com.zwx.scan.app.feature.countanalysis.staffreward;

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
public class StaffModule {

    private  StaffContract.View view;

    public StaffModule(StaffContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public StaffContract.Presenter providePresenter(){
        return new StaffPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public StaffContract.View provideStaffContractView(){
        return view;
    }
}
