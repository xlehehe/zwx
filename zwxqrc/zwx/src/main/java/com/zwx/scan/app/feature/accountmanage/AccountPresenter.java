package com.zwx.scan.app.feature.accountmanage;

import android.content.Context;
import android.view.View;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.base.BaseObserver;
import com.zwx.scan.app.data.bean.Resource;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.service.RetrofitServiceManager;
import com.zwx.scan.app.data.http.service.UserServiceManager;
import com.zwx.scan.app.feature.campaign.CampaignListAdatper;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.personal.PasswordManageActivity;
import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.widget.treeview.adapter.SimpleTreeListViewAdapter;
import com.zwx.scan.app.widget.treeview.utils.Node;
import com.zwx.scan.app.widget.treeview.utils.adapter.TreeListViewAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author : lizhilong
 * time   : 2018/08/31
 * desc   : 登录业务逻辑
 * version: 1.0
 **/

public class AccountPresenter implements AccountContract.Presenter{

    private final AccountContract.View accountView;
    private UserServiceManager userServiceManager;

    //添加订阅，应用于取消订阅，防止造成内存泄漏
    private CompositeDisposable disposable;
    LoginActivity activity;
    @Inject
    public AccountPresenter(AccountContract.View view) {
        this.accountView = view;
        userServiceManager = new UserServiceManager();
        this.disposable = new CompositeDisposable();


    }

    @Override
    public void accountList(Context context, String userId, Integer pageNumber, Integer pageSize,boolean isRefresh) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("pageNumber",String.valueOf(pageNumber));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountActivity activity =  (AccountActivity)context;

