package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 商品素材表t_product_media
 */
public class ProductMedia implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    private Long compId;

    /**
     * product_set表主键
     */
    private Long productId;

    /**
     * 商品主图缩略图文件id
     */
    private String thumbnailUrl;

    /**
     * 商品图片id
     */
    private String imgUrl;

    /**
     * 视频文件id
     */
//    private String videoUrl;

    /**
     * 序号
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
/*
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }*/

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}