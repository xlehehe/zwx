package com.zwx.scan.app.feature.member;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.MyPagerAdapter;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.countanalysis.campaign.CampaignChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 会员消费记录管理
 * version: 1.0
 **/
public class MemberConsumeListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tab_layout)
    protected SlidingTabLayout tabLayout;

    @BindView(R.id.pager)
    protected CustomViewPager pager;

    private MyPagerAdapter pagerAdapter;
//    protected List<String> mTitles = new ArrayList<>();
   private String[] titles = {"按消费金额", "按消费次数","按最近消费时间"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_consume;
    }

    @Override
    protected void initView() {
        tvTitle.setText("会员消费记录");
        for (String title : titles) {
            List<String> tabList= new ArrayList<>();
            if(title.equals("按消费金额")){
                tabList.add("由高到低排序");
                tabList.add("由低到高排序");
                fragments.add(MemberConsumeListFragment.getInstance("1",tabList));
            }else if(title.equals("按消费次数")){
                tabList.add("由多到少排序");
                tabList.add("由少到多排序");
                fragments.add(MemberConsumeListFragment.getInstance("2",tabList));
            }else if(title.equals("按最近消费时间")){
                tabList.add("由近到远排序");
                tabList.add("由远到近排序");
                fragments.add(MemberConsumeListFragment.getInstance("3",tabList));
            }

        }

        pagerAdapter = new MyPagerAdapter(fragments,titles,getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(pager);
        pager.setCurrentItem(0);

    }

    @Override
    protected void initData() {



    }
    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

                ActivityUtils.finishActivity(MemberConsumeListActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
//                ActivityUtils.startActivity(CountAnalysisHomeActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
//                ActivityUtils.finishActivity(MemberInfoListActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.finishActivity(MemberConsumeListActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
