
package com.zwx.instalment.app.injector.component;

import com.zwx.instalment.app.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *  author : lizhilong
 *  time   : 2018/10/15
 *  desc   : 接口依赖注入
 *  version: 1.0
 **/
@Singleton
@Component(modules = {ApplicationModule.class})
public interface PresenterComponent {

  /*  void inject(LoginPresenter presenter);

    void inject(VerificationPresenter presenter);

    void inject(HomePresenter presenter);

    void inject(MemberPresenter presenter);

    void inject(StaffPresenter presenter);

    void inject(CampaignPresenter presenter);


    void inject(StaffManagePresenter presenter);


    void inject(MemberManagePresenter presenter);

    void inject(CampaignsPresenter presenter);

    void inject(CateringInfoPresenter presenter);
    void inject(GiveCouponPresenter presenter);
    void inject(ShopPresenter presenter);

    void inject(FinancialAffairsPresenter presenter);*/
}
 