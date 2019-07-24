package com.zwx.instalment.app.data.service;

import com.zwx.instalment.app.data.bean.BaseResponse;
import com.zwx.instalment.app.data.entity.User;
import com.zwx.instalment.app.data.request.HttpUrls;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : lizhilong
 * time   : 2019/07/05
 * desc   :
 * version: 1.0
 **/
public interface UserService {

    /***
     * 用户登录
     * @param  username
     * @param  password
     * @return Type Observable<HttpResponse>
     * */
    @FormUrlEncoded
    @POST(HttpUrls.LOGIN_URL)
    Observable<BaseResponse<User>> login(@Field("username") String username, @Field("password") String password);
}
