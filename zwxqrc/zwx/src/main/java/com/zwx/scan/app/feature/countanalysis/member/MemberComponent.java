package com.zwx.scan.app.feature.countanalysis.member;

import com.zwx.scan.app.feature.home.HomeFragment;
import com.zwx.scan.app.feature.home.HomeModule;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
@ActivtiyScoped
@Component(modules = MemberModule.class,dependencies = ApplicationComponent.class)
public interface MemberComponent {

    void inject(MemberAnalysisActivity activity);

    void inject(MemberAnalysisDetailActivity activity);
}
