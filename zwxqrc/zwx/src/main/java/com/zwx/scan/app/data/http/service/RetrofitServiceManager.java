package com.zwx.scan.app.data.http.service;


import android.net.TrafficStats;
import android.util.Log;

import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.BuildConfig;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Toke;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * @author lizhilong
 * desc     整个网络通信服务的启动控制，
 *          必须先调用初始化函数才能正常使用网络通信接口
 * time   2018/8/22
 * version 1.0
 * */
public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 60;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 60;
    private Retrofit retrofit;
    private static Map<String,String> headTokenMap=new HashMap<>();   //认证参数  token singure timestamp

    private static final String CACHE_NAME="HttpCache";
    private static RetrofitServiceManager instance;


    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        if (null == instance) {
            synchronized (RetrofitServiceManager.class) {
                if (null == instance) {
                    instance = new RetrofitServiceManager();
                }
            }
        }
        return instance;
    }
    /***
     * 初始化网络通信服务
     * 接口实例Service都可以用这个来生成如下
     * userService = RetrofitServiceManager.getInstance().create(SserService.class);
     * */
    private RetrofitServiceManager(){
        // 指定缓存路径,缓存大小1Mb
//        Cache cache = new Cache(new File(AppContext.getInstance().getExternalCacheDir(), CACHE_NAME),
//                1024 * 1024 * 1);
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        //添加日志打印以及Stetho 网络线程和内存溢出监控接口
        if(BuildConfig.DEBUG){
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//            loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
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
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(requestInterceptor);



        OkHttpClient client=builder.build();
        retrofit=new Retrofit.Builder()
                .baseUrl(HttpUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

    }


    /**
     * desc 设置请求头信息 token singurning  timestamp
     * @param  headMap 请求头信息
     * return  void
     * */
    public static void setHeadTokenMap(Map<String, String> headMap) {
        headTokenMap = headMap;
    }

    public static void setHeadTokenObjMap(Map<String, Object> headMap) {
        headTokenMap = new HashMap<>();
        if(headMap != null && headMap.size()>0){
            for (String k : headMap.keySet()){
                headTokenMap.put(k,String.valueOf(headMap.get(k)));
            }
        }
    }

    /**
     * 那个 if 判断意思是，如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
     * 只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
     * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
     */
    Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) {
            Request originalRequest = chain.request();
            Response originalResponse=null;


            if (headTokenMap == null){
                try {
                    //标记线程内部发生数据传输情况
                    TrafficStats.setThreadStatsTag(0xF00D);
                    originalResponse = chain.proceed(originalRequest);
                    return originalResponse.newBuilder()
                            .body(originalResponse.body())
                            .build();
                } catch(Exception ex){
//                    ex.printStackTrace();
                    TrafficStats.clearThreadStatsTag();
                }finally {
                    TrafficStats.clearThreadStatsTag();
                }
            }

            Request.Builder builder = originalRequest.newBuilder();
            builder.addHeader("Connection", "close");
            Set<String> keys = headTokenMap.keySet();
            for (String headerKey : keys) {

                builder.addHeader(headerKey, headTokenMap.get(headerKey));
            }


            try {
                //标记线程内部发生数据传输情况
                TrafficStats.setThreadStatsTag(0xF00D);
                originalResponse = chain.proceed(builder.build());
                return originalResponse.newBuilder()
                        .body(originalResponse.body())
                        .build();

            } catch(Exception ex){
//                ex.printStackTrace();
                TrafficStats.clearThreadStatsTag();
            }finally {
                TrafficStats.clearThreadStatsTag();
            }
            //根据和服务端的约定判断token过期
           /* if(isTokenExpired(originalResponse)){
                String refreshToken=getRefreshToken();
                if(!"".equals(refreshToken)){
                    builder.addHeader("token",refreshToken);
                }

            }*/

            return originalResponse;
        }
    };

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }



    /**
     * Description: 根据Response，判断Token是否失效
     *
     * @param  response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    /**
     * Description : 同步请求方式，获取最新的Token
     *
     * @return Token 字符串
     */
    private String getRefreshToken() {

        String username = SPUtils.getInstance().getString("username");
        String password = SPUtils.getInstance().getString("password");
        Call<HttpResponse<Toke>> refreshTokenCall = new UserServiceManager().refreshToken(username,password);
        try{
            TrafficStats.setThreadStatsTag(0xF00D);
            retrofit2.Response<HttpResponse<Toke>> response = refreshTokenCall.execute();
            if (response.isSuccessful()) {
                int responseCode = response.body().getCode();
                if (responseCode == 1) {
                    Toke token = response.body().getResult();

                    ApiConstants.refreshToken = token.getToken();
                    SPUtils.getInstance().put("token",ApiConstants.refreshToken);
                    return ApiConstants.refreshToken;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            TrafficStats.clearThreadStatsTag();
        }

        return "";
    }
}
