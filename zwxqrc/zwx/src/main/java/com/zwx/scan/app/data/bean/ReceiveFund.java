package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReceiveFund implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long fundId;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 餐企id
     */
    private Long compId;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 门店id
     */
    private Long storeId;

    /**
     * 状态：未收U，已收R，未生效N
     */
    private String status;

    /**
     * 计划收款日期
     */
    private String planReceiveDate;

    /**
     * 实际收款日期
     */
    private String factReceiveDate;

    /**
     * 收款金额
     */
    private BigDecimal money;

    /**
     * 创建日期
     */
    private String createTime;
    
    /**
     * 店铺名称
     */
    private String storeName;
    
    /**
     * 是否逾期
     */
    private String isOverdue;

    private boolean isChecked;

    /**
     * 应收账款编号C1901220001-01从1开始
     */
    private String fundNo;


    /**
     * 支付流水号
     */
    private String paymentNo;

    /**
     * 发票申请id
     */
    private String invoiceId;

    private String contractNo;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFundNo() {
        return fundNo;
    }

    public void setFundNo(String fundNo) {
        this.fundNo = fundNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }



    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }



	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

    public String getPlanReceiveDate() {
        return planReceiveDate;
    }

    public void setPlanReceiveDate(String planReceiveDate) {
        this.planReceiveDate = planReceiveDate;
    }

    public String getFactReceiveDate() {
        return factReceiveDate;
    }

    public void setFactReceiveDate(String factReceiveDate) {
        this.factReceiveDate = factReceiveDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}