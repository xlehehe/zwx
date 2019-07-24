package com.zwx.scan.app.feature.personal;

import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

@ActivtiyScoped
@Component(modules = PersonalModule.class,dependencies = ApplicationComponent.class)
public interface PersonalComponent {


    void inject(PersonalFragment fragment);
    void inject(SystemManageActivity activity);

    void inject(PersonalAccountManageActivity activity);

    void inject(PasswordManageActivity activity);


    void inject(MessageListActivity activity);


    void inject(MessageListFragment fragment);

    void inject(PersonalTwoFragment fragment);

    void inject(PersonalThreeFragment fragment);
    //意见反馈
    void inject(FeedBackActivity activity);


    void inject(FeedBackListActivity activity);
    void inject(FeedBackDetailActivity activity);

    //认证 2019-05-13
    //个人认证
    void inject(TradeDrawingAuthActivity activity);

    void inject(TradeDrawingPersonalAuthOneActivity activity);
    void inject(TradeDrawingPersonalAuthTwoActivity activity);

    void inject(TradeDrawingPersonalAuthThreeActivity activity);

    //企业认证
    void inject(TradeDrawingEnterpriseAuthOneActivity activity);



    //支付认证完成页面
    void inject(PayAuthComplateActivity activity);
    void inject(PayAuthNoComplateActivity activity);
    //更换手机号
    void inject(PayAuthPhoneActivity activity);
    //个人认证- 解绑银行卡
    void inject(PayAuthUnboundActivity activity);
    void inject(PayAuthAgreeActivity activity);

    //个人认证 信息
    void inject(PayAuthInfoActivity activity);

    void inject(TradeDrawingEnterpriseInfoActivity activity);
}
