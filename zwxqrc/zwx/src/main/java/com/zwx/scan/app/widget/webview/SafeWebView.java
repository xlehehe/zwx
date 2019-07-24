package com.zwx.scan.app.widget.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Method;

public class SafeWebView extends WebView {
    private SafeWebChromeClient webChromeClient = new SafeWebChromeClient();
    private SafeWebViewClient webViewClient = new SafeWebViewClient();

    public SafeWebView(Context context) {
        super(context);
        init(context);
//        removeSearchBox();
    }

    public SafeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
//        removeSearchBox();
    }

    public SafeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
//        removeSearchBox();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {

        WebSettings webSettings = this.getSettings();
        if (webSettings == null) return;
        //设置字体缩放倍数，默认100
//        webSettings.setTextZoom(100);
        // 支持 Js 使用
        webSettings.setJavaScriptEnabled(true);
        // 开启DOM缓存
        webSettings.setDomStorageEnabled(true);
        // 开启数据库缓存
        webSettings.setDatabaseEnabled(false);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(hasKitkat());
        // 设置 WebView 的缓存模式
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(false);
        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
//        webSettings.setAppCacheMaxSize(8* 1024 * 1024);
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
        webSettings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        // 数据库路径
        if (!hasKitkat()) {
            webSettings.setDatabasePath(getContext().getDatabasePath("html").getPath());
        }
        // 关闭密码保存提醒功能
//        webSettings.setSavePassword(false);
        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //扩大比例的缩放
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 设置 UserAgent 属性
//        webSettings.setUserAgentString("");
        // 允许加载本地 html 文件/false
        webSettings.setAllowFileAccess(true);
        // 允许通过 file url 加载的 Javascript 读取其他的本地文件,Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
        // Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        // 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        this.setWebViewClient(webViewClient);
        this.setWebChromeClient(webChromeClient);
        this.onResume();

        /**
         *  关于缓存目录:
         *
         *  我自测发现以下规律，如果你有涉及到目录操作，需要自己做下验证。
         *  Android 4.4 以下：/data/data/包名/cache
         *  Android 4.4 - 5.0：/data/data/包名/app_webview/cache/
         */
    }
    private static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    /**
     * 移除系统注入的对象，避免js漏洞
     * <p>
     * https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-1939
     * https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-7224
     */
    private void removeSearchBox() {
        super.removeJavascriptInterface("searchBoxJavaBridge_");
        super.removeJavascriptInterface("accessibility");
        super.removeJavascriptInterface("accessibilityTraversal");
    }

    public static void disableAccessibility(Context context) {
//        if (Build.VERSION.SDK_INT == 17/*4.2 (Build.VERSION_CODES.JELLY_BEAN_MR1)*/) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (context != null) {
                try {
                    AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (!am.isEnabled()) {
                        //Not need to disable accessibility
                        return;
                    }

                    Method setState = am.getClass().getDeclaredMethod("setState", int.class);
                    setState.setAccessible(true);
                    setState.invoke(am, 0);/**{@link AccessibilityManager#STATE_FLAG_ACCESSIBILITY_ENABLED}*/
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

/*    @Override
    public void setOverScrollMode(int mode) {
        try {
            super.setOverScrollMode(mode);
        } catch (Throwable e) {
            String trace = Log.getStackTraceString(e);
            if (trace.contains("android.content.pm.PackageManager$NameNotFoundException")
                    || trace.contains("java.lang.RuntimeException: Cannot load WebView")
                    || trace.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean isPrivateBrowsingEnabled() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && getSettings() == null) {
            return false;
        } else {
            return super.isPrivateBrowsingEnabled();
        }
    }*/


    public void setOpenFileChooserCallBack(SafeWebChromeClient.OpenFileChooserCallBack callBack) {
        webChromeClient.setOpenFileChooserCallBack(callBack);
    }
}
