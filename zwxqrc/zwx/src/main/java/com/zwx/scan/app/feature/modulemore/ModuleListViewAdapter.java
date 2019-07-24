package com.zwx.scan.app.feature.modulemore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CommonImageBean;
import com.zwx.scan.app.feature.accountmanage.AccountActivity;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.cateringinfomanage.CateringinfoManageActivity;
import com.zwx.scan.app.feature.contractmanage.ContractActivity;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;
import com.zwx.scan.app.feature.couponmanage.CouponManageActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponManageActivity;
import com.zwx.scan.app.feature.couponmanage.GiveCouponNewActivity;
import com.zwx.scan.app.feature.financialaffairs.CollectionMoneyAccountSettingActivity;
import com.zwx.scan.app.feature.financialaffairs.CollectionMoneyIntoAccountListActivity;
import com.zwx.scan.app.feature.financialaffairs.MemCardEmploeeSaleReportActivity;
import com.zwx.scan.app.feature.financialaffairs.TradeDrawingActivity;
import com.zwx.scan.app.feature.member.CommonConstantBean;
import com.zwx.scan.app.feature.member.MemberCardManageActivity;
import com.zwx.scan.app.feature.member.MemberCardStreamActivity;
import com.zwx.scan.app.feature.member.MemberConsumeListActivity;
import com.zwx.scan.app.feature.member.MemberInfoListActivity;
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
import com.zwx.scan.app.utils.RxPermissions;
import com.zwx.scan.app.widget.MyGridView;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   :  功能模块列表
 * version: 1.0
 **/
public class ModuleListViewAdapter extends BaseAdapter {


    private Context mContext;
    private List<ModuleBean> list;
    private LayoutInflater layinf;
    //GridView加载不同布局
//    public List<CommonImageBean> commonImagList = null;
    RxPermissions rxPermissions = null;
    Intent intent = null;
    public ModuleListViewAdapter(Context context, List<ModuleBean> lists){
        this.mContext = context;
        this.list = lists;
        layinf = LayoutInflater.from(context);
        rxPermissions = new RxPermissions((ModuleMoreListActivity)context);
        intent =new Intent();
    }

    @Override
    public int getCount() {
        return list.size();
    }

