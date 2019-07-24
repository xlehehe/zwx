package com.zwx.scan.app.feature.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.tablayout.listener.CustomTabEntity;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ScreenUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.base.adapter.CommonListAdapter;
import com.zwx.scan.app.base.adapter.CommonListHolder;
import com.zwx.scan.app.data.bean.CommonImageBean;
import com.zwx.scan.app.data.bean.Index;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.TResource;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffRewardAnalysisActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.feature.verificationrecord.ChartFragment;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.widget.CustomPopWindow;
import com.zwx.scan.app.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.ll_member)
    protected LinearLayout llMember;

    @BindView(R.id.ll_campaign)
    protected LinearLayout llCampaign;

    @BindView(R.id.ll_staff)
    protected LinearLayout llStaff;

    @BindView(R.id.tv_store)
    protected TextView tvStore;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_right)
    protected ImageView ivRight;

    @BindView(R.id.ptr)
    protected PullToRefreshScrollView ptr;


    @BindView(R.id.tv_member_cur)
    protected TextView tv_member_cur;

    @BindView(R.id.tv_member_total)
    protected TextView tv_member_total;

    @BindView(R.id.tv_campaign_cur)
    protected TextView tv_campaign_cur;


    @BindView(R.id.tv_campaign_total)
    protected TextView tv_campaign_total;

    @BindView(R.id.tv_staff_cur)
    protected TextView tv_staff_cur;

    @BindView(R.id.tv_staff_total)
    protected TextView tv_staff_total;

    @BindView(R.id.ll_resource)
    protected LinearLayout llResource;


     @BindView(R.id.tv_date)
    protected TextView tvDate;

    @BindView(R.id.tv_label)
    protected TextView tvLabel;
    @BindView(R.id.pager)
    protected CustomViewPager viewPager;
    @BindView(R.id.grid_view)
    protected MyGridView gridView;


    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;


     protected FPagerAdapter pageAdapter;
    protected  String date="";
    protected  String title="";

    private CustomPopWindow popWindow;
    protected PopWindow shopPop;
    private  String brandLogo;
    private String brandId;
    private String brandName;

    public List<Store> stores  = new ArrayList<>();
    protected List<TResource> resources = new ArrayList<>();
    protected List<Map<String,Object>> memberReport = new ArrayList<>();
    protected List<Map<String,Object>> campaignReport = new ArrayList<>();
    protected List<Map<String,Object>> staffReport = new ArrayList<>();

    protected List<CommonImageBean> resourcesList = new ArrayList<>();
    protected HomeModuleGridViewAdapter moduleGridViewAdapter;
    private String param;

    private Camera camera;

    private Disposable disposable;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    protected String userId;
    private String storeId;

    private String storeName;

    protected String isPopAndDialog = "1";


    private  boolean isRefresh = false;
    private RxPermissions rxPermissions = null;
  /*  @Inject
    HomePresenter presenter;*/
  private static final int REQUEST_PERMISSION = 0;

    private List<ImageView> mDotsIV = new ArrayList<>();

    protected Index index = new Index();
    public HomeFragment() {
    }


    public static HomeFragment getInstance(String param) {
        HomeFragment fragment = new HomeFragment();
        fragment.param = param;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInjector() {
        DaggerHomeComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setData();
    }
    @Override
    protected void initView() {


        ivBack.setVisibility(View.GONE);

        tvStore.setVisibility(View.VISIBLE);
        tvTitle.setText("趣管家");
        ivRight.setBackgroundResource(R.mipmap.ic_scan);
        ivRight.setVisibility(View.VISIBLE);

        tvLabel.setText("本周会员增长趋势");


        initTab();
//        setPageAdapter();

        viewPager.setCurrentItem(0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            llResource.setMinimumHeight(400);

        }

        moduleGridViewAdapter = new HomeModuleGridViewAdapter(getActivity(),resourcesList);
        gridView.setAdapter(moduleGridViewAdapter);
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//自动出现下拉布局
//        ptr.setRefreshing(PullToRefreshBase.Mode.PULL_FROM_END);//自动出现上拉布局
        ILoadingLayout endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");// 下来达到一定距离时，显示的提示

        userId = SPUtils.getInstance().getString("userId");
        rxPermissions = new RxPermissions(getActivity());
    }


    @Override
    protected void initData() {
//        requestCodeQRCodePermissions();

    }
   /* private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//                PushManager.getInstance().initialize(getActivity().getApplicationContext(), GeTuiPushService.class);
            } else {
                LogUtils.e(TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
//                PushManager.getInstance().initialize(getActivity().getApplicationContext(), GeTuiPushService.class);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/

    protected  void setData(){
        userId = SPUtils.getInstance().getString("userId");
        storeId = SPUtils.getInstance().getString("storeId");
        storeName = SPUtils.getInstance().getString("storeName");
        if(userId == null){
            ToastUtils.showShort("暂无用户，请退出重新登录");
            return;
        }

//        presenter.queryStore(getActivity(),userId,isRefresh);
        if(storeId !=null && storeId.length()>0){
            tvStore.setText(storeName);
            isPopAndDialog = "0";
            presenter.index(getActivity(),storeId,isRefresh);

        }else {
            isPopAndDialog = "1";
            presenter.queryStore(getActivity(),userId,isRefresh);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = true;
        setData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }



    private void initTab() {

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonImageBean imageBean = resourcesList.get(position);
                Intent intent = new Intent();

                if(imageBean !=null){
                    String code = imageBean.getType();
                    intent.putExtra("title",imageBean.getName());

                    if("app:verification".equals(code)){

                        rxPermissions
                                .request(Manifest.permission.CAMERA)
                                .subscribe(granted -> {
                                    if (granted) {
                                        intent.setClass(getActivity(),VerificationActivity.class);
                                        intent.putExtra("fromHomeFragmentIntent","YES");
                                        ActivityUtils.startActivity(intent,
                                                R.anim.slide_in_right, R.anim.slide_out_left);

                                    } else {
                                        ToastUtils.showShort(getActivity().getResources().getText(R.string.expermission_camera));
                                        return;
                                    }
                                });
                    }else if("app:verificationrecord".equals(code)){
                        intent.setClass(getActivity(),VerificationRecordActivity.class);
                        intent.putExtra("fromHomeFragmentIntent","YES");
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                    }else if("app:countanalysis".equals(code)){
                        intent.setClass(getActivity(),CountAnalysisHomeActivity.class);
                        intent.putExtra("fromHomeFragmentIntent","YES");
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                    }else if("app:more".equals(code)){
                        intent.setClass(getActivity(), ModuleMoreListActivity.class);
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                    }



                }
            }
        });


    }


    @OnClick({R.id.iv_right,R.id.tv_store,R.id.ll_member,R.id.ll_campaign,R.id.ll_staff})
    @Override
    public void onClick(View v) {
        Intent  intent = new Intent();
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        switch (v.getId()) {
            case R.id.iv_right:

                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {
                                intent.setClass(getActivity(),VerificationActivity.class);
                                intent.putExtra("title","二维码扫描");
                                intent.putExtra("fromHomeFragmentIntent","YES");
                                intent.putExtra("rightTopVerification","YES");
                                ActivityUtils.startActivity(intent,
                                        R.anim.slide_in_right, R.anim.slide_out_left);

                            } else {
                                // At least one permission is denied
                                ToastUtils.showShort(getActivity().getResources().getText(R.string.expermission_camera));
                                return;
                            }
                        });

                break;

            case R.id.ll_member:
                intent.setClass(getActivity(),MemberAnalysisActivity.class);

                intent.putExtra("brandLogo",brandLogo);
                intent.putExtra("isShowOneStore","YES");
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);
//                ToastUtils.showShort("敬请期待！");
//                llMember.setPressed(true);
//                llCampaign.setPressed(false);
//                llStaff.setPressed(false);
                break;
            case R.id.ll_campaign:
                intent.setClass(getActivity(),CampaignAnalysisActivity.class);
                intent.putExtra("brandLogo",brandLogo);
                intent.putExtra("isShowOneStore","YES");
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.ll_staff:
                intent.setClass(getActivity(),StaffRewardAnalysisActivity.class);
                intent.putExtra("brandLogo",brandLogo);
                intent.putExtra("isShowOneStore","YES");
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tv_store:


                if(stores != null && stores.size()>0){
                    showPopView(getActivity());
                }else {
                    isPopAndDialog = "0";
                    presenter.queryStore(getActivity(),userId,isRefresh);

                }



                break;
        }
    }


    @Override
    public void onDestroyView() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseCamera();
    }


    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    /**
     * 切换店铺
     * */
    protected void  showPopView(Context context){

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_store_list,null);
        //创建并显示popWindow
      /*  popWindow= new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
//                .enableBackgroundDark(true)   //背景是否变暗
//                 .setBgDarkAlpha(0.7f) //调整亮度
//                .enableOutsideTouchableDissmiss(false)
                 .setOutsideTouchable(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(tvStore);*/
       shopPop= new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(tvStore);

        ListView listView = contentView.findViewById(R.id.lv_shop);
        final SelectStoreAdapter myAdapter = new SelectStoreAdapter(context);
        myAdapter.setDatas(stores);
        listView.setAdapter(myAdapter);
        String storeName = tvStore.getText().toString().trim();
        //判断默认选择的店铺为true;否则false
        for(Store store : stores){
           if(store.getStoreName().equals(storeName)){
               store.setChecked(true);
           }else{
               store.setChecked(false);
           }

        }
        myAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                brandLogo = HttpUrls.BRAND_LOGO_ULR+stores.get(position).getBrandLogo();
                brandId = stores.get(position).getBrandId();
                brandName =stores.get(position).getBrandName();
                if(currentNum == -1){ //选中
                    stores.get(position).setChecked(true);
                    currentNum = position;
                    tvStore.setText(stores.get(position).getStoreName());
                    SPUtils.getInstance().put("storeId",String.valueOf(stores.get(position).getStoreId()));
                    SPUtils.getInstance().put("storeName",stores.get(position).getStoreName());
                    //统计分析模块用到
                    SPUtils.getInstance().put("storeIdArray",stores.get(position).getStoreId()+"");
                    SPUtils.getInstance().put("storeNameArray",stores.get(position).getStoreName());

                    SPUtils.getInstance().put("compId",stores.get(position).getCompId()+"");
                    SPUtils.getInstance().put("brandId",brandId);
                    SPUtils.getInstance().put("brandLogo",brandLogo);
                    SPUtils.getInstance().put("brandName",brandName);

                }else if(currentNum == position){ //同一个item选中变未选中
                    for(Store store : stores){
                        store.setChecked(false);
                    }
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(Store store : stores){
                        store.setChecked(false);
                    }
                    stores.get(position).setChecked(true);
                    currentNum = position;

                    tvStore.setText(stores.get(position).getStoreName());
                    SPUtils.getInstance().put("storeId",stores.get(position).getStoreId()+"");
                    SPUtils.getInstance().put("storeName",stores.get(position).getStoreName());
                    //统计分析模块用到
                    SPUtils.getInstance().put("storeIdArray",stores.get(position).getStoreId()+"");
                    SPUtils.getInstance().put("storeNameArray",stores.get(position).getStoreName());
                    SPUtils.getInstance().put("brandLogo",brandLogo);
                    SPUtils.getInstance().put("brandId",brandId);
                    SPUtils.getInstance().put("brandName",stores.get(position).getBrandName());
                    SPUtils.getInstance().put("compId",stores.get(position).getCompId()+"");
                    presenter.index(context,stores.get(position).getStoreId()+"",isRefresh);
                }
                presenter.index(context,stores.get(position).getStoreId()+"",isRefresh);

//                setData();

                myAdapter.notifyDataSetChanged();
                shopPop.dismiss();

            }
        });
    }

    protected  void showSelectStore(Context context){

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight =dm.heightPixels;
        // 将pixels转为dip
        int xoffInDip = ScreenUtils.px2dip(context,screenHeight);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_store_dialog,null);
        //创建并显示popWindow
         popWindow =  new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
                .enableBackgroundDark(true)   //背景是否变暗
                 .setBgDarkAlpha(0.5f) //调整亮度
