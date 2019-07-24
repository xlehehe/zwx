package com.zwx.scan.app.data.http.service;

import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.data.bean.MessageBean;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.MobileVersion;
import com.zwx.scan.app.data.bean.PersonalBean;
import com.zwx.scan.app.data.bean.Resource;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TokenVali;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Toke;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.HttpUrls;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : lizhilong
 * time   : 2018/10/15
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
//    Observable<HttpResponse> login(@Field("username") String username, @Field("password") String password);
    Observable<HttpResponse<User>> login(@Field("username") String username, @Field("password") String password);


    /***
     * token认证
     * @param id
     * @return Observable<HttpResponse>
     * */
    @GET(HttpUrls.TOKEN_AUTH_URL)
    Observable<HttpResponse<TokenVali>>  tokenAuth(@Query("id") String id);

    @FormUrlEncoded
    @POST(HttpUrls.LOGIN_URL)
    Call<HttpResponse<Toke>> refreshToken(@Field("username") String username, @Field("password") String password);



    /***
     * 退出登录
     * @param  user
     * @return Type Observable<HttpResponse>
     * */
    @POST(HttpUrls.LOGOUT_URL)
    Observable<HttpResponse<String>> logout();
//    Call<HttpResponse<String>> logout(@Body User user);




    /***
     * 退出登录
     * @param  username
     * @return Type Observable<HttpResponse>
     * */
    @FormUrlEncoded
    @POST(HttpUrls.CHECK_USERNAME_URL)
    Observable<HttpResponse<String>> checkUsername(@Field("username") String username);



//    @FormUrlEncoded
    @GET(HttpUrls.MY_QUERY_USER_URL)
    Observable<HttpResponse<PersonalBean>> queryUser(@Query("userId") String userId);

    @FormUrlEncoded
    @POST(HttpUrls.MY_UPDATE_USER_URL)
    Observable<HttpResponse<String>> updateUser(@Field("userId") String userId,@Field("updateStr") String updateStr,@Field("operateFlag") String operateFlag);

    @GET(HttpUrls.MY_CHECK_PSD_URL)
    Observable<HttpResponse<String>> checkPsd(@Query("userId") String userId,@Query("password") String password);


    @GET(HttpUrls.ACCOUNT_MANAGE_LIST_URL)
    Observable<HttpResponse<List<User>>> accountList(@Query("userId") String userId,
                                                     @Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize);


    @FormUrlEncoded
    @POST(HttpUrls.PER_MESSAGE_TYPE_URL)
    Observable<HttpResponse<List<MessageSet>>> queryMessageType(@Field("userId") String userId);


    @FormUrlEncoded
    @POST(HttpUrls.PER_MESSAGE_LIST_URL)
    Observable<HttpResponse<MessageBean>> queryMessageList(@Field("userId") String userId, @Field("pageNumber") Integer pageNumber,
                                                           @Field("pageSize") Integer pageSize, @Field("messageType") String messageType);
    @FormUrlEncoded
    @POST(HttpUrls.MESSAGE_STATUS_URL)
    Observable<HttpResponse<String>> doStatus(@Field("userId") String userId, @Field("messageType") String messageType,@Field("status") String status );


    @GET(HttpUrls.DO_MESSAGE_STATUS_URL)
    Observable<HttpResponse<MessageSet>> doMessageStatus(@Query("id") String id,@Query("userId") String userId);



    @FormUrlEncoded
    @POST(HttpUrls.ACCOUNT_MANAGE_ROLE_URL)
    Observable<HttpResponse<List<Role>>> listType(@Field("type") Integer type);


    @GET(HttpUrls.ACCOUNT_MANAGE_STORE_URL)
    Observable<HttpResponse<List<Store>>> listTreeByCurrentUser(@Query("page") String page, @Query("limit") String limit, @Query("userId") String userId );


    @FormUrlEncoded
    @POST(HttpUrls.ACCOUNT_MANAGE_INSERT_USER_URL)
    Observable<HttpResponse<String>> insertCateingUser(@Field("username") String username, @Field("nickname") String nickname,
                                                                    @Field("password") String password, @Field("roleIds") String roleIds,@Field("storeIds") String storeIds,@Field("authFlag") String authFlag);

    @FormUrlEncoded
    @POST(HttpUrls.ACCOUNT_MANAGE_EDIT_USER_URL)
    Observable<HttpResponse<String>> editCateingUser(@Field("id") String id, @Field("username") String username,@Field("nickname") String nickname,
                                                       @Field("password") String password, @Field("roleIds") String roleIds,@Field("storeIds") String storeIds,@Field("authFlag") String authFlag);


    @FormUrlEncoded
    @POST(HttpUrls.ACCOUNT_MANAGE_CHECK_USERNAME_URL)
    Observable<HttpResponse<String>> checkUserNameRepeat(@Field("username") String username,@Field("id") String id);


    @FormUrlEncoded
    @POST(HttpUrls.ACCOUNT_MANAGE_ROLE_LIST_URL)
    Observable<HttpResponse<List<Resource>>> resourceTreeListByRoleId(@Field("ids") String ids);



    @GET(HttpUrls.FEED_LIST)
    Observable<HttpResponse<List<Feedback>>> doFeedList(@Query("userId") String userId,
                                                        @Query("pageSize") Integer pageSize,@Query("pageNumber") Integer pageNumber);

    @GET(HttpUrls.FEED_QUERY)
    Observable<HttpResponse<Feedback>> doFeedQuery(@Query("feedbackId") String feedbackId);

    @FormUrlEncoded
    @POST(HttpUrls.FEED_SAVE)
    Observable<HttpResponse<String>> doFeedSave(@Field("type") String type, @Field("content") String content,
                                                     @Field("images") String images, @Field("createrTel") String createrTel,@Field("userId") String userId);


    @GET(HttpUrls.FEED_UN_READ)
    Observable<HttpResponse<String>> doFeedFlag(@Query("userId") String userId);

    @GET(HttpUrls.DOWNLOAD_VERSION)
    Observable<HttpResponse<MobileVersion>> doDownloadVersion(@Query("id") String id);
}
