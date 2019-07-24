package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 海报素材
 * @author HanJian
 * @date 2018年8月29日
 */
public class PosterMaterial implements Serializable {
    
    private static final long serialVersionUID = 5153132360656539805L;


    private  boolean isChecked;
    /**
     * ID
     */
    private Long id;
    
    /**
     * 素材名称
     */
    private String name;
    
    /**
     * 背景图片ID
     */
    private String background;
    
    /**
     * 背景图片缩略图ID
     */
    private String backgroundThumbnail;
    
    /**
     * 广告图片ID
     */
    private String banner;
    
    /**
     * 广告图片缩略图ID
     */
    private String bannerThumbnail;
    
    /**
     * 微信图标 图片ID
     */
    private String wechatIcon;
    
    /**
     * 微信图标缩略图 图片ID
     */
    private String wechatIconThumbnail;
    
    /**
     * 公司
     */
    private Long compId;
    /**
     * 品牌
     */
    private Long brandId;
    
    /**
     * 所属品牌
     */
    private Brand brand;
    
    /**
     * 所属公司
     */
    private Company company;

    private String miniLinkIcon;


    public String getMiniLinkIcon() {
        return miniLinkIcon;
    }

    public void setMiniLinkIcon(String miniLinkIcon) {
        this.miniLinkIcon = miniLinkIcon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackgroundThumbnail() {
        return backgroundThumbnail;
    }

    public void setBackgroundThumbnail(String backgroundThumbnail) {
        this.backgroundThumbnail = backgroundThumbnail;
    }

    public String getBannerThumbnail() {
        return bannerThumbnail;
    }

    public void setBannerThumbnail(String bannerThumbnail) {
        this.bannerThumbnail = bannerThumbnail;
    }


	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

    public String getWechatIcon() {
        return wechatIcon;
    }

    public void setWechatIcon(String wechatIcon) {
        this.wechatIcon = wechatIcon;
    }

    public String getWechatIconThumbnail() {
        return wechatIconThumbnail;
    }

    public void setWechatIconThumbnail(String wechatIconThumbnail) {
        this.wechatIconThumbnail = wechatIconThumbnail;
    }
	

}
