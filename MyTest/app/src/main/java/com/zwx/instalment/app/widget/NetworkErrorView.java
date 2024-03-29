package com.zwx.instalment.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.zwx.instalment.app.R;


/**
 * Description: <NetworkErrorView><br>
 * Author:      lizhilong<br>
 * Date:        2018/6/20<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NetworkErrorView extends RelativeLayout {

    public NetworkErrorView(Context context) {
        super(context);
        init();
    }

    public NetworkErrorView(Context context, int layoutID) {
        super(context);
        init();
    }

    public NetworkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.button_network_err, this, true);
        this.setClickable(true);
        this.setFocusable(true);
    }
}
