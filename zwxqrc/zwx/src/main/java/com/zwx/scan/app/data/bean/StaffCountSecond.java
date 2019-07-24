package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/03/06
 * desc   :
 * version: 1.0
 **/
public class StaffCountSecond implements Serializable {

    private String  joinCount;
    private String  successCount;

    private List<CampaignChartBean> joinCountList;

    private List<CampaignChartBean> successCountList;

    public String getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(String joinCount) {
        this.joinCount = joinCount;
    }

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    public List<CampaignChartBean> getJoinCountList() {
        return joinCountList;
    }

    public void setJoinCountList(List<CampaignChartBean> joinCountList) {
        this.joinCountList = joinCountList;
    }

    public List<CampaignChartBean> getSuccessCountList() {
        return successCountList;
    }

    public void setSuccessCountList(List<CampaignChartBean> successCountList) {
        this.successCountList = successCountList;
    }
}
