package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.banner.MZBanner;
import com.zwx.library.banner.holder.MZHolderCreator;
import com.zwx.library.banner.holder.MZViewHolder;
import com.zwx.library.expandablelayout.ExpandableUtils;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.popwindow.view.PopAlertView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.bean.ResultBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.MyGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2018/10/22
 * desc   : 转发活动第四步 海报选择
 * version: 1.0
 **/
public class PosterListActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.rv_grid)
    protected MyGridView rvGrid;

    @BindView(R.id.btn_pre)
    protected Button btnPre;
    @BindView(R.id.btn_save_and_public)
    protected Button btnSavePublic;
    @BindView(R.id.ll_save)
    protected LinearLayout ll_save;
    @BindView(R.id.btn_save)
    protected Button btnSave;

    @BindView(R.id.rl_title)
    protected RelativeLayout rlTitle;
    @BindView(R.id.tv_forward_title)
    protected TextView tvForWardTitle;
    @BindView(R.id.rl_content)
    protected RelativeLayout rlContent;

    @BindView(R.id.tv_forward_content)
    protected TextView tvForWardContent;

    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;
    @BindView(R.id.iv_ellipsis_two)
    protected ImageView ivEllipsisTwo;
    @BindView(R.id.iv_three)
    protected ImageView ivThree;
    @BindView(R.id.iv_ellipsis_three)
    protected ImageView ivEllipsisThree;
    @BindView(R.id.iv_four)
    protected ImageView ivFour;

//    @BindView(R.id.rv_grid)
//    protected RecyclerView rvGrid;

//    @BindView(R.id.test_grid_view)
//    protected GridViewWithHeaderAndFooter rvGrid;
//    @BindView(R.id.recycler_view_frame)
//    protected PtrClassicFrameLayout ptr;

