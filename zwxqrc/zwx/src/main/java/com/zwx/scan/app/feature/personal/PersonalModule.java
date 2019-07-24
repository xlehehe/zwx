package com.zwx.scan.app.feature.personal;


import com.zwx.scan.app.feature.user.LoginPresenter;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author lizhilogn
 *         2018/8/15
 */
@Module
public class PersonalModule {

    private  PersonalContract.View view;

    public PersonalModule(PersonalContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public PersonalContract.Presenter providePresenter(){
        return new PersonalPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public PersonalContract.View providePersonalContractView(){
        return view;
    }
}
