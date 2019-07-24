package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 活动优惠券实体类
 *@author yanshoufu
 *@date 2018年9月4日
 */
public class CampaignCoupon implements Serializable {
	private static final long serialVersionUID = -1852007717184910585L;

	/**
     * 主键
     */
    private Long id;

    /**
     * 公司cod
     */
    private Long compId;

    /**
     * 活动cod
     */
    private Long campaignId;

    /**
     * 会员等级cod 
     */
    private Long compMemtypeId;

    /**
     * 优惠券种代号 
     */
    private Long couponId;

    /**
     * 有效期--开始日期--有效方式 A:絕對日期 R1:相對日期，以获得日加可用天數 R2:相對日期，以获得日加可用天數，到當月底 R3:相對日期，以领取日加可用天數 R4:相對日期，以领取日加可用天數，到當月底
     */
    private String expireStartType;

    /**
     * 有效期--开始日期-有效时间
     */
    private String expireStartDate;

    /**
     * 有效期--开始日期-有效天数
     */
    private String expireStartDay;

    /**
     * 優先順序1 2 3 4
     */
    private String priorityStatus;

    /**
     *  优惠券数量 默认为0——不限制数量 填写数量——根据数量发放优惠券
     */
    private Integer couponQnt;

    /**
     * 优惠券张数规则 每天循环更新张数——Y 不循环更新张数——N
     */
    private String couponQntRule;

    /**
     * 门店cod 使用；进行分割
     */
    private Long storeId;

    /**
     * 已发放总数量
     */
    private Integer grantQnt;

    /**
     * 券样式
     */
    private String couponTemplete;

    /**
     * 有效期--截止日期--有效方式 A:絕對日期 R1:相對日期，以获得日加可用天數 R2:相對日期，以获得日加可用天數，到當月底 R3:相對日期，以领取日加可用天數 R4:相對日期，以领取日加可用天數，到當月底
     */
    private String expireEndType;

    /**
     * 有效期--截止日期-有效时间
     */
    private String expireEndDate;

    /**
     * 有效期--截止日期-有效天数
     */
    private String expireEndDay;
    
    /**
     * 不可用项目
     */
    private String noItem;
    
    /**
     * 可否与其他优惠共享
     */
    private Boolean share;
    
    /**
     * 可否转让给朋友
     */
    private Boolean assign;
    
    /**
     * 备注说明
     */
    private String notes;
    
    /**
     * 时段限制：1:全天无限制，2：午时，3：晚市
     */
    private Integer timePeriod;
    
    /**
     * 日期限制：0：没有日期限制；1：周六，周日不可用；2：自定义日期不可用
     */
    private String noDate;

    /**
     * 优惠金额
     */    
    private Float money;
    
    /**
     * 折扣
     */
    private Float discount;
    
    /**
     * 兑换物品
     */
    private String object;
    
    /**
     * 其它优惠
     */
    private String other;
    
    /**
     * 使用限制，消费满多少，可用券
     */
    private Float limit;
    
    /**
     * 背景图片ID
     */
    private String background;
    
    /**
     * 背景图片缩略图ID
     */
    private String backgroundThumbnail;
    
    /**
     * 优惠券选中状态
     */
    private String checkFlag;
    
    /**
     * 活动优惠券id
     */
    private Long memcouId;
    
    /**
     * 获奖概率
     */
    private BigDecimal prizeProb;

    /**
     * 奖品总数
     */
    private Integer prizeCount;

    /**
     * 奖品图片地址
     */
    private String prizeImageUrl;
    
    
    private String couponName;
    
    private String storeStr;
    
    private String storeSelectType;

    private  Store store;


    private String[] storeArr;
    //
    private List<Store> stores;

    private String storeName;

    private String showDate;

    private String type;

	private String dateCode;

	private String campaignStatus;

	private Integer client;

	private String isSort;


	public String getIsSort() {
		return isSort;
	}

