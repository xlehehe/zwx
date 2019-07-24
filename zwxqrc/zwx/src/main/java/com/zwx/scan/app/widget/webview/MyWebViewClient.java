package com.zwx.scan.app.widget.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;

import org.json.JSONObject;

/**
 * author : lizhilong
 * time   : 2019/07/18
 * desc   :
 * version: 1.0
 **/
public class MyWebViewClient extends WebViewClient {

    private SafeWebView mWebView;

    public MyWebViewClient(SafeWebView mWebView) {
        this.mWebView = mWebView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e("1 shouldOverrideUrlLoading--");
        WebView.HitTestResult hit = view.getHitTestResult();
        if (hit != null) {
            Intent intent = new Intent();
            intent.setAction( "android.intent.action.VIEW");
            Uri content_url = Uri. parse(url);
            intent.setData(content_url);
            AppContext.getInstance().startActivity(intent);
            return true;
        } else {
            view.loadUrl(url);
            return true;
        }
        /*Uri uri = Uri.parse(url);
        if (uri.getHost() != null && uri.getHost().contains("index.html")) {
            LogUtils.e("1 进入--均交给webView自己处理--"+"true");
            return true;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        LogUtils.e("1 未进入--均交给webView自己处理--"+"false");
        return false;*/

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){

        LogUtils.e("2 shouldOverrideUrlLoading--");
        //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
        //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (request.getUrl().toString().contains("index.html")){
                view.loadUrl(HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateList");
                LogUtils.e("2 进入--均交给webView自己处理--"+"true");
                return false;
            }
        }
        return true;
    }


    @Override
    public void  onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //设定加载开始的操作
        LogUtils.e("onPageStarted---"+ url);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
            callEvaluateJavascript(mWebView);
        }


    }
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtils.e("onPageFinished---"+ url);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
            callEvaluateJavascript(mWebView);
        }
    }



    private void callEvaluateJavascript(WebView webView) {

        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("userId", SPUtils.getInstance().getString("userId"));
            jsonObject.put("compId",SPUtils.getInstance().getString("compId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            webView.evaluateJavascript("javascript:window.getUserId('"+jsonObject.toString()+"')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
//                    ToastUtils.showShort(value);
                    LogUtils.e("javascript:getUserId---"+value);
                }});

        }

    }
}
