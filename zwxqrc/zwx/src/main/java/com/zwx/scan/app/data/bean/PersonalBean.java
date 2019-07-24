package com.zwx.scan.app.data.bean;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/14
 * desc   :
 * version: 1.0
 **/
public class PersonalBean {


    private String userId;
    private String userName;
    private String nickName;


    private List<Role> roleList;

    private List<Store> storeList;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }




}