	public void setIsSort(String isSort) {
		this.isSort = isSort;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String[] getStoreArr() {
        return storeArr;
    }

    public void setStoreArr(String[] storeArr) {
        this.storeArr = storeArr;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
	 * @return the storeSelectType
	 */
	public String getStoreSelectType() {
		return storeSelectType;
	}

	/**
	 * @param storeSelectType the storeSelectType to set
	 */
	public void setStoreSelectType(String storeSelectType) {
		this.storeSelectType = storeSelectType;
	}

	/**
	 * @return the noItem
	 */
	public String getNoItem() {
		return noItem;
	}

	/**
	 * @param noItem the noItem to set
	 */
	public void setNoItem(String noItem) {
		this.noItem = noItem;
	}

	/**
	 * @return the share
	 */
	public Boolean getShare() {
		return share;
	}

	/**
	 * @param share the share to set
	 */
	public void setShare(Boolean share) {
		this.share = share;
	}

	/**
	 * @return the assign
	 */
	public Boolean getAssign() {
		return assign;
	}

	/**
	 * @param assign the assign to set
	 */
	public void setAssign(Boolean assign) {
		this.assign = assign;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the timePeriod
	 */
	public Integer getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(Integer timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * @return the noDate
	 */
	public String getNoDate() {
		return noDate;
	}

	/**
	 * @param noDate the noDate to set
	 */
	public void setNoDate(String noDate) {
		this.noDate = noDate;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	/**
	 * @return the discount
	 */
	public Float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * @return the limit
	 */
	public Float getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Float limit) {
		this.limit = limit;
	}

	/**
	 * @return the background
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * @return the backgroundThumbnail
	 */
	public String getBackgroundThumbnail() {
		return backgroundThumbnail;
	}

	/**
	 * @param backgroundThumbnail the backgroundThumbnail to set
	 */
	public void setBackgroundThumbnail(String backgroundThumbnail) {
		this.backgroundThumbnail = backgroundThumbnail;
	}

	/**
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * @return the memcouId
	 */
	public Long getMemcouId() {
		return memcouId;
	}

	/**
	 * @param memcouId the memcouId to set
	 */
	public void setMemcouId(Long memcouId) {
		this.memcouId = memcouId;
	}

	/**
	 * @return the prizeProb
	 */
	public BigDecimal getPrizeProb() {
		return prizeProb;
	}

	/**
	 * @param prizeProb the prizeProb to set
	 */
	public void setPrizeProb(BigDecimal prizeProb) {
		this.prizeProb = prizeProb;
	}

	/**
	 * @return the prizeCount
	 */
	public Integer getPrizeCount() {
		return prizeCount;
	}

	/**
	 * @param prizeCount the prizeCount to set
	 */
	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}

	/**
	 * @return the prizeImageUrl
	 */
	public String getPrizeImageUrl() {
		return prizeImageUrl;
	}

	/**
	 * @param prizeImageUrl the prizeImageUrl to set
	 */
	public void setPrizeImageUrl(String prizeImageUrl) {
		this.prizeImageUrl = prizeImageUrl;
	}

	/**
	 * @return the couponName
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * @param couponName the couponName to set
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	/**
	 * @return the storeStr
	 */
	public String getStoreStr() {
		return storeStr;
	}

	/**
	 * @param storeStr the storeStr to set
	 */
	public void setStoreStr(String storeStr) {
		this.storeStr = storeStr;
	}

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

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public Long getCompMemtypeId() {
		return compMemtypeId;
	}

	public void setCompMemtypeId(Long compMemtypeId) {
		this.compMemtypeId = compMemtypeId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getExpireStartType() {
		return expireStartType;
	}

	public void setExpireStartType(String expireStartType) {
		this.expireStartType = expireStartType;
	}



	public String getPriorityStatus() {
		return priorityStatus;
	}

	public void setPriorityStatus(String priorityStatus) {
		this.priorityStatus = priorityStatus;
	}

	public Integer getCouponQnt() {
		return couponQnt;
	}

	public void setCouponQnt(Integer couponQnt) {
		this.couponQnt = couponQnt;
	}

	public String getCouponQntRule() {
		return couponQntRule;
	}

	public void setCouponQntRule(String couponQntRule) {
		this.couponQntRule = couponQntRule;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Integer getGrantQnt() {
		return grantQnt;
	}

	public void setGrantQnt(Integer grantQnt) {
		this.grantQnt = grantQnt;
	}

	public String getCouponTemplete() {
		return couponTemplete;
	}

	public void setCouponTemplete(String couponTemplete) {
		this.couponTemplete = couponTemplete;
	}

	public String getExpireEndType() {
		return expireEndType;
	}

	public void setExpireEndType(String expireEndType) {
		this.expireEndType = expireEndType;
	}

	public String getExpireStartDay() {
		return expireStartDay;
	}

	public void setExpireStartDay(String expireStartDay) {
		this.expireStartDay = expireStartDay;
	}

	public String getExpireEndDay() {
		return expireEndDay;
	}

	public void setExpireEndDay(String expireEndDay) {
		this.expireEndDay = expireEndDay;
	}

	public String getExpireStartDate() {
		return expireStartDate;
	}

	public void setExpireStartDate(String expireStartDate) {
		this.expireStartDate = expireStartDate;
	}

	public String getExpireEndDate() {
		return expireEndDate;
	}

	public void setExpireEndDate(String expireEndDate) {
		this.expireEndDate = expireEndDate;
	}
}