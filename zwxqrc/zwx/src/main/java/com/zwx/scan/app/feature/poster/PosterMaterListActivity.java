package com.zwx.scan.app.feature.poster;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.widget.webview.NativeInterface;
import com.zwx.scan.app.widget.webview.SafeWebChromeClient;
import com.zwx.scan.app.widget.webview.SafeWebView;
import com.zwx.scan.app.widget.webview.SafeWebViewClient;
import com.zwx.scan.app.widget.webview.ZpImageUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/09
 * desc   : 海报管理 ->我的素材
 * version: 1.0
 **/

/**
 * author : lizhilong
 * time   : 2019/04/13
 * desc   :
 * version: 1.0
 **/
public class PosterMaterListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.web_view)
    protected SafeWebView mWebView;


    String webParamUrl;
    @BindView(R.id.container)
    protected LinearLayout container;
    String compId;
    String userId;


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
    private BottomSheetDialog selectPicDialog;


   /* private long timeout = 5000;

    private Handler mHandler = new Handler();

    private Timer timer;*/
// Flag indicates that a loadUrl timeout occurred
    int loadUrlTimeout = 0;

    // LoadUrl timeout value in msec (default of 20 sec)
    protected int loadUrlTimeoutValue = 20000;
    // Load URL on UI thread
//    final DroidGap me = this;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_poster_manage_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的海报");
//        tvTitle.setText(getResources().getString(R.string.poster_manage));
//        tvTitle.setText(getResources().getString(R.string.poster_manage));
        tvRight.setVisibility(View.GONE);
        tvRight.setText("新建模板");
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        Map<String, String> params = new HashMap<String, String>();
        params.put("compId",compId);
        params.put("userId",userId);

  //WebSettings.LOAD_CACHE_ELSE_NETWORK 关闭webview中缓存
//        webView.loadUrl("file://android_asset//index.html?userId ="+userId+"&token="+token+"&timestamp="+timestamp+"&signature="+signature+"&id="+messageId);
        /*try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = settings.getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        webView.loadUrl("file:///android_asset/index.html");
//        webView.loadUrl(BASE_URL+"/index.html");
        webView.loadUrl(HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateListShang");
//        webView.loadUrl("http://192.168.0.141:3625/#/templateListShang");
        webView.addJavascriptInterface(new JsInteration(), "control");//传递对象进行交互
//        webView.setWebChromeClient(new WebChromeClient() {});
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//当页面加载完成
                super.onPageFinished(view, url);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
                    callEvaluateJavascript(webView);
                   *//* JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("compId","1");
                        jsonObject.put("userId","13292882314231808");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    webView.loadUrl("javascript:getUserId("+jsonObject.toString()+")");*//*
                }else {
                    callMethod(webView);
                }
            }

        });*/

    }

    @Override
    protected void initData() {

        //4.2 开启辅助功能崩溃
        mWebView.disableAccessibility(getApplicationContext());
        initWebSettings();
        initListener();
        initinject();
//        mWebView.loadUrl("http://www.jianshu.com/u/fa272f63280a");
        webParamUrl = HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateListShang";
        mWebView.loadUrl(webParamUrl);
    }
    private static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        if (webSettings == null) return;
        //设置字体缩放倍数，默认100
        webSettings.setTextZoom(100);
        // 支持 Js 使用
        webSettings.setJavaScriptEnabled(true);
        // 开启DOM缓存
        webSettings.setDomStorageEnabled(false);
        // 开启数据库缓存
        webSettings.setDatabaseEnabled(false);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(hasKitkat());
        // 设置 WebView 的缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(false);
        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
