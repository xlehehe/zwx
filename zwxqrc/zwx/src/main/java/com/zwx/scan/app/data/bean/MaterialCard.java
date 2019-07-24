package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 会员卡素材
 * @author 史俊杰
 * @Title: CardMaterial.java
 * @Description: 
 * @date:2019年4月11日 下午5:07:12
 */
public class MaterialCard implements Serializable {

    private static final long serialVersionUID = -8194395073700675632L;
    
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
     * 所属公司id
     */
    private Long compId;
     
    /**
     * 所属公司
     */
    private Company company;

    private Boolean isChecked;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
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

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

}
