package com.zwx.instalment.app.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * author : lizhilong
 * time   : 2019/07/04
 * desc   :  用户实体
 * version: 1.0
 **/
public class User {
    @SerializedName("id")
    private long id;

    @SerializedName("token")
    private String token;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("nickname")
    private String nickname;

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNickname() {
        return nickname;
    }
}
