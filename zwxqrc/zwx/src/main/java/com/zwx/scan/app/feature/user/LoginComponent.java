package com.zwx.scan.app.feature.user;

import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

@ActivtiyScoped
@Component(modules = LoginModule.class,dependencies = ApplicationComponent.class)
public interface LoginComponent {


    void inject(LoginActivity loginActivity);
}
