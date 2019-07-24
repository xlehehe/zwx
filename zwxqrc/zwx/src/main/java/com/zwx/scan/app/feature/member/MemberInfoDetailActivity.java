package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoBean;
import com.zwx.scan.app.data.bean.MemberInfoDetailBean;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordDetailActivity;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberInfoDetailActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.rv_list)
    public RecyclerView recyclerView;


    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_name)
    protected TextView tv_name;
    @BindView(R.id.tv_tel)
    protected TextView tv_tel;
    @BindView(R.id.tv_birthday)
    protected TextView tv_birthday;
    @BindView(R.id.tv_sex)
    protected TextView tv_sex;
    @BindView(R.id.tv_mamry)
    protected TextView tv_mamry;

//    @BindView(R.id.tv_address)
//    protected TextView tv_address;


    private String name;
    private String tel;
    private String birthday;
    private String sex;
    private String marry;

    private String address;
    public List<MemberInfoDetailBean.MemberInfoDetail> detailList = new ArrayList<MemberInfoDetailBean.MemberInfoDetail>();
    protected MemberInfoListDetailAdapter detailAdapter;

    private String memberId;
    private String userId;
    private String compMemTypeId;
    private String memberTypeName;
    private Integer sexType;
    private String isShowMemberDetail;

    private String isShowSelectMemberRecord;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_info_detail;
    }

    @Override
    protected void initView() {


        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员详细信息");
        isShowSelectMemberRecord  = getIntent().getStringExtra("isShowSelectMemberRecord");  //从消费记录跳转进入

        isShowMemberDetail = getIntent().getStringExtra("isShowMemberDetail"); //从会员信息列表进入传参
        tvRight.setText("查看消费记录");
        if(isShowSelectMemberRecord!=null && !"YES".equals(isShowSelectMemberRecord)){
            tvRight.setVisibility(View.VISIBLE);
        }else {

            if(isShowMemberDetail!=null && !"YES".equals(isShowMemberDetail)){

                tvRight.setVisibility(View.GONE);
            }else {
                if("YES".equals(isShowSelectMemberRecord)){
                    tvRight.setVisibility(View.GONE);
                }else {
                    tvRight.setVisibility(View.VISIBLE);
                }

            }

        }


        userId = SPUtils.getInstance().getString("userId");



        MemberInfoBean.MemberInfo memberInfo = (MemberInfoBean.MemberInfo )getIntent().getSerializableExtra("memberInfo");
        String sex = "-";
        sexType = memberInfo.getSexType();
        if(sexType!=null && sexType ==1){
            sex = "男";
        }else if(sexType!=null && sexType ==0){
            sex = "女";
        }
        memberTypeName = memberInfo.getMemTypeName();
        compMemTypeId = memberInfo.getCompMemTypeId();
        if(compMemTypeId !=null){

        }else {
            compMemTypeId = SPUtils.getInstance().getString("compMemTypeId");
        }
         marry =memberInfo.getMarry();
         memberId = memberInfo.getMemberId();
         birthday =memberInfo.getBrirthday();
         tel = memberInfo.getMemberTel();
         name= memberInfo.getMemberName();
         address = memberInfo.getStoreAddress();
        tv_sex.setText(sex);
        tv_name.setText(name != null && !"".equals(name)?name:"-");
//        tv_address.setText(address);

        String birth = "-";
        if(birthday != null &&  !"".equals(birthday)){
//            birth =  TimeUtils.date2String(TimeUtils.string2Date(birthday),TimeUtils.DATE_FORMAT_DATE).replace("-",".");

            String[] str = TimeUtils.date2String(TimeUtils.string2Date(birthday),TimeUtils.DATE_FORMAT_DATE).split("-");
            birth = str[0]+"年"+str[1]+"月"+str[2]+"日";

        }
        tv_birthday.setText(birth);
        tv_tel.setText(tel != null && !"".equals(tel)?tel:"-");

        String mar = "-";
        if("0".equals(marry)){
            mar = "未婚";

        }else if("1".equals(marry)){
            mar = "已婚";
        }
        tv_mamry.setText(mar);





    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.doGetMemberType(this,memberId,userId);
    }

    protected  void setAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailAdapter = new MemberInfoListDetailAdapter(MemberInfoDetailActivity.this,detailList);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(detailAdapter);
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.finishActivity(MemberInfoDetailActivity.class,
                        R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.tv_right:
                Intent intent = new Intent(MemberInfoDetailActivity.this,MemberConsumeListDetailActivity.class);
                MemberConsumeBean.MemberIT.MemberConsume memberConsume = new MemberConsumeBean.MemberIT.MemberConsume();
                memberConsume.setCompMemTypeId(compMemTypeId);
                memberConsume.setMemberName(name);
                memberConsume.setMemberTel(tel);
                memberConsume.setMemTypeName(memberTypeName);
                memberConsume.setMemberId(memberId);
                memberConsume.setSexType(sexType);
                memberConsume.setMarry(marry);
                memberConsume.setBrirthday(birthday);

                intent.putExtra("isShowMemberDetail",isShowMemberDetail);
                intent.putExtra("MemberConsume",memberConsume);
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemberInfoDetailActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
