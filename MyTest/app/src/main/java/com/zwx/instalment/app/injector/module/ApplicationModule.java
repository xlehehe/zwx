package com.zwx.instalment.app.injector.module;

import android.content.Context;

import com.zwx.instalment.app.feature.QDApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


//提供全局Application  module对象实例
@Module
public class ApplicationModule {

    public Context context;

    public ApplicationModule(Context context){
        this.context=context;
    }


    @Provides
    @Singleton
    public QDApplication provideApplication(){
        return (QDApplication)context.getApplicationContext();
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

}
