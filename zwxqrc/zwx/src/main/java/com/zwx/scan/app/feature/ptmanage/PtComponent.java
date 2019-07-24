package com.zwx.scan.app.feature.ptmanage;

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
@Component(modules = PtModule.class,dependencies = ApplicationComponent.class)
public interface PtComponent {


    void inject(PtManageActivity activity);

    void inject(PtNewActivity activity);

    void inject(PtNextTwoActivity activity);
    void inject(PtNextThreeActivity activity);


    void inject(PtNextSettingFragment fragment);

    void inject(PtOrderActivity activity);
    void inject(PtOrderDetailActivity activity);
}
