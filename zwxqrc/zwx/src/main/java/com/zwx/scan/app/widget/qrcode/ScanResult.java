package com.zwx.scan.app.widget.qrcode;

import android.graphics.PointF;

/**
 * 创建时间:2018/6/15
 * 描述:
 */
public class ScanResult {
    String result;
    PointF[] resultPoints;

    public ScanResult(String result) {
        this.result = result;
    }

    public ScanResult(String result, PointF[] resultPoints) {
        this.result = result;
        this.resultPoints = resultPoints;
    }
}
