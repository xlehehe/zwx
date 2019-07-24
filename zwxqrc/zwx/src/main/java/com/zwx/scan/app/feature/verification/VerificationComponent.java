package com.zwx.scan.app.feature.verification;

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
@Component(modules = VerificationModule.class,dependencies = ApplicationComponent.class)
public interface VerificationComponent {

    void inject(VerificationActivity activity);
    void inject(VerificationRecordActivity activity);
    void inject(VerificationRecordDetailActivity activity);
    void inject(ProductVerificationActivity activity);
}
