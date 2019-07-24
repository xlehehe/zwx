package com.zwx.scan.app.feature.staffmanage;

import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * @author lizhilong
 * @version 1.0
 * @desc
 * @time 2018/10/16
 */

@ActivtiyScoped
@Component(modules = StaffManageModule.class,dependencies = ApplicationComponent.class)
public interface StaffManageComponent {

    void inject(StaffEditActivity activity);

    void inject(StaffListActivity activity);

    void inject(PullQrcManageActivity activity);
    void inject(PullQrcSelectContentActivity activity);

}
