package com.zwx.instalment.app.feature.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.BaseActivity;
import com.zwx.instalment.app.feature.main.MainActivity;
import com.zwx.instalment.app.feature.main.QDMainActivity;
import com.zwx.instalment.app.widget.tablayout.utils.BarUtils;
import com.zwx.instalment.app.widget.viewpager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends Activity {


    @BindView(R.id.viewPager)
    protected CustomViewPager viewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.tv_go)
    protected TextView tv_go;

    @BindView(R.id.btn)
    protected ImageView btnStart;
    private List<View> mViewList = new ArrayList<>();
    private int[]images={R.drawable.leader_page2,R.drawable.leader_page1,R.drawable.leader_page3};
    private List<ImageView> mDotsIV = new ArrayList<>();

    private String isLogined = "NO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewStatusBar();
        setContentView(getLayoutId());
        //绑定到butterknife
        ButterKnife.bind(this);
        initView();
    }


    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    protected void initView() {
        initData();
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

    protected void initData() {

        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);

            mViewList.add(imageView);
        }
        btnStart.setVisibility(View.INVISIBLE);

        setDotLayout(mViewList);

        viewPager.setAdapter(new ViewPagerAdatper(mViewList));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotsIV.size(); i++) {
                    if (i == position) {
                        mDotsIV.get(i).setImageResource(R.drawable.leader_dot_active);
                        if(position == 3){
                            btnStart.setVisibility(View.VISIBLE);
                        }else {
                            btnStart.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        mDotsIV.get(i).setImageResource(R.drawable.leader_dot);
                    }
                }

                //判断是否是最后一页，若是则显示按钮
                if (position == images.length - 1){
                    btnStart.setVisibility(View.VISIBLE);
                }else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, QDMainActivity.class));
               /* isLogined = SPUtils.getInstance().getString("isLogined");
                if(isLogined != null &&!"".equals(isLogined)){
                    if("YES".equals(isLogined)){ //登录
//                        startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                    }else {
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    }

                }else {
//                    startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                }*/
                finish();
            }
        });

        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    protected void setDotLayout(List<View> dataList){
        for (int i = 0; i < dataList.size(); i++) {
            ImageView dotIV = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 20;
            mDotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.leader_dot_active);
            } else {
                dotIV.setImageResource(R.drawable.leader_dot);
            }
            mDotsIV.add(dotIV);
        }

    }

}
