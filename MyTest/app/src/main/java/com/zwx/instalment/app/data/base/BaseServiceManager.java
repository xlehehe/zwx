package com.zwx.instalment.app.data.base;


import android.util.Log;

import com.zwx.instalment.app.data.bean.BaseResponse;
import com.zwx.instalment.app.data.request.exception.ApiException;
import com.zwx.instalment.app.data.request.json.BaseDataResponse;
import com.zwx.instalment.app.utils.LogUtils;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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
    protected  <T> Observable<BaseResponse<T>> observe(Observable<BaseResponse<T>> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Response<BaseResponse<T>> call(Call<BaseResponse<T>> call) throws IOException{
        return call.execute();
    }



    protected <T> Observable<BaseResponse<T>> concat(Observable<BaseResponse<T>> observable1,Observable<BaseResponse<T>> observable12){
        return Observable.concatArray(observable1,observable12).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 默认情况下,发送者和接收者都运行在主线程,但是这显然是不符合实际需求的,我们在日常使用中,
     * 通常用的最多的就是在子线程进行各种耗时操作,然后发送到主线程进行,难道我们就没有办法继续
     * 用这个优秀的库了?想多了你,一个优秀的库如果连这都想不到,怎么能被称为优秀呢,RxJava中有线
     * 程调度器,通过线程调度器,
     *
     * Transformer的变化:RxJava1.X为rx.Observable.Transformer接口, 继承自
     * Func1<Observable<T>, Observable<R>>, RxJava2.X为io.reactivex.ObservableTransformer<Upstream, Downstream>,是一个独立的接口。
     * Flowable则是FlowableTransformer，如果你使用Flowable，以下ObservableTransformer
     * 替换FlowableTransformer即可。
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseDataResponse<T>, T> handleResult() {
        return new ObservableTransformer<BaseDataResponse<T>, T>() {
            @Override public Observable<T> apply(Observable<BaseDataResponse<T>> tObservable) {
                return tObservable.flatMap(new Function<BaseDataResponse<T>, ObservableSource<T>>() {
                    @Override public Observable<T> apply(BaseDataResponse<T> result) {
                        //成功后交给界面处理
                        if (result.getCode() == BaseProjectConfig.successCode) {
                            return createData(result.getData());
                        } else {
                            //统一处理服务器返回值非正常结果
                            LogUtils.d(BaseProjectConfig.TAG, "BaseRequestBusiness统一处理服务器返回值非正常结果apply: " + BaseProjectConfig.getApiReason(result.getCode()));
                            return Observable.error(new ApiException(BaseProjectConfig.getApiReason(result.getCode())));
                        }
                    }
                })
                        /*
                         - Schedulers.io()      io操作的线程, 通常io操作,如文件读写,读写数据库、网络信息交互等.
                         - Schedulers.computation()      计算线程,适合高计算,数据量高的操作.
                         - Schedulers.newThread()      创建一个新线程,适合子线程操作.
                         - AndroidSchedulers.mainThread()      Android的主线程,主线程
                        */
                        .subscribeOn(Schedulers.io()) //线程调度器,将发送者运行在子线程,subscribeOn(),只有在第一次调用的时候生效,之后不管调用多少次,只会以第一次为准.
                        .unsubscribeOn(Schedulers.io())//解除订阅
//        .subscribeOn(AndroidSchedulers.mainThread())//
                        .observeOn(AndroidSchedulers.mainThread());//接受者运行在主线程 observeOn(),可以被调用多次,每次调用都会更改线程.
            }
        };
    }


    /**
     * 创建成功的数据,观察者模式,这里产生事件,事件产生后发送给接受者
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });
    }

}
