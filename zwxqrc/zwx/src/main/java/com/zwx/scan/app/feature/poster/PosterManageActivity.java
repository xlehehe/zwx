package com.zwx.scan.app.feature.poster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.NetworkUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.utils.AndroidBug5497Workaround;
import com.zwx.scan.app.widget.KeyBoardListener;
import com.zwx.scan.app.widget.webview.MyWebViewClient;
import com.zwx.scan.app.widget.webview.NativeInterface;
import com.zwx.scan.app.widget.webview.SafeWebChromeClient;
import com.zwx.scan.app.widget.webview.SafeWebView;
import com.zwx.scan.app.widget.webview.SafeWebViewClient;
import com.zwx.scan.app.widget.webview.ZpImageUtils;
import com.zwx.scan.app.widget.webview.safe.JavaScriptInterface;
import com.zwx.scan.app.widget.webview.safe.SafeWebViewTwo;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * author : lizhilong
 * time   : 2019/04/09
 * desc   : 海报管理
 * version: 1.0
 **/
public class PosterManageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.web_view)
    protected SafeWebView mWebView;


//    @BindView(R.id.main)
//    protected LinearLayout mRoot;

    private String  webParamUrl = HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateList";

    private String compId;
    private String userId;

    //图片上传
    public static final int REQUEST_SELECT_FILE_CODE = 100;
    private static final int REQUEST_FILE_CHOOSER_CODE = 101;
    private static final int REQUEST_FILE_CAMERA_CODE = 102;
    // 默认图片压缩大小（单位：K）
    public static final int IMAGE_COMPRESS_SIZE_DEFAULT = 400;
    // 压缩图片最小高度
    public static final int COMPRESS_MIN_HEIGHT = 900;
    // 压缩图片最小宽度
    public static final int COMPRESS_MIN_WIDTH = 675;

    private ValueCallback<Uri> mUploadMsg;
    private ValueCallback<Uri[]> mUploadMsgs;
    // 相机拍照返回的图片文件
    private File mFileFromCamera;


    private static final String APP_CACAHE_DIRNAME = "/cache";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poster_manage;
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        KeyBoardListener.getInstance(this).init();//即可解决全屏下，键盘遮挡问题。
        tvTitle.setText("新建海报");
        tvRight.setVisibility(View.GONE);
//        tvRight.setText("我的素材");
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        Map<String, String> params = new HashMap<String, String>();
        params.put("compId",compId);
        params.put("userId",userId);

        //4.2 开启辅助功能崩溃
