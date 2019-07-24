package com.zwx.scan.app.feature.couponmanage;

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
@Component(modules = GiveCouponModule.class,dependencies = ApplicationComponent.class)
public interface GiveCouponComponent {

    void inject(GiveCouponNewActivity activity);
//    void inject(CampaignCouponListFragment fragment);
    void inject(GiveCouponManageActivity activity);
    void inject(GiveCouponNewNextActivity activity);
    void inject(GiveCouponNextFragment fragment);


    void inject(CouponManageActivity activity);
    void inject(CouponNewActivity activity);



    void inject(GiveCouponNewNextConsumeActivity activity);

    void inject(GiveCouponConsumeSettingFragment fragment);
}
