/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zwx.instalment.app.feature;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.squareup.leakcanary.LeakCanary;
import com.zwx.instalment.app.BuildConfig;

import okhttp3.OkHttpClient;

/**
 * Demo 的 Application 入口。
 * Created by lizhilong on 16/3/22.
 */
public class QDApplication extends Application {

    @SuppressLint("StaticFieldLeak") private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

//        QDUpgradeManager.getInstance(this).check();
        QMUISwipeBackActivityManager.init(this);

        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
            System.out.println("this is a debug mode");
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }

    }
}
