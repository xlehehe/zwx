package com.zwx.scan.app.feature.modulemore;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CommonImageBean;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   : 更多模块 - 动态添加
 * version: 1.0
 **/
public class ModuleMoreListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.list_view)
    protected MyListView listView;
    protected List<ModuleBean> moduleBeanList = new ArrayList<>();
    protected List<CommonImageBean> imgeList = new ArrayList<>();
    protected ModuleListViewAdapter adapter = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_more_list;
    }

    @Override
    protected void initView() {

        tvTitle.setText("更多");
    }

    @Override
    protected void initData() {


        ModuleBean moduleBean = new ModuleBean();
        moduleBean.setName("常用功能");
        moduleBean.setId("1");
        imgeList = new ArrayList<>();
        CommonImageBean imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_home_scancode);
        imageBean.setName("核销");
        imageBean.setType("1");
        imgeList.add(imageBean);

        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_home_recording);
        imageBean.setName("核销记录");
        imageBean.setType("2");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_home_statistics);
        imageBean.setName("统计分析");
        imageBean.setType("3");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setName("会员管理");
        moduleBean.setId("2");
        imgeList = new ArrayList<>();
         imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_member_info);
        imageBean.setName("会员卡信息管理");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_consume_record);
        imageBean.setName("会员消费记录");
        imageBean.setType("2");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_more_red_envelope);
        imageBean.setName("会员红包查询");
        imageBean.setType("3");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setName("会员卡管理");
        moduleBean.setId("3");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_member_card_manage);
        imageBean.setName("会员卡管理");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_member_card_liushui);
        imageBean.setName("会员卡收款流水");
        imageBean.setType("2");
        imgeList.add(imageBean);

        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setName("优惠券和活动");
        moduleBean.setId("4");
        imgeList = new ArrayList<>();

//        imageBean = new CommonImageBean();
//        imageBean.setDrawable(R.drawable.ic_poster_manage);
//        imageBean.setName("新建模板");
//        imageBean.setType("1");
//        imgeList.add(imageBean);

        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_coupon_manage);
        imageBean.setName("优惠券管理");
        imageBean.setType("2");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_campaign_manage);
        imageBean.setName("活动管理");
        imageBean.setType("3");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_changguifafang_manage);
        imageBean.setName(getResources().getString(R.string.changguifaquan_manage));
        imageBean.setType("4");
        imgeList.add(imageBean);

//        imageBean = new CommonImageBean();
//        imageBean.setDrawable(R.drawable.ic_new_poster_manage);
//        imageBean.setName("我的海报");
//        imageBean.setType("5");
//        imgeList.add(imageBean);

        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setId("5");
        moduleBean.setName("拼团");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_pt_order);
        imageBean.setName("拼团管理");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_pt_manage);
        imageBean.setName("拼团订单");
        imageBean.setType("2");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setId("6");
        moduleBean.setName("商城");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_product_verification);
        imageBean.setName("商品核销");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_product_verification_manage);
        imageBean.setName("商品核销记录");
        imageBean.setType("2");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_shop_setting);
        imageBean.setName("商城基本设置");
        imageBean.setType("3");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_shop_good_manage);
        imageBean.setName("商品管理");
        imageBean.setType("4");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_shop_order_manage);
        imageBean.setName("订单管理");
        imageBean.setType("5");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setId("7");
        moduleBean.setName("财务中心");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_collection_account_setting);
        imageBean.setName("收款帐号设置");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_collection_money_to_account);
        imageBean.setName("收款到账");
        imageBean.setType("2");
        imgeList.add(imageBean);
        /*imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_collect_consume_manage);
        imageBean.setName("收款流水");
        imageBean.setType("3");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_memcard_sale_report_manage);
        imageBean.setName("会员卡员工销售报表");
        imageBean.setType("4");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_ptempl_sale_report_manage);
        imageBean.setName("拼团员工销售报表");
        imageBean.setType("5");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_goodsemp_sale_report_manage);
        imageBean.setName("商品员工销售报表");
        imageBean.setType("6");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_noemp_sale_report_manage);
        imageBean.setName("非员工销售报表");
        imageBean.setType("7");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_trade_drawing_manage);
        imageBean.setName("交易提款");
        imageBean.setType("8");
        imgeList.add(imageBean);*/
        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setName("餐企管理");
        moduleBean.setId("8");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_member_manage);
        imageBean.setName("员工管理");
        imageBean.setType("1");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_catering_manage);
        imageBean.setName("餐企信息管理");
        imageBean.setType("2");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_home_account);
        imageBean.setName("账号管理");
        imageBean.setType("3");
        imgeList.add(imageBean);
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_cate_qrc_manage);
        imageBean.setName("拉新二维码推送消息");
        imageBean.setType("4");
        imgeList.add(imageBean);

        moduleBean.setData(imgeList);
        moduleBeanList.add(moduleBean);

        moduleBean = new ModuleBean();
        moduleBean.setName("合同与缴费管理");
        moduleBean.setId("9");
        imgeList = new ArrayList<>();
        imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_more_contract);
        imageBean.setName("合同查询");
        imageBean.setType("1");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);
       /* imageBean = new CommonImageBean();
        imageBean.setDrawable(R.drawable.ic_pay_fee_manage);
        imageBean.setName("缴费管理");
        imageBean.setType("2");
        imgeList.add(imageBean);
        moduleBean.setData(imgeList);*/
        moduleBeanList.add(moduleBean);

        adapter = new ModuleListViewAdapter(this,moduleBeanList);
        listView.setAdapter(adapter);

    }




    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.startActivity(MainActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}
