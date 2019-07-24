package com.zwx.scan.app.feature.countanalysis.staffreward;

import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisDetailActivity;
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
@Component(modules = StaffModule.class,dependencies = ApplicationComponent.class)
public interface StaffComponent {

    void inject(StaffRewardAnalysisActivity activity);

}
