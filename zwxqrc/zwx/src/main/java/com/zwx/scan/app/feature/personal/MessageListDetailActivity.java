package com.zwx.scan.app.feature.personal;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.EncryptUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.member.MemberInfoDetailActivity;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageListDetailActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.web_view)
    protected WebView webView;

    String messageId;
    String userId;

    private String token;
    private String timestamp;
    private String signature;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getResources().getString(R.string.title_message_manage));
        userId = SPUtils.getInstance().getString("userId");
        messageId = getIntent().getStringExtra("messageId");
        Map<String, String> params = new HashMap<String, String>();
         token = SPUtils.getInstance().getString("token");
         timestamp=String.valueOf(new Date().getTime());
        params.put("id",messageId);
        params.put("userId",userId);
        params.put("token", token);
        params.put("timestamp", timestamp);
         signature = EncryptUtils.createSign(params);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//设置JS可用
//        webView.loadUrl("file://android_asset//index.html?userId ="+userId+"&token="+token+"&timestamp="+timestamp+"&signature="+signature+"&id="+messageId);
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
        }
//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl(HttpUrls.BASE_URL+"/html/index.html");

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

        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(MessageListDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(MessageListDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }



    private void callMethod(WebView webView) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id",messageId);
            jsonObject.put("userId",userId);
            jsonObject.put("token",token);
            jsonObject.put("signature",signature);
            jsonObject.put("timestamp",timestamp);
        }catch (Exception e){
            e.printStackTrace();
        }

        String call = "javascript:getInfo("+jsonObject.toString()+")";

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
            jsonObject.put("id",messageId);
            jsonObject.put("userId",userId);
            jsonObject.put("token",token);
            jsonObject.put("signature",signature);
            jsonObject.put("timestamp",timestamp);
        }catch (Exception e){
            e.printStackTrace();
        }



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

            webView.evaluateJavascript("getInfo("+jsonObject+")", new ValueCallback<String>() {

                @Override
                public void onReceiveValue(String value) {

                }});
        }

    }


}