//    protected PosterListAdapter posterListAdapter;
//    protected RecyclerAdapterWithHF mAdapter;
    protected PosterMeListAdapter mAdapter;

    PosterMaterial posterMaterial = new PosterMaterial();

    protected List<PosterMaterial> posterMaterialList = new ArrayList<>();
    protected List<PosterMaterial> selectPosterMaterialList = new ArrayList<>();

    protected static String posterId = "";

    protected static String posterBackground;
    private String btnFlag = "save";

    private  String compId;

    private Campaign campaign = new Campaign();

    protected String posterTemplete;

    protected  List<CampaignCoupon> receiveCouponList = new ArrayList<>();
    protected  List<CampaignCoupon> forwardCouponList = new ArrayList<>();


    private String storeSelectType ;
    private String storeStr = "";
    private String userId;
    private String compaignStatus;
    CampaignNewNextTwoActivity campaignNewNextTwoActivity = new CampaignNewNextTwoActivity();
    CampaignNewNextThreeActivity campaignNewNextThreeActivity = new CampaignNewNextThreeActivity();


    protected   String isEditCampaign = "NO";

    private String isCopyCreate ;

    private String content = "亲，动动手指，点我参加活动，免费领取奖品吧。";
    private String forwardTitle = "[点我玩，获优惠]点我就拿优惠，这个机会你不能错" ;

    private String title;
    // 两次点击按钮之间的点击间隔不能少于3000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    private String wxIconId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_poster_list;
    }

    @Override
    protected void initView() {
        title  = getIntent().getStringExtra("title");


        tvTitle.setText("转发内容");
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

//        initPtr();

        setSetTep();
    }

    private void setSetTep(){

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivThree.setBackgroundResource(R.drawable.ic_three_clicked);
        ivEllipsisThree.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivFour.setBackgroundResource(R.drawable.ic_four_clicked);
    }

    @Override
    protected void initData() {
        setPostAdapter();
        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");


        isEditCampaign  = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        if("YES".equals(isEditCampaign)){
            if("YES".equals(isCopyCreate)){  //编辑页面复制并创建
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            }else {
                tvRight.setVisibility(View.GONE);
                tvRight.setText("");
            }
        }else {
            tvRight.setVisibility(View.GONE);
        }




        forwardCouponList = CampaignNewNextTwoActivity.forwardCouponList;
        receiveCouponList = CampaignNewNextThreeActivity.receiveCouponList;
//        storeStr = CampaignNewActivity.storeStr;
//        storeSelectType = CampaignNewActivity.campaignStoreSelectType;
        compaignStatus = CampaignNewActivity.compaignStatus;

//        tvForWardTitle.setText("");
//        tvForWardContent.setText("");


        campaign = CampaignNewActivity.campaign;

        if(campaign != null){
            forwardTitle = campaign.getShareTitle();
            if(forwardTitle != null && !"".equals(forwardTitle)){

            }else {
                forwardTitle = tvForWardTitle.getText().toString().trim();
            }

            content = campaign.getShareDesc();
            if(content != null && !"".equals(content)){

            }else {
                content = tvForWardContent.getText().toString().trim();
            }
            posterId = campaign.getPosterTemplete();
            if(campaign.getPoster() !=null){
                posterBackground = campaign.getPoster().getBackground();
            }

        }

        tvForWardTitle.setText(forwardTitle);
        tvForWardContent.setText(content);

        if("A".equals(storeSelectType)){
            storeStr = "";
        }else if("D".equals(storeSelectType)){
            storeStr = "";
        }else if("J".equals(storeSelectType)){
            storeStr = "";
        }else{
            storeSelectType = "";

        }

        if("S".equals(compaignStatus)||"NEW".equals(compaignStatus)){
            ll_save.setVisibility(View.VISIBLE);
            btnSavePublic.setVisibility(View.VISIBLE);
            btnPre.setVisibility(View.VISIBLE);
            presenter.queryPoster(PosterListActivity.this,compId);
        }else {
            btnSavePublic.setText("返回");
            btnSavePublic.setVisibility(View.VISIBLE);
            ll_save.setVisibility(View.GONE);
            btnPre.setVisibility(View.VISIBLE);
            posterMaterialList = new ArrayList<>();
            PosterMaterial posterMaterial = new PosterMaterial();


            posterMaterial.setChecked(true);
            posterMaterial.setBackground(posterBackground);
            if(posterId != null && !"".equals(posterId)){
                posterMaterial.setId(Long.parseLong(posterId));
                posterMaterialList.add(posterMaterial);

                setPostAdapter();
            }

        }

        if("S".equals(CampaignNewActivity.compaignStatus)||"NEW".equals(CampaignNewActivity.compaignStatus)){
            rlTitle.setEnabled(true);
            rlContent.setEnabled(true);
            rvGrid.setEnabled(true);

        }else {
            rlTitle.setEnabled(false);
            rlContent.setEnabled(false);
            rvGrid.setEnabled(false);
        }
    }

    protected void setPostAdapter(){

        mAdapter = new PosterMeListAdapter(PosterListActivity.this,posterMaterialList);
        rvGrid.setAdapter(mAdapter);
        rvGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    for(PosterMaterial posterMaterial : posterMaterialList){
                        posterMaterial.setChecked(false);
                    }
                    posterMaterialList.get(position).setChecked(true);
                    currentNum = position;
                    posterMaterial = posterMaterialList.get(position);
                    posterId =  posterMaterial.getId() !=null ?posterMaterial.getId()+"":"";
                    wxIconId = posterMaterial.getWechatIcon();
                }else if(currentNum == position){ //同一个item选中变未选中
                    for(PosterMaterial posterMaterial : posterMaterialList){
                        posterMaterial.setChecked(false);
                    }
                    currentNum = -1;
                    posterMaterial = posterMaterialList.get(position);
                    posterId =  posterMaterial.getId() !=null ?posterMaterial.getId()+"":"";
                    wxIconId = posterMaterial.getWechatIcon();
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(PosterMaterial posterMaterial : posterMaterialList){
                        posterMaterial.setChecked(false);
                    }
                    posterMaterialList.get(position).setChecked(true);
                    currentNum = position;

                    posterMaterial = posterMaterialList.get(position);
                    posterId =  posterMaterial.getId() !=null ?posterMaterial.getId()+"":"";
                    wxIconId = posterMaterial.getWechatIcon();
                }


                mAdapter.notifyDataSetChanged();
                setPopDialog();

            }
        });


    }

    @OnClick({R.id.iv_back,R.id.tv_right,R.id.rl_title,R.id.rl_content,
            R.id.btn_save_and_public,R.id.btn_save,R.id.btn_pre})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
               /* ActivityUtils.finishActivity(PosterListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);*/

                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                isCopyCreate = "NO";
                isEditCampaign = "YES";
                CampaignNewActivity.compaignStatus = "NEW";
                content = tvForWardContent.getText().toString().trim();
                forwardTitle = tvForWardTitle.getText().toString().trim();
                CampaignNewActivity.campaign.setShareTitle(forwardTitle);
                CampaignNewActivity.campaign.setShareDesc(content);
                CampaignNewActivity.campaign.setPosterTemplete(posterId);
                CampaignNewActivity.campaign.setCampaignId(null);
                CampaignNewActivity.campaign.setCampaignName("");


                Intent intent1 = new Intent(PosterListActivity.this,CampaignNewActivity.class);
                intent1.putExtra("title",title);
                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("campaignType","zf");
                intent1.putExtra("compaignStatus",CampaignNewActivity.compaignStatus);
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.btn_save:
                content = tvForWardContent.getText().toString().trim();
                forwardTitle = tvForWardTitle.getText().toString().trim();
                btnFlag = "save";
                CampaignNewActivity.campaign.setShareDesc(content);
                CampaignNewActivity.campaign.setShareTitle(forwardTitle);
                CampaignNewActivity.campaign.setPosterTemplete(posterId);
                CampaignNewActivity.campaign.setPoster(null);
                if(posterId == null || "".equals(posterId)){
                    String posterName =posterMaterial.getName();
                    setDialog("在第四步，未选择海报！");
                    return ;
                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                    presenter.saveCampaignInfo(this,campaign,CampaignNewActivity.storeIdCampaignType,CampaignNewActivity.storeStr,forwardCouponList,receiveCouponList,posterId,userId,compId,btnFlag);
                }
                break;
            case R.id.btn_save_and_public:
                content = tvForWardContent.getText().toString().trim();
                forwardTitle = tvForWardTitle.getText().toString().trim();
                CampaignNewActivity.campaign.setShareDesc(content);
                CampaignNewActivity.campaign.setShareTitle(forwardTitle);
                CampaignNewActivity.campaign.setPosterTemplete(posterId);
                CampaignNewActivity.campaign.setPoster(null);
                CampaignNewActivity.campaign.setCampaignType("zf");
                btnFlag = "saveAndpublic";
                if(check()){
                    return;
                }
                if("S".equals(compaignStatus)||"NEW".equals(compaignStatus)){
//
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {
                        presenter.saveCampaignInfo(this,campaign,CampaignNewActivity.storeIdCampaignType,CampaignNewActivity.storeStr,forwardCouponList,receiveCouponList,posterId,userId,compId,btnFlag);
                    }

                }else {
                    tvForWardTitle.setText("");
                    tvForWardContent.setText("");
                    ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                }
                break;
            case R.id.btn_pre:

                content = tvForWardContent.getText().toString().trim();
                forwardTitle = tvForWardTitle.getText().toString().trim();
                CampaignNewActivity.campaign.setShareDesc(content);
                CampaignNewActivity.campaign.setShareTitle(forwardTitle);
                CampaignNewActivity.campaign.setPosterTemplete(posterId);


                ActivityUtils.finishActivity(PosterListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

                break;

            case R.id.rl_title:
                 intent = new Intent(PosterListActivity.this,CampaignTitleContentActivity.class);
                intent.putExtra("title","转发标题");
                intent.putExtra("forwardTitle","title");
                intent.putExtra("content",forwardTitle);
                ActivityUtils.startActivityForResult(PosterListActivity.this,intent,4000,R.anim.slide_in_right,R.anim.slide_out_left);
//                ActivityUtils.startActivity(.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_content:

                 intent = new Intent(PosterListActivity.this,CampaignTitleContentActivity.class);
                intent.putExtra("title","转发内容");
                intent.putExtra("forwardTitle","content");
                intent.putExtra("content",content);
                ActivityUtils.startActivityForResult(PosterListActivity.this,intent,5000,R.anim.slide_in_right,R.anim.slide_out_left);
                break;


        }
    }

    private boolean check(){

        if("save".equals(btnFlag)){

        }else {
            boolean result =true;
            if(campaign !=null){
                String campaignName = campaign.getCampaignName();


                if(campaignName == null ||"".equals(campaignName)){
//                ToastUtils.showShort("请输入活动名称");
                    setDialog("在第一步，活动名称未填写完整！");
                    return true;
                }

                String startDate = campaign.getBeginDate();
                String endDate = campaign.getEndDate();
                if(startDate == null ||"".equals(startDate)){
                    setDialog("在第一步，活动开始日期未填写完整！");
                    return true;
                }

                if(endDate == null ||"".equals(endDate)){
                    setDialog("在第一步，活动结束日期未填写完整！");
                    return true;
                }
            }else {
                setDialog("在第一步，信息未填写完整！");
                return true;
            }


            if(forwardCouponList!=null && forwardCouponList.size()>0){
                for(CampaignCoupon campaignCoupon : forwardCouponList){
                    String startDate =campaignCoupon.getExpireStartDate();
                    String endDate = campaignCoupon.getExpireEndDate();
                    String startDay =campaignCoupon.getExpireStartDay();
                    String endDay = campaignCoupon.getExpireEndDay();
                    String couponName =campaignCoupon.getCouponName();
                    String expireType = campaignCoupon.getExpireEndType();
                    if("A".equals(expireType)){
                        if(startDate == null ||"".equals(startDate)){
                            setDialog("在第二步，活动开始时间未填写完整！");

                            return true;
                        }

                        if(endDate == null ||"".equals(endDate)){
                            setDialog("在第二步，活动结束时间未填写完整！");
                            return true;
                        }
                    }else if("R1".equals(expireType)){
                        if(startDay == null ||"".equals(startDay)){
                            setDialog("在第二步，生效时间未填写完整！");
                            return true;
                        }

                        if(endDay == null ||"".equals(endDay)){
                            setDialog("在第二步，有效时间未填写完整！");
                            return true;
                        }


                    }
                    if(couponName == null ||"".equals(couponName)){
                        setDialog("在第二步，优惠券名称未填写完整！");
                        return true;
                    }

                }
            }else {
                setDialog("在第二步，信息未填写完整！");
                return true;
            }

            if(receiveCouponList!=null && receiveCouponList.size()>0){
                for(CampaignCoupon receiveCoupon : receiveCouponList){
                    String startDate =receiveCoupon.getExpireStartDate();
                    String endDate = receiveCoupon.getExpireEndDate();
                    String startDay =receiveCoupon.getExpireStartDay();
                    String endDay = receiveCoupon.getExpireEndDay();
                    String couponName =receiveCoupon.getCouponName();
                    String expireType = receiveCoupon.getExpireEndType();
                    if("A".equals(expireType)){
                        if(startDate == null ||"".equals(startDate)){
                            setDialog("在第三步，活动开始时间未填写完整！");
                            return true;
                        }

                        if(endDate == null ||"".equals(endDate)){
                            setDialog("在第三步，活动结束时间未填写完整！");
                            return true;
                        }
                    }else if("R1".equals(expireType)){
                        if(startDay == null ||"".equals(startDay)){
                            setDialog("在第三步，生效时间未填写完整！");
                            return true;
                        }

                        if(endDay == null ||"".equals(endDay)){
                            setDialog("在第三步，有效时间未填写完整！");
                            return true;
                        }
                    }
                    if(couponName == null ||"".equals(couponName)){
                        setDialog("在第三步，优惠券名称未填写完整！");
                        return true;
                    }
                }
            }else {
                setDialog("在第三步，未填写完整！");
                return true;
            }


            if(content == null ||"".equals(content)){
                setDialog("在第四步，转发标题未填写！");
                return true;
            }
            if(forwardTitle == null || "".equals(forwardTitle)){
                setDialog("在第四步，转发内容未填写！");
                return true;
            }

            if(posterId == null || "".equals(posterId)){
                String posterName =posterMaterial.getName();
                setDialog("在第四步，未选择海报！");
                return true;
            }
        }

        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SPUtils.getInstance().remove("receiveCouponList");
        SPUtils.getInstance().remove("forwardCouponList");
        SPUtils.getInstance().remove("campaign",true);
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    private void setPopDialog(){

        View customView = View.inflate(this, R.layout.layout_campaign_poster_dialog, null);

        PopWindow  popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();

        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posterMaterial !=null){
                    posterId = String.valueOf(posterMaterial.getId());
                }

                popAlertView.dismiss();
            }
        });

        FrameLayout fl_content = (FrameLayout)customView.findViewById(R.id.fl_content);


        ImageView iv_show_poster = (ImageView)customView.findViewById(R.id.iv_show_poster);
        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_hf = (LinearLayout) customView.findViewById(R.id.ll_hf);
        ImageView iv_banner = (ImageView)customView.findViewById(R.id.iv_banner);
        LinearLayout ll_wechart = (LinearLayout) customView.findViewById(R.id.ll_wechart);
        ImageView iv_imgs = (ImageView)customView.findViewById(R.id.iv_imgs);
        ImageView iv_imgs2 = (ImageView)customView.findViewById(R.id.iv_imgs2);
        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) customView.findViewById(R.id.tv_content);
        TextView tv_title2 = (TextView) customView.findViewById(R.id.tv_title2);
        TextView tv_content2 = (TextView) customView.findViewById(R.id.tv_content2);

        Button iv_poster = (Button)customView.findViewById(R.id.iv_poster);
        Button iv_hengfu = (Button)customView.findViewById(R.id.iv_hengfu);
        Button iv_fenxiang = (Button)customView.findViewById(R.id.iv_fenxiang);
        //默认海报
        iv_poster.setPressed(true);
        iv_hengfu.setPressed(false);
        iv_fenxiang.setPressed(false);

        iv_show_poster.setVisibility(View.VISIBLE);
        ll_hf.setVisibility(View.GONE);
        ll_wechart.setVisibility(View.GONE);

        iv_poster.setTextColor(getResources().getColor(R.color.btn_color_blue));
        iv_hengfu.setTextColor(getResources().getColor(R.color.color_gray_deep));
        iv_fenxiang.setTextColor(getResources().getColor(R.color.color_gray_deep));
