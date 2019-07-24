package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * t_product_extend_desc   商品详情 图文混排
 * @yanshoufu 
 */
public class ProductExtendDesc implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品详情内容
     */
    private String productContent;

    /**
     * 媒体类型 1为图片 2为文字
     */
    private String mediaType;

    /**
     * 上传的文字和图片的顺序
     */
    private int mediaSort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

	/**
	 * @return the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the mediaSort
	 */
	public int getMediaSort() {
		return mediaSort;
	}

	/**
	 * @param mediaSort the mediaSort to set
	 */
	public void setMediaSort(int mediaSort) {
		this.mediaSort = mediaSort;
	}

    
}