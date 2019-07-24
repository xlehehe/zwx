package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 优惠券素材
 * @author HanJian
 * @date 2018年8月29日
 */
public class CouponMaterial implements Serializable {
    
    private static final long serialVersionUID = 5153132360656539805L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 素材名称
     */
    private String name;
    
    /**
     * 优惠券类型：CPC代金券，CPD折扣券，CPO礼品券，CPU菜品券、CPJ插队券、CPT其他
     */
    private String type;
    
    /**
     * 默认的素材
     */
    private boolean def;
    
    /**
     * 背景图片ID
     */
    private String background;
    
    /**
     * 背景图片缩略图ID
     */
    private String backgroundThumbnail;
    
    /**
     * 所属公司
     */
    private Company company;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getBackgroundThumbnail() {
        return backgroundThumbnail;
    }

    public void setBackgroundThumbnail(String backgroundThumbnail) {
        this.backgroundThumbnail = backgroundThumbnail;
    }
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
    
}