//        mWebView.disableAccessibility(getApplicationContext());
//        AndroidBug5497Workaround.assistActivity(this);
        initWebView();
        initViewClient();
        mWebView.loadUrl(webParamUrl);
    }

    @Override
    protected void initData() {

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
                ActivityUtils.finishActivity(PosterManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PosterManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }






    private void initinject() {
        mWebView.addJavascriptInterface(new JavaScriptInterface(mWebView), "Android");
    }


    private void initWebView() {
//        mWebView = new SafeWebView(getApplicationContext());

        mWebView.clearCache(true);

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

//        mRoot.addView(mWebView);
    }

   /* private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        if (webSettings == null) return;
        //设置字体缩放倍数，默认100
        webSettings.setTextZoom(100);

        // 支持 Js 使用
        webSettings.setJavaScriptEnabled(true);
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


        // 开启DOM缓存
        webSettings.setDomStorageEnabled(true);
        // 开启数据库缓存
        webSettings.setDatabaseEnabled(false);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(hasKitkat());
        // 设置 WebView 的缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
//        webSettings.setAppCacheMaxSize(50* 1024 * 1024);
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
//        String cacheDirPath = this.getFilesDir().getAbsolutePath()+"/cache/";
        String cacheDirPath = getCacheDir().getAbsolutePath();
        // 1. 设置缓存路径
        webSettings.setAppCachePath(cacheDirPath);
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(false);

        // 支持缩放
//        webSettings.setSupportZoom(true);
        // 设置 UserAgent 属性
//        webSettings.setUserAgentString("");
        // 允许加载本地 html 文件/false

        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        // 允许通过 file url 加载的 Javascript 读取其他的本地文件,Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
        // Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        // 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
        webSettings.setAllowUniversalAccessFromFileURLs(false);

    }*/

    private void initViewClient() {
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.setWebViewClient(new WebViewClient(){
    /*        @Override
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
                return false;
            }
*/

            @Override
            public void  onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //设定加载开始的操作
               /* LogUtils.e("onPageStarted---"+ url);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
                    callEvaluateJavascript(mWebView);
                }*/


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


        mWebView.setOpenFileChooserCallBack(new SafeWebChromeClient.OpenFileChooserCallBack() {
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMsg = uploadMsg;
                showSelectPictruePop(0, null);
            }

            @Override
            public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (mUploadMsgs != null) {
                    mUploadMsgs.onReceiveValue(null);
                }
                mUploadMsgs = filePathCallback;
                showSelectPictruePop(1, fileChooserParams);
            }
        });
    }

    private void callEvaluateJavascript(WebView webView) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", SPUtils.getInstance().getString("userId"));
            jsonObject.put("compId", SPUtils.getInstance().getString("compId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:window.getUserId('" + jsonObject.toString() + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
//                    ToastUtils.showShort(value);
                    LogUtils.e("javascript:getUserId----" + value);
                }
            });

        }
    }



    @Override
    protected void onResume() {

        super.onResume();
//        mWebView.onResume();
//        mWebView.resumeTimers();
    }

    @Override
    protected void onPause() {
//        mWebView.onPause();
//        mWebView.pauseTimers();
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebView();
    }



    private void clearWebView(){

       /* if(mRoot != null){
            mRoot.removeAllViews();
        }*/
        if (mWebView != null) {
//            mWebView.stopLoading();
      /*      mWebView.clearMatches();
//            mWebView.clearHistory();
            mWebView.clearSslPreferences();
//            mWebView.clearCache(true);
//            mWebView.loadUrl("about:blank");
            mWebView.removeAllViews();
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                mWebView.removeJavascriptInterface("Android");
//            }*/
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            mWebView.destroy();
        }
        mWebView = null;
    }


    //改写物理按键——返回的逻辑
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= 19;
    }


    private void showSelectPictruePop(final int tag,  WebChromeClient.FileChooserParams fileChooserParams) {


        PopWindow popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setIsShowCircleBackground(false)  //禁止圆角
                .addItemAction(new PopItemAction("从相机中选择", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (tag == 0) {
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.addCategory(Intent.CATEGORY_OPENABLE);
                            i.setType("*/*");
                            startActivityForResult(Intent.createChooser(i, "File Browser"), REQUEST_FILE_CHOOSER_CODE);
                        } else {
                            try {

                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                                    Intent intent = fileChooserParams.createIntent();
                                    startActivityForResult(intent, REQUEST_SELECT_FILE_CODE);
                                }

                            } catch (ActivityNotFoundException e) {
                                mUploadMsgs = null;
                            }
                        }
                    }
                }))
                .addItemAction(new PopItemAction("拍摄", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        takeCameraPhoto();
                    }
                }))
                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (mUploadMsgs != null) {
                            mUploadMsgs.onReceiveValue(null);
                            mUploadMsgs = null;
                        }
                    }
                })).create();

        popWindow.show();

    }

    public void takeCameraPhoto() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Toast.makeText(this, "设备无摄像头", Toast.LENGTH_SHORT).show();
            return;
        }

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

//        File storageDir = new File(filePath);
//        storageDir.mkdirs();
        mFileFromCamera = new File(filePath, System.nanoTime() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUrl;
        if (getApplicationInfo().targetSdkVersion > Build.VERSION_CODES.M) {  //19系统版本
            String authority = "com.zwx.scan.app.widget.webview.UploadFileProvider";
            imgUrl = FileProvider.getUriForFile(this, authority, mFileFromCamera);
        } else {
            imgUrl = Uri.fromFile(mFileFromCamera);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);
        startActivityForResult(intent, REQUEST_FILE_CAMERA_CODE);
    }


    /**
     * 处理相机返回的图片
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void takePictureFromCamera() {
        if (mFileFromCamera != null && mFileFromCamera.exists()) {
            String filePath = mFileFromCamera.getAbsolutePath();
            // 压缩图片到指定大小
            File imgFile = ZpImageUtils.compressImage(this, filePath, COMPRESS_MIN_WIDTH, COMPRESS_MIN_HEIGHT, IMAGE_COMPRESS_SIZE_DEFAULT);

            Uri localUri = Uri.fromFile(imgFile);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            this.sendBroadcast(localIntent);
            Uri result = Uri.fromFile(imgFile);

            if (mUploadMsg != null) {
                mUploadMsg.onReceiveValue(Uri.parse(filePath));
                mUploadMsg = null;
            }
            if (mUploadMsgs != null) {
                mUploadMsgs.onReceiveValue(new Uri[]{result});
                mUploadMsgs = null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SELECT_FILE_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mUploadMsgs == null) {
                        return;
                    }
                    mUploadMsgs.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                    mUploadMsgs = null;
                }
                break;
            case REQUEST_FILE_CHOOSER_CODE:
                if (mUploadMsg == null) {
                    return;
                }
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                mUploadMsg.onReceiveValue(result);
                mUploadMsg = null;
                break;
            case REQUEST_FILE_CAMERA_CODE:
                takePictureFromCamera();
                break;
        }
    }

}
