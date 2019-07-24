package com.zwx.scan.app.widget.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * author : lizhilong
 * time   : 2019/05/18
 * desc   :
 * version: 1.0
 **/
public class SafeWebChromeClient extends WebChromeClient {

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    /**
     * 当前 WebView 加载网页进度
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    /**
     * Js 中调用 alert() 函数，产生的对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    /**
     * 处理 Js 中的 Confirm 对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 处理 JS 中的 Prompt对话框
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    /**
     * 接收web页面的icon
     *
     * @param view
     * @param icon
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    /**
     * 接收web页面的 Title
     *
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    //For Android 3.0 - 4.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }
    }

    // For Android 4.0 - 5.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.showFileChooserCallBack(filePathCallback, fileChooserParams);
        }
        return true;
    }

    public void setOpenFileChooserCallBack(OpenFileChooserCallBack callBack) {
        mOpenFileChooserCallBack = callBack;
    }

    public interface OpenFileChooserCallBack {

        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
    }
}
