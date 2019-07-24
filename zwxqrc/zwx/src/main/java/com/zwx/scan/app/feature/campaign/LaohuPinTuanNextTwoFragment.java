package com.zwx.scan.app.feature.campaign;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.CouponMaterial;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.cateringinfomanage.GridImageAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaohuPinTuanNextTwoFragment extends BaseFragment<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener,LaohuPinTuanCouponFragment.ChanageLhPtTwoLayoutLisenter{


    @BindView(R.id.ll_prize)
    protected LinearLayout ll_prize;


    @BindView(R.id.fl_prize)
    protected FrameLayout fl_prize;
    @BindView(R.id.ll_un_prize)
    protected LinearLayout ll_un_prize;

    @BindView(R.id.iv_prize)
    protected ImageView iv_prize;
    @BindView(R.id.tv_prize_image_label)
    protected TextView tv_prize_image_label;


    @BindView(R.id.btn_add_coupon)
    protected Button btnAddCoupon;

    @BindView(R.id.coupon_view_pager)
    protected CustomViewPager couponViewPager;

    @BindView(R.id.setting_view_pager)
    protected NoScrollViewPager setViewPager;
    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;
    @BindView(R.id.ll_add_top)
    protected LinearLayout llAddTop;

    @BindView(R.id.ll_bottom_coupon_edit)
    protected LinearLayout llBottom;


    @BindView(R.id.tv_prize_num)
    protected TextView tv_prize_num;

    @BindView(R.id.edt_day_one)
    protected EditText edt_day_one;
    @BindView(R.id.ll_prize_count)
    protected LinearLayout ll_prize_count;

    @BindView(R.id.tv_lh_ge_panda_label)
    protected TextView tv_lh_ge_panda_label;
    @BindView(R.id.iv_lh_ge)
    protected ImageView iv_lh_ge;

    @BindView(R.id.fab)
    protected ImageButton fab;
    //奖项名称
    private String name;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;

    protected ArrayList<Coupon> couponList = new ArrayList<>();

    protected  ArrayList<Coupon> couponList1 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList2 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList3 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList4 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList5 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList6 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList7 = new ArrayList<>();
    protected  ArrayList<Coupon> couponList8 = new ArrayList<>();
    private List<ImageView> mDotsIV = new ArrayList<>();


    private String isNextTwoAndThree = "YES";


    protected  static ArrayList<CampaignCoupon> rewardCouponList = new ArrayList<>();


    protected CampaignGamesetreward  campaignGamesetreward = new CampaignGamesetreward();


    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    protected   String storeIdCouponType ;
    private  LaohuPinTuanNextSettingFragment nextSettingFragment = new LaohuPinTuanNextSettingFragment();
    protected String prizePath  ;
    protected String prizeUrlId;  //奖项id
    //****************未中奖****************

    @BindView(R.id.rv_photo)
    protected RecyclerView rvPhoto;

    @BindView(R.id.tv_photo_num)
    protected TextView tv_photo_num;

    int selectNum = 1;
    private FullyGridLayoutManager manager;
    protected PrizeGridImageAdapter adapter;

    protected List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 6;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;


    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;

    public List<File> fileList = new ArrayList<>();


    protected CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();

    //相册
    public  static File file;
    public static Uri uri;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;


    private String prizeCount; //奖项次数
    private Integer count;
    private String imageId;
    private String prizeUrl;
    private LaohuPinTuanNextTwoActivity activity =null;

    private String prizeImageLabel = "请问你想在游戏中，用什么图片，代表";
    private String pandaLabel =",你想设置什么优惠券,作为奖品";
    private boolean isPrepared = false;
    private String userId;
    private String compId;
    private String id;  //素材Id

    private String isUnPrize;

    private String campaignTypeId;


    public LaohuPinTuanNextTwoFragment() {
        // Required empty public constructor
    }

    public static LaohuPinTuanNextTwoFragment getInstance(String name,ArrayList<Coupon> couponList,String prizePath){
        LaohuPinTuanNextTwoFragment nextTwoFragment = new LaohuPinTuanNextTwoFragment();
        nextTwoFragment.name = name;
        nextTwoFragment.couponList = couponList;
        nextTwoFragment.prizePath = prizePath;
        return nextTwoFragment;

    }


   @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_laohu_pin_tuan_next_two;
    }

    @Override
    protected void initInjector() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        //模板素材 选择之后返回经过onresume方法重新赋值
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            id = "0";
        }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
            id = "1";
        }
        if("奖项一".equals(name)){ //奖项1 ~6
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            isUnPrize = "Prize1";
        }else if("奖项二".equals(name)){ //奖项1 ~6
            prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
            isUnPrize = "Prize2";
        }else if("奖项三".equals(name)){ //奖项1 ~6
            prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
            isUnPrize = "Prize3";
        }else if("奖项四".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
            isUnPrize = "Prize4";
        }else if("奖项五".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
            isUnPrize = "Prize5";
        }else if("奖项六".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
            isUnPrize = "Prize6";
        }else if("安慰奖".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
            isUnPrize = "Prize7";
        }/*else if("未中奖".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath8;
            isUnPrize = "Prize8";
        }*/
       /* if(!"未中奖".equals(name)){
            setImage();
        }*/


    }
    protected void setImage(){
        /*Glide.with(getActivity()).load(prizePath).into(new SimpleTarget<Drawable>(111,111) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_prize.setBackground(resource);    //设置背景
                }
            }
        });*/
        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions2 = new RequestOptions()
                .bitmapTransform(roundedCorners)

                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(this).load(prizePath).apply(requestOptions2).into(iv_prize);
    }

    //奖项和未中奖 图片 更新
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhGeEventBus event){
        if(event != null){

            if("奖项一".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                setImage();
            }else if("奖项二".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
                setImage();

            }else if("奖项三".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                setImage();
            }else if("奖项四".equals(name)){

                prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
                setImage();
            }else if("奖项五".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
                setImage();
            }else if("奖项六".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
                setImage();
            }else if("安慰奖".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
                setImage();
            }/*else if("未中奖".equals(name)){
                if(adapter != null){
                    adapter.notifyDataSetChanged();
                }else {
                    initUnPrizeData();
                }

            }*/

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhPtEventBus event){

        if(event != null){
            name = event.getName();

            tv_prize_image_label.setText(prizeImageLabel+name);

            if("奖项一".equals(name)){ //奖项1 ~6
                couponList1 = LaohuPinTuanNextTwoActivity.couponList1;
                ll_prize_count.setVisibility(View.VISIBLE);
                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                rvPhoto.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                if(couponList1!=null && couponList1.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList1);
//                    pagerAdapter.updateData(couponList1);
//                    setPagerAdapter.updateData(couponList1);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("奖项二".equals(name)){ //奖项1 ~6
                couponList2 = LaohuPinTuanNextTwoActivity.couponList2;
                ll_prize_count.setVisibility(View.VISIBLE);

                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);

                rvPhoto.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
                if(couponList2!=null && couponList2.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList2);
//                    pagerAdapter.updateData(couponList2);
//                    setPagerAdapter.updateData(couponList2);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("奖项三".equals(name)){ //奖项1 ~6
                couponList3 = LaohuPinTuanNextTwoActivity.couponList3;
                ll_prize_count.setVisibility(View.VISIBLE);

                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                if(couponList3!=null && couponList3.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);

                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList3);
//                    pagerAdapter.updateData(couponList3);
//                    setPagerAdapter.updateData(couponList3);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("奖项四".equals(name)){ //奖项1 ~6
                couponList4 = LaohuPinTuanNextTwoActivity.couponList4;
                ll_prize_count.setVisibility(View.VISIBLE);

                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
                if(couponList4!=null && couponList4.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList4);
//                    pagerAdapter.updateData(couponList4);
//                    setPagerAdapter.updateData(couponList4);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("奖项五".equals(name)){ //奖项1 ~6
                couponList5 = LaohuPinTuanNextTwoActivity.couponList5;
                ll_prize_count.setVisibility(View.VISIBLE);

                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
                if(couponList5!=null && couponList5.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList5);
//                    pagerAdapter.updateData(couponList5);
//                    setPagerAdapter.updateData(couponList5);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("奖项六".equals(name)){ //奖项1 ~6
                couponList6 = LaohuPinTuanNextTwoActivity.couponList6;
                ll_prize_count.setVisibility(View.VISIBLE);

                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText(name+pandaLabel);
//                tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
                if(couponList6!=null && couponList6.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList6);
//                    pagerAdapter.updateData(couponList6);
//                    setPagerAdapter.updateData(couponList6);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }else if("安慰奖".equals(name)){
                couponList7 = LaohuPinTuanNextTwoActivity.couponList7;
                ll_prize_count.setVisibility(View.GONE);
                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
                tv_lh_ge_panda_label.setText("请问安慰奖,你想设置什么优惠券,作为奖品");
                prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                if(couponList7!=null && couponList7.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                        fab.setVisibility(View.VISIBLE);
                    }else {
                        fab.setVisibility(View.GONE);
                    }
                    setDotLayout(couponList7);
//                    pagerAdapter.updateData(couponList7);
//                    setPagerAdapter.updateData(couponList7);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }

            }
            setViewPagerAdapter();

            if("奖项一".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                isUnPrize = "Prize1";
            }else if("奖项二".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
                isUnPrize = "Prize2";
            }else if("奖项三".equals(name)){ //奖项1 ~6
                prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                isUnPrize = "Prize3";
            }else if("奖项四".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
                isUnPrize = "Prize4";
            }else if("奖项五".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
                isUnPrize = "Prize5";
            }else if("奖项六".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
                isUnPrize = "Prize6";
            }else if("安慰奖".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
                isUnPrize = "Prize7";
            }else if("未中奖".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath8;
                isUnPrize = "Prize8";

            }

            if(!"未中奖".equals(name)){
                setImage();
            }else {
                initUnPrizeData();
            }

        }
    }
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //模板素材
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");

        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            id = "0";
        }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
            id = "1";
        }
        //加载数据
        isPrepared = true;
        tv_prize_num.setText(name+"，你想发放多少奖品");
        activity = (LaohuPinTuanNextTwoActivity)getActivity();
        edt_day_one.setSelection(edt_day_one.getText().toString().length());
        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            iv_lh_ge.setBackgroundResource(R.drawable.ic_lh_panda);
        }else {
            iv_lh_ge.setBackgroundResource(R.drawable.ic_ge_panda);
        }


        if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
            tv_prize_num.setEnabled(true);
        }else {
            tv_prize_num.setEnabled(false);
        }

        tv_prize_image_label.setText(prizeImageLabel+name);
        if("奖项一".equals(name)){ //奖项1 ~6

            ll_prize_count.setVisibility(View.VISIBLE);
            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            rvPhoto.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            couponList1 = LaohuPinTuanNextTwoActivity.couponList1;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项二".equals(name)){ //奖项1 ~6
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);

            rvPhoto.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
            couponList2 = LaohuPinTuanNextTwoActivity.couponList2;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项三".equals(name)){ //奖项1 ~6
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
            couponList3 = LaohuPinTuanNextTwoActivity.couponList3;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);

                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }

            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项四".equals(name)){ //奖项1 ~6
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
            couponList4 = LaohuPinTuanNextTwoActivity.couponList4;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项五".equals(name)){ //奖项1 ~6
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
            couponList5 = LaohuPinTuanNextTwoActivity.couponList5;
            if(couponList5!=null && couponList5.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项六".equals(name)){ //奖项1 ~6
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
            couponList6 = LaohuPinTuanNextTwoActivity.couponList6;
            if(couponList6!=null && couponList6.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("安慰奖".equals(name)){
            ll_prize_count.setVisibility(View.GONE);
            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText("请问安慰奖,你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            couponList7 = LaohuPinTuanNextTwoActivity.couponList7;
            if(couponList7!=null && couponList7.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }
        if(!"未中奖".equals(name)){
            if(prizePath != null && !"".equals(prizePath)){

                if(prizePath.contains("get.do?id=")){
                    imageId = prizePath.substring(prizePath.indexOf("get.do?id=")+10,prizePath.length());
                }

            }
            setImage();
        }

    }

    @Override
    protected void initData() {

        if("未中奖".equals(name)){
            ll_prize.setVisibility(View.GONE);
            ll_prize_count.setVisibility(View.GONE);
            ll_un_prize.setVisibility(View.VISIBLE);
            initUnPrizeData();
        }else {
            if("安慰奖".equals(name)){
                ll_prize_count.setVisibility(View.GONE);
                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
            }else {
                ll_prize_count.setVisibility(View.VISIBLE);
                ll_prize.setVisibility(View.VISIBLE);
                ll_un_prize.setVisibility(View.GONE);
            }
            prizeCount = edt_day_one.getText().toString().trim();
            if(prizePath != null && !"".equals(prizePath)){
                if(prizePath.contains("get.do?id=")){
                    String subResult =prizePath;
                    imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

                }
                setImage();
            }else {
//                prizePath = HttpUrls.IMAGE_ULR + ApiConstants.PRIZE_DEFAULT;
                if(prizePath != null && !"".equals(prizePath) && prizePath.contains("get.do?id=")){
                    String subResult =prizePath;
                    imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

                }
            }

            setViewPagerAdapter();
          /*  if("NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                initcampaignGamesetreward();
            }*/
//            initcampaignGamesetreward();
            //添加监听事件
            addPrizeCountListener();
            initCouponList();
        }


    }

    private void initUnPrizeData(){
        if(LaohuPinTuanNextTwoActivity.selectList != null && LaohuPinTuanNextTwoActivity.selectList.size()>0){
            selectNum = LaohuPinTuanNextTwoActivity.selectList.size();
        }
        tv_photo_num.setText(selectNum+"/6");
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }else {
            initPicAdapter();
        }

    }

    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
        manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvPhoto.setLayoutManager(manager);
        adapter = new PrizeGridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(LaohuPinTuanNextTwoActivity.selectList);
        adapter.setSelectMax(maxSelectNum);
        rvPhoto.setAdapter(adapter);

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (LaohuPinTuanNextTwoActivity.selectList.size() > 0) {
                    LocalMedia media = LaohuPinTuanNextTwoActivity.selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(position, LaohuPinTuanNextTwoActivity.selectList);
                            break;

                    }
                }
            }
        });

    }

    private PrizeGridImageAdapter.onAddPicClickListener onAddPicClickListener = new PrizeGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            selectPrizeImg();
        }

    };


    private void  setCampaignGamesetrewardValue(){
        if(prizePath != null && !"".equals(prizePath)){
            if(prizePath.contains("get.do?id=")){
                imageId = prizePath.substring(prizePath.indexOf("get.do?id=")+10,prizePath.length());
            }
        }
        if(prizeCount!= null && !"".equals(prizeCount)){

            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
        }else {
            campaignGamesetreward.setPrizeCount(0);
        }
        campaignGamesetreward.setPrizeName(name);
        campaignGamesetreward.setRewardType("1");
        campaignGamesetreward.setPrizeImageUrl(imageId);
        campaignGamesetreward.setList(new ArrayList<CampaignCoupon>());
    }


    private void addPrizeCountListener(){
        edt_day_one.setSelection(edt_day_one.getText().toString().length());
        edt_day_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                prizeCount = edt_day_one.getText().toString().trim();

                if(prizeCount != null && !"".equals(prizeCount)){
                }else {
                    prizeCount = "0";
                }
                if(activity.campaignGamesetrewardList != null && activity.campaignGamesetrewardList.size()>0){
                    for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){

                      String imageId;
                        String prizeName = campaignGamesetreward.getPrizeName();
                        if("奖项一".equals(prizeName) && name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            if(LaohuPinTuanNextTwoActivity.prizePath1 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath1)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath1)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath1.substring(LaohuPinTuanNextTwoActivity.prizePath1.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath1.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }

                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList1);
                        }else if("奖项二".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            if(LaohuPinTuanNextTwoActivity.prizePath2 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath2)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath2)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath2.substring(LaohuPinTuanNextTwoActivity.prizePath2.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath2.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList2);
                        }else if("奖项三".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            if(LaohuPinTuanNextTwoActivity.prizePath3 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath3)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath3)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath3.substring(LaohuPinTuanNextTwoActivity.prizePath3.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath3.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList3);
                        }else if("奖项四".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            if(LaohuPinTuanNextTwoActivity.prizePath4 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath4)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath4)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath4.substring(LaohuPinTuanNextTwoActivity.prizePath4.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath4.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList4);
                        }else if("奖项五".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            if(LaohuPinTuanNextTwoActivity.prizePath5 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath5)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath5)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath5.substring(LaohuPinTuanNextTwoActivity.prizePath5.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath5.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setRewardType("1");
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList5);
                        }else if("奖项六".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(Integer.parseInt(prizeCount));
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("1");
                            if(LaohuPinTuanNextTwoActivity.prizePath6 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath6)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath6)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath6.substring(LaohuPinTuanNextTwoActivity.prizePath6.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath6.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList6);
                        }else if("安慰奖".equals(prizeName)&& name.equals(prizeName)){
                            campaignGamesetreward.setPrizeCount(0);
                            campaignGamesetreward.setPrizeName(prizeName);
                            campaignGamesetreward.setRewardType("0");
                            if(LaohuPinTuanNextTwoActivity.prizePath7 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath7)&&"get.do?id".contains(LaohuPinTuanNextTwoActivity.prizePath7)){
                                imageId = LaohuPinTuanNextTwoActivity.prizePath7.substring(LaohuPinTuanNextTwoActivity.prizePath7.indexOf("get.do?id=")+10,LaohuPinTuanNextTwoActivity.prizePath7.length());
                                campaignGamesetreward.setPrizeImageUrl(imageId);
                            }
                            campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList7);
                        }

                    }

                }
            }
        });
    }

    private void initCouponList(){
        if(LaohuPinTuanNextTwoActivity.campaignGamesetrewardList != null && LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.size()>0) {
            for (CampaignGamesetreward campaignGamesetreward : LaohuPinTuanNextTwoActivity.campaignGamesetrewardList) {
                String prizeName = campaignGamesetreward.getPrizeName();
                count = campaignGamesetreward.getPrizeCount();
                if("奖项一".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList1 = campaignGamesetreward.getList();

                   rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList1;
                   prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
                   setImage();
                }else if("奖项二".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList2 = campaignGamesetreward.getList();
                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList2;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
                    setImage();
                }else if("奖项三".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList3 = campaignGamesetreward.getList();
                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList3;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
                    setImage();
                }else if("奖项四".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList4= campaignGamesetreward.getList();

                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList4;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
                    setImage();
                }else if("奖项五".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList5= campaignGamesetreward.getList();
                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList5;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
                    setImage();
                }else if("奖项六".equals(prizeName)&& name.equals(prizeName)){
                    if(count != null && count.intValue()>0){
                        edt_day_one.setText(String.valueOf(count));
                    }else {
                        edt_day_one.setText("");
                    }
                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList6 = campaignGamesetreward.getList();
                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList6;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
                    setImage();
                }else if("安慰奖".equals(prizeName)&& name.equals(prizeName)){

                    if(campaignGamesetreward.getPrizeImageUrl()!=null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                        LaohuPinTuanNextTwoActivity.prizePath7 = HttpUrls.IMAGE_ULR +campaignGamesetreward.getPrizeImageUrl();
                    }
                    LaohuPinTuanNextTwoActivity.campaignCouponList7 = campaignGamesetreward.getList();

                    rewardCouponList =  LaohuPinTuanNextTwoActivity.campaignCouponList7;
                    prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
                    setImage();
                }
                edt_day_one.setSelection(edt_day_one.getText().toString().length());
                //本地缓存 提取
                if(rewardCouponList!=null && rewardCouponList.size()>0){

                    for (CampaignCoupon campaignCoupon :rewardCouponList){
                        List<Store> storeList = campaignCoupon.getStores();
                        Coupon coupon = new Coupon();
                        StringBuilder storeIdSb = new StringBuilder();
                        StringBuilder storeNameSb = new StringBuilder();
                        if(storeList!=null && storeList.size()>0){
                            Store store1 = storeList.get(0);
                            if(store1 != null){
                                storeIdCouponType = store1.getStoreSelectType();
                                String storeName = "";
                                String storeIdArr = "";
                                if("A".equals(storeIdCouponType)){
                                    storeName = "#全部店铺";
                                    for (Store store:storeList){
                                        store.setChecked(false);
                                        String storeId = store.getStoreId();
                                        storeIdSb.append(storeId).append("-");

                                    }
                                    storeIdArr = "";
                                }else if("D".equals(storeIdCouponType)){
                                    storeName = "#全部自营";
                                    for (Store store:storeList){
                                        store.setChecked(false);
                                        String storeId = store.getStoreId();
                                        storeIdSb.append(storeId).append("-");

                                    }
                                    storeIdArr = "";
                                }else if("J".equals(storeIdCouponType)){
                                    storeName = "#全部加盟";
                                    for (Store store:storeList){
                                        store.setChecked(false);
                                        String storeId = store.getStoreId();
                                        storeIdSb.append(storeId).append("-");

                                    }
                                    storeIdArr = "";
                                }else {
                                    storeIdCouponType = "H";
                                    for (Store store:storeList){
                                        store.setChecked(true);
                                        String storeId = store.getStoreId();
                                        String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeIdSb.append(storeId).append("-");
                                        storeNameSb.append(storeName2);

                                    }
                                    storeName = storeNameSb.toString();

                                    String storeIdA= storeIdSb.toString();
                                    if(storeIdA != null  && !"".equals(storeIdA)){
                                        storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                                    }
                                    coupon.setStoreIds(storeIdArr);
                                    coupon.setStoreNames(storeName);
                                }
                            }
                        }
                        CouponMaterial material = new CouponMaterial();
                        material.setBackground(campaignCoupon.getBackgroundThumbnail());
                        material.setBackgroundThumbnail(campaignCoupon.getBackground());
                        coupon.setMaterial(material);
                        coupon.setCouponQnt(campaignCoupon.getCouponQnt());
                        coupon.setObject(campaignCoupon.getObject());
                        coupon.setDiscount(campaignCoupon.getDiscount());
                        coupon.setMoney(campaignCoupon.getMoney());
                        coupon.setNoDate(campaignCoupon.getNoDate());
                        coupon.setNoItem(campaignCoupon.getNoItem());
                        coupon.setDateCode(campaignCoupon.getDateCode());
                        coupon.setStoreSelectType(campaignCoupon.getStoreSelectType());
                        coupon.setExpireStartType(campaignCoupon.getExpireStartType());
                        coupon.setExpireEndType(campaignCoupon.getExpireEndType());
                        coupon.setOther(campaignCoupon.getOther());
                        coupon.setLimit(campaignCoupon.getLimit());
                        coupon.setType(campaignCoupon.getType());
                        coupon.setId(campaignCoupon.getCouponId());
                        coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                        coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                        coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                        coupon.setName(campaignCoupon.getCouponName());
                        coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                        coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());
                        /*if ("奖项一".equals(prizeName)) {
                            couponList1.add(coupon);
                        } else if ("奖项二".equals(prizeName)) {
                            couponList2.add(coupon);
                        } else if ("奖项三".equals(prizeName)) {
                            couponList3.add(coupon);
                        } else if ("奖项四".equals(prizeName)) {
                            couponList4.add(coupon);
                        } else if ("奖项五".equals(prizeName)) {
                            couponList5.add(coupon);
                        } else if ("奖项六".equals(prizeName)) {
                            couponList6.add(coupon);
                        } else if ("安慰奖".equals(prizeName)) {
                            couponList7.add(coupon);
                        }*/


                    }

                }
            }
            addCoupontList();
        }
    }


    protected  void initcampaignGamesetreward(){

        //未从缓存中获取
        if("奖项一".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            setCampaignGamesetrewardValue();
            campaignGamesetreward.setPrizeOrder(1);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("奖项二".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
            setCampaignGamesetrewardValue();
            campaignGamesetreward.setPrizeOrder(2);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("奖项三".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
            setCampaignGamesetrewardValue();

            campaignGamesetreward.setPrizeOrder(3);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("奖项四".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
            setCampaignGamesetrewardValue();
            campaignGamesetreward.setPrizeOrder(4);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("奖项五".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
            setCampaignGamesetrewardValue();
            campaignGamesetreward.setPrizeOrder(5);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("奖项六".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
            setCampaignGamesetrewardValue();
            campaignGamesetreward.setPrizeOrder(6);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }else if("安慰奖".equals(name)) {
            prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
            setCampaignGamesetrewardValue();

            //重设 奖项数量
            campaignGamesetreward.setPrizeCount(null);
            campaignGamesetreward.setRewardType("0");
            campaignGamesetreward.setPrizeOrder(0);
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }

    }

    private void addCoupontList(){
        /*if ("奖项一".equals(name)) {
            couponList1 = couponList;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList1);

                pagerAdapter.updateData(couponList1);
                setPagerAdapter.updateData(couponList1);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("奖项二".equals(name)) {
            couponList2 = couponList;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList2);

                pagerAdapter.updateData(couponList2);
                setPagerAdapter.updateData(couponList2);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("奖项三".equals(name)) {
            couponList3 = couponList;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList3);

                pagerAdapter.updateData(couponList3);
                setPagerAdapter.updateData(couponList3);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("奖项四".equals(name)) {
            couponList4 = couponList;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList4);

                pagerAdapter.updateData(couponList4);
                setPagerAdapter.updateData(couponList4);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("奖项五".equals(name)) {
            couponList5 = couponList;
            if(couponList5!=null && couponList5.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList5);

                pagerAdapter.updateData(couponList5);
                setPagerAdapter.updateData(couponList5);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("奖项六".equals(name)) {
            couponList6 = couponList;
            if(couponList6!=null && couponList6.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList6);

                pagerAdapter.updateData(couponList6);
                setPagerAdapter.updateData(couponList6);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        } else if ("安慰奖".equals(name)) {
            couponList7 = couponList;
            if(couponList7!=null && couponList7.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList7);

                pagerAdapter.updateData(couponList7);
                setPagerAdapter.updateData(couponList7);


            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        }*/

        if("奖项一".equals(name)){ //奖项1 ~6
            couponList1 = LaohuPinTuanNextTwoActivity.couponList1;
            ll_prize_count.setVisibility(View.VISIBLE);
            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            rvPhoto.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            if(couponList1!=null && couponList1.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
//                setDotLayout(couponList1);
//                pagerAdapter.updateData(couponList1);
//                setPagerAdapter.updateData(couponList1);
                setDotLayout(couponList1);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项二".equals(name)){ //奖项1 ~6
            couponList2 = LaohuPinTuanNextTwoActivity.couponList2;
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);

            rvPhoto.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath2;
            if(couponList2!=null && couponList2.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList2);
//                pagerAdapter.updateData(couponList2);
//                setPagerAdapter.updateData(couponList2);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项三".equals(name)){ //奖项1 ~6
            couponList3 = LaohuPinTuanNextTwoActivity.couponList3;
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath3;
            if(couponList3!=null && couponList3.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);

                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList3);
//                pagerAdapter.updateData(couponList3);
//                setPagerAdapter.updateData(couponList3);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项四".equals(name)){ //奖项1 ~6
            couponList4 = LaohuPinTuanNextTwoActivity.couponList4;
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath4;
            if(couponList4!=null && couponList4.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList4);
//                pagerAdapter.updateData(couponList4);
//                setPagerAdapter.updateData(couponList4);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项五".equals(name)){ //奖项1 ~6
            couponList5 = LaohuPinTuanNextTwoActivity.couponList5;
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath5;
            if(couponList5!=null && couponList5.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList5);
//                pagerAdapter.updateData(couponList5);
//                setPagerAdapter.updateData(couponList5);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("奖项六".equals(name)){ //奖项1 ~6
            couponList6 = LaohuPinTuanNextTwoActivity.couponList6;
            ll_prize_count.setVisibility(View.VISIBLE);

            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText(name+",你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath6;
            if(couponList6!=null && couponList6.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList6);
//                pagerAdapter.updateData(couponList6);
//                setPagerAdapter.updateData(couponList6);
                setViewPagerAdapter();
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }else if("安慰奖".equals(name)){
            couponList7 = LaohuPinTuanNextTwoActivity.couponList7;
            ll_prize_count.setVisibility(View.GONE);
            ll_prize.setVisibility(View.VISIBLE);
            ll_un_prize.setVisibility(View.GONE);
            tv_lh_ge_panda_label.setText("请问安慰奖,你想设置什么优惠券,作为奖品");
            prizePath = LaohuPinTuanNextTwoActivity.prizePath1;
            if(couponList7!=null && couponList7.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                if("S".equals(LaohuPinTuanNewActivity.compaignStatus)||"NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
                setDotLayout(couponList7);
                pagerAdapter.updateData(couponList7);
                setPagerAdapter.updateData(couponList7);
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }/*else if("未中奖".equals(name)){
            ll_prize.setVisibility(View.GONE);
            ll_prize_count.setVisibility(View.GONE);
            ll_un_prize.setVisibility(View.VISIBLE);
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }else {
                initUnPrizeData();
            }

        }
*/
    }

    protected void removeCampaignCouponList(ArrayList<CampaignCoupon> campaignCouponList){
        if(campaignCouponList != null && campaignCouponList.size()>0){
            for(int i=0;i<campaignCouponList.size();i++){
                for(int j=campaignCouponList.size()-1;j>i;j--){
                    if((campaignCouponList.get(i).getCouponId().equals(campaignCouponList.get(j).getCouponId()))){
                        campaignCouponList.remove(j);
                    }
                }
            }
        }

    }

    @OnClick({R.id.btn_add_coupon,R.id.iv_prize,R.id.fab})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {


            case R.id.btn_add_coupon:
                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon",LaohuPinTuanNewActivity.campaignType);
                CampaignCouponListFragment.selectCoupons.clear();
                if("奖项一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5551,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5552,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5553,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项四".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5554,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项五".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5555,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项六".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5556,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("安慰奖".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5557,R.anim.slide_in_right,R.anim.slide_out_left);
                }

                break;

            case R.id.fab:

                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon",LaohuPinTuanNewActivity.campaignType);
                CampaignCouponListFragment.selectCoupons.clear();
                if("奖项一".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5551,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项二".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5552,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项三".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5553,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项四".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5554,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项五".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5555,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("奖项六".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5556,R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("安慰奖".equals(name)){
                    ActivityUtils.startActivityForResult(getActivity(),intent,5557,R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;
            case R.id.iv_prize:

                selectPrizeImg();
                break;




        }
    }

    private void selectPrizeImg(){
        PopWindow popWindow = new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction("从模版中选择",PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {

                        Intent intent1  = new Intent(getActivity(),PrizeTempletActivity.class);
                        if("奖项一".equals(name)){
                            intent1.putExtra("isUnPrize","Prize1");
                        }else if("奖项二".equals(name)){
                            intent1.putExtra("isUnPrize","Prize2");
                        }else if("奖项三".equals(name)){
                            intent1.putExtra("isUnPrize","Prize3");
                        }else if("奖项四".equals(name)){
                            intent1.putExtra("isUnPrize","Prize4");
                        }else if("奖项五".equals(name)){
                            intent1.putExtra("isUnPrize","Prize5");
                        }else if("奖项六".equals(name)){
                            intent1.putExtra("isUnPrize","Prize6");
                        }else if("安慰奖".equals(name)){
                            intent1.putExtra("isUnPrize","Prize7");
                        }else if("未中奖".equals(name)){
                            intent1.putExtra("isUnPrize","Prize8");
                        }
                        intent1.putExtra("campaignType",LaohuPinTuanNewActivity.campaignType);
                        ActivityUtils.startActivity(intent1,R.anim.slide_in_right,R.anim.slide_out_left);

                    }
                }))
                .addItemAction(new PopItemAction("自定义上传", PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        LocalMedia localMedia = new LocalMedia();
                        if(prizePath != null && !"".equals(prizePath)){

                        }else {
//                            prizePath = HttpUrls.IMAGE_ULR + ApiConstants.PRIZE_DEFAULT;
                            prizePath = HttpUrls.IMAGE_ULR ;
                        }

                        selectList = new ArrayList<>();
                        int config = PictureConfig.CHOOSE_REQUEST;
                        if("奖项一".equals(name)){

                            config = PictureConfig.CHOOSE_REQUEST1;
                        }else if("奖项二".equals(name)){
                            config = PictureConfig.CHOOSE_REQUEST2;
                        }else if("奖项三".equals(name)){
                            config = PictureConfig.CHOOSE_REQUEST3;
                        }else if("奖项四".equals(name)){
                            config = PictureConfig.CHOOSE_REQUEST4;
                        }else if("奖项五".equals(name)){
                            config = PictureConfig.CHOOSE_REQUEST5;
                        }else if("奖项六".equals(name)){
                            selectList = new ArrayList<>();
                            config = PictureConfig.CHOOSE_REQUEST6;
                        }else if("安慰奖".equals(name)){
                            config = PictureConfig.CHOOSE_REQUEST7;
                        }/*else if("未中奖".equals(name)){
                           selectList = activity.selectList;
                            config = PictureConfig.CHOOSE_REQUEST8;
                        }*/

                        themeId = R.style.picture_default_style;
                        aspect_ratio_x = 1;
                        aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
                        PictureSelectionModel pictureSelector = PictureSelector.create(getActivity())
                                .openGallery(chooseMode).theme(themeId)
                                .minSelectNum(1).imageSpanCount(4).previewImage(true)// 是否可预览图片
                                .previewVideo(false)// 是否可预览视频
                                .enablePreviewAudio(false).isCamera(false)// 是否显示拍照按钮
                                .isZoomAnim(true).enableCrop(false)// 是否裁剪
                                .compress(true)// 是否压缩
                                .synOrAsy(true).glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(false)
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                                .isGif(false)// 是否显示gif图片
                                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                                .circleDimmedLayer(false)// 是否圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                .openClickSound(false)// 是否开启点击声音
                                .selectionMedia(selectList).minimumCompressSize(100);// 小于100kb的图片不压缩

                                if("未中奖".equals(name)){
                                    pictureSelector .maxSelectNum(6);// 最大图片选择数量
                                    pictureSelector.selectionMode(PictureConfig.MULTIPLE);
                                }else {
                                    pictureSelector .maxSelectNum(1);// 最大图片选择数量
                                    pictureSelector.selectionMode(PictureConfig.SINGLE);
                                }
                                pictureSelector.forResult(config);

                    }
                }))

                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                .create();
        popWindow.show();
    }


    public   class CouponPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        public CouponPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = LaohuPinTuanCouponFragment.getInstance(coupon,name);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<Coupon> couponList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(couponList !=null && couponList.size()>0) {
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = LaohuPinTuanCouponFragment.getInstance(coupon,name);
                    fragments.add(fragment);
                }
            }
            setFragments(fragments);
        }


        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commit();
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
            if(mFragmentList.size()>0){
                return mFragmentList.get(position);
            }else {
                return null;
            }

        }


    }

    protected void setViewPagerAdapter(){
        pagerAdapter  = new CouponPagerAdapter(getChildFragmentManager(),couponList);
        couponViewPager.setAdapter(pagerAdapter);
        couponViewPager.setCurrentItem(0);

        couponViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < mDotsIV.size(); i++) {
                    if (i == position) {
                        mDotsIV.get(i).setImageResource(R.drawable.dot_focus);
                    } else {
                        mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
                    }
                }
                setViewPager.setCurrentItem(position);


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //底部优惠券设置内容
        setPagerAdapter = new CouponSettPagerAdapter(getChildFragmentManager(),couponList);
        setViewPager.setAdapter(setPagerAdapter);
        setViewPager.setCurrentItem(0);

        //活动优惠券底部设置
        setViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
//                setCampaignCoupon(position);


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void setCampaignCoupon(int position){
        if("奖项一".equals(name)){
            if(couponList1 != null && couponList1.size()>0){
                Coupon coupon = couponList1.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList1!=null && LaohuPinTuanNextTwoActivity.campaignCouponList1.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList1.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList1.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("奖项二".equals(name)){
            if(couponList2 != null && couponList2.size()>0){
                Coupon coupon = couponList2.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList2!=null && LaohuPinTuanNextTwoActivity.campaignCouponList2.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList2.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList2.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("奖项三".equals(name)){

            if(couponList3 != null && couponList3.size()>0){
                Coupon coupon = couponList3.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList3!=null && LaohuPinTuanNextTwoActivity.campaignCouponList3.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList3.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList3.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("奖项四".equals(name)){
            if(couponList4 != null && couponList4.size()>0){
                Coupon coupon = couponList4.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList4!=null && LaohuPinTuanNextTwoActivity.campaignCouponList4.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList4.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList4.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("奖项五".equals(name)){
            if(couponList5 != null && couponList5.size()>0){
                Coupon coupon = couponList5.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList5!=null && LaohuPinTuanNextTwoActivity.campaignCouponList5.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList5.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList5.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("奖项六".equals(name)){
            if(couponList6 != null && couponList6.size()>0){
                Coupon coupon = couponList6.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList6!=null && LaohuPinTuanNextTwoActivity.campaignCouponList6.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList6.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList6.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }else if("安慰奖".equals(name)){
            if(couponList7 != null && couponList7.size()>0){
                Coupon coupon = couponList7.get(position);
                if(LaohuPinTuanNextTwoActivity.campaignCouponList7!=null && LaohuPinTuanNextTwoActivity.campaignCouponList7.size()>0){
                    for(int i=0;i<LaohuPinTuanNextTwoActivity.campaignCouponList7.size();i++){
                        CampaignCoupon campaignCoupon = LaohuPinTuanNextTwoActivity.campaignCouponList7.get(i);

                        if(position == i){
                            campaignCoupon.setCouponId(coupon.getId());
                            campaignCoupon.setCouponName(coupon.getName());
                            campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                            campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                            if("A".equals(nextSettingFragment.expireEndType)){
                                String startDate = nextSettingFragment.startDate;
                                String endDate = nextSettingFragment.endDate;

                                campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                                campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");
                            }else if("R1".equals(nextSettingFragment.expireEndType)){
                                String startDay= nextSettingFragment.startDay;
                                String endDay = nextSettingFragment.endDay;
                                campaignCoupon.setExpireStartDay(startDay != null?startDay:"");
                                campaignCoupon.setExpireEndDay(endDay != null?endDay:"");

                            }
                        }

                    }
                }
            }
        }

    }

    public   class CouponSettPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private  List<Coupon> couponList = null;
        public CouponSettPagerAdapter(FragmentManager fm, List<Coupon> couponList) {
            super(fm);
            this.mFragmentManager = fm;
            this.couponList = couponList;
            mFragmentList = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = LaohuPinTuanNextSettingFragment.getInstance(coupon,i,name,setViewPager);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<Coupon> couponList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(couponList !=null && couponList.size()>0){
                for (int i = 0;i<couponList.size();i++){
                    Coupon coupon = couponList.get(i);
                    Fragment fragment = LaohuPinTuanNextSettingFragment.getInstance(coupon,i,name,setViewPager);
                    fragments.add(fragment);
                }
            }
            setFragments(fragments);
        }


        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commit();
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

   

    protected void setDotLayout(ArrayList<Coupon> dataList){
        //先删除
        mDotsIV.clear();
        mDotsLayout.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
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

    @Override
    public void chanageLayout(ArrayList<Coupon> couponList, String prizeName) {
        LaohuPinTuanNextTwoActivity activity = (LaohuPinTuanNextTwoActivity)getActivity();
        if("奖项一".equals(prizeName)){
            if(activity.couponList1.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList1);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList1);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList1);
            setPagerAdapter.updateData(activity.couponList1);


        }else if("奖项二".equals(prizeName)){
            if(activity.couponList2.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList2);

            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList2);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList2);
            setPagerAdapter.updateData(activity.couponList2);

        }else if("奖项三".equals(prizeName)){
            if(activity.couponList3.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList3);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList3);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList3);
            setPagerAdapter.updateData(activity.couponList3);
        }else if("奖项四".equals(prizeName)){
            if(activity.couponList4.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList4);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList4);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList4);
            setPagerAdapter.updateData(activity.couponList4);
        }else if("奖项五".equals(prizeName)){
            if(activity.couponList5.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList5);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList5);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList5);
            setPagerAdapter.updateData(activity.couponList5);
        }else if("奖项六".equals(prizeName)){
            if(activity.couponList6.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList6);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){

                        campaignGamesetreward.setList(activity.campaignCouponList6);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList6);
            setPagerAdapter.updateData(activity.couponList6);
        }else if("安慰奖".equals(prizeName)){
            if(activity.couponList7.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList7);
            if(activity.campaignGamesetrewardList!= null && activity.campaignGamesetrewardList.size()>0){
                for (CampaignGamesetreward campaignGamesetreward : activity.campaignGamesetrewardList){
                    String name = campaignGamesetreward.getPrizeName();

                    if(prizeName.equals(name)){
                        campaignGamesetreward.setList(activity.campaignCouponList7);
                    }
                }
            }
            pagerAdapter.updateData(activity.couponList7);
            setPagerAdapter.updateData(activity.couponList7);
        }
    }

    private void remove(ArrayList<Coupon> couponList){
        for(int i=0;i<couponList.size();i++){
            for(int j=couponList.size()-1;j>i;j--){
                if(String.valueOf(couponList.get(i).getId()).equals(String.valueOf(couponList.get(j).getId()))){
                    couponList.remove(j);
                }
            }
        }
    }


}
