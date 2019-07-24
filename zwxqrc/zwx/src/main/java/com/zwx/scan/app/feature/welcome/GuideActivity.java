package com.zwx.scan.app.feature.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.utils.ImageUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {


    @BindView(R.id.viewPager)
    protected CustomViewPager viewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.btn)
    protected ImageView btnStart;
    private List<View> mViewList = new ArrayList<>();
    private int[]images={R.drawable.ic_guide_one,R.drawable.ic_guide_two,R.drawable.ic_guide_three};
    private List<ImageView> mDotsIV = new ArrayList<>();

    private String isLogined = "NO";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
        /*    DisplayMetrics disMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(disMetrics);
            int width = disMetrics.widthPixels;
            int height = disMetrics.heightPixels;
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), images[i]);//link the drable image
            ImageUtils.setImageBackground(bitmap,imageView,width,height);*/

            mViewList.add(imageView);
        }
        btnStart.setVisibility(View.GONE);

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
                        mDotsIV.get(i).setImageResource(R.drawable.ic_guide_blue);
                        if(position == 3){
                            btnStart.setVisibility(View.VISIBLE);
                        }else {
                            btnStart.setVisibility(View.GONE);
                        }
                    } else {
                        mDotsIV.get(i).setImageResource(R.drawable.ic_guide_gray);
                    }
                }

                //判断是否是最后一页，若是则显示按钮
                if (position == images.length - 1){
                    btnStart.setVisibility(View.VISIBLE);
                }else {
                    btnStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogined = SPUtils.getInstance().getString("isLogined");
                if(isLogined != null &&!"".equals(isLogined)){
                    if("YES".equals(isLogined)){ //登录
                        LogUtils.e("进入LoginActivity");
                        startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                    }else {
                        LogUtils.e("进入LoginActivity");
                        startActivity(new Intent(GuideActivity.this,MainActivity.class));
                    }

                }else {
                    LogUtils.e("进入LoginActivity");
                    startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                }
                finish();
                LogUtils.e("结束LoginActivity");
            }
        });

    }


    protected void setDotLayout(List<View> dataList){
        for (int i = 0; i < dataList.size(); i++) {
            ImageView dotIV = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 4;
            mDotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.ic_guide_blue);
            } else {
                dotIV.setImageResource(R.drawable.ic_guide_gray);
            }
            mDotsIV.add(dotIV);
        }

    }

}
