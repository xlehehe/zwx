package com.zwx.scan.app.feature.cateringinfomanage;

import com.zwx.scan.app.feature.couponmanage.GiveCouponNewNextActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNextFragment;
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
@Component(modules = CateringInfoModule.class,dependencies = ApplicationComponent.class)
public interface CateringInfoComponent {
    void inject(CateringinfoManageActivity activity);
    void inject(BrandSettingActivity activity);
    void inject(StoreInfoManageActivity activity);

}
