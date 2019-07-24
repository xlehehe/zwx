package com.zwx.instalment.app.feature.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.zwx.instalment.app.R;
import com.zwx.instalment.app.feature.main.MainActivity;
import com.zwx.instalment.app.utils.SPUtils;
import com.zwx.instalment.app.widget.tablayout.utils.BarUtils;

import butterknife.BindView;

public class SplashActivity extends Activity {

    @BindView(R.id.iv_welcome)
    ImageView ivWel;

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private String isLogined = "NO";
    private boolean isFirst = false;

    private String isLogin = "YES";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewStatusBar();
        setContentView(getLayoutId());
        initView();
    }

    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoLoginPage();
            }
        },2000);
    }

    private void gotoLoginPage() {

    /*    isLogined = SPUtils.getInstance().getString("isLogined");

        if(isLogined != null &&!"".equals(isLogined)){
            if("YES".equals(isLogined)){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }

        }else {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        finish();*/
//        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        isFirst = SPUtils.getInstance().getBoolean("isFirst",true);

        if(isFirst){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));

        }else {
          /*  isLogin = SPUtils.getInstance().getString("isLogined");
            //物理返回键 从登录页面返回桌面 再次进入登录页面，否则主页
            if("YES".equals(isLogin)){
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }*/

            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        }
        finish();

    }


    /**
     * //去标题栏和状态栏
     * */
    protected void setViewStatusBar(){
        //全屏设置

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View mStatusBarTintView = new View(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight());
            mStatusBarTintView.setLayoutParams(params);
            mStatusBarTintView.setBackgroundColor(getResources().getColor(R.color.white));
            mStatusBarTintView.setVisibility(View.GONE);
//            mStatusBarTintView.setFitsSystemWindows(true);

            decorView.addView(mStatusBarTintView);
        }

    }
}
