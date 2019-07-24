package com.zwx.scan.app.widget.tagflowlayout;

import android.view.View;

/**
 *
 * Author   :lizhilong
 * Email    :longxiaotian@163.com
 * Create at 2016/9/4
 * Description:
 */
public interface OnTagClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
