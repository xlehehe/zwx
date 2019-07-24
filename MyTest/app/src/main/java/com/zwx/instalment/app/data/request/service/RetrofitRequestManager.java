package com.zwx.instalment.app.data.request.service;

import android.util.Log;

import com.zwx.instalment.app.BuildConfig;
import com.zwx.instalment.app.data.base.BaseProjectConfig;
import com.zwx.instalment.app.data.request.factory.MyGsonConverterFactory;
import com.zwx.instalment.app.data.request.interceptor.BaseUrlInterceptor;
import com.zwx.instalment.app.data.request.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * author : lizhilong
 * time   : 2019/07/04
 * desc   : 整个网络通信服务的启动控制，
 *          必须先调用初始化函数才能正常使用网络通信接口
 * version: 1.0
 **/
public class RetrofitRequestManager {
    private static final int DEFAULT_TIME_OUT = 60;//超时时间 60s
    private static final int DEFAULT_READ_TIME_OUT = 60;
    private Retrofit retrofit;
    private static final String CACHE_NAME="HttpCache";
    private static RetrofitRequestManager instance;


    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitRequestManager getInstance(){
        if (null == instance) {
            synchronized (RetrofitRequestManager.class) {
                if (null == instance) {
                    instance = new RetrofitRequestManager();
                }
            }
        }
        return instance;
    }

    private RetrofitRequestManager() {
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseProjectConfig.baseURL)//配置服务器路径
//                .addConverterFactory(MyStringConverterFactory.create())//配置转化库，String
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())//配置转化库，FastJson
                .addConverterFactory(MyGsonConverterFactory.create())//配置转化库，Gson
                // 配置转化库，默认是Gson(返回参数不规范 要不然可以直接转换成实体类)
                //配置回调库，采用RxJava
                //设置OKHttpClient为网络客户端
                .client(getClient())
                .build();
    }



    private OkHttpClient getClient(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        //添加日志打印以及Stetho 网络线程和内存溢出监控接口
        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("retrofitServiceManager","retrofitServiceManager----- "+message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        builder
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);   //错误重连
//                .addNetworkInterceptor(requestInterceptor);
        if (BaseProjectConfig.isBaseURLInterceptor) {
            builder.addInterceptor(new BaseUrlInterceptor());
        }
        if (BaseProjectConfig.isHeaderInterceptor) {
            builder.addInterceptor(new HeaderInterceptor());
        }

        OkHttpClient client=builder.build();

        return client;
    }



    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }




}