//        webSettings.setAppCacheMaxSize(20* 1024 * 1024);
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        // 数据库路径
        if (!hasKitkat()) {
            webSettings.setDatabasePath(getDatabasePath("html").getPath());
        }
        // 关闭密码保存提醒功能
        webSettings.setSavePassword(false);
        // 支持缩放
        webSettings.setSupportZoom(true);
        // 设置 UserAgent 属性
        webSettings.setUserAgentString("");
        // 允许加载本地 html 文件/false
        webSettings.setAllowFileAccess(true);
        // 允许通过 file url 加载的 Javascript 读取其他的本地文件,Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
        // Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        // 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
        webSettings.setAllowUniversalAccessFromFileURLs(false);
    }


    private void initListener() {
        mWebView.setWebViewClient(new SafeWebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
                    callEvaluateJavascript(mWebView);

                }else {
                    callMethod(mWebView);
                }

            /*    //超时处理
                LogUtils.d("testTimeout", "onPageStarted...........");
                super.onPageStarted(view, url, favicon);
                timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        *//*
                         * 超时后,首先判断页面加载进度,超时并且进度小于100,就执行超时后的动作
                         *//*
                        if (PosterMaterListActivity.this.mWebView.getProgress() < 10000) {
                            LogUtils.d("testTimeout", "timeout...........");
                            Message msg = new Message();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                            timer.cancel();
                            timer.purge();
                        }
                    }
                };
                timer.schedule(tt, timeout, 1);*/


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            /*    LogUtils.d("testTimeout", "onPageFinished+++++++++++++++++++++++++");
                LogUtils.d("testTimeout", "+++++++++++++++++++++++++"
                        + PosterMaterListActivity.this.mWebView.getProgress());
                timer.cancel();
                timer.purge();*/
            }
        });


        mWebView.setOpenFileChooserCallBack(new SafeWebChromeClient.OpenFileChooserCallBack() {
            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMsg = uploadMsg;
//                showSelectPictrueDialog(0, null);
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
    private void initinject() {
        mWebView.addJavascriptInterface(new NativeInterface(this), "AndroidNative");
    }

    private void showSelectPictruePop(final int tag,  WebChromeClient.FileChooserParams fileChooserParams) {

        View customView = View.inflate(this, R.layout.layout_poster_mater_upload_image, null);
//        PopWindow popWindow = new PopWindow(PosterMaterListActivity.this, "标题", null, PopWindow.PopWindowStyle.PopUp);

     /*   PopWindow popWindow = new PopWindow(this);
        popWindow.setIsShowCircleBackground(false);
        popWindow.setIsShowLine(false);
        popWindow.addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel));
        popWindow.addItemAction(new PopItemAction("选项1"));
        popWindow.addContentView(customView);
        popWindow.addItemAction(new PopItemAction("选项2"));
        popWindow.show();*/
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

    /**
     * 选择图片弹框
     */
    private void showSelectPictrueDialog(final int tag,  WebChromeClient.FileChooserParams fileChooserParams) {
        selectPicDialog = new BottomSheetDialog(this, R.style.Dialog_NoTitle);
        selectPicDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (mUploadMsgs != null) {
                    mUploadMsgs.onReceiveValue(null);
                    mUploadMsgs = null;
                }
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_select_pictrue, null);
        // 相册
        TextView album = view.findViewById(R.id.tv_select_pictrue_album);
        // 相机
        TextView camera = view.findViewById(R.id.tv_select_pictrue_camera);
        // 取消
        TextView cancel = view.findViewById(R.id.tv_select_pictrue_cancel);

        album.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (tag == 0) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    startActivityForResult(Intent.createChooser(i, "File Browser"), REQUEST_FILE_CHOOSER_CODE);
                } else {
                    try {
                        Intent intent = fileChooserParams.createIntent();
                        startActivityForResult(intent, REQUEST_SELECT_FILE_CODE);
                    } catch (ActivityNotFoundException e) {
                        mUploadMsgs = null;
                    }
                }
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeCameraPhoto();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicDialog.dismiss();
            }
        });

        selectPicDialog.setContentView(view);
        selectPicDialog.show();
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

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PosterMaterListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.tv_right:
                ActivityUtils.startActivity(PosterManageActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ActivityUtils.finishActivity(PosterMaterListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }



    private void callMethod(WebView webView) {
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("userId",userId);
            jsonObject.put("compId",compId);
        }catch (Exception e){
            e.printStackTrace();
        }

        String call = "javascript:getUserId("+jsonObject.toString()+")";

        call = "javascript:alertMessage(\"content\")";

        // call = "javascript:toastMessage(\"Hello World\")";

        //call = "javascript:sumToJava(1,2)";

        //call = "javascript:mult1(3,3)";

        webView.loadUrl(call);
    }

    public class JsInteration {

        @JavascriptInterface
        public void toastMessage(String message) {


            ToastUtils.showShort("toastMessage"+message);
        }

        @JavascriptInterface
        public void onSumResult(int result) {

            ToastUtils.showShort("onSumResult"+result);
        }

        @JavascriptInterface
        public void onMultResult(int result) {

            ToastUtils.showShort("onMultResult"+result);
        }
    }

    private void callEvaluateJavascript(WebView webView) {

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("userId",SPUtils.getInstance().getString("userId"));
            jsonObject.put("compId","1");
        }catch (Exception e){
            e.printStackTrace();
        }



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            LogUtils.e("-----------------"+jsonObject.toString());
            webView.evaluateJavascript("javascript:window.getUserId('"+jsonObject.toString()+"')", new ValueCallback<String>() {

                @Override
                public void onReceiveValue(String value) {
                }});
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (container != null) {
            container.removeView(mWebView);
        }
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.clearMatches();
            mWebView.clearHistory();
            mWebView.clearSslPreferences();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank");
            mWebView.removeAllViews();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mWebView.removeJavascriptInterface("AndroidNative");
            }
            mWebView.destroy();
        }
        mWebView = null;
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
