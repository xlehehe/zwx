package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 用户实体类
 * @author HanJian
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7075503769141001146L;

    /**
     * ID
     */
    private Long id;

    private Long userId;
	
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    
    /**
     * 密码盐
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 姓名
     */
    private String realname;
    
    /**
     * 状态 - 1:激活，0：禁用
     */
    private Long status;
    
    /**
     * 用户类型 - 1系统，2餐企，3经销商，4账务，5客服
     */
    private Long type;
    
    /**
     * 角色名称，不保存到数据库
     */
    private String roleNames;
    
    /**
     * 店铺名称，不保存到数据库
     */
    private String storeNames;
    
    
    private String token;


    private String roleIds;
    private String storeIds;
    private String authFlag;

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getStoreNames() {
        return storeNames;
    }

    public void setStoreNames(String storeNames) {
        this.storeNames = storeNames;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
