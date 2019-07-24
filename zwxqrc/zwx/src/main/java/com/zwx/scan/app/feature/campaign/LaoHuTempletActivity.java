package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LaoHuTempletActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.rv_grid)
    protected MyGridView rvGrid;


    protected   LaoHuTempletListAdapter mAdapter;
    private String userId;
    protected String storeName ;
    protected String storeId;
    private String compId;

    private  String isEditCampaign = "NO";
    protected static String compaignStatus ;   //活动状态
    private String isCopyCreate = "YES" ;
    private String campaignId;
    private String campaignType;
    private String title;
    protected List<MaterialGame> materialGameList = new ArrayList<>();
    protected  String gameId;
    protected static String posterId;
    protected String bannerId;
    protected static String backGroundId;
    protected String wxIconId;
    protected String miniLinkIcon;

    protected static String shareDesc;// "shareDesc":"亲，快来点我玩游戏获大奖喽~","shareTitle":"[点我玩，获优惠]确认了眼神，你就是幸运的人"
    protected static String shareTitle;
    //素材
    protected MaterialGame materialGame = new MaterialGame();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_lao_hu_templet;
    }

    @Override
    protected void initView() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
        setPostAdapter();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");

        //活动类型
        campaignType = getIntent().getStringExtra("campaignType");
        //是否编辑还是新建
        isEditCampaign =  getIntent().getStringExtra("isEditCampaign");
        isCopyCreate =  getIntent().getStringExtra("isCopyCreate");
        compaignStatus = getIntent().getStringExtra("compaignStatus");
        if("zf".equals(campaignType)){
            title = "请选择转发活动模板";
        }else if("ge".equals(campaignType)){
            title = "请选择砸金蛋模板";
            gameId = "1";
        }else if("lh".equals(campaignType)){
            title = "请选择老虎机模板";
            gameId = "0";
        }
        tvTitle.setText(title);
        gameId = "";
        compId = "";
        presenter.queryTemplateTigerPoster(this,userId,compId,gameId,campaignType);


    }


    protected void setPostAdapter(){

        mAdapter = new LaoHuTempletListAdapter(LaoHuTempletActivity.this,materialGameList);
        rvGrid.setAdapter(mAdapter);

        rvGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    for(MaterialGame materialGame : materialGameList){
                        materialGame.setChecked(false);
                    }
                    materialGameList.get(position).setChecked(true);
                    currentNum = position;
                    materialGame = materialGameList.get(position);


                }else if(currentNum == position){ //同一个item选中变未选中
                    for(MaterialGame materialGame : materialGameList){
                        materialGame.setChecked(false);
                    }
                    currentNum = -1;
                    materialGame = materialGameList.get(position);
//                    posterId =  materialGame.getId() !=null ?materialGame.getId()+"":"";
//                    backGroundId = materialGame.getBackground();
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(MaterialGame materialGame : materialGameList){
                        materialGame.setChecked(false);
                    }
                    materialGameList.get(position).setChecked(true);
                    currentNum = position;

                    materialGame = materialGameList.get(position);
//                    posterId =  materialGame.getId() !=null ?materialGame.getId()+"":"";
//                    backGroundId = materialGame.getBackground();
                }
                posterId =  materialGame.getId() !=null ?materialGame.getId()+"":"";
                backGroundId = materialGame.getBackground();
                miniLinkIcon = materialGame.getWxLinkIcon();
                shareDesc = materialGame.getShareDesc();
                shareTitle = materialGame.getShareTitle();
                mAdapter.notifyDataSetChanged();
                setPopDialogLhGe();
            }
        });


    }

    @OnClick({R.id.iv_back,R.id.tv_right,R.id.btn_save})
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
            case R.id.btn_save:

                setIntentActivity();

                break;
        }

    }


    private void setIntentActivity(){
        Intent intent = new Intent();
        if(posterId == null || "".equals(posterId)){
            ToastUtils.showShort("请选择模板");
            return;
        }
        String lhTitle = "";
        intent.setClass(LaoHuTempletActivity.this,LaohuPinTuanNewActivity.class);
        intent.putExtra("compaignStatus","NEW");
        intent.putExtra("campaignType",campaignType);
        intent.putExtra("intentTemplet","YES");
        intent.putExtra("posterId",posterId);
        intent.putExtra("backGroundId",backGroundId);
        if("ge".equals(campaignType)){
            lhTitle = "砸金蛋活动";
        }else if("lh".equals(campaignType)){
            lhTitle = "老虎机活动";
        }
        intent.putExtra("title",lhTitle);
        intent.putExtra("isEditCampaign","NO");
        intent.putExtra("isCopyCreate","NO");
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
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



    protected void setPopDialogLhGe(){

        View customView = View.inflate(this, R.layout.layout_lh_ge_template_preview, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();
        FrameLayout fl_content = (FrameLayout) customView.findViewById(R.id.fl_content);
        Button btn_use = (Button)customView.findViewById(R.id.btn_use);
        btn_use.setText("使用并创建");
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntentActivity();
                popAlertView.dismiss();
            }
        });

        ImageView iv_banner = (ImageView)customView.findViewById(R.id.iv_banner);

