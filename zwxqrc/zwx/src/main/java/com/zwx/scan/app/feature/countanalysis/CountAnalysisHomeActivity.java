package com.zwx.scan.app.feature.countanalysis;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.GiveAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.PinTuanAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.countanalysis.staffreward.StaffRewardAnalysisActivity;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CountAnalysisHomeActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.rv_grid)
    protected RecyclerView rvGrid;
   /* @BindView(R.id.ll_campaign)
    protected LinearLayout llCampaign;

    @BindView(R.id.ll_member)
    protected LinearLayout llMember;

    @BindView(R.id.ll_staff)
    protected LinearLayout llStaff;*/

   private  CountAnalysis countAnalysis;

   private List<CountAnalysis> list = new ArrayList<>();

   private String fromHomeFragmentIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_count_analysis_home;
    }

    @Override
    protected void initView() {
        tvTitle.setText("统计分析");
    }

    @Override
    protected void initData() {
        fromHomeFragmentIntent = getIntent().getStringExtra("fromHomeFragmentIntent");
        CountAnalysis countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_analysis_campaign);
        countAnalysis.setMemTypeName("活动报表");
        countAnalysis.setMemTypeNameEn("Activities");
        countAnalysis.setTitle("活动");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();

        countAnalysis.setDrawableName(R.drawable.ic_analysis_member);
        countAnalysis.setMemTypeName("会员管理");
        countAnalysis.setMemTypeNameEn("Members");
        countAnalysis.setTitle("会员");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_analysis_staff);
        countAnalysis.setMemTypeName("拉新报表");
        countAnalysis.setMemTypeNameEn("Pull new");
        countAnalysis.setTitle("员工拉新");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_analysis_give);
        countAnalysis.setMemTypeName("常规发券");
        countAnalysis.setMemTypeNameEn("");
        countAnalysis.setTitle("常规发券");
        list.add(countAnalysis);

        countAnalysis = new CountAnalysis();
        countAnalysis.setDrawableName(R.drawable.ic_pt_analysis);
        countAnalysis.setMemTypeName("拼团报表");
        countAnalysis.setMemTypeNameEn("Assemble");
        countAnalysis.setTitle("拼团统计分析");
        list.add(countAnalysis);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvGrid.setLayoutManager(layoutManager);
        rvGrid.setAdapter(new CountAnalysisAdapter(CountAnalysisHomeActivity.this, list, new CommonRecyclerViewHolder.OnItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                CountAnalysis countAnalysis1 = list.get(position);


                if("活动报表".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CountAnalysisHomeActivity.this,CampaignAnalysisActivity.class);
                    intent.putExtra("isShowOneStore","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("会员管理".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CountAnalysisHomeActivity.this,MemberAnalysisActivity.class);
                    intent.putExtra("isShowOneStore","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("拉新报表".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CountAnalysisHomeActivity.this,StaffRewardAnalysisActivity.class);
                    intent.putExtra("isShowOneStore","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("常规发券".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CountAnalysisHomeActivity.this,GiveAnalysisActivity.class);
                    intent.putExtra("isShowOneStore","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }else if("拼团报表".equals(countAnalysis1.getMemTypeName())){
                    intent.setClass(CountAnalysisHomeActivity.this,PinTuanAnalysisActivity.class);
                    intent.putExtra("isShowOneStore","NO");
                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        }));

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if("YES".equals(fromHomeFragmentIntent)){
                    ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                }

                ActivityUtils.finishActivity(CountAnalysisHomeActivity.class);
                break;

      /*      case R.id.ll_campaign:
                ActivityUtils.startActivity(CampaignAnalysisActivity.class,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.ll_member:
                ActivityUtils.startActivity(MemberAnalysisActivity.class,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.ll_staff:
                ActivityUtils.startActivity(StaffRewardAnalysisActivity.class,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if("YES".equals(fromHomeFragmentIntent)){
            ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ActivityUtils.finishActivity(CountAnalysisHomeActivity.class);
    }


}
