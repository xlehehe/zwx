package com.zwx.scan.app.feature.campaign;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaohuPinTuanNextConsolePrizeFragment extends BaseFragment implements View.OnClickListener,LaohuPinTuanCouponFragment.ChanageLhPtTwoLayoutLisenter {



    @BindView(R.id.iv_prize)
    protected ImageView iv_prize;

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

    /*@BindView(R.id.fab)
    protected ImageButton fab;*/

    @BindView(R.id.iv_lh_ge)
    protected ImageView iv_lh_ge;



    //奖项名称
    private String name;

    protected CouponPagerAdapter pagerAdapter = null;
    protected CouponSettPagerAdapter setPagerAdapter = null;
    protected ArrayList<Coupon> couponList = new ArrayList<>();
    private List<ImageView> mDotsIV = new ArrayList<>();


    private String isNextTwoAndThree = "YES";

    protected static ArrayList<CampaignCoupon> campaignCouponList7 =new ArrayList<>();

    protected  static ArrayList<CampaignCoupon> prizeCouponList = new ArrayList<>();

    protected   ArrayList<CampaignCoupon> saveForwardCouponList = new ArrayList<>();


    protected   String isEditCampaign = "NO";

    protected String isCopyCreate ;


    protected   String storeIdCouponType ;
    private  LaohuPinTuanNextSettingFragment nextSettingFragment = new LaohuPinTuanNextSettingFragment();

    protected List<LocalMedia> selectList = new ArrayList<>();

    private String imageId;

    private String prizePath;
    private LaohuPinTuanNextTwoActivity activity =null;
    CampaignGamesetreward campaignGamesetreward = new CampaignGamesetreward();

    public LaohuPinTuanNextConsolePrizeFragment() {
        // Required empty public constructor
    }


    public static LaohuPinTuanNextConsolePrizeFragment getInstance(String name,ArrayList<Coupon> couponList,String prizePath){
        LaohuPinTuanNextConsolePrizeFragment nextTwoFragment = new LaohuPinTuanNextConsolePrizeFragment();
        nextTwoFragment.name = name;
        nextTwoFragment.couponList = couponList;
        nextTwoFragment.prizePath =prizePath;
        return nextTwoFragment;

    }



    @Override
    public void onResume() {
        super.onResume();

        if("安慰奖".equals(name)){
            if(LaohuPinTuanNextTwoActivity.prizePath7 != null && !"".equals(LaohuPinTuanNextTwoActivity.prizePath7 )){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath7;
            }
        }

        setPrizeImage();
    }
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_laohu_pin_tuan_next_console_prize;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        activity = (LaohuPinTuanNextTwoActivity)getActivity();
        //设置图片背景
        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            iv_lh_ge.setBackgroundResource(R.drawable.ic_lh_panda);
        }else {
            iv_lh_ge.setBackgroundResource(R.drawable.ic_ge_panda);
        }

        if(couponList!=null && couponList.size()>0 ){
            llAddTop.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
//            activity.fab.setVisibility(View.VISIBLE);
            setDotLayout(couponList);

            setViewPagerAdapter();


        }else {
            llAddTop.setVisibility(View.VISIBLE);

            llBottom.setVisibility(View.GONE);
//            activity.fab.setVisibility(View.GONE);
        }

        //若编辑返回数据则从编辑获取，否则本地缓存读取
        if(LaohuPinTuanNextTwoActivity.campaignGamesetrewardList != null && LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.size()>0){

            for (CampaignGamesetreward campaignGamesetreward : LaohuPinTuanNextTwoActivity.campaignGamesetrewardList){
                if(campaignGamesetreward != null){
                    String name = campaignGamesetreward.getPrizeName();
                    if("安慰奖".equals(name)){

                      prizeCouponList = campaignGamesetreward.getList();
                    }
                }


            }
        }
        initDataCouponList();
        if(LaohuPinTuanNewActivity.compaignStatus != null && !"".equals(LaohuPinTuanNewActivity.compaignStatus)){
            if("S".equals(LaohuPinTuanNewActivity.compaignStatus)|| "NEW".equals(LaohuPinTuanNewActivity.compaignStatus)){
                if(prizeCouponList !=null && prizeCouponList.size()>0){
//                    activity.fab.setVisibility(View.VISIBLE);
                }else {
//                    activity.fab.setVisibility(View.GONE);
                }

                btnAddCoupon.setVisibility(View.VISIBLE);
            }else {
//                activity.fab.setVisibility(View.GONE);
                btnAddCoupon.setVisibility(View.GONE);

            }
        }else {
//            activity.fab.setVisibility(View.GONE);
            btnAddCoupon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        if(selectList != null && selectList.size()>0){
            LocalMedia localMedia = selectList.get(0);

            if(localMedia != null){
                prizePath = localMedia.getPath();
                setPrizeImage();

            }

        }
        if(prizePath != null && !"".equals(prizePath)){
            if(prizePath.contains("get.do?id=")){
                String subResult =prizePath;
                imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

            }
        }else {
            prizePath = HttpUrls.IMAGE_ULR + ApiConstants.PRIZE_DEFAULT;
            if(prizePath.contains("get.do?id=")){
                String subResult =prizePath;
                imageId = subResult.substring(subResult.indexOf("get.do?id=")+10,subResult.length());

            }
        }

        if(LaohuPinTuanNextTwoActivity.campaignGamesetrewardList != null && LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.size()>0){

            for (CampaignGamesetreward campaignGamesetreward : LaohuPinTuanNextTwoActivity.campaignGamesetrewardList){
                if(campaignGamesetreward != null){
                     name = campaignGamesetreward.getPrizeName();
                    if("安慰奖".equals(name)){
                        String rewardType = campaignGamesetreward.getRewardType();

                        if("0".equals(rewardType)){
                            campaignGamesetreward.setPrizeName(name);
                            if(campaignGamesetreward.getList() != null && campaignGamesetreward.getList().size()>0){
                                LaohuPinTuanNextTwoActivity.campaignCouponList7 = campaignGamesetreward.getList();
                            }

                            if(campaignGamesetreward.getPrizeImageUrl() != null && !"".equals(campaignGamesetreward.getPrizeImageUrl())){
                                campaignGamesetreward.setPrizeImageUrl(campaignGamesetreward.getPrizeImageUrl());
                            }else {
                                campaignGamesetreward.setPrizeImageUrl(ApiConstants.PRIZE_DEFAULT);
                            }
                            campaignGamesetreward.setList(campaignGamesetreward.getList());
                        }

                    }
                }


            }
        }else {
            setCampaignGamesetrewardValue();
            LaohuPinTuanNextTwoActivity.campaignGamesetrewardList.add(campaignGamesetreward);
        }
    }
    private void  setCampaignGamesetrewardValue(){
        campaignGamesetreward.setPrizeName(name);
        campaignGamesetreward.setRewardType("0");
        campaignGamesetreward.setPrizeImageUrl(ApiConstants.PRIZE_DEFAULT);
        removeCampaignCouponList(LaohuPinTuanNextTwoActivity.campaignCouponList7);
        campaignGamesetreward.setList(LaohuPinTuanNextTwoActivity.campaignCouponList7);
    }

    private void removeCampaignCouponList(ArrayList<CampaignCoupon> campaignCouponList){
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
    private void setPrizeImage(){


        Glide.with(this).load(prizePath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_prize.setBackground(resource);
                }
            }
        });
    }



    @OnClick({R.id.btn_add_coupon,R.id.iv_prize})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {


            case R.id.btn_add_coupon:
                intent = new Intent(getActivity(),CampaignCouponListActivity.class);
                intent.putExtra("prizeName",name);
                intent.putExtra("nextTwoSelectCoupon",LaohuPinTuanNewActivity.campaignType);

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

                PopWindow popWindow = new PopWindow.Builder(getActivity())
                        .setStyle(PopWindow.PopWindowStyle.PopUp)
                        .addItemAction(new PopItemAction("从模版中选择",PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {

                                Intent intent1  = new Intent(getActivity(),PrizeTempletActivity.class);
                                intent1.putExtra("isUnPrize","ConsolePrize");
                                ActivityUtils.startActivity(intent1,R.anim.slide_in_left,R.anim.slide_out_right);

                            }
                        }))
                        .addItemAction(new PopItemAction("自定义上传", PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {

                                LocalMedia localMedia = new LocalMedia();
                                int config = PictureConfig.CHOOSE_REQUEST;
                                if("安慰奖".equals(name)){
                                    localMedia.setPath(prizePath);
                                    localMedia.setCompressPath(prizePath);
                                    selectList.add(localMedia);
                                    config = PictureConfig.CHOOSE_REQUEST7;
                                }
                                int themeId = R.style.picture_default_style;
                                int aspect_ratio_x = 1;
                                int aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
                                PictureSelector.create(getActivity())
                                        .openGallery( PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                                        .maxSelectNum(1)// 最大图片选择数量
                                        .minSelectNum(1)// 最小选择数量
                                        .imageSpanCount(4)// 每行显示个数
                                        .selectionMode(PictureConfig.MULTIPLE )
                                        /* .selectionMode(cb_choose_mode.isChecked() ?
                                                 PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选*/
                                        .previewImage(true)// 是否可预览图片
                                        .previewVideo(false)// 是否可预览视频
                                        .enablePreviewAudio(false) // 是否可播放音频
                                        .isCamera(false)// 是否显示拍照按钮
                                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                                        .enableCrop(false)// 是否裁剪
                                        .compress(true)// 是否压缩
                                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                        //.compressSavePath(getPath())//压缩图片保存地址
                                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                        .hideBottomControls(false)
                                        .isGif(false)// 是否显示gif图片
                                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                                        .circleDimmedLayer(false)// 是否圆形裁剪
                                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                        .openClickSound(false)// 是否开启点击声音
                                        .selectionMedia(selectList)// 是否传入已选图片
                                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                                        .forResult(config);//结果回调onActivityResult code

                            }
                        }))

                        .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                        .create();
                popWindow.show();
                break;




        }
    }

    private void initDataCouponList(){
        //本地缓存 提取
        prizeCouponList = (ArrayList<CampaignCoupon>) SPObjUtil.get("prizeCouponList");
        if(prizeCouponList!=null && prizeCouponList.size()>0){
            for (CampaignCoupon campaignCoupon :prizeCouponList){
                List<Store> storeList = campaignCoupon.getStores();

                if(storeList!=null && storeList.size()>0){
                    Store store1 = storeList.get(0);
                    StringBuilder storeIdSb = new StringBuilder();
                    StringBuilder storeNameSb = new StringBuilder();

                    if(store1 != null){
                        storeIdCouponType = store1.getStoreSelectType();
                        String storeName = "";
                        String storeIdArr = "";
                        if("A".equals(storeIdCouponType)){
                            storeName = "全部店铺";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("D".equals(storeIdCouponType)){
                            storeName = "全部自营";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else if("J".equals(storeIdCouponType)){
                            storeName = "全部加盟";
                            for (Store store:storeList){
                                store.setChecked(false);
                                String storeId = store.getStoreId();
                                storeIdSb.append(storeId).append("-");

                            }
                            storeIdArr = "";
                        }else {

                            for (Store store:storeList){
                                store.setChecked(false);
                                Integer statusQuo = store.getStatusQuo();
                                String storeId = store.getStoreId();
                                String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                if(statusQuo!=null){
                                    if(statusQuo == 1){
                                        storeIdSb.append(storeId).append("-");
                                        storeNameSb.append(storeName2);
                                    }

                                }

                            }
                            storeName = storeNameSb.toString();

                            String storeIdA= storeIdSb.toString();
                            if(storeIdSb.toString().isEmpty()){
                                ToastUtils.showShort("请选择店铺");
                                return;
                            }

                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);

                        }
                        SPUtils.getInstance().put("brandLogoConsolePrize",SPUtils.getInstance().getString("brandLogo"));
                        SPUtils.getInstance().put("storeIdArrayConsolePrize",storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayConsolePrize",storeName);
                        SPUtils.getInstance().put("storeIdCouponTypeConsolePrize",storeIdCouponType);


                    }
                }

                Coupon coupon = new Coupon();
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
                coupon.setId(campaignCoupon.getId());
                coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                coupon.setName(campaignCoupon.getCouponName());
                coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());
                couponList.add(coupon);
            }

            if(couponList!=null && couponList.size()>0 ){
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
//                activity.fab.setVisibility(View.VISIBLE);
                setDotLayout(couponList);
                pagerAdapter.updateData(couponList);
                setPagerAdapter.updateData(couponList);
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
//                activity.fab.setVisibility(View.GONE);
            }
        }else {
            prizeCouponList = LaohuPinTuanNextTwoActivity.campaignCouponList7;
            if(prizeCouponList != null && prizeCouponList.size()>0){
                for (CampaignCoupon campaignCoupon :prizeCouponList){
                    List<Store> storeList = campaignCoupon.getStores();

                    if(storeList!=null && storeList.size()>0){
                        Store store1 = storeList.get(0);
                        StringBuilder storeIdSb = new StringBuilder();
                        StringBuilder storeNameSb = new StringBuilder();

                        if(store1 != null){
                            storeIdCouponType = store1.getStoreSelectType();
                            String storeName = "";
                            String storeIdArr = "";
                            if("A".equals(storeIdCouponType)){
                                storeName = "全部店铺";
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    storeIdSb.append(storeId).append("-");

                                }
                                storeIdArr = "";
                            }else if("D".equals(storeIdCouponType)){
                                storeName = "全部自营";
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    storeIdSb.append(storeId).append("-");

                                }
                                storeIdArr = "";
                            }else if("J".equals(storeIdCouponType)){
                                storeName = "全部加盟";
                                for (Store store:storeList){
                                    store.setChecked(false);
                                    String storeId = store.getStoreId();
                                    storeIdSb.append(storeId).append("-");

                                }
                                storeIdArr = "";
                            }else {

                                for (Store store:storeList){
                                    store.setChecked(false);
                                    Integer statusQuo = store.getStatusQuo();
                                    String storeId = store.getStoreId();
                                    String storeName2 = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    if(statusQuo!=null){
                                        if(statusQuo == 1){
                                            storeIdSb.append(storeId).append("-");
                                            storeNameSb.append(storeName2);
                                        }

                                    }

                                }
                                storeName = storeNameSb.toString();

                                String storeIdA= storeIdSb.toString();
                                if(storeIdSb.toString().isEmpty()){
                                    ToastUtils.showShort("请选择店铺");
                                    return;
                                }

                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);

                            }
                            SPUtils.getInstance().put("brandLogoConsolePrize",SPUtils.getInstance().getString("brandLogo"));
                            SPUtils.getInstance().put("storeIdArrayConsolePrize",storeIdArr);
                            SPUtils.getInstance().put("storeNameArrayConsolePrize",storeName);
                            SPUtils.getInstance().put("storeIdCouponTypeConsolePrize",storeIdCouponType);


                        }
                    }

                    Coupon coupon = new Coupon();
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
                    coupon.setId(campaignCoupon.getId());
                    coupon.setTimePeriod(campaignCoupon.getTimePeriod());
                    coupon.setExpireStartDate(campaignCoupon.getExpireStartDate());
                    coupon.setExpireEndDate(campaignCoupon.getExpireEndDate());
                    coupon.setName(campaignCoupon.getCouponName());
                    coupon.setExpireStartDay(campaignCoupon.getExpireStartDay());
                    coupon.setExpireEndDay(campaignCoupon.getExpireEndDay());
                    couponList.add(coupon);
                }

                if(couponList!=null && couponList.size()>0 ){
                    llAddTop.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
//                    activity.fab.setVisibility(View.VISIBLE);
                    setDotLayout(couponList);
                    pagerAdapter.updateData(couponList);
                    setPagerAdapter.updateData(couponList);
                }else {
                    llAddTop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
//                    activity.fab.setVisibility(View.GONE);
                }
            }else {
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
//                activity.fab.setVisibility(View.GONE);
            }

        }
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
  /*      couponViewPager.setPageTransformer(true,new RotationPageTransformer());
        couponViewPager.setOffscreenPageLimit(couponList.size());
        couponViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.margin_20));*/
        /*carouselViewPager.setOffscreenPageLimit(couponList.size());*/