 /*   @Override
    public int getItemViewType(int position) {
        //根据position返回指定的布局类型，比如0、1，根据这个返回值加载不同布局
        return lists.get(position).getPropertyType();
    }

    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 2;
    }*/

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null){
            convertView = layinf.inflate(R.layout.item_module_more_list, parent, false);

            //使用减少findView的次数
            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.gridView = (MyGridView) convertView.findViewById(R.id.grid_view);

            //设置标记
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModuleBean moduleBean = list.get(position);

        holder.tvName.setText(moduleBean.getName());
        List<CommonImageBean> commonImagList = moduleBean.data;

        if(commonImagList != null &&  commonImagList.size()>0){
            ModuleGridViewAdapter adapter = new ModuleGridViewAdapter(mContext,commonImagList);
            holder.gridView.setAdapter(adapter);

            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int positi, long id) {

                   String idStr = list.get(position).getId();
                    List<CommonImageBean> imageBeanList = list.get(position).getData();
                    CommonImageBean imageBean = imageBeanList.get(positi);
                     intent = new Intent();
                    if("1".equals(idStr)){
                        String type= imageBean.getType();
                        if("1".equals(type)){  //核销

                            rxPermissions
                                    .request(Manifest.permission.CAMERA)
                                    .subscribe(granted -> {
                                        if (granted) {
                                            intent.setClass(mContext, VerificationActivity.class);
                                            intent.putExtra("title","核销");
                                            ActivityUtils.startActivity(intent,
                                                    R.anim.slide_in_right, R.anim.slide_out_left);

                                        } else {
                                            ToastUtils.showShort(mContext.getResources().getText(R.string.expermission_camera));
                                            return;
                                        }
                                    });
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(VerificationRecordActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                            ActivityUtils.startActivity(CountAnalysisHomeActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("2".equals(idStr)){ //会员管理
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(MemberInfoListActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(MemberConsumeListActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                            ActivityUtils.startActivity(RedEnvelopeActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("3".equals(idStr)){ //会员卡管理
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(MemberCardManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(MemberCardStreamActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("4".equals(idStr)){ //优惠券和活动
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(WebViewActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(CouponManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                            ActivityUtils.startActivity(CampaignListActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("4".equals(type)){
                            ActivityUtils.startActivity(GiveCouponManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("5".equals(type)){
                            ActivityUtils.startActivity(PosterMaterListActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("5".equals(idStr)){ //拼团
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(PtManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(PtOrderActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("6".equals(idStr)){ //商城
                        String type= imageBean.getType();
                        if("1".equals(type)){


                            rxPermissions
                                    .request(Manifest.permission.CAMERA)
                                    .subscribe(granted -> {
                                        if (granted) {
                                            ActivityUtils.startActivity(ProductVerificationActivity.class,
                                                    R.anim.slide_in_right, R.anim.slide_out_left);

                                        } else {
                                            ToastUtils.showShort(mContext.getResources().getText(R.string.expermission_camera));
                                            return;
                                        }
                                    });
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(ProductVerificationRecordActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                            ActivityUtils.startActivity(ShopSettingActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("4".equals(type)){
                            ActivityUtils.startActivity(ShopManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("5".equals(type)){
                            ActivityUtils.startActivity(ShopOrderActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("7".equals(idStr)){ //财务中心
                        String type= imageBean.getType();
                        Class cla =null;
                        if("1".equals(type)){
                            cla = CollectionMoneyAccountSettingActivity.class;
                            ActivityUtils.startActivity(cla,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            cla = CollectionMoneyIntoAccountListActivity.class;
                            ActivityUtils.startActivity(cla,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                          /*  cla = CollectionMoneyAccountSettingActivity.class;
                            ActivityUtils.startActivity(cla,
                                    R.anim.slide_in_right, R.anim.slide_out_left);*/
                            ToastUtils.showShort("暂未实现");
                        }else if("4".equals(type)){
                            intent = new Intent(mContext,MemCardEmploeeSaleReportActivity.class);
                            intent.putExtra("orderType","M");  //订单类型 购卡M 拼团G  商城商品P
                            ActivityUtils.startActivity(intent,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("5".equals(type)){
                             intent = new Intent(mContext, MemCardEmploeeSaleReportActivity.class);
                            intent.putExtra("orderType","M");  //订单类型 购卡M 拼团G  商城商品P
                            ActivityUtils.startActivity(intent,
                                    R.anim.slide_in_right, R.anim.slide_out_left);

                        }else if("6".equals(type)){
                            intent = new Intent(mContext,MemCardEmploeeSaleReportActivity.class);
                            intent.putExtra("orderType","P");  //订单类型 购卡M 拼团G  商城商品P
                            ActivityUtils.startActivity(intent,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("7".equals(type)){
                            ToastUtils.showShort("暂未实现");

                        }else if("8".equals(type)){
                            cla = TradeDrawingActivity.class;
                            ActivityUtils.startActivity(cla,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }

                    }else if("8".equals(idStr)){
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(StaffListActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("2".equals(type)){
                            ActivityUtils.startActivity(CateringinfoManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("3".equals(type)){
                            ActivityUtils.startActivity(AccountActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }else if("4".equals(type)){
                            ActivityUtils.startActivity(PullQrcManageActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }else if("9".equals(idStr)){
                        String type= imageBean.getType();
                        if("1".equals(type)){
                            ActivityUtils.startActivity(ContractActivity.class,
                                    R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
                }
            });
        }

        notifyDataSetChanged();
        return convertView;
    }


    static class ViewHolder {
        TextView tvName;
        MyGridView gridView;
    }

}
