package com.zwx.scan.app.feature.contractmanage;

import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

@ActivtiyScoped
@Component(modules = ContractModule.class,dependencies = ApplicationComponent.class)
public interface ContractComponent {


    void inject(ContractActivity activity);

    void inject(ContractInfoFragment fragment);

    void inject(ContractListActivity activity);
}
