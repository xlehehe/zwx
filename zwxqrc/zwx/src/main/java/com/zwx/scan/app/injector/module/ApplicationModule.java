package com.zwx.scan.app.injector.module;

import android.content.Context;

import com.zwx.scan.app.feature.AppContext;

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
    public AppContext provideApplication(){
        return (AppContext)context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

}
