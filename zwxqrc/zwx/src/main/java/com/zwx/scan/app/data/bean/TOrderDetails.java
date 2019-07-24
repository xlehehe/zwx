package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TOrderDetails implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 明细Id
     */
    private Long detailedId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * t_product_set.product_code
     */
    private String productCode;

    /**
     * 商品分类ID
     */
    private Long catId;

    /**
     * 分类名称
     */
    private String catName;

    /**
     * 销售金额（eg: ￥39+200积分)
     */
    private String factSalesPrice;

    /**
     * 购买数量
     */
    private Long buyCount;

    /**
     * 0: 未兑换，1：已兑换
     */
    private String status;
    
    /**
     * 积分
     */
    private Integer unitScore;
    
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    
    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 商品类别
     */
    private String salesType;
    
    /**
     * 公司id
     */
    private Long compId;
    
    /**
     * 商品规格
     */
    private String productSpec;
    
    /**
     * 商品缩略图
     */
    private String thumbnailUrl;
    
    /**
     * 获得红包金额
     */
    private BigDecimal getRedEnvelope;
    
    /**
     * 使用红包金额
     */
    private BigDecimal useRedEnvelope;
    
    /**
     * 商品市场价
     */
    private BigDecimal marketPrice;
    
    /**邮寄运费
     */
    private BigDecimal deliveryFee;
    
    /**
     * 商品详情
     */
    private String productDetail;
    
    
    
    /**
	 * @return the productDetail
	 */
	public String getProductDetail() {
		return productDetail;
	}

	/**
	 * @param productDetail the productDetail to set
	 */
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	/**
	 * @return the deliveryFee
	 */
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
	 * @return the getRedEnvelope
	 */
	public BigDecimal getGetRedEnvelope() {
		return getRedEnvelope;
	}

	/**
	 * @param getRedEnvelope the getRedEnvelope to set
	 */
	public void setGetRedEnvelope(BigDecimal getRedEnvelope) {
		this.getRedEnvelope = getRedEnvelope;
	}

	/**
	 * @return the useRedEnvelope
	 */
	public BigDecimal getUseRedEnvelope() {
		return useRedEnvelope;
	}

	/**
	 * @param useRedEnvelope the useRedEnvelope to set
	 */
	public void setUseRedEnvelope(BigDecimal useRedEnvelope) {
		this.useRedEnvelope = useRedEnvelope;
	}

	/**
     * count
     */
    private Integer cou;
    
    
    public Integer getCou() {
        return cou;
    }

    public void setCou(Integer cou) {
        this.cou = cou;
    }

    /**
	 * @return the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * @param thumbnailUrl the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * @return the salesType
	 */
	public String getSalesType() {
		return salesType;
	}

	/**
	 * @param salesType the salesType to set
	 */
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	/**
	 * @return the compId
	 */
	public Long getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(Long compId) {
		this.compId = compId;
	}

	/**
	 * @return the productSpec
	 */
	public String getProductSpec() {
		return productSpec;
	}

	/**
	 * @param productSpec the productSpec to set
	 */
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	/**
	 * @return the unitScore
	 */
	public Integer getUnitScore() {
		return unitScore;
	}

	/**
	 * @param unitScore the unitScore to set
	 */
	public void setUnitScore(Integer unitScore) {
		this.unitScore = unitScore;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}



	/**
	 * @return the payWay
	 */
	public String getPayWay() {
		return payWay;
	}

	/**
	 * @param payWay the payWay to set
	 */
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Long getDetailedId() {
        return detailedId;
    }

    public void setDetailedId(Long detailedId) {
        this.detailedId = detailedId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getFactSalesPrice() {
        return factSalesPrice;
    }

    public void setFactSalesPrice(String factSalesPrice) {
        this.factSalesPrice = factSalesPrice == null ? null : factSalesPrice.trim();
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


}