        userServiceManager.accountList(userId,pageNumber,pageSize)
                .subscribe(new BaseObserver<List<User>>(context,false){
                    @Override
                    public void onSuccess(List<User> userList,String msg) {

                       /* if(userList != null && userList.size()>0){



                        }else {
                            activity.ptr.setVisibility(View.GONE);
                            activity.llNoData.setVisibility(View.VISIBLE);
                        }*/

                       if(userList != null && userList.size()>0){
                           if (isRefresh) {
                               activity.userList.clear();
                               activity.userList.addAll(userList);
                               activity.listAdapter = new AccountListAdapter(activity, activity.userList);
                               activity.ptr.refreshComplete();
                               activity.mAdapter.notifyDataSetChanged();
                               if (userList.size() < 10) {
                                   activity.ptr.setLoadMoreEnable(false);
                               } else {
                                   activity.ptr.setLoadMoreEnable(true);
                               }

                           } else {
                               activity.userList.addAll(userList);
                               activity.listAdapter = new AccountListAdapter(activity, activity.userList);
                               activity.mAdapter.notifyDataSetChanged();
                               activity.ptr.refreshComplete();
                               if (userList.size() < 10) {
                                   activity.ptr.setLoadMoreEnable(false);
//                                    activity.ptr.loadMoreComplete(true);
                               } else {
                                   activity.ptr.loadMoreComplete(true);
                               }
                           }
//                           activity.ll_no_data.setVisibility(View.GONE);
//                           activity.rvList.setVisibility(View.VISIBLE);
                       }else {
//                           activity.ll_no_data.setVisibility(View.VISIBLE);
//                           activity.rvList.setVisibility(View.GONE);
//                           activity.iv_no_data.setImageResource(R.drawable.ic_no_data_tip);
//                           activity.tv_no_data.setText("暂无账号数据");
                           ToastUtils.showShort("暂无账号数据");
                       }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        activity.mAdapter.notifyDataSetChanged();
                        activity.ptr.refreshComplete();
                        activity.ptr.setLoadMoreEnable(false);
                        ToastUtils.showShort(message);
                    /*    activity.ll_no_data.setVisibility(View.VISIBLE);
                        activity.rvList.setVisibility(View.GONE);
                        activity.iv_no_data.setImageResource(R.drawable.ic_wifi_not);
                        activity.tv_no_data.setText("信号不好");
*/
                    }
                });

    }

    //角色列表
    @Override
    public void listByType(Context context, Integer type) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("type",String.valueOf(type));
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountNewActivity activity =  (AccountNewActivity)context;

        userServiceManager.listType(type)
                .subscribe(new BaseObserver<List<Role>>(context,true){
                    @Override
                    public void onSuccess(List<Role> roleList,String msg) {

                        if(roleList != null && roleList.size()>0){
                            activity.roleList = roleList;
//                            activity.roleListAdapter.notifyDataSetChanged();

                            if("EDIT".equals(activity.isNew)){
                                if(activity.rolesIds != null && !"".equals(activity.rolesIds)){

                                    if(activity.rolesIds.contains(",")){
                                        String[] roleIDs = activity.rolesIds.split(",");

                                        for (int i = 0;i <roleIDs.length;i++){

                                            for (Role role : activity.roleList){
                                                if(role != null){

                                                    String roleId = String.valueOf(role.getId());
                                                    if(roleId.equals(roleIDs[i])){
                                                        role.setChecked(true);
                                                    }
                                                }
                                            }
                                        }
                                    }else {
                                        for (Role role : activity.roleList){
                                            if(role != null){
                                                String roleId = String.valueOf(role.getId());
                                                if(roleId.equals(activity.rolesIds)){
                                                    role.setChecked(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            activity.roleListAdapter = new AccountRoleListAdapter(context,roleList);
                            activity.lvRole.setAdapter(activity.roleListAdapter);
                        }


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    //店铺列表
    @Override
    public void listTreeByCurrentUser(Context context, String page, String limit, String userId) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("userId",userId);
        params.put("page",page);
        params.put("limit", limit);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountNewActivity activity =  (AccountNewActivity)context;

        userServiceManager.listTreeByCurrentUser(page,limit,userId)
                .subscribe(new BaseObserver<List<Store>>(context,true){
                    @Override
                    public void onSuccess(List<Store> storeList,String msg) {


                        if(storeList != null && storeList.size()>0){

                           /* if("NEW".equals(activity.isNew)){
                            }else {
                                if(activity.storeIds != null && !"".equals(activity.storeIds)){
                                }
                            }*/
                            activity.storeList = storeList;

                            if("NEW".equals(activity.isNew)){
                            }else {
                                if(activity.storeIds != null && !"".equals(activity.storeIds)){

                                    if(activity.storeIds.contains(",")){
                                        String[] storeIDs = activity.storeIds.split(",");

                                        for (int i = 0;i <storeIDs.length;i++){

                                            for (Store store : activity.storeList ){
                                                if(store != null){

                                                    String storeId = String.valueOf(store.getId());
                                                    if(storeId.equals(storeIDs[i])){
                                                        store.setChecked(true);
                                                    }
                                                }
                                            }
                                        }
                                    }else {
                                        for (Store store : activity.storeList ){
                                            if(store != null){
                                                String storeId = String.valueOf(store.getId());
                                                if(storeId.equals(activity.storeIds)){
                                                    store.setChecked(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            activity.storeListAdapter = new AccountStoreListAdapter(context,storeList);
                            activity.lvStore.setAdapter(activity.storeListAdapter);

                        }

                        int type = 2;  //表示餐企
                        listByType(context,type);

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);

                    }
                });
    }

    @Override
    public void checkUserNameRepeat(Context context, String username, String id) {

        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("username",username);
        params.put("id",id);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountNewActivity activity =  (AccountNewActivity)context;

        userServiceManager.checkUserNameRepeat(username,id)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                        ToastUtils.showShort("您输入的账号已被使用，请重新输入！");
                    }
                });
    }

    @Override
    public void insertCateingUser(Context context, String nickname, String username, String password, String roleIds, String storeIds,String authFlag) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("username",username);
        params.put("nickname",nickname);
        params.put("password",password);
        params.put("roleIds",roleIds);
        params.put("storeIds",storeIds);
        params.put("authFlag",authFlag);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountNewActivity activity =  (AccountNewActivity)context;

        userServiceManager.insertCateingUser(username,nickname,password,roleIds,storeIds,authFlag)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {


                        ActivityUtils.startActivity(AccountActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                        ToastUtils.showCustomShortBottom("保存成功");

                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void editCateingUser(Context context, String id,String username, String nickname, String password, String roleIds, String storeIds,String authFlag) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("id",id);
        params.put("username",username);
        params.put("nickname",nickname);
        params.put("password", password);
        params.put("roleIds", roleIds);
        params.put("storeIds", storeIds);
        params.put("token", token);
        params.put("authFlag", authFlag);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountNewActivity activity =  (AccountNewActivity)context;

        userServiceManager.editCateingUser(id,username,nickname,password,roleIds,storeIds,authFlag)
                .subscribe(new BaseObserver<String>(context,false){
                    @Override
                    public void onSuccess(String result,String msg) {


                        ActivityUtils.startActivity(AccountActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                        ToastUtils.showCustomShortBottom("编辑成功");


                    }

                    @Override
                    public void onFailure(int code, String message) {
                        super.onFailure(code, message);
                    }
                });
    }

    @Override
    public void resourceTreeListByRoleId(Context context, String id) {
        Map<String, String> params = new HashMap<String, String>();
        String token = SPUtils.getInstance().getString("token");
        String timestamp=String.valueOf(new Date().getTime());

        params.put("ids",id);
        params.put("token", token);
        params.put("timestamp", timestamp);
        String signature = EncryptUtils.createSign(params);
        params=new HashMap<>();
        params.put("token",token);
        params.put("timestamp",timestamp);
        params.put("signature",signature);
        RetrofitServiceManager.setHeadTokenMap(params);
        AccountRoleAuthActivity activity =  (AccountRoleAuthActivity)context;

        userServiceManager.resourceTreeListByRoleId(id)
                .subscribe(new BaseObserver<List<Resource>>(context,false){
                    @Override
                    public void onSuccess(List<Resource> resourceList,String msg) {



                        if(resourceList != null && resourceList.size()>0){
                            activity.resourceList = resourceList;
                            for (Resource resource1 :activity.resourceList){
                                Long id =resource1.getId() ;
                                Long pId =resource1.getPid() ;
                                String label =resource1.getName();
                                int type = resource1.getType();
                                Node bean = new Node(id, pId, label,type);
                                activity.mDatas.add(bean);

                            }

                            try {
                                activity.mAdapter = new SimpleTreeListViewAdapter(context, activity.lvType, activity.mDatas, 1);
                                activity.lvType.setAdapter(activity.mAdapter);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            activity.mAdapter.setOnTreeNodeClickLitener(new TreeListViewAdapter.OnTreeNodeClickListener() {

                                @Override
                                public void onClick(Node node, int position) {
                                    if (node.isLeaf()) {
//                                        ToastUtils.showShort(node.getName());
                                    }
                                }
                            });
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
