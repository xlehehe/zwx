
package com.zwx.scan.app.injector.component;

import com.zwx.scan.app.feature.campaign.CampaignsPresenter;
import com.zwx.scan.app.feature.cateringinfomanage.CateringInfoPresenter;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignPresenter;
import com.zwx.scan.app.feature.countanalysis.member.MemberPresenter;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffPresenter;
import com.zwx.scan.app.feature.couponmanage.GiveCouponPresenter;
import com.zwx.scan.app.feature.financialaffairs.FinancialAffairsPresenter;
import com.zwx.scan.app.feature.home.HomePresenter;
import com.zwx.scan.app.feature.member.MemberManagePresenter;
import com.zwx.scan.app.feature.shop.ShopPresenter;
import com.zwx.scan.app.feature.staffmanage.StaffManagePresenter;
import com.zwx.scan.app.feature.user.LoginPresenter;
import com.zwx.scan.app.injector.module.ApplicationModule;
import com.zwx.scan.app.feature.verification.VerificationPresenter;

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

    void inject(LoginPresenter presenter);

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

    void inject(FinancialAffairsPresenter presenter);
}
 