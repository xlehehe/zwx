package com.zwx.scan.app.feature.modulemore;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.cateringinfomanage.CateringinfoManageActivity;
import com.zwx.scan.app.feature.contractmanage.ContractActivity;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.couponmanage.CouponManageActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponManageActivity;
import com.zwx.scan.app.feature.financemanage.PayFeeManageActivity;
import com.zwx.scan.app.feature.financialaffairs.CollectionMoneyAccountSettingActivity;
import com.zwx.scan.app.feature.financialaffairs.CollectionMoneyIntoAccountListActivity;
import com.zwx.scan.app.feature.financialaffairs.MemCardEmploeeSaleReportActivity;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.member.MemberCardManageActivity;
import com.zwx.scan.app.feature.member.MemberCardStreamActivity;
import com.zwx.scan.app.feature.member.MemberConsumeListActivity;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
import com.zwx.scan.app.feature.financialaffairs.TradeDrawingActivity;
import com.zwx.scan.app.feature.poster.PosterManageActivity;
import com.zwx.scan.app.feature.poster.PosterMaterListActivity;
import com.zwx.scan.app.feature.poster.WebViewActivity;
import com.zwx.scan.app.feature.ptmanage.PtManageActivity;
import com.zwx.scan.app.feature.ptmanage.PtOrderActivity;
import com.zwx.scan.app.feature.shop.ProductVerificationRecordActivity;
import com.zwx.scan.app.feature.shop.RedEnvelopeActivity;
import com.zwx.scan.app.feature.shop.ShopManageActivity;
import com.zwx.scan.app.feature.shop.ShopOrderActivity;
import com.zwx.scan.app.feature.shop.ShopSettingActivity;
import com.zwx.scan.app.feature.staffmanage.PullQrcManageActivity;
import com.zwx.scan.app.feature.staffmanage.StaffListActivity;
import com.zwx.scan.app.feature.verification.ProductVerificationActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;

