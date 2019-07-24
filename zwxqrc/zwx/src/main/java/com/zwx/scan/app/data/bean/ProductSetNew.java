package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 */
public class ProductSetNew implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 企业Id
     */
    private Long compId;

    /**
     * 营销类型常规出售商品——r促销商品——p      p 热卖 r取消热卖
     */
    private String marketingType;

    /**
     * 销售货品的id 会员卡id,优惠券id等
     */
    private Long sellGoodsId;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 出售商品类型 会员卡——m优惠券——c
     */
    private String salesType;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 上架时间
     */
    private String groundingTime;

    /**
     * 销售金额(单价)
     */
    private BigDecimal unitPrice;

    /**
     * 立减金额
     */
    private BigDecimal verticalReduction;

    /**
     * 下架时间
     */
    private String ungroundingTime;

    /**
     * 上架数量商品数量
     */
    private Long groundingCount;

    /**
     * 剩余库存
     */
    private Long surplusStock;

    /**
     * 状态 未上架——n 已下架——o 销售中——s 不再出售——c
     *
      */
    private String state;

    /**
     * 是否限制消费者最大购买数量 限制——y 不限制——n
     */
    private String buyMaxLimit;

    /**
     * 消费者最大购买数量
     */
    private Long buyMaxCount;

    /**
     * 会员卡类型名称 
     */
    private String memtypeName;
    
    /**
     * 统计  数据库中不存在的字段
     */
    private Integer count;
    
    /**
     * 支付方式  cash-现金，point-积分，cp-现金+积分
     */
    private String payWay;
    
    /**
     * 商品分类
     */
    private Long catId;
    
    /**
     * 积分
     */
    private Integer unitScore;
    
    /**
     * 冻结库存
     */
    private Long frozenStock;
    
    /**
     * 状态名称 
     */
    private String stateName;
    
    /**
     * 商品分类名称
     */
    private String catName;
    
    /**
     * 缩略图
     */
    private String thumbnailUrl;
    
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    
    /**
     * 配送方式
     */
    private String  deliveryType;
    
    /**
     * 是否返利 1是 0否
     */
    private String rebateFlag;
    
    /**
     * 返利金额
     */
    private BigDecimal rebateAmt;


    /**
	 * @return the rebateFlag
	 */
	public String getRebateFlag() {
		return rebateFlag;
	}

	/**
	 * @param rebateFlag the rebateFlag to set
	 */
	public void setRebateFlag(String rebateFlag) {
		this.rebateFlag = rebateFlag;
	}

	/**
	 * @return the rebateAmt
	 */
	public BigDecimal getRebateAmt() {
		return rebateAmt;
	}

	/**
	 * @param rebateAmt the rebateAmt to set
	 */
	public void setRebateAmt(BigDecimal rebateAmt) {
		this.rebateAmt = rebateAmt;
	}

	/**
	 *  the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 *  deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 *  the marketPrice
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 *  marketPrice the marketPrice to set
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 *  the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 *  thumbnailUrl the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 *  the catName
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 *  catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	 *  the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 *  stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	

	/**
	 *  the catId
	 */
	public Long getCatId() {
		return catId;
	}

	/**
	 *  catId the catId to set
	 */
	public void setCatId(Long catId) {
		this.catId = catId;
	}

	/**
	 *  the unitScore
	 */
	public Integer getUnitScore() {
		return unitScore;
	}

	/**
	 *  unitScore the unitScore to set
	 */
	public void setUnitScore(Integer unitScore) {
		this.unitScore = unitScore;
	}



	/**
	 *  the frozenStock
	 */
	public Long getFrozenStock() {
		return frozenStock;
	}

	/**
	 *  frozenStock the frozenStock to set
	 */
	public void setFrozenStock(Long frozenStock) {
		this.frozenStock = frozenStock;
	}

	/**
	 *  the payWay
	 */
	public String getPayWay() {
		return payWay;
	}

	/**
	 *  payWay the payWay to set
	 */
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMemtypeName() {
        return memtypeName;
    }

    public void setMemtypeName(String memtypeName) {
        this.memtypeName = memtypeName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(String marketingType) {
        this.marketingType = marketingType == null ? null : marketingType.trim();
    }

    public Long getSellGoodsId() {
        return sellGoodsId;
    }

    public void setSellGoodsId(Long sellGoodsId) {
        this.sellGoodsId = sellGoodsId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType == null ? null : salesType.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }


    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getVerticalReduction() {
        return verticalReduction;
    }

    public void setVerticalReduction(BigDecimal verticalReduction) {
        this.verticalReduction = verticalReduction;
    }

    public String getGroundingTime() {
        return groundingTime;
    }

    public void setGroundingTime(String groundingTime) {
        this.groundingTime = groundingTime;
    }

    public String getUngroundingTime() {
        return ungroundingTime;
    }

    public void setUngroundingTime(String ungroundingTime) {
        this.ungroundingTime = ungroundingTime;
    }

    public Long getGroundingCount() {
        return groundingCount;
    }

    public void setGroundingCount(Long groundingCount) {
        this.groundingCount = groundingCount;
    }

    public Long getSurplusStock() {
        return surplusStock;
    }

    public void setSurplusStock(Long surplusStock) {
        this.surplusStock = surplusStock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getBuyMaxLimit() {
        return buyMaxLimit;
    }

    public void setBuyMaxLimit(String buyMaxLimit) {
        this.buyMaxLimit = buyMaxLimit == null ? null : buyMaxLimit.trim();
    }

    public Long getBuyMaxCount() {
        return buyMaxCount;
    }

    public void setBuyMaxCount(Long buyMaxCount) {
        this.buyMaxCount = buyMaxCount;
    }

	@Override
	public String toString() {
		return "ProductSetNew [productId=" + productId + ", compId=" + compId + ", marketingType=" + marketingType
				+ ", sellGoodsId=" + sellGoodsId + ", productCode=" + productCode + ", salesType=" + salesType
				+ ", productName=" + productName + ", groundingTime=" + groundingTime + ", unitPrice=" + unitPrice
				+ ", verticalReduction=" + verticalReduction + ", ungroundingTime=" + ungroundingTime
				+ ", groundingCount=" + groundingCount + ", surplusStock=" + surplusStock + ", state=" + state
				+ ", buyMaxLimit=" + buyMaxLimit + ", buyMaxCount=" + buyMaxCount + ", memtypeName=" + memtypeName
				+ ", count=" + count + ", payWay=" + payWay + ", catId=" + catId + ", unitScore=" + unitScore
				+ ", frozenStock=" + frozenStock + ", stateName=" + stateName + ", catName=" + catName
				+ ", thumbnailUrl=" + thumbnailUrl + ", marketPrice=" + marketPrice + ", deliveryType=" + deliveryType
				+ "]";
	}
    
    
}