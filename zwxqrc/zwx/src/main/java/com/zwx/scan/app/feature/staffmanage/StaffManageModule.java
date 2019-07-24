package com.zwx.scan.app.feature.staffmanage;

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
public class StaffManageModule {

    private  StaffManageContract.View view;

    public StaffManageModule(StaffManageContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public StaffManageContract.Presenter providePresenter(){
        return new StaffManagePresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public StaffManageContract.View provideStaffManageContractView(){
        return view;
    }
}
