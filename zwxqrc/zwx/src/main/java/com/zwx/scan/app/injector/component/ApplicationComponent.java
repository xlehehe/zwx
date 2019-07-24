package com.zwx.scan.app.injector.component;

import android.content.Context;

import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

//需求方通过Componet 引入module 中的Application实例对象
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    AppContext getApplication();

    Context getContext();
}
