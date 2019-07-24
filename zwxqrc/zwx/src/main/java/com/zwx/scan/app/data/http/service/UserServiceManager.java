package com.zwx.scan.app.data.http.service;

import android.content.Context;

import com.zwx.scan.app.data.base.BaseServiceManager;
import com.zwx.scan.app.data.bean.MobileVersion;
import com.zwx.scan.app.data.bean.Resource;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TokenVali;
import com.zwx.scan.app.data.bean.HttpResponse;
import com.zwx.scan.app.data.bean.Toke;
import com.zwx.scan.app.data.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * author : lizhilong
 * time   : 2018/10/15
 * desc   :
 * version: 1.0
 **/
public class UserServiceManager  extends BaseServiceManager{

    private Context context;

    private UserService userService;


    public UserServiceManager(){
        userService = RetrofitServiceManager.getInstance().create(UserService.class);
    }

    /**
     *  desc用户登录
     * @param username
     * @param password
     * @return
     */
     /*public Observable<HttpResponse<LoginResult>> login(String username, String password){
         return  observe(userService.login(username,password));
     }*/
    public Observable<HttpResponse<User>> login(String username, String password){
        return  observe(userService.login(username,password));
    }


    public Observable<HttpResponse<String>> logout(User user){
        return  observe(userService.logout());
    }
    /**
     * desc     token认证
     * @param id
     * @return Observable
     */
   /* public Observable<HttpResponse<LoginResult>> tokenAuth(String id){
        return  observe(userService.tokenAuth(id));
    }*/
    public Observable<HttpResponse<TokenVali>> tokenAuth(String id){
        return  observe(userService.tokenAuth(id));
    }



    public Call<HttpResponse<Toke>> refreshToken(String username, String password){
        return userService.refreshToken(username,password);
    }



    public Observable<HttpResponse<String>> checkUsername(String username){
        return  observe(userService.checkUsername(username));
    }

    public Observable<HttpResponse<List<User>>> accountList(String userId, Integer pageNumber, Integer pageSize){
        return  observe(userService.accountList(userId,pageNumber,pageSize));
    }

    public Observable<HttpResponse<List<Store>>> listTreeByCurrentUser(String page, String limit, String userId){
        return  observe(userService.listTreeByCurrentUser(page,limit,userId));
    }


    public Observable<HttpResponse<List<Role>>> listType(Integer type){
        return  observe(userService.listType(type));
    }

    public Observable<HttpResponse<String>> checkUserNameRepeat(String username,String id){
        return  observe(userService.checkUserNameRepeat(username,id));
    }


    public Observable<HttpResponse<String>> insertCateingUser(String username,String nickname,String password,String roleIds,String storeIds,String authFlag){
        return  observe(userService.insertCateingUser(username,nickname,password,roleIds,storeIds,authFlag));
    }

    public Observable<HttpResponse<String>> editCateingUser(String id,String username,String nickname,String password,String roleIds,String storeIds,String authFlag){
        return  observe(userService.editCateingUser(id, username,nickname,password,roleIds,storeIds,authFlag));
    }


    public Observable<HttpResponse<List<Resource>>> resourceTreeListByRoleId(String id){
        return  observe(userService.resourceTreeListByRoleId(id));
    }

    public Observable<HttpResponse<MobileVersion>> doDownloadVersion(String id){
        return  observe(userService.doDownloadVersion(id));
    }
}