//        couponViewPager.setPageTransformer(true,new RotationPageTransformer());
//        couponViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.margin_20));

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
                if(couponList != null && couponList.size()>0){
                    Coupon coupon = couponList.get(position);
                    CampaignCoupon campaignCoupon  = new CampaignCoupon();
                    campaignCoupon.setCouponId(coupon.getId());
                    campaignCoupon.setCouponName(coupon.getName());
                    campaignCoupon.setStoreSelectType(nextSettingFragment.couponStoreSelectType);
                    campaignCoupon.setStoreStr(nextSettingFragment.storeId);

                    if("A".equals(nextSettingFragment.expireEndType)){
                        String startDate = nextSettingFragment.startDate;
                        String endDate = nextSettingFragment.endDate;

                        campaignCoupon.setExpireStartDate(startDate!=null?startDate:"");
                        campaignCoupon.setExpireEndDate(endDate!=null?endDate:"");

                        if(nextSettingFragment.storeId!=null && (startDate!=null&& !"".equals(startDate)) && (endDate!=null&& !"".equals(endDate))){
                            campaignCouponList7.add(campaignCoupon);
                        }

                    }else if("R1".equals(nextSettingFragment.expireEndType)){
                        String startDay= nextSettingFragment.startDay;
                        String endDay = nextSettingFragment.endDay;

                        if(nextSettingFragment.storeId!=null && (startDay!=null&& !"".equals(startDay)) && (endDay!=null&& !"".equals(endDay))){
                            campaignCouponList7.add(campaignCoupon);
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


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
      if("安慰奖".equals(prizeName)){
         /*   if(activity.couponList7.isEmpty()){
                llAddTop.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                activity.fab.setVisibility(View.GONE);
            }else {
                llAddTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                activity.fab.setVisibility(View.VISIBLE);
            }
            setDotLayout(activity.couponList7);
            pagerAdapter.updateData(activity.couponList7);
            setPagerAdapter.updateData(activity.couponList7);*/
        }

    }
}