//        iv_poster.setBackgroundResource(R.drawable.ic_poster_righted);
//        iv_hengfu.setBackgroundResource(R.drawable.ic_poster_unright);
//        iv_fenxiang.setBackgroundResource(R.drawable.ic_poster_unright);

        fl_content.setBackgroundResource(R.drawable.ic_iphone_poster);
//        String wxPath = HttpUrls.IMAGE_ULR+ posterMaterial.getWechatIcon();
        String wxPath = HttpUrls.IMAGE_ULR +wxIconId;
        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(100,100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_imgs.setBackground(resource);    //设置背景
                }
            }
        });

        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(100,100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_imgs2.setBackground(resource);    //设置背景
                }
            }
        });

        if(posterMaterial !=null){
            String ivbg = HttpUrls.IMAGE_ULR+posterMaterial.getBackground();
            Glide.with(this).load(ivbg).into(new SimpleTarget<Drawable>(100,220) {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        iv_show_poster.setBackground(resource);    //设置背景
                    }
                }
            });

            String bannerId = HttpUrls.IMAGE_ULR + posterMaterial.getBanner();
            Glide.with(this).load(bannerId).into(new SimpleTarget<Drawable>(100,220) {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        iv_banner.setBackground(resource);
                    }
                }
            });
        }

        if("NEW".equals(CampaignNewActivity.compaignStatus)){
            if(forwardTitle != null && !"".equals(forwardTitle)){
                tv_title.setText(forwardTitle);
            }
         /*   if(content != null && !"".equals(content)){
                tv_content.setText(content);
            }*/


            if(forwardTitle != null && !"".equals(forwardTitle)){
                tv_title2.setText(forwardTitle);
            }
            if(content != null && !"".equals(content)){
                tv_content2.setText(content);
            }
        }else {
            if(campaign != null){

                forwardTitle = campaign.getShareTitle();
                content = campaign.getShareDesc();

                if(forwardTitle != null && !"".equals(forwardTitle)){
                    tv_title.setText(forwardTitle);
                }
             /*   if(content != null && !"".equals(content)){
                    tv_content.setText(content);
                }*/


                if(forwardTitle != null && !"".equals(forwardTitle)){
                    tv_title2.setText(forwardTitle);
                }
                if(content != null && !"".equals(content)){
                    tv_content2.setText(content);
                }
            }
        }


        //小程序
        TextView tv_time = (TextView) customView.findViewById(R.id.tv_time);
        TextView tv_campaign_name = (TextView) customView.findViewById(R.id.tv_campaign_name);
        String startDate = "";
        String endDate = "";
        if(campaign != null){
            tv_campaign_name.setText(campaign.getCampaignName() != null ?campaign.getCampaignName():"");
            String startTime = campaign.getBeginDate();
            String endTime = campaign.getEndDate();

            if(startTime != null && !"".equals(startTime)){
                startDate = TimeUtils.date2String(TimeUtils.string2Date(startTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                if(endTime != null && !"".equals(endTime)){
                    endDate = TimeUtils.date2String(TimeUtils.string2Date(endTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                    tv_time.setText(startDate + "-" +endDate);
                }else {
                    tv_time.setText(startDate);
                }
            }else {
                tv_time.setText("");
            }

        }else {
            tv_time.setText("");
        }

        ImageView iv_lhpt_sylb_campaign = (ImageView)customView.findViewById(R.id.iv_lhpt_sylb_campaign);

        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(wxPath).apply(requestOptions).into(iv_lhpt_sylb_campaign);

      /*  ImageView iv_imgs2 = (ImageView)customView.findViewById(R.id.iv_imgs2);
        Glide.with(this).load(campaignPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_imgs2.setBackground(resource);
                }
            }
        });*/
        iv_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_poster.setPressed(true);
                iv_hengfu.setPressed(false);
                iv_fenxiang.setPressed(false);

                iv_poster.setTextColor(getResources().getColor(R.color.btn_color_blue));
                iv_hengfu.setTextColor(getResources().getColor(R.color.color_gray_deep));
                iv_fenxiang.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                iv_poster.setBackgroundResource(R.drawable.ic_poster_righted);
//                iv_hengfu.setBackgroundResource(R.drawable.ic_poster_unright);
//                iv_fenxiang.setBackgroundResource(R.drawable.ic_poster_unright);

                iv_show_poster.setVisibility(View.VISIBLE);
                ll_hf.setVisibility(View.GONE);
                ll_wechart.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_iphone_poster);
            }
        });

        iv_hengfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_poster.setPressed(false);
                iv_hengfu.setPressed(true);

                iv_fenxiang.setPressed(false);
                iv_show_poster.setVisibility(View.GONE);
                ll_hf.setVisibility(View.VISIBLE);
                ll_wechart.setVisibility(View.GONE);
                iv_poster.setTextColor(getResources().getColor(R.color.color_gray_deep));
                iv_hengfu.setTextColor(getResources().getColor(R.color.btn_color_blue));
                iv_fenxiang.setTextColor(getResources().getColor(R.color.color_gray_deep));