public class ModuleMoreActivity extends BaseActivity  implements View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.rb_verification)
    protected RadioButton rbVeri;

    @BindView(R.id.rb_verification_record)
    protected RadioButton rbRecord;


    @BindView(R.id.rb_count_analysis)
    protected RadioButton rbCount;

    //会员管理
    @BindView(R.id.rg_member)
    protected RadioGroup rgMember;

    @BindView(R.id.rb_member_info)
    protected RadioButton rbInfo;

    @BindView(R.id.rb_member_consume_record)
    protected RadioButton rbConsume;
    @BindView(R.id.rb_member_red_envelopo)
    protected RadioButton rbRedEnvelopo;
    //会员卡管理
    @BindView(R.id.rg_member_card)
    protected RadioGroup rgMemberCard;

    @BindView(R.id.rb_member_card_manage)
    protected RadioButton rbMemberCardManage;

    @BindView(R.id.rb_member_card_manage2)
    protected RadioButton rbMemberCardManage2;
    @BindView(R.id.rb_member_card_manage3)
    protected RadioButton rbMemberCardManage3;

    @BindView(R.id.rb_poster_manage)
    protected RadioButton rbPosterManage;
    //活动管理
    @BindView(R.id.rg_coupon_campaign)
    protected RadioGroup rgCouponCampaign;

    @BindView(R.id.rb_coupon_manage)
    protected RadioButton rbCouponManage;


    @BindView(R.id.rb_campaign)
    protected RadioButton rbCampaign;
    @BindView(R.id.rb_changguifafang_manage)
    protected RadioButton rb_changguifafang_manage;

    @BindView(R.id.radiogroup)
    protected RadioGroup radioGroup;

    @BindView(R.id.rg_staff)
    protected RadioGroup rgStaff;

    //新建海报
    @BindView(R.id.rg_coupon_campaign2)
    protected RadioGroup rgCouponCampaign2;

    @BindView(R.id.rb_new_poster_manage)
    protected RadioButton rbPosterManage2;


    //餐企管理
    @BindView(R.id.rb_staff_mamange)
    protected RadioButton rbStaff;
    @BindView(R.id.rb_cateringinfo_mamange)
    protected RadioButton rbCateringinfo;
    @BindView(R.id.rb_account_mamange)
    protected RadioButton rbAccount;
    @BindView(R.id.rb_qrc_manage)
    protected RadioButton rbQrc;

    //拼团
    @BindView(R.id.rg_pt)
    protected RadioGroup rgPt;

    @BindView(R.id.rb_pt_manage)
    protected RadioButton rbPtManage;
    @BindView(R.id.rb_pt_order)
    protected RadioButton rbPtOrder;

    //合同管理
    @BindView(R.id.rg_contract)
    protected RadioGroup rgContract;

    @BindView(R.id.rb_contract_mamange)
    protected RadioButton rbContract;
    @BindView(R.id.rb_pay_fee_mamange)
    protected RadioButton rbPayFee;

    @BindView(R.id.rb_invoice_mamange)
    protected RadioButton rbInvoice;

    //商城

    @BindView(R.id.rg_shop)
    protected RadioGroup rgShop;

    //商品核销
    @BindView(R.id.rb_product_verification)
    protected RadioButton rbProductVerification;

    //商品核销记录
    @BindView(R.id.rb_product_verification_manage)
    protected RadioButton rbProductVerificationManage;

    @BindView(R.id.rb_shop_setting)
    protected RadioButton rbShopSetting;

    @BindView(R.id.rb_shop_manage)
    protected RadioButton rbShopManage;

    @BindView(R.id.rb_shop_order)
    protected RadioButton rbShopOrder;

    @BindView(R.id.rg_shop2)
    protected RadioGroup rgShop2;


    //财务中心
    @BindView(R.id.rg_financial_affairs)
    protected RadioGroup rg_financial_affairs;

    //收款账号设置
    @BindView(R.id.rb_collection_kuan_setting)
    protected RadioButton rb_collection_kuan_setting;

    //收款到账
    @BindView(R.id.rb_collection_kuan_to_account)
    protected RadioButton rb_collection_kuan_to_account;

    //收款流水
    @BindView(R.id.rb_collection_flow)
    protected RadioButton rb_collection_flow;

    //会员卡员工销售报表
    @BindView(R.id.rb_card_emploee_report)
    protected RadioButton rb_card_emploee_report;

    //拼团员工销售报表
    @BindView(R.id.rb_pt_emploee_report)
    protected RadioButton rb_pt_emploee_report;
    //商品员工销售报表
    @BindView(R.id.rb_goods_emploee_report)
    protected RadioButton rb_goods_emploee_report;


    @BindView(R.id.rg_financial_affairs2)
    protected RadioGroup rg_financial_affairs2;
    //非员工销售报表
    @BindView(R.id.rb_no_emploee_report)
    protected RadioButton rb_no_emploee_report;

    //交易提款
    @BindView(R.id.rb_trade_drawing)
    protected RadioButton rb_trade_drawing;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_more;
    }

    @Override
    protected void initView() {
        tvTitle.setText("更多");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rb_verification:
                        rbVeri.setChecked(false);
                        Intent intent = new Intent();
                        intent.setClass(ModuleMoreActivity.this,VerificationActivity.class);
                        intent.putExtra("title","核销");
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_verification_record:
                        rbRecord.setChecked(false);
                        ActivityUtils.startActivity(VerificationRecordActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_count_analysis:
                        rbCount.setChecked(false);
                        ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });

        rgStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_staff_mamange:
                        rbStaff.setChecked(false);
                        ActivityUtils.startActivity(StaffListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);


                        break;

                    case R.id.rb_cateringinfo_mamange:
                        rbCateringinfo.setChecked(false);
                        ActivityUtils.startActivity(CateringinfoManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_account_mamange:
                        rbAccount.setChecked(false);
                        ActivityUtils.startActivity(AccountActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_qrc_manage:
                        rbQrc.setChecked(false);
                        ActivityUtils.startActivity(PullQrcManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });
        rgCouponCampaign.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_poster_manage:
                        rbPosterManage.setChecked(false);
                        ActivityUtils.startActivity(PosterManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_coupon_manage:
                        rbCouponManage.setChecked(false);
                        ActivityUtils.startActivity(CouponManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_campaign:
                        rbCampaign.setChecked(false);
                        ActivityUtils.startActivity(CampaignListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_changguifafang_manage:
                        rb_changguifafang_manage.setChecked(false);
                        ActivityUtils.startActivity(GiveCouponManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }
            }
        });

        rgCouponCampaign2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_new_poster_manage:
                        rbPosterManage.setChecked(false);
                        ActivityUtils.startActivity(PosterMaterListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    /*case R.id.rb_coupon_manage:
                        rbCouponManage.setChecked(false);
                        ActivityUtils.startActivity(CouponManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_campaign:
                        rbCampaign.setChecked(false);
                        ActivityUtils.startActivity(CampaignListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_changguifafang_manage:
                        rb_changguifafang_manage.setChecked(false);
                        ActivityUtils.startActivity(GiveCouponManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;*/
                    default:
                        break;
                }
            }
        });

        rgPt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_pt_manage:
                        rbPtManage.setChecked(false);
                        ActivityUtils.startActivity(PtManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_pt_order:
                        rbPtOrder.setChecked(false);
                        ActivityUtils.startActivity(PtOrderActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });

        rgMember.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_member_info:
                        rbInfo.setChecked(false);
                        ActivityUtils.startActivity(MemberInfoListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_member_consume_record:
                        rbConsume.setChecked(false);
                        ActivityUtils.startActivity(MemberConsumeListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_member_red_envelopo:
                        rbRedEnvelopo.setChecked(false);
                        ActivityUtils.startActivity(RedEnvelopeActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });

        rgMemberCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_member_card_manage:
                        rbMemberCardManage.setChecked(false);
                        ActivityUtils.startActivity(MemberCardManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_member_card_manage2:
                        rbMemberCardManage2.setChecked(false);
                        ActivityUtils.startActivity(MemberCardStreamActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });

        rgContract.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_contract_mamange:
                        rbContract.setChecked(false);
                        ActivityUtils.startActivity(ContractActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_pay_fee_mamange:
                        rbPayFee.setChecked(false);
                        ActivityUtils.startActivity(PayFeeManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_invoice_mamange:
                        rbInvoice.setChecked(false);
//                        ToastUtils.showShort("该功能暂未实现");
//                        ActivityUtils.startActivity(ContractActivity.class,
//                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    default:
                        break;
                }

            }
        });


        rgShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_product_verification:
                        rbProductVerification.setChecked(false);
                        ActivityUtils.startActivity(ProductVerificationActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_product_verification_manage:
                        rbProductVerificationManage.setChecked(false);
                        ActivityUtils.startActivity(ProductVerificationRecordActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_shop_setting:
                        rbShopSetting.setChecked(false);
                        ActivityUtils.startActivity(ShopSettingActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_shop_manage:
                        ShopManageActivity.productSetNewList.clear();;
                        rbShopManage.setChecked(false);
                        ActivityUtils.startActivity(ShopManageActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;


                    case R.id.rb_shop_order:
                        rbShopOrder.setChecked(false);
                        ShopOrderActivity.orderList.clear();
                        ActivityUtils.startActivity(ShopOrderActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }

            }
        });

        rgShop2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_shop_order:
                        rbShopOrder.setChecked(false);
                        ActivityUtils.startActivity(ShopOrderActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    default:
                        break;
                }

            }
        });

        rg_financial_affairs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_collection_kuan_setting:
                        rb_collection_kuan_setting.setChecked(false);
                        ActivityUtils.startActivity(CollectionMoneyAccountSettingActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_collection_kuan_to_account:
                        rb_collection_kuan_to_account.setChecked(false);
                        ActivityUtils.startActivity(CollectionMoneyIntoAccountListActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_collection_flow:
                        rb_collection_flow.setChecked(false);
                        ToastUtils.showShort("暂未实现");
//                        ActivityUtils.startActivity(ProductVerificationActivity.class,
//                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_card_emploee_report:
                        rb_card_emploee_report.setChecked(false);
                        Intent intent = new Intent(ModuleMoreActivity.this,MemCardEmploeeSaleReportActivity.class);
                        intent.putExtra("orderType","M");  //订单类型 购卡M 拼团G  商城商品P
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;


                    default:
                        break;
                }

            }
        });

        rg_financial_affairs2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_pt_emploee_report:
                        rb_pt_emploee_report.setChecked(false);

                        Intent intent = new Intent(ModuleMoreActivity.this,MemCardEmploeeSaleReportActivity.class);
                        intent.putExtra("orderType","G");  //订单类型 购卡M 拼团G  商城商品P
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.rb_goods_emploee_report:
                        rb_goods_emploee_report.setChecked(false);
                         intent = new Intent(ModuleMoreActivity.this,MemCardEmploeeSaleReportActivity.class);
                        intent.putExtra("orderType","P");  //订单类型 购卡M 拼团G  商城商品P
                        ActivityUtils.startActivity(intent,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.rb_no_emploee_report:
                        rb_no_emploee_report.setChecked(false);
                        ToastUtils.showShort("暂未实现");
                        break;

                    case R.id.rb_trade_drawing:
                        rb_trade_drawing.setChecked(false);
                        ActivityUtils.startActivity(TradeDrawingActivity.class,
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    default:
                        break;
                }

            }
        });

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
//                ActivityUtils.finishActivity(ModuleMoreActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
//        ActivityUtils.finishActivity(ModuleMoreActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
