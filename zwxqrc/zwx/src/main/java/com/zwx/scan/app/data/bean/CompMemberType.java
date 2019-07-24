package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员卡类型
 * 
 * @author DongWei
 * @date 2018年9月7日
 */
public class CompMemberType implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 公司主键
     */
    private Long compId;
    /**
     * 会员类型主键
     */
    private Long compMemtypeId;
    /**
     * 会员类型名称(会员卡等级)
     */
    private String memtypeName;
    /**
     * 会员权益说明
     */
    private String memtypeNotes;
    /**
     * 会员类型状态 0 停用 1 使用
     */
    private String memtypeStatus;
    /**
     * 会员类型系列(会员卡系列名称)
     */
    private String compMemberGroup;
    /**
     * 绝对时间（选择时间段）——A 相对时间（激活之日起多少天）——R N——代表无限时间
     */
    private String customTime;
    /**
     * 过期提示时间，过期前几天发出提示。
     */
//    private Integer promptTime;
    /**
     * 绝对时间的起始时间
     */
    private String absoluteStartime;
    /**
     * 绝对时间的结束时间
     */
    private String absoluteEndtime;
    /**
     * 记录相对时间
     */
//    private Integer relativeTime;
    /**
     * 会员卡升级类型 T-升级/F不升级
     */
    private String upgradeType;
    /**
     * 停用时间
     */
//    private String disableDate;
    /**
     * 启用时间(启用时间有值时不允许物理删除当前数据)
     */
//    private String enableDate;
    /**
     * 1:共享权益；0:不共享权益
     */
    private String isShareright;

    /**
     * 活动管理模块 查询字段
     */
//    private Long newCompMemTypeCod;
    /**
     * branCod 会员管理下拉框
     */
//    private Long branCod;
    /**
     * brandNam 会员卡管理查询
     */
//    private String brandNam;

    /**
     * compCodArray 公司cod集合
     */
//    private String compCodArray;

    /**
     * 年限 按年升级规则，几年可升级
     */
//    private Integer yearRule;

    /**
     * 年限 按消费规则限制，几年内消费
     */
//    private Integer amountRuleLimit;

    /**
     * 消费金额 按消费升级规则，消费满多少可升级
     */
//    private Integer amountRuleAmt;

    /**
     * 年限 按频次升级限制，几年内频次
     */
//    private Integer countRuleLimit;

    /**
     * 次数 按频次升级规则，消费满多少可升级
     */
//    private Integer countRuleQnt;



    
    private Boolean checked;

    private Integer status;


    /**
     * 素材Id
     */
    private Long materialId;

    /**
     * 背景图id
     */
    private String materialBack;

    /**
     * pos机识别码
     */
    private String posId;

    /**
     * 显示内容规则
     */
    private String displayRule;

    /**
     * 字体颜色(记录色值)
     */
    private String colour;

    /**
     * 加入条件
     */
    private String joinCondition;

    /**
     * 是否使用模板  0自定义 1 权限模板
     */
    private String isTemplet;

    /**
     * 购买金额
     */
    private BigDecimal purchasingPrice;

    /**
     * 备注
     */
    private String notes;


    /**
     * 过期提示时间，过期前几天发出提示。
     */
    private Integer promptTime;

    /**
     * 记录相对时间 开卡日时间
     */
    private Integer relativeTime;

    /**
     * 过期提示语
     */
    private String reminder;



    /**
     * 背景图片缩略图ID
     */
    private String background;
    private String name;


    private String memtypeIdArray;

    /**
     * 是否为默认卡（1不是 0是）
     */
    private String isDefault;

    /**
     * 首页是否显示（1是 0是）
     */
    private String visible;

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getMemtypeIdArray() {
        return memtypeIdArray;
    }

    public void setMemtypeIdArray(String memtypeIdArray) {
        this.memtypeIdArray = memtypeIdArray;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(String upgradeType) {
        this.upgradeType = upgradeType;
    }

    public Integer getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(Integer relativeTime) {
        this.relativeTime = relativeTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public BigDecimal getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(BigDecimal purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public Integer getPromptTime() {
        return promptTime;
    }

    public void setPromptTime(Integer promptTime) {
        this.promptTime = promptTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public String getIsTemplet() {
        return isTemplet;
    }

    public void setIsTemplet(String isTemplet) {
        this.isTemplet = isTemplet;
    }

    public String getCustomTime() {
        return customTime;
    }

    public void setCustomTime(String customTime) {
        this.customTime = customTime;
    }

    public String getAbsoluteStartime() {
        return absoluteStartime;
    }

    public void setAbsoluteStartime(String absoluteStartime) {
        this.absoluteStartime = absoluteStartime;
    }

    public String getAbsoluteEndtime() {
        return absoluteEndtime;
    }

    public void setAbsoluteEndtime(String absoluteEndtime) {
        this.absoluteEndtime = absoluteEndtime;
    }


    public String getIsShareright() {
        return isShareright;
    }

    public void setIsShareright(String isShareright) {
        this.isShareright = isShareright;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialBack() {
        return materialBack;
    }

    public void setMaterialBack(String materialBack) {
        this.materialBack = materialBack;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getDisplayRule() {
        return displayRule;
    }

    public void setDisplayRule(String displayRule) {
        this.displayRule = displayRule;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getCompMemtypeId() {
        return compMemtypeId;
    }

    public void setCompMemtypeId(Long compMemtypeId) {
        this.compMemtypeId = compMemtypeId;
    }

    public String getMemtypeName() {
        return memtypeName;
    }

    public void setMemtypeName(String memtypeName) {
        this.memtypeName = memtypeName;
    }

    public String getMemtypeNotes() {
        return memtypeNotes;
    }

    public void setMemtypeNotes(String memtypeNotes) {
        this.memtypeNotes = memtypeNotes;
    }

    public String getMemtypeStatus() {
        return memtypeStatus;
    }

    public void setMemtypeStatus(String memtypeStatus) {
        this.memtypeStatus = memtypeStatus;
    }

    public String getCompMemberGroup() {
        return compMemberGroup;
    }

    public void setCompMemberGroup(String compMemberGroup) {
        this.compMemberGroup = compMemberGroup;
    }
}