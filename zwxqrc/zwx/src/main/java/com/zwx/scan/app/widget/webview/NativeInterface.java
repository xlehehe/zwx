package com.zwx.scan.app.widget.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


public class NativeInterface {

    private Context mContext;

    public NativeInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void hello() {
        Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void hello(String params) {
        Toast.makeText(mContext, params, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAndroid() {
        Toast.makeText(mContext, "getAndroid", Toast.LENGTH_SHORT).show();
        return "Android data";
    }

    @JavascriptInterface
    public String getAndroidTime() {
        return String.valueOf(System.currentTimeMillis());
    }



    @JavascriptInterface
    public void CancelContract() {
        Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
    }


    @JavascriptInterface
    public void signContract() {
        Toast.makeText(mContext, "签约成功", Toast.LENGTH_SHORT).show();
    }

}
