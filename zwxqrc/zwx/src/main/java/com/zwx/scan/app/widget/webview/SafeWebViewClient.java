package com.zwx.scan.app.widget.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zwx.library.utils.LogUtils;

/**
 * author : lizhilong
 * time   : 2019/05/18
 * desc   :
 * version: 1.0
 **/
public class SafeWebViewClient extends WebViewClient {


    /**
     * 当WebView得页面Scale值发生改变时回调
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    /**
     * 是否在 WebView 内加载页面
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * WebView 开始加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    /**
     * WebView 完成加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    /**
     * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
     *
     * @param view WebView
     * @param url  url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * WebView 可以拦截某一次的 request 来返回我们自己加载的数据，这个方法在后面缓存会有很大作用。
     *
     * @param view    WebView
     * @param request 当前产生 request 请求
     * @return WebResourceResponse
     */
  /*  @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }*/

    /**
     * WebView 访问 url 出错
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        LogUtils.e("WebViewClient  onReceivedError error= "+error);
        super.onReceivedError(view, request, error);
    }

    /**
     * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        LogUtils.e("WebViewClient  onReceivedSslError  error="+error);
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedHttpError(
            WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        LogUtils.e("WebViewClient onReceivedSslError  ");
        super.onReceivedHttpError(view,request,errorResponse);
    }
    @Override
    public void doUpdateVisitedHistory(WebView view, String url,
                                       boolean isReload) {
        //Notify the host application to update its visited links database.   reload重新加载
        LogUtils.e("WebViewClient doUpdateVisitedHistory  isReload="+isReload+ "  url="+url);
        super.doUpdateVisitedHistory(view, url, isReload);
    }
}
