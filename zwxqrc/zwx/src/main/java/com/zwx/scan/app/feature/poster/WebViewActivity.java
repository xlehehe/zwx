package com.zwx.scan.app.feature.poster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.widget.webview.LWebView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private static final String APP_CACAHE_DIRNAME = "/webcache";


    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.web_view)
    LWebView mWebView;
    private String Url = HttpUrls.POSTER_BASE_URL +"jsp/mobile/index.html#/templateList";
//    private String Url = "https://www.baidu.com/";
    private static int FILE_CHOOSER_RESULT_CODE = 999;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //        QMUIStatusBarHelper.translucent(this,getResources().getColor(R.color.gray));
//        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initWebView();
        mWebView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){

                LogUtils.e("2 shouldOverrideUrlLoading--");
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("index.html")){
                        view.loadUrl(HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateList");
                        LogUtils.e("2 进入--均交给webView自己处理--"+"true");
                        return true;
                    }
                }
                LogUtils.e("2 未进入--均交给webView自己处理--"+"false");
                return super.shouldOverrideUrlLoading(view,request);
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

        });

        mWebView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//
                super.onProgressChanged(view, newProgress);
            }

            //For Android API < 11 (3.0 OS)
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android API >= 11 (3.0 OS)
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android API >= 21 (5.0 OS)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.e(TAG, "onJsAlert " + message);

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                result.confirm();

                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {

                Log.e(TAG, "onJsConfirm " + message);

                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                Log.e(TAG, "onJsPrompt " + url);

                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

        });
        mWebView.loadUrl(Url);
    }

    private void callEvaluateJavascript(WebView webView) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", SPUtils.getInstance().getString("userId"));
            jsonObject.put("compId", SPUtils.getInstance().getString("compId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("javascript:getUserId----" + jsonObject.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:window.getUserId('" + jsonObject.toString() + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
//                    ToastUtils.showShort(value);
                    LogUtils.e("javascript:getUserId---接受-" + value);
                }
            });

        }
    }

    private void initEvent() {
        mWebView.addJavascriptInterface(new JSCallJavaInterface(),"JSCallJava");
    }
    private Handler mHandler  = new Handler();
    /**
     * js调用java本地方法是子线程，需要注意
     */
    private final class JSCallJavaInterface{
        @JavascriptInterface
        public String callJava(final String msg){
            mHandler.post(new Runnable() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {

                    JSONObject jsonObject = new JSONObject();
                    try{

                        jsonObject.put("userId", SPUtils.getInstance().getString("userId"));
                        jsonObject.put("compId",SPUtils.getInstance().getString("compId"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d("MainActivity", "JS Call Java msg=" + msg);
                    String jsmethod = "javascript:javaCallJS(\'JavaCallJSMethod"+msg+"\')";
//          android 4.4 开始使用evaluateJavascript调用js函数 ValueCallback获得调用js函数的返回值
//                    mWebView.loadUrl(jsmethod);
                    mWebView.evaluateJavascript(jsmethod, new ValueCallback<String>() {

                        @Override
                        public void onReceiveValue(String value) {
                            Log.d("MainActivity", "onReceiveValue value=" + value);
                        }
                    });
                }
            });
            return "Java result "+msg;
        }

        //        android 4.4之前使用额外增加一个回调方法让js调用该函数返回函数结果，4.4开始可以使用ValueCallback
        @JavascriptInterface
        public void onJavaCallJSResult(String msg){
            Log.d("MainActivity","java call js result "+msg);
        }
    }


    // 下载完成
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downLoadCheck(DownLoadDataBean dataBean) {
        mWebView.loadUrl("javascript:downLoadFinish()");
        ToastUtils.showShort("下载完成");
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    //改写物理按键——返回的逻辑
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
     /*   if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }*/
        clearWebViewCache();
        super.onDestroy();

        if(mWebView != null){
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }

    @JavascriptInterface
    public void savePicture(String url, String name) {
//        RxToast.success("取到名字:" + name);
//        RxToast.success("取到链接:" + url);
        //将图片存放到本地的路径
//        String s = "E:\\工作专用\\大连规划\\图片分类\\信息动态\\综合信息\\";
        Log.e("savePicture", "取到名字: " + name);
        Log.e("savePicture", "取到链接: " + url.replace("\\", "/"));
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        intent.setData(Uri.parse(url));
//        this.startActivity(intent);
        if (downLoadMangerIsEnable(this)){
            savePhoto(url.replace("\\", "/"), name);
        }else {
            try {
                //Open the specific App Info page:
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + "com.android.providers.downloads"));
                startActivity(intent);
            } catch ( ActivityNotFoundException e ) {
                e.printStackTrace();
                //Open the generic Apps page:
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                startActivity(intent);
            }
        }


    }


    static boolean downLoadMangerIsEnable(Context context) {
        int state = context.getApplicationContext().getPackageManager()
                .getApplicationEnabledSetting("com.android.providers.downloads");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED);
        } else {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER);
        }
    }


    private void savePhoto(String imgUrl, final String imgName) {
        ToastUtils.showShort("开始下载");
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
////将下载任务加入下载队列，否则不会进行下载
        Uri uri = Uri.parse(imgUrl);//imgUrl:图片网络地址
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.allowScanningByMediaScanner();
        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);
// 在Notification显示下载进度
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
// 设置Title
        request.setTitle(imgName);
// 设置描述
        request.setDescription("正在下载图片...");
        request.allowScanningByMediaScanner();
        //不显示下载界面
        request.setVisibleInDownloadsUi(true);
//        request.setDestinationInExternalPublicDir("/DCIM/wx/", imgName);//name:图片名称，记得带后缀
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, imgName);//name:图片名称，记得带后缀
        //设置文件存放目录
//        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_PICTURES, imgName);
        long id = downloadManager.enqueue(request);
        SPUtils.getInstance().put( "onloadID", id);
    }

    // 程序sdcard目录
    public static String getSDCardAppCachePath(@NonNull Context context) {
        File file = context.getExternalCacheDir();
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }



    private void initWebView() {

        mWebView.clearCache(true);
        mWebView.setBarLayout(null);
        mWebView.setProgressBarView(null);
        mWebView.setTitleTextView(null);

//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setDomStorageEnabled(true); //重点是这个设置
//        mWebView.addJavascriptInterface(this, "$App");

//
////设置自适应屏幕，两者合用
        mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
////缩放操作
        mWebView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
////其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mWebView.getSettings().setAllowFileAccess(true); //设置可以访问文件
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);	//设置 缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(false);
        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//		String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i(TAG, "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(false);

//        mWebView.addJavascriptInterface(new JSCallJavaInterface(),"JSCallJava");
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){
        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                ActivityUtils.startActivity(PosterMaterListActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_back:
                ActivityUtils.finishActivity(WebViewActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        clearWebViewCache();
        super.onBackPressed();
        ActivityUtils.finishActivity(WebViewActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }



}

