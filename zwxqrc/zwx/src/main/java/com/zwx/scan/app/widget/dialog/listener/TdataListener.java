package com.zwx.scan.app.widget.dialog.listener;

import android.view.View;
import android.widget.AdapterView;

import com.zwx.scan.app.widget.dialog.bean.PopuBean;

import java.util.List;

/**
 * 动态设置下拉框的数据
 */
public interface TdataListener {
    /**
     * 初始化数据
     */
    void initPupoData(List<PopuBean> lists);

    /**
     * 点击事件
     */
    void onItemClick(AdapterView<?> adapterView, View view, int position);
}