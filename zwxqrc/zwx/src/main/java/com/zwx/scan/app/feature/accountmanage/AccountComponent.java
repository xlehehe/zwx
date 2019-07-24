package com.zwx.scan.app.feature.accountmanage;

import android.accounts.Account;

import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

@ActivtiyScoped
@Component(modules = AccountModule.class,dependencies = ApplicationComponent.class)
public interface AccountComponent {


    void inject(AccountActivity activity);

    void inject(AccountNewActivity activity);

    void inject(AccountRoleAuthActivity activity);
}
