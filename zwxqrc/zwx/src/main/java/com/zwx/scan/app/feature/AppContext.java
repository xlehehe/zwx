package com.zwx.scan.app.feature;

import android.content.Context;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zwx.scan.app.BuildConfig;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.component.DaggerApplicationComponent;
import com.zwx.scan.app.injector.module.ApplicationModule;
import com.zwx.scan.app.utils.log.CrashHandler;
import com.zwx.scan.app.utils.log.Log4jConfigure;

import org.apache.log4j.Logger;

public class AppContext extends MultiDexApplication {
//    public class AppContext extends Application {
    //初始化Logger
    protected static Logger log = Logger.getLogger(AppContext.class);
    private static final String TAG = AppContext.class.getSimpleName();
    public static AppContext appContext;

    private static ApplicationComponent applicationComponent;

    private RefWatcher mRefWatcher;

    public static AppContext getInstance() {
        return appContext;
    }
    public  static boolean DEBUG = BuildConfig.DEBUG;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
     /*   if (BuildConfig.DEBUG) {
            //系统检测主线程违例的情况并做出相应的反应,提高代码优化和改善代码逻辑
            //线程策略检测
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy
                    .Builder(StrictMode.getThreadPolicy())
//                    .detectAll()
//                    .penaltyDialog() //弹出违规提示对话框
                    .detectAll()
                    .permitDiskReads()    //解决 distread 问题
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .build());

            //虚拟机策略检测(内存泄漏)
            StrictMode.setVmPolicy(new StrictMode.VmPolicy
                    .Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());



        }*/

        DEBUG = BuildConfig.DEBUG;

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        mRefWatcher = DEBUG ?  LeakCanary.install(this) : RefWatcher.DISABLED;
        LeakCanary.install(this);
        appContext=this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        //初始化LogLibrary
        CrashHandler.getInstance().init(getApplicationContext());
        new Thread(){
            @Override
            public void run() {
                Log4jConfigure.configure(getFilesDir().getAbsolutePath());
                log.info("configure log4j ok---path--"+getFilesDir().getAbsolutePath());
            }
        }.start();




    }


    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }
}
