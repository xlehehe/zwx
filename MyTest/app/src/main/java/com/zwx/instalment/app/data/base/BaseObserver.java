package com.zwx.instalment.app.data.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.zwx.instalment.app.data.bean.BaseResponse;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Base Observer 的封装处理
 *
 * Created by lizhilong on 2018/8/20.
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private final String TAG = BaseObserver.class.getSimpleName();
    private final int RESPONSE_CODE_OK = 1;      //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_CODE_FAILED = -1; //返回数据失败

    //是否需要显示Http 请求的进度，默认的是需要，但是Service 和 预取数据不需要
    private boolean showProgress = true;
    private Context mContext;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();   //不处理吧
//    Disposable disposable;
    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！
     *
     * @param t
     */
    public abstract void onSuccess(T t,String message);
    /**
     * @param mContext
     * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public BaseObserver(Context mContext, boolean showProgress) {
        this.showProgress = showProgress;
        this.mContext = mContext;
        if (showProgress) {
//            HttpUiTips.showDialog(mContext, true, null);
        }
    }

    @Override
    public final void onSubscribe(Disposable disposable) {

//        this.disposable = disposable;
        compositeDisposable.add(disposable);
    }

    @Override
    public final void onNext(BaseResponse<T> response) {
        Log.e(TAG, response.toString());
//        HttpUiTips.dismissDialog(mContext);
        if (response.getCode() == RESPONSE_CODE_OK) {
            onSuccess(response.getResult(),response.getMessage());
        } else {
            onFailure(response.getCode(), response.getMessage());

        }

    }

    @Override
    public final void onError(Throwable t) {
//        HttpUiTips.dismissDialog(mContext);
        int code = 0;
        String errorMessage = "未知错误";
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            String meg = httpException.response().toString();   //
            code = httpException.code();
            errorMessage = httpException.getMessage();
            if(code == 404){
                code = RESPONSE_CODE_FAILED;
                errorMessage = "请求服务器路径错误";
//                errorMessage = "网络连接异常，请检查网络！";
            }else if(code == 401){
                code = RESPONSE_CODE_FAILED;
                errorMessage = "认证失败！";
            }else if(code == 400){
                code = RESPONSE_CODE_FAILED;
                errorMessage = "服务器内部错误";
            }

        } else if (t instanceof SocketTimeoutException) {  //VPN open
            code = RESPONSE_CODE_FAILED;
            errorMessage = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "网络连接异常，请检查网络";
        } else if (t instanceof RuntimeException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "运行时错误";
        } else if (t instanceof UnknownHostException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            code = RESPONSE_CODE_FAILED;
            errorMessage = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            code = RESPONSE_CODE_FAILED;
            errorMessage = "没有网络，请检查网络连接";
        } else if(t.getMessage().contains("401")){
            code = RESPONSE_CODE_FAILED;
            errorMessage = "认证失败";
        }else if(t.getMessage().contains("404")){
            code = RESPONSE_CODE_FAILED;
            errorMessage = "请求服务器路径错误";
        }

        /**
         * 严重的错误弹出dialog，一般的错误就只要Toast
         */
        if (RESPONSE_CODE_FAILED == code) {
            onFailure(RESPONSE_CODE_FAILED, errorMessage);
        } else {
            if (mContext != null && !((Activity) mContext).isFinishing()) {
//                ToastUtils.showShort(errorMessage);
            }
        }
    }


    /**
     * 简单的把Dialog 处理掉
     */
    @Override
    public final void onComplete() {
//        HttpUiTips.dismissDialog(mContext);
    }

    /**
     * Default error dispose!
     * 一般的就是 AlertDialog 或 SnackBar
     *
     * @param code
     * @param message
     */
    @CallSuper
    public void onFailure(int code, String message) {
        if (code == RESPONSE_CODE_FAILED && mContext != null) {
//            HttpUiTips.alertTip(mContext, message, code);
          /*  if(message.contains("认证失败")){
            }else {
//                ToastUtils.showShort(message);
            }*/
        } else {
            disposeEorCode(message, code);
        }
    }


    /**
     * 对通用问题的统一拦截处理
     *
     * @param code
     */
    private final void disposeEorCode(String message, int code) {
        switch (code) {
            case 0:
                break;
            //账号错误
            case 6:
                break;
                //密码错误
            case 7:
                break;
                //认证失败
            case 401:
                //退回到登录页面
                break;
        }

    }

}
