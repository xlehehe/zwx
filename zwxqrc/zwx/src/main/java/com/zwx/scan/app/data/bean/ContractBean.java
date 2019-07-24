package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/19
 * desc   : 合同管理
 * version: 1.0
 **/
public class ContractBean implements Serializable {



    private  Contract contract;

    private String isShowAllIcon;

    private List<ReceiveFund> receiveFundList;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getIsShowAllIcon() {
        return isShowAllIcon;
    }

    public void setIsShowAllIcon(String isShowAllIcon) {
        this.isShowAllIcon = isShowAllIcon;
    }

    public List<ReceiveFund> getReceiveFundList() {
        return receiveFundList;
    }

    public void setReceiveFundList(List<ReceiveFund> receiveFundList) {
        this.receiveFundList = receiveFundList;
    }
}
