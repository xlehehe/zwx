package com.zwx.scan.app.feature.main;


import android.Manifest;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zwx.library.tablayout.CommonTabLayout;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.tablayout.listener.TabEntity;
import com.zwx.library.tablayout.widget.BanViewPager;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.MyPagerAdapter;
import com.zwx.scan.app.feature.home.HomeFragment;
import com.zwx.scan.app.feature.personal.PersonalThreeFragment;

import org.apache.log4j.Logger;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    //初始化Logger
    protected static Logger log = Logger.getLogger(MainActivity.class);
    private Context mContext = this;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mTitles = {"首页",  "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_unselected,
            R.drawable.ic_my_unselect};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_selected,
            R.drawable.ic_my_selected};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;

    @BindView(R.id.view_pager)
    protected BanViewPager mViewPager;
    @BindView(R.id.tab_layout)
    protected CommonTabLayout mTabLayout;
    private RxPermissions rxPermissions = null;
//    private static final int REQUEST_PERMISSION = 0;

    private long firstClick = 0;

    private String isIntentFragment = "";

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        initTab();
    }

    @Override
    protected void initData() {
        rxPermissions  = new RxPermissions(this);

        rxPermissions
                .request(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {

                    } else {
                        // At least one permission is denied
                        return;
                    }
                });

    }



/*    private void initGeTui(){
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestCodeQRCodePermissions();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        }

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
    }*/


    private void initTab(){
        for (String title : mTitles) {
            if(title == "首页"){
                fragments.add(HomeFragment.getInstance(title));
            }else {
                fragments.add(PersonalThreeFragment.getInstance(title));
            }

        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mViewPager.setAdapter(new MyPagerAdapter(fragments,mTitles,getSupportFragmentManager()));
        tabLayout();
    }


    private void tabLayout() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        isIntentFragment = getIntent().getStringExtra("isIntentFragment");
        if("PersonalFragment".equals(isIntentFragment)){
            mTabLayout.setCurrentTab(1);
            mViewPager.setCurrentItem(1);
        }else {
            mTabLayout.setCurrentTab(0);
            mViewPager.setCurrentItem(0);
        }
    }




    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                ToastUtils.showShort(getResources().getText(R.string.quit));
                firstClick = secondClick;
                return true;
            } else {
                //退出应用
//                ActivityUtils.finishAllActivities();
                moveTaskToBack(true);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
