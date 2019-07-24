package com.zwx.scan.app.feature.member;

import com.zwx.scan.app.feature.personal.PersonalFragment;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

@ActivtiyScoped
@Component(modules = MemberManageModule.class,dependencies = ApplicationComponent.class)
public interface MemberManageComponent {


    void inject(MemberConsumeListFragment fragment);

    void inject(MemberConsumeListDetailActivity activity);

    void inject(MemberInfoListActivity activity);

    void inject(MemberInfoDetailActivity activity);


    void inject(MemberCardManageActivity activity);

    void inject(MemberCardNewActivity activity);

    void inject(MemberCardNewTwoActivity activity);

    void inject(MemberCardStreamActivity activity);

    void inject(MemberCardStreamDetailActivity activity);
}
