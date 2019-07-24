package com.zwx.scan.app.feature.user;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.vector.update_app.HttpManager;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateDialogFragment;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.AppUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.MobileVersion;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.UserServiceManager;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.utils.UpdateAppHttpUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录业务逻辑
 * version: 1.0
 **/

public class LoginPresenter  implements LoginContract.Presenter{

    private final LoginContract.View loginView;
    private UserServiceManager userServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;
    LoginActivity activity;
    @Inject
    public LoginPresenter(LoginContract.View view) {
        this.loginView = view;
//        this.subscriptions=new CompositeSubscription();
//        this.context=context;
//        loginView.setPresenter(this);
//        DaggerUserComponent.builder()
//                .applicationModule(new ApplicationModule(AppContext.getInstance().getApplicationContext()))
//                .build()
//                .inject(this);

//        DaggerPresenterComponent.builder()
//                .applicationModule(new ApplicationModule(context))
//                .build().inject(this);
        userServiceManager = new UserServiceManager();
        this.disposable = new CompositeDisposable();


    }

    @Override
    public void login(Context context,String username, String password) {

        RetrofitServiceManager.setHeadTokenMap(null);

        if(!NetworkUtils.isNetworkAvailable(context) ){
            ToastUtils.showShort(R.string.network_error);
            return;
        }
        activity = (LoginActivity)context;
        userServiceManager.login(username,password)
                .subscribe(new BaseObserver<User>(context,true){
                    @Override
                    public void onSuccess(User user,String msg) {
                        activity.tvAccountTip.setText("");
                        activity.tvPsdTip.setText("");

                        if(user == null){
                            ToastUtils.showShort("登录失败!");
                            return;
                        }

                        String userId = SPUtils.getInstance().getString("userId");

                        if(!userId.equals(String.valueOf(user.getId()))){

                            SPUtils.getInstance().clear();
                            SPUtils.getInstance().clear(true);
                        }

                        SPUtils.getInstance().put("userId",user.getUserId()+"");
                        SPUtils.getInstance().put("username",username);
                        SPUtils.getInstance().put("password",password);

                        SPUtils.getInstance().put("storeNames",user.getStoreNames());
                        SPUtils.getInstance().put("rolesNames",user.getRoleNames());
                        SPUtils.getInstance().put("nickName",user.getNickname());
                        String token=user.getToken();
                        SPUtils.getInstance().put("token",token);
                        SPUtils.getInstance().put("isLogined","NO");
                        SPUtils.getInstance().put("isFirst",false);
                        ActivityUtils.startActivity(MainActivity.class,
                                R.anim.slide_in_right,R.anim.slide_out_left);
                        ((LoginActivity) context).finish();

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        SPUtils.getInstance().put("isLogined","YES");
                        activity.tvPsdTip.setText(message);
                        activity.edtPsd.setFocusable(true);
                        activity.edtPsd.setFocusableInTouchMode(true);

                    }
                });
    }



    /**
     * desc 校验账号是否重复
     * @param username
     * @return
     * */
    @Override
    public void checkUsername(Context context,String username) {
        activity = (LoginActivity)context;

        /*if(!NetworkUtils.isNetworkConnected(context)){
            ToastUtils.showShort(R.string.network_error);
            return;
        }*/
        userServiceManager.checkUsername(username)
                .subscribe(new BaseObserver<String>(context,false){

            @Override
            public void onSuccess(String result,String msg) {
                activity.tvAccountTip.setText("");
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);

                LoginActivity activity = (LoginActivity)context;
                if(code == 6){
                    activity.tvAccountTip.setText(message);
                }else if(code ==8){  //表示账号已存在
                    activity.tvAccountTip.setText("");
                }else {
                    ToastUtils.showToast(message);
                }


            }
        });
    }

    @Override
    public void doDownloadVersion(Context context, String id) {
        activity = (LoginActivity)context;

        /*if(!NetworkUtils.isNetworkConnected(context)){
            ToastUtils.showShort(R.string.network_error);
            return;
        }*/
        userServiceManager.doDownloadVersion(id)
                .subscribe(new BaseObserver<MobileVersion>(context,false){

                    @Override
                    public void onSuccess(MobileVersion result,String msg) {
                        if(result != null){
                            int serverVersion =Integer.parseInt(result.getVersionCode());
                            int version = AppUtils.getAppVersionCode();
                            if(serverVersion>version){
                                String updateUrl = result.getUpdatePath();
                                UpdateAppManager appManager = new UpdateAppManager();
                                UpdateAppBean mUpdateApp = new UpdateAppBean();
                                mUpdateApp.setUpdate("Yes");
                                mUpdateApp.setApkFileUrl(updateUrl);
                                mUpdateApp.setOnlyWifi(false);
                                mUpdateApp.setNewVersion(result.getVersionName());
                                mUpdateApp.setUpdateLog(result.getUpdateLog());
                                mUpdateApp.setTargetSize("26");
                                appManager.mUpdateApp = mUpdateApp;

                                Bundle bundle = new Bundle();
                                //添加信息，
                                String path = "";
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
                                    try {
                                        path = context.getExternalCacheDir().getAbsolutePath();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (TextUtils.isEmpty(path)) {
                                        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                                    }
                                } else {
                                    path = context.getCacheDir().getAbsolutePath();
                                }
                                if (mUpdateApp != null) {
                                    mUpdateApp.setTargetPath(path);
                                    mUpdateApp.setHttpManager(new UpdateAppHttpUtil());
                                    mUpdateApp.setHideDialog(false);
                                    mUpdateApp.showIgnoreVersion(false);
                                    mUpdateApp.dismissNotificationProgress(false);
                                    mUpdateApp.setOnlyWifi(false);
                                }
                                bundle.putSerializable("update_dialog_values", mUpdateApp);
                                int mThemeColor =0;
                                if (mThemeColor != 0) {
                                    bundle.putInt("theme_color", mThemeColor);
                                }
                                int mTopPic =0;
                                if (mTopPic != 0) {
                                    bundle.putInt("top_resId", mTopPic);
                                }

                                UpdateDialogFragment
                                        .newInstance(bundle)
                                        .setUpdateDialogFragmentListener(null)
                                        .show(((FragmentActivity) context).getSupportFragmentManager(), "dialog");
                            }





                        }

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);


                    }
                });
    }
/* @Override
    public void subscribe() {
    }*/

/*    @Override
    public void unSubscribe() {
        //取消订阅
        disposable.dispose();

    }*/


}
