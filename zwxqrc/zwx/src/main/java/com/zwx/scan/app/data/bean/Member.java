package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员表对应类
 * 
 * @author DongWei
 * @date 2018年8月27日
 */
public class Member implements Serializable {


    /**
     * 会员cod
     */
    private Long memberId;



    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员手机
     */
    private String memberTel;

    /**
     * 性别 男——1 女——0
     */
    private Integer sexType;

    /**
     * 生日
     */
    private String brirthday;



    /**
     * 加入平台时间
     */
    private String joinSysTime;



    /**
     * 最近一次修改人员
     */
    private String modifyUser;



    /**
     * 会员昵称
     */
    private String nickName;




    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public Integer getSexType() {
        return sexType;
    }

    public void setSexType(Integer sexType) {
        this.sexType = sexType;
    }

    public String getBrirthday() {
        return brirthday;
    }

    public void setBrirthday(String brirthday) {
        this.brirthday = brirthday;
    }

    public String getJoinSysTime() {
        return joinSysTime;
    }

    public void setJoinSysTime(String joinSysTime) {
        this.joinSysTime = joinSysTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}