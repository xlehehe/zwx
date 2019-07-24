package com.zwx.scan.app.widget.tagflowlayout;

import android.view.View;

import com.zwx.scan.app.widget.tagflowlayout.bean.TagBean;

import java.util.List;

/**
 * Author   :lizhilong
 * Email    :longxiaotian@163.com
 * Create at 2017-06-13
 * Description: 标签被选中或取消选中监听
 */
public interface OnTagSelectedListener {
    void selected(View view, int position, List<TagBean> selected);

    void unSelected(View view, int position, List<TagBean> selected);
}
