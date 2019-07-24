package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.widget.webview.NativeInterface;
import com.zwx.scan.app.widget.webview.SafeWebChromeClient;
import com.zwx.scan.app.widget.webview.SafeWebView;
import com.zwx.scan.app.widget.webview.SafeWebViewClient;

import butterknife.BindView;
import butterknife.OnClick;

public class PayAuthAgreeActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.container)
    protected LinearLayout container;

    @BindView(R.id.web_view)
    protected SafeWebView mWebView;




 /*   @BindView(R.id.btn_cancel)
    protected Button btn_cancel;
    @BindView(R.id.btn_submit)
    protected Button btn_submit;

    @BindView(R.id.web_view)
    protected WebView webView;*/
//    protected AgentWeb mAgentWeb;
    protected String name;
    protected String userId;
    protected String identityNo; //身份证号
    //status N 下 process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
    protected String process;
    protected String status;
    protected Intent intent = null;
    protected String webParamUrl;

   /* @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }*/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_auth_agree;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("云商通用户协议");


       /* WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//设置JS可用
        //缓存
        settings.setDomStorageEnabled(true);  //// 开启 DOM storage API 功能
        settings.setAppCacheMaxSize(1024*1024*16);//存储的最大容量
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);

        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
//         settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //优先使用缓存:
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放

        try {
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
        }*/
//        webView.loadUrl("http://192.168.0.141:3625/#/templateList");
        //#/templateList
       /* webView.loadUrl(url);
//        webView.loadUrl(HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateList");
        webView.addJavascriptInterface(new JsInteration(), "control");//传递对象进行交互
//        webView.setWebChromeClient(new WebChromeClient() {});
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//当页面加载完成
                super.onPageFinished(view, url);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
                    callEvaluateJavascript(webView);
                }else {
                    callMethod(webView);
                }
            }

        });*/
    }

    protected void setUrl(){

        mWebView.loadUrl(webParamUrl);
//                mAgentWeb.getJsAccessEntrace().quickCallJs("CancelContract");  //取消
//        mAgentWeb.getJsAccessEntrace().quickCallJs("signContract");  //取消


       /* mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidMoreParams", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.i("Info","value:"+value);
            }
        },getJson(),"say:", " Hello! Agentweb");*/

//        webView.loadUrl(webParamUrl);
//        webView.loadUrl(HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateList");
//        webView.addJavascriptInterface(new JsInteration(), "control");//传递对象进行交互
//        webView.setWebChromeClient(new WebChromeClient() {});
       /* webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//当页面加载完成
                super.onPageFinished(view, url);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){//当Android SDK>=4.4时
//                    callEvaluateJavascript(webView);
                }else {
//                    callMethod(webView);
                }
            }

        });*/
    }

    @Override
    protected void initData() {

        //4.2 开启辅助功能崩溃
        mWebView.disableAccessibility(getApplicationContext());
        initListener();
        initinject();
//        mWebView.loadUrl("http://www.jianshu.com/u/fa272f63280a");
//        webParamUrl = HttpUrls.POSTER_BASE_URL+"jsp/mobile/index.html#/templateListShang";
//        mWebView.loadUrl("file:///android_asset/test.html");
        userId  = SPUtils.getInstance().getString("userId");
        presenter.signContractNotice(this,userId);

    }

    private void initListener() {

        mWebView.setWebViewClient(new SafeWebViewClient());

        mWebView.setWebChromeClient(new SafeWebChromeClient(){

            //捕获alter弹窗
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

//                ToastUtils.showShort("onJsAlert:"+message);

                if("签约失败".equals(message)){
                    setIntentActivity();

                }else {
                    setIntentComplateActivity();
                }
                //屏蔽alter弹窗， confirm   true必须结合使用
                 result.confirm();
                return true;
//                return super.onJsAlert(view, url, message, result);

            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
             /*   ToastUtils.showShort("onJsConfirm:"+message);

                if("签约失败".equals(message)){
                    setIntentActivity();

                }else {
                    setIntentComplateActivity();
                }*/
                return super.onJsConfirm(view, url, message, result);

            }
        });
    }
    private void initinject() {
        mWebView.addJavascriptInterface(new NativeInterface(this), "AndroidNative");   //name signContract
    }




    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                setIntentActivity();
                break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setIntentActivity();

    }


    private  void setIntentActivity(){
        intent = new Intent(PayAuthAgreeActivity.this,MainActivity.class);
        intent.putExtra("isIntentFragment","PersonalFragment");
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private  void setIntentComplateActivity(){
        intent = new Intent(PayAuthAgreeActivity.this,PayAuthComplateActivity.class);

        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
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




}
