package com.zwx.scan.app.feature.home;

import com.zwx.scan.app.feature.verification.VerificationModule;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * author : lizhilong
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 **/

@ActivtiyScoped
@Component(modules = HomeModule.class,dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);

}
