package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class CampaignDetailBean implements Serializable {

    private String joinTotal;
    private String fowardTotal;
    private String sendTotal;
    private String getTotal;
    private String receiveTotal;
    private String registerTotal;
    private String viewTotal;

    private List<CampaignChartBean> joinList;
    private List<CampaignChartBean> fowardList;

    private List<CampaignChartBean> sendList;
    private List<CampaignChartBean> getList;

    private List<CampaignChartBean> receiveList;

    private List<CampaignChartBean> registerList;

    private List<CampaignChartBean> viewList;

    public String getJoinTotal() {
        return joinTotal;
    }

    public void setJoinTotal(String joinTotal) {
        this.joinTotal = joinTotal;
    }

    public String getFowardTotal() {
        return fowardTotal;
    }

    public void setFowardTotal(String fowardTotal) {
        this.fowardTotal = fowardTotal;
    }

    public String getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(String sendTotal) {
        this.sendTotal = sendTotal;
    }

    public String getGetTotal() {
        return getTotal;
    }

    public void setGetTotal(String getTotal) {
        this.getTotal = getTotal;
    }

    public String getReceiveTotal() {
        return receiveTotal;
    }

    public void setReceiveTotal(String receiveTotal) {
        this.receiveTotal = receiveTotal;
    }

    public String getRegisterTotal() {
        return registerTotal;
    }

    public void setRegisterTotal(String registerTotal) {
        this.registerTotal = registerTotal;
    }

    public String getViewTotal() {
        return viewTotal;
    }

    public void setViewTotal(String viewTotal) {
        this.viewTotal = viewTotal;
    }

    public List<CampaignChartBean> getJoinList() {
        return joinList;
    }

    public void setJoinList(List<CampaignChartBean> joinList) {
        this.joinList = joinList;
    }

    public List<CampaignChartBean> getFowardList() {
        return fowardList;
    }

    public void setFowardList(List<CampaignChartBean> fowardList) {
        this.fowardList = fowardList;
    }

    public List<CampaignChartBean> getSendList() {
        return sendList;
    }

    public void setSendList(List<CampaignChartBean> sendList) {
        this.sendList = sendList;
    }

    public List<CampaignChartBean> getGetList() {
        return getList;
    }

    public void setGetList(List<CampaignChartBean> getList) {
        this.getList = getList;
    }

    public List<CampaignChartBean> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<CampaignChartBean> receiveList) {
        this.receiveList = receiveList;
    }

    public List<CampaignChartBean> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<CampaignChartBean> registerList) {
        this.registerList = registerList;
    }

    public List<CampaignChartBean> getViewList() {
        return viewList;
    }

    public void setViewList(List<CampaignChartBean> viewList) {
        this.viewList = viewList;
    }
}
