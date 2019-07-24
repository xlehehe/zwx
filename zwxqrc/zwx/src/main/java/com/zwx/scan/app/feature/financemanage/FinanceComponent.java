package com.zwx.scan.app.feature.financemanage;

import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtNewActivity;
import com.zwx.scan.app.feature.ptmanage.PtOrderActivity;
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
@Component(modules = FinanceModule.class,dependencies = ApplicationComponent.class)
public interface FinanceComponent {

    void inject(PayFeeManageActivity activity);
    void inject(PayFeeListDetailActivity activity);

}
