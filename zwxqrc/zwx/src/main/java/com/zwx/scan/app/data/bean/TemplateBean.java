package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/12
 * desc   :  会员卡管理模板素材 返回实体类封装
 * version: 1.0
 **/
public class TemplateBean implements Serializable {


    private String companyId;
    private String templateSort;
    List<Template> templates;

    List<MaterialCard> materialCards;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTemplateSort() {
        return templateSort;
    }

    public void setTemplateSort(String templateSort) {
        this.templateSort = templateSort;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<MaterialCard> getMaterialCards() {
        return materialCards;
    }

    public void setMaterialCards(List<MaterialCard> materialCards) {
        this.materialCards = materialCards;
    }
}
