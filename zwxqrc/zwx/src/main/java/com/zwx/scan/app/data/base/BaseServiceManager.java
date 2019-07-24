package com.zwx.scan.app.data.base;


import android.os.Looper;

import com.zwx.scan.app.data.bean.HttpResponse;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author lizhilong
 * desc   将一些重复的操作提出来，
 *               放到父类以免Manager里每个接口都有重复代码
 * time   2018/8/22
 * version 1.0
 * */
public class BaseServiceManager {

    /**
     *
     * @param observable
     * @param <T>
     * @return
     */
    protected  <T> Observable<HttpResponse<T>> observe(Observable<HttpResponse<T>> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected  <T> Observable<HttpResponse<T>> observeNewThread(Observable<HttpResponse<T>> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }


    protected <T> Response<HttpResponse<T>> call(Call<HttpResponse<T>> call) throws IOException{
        return call.execute();
    }



    protected <T> Observable<HttpResponse<T>> concat(Observable<HttpResponse<T>> observable1,Observable<HttpResponse<T>> observable12){
        return Observable.concatArray(observable1,observable12).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());

    }


}
