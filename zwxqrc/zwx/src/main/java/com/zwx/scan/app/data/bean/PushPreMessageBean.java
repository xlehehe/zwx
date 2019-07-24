package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/03/28
 * desc   : 推送配置消息 预览 实体类
 * version: 1.0
 **/
public class PushPreMessageBean implements Serializable {

    //活动消息
    private String  banner;
    private String  shareTitle;
    private String  shareDesc;


    //拼团或会员卡消息
    private String  title;
    private String  first;
    private String  keyword1_title;

    private String  keyword1;
    private String  keyword2_title;
    private String  keyword2;

    private String  campcoupId;
    private String  keyword3_title;
    private String  keyword3;
    private String  keyword4_title;
    private String  keyword4;
    private String  remark;


    private String  pushType;
    private String   pushContentId;

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getPushContentId() {
        return pushContentId;
    }

    public void setPushContentId(String pushContentId) {
        this.pushContentId = pushContentId;
    }

    //会员卡消息
  /*  private String  title;
    private String  first;
    private String  keyword1_title;

    private String  keyword1;
    private String  keyword2_title;
    private String  keyword2;

    private String  campcoupId;
    private String  keyword3_title;
    private String  keyword3;
    private String  remark;*/

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getKeyword1_title() {
        return keyword1_title;
    }

    public void setKeyword1_title(String keyword1_title) {
        this.keyword1_title = keyword1_title;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2_title() {
        return keyword2_title;
    }

    public void setKeyword2_title(String keyword2_title) {
        this.keyword2_title = keyword2_title;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getCampcoupId() {
        return campcoupId;
    }

    public void setCampcoupId(String campcoupId) {
        this.campcoupId = campcoupId;
    }

    public String getKeyword3_title() {
        return keyword3_title;
    }

    public void setKeyword3_title(String keyword3_title) {
        this.keyword3_title = keyword3_title;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getKeyword4_title() {
        return keyword4_title;
    }

    public void setKeyword4_title(String keyword4_title) {
        this.keyword4_title = keyword4_title;
    }

    public String getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
