package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * t_material_game
 * @yanshoufu 
 */
public class MaterialGame implements Serializable {
    private Long id;

    private String name;

    private Long compId;

    private String background;

    private String backgroundThumb;

    private String banner;

    private String bannerThumb;

    private String wxLinkIcon;

    /**
     * 活动列表图片id
     */
    private String campaignListPic;

    /**
     * 中奖图片id,多个以“-”分隔
     */
    private String campaignWinPic;

    /**
     * 未中奖图片id,多个以“-”分隔
     */
    private String campaignNonrewardPic;

    /**
     * 转发标题
     */
    private String shareTitle;

    /**
     * 转发描述
     */
    private String shareDesc;
    
    private Object win;
    
    private Object non;
    
    

    private Boolean isChecked;

    private String miniLinkIcon;
    private String templateType;

    public String getMiniLinkIcon() {
        return miniLinkIcon;
    }

    public void setMiniLinkIcon(String miniLinkIcon) {
        this.miniLinkIcon = miniLinkIcon;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Object getWin() {
        return win;
    }

    public void setWin(Object win) {
        this.win = win;
    }

    public Object getNon() {
        return non;
    }

    public void setNon(Object non) {
        this.non = non;
    }

    /*
    public String getWin() {
        if(win != null && !"".equals(win)){
            return (String)JSON.toJSON(win);
        }
        return "";
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getNon() {
        if(non != null && !"".equals(non)){
            return (String)JSON.toJSON(non);
        }
        return "";
    }

    public void setNon(String non) {
        this.non = non;
    }*/

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackgroundThumb() {
        return backgroundThumb;
    }

    public void setBackgroundThumb(String backgroundThumb) {
        this.backgroundThumb = backgroundThumb;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBannerThumb() {
        return bannerThumb;
    }

    public void setBannerThumb(String bannerThumb) {
        this.bannerThumb = bannerThumb;
    }

    public String getWxLinkIcon() {
        return wxLinkIcon;
    }

    public void setWxLinkIcon(String wxLinkIcon) {
        this.wxLinkIcon = wxLinkIcon;
    }

    public String getCampaignListPic() {
        return campaignListPic;
    }

    public void setCampaignListPic(String campaignListPic) {
        this.campaignListPic = campaignListPic;
    }

    public String getCampaignWinPic() {
        return campaignWinPic;
    }

    public void setCampaignWinPic(String campaignWinPic) {
        this.campaignWinPic = campaignWinPic;
    }

    public String getCampaignNonrewardPic() {
        return campaignNonrewardPic;
    }

    public void setCampaignNonrewardPic(String campaignNonrewardPic) {
        this.campaignNonrewardPic = campaignNonrewardPic;
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
}