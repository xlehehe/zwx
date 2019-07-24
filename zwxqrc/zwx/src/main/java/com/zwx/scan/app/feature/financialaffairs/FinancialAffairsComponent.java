package com.zwx.scan.app.feature.financialaffairs;

import com.zwx.scan.app.data.bean.Member;
import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * author : lizhilong
 * time   : 2019/05/09
 * desc   :  财务中心 依赖注入
 * version: 1.0
 **/
@ActivtiyScoped
@Component(modules = FinancialAffairsModule.class,dependencies = ApplicationComponent.class)
public interface FinancialAffairsComponent {


    void inject(TradeDrawingActivity activity);
    //交易提款
    void inject(TradeDrawingCollectionFlowListActivity activity);

   //列表详情
    void inject(TradeDrawingCollectionFlowDetailActivity activity);

    //交易提款记录
    void inject(TradeDrawingRecordActivity activity);

    //交易提款
    void inject(FinancialDrawingActivity activity);

    //提款详情
    void inject(FinancialDrawingDetailActivity activity);

    //代收款流水列表
    void inject(CollectionFlowListActivity activity);

    //收款账号设置
    void inject(CollectionMoneyAccountSettingActivity activity);

    //收款账号设置- 微信 生效中页面
    void inject(CollectionWxNoEffectiveActivity activity);

    //收款账号设置- 银行卡 生效中页面
    void inject(CollectionBankNoEffectiveActivity activity);

    //银行卡名称选项
    void inject(CollectionSelectBankNameListActivity activity);

   //预计明天到账订单
   void inject(CollectionExpectToArriveAmtFragment fragment);

    //未到账订单
    void inject(CollectionNoToArriveAmtFragment fragment);

    //到账订单详情
    void inject(CollectionToAccountOrderDetailActivity activity);
    //转账记录
    void inject(CollectionTransferRecordActivity activity);
    void inject(CollectionMoneyIntoAccountListActivity activity);

    void inject(CollectionTransferRecordDetailActivity activity);
   //会员卡员工销售报表
    void inject(MemCardEmploeeSaleReportActivity activity);
    //会员卡员工销售报表详情
    void inject(MemCardEmploeeSaleReportDetailActivity activity);

    //会员卡员工销售报表详情列表具体详情
    void inject(MemCardEmploeeSaleReportDetailListActivity activity);
/*    void inject(CollectionExpectToArriveAmtFragment fragment);
    void inject(ShopTelFragment fragment);



    void inject(ShopManageActivity activity);

    void inject(ShopManageNewActivity activity);


    //红包
    //列表 包含金额展示
    void inject(RedEnvelopeActivity activity);

    void inject(RedEnvelopeDetailActivity activity);

    void inject(SearchRedEnvelopeListActivity activity);
    void inject(ProductVerificationRecordActivity activity);
    void inject(ProductVerificationRecordDetailActivity activity);*/
}