//        ImageView iv_show_poster = (ImageView)customView.findViewById(R.id.iv_show_poster);
        LinearLayout ll_dis_top = (LinearLayout)customView.findViewById(R.id.ll_dis_top);
        ll_dis_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        wxIconId = materialGame.getWxLinkIcon();
        bannerId = materialGame.getBanner();
        backGroundId = materialGame.getBackground();
        miniLinkIcon = HttpUrls.IMAGE_ULR+ materialGame.getMiniLinkIcon();
        LinearLayout ll_wechart = (LinearLayout) customView.findViewById(R.id.ll_wechart);
        //小程序
        ImageView iv_img = (ImageView) customView.findViewById(R.id.iv_img);
        //微信小图标
        ImageView iv_imgs = (ImageView) customView.findViewById(R.id.iv_imgs);
        TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
        TextView tv_title2 = (TextView) customView.findViewById(R.id.tv_title2);
        TextView tv_content2 = (TextView) customView.findViewById(R.id.tv_content2);
        String wxPath = HttpUrls.IMAGE_ULR +wxIconId;

        Glide.with(this).load(miniLinkIcon).into(new SimpleTarget<Drawable>(300,200) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_img.setBackground(resource);
                }
            }
        });
        Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_imgs.setBackground(resource);
                }
            }
        });

        String forwardContent = materialGame.getShareDesc();
        String forwardTitle = materialGame.getShareTitle();


        tv_title.setText(forwardTitle != null ?forwardTitle:"");
        tv_title2.setText(forwardTitle != null ?forwardTitle:"");

        tv_content2.setText(forwardContent != null ?forwardContent:"");


        RelativeLayout ll_sylb = (RelativeLayout) customView.findViewById(R.id.ll_sylb);
        ImageView iv_lhpt_sylb_campaign = (ImageView)customView.findViewById(R.id.iv_lhpt_sylb_campaign);


        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions2 = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        Glide.with(this).load(wxPath).apply(requestOptions2).into(iv_lhpt_sylb_campaign);


       /* Glide.with(this).load(wxPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_lhpt_sylb_campaign.setBackground(resource);
                }
            }
        });
*/

        LinearLayout ll_yxzy = (LinearLayout) customView.findViewById(R.id.ll_yxzy);
        ImageView iv_lh_pt_yxzy = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy);

        LinearLayout ll_yxzy2 = (LinearLayout) customView.findViewById(R.id.ll_yxzy2);
        ImageView iv_lh_pt_yxzy2 = (ImageView)customView.findViewById(R.id.iv_lh_pt_yxzy2);
        String bgPath = HttpUrls.IMAGE_ULR +backGroundId;
  /*      Glide.with(this).load(bgPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_lh_pt_yxzy.setBackground(resource);
                }
            }
        });*/

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);


        Glide.with(this).load(bgPath).apply(requestOptions).into(iv_lh_pt_yxzy);
        Glide.with(this).load(bgPath).apply(requestOptions).into(iv_lh_pt_yxzy2);

        Button lh_pt_fxxg = (Button)customView.findViewById(R.id.lh_pt_fxxg);
        Button lh_pt_sylb = (Button)customView.findViewById(R.id.lh_pt_sylb);
        Button lh_pt_yxzy = (Button)customView.findViewById(R.id.lh_pt_yxzy);

        //首页列表
        TextView tv_time = (TextView) customView.findViewById(R.id.tv_time);
        TextView tv_campaign_name = (TextView) customView.findViewById(R.id.tv_campaign_name);
        String startDate = "";
        String endDate = "";
      /*  if(campaign != null){
            tv_campaign_name.setText(campaign.getCampaignName() != null ?campaign.getCampaignName():"");
            String startTime = campaign.getBeginDate();
            String endTime = campaign.getEndDate();

            if(startTime != null && !"".equals(startTime)){
                startDate = TimeUtils.date2String(TimeUtils.string2Date(startTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                if(endTime != null && !"".equals(endTime)){
                    endDate = TimeUtils.date2String(TimeUtils.string2Date(endTime),TimeUtils.DATE_FORMAT_DATE).replace("-",".");
                    tv_time.setText(startDate + "-" +endDate);
                }else {
                    tv_time.setText(startDate );
                }
            }else {
                tv_time.setText("");
            }

        }else {
            tv_time.setText("");
        }*/

      if("lh".equals(campaignType)){
          tv_campaign_name.setText("老虎机活动");
      }else if("ge".equals(campaignType)){
          tv_campaign_name.setText("砸金蛋活动");
      }
        tv_time.setText( "2019.01.01 - 2019.12.31" );
        String bannerPath = HttpUrls.IMAGE_ULR +bannerId;
        Glide.with(this).load(bannerPath).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_banner.setBackground(resource);
                }
            }
        });


        //默认海报
        lh_pt_fxxg.setPressed(false);
        lh_pt_sylb.setPressed(false);
        lh_pt_yxzy.setPressed(true);
        lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
        lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));


        ll_wechart.setVisibility(View.GONE);
        ll_sylb.setVisibility(View.GONE);
        if("lh".equals(LaohuPinTuanNewActivity.campaignType)){
            ll_yxzy.setVisibility(View.VISIBLE);
            ll_yxzy2.setVisibility(View.GONE);
            fl_content.setBackgroundResource(R.drawable.ic_lh_preview_bg);
        }else if("ge".equals(LaohuPinTuanNewActivity.campaignType)){
            ll_yxzy.setVisibility(View.GONE);
            ll_yxzy2.setVisibility(View.VISIBLE);
            fl_content.setBackgroundResource(R.drawable.ic_ge_three_bg);
        }
        lh_pt_fxxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(true);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.VISIBLE);
                ll_sylb.setVisibility(View.GONE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);
                fl_content.setBackgroundResource(R.drawable.ic_iphone_forward);
            }
        });



        lh_pt_sylb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(true);
                lh_pt_yxzy.setPressed(false);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.btn_color_blue));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.color_gray_deep));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.VISIBLE);
                ll_yxzy.setVisibility(View.GONE);
                ll_yxzy2.setVisibility(View.GONE);

                fl_content.setBackgroundResource(R.drawable.ic_lh_ge_preview_xcxsy_bg);
            }
        });

        lh_pt_yxzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lh_pt_fxxg.setPressed(false);
                lh_pt_sylb.setPressed(false);
                lh_pt_yxzy.setPressed(true);

                lh_pt_fxxg.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_sylb.setTextColor(getResources().getColor(R.color.color_gray_deep));
                lh_pt_yxzy.setTextColor(getResources().getColor(R.color.btn_color_blue));


                ll_wechart.setVisibility(View.GONE);
                ll_sylb.setVisibility(View.GONE);


                if("lh".equals(campaignType)){
                    ll_yxzy.setVisibility(View.VISIBLE);
                    ll_yxzy2.setVisibility(View.GONE);
                    fl_content.setBackgroundResource(R.drawable.ic_lh_preview_bg);
                }else if("ge".equals(campaignType)){
                    ll_yxzy.setVisibility(View.GONE);
                    ll_yxzy2.setVisibility(View.VISIBLE);
                    fl_content.setBackgroundResource(R.drawable.ic_ge_three_bg);
                }
            }
        });

    }


}
