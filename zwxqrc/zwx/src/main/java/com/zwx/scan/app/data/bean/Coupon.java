package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券实体类
 * @author HanJian
 * @date 2018年9月3日
 */
public class Coupon implements Serializable {

    private static final long serialVersionUID = 3722769115196354693L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 优惠券名称
     */
    private String name;
    
    /**
     * 所属公司
     */
//    private Company company;
    
    /**
     * 优惠券类型：VCS單次儲值卡，VCM多次儲值卡，CPC代金券，CPD折扣券，CPO礼品券，CPU菜品券、CPJ插队券、CPT其他
     */
    private String type;
    
    /**
     * 启用状态：启用1，停用0
     */
    private Integer status;
    
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
     * 自定义不可用日期
     */
    private List<UnavailableDate> unavailableDate;
    
    /**
     * 第一张优惠券序号
     */
    private Long startNos;
    
    /**
     * 下一张发放优惠券序号
     */
    private Long nextNos;
    
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
     * 优惠券素材
     */
    private CouponMaterial material;
    
    /**
     * 创建日期
     */
    private String createTime;
    
    /**
     * 创建人
     */
//    private User creator;
    
    /**
     * 最后一次修改日期
     */
    private String modifyTime;
    
    /**
     * 最后一次修改人
     */
//    private User modifier;
    
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


    /**
     *  优惠券数量 默认为0——不限制数量 填写数量——根据数量发放优惠券
     */
    private Integer couponQnt;


    /**
     * 不可用日期 的 编码
     *
     * 第一位  表示没有不可用日期0 和 有不可用日期1
     * 第二位  为1表示周六日不可用 为0表示 未选择
     * 第三位  为1表示周六、日及法定节假日不可用， 为0表示 未选择： 第二位和第三位互斥
     * 第4位  法定节假日不可用 1 为是 0为否
     * 第5位  自定义不可用时间段

     * 11000 周六、日不可用
     * 10100 周六、日及法定节假日不可用
     * 10010 法定节假日不可用
     * 00000 没有不可用日期
     * 11001 周六、日不可用…
     * 10101 周六、日及法定节假日不可用…
     * 10011 法定节假日不可用…
     *
     */
    private String dateCode;

    private String storeSelectType;

    private String storeNames;
    private String storeIds;
    private boolean isChecked;


    private String background;

    private String  backgroundThumbnail;

    public CouponMaterial getMaterial() {
        return material;
    }

    public void setMaterial(CouponMaterial material) {
        this.material = material;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackgroundThumbnail() {
        return backgroundThumbnail;
    }

    public void setBackgroundThumbnail(String backgroundThumbnail) {
        this.backgroundThumbnail = backgroundThumbnail;
    }

    public Integer getCouponQnt() {
        return couponQnt;
    }

    public void setCouponQnt(Integer couponQnt) {
        this.couponQnt = couponQnt;
    }

    public String getStoreNames() {
        return storeNames;
    }

    public void setStoreNames(String storeNames) {
        this.storeNames = storeNames;
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public String getStoreSelectType() {
        return storeSelectType;
    }

    public void setStoreSelectType(String storeSelectType) {
        this.storeSelectType = storeSelectType;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

	public Long getMemcouId() {
		return memcouId;
	}

	public void setMemcouId(Long memcouId) {
		this.memcouId = memcouId;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getExpireStartType() {
		return expireStartType;
	}

	public void setExpireStartType(String expireStartType) {
		this.expireStartType = expireStartType;
	}

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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



	public String getExpireEndType() {
		return expireEndType;
	}

	public void setExpireEndType(String expireEndType) {
		this.expireEndType = expireEndType;
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

//    public Company getCompany() {
//        return company;
//    }
//
//    public void setCompany(Company company) {
//        this.company = company;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Boolean getAssign() {
        return assign;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Integer timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getNoDate() {
        return noDate;
    }

    public void setNoDate(String noDate) {
        this.noDate = noDate;
    }

    public List<UnavailableDate> getUnavailableDate() {
        return unavailableDate;
    }

    public void setUnavailableDate(List<UnavailableDate> unavailableDate) {
        this.unavailableDate = unavailableDate;
    }

    public Long getStartNos() {
        return startNos;
    }

    public void setStartNos(Long startNos) {
        this.startNos = startNos;
    }

    public Long getNextNos() {
        return nextNos;
    }

    public void setNextNos(Long nextNos) {
        this.nextNos = nextNos;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Float getLimit() {
        return limit;
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }

//    public User getModifier() {
//        return modifier;
//    }
//
//    public void setModifier(User modifier) {
//        this.modifier = modifier;
//    }

//    public CouponMaterial getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(CouponMaterial material) {
//        this.material = material;
//    }

    public String getCreateTime() {
        return createTime;
    }

//    public User getCreator() {
//        return creator;
//    }
//
//    public void setCreator(User creator) {
//        this.creator = creator;
//    }

    public String getModifyTime() {
        return modifyTime;
    }
}