//                 .enableOutsideTouchableDissmiss(false)
                 .setOutsideTouchable(false)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAtLocation(contentView,Gravity.CENTER_HORIZONTAL,0,0);


        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        StoreList2Adapter adapter = new StoreList2Adapter(getActivity(),stores);
        listView.setAdapter(adapter);
     /*   listView.setAdapter(new CommonListAdapter<Store>(getActivity(),stores,R.layout.item_home_store_content) {
            @Override
            protected void convertView(View item,Store store) {
                popWindow.dissmiss();
                TextView tvBrandId= CommonListHolder.get(item,R.id.tv_brand_id);
                tvBrandId.setText(store.getStoreId());
                TextView tvBrand= CommonListHolder.get(item,R.id.tv_brand_name);
                TextView tvStore=CommonListHolder.get(item,R.id.tv_store_name);
                tvBrand.setText(store.getBrandName());
                tvStore.setText(store.getStoreName());
            }
        });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isPopAndDialog = "0";  //是否弹窗展示店铺选择
                if(stores!=null && stores.size()>0){
                    Store store = stores.get(position);

                    if(store != null){


                        SPUtils.getInstance().put("storeId",store.getStoreId()+"");
                        SPUtils.getInstance().put("brandLogo", HttpUrls.BRAND_LOGO_ULR+store.getBrandLogo());
                        SPUtils.getInstance().put("brandName",store.getBrandName());
                        SPUtils.getInstance().put("brandId",store.getBrandId());
                        SPUtils.getInstance().put("compId",store.getCompId()+"");
                        SPUtils.getInstance().put("storeName",store.getStoreName());
                        tvStore.setText(store.getStoreName());
                        if(store.getStoreId() != null ){
                            storeId = String.valueOf(store.getStoreId());
                            presenter.index(getActivity(),storeId,isRefresh);
                        }

                    }

                }
                popWindow.dissmiss();

            }
        });
    }


    public void setPageAdapter(){
        pageAdapter = new FPagerAdapter(getChildFragmentManager(),index);
        viewPager.setAdapter(pageAdapter);

      viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

          }

          @Override
          public void onPageSelected(int position) {
              if(position == 0){
                  tvLabel.setText("本周会员增长趋势");
              }else if(position == 1){
                  tvLabel.setText("本周消费者参与活动数量趋势");
              }else if(position == 2){
                  tvLabel.setText("本周员工拉新数量趋势");
              }
            setMoveDot(position);
          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
        setDotLayout();

    }

    private void setMoveDot(int position){
        for (int i = 0; i < mDotsIV.size(); i++) {
            if (i == position) {
                mDotsIV.get(i).setImageResource(R.drawable.dot_focus);
            } else {
                mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
    }

    protected void setDotLayout(){
        //先删除
        mDotsIV.clear();
        mDotsLayout.removeAllViews();
        for (int i = 0; i < 3; i++) {
            ImageView dotIV = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 4;
            mDotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.dot_focus);
            } else {
                dotIV.setImageResource(R.drawable.dot_blur);
            }
            mDotsIV.add(dotIV);
        }

    }


    private class PageAdapter extends FragmentPagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLegend",false);
            bundle.putString("date","week");
            switch(position) {
                case 0:
                    title = "本周会员增长趋势";
                    f = ChartFragment.getInstance(title,memberReport);

                    f.setArguments(bundle);
                    break;
                case 1:
                    title ="本周消费者参与活动数量趋势";
                    f = ChartFragment.getInstance(title,campaignReport);
                    f.setArguments(bundle);
                    break;
                case 2:
                    title = "本周员工拉新数量趋势";
                    f = ChartFragment.getInstance(title,staffReport);
                    f.setArguments(bundle);
                    break;

            }
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private String isTitlePager1 = "本周会员增长趋势";
    private String isTitlePager2 = "本周消费者参与活动数量趋势";

    private String isTitlePager3 = "本周员工拉新数量趋势";

    public   class FPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        public FPagerAdapter(FragmentManager fm, Index index) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(index != null){
//                memberReport = new ArrayList<>();
//                campaignReport = new ArrayList<>();
//                staffReport = new ArrayList<>();
                memberReport = index.getMemberTotalReport();
                campaignReport = index.getCampaignTotalReport();
                staffReport = index.getStaffTotalReport();
                Fragment fragment1 = ChartFragment.getInstance(isTitlePager1,memberReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",true);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager1);
                fragment1.setArguments(bundle);
                Fragment fragment2=ChartFragment.getInstance(isTitlePager2,campaignReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",true);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager2);
                fragment2.setArguments(bundle);

                Fragment fragment3=ChartFragment.getInstance(isTitlePager3,staffReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",true);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager3);
                fragment3.setArguments(bundle);
                mFragmentList.add(fragment1);
                mFragmentList.add(fragment2);
                mFragmentList.add(fragment3);
                setFragments(mFragmentList);
            }

        }

        /*  public void updateData(List<String> titles,List<Map<String,Object>> dataList) {
              ArrayList<Fragment> fragments = new ArrayList<>();
              for (int i = 0, size = titles.size(); i < size; i++) {
                  Log.e("FPagerAdapter1", titles.get(i).toString());
                  String title = titles.get(i);
                  fragments.add(CommonChartFragment.getInstance(title,date,dataList));
              }
              setFragments(fragments);
          }*/
        public void updateData(Index index) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(index != null){
                memberReport = index.getMemberTotalReport();
                campaignReport = index.getCampaignTotalReport();
                staffReport = index.getStaffTotalReport();
                Fragment fragment1 =ChartFragment.getInstance(isTitlePager1,memberReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",false);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager1);
                fragment1.setArguments(bundle);
                Fragment fragment2=ChartFragment.getInstance(isTitlePager2,campaignReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",false);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager2);
                fragment2.setArguments(bundle);

                Fragment fragment3=ChartFragment.getInstance(isTitlePager3,staffReport);
                bundle  = new Bundle();
                bundle.putBoolean("isLegend",false);
                bundle.putString("date",date);
                bundle.putString("title",isTitlePager3);
                fragment3.setArguments(bundle);
                fragments.add(fragment1);
                fragments.add(fragment2);
                fragments.add(fragment3);
                setFragments(fragments);
            }

        }


        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
//                fragmentTransaction.commit();
                fragmentTransaction.commitAllowingStateLoss();   //解决 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState异常

                mFragmentManager.executePendingTransactions();
            }
            this.mFragmentList = mFragmentList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

    }


}