//                iv_poster.setBackgroundResource(R.drawable.ic_poster_unright);
//                iv_hengfu.setBackgroundResource(R.drawable.ic_poster_righted);
//                iv_fenxiang.setBackgroundResource(R.drawable.ic_poster_unright);
                fl_content.setBackgroundResource(R.drawable.ic_lh_ge_preview_xcxsy_bg);
            }
        });

        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_poster.setPressed(false);
                iv_hengfu.setPressed(false);
                iv_fenxiang.setPressed(true);
                iv_show_poster.setVisibility(View.GONE);
                ll_hf.setVisibility(View.GONE);
                ll_wechart.setVisibility(View.VISIBLE);
                iv_poster.setTextColor(getResources().getColor(R.color.color_gray_deep));
                iv_hengfu.setTextColor(getResources().getColor(R.color.color_gray_deep));
                iv_fenxiang.setTextColor(getResources().getColor(R.color.btn_color_blue));
//                iv_poster.setBackgroundResource(R.drawable.ic_poster_unright);
//                iv_hengfu.setBackgroundResource(R.drawable.ic_poster_unright);
//                iv_fenxiang.setBackgroundResource(R.drawable.ic_poster_righted);

                fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
            }
        });

    }




    public void setDialog(String str2){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(str2);
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 4000){
            if(requestCode == 4000){
                if(data != null){
                    forwardTitle = data.getStringExtra("forward");
                    tvForWardTitle.setText(forwardTitle!=null?forwardTitle:"");
                    campaign.setShareTitle(forwardTitle);
                }

            }
        }else {
            if(requestCode == 5000){
                if(data != null){
                    content = data.getStringExtra("forward");
                    tvForWardContent.setText(content!=null?content:"");
                    campaign.setShareDesc(content);
                }

            }

        }
    }
}
