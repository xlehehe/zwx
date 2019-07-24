package com.zwx.scan.app.feature.campaign;

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
@Component(modules = CampaignsModule.class,dependencies = ApplicationComponent.class)
public interface CampaignsComponent {
    //活动列表
    void inject(CampaignListActivity activity);
    void inject(CampaignListTwoActivity activity);

    //转发活动
    void inject(CampaignNewActivity activity);
    void inject(CampaignNewNextActivity activity);
    void inject(CampaignCouponListActivity activity);
    void inject(CampaignCouponListFragment fragment);

    void inject(CampaignNewNextTwoActivity activity);
    void inject(CampaignNewNextThreeActivity activity);
    void inject(CampaignCouponNextSettingFragment fragment);
    //转发活动海报
    void inject(PosterListActivity activity);

    //老虎机/砸金蛋
    void inject(LaohuPinTuanNewActivity activity);

    void inject(LaohuPinTuanNextTwoActivity activity);
    void inject(LaohuPinTuanNextTwoFragment fragment);
    void inject(LaohuPinTuanNextThreeActivity activity);

    void inject(LaohuPinTuanNextSettingFragment fragment);

    void inject(PrizeTempletActivity activity);

    void inject(LaoHuTempletActivity activity);

    //会员完善信息
    void inject(MemberInfoPerfectNewActivity activity);
    void inject(MemberInfoPerfectNextTwoActivity activity);

    void inject(MemberInfoPerfectCouponNextSettingFragment fragment);

    //集赞活动
    void inject(CampaignLikeNewActivity activity);
    void inject(CampaignLikeTwoActivity activity);
    void inject(CampaignLikeThreeActivity activity);

//    void inject(CampaignLikeTwoFragment activity);
    void inject(CampaignLikeTwoCouponSettingFragment activity);
}
