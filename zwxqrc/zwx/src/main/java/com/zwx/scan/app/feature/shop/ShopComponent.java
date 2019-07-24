package com.zwx.scan.app.feature.shop;

import com.zwx.scan.app.feature.ptmanage.PtModule;
import com.zwx.scan.app.feature.verification.ProductVerificationActivity;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :  商城 类注入
 * version: 1.0
 **/
@ActivtiyScoped
@Component(modules = ShopModule.class,dependencies = ApplicationComponent.class)
public interface ShopComponent {

    void inject(ShopClassFragment fragment);
    void inject(ShopTelFragment fragment);
    void inject(ShopOrderActivity activity);

    void inject(ShopOrderDetailActivity activity);
    void inject(ShopManageActivity activity);

    void inject(ShopManageNewActivity activity);


    //红包
    //列表 包含金额展示
    void inject(RedEnvelopeActivity activity);

    void inject(RedEnvelopeDetailActivity activity);

    void inject(SearchRedEnvelopeListActivity activity);
    void inject(ProductVerificationRecordActivity activity);
    void inject(ProductVerificationRecordDetailActivity activity);
}
