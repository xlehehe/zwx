package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CampaignDetail implements Serializable {

    private String joinTotal;
    private String fowardTotal;
    private String sendTotal;
    private String getTotal;
    private String receiveTotal;
    private String registerTotal;
    private String viewTotal;

    private ArrayList<Map<String,Object>> joinList;
    private ArrayList<Map<String,Object>> fowardList;

    private ArrayList<Map<String,Object>> sendList;
    private ArrayList<Map<String,Object>> getList;

    private ArrayList<Map<String,Object>> receiveList;

    private ArrayList<Map<String,Object>> registerList;

    private ArrayList<Map<String,Object>> viewList;

    public ArrayList<Map<String, Object>> getJoinList() {
        return joinList;
    }

    public void setJoinList(ArrayList<Map<String, Object>> joinList) {
        this.joinList = joinList;
    }

    public ArrayList<Map<String, Object>> getFowardList() {
        return fowardList;
    }

    public void setFowardList(ArrayList<Map<String, Object>> fowardList) {
        this.fowardList = fowardList;
    }

    public ArrayList<Map<String, Object>> getSendList() {
        return sendList;
    }

    public void setSendList(ArrayList<Map<String, Object>> sendList) {
        this.sendList = sendList;
    }

    public ArrayList<Map<String, Object>> getGetList() {
        return getList;
    }

    public void setGetList(ArrayList<Map<String, Object>> getList) {
        this.getList = getList;
    }

    public ArrayList<Map<String, Object>> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(ArrayList<Map<String, Object>> receiveList) {
        this.receiveList = receiveList;
    }

    public ArrayList<Map<String, Object>> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(ArrayList<Map<String, Object>> registerList) {
        this.registerList = registerList;
    }

    public ArrayList<Map<String, Object>> getViewList() {
        return viewList;
    }

    public void setViewList(ArrayList<Map<String, Object>> viewList) {
        this.viewList = viewList;
    }

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
}
