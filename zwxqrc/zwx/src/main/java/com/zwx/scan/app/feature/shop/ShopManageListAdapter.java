package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.campaign.LhGeEventBus;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.fuseable.HasUpstreamObservableSource;

/**
 * author : lizhilong
 * time   : 2019/04/19
 * desc   :  商城管理列表
 * version: 1.0
 **/
public class ShopManageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ProductSetNew> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public ShopManageListAdapter(Context context, List<ProductSetNew> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if(data.size() == 0){
            return 0;
        }else {
            return data.size();
        }

    }


    @Override
    public int getItemViewType(int position) {

        ProductSetNew good = data.get(position);

       // state 状态 未上架——n 已下架——o 销售中——s 不再出售——c
        String state = good.getState();

        if("n".equals(state)){
            return   0 ;
        }

        return position;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }


        ChildViewHolder holder = (ChildViewHolder) viewHolder;

        int viewType =getItemViewType(position);


        ProductSetNew good  = data.get(position);

        String thumbnailUrl = HttpUrls.IMAGE_ULR + good.getThumbnailUrl();

        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(context).load(thumbnailUrl).apply(requestOptions).into(holder.iv_img);

        String catName = good.getCatName();
        holder.tv_cate_name.setText(catName != null && !"".equals(catName)?catName:"-");
        String name = good.getProductName();
        String code = good.getProductCode();
        String marketingType = good.getMarketingType();
        //售价
        BigDecimal unitPrice = good.getUnitPrice();

        //库存
        Long surplusStock = good.getSurplusStock();
        String stoke = "-";
        if(surplusStock != null && surplusStock.intValue()>0){
            stoke = String.valueOf(surplusStock.intValue());
        }


        holder.tv_stock.setText(stoke);

//        Long groundingCount = good.getGroundingCount();
        Integer cou = good.getCount();
        String count = "-";
        if(cou != null && cou.intValue()>0){
            count = String.valueOf(cou.intValue());
        }
        holder.tv_sale_num.setText(count);
        holder.tv_good_name.setText(name!=null?name:"-");
        holder.tv_good_num.setText(code!=null?code:"-");


        if(unitPrice != null && unitPrice.doubleValue()>0){
            holder.tv_sale_price.setText(RegexUtils.getDoubleString(unitPrice.doubleValue()));
        }else {
            holder.tv_sale_price.setText("-");
        }
        final String operateRmFlag = "";
        if("r".equals(marketingType)){   //取消热卖
            holder.iv_hot.setVisibility(View.GONE);
            holder.tv_rm.setCompoundDrawablesWithIntrinsicBounds(null,context.getResources().getDrawable(R.drawable.ic_shop_manage_sz),null,null);
            holder.tv_rm.setText("设为热卖");
        }else if("p".equals(marketingType)){  //设为热卖
            holder.iv_hot.setVisibility(View.VISIBLE);
            holder.tv_rm.setText("取消热卖");
            holder.tv_rm.setCompoundDrawablesWithIntrinsicBounds(null,context.getResources().getDrawable(R.drawable.ic_shop_manage_cancel_rm),null,null);
        }


        String state = good.getState();
        if("o".equals(state)){ //已下架
            holder.iv_status.setImageResource(R.drawable.ic_good_yxj);
            holder.ll_sj.setVisibility(View.VISIBLE);
            holder.ll_rm.setVisibility(View.GONE);
            holder.ll_xj.setVisibility(View.GONE);
            holder.ll_tzkc.setVisibility(View.VISIBLE);
        }else if("s".equals(state)){  //在售
            holder.iv_status.setImageResource(R.drawable.ic_good_zx);
            holder.ll_sj.setVisibility(View.GONE);
            holder.ll_rm.setVisibility(View.VISIBLE);
            holder.ll_xj.setVisibility(View.VISIBLE);
            holder.ll_tzkc.setVisibility(View.VISIBLE);
        }else if("c".equals(state)){  // 不在出售
            holder.ll_sj.setVisibility(View.GONE);
            holder.ll_rm.setVisibility(View.GONE);
            holder.ll_xj.setVisibility(View.GONE);
            holder.ll_tzkc.setVisibility(View.GONE);


        }else if("n".equals(state)){ //未上架
            holder.iv_status.setImageResource(R.drawable.ic_good_zc);
            holder.ll_sj.setVisibility(View.GONE);
            holder.ll_rm.setVisibility(View.GONE);
            holder.ll_xj.setVisibility(View.GONE);
            holder.ll_tzkc.setVisibility(View.GONE);

        }
        //上架
        holder.ll_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(data.get(position).getProductId()),"push",""));
            }
        });
        //下架
        holder.ll_xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(data.get(position).getProductId()),"stop",""));
            }
        });
        //热卖 setHot/ 取消热卖cancleHot
        holder.ll_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("r".equals(data.get(position).getMarketingType())){ //取消热卖  则可以设置成为热卖
                    EventBus.getDefault().post(new ProductEventBus(String.valueOf(data.get(position).getProductId()),"setHot",""));

                }else if("p".equals(data.get(position).getMarketingType())){ //热卖则可以设置成为取消热卖
                    EventBus.getDefault().post(new ProductEventBus(String.valueOf(data.get(position).getProductId()),"cancleHot",""));
                }



            }
        });

        //调整库存
        holder.ll_tzkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(data.get(position).getProductId()),"adjust",String.valueOf(data.get(position).getSurplusStock())));
            }
        });

       /* if("zf".equals(type)){
            campaignTypeName = "转发活动";
        }*/
//        holder.tvType.setText(campaignTypeName);




      /*  String logo = campaign.getWechatIcon();
        if(poster != null){
            brandLogo = HttpUrls.IMAGE_ULR + poster.getWechatIcon();

        }else {
            brandLogo = HttpUrls.IMAGE_ULR ;
        }
        Glide.with(context).load(brandLogo).apply(requestOptions).into(holder.ivHead);

*/


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_shop_manage_list, viewHolder,false);
        return new ChildViewHolder(view);
    }
    private class ChildViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_good_name;
        public TextView tv_cate_name;
        public ImageView iv_hot;

        public TextView tv_good_num;
        public ImageView iv_img;

        public TextView tv_sale_price;
        public TextView tv_stock;
        public ImageView iv_status;
        public TextView tv_sale_num;

        public LinearLayout ll_sj;
        public TextView tv_sj;
        public LinearLayout ll_xj;
        public TextView tv_xj;
        public LinearLayout ll_tzkc;
        public TextView tv_tzkc;
        public LinearLayout ll_rm;
        public TextView tv_rm;
        public ChildViewHolder(View view) {
            super(view);
            tv_good_name = (TextView) view.findViewById(R.id.tv_good_name);
            tv_cate_name = (TextView) view.findViewById(R.id.tv_cate_name);
            tv_good_num = (TextView) view.findViewById(R.id.tv_good_num);
            tv_sale_price = (TextView) view.findViewById(R.id.tv_sale_price);
            tv_sale_num = (TextView) view.findViewById(R.id.tv_sale_num);

            tv_stock = (TextView) view.findViewById(R.id.tv_stock);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            iv_img = (ImageView) view.findViewById(R.id.iv_img);
            iv_hot = (ImageView) view.findViewById(R.id.iv_hot);

            ll_sj = (LinearLayout) view.findViewById(R.id.ll_sj);
            ll_xj = (LinearLayout) view.findViewById(R.id.ll_xj);
            ll_tzkc = (LinearLayout) view.findViewById(R.id.ll_tzkc);
            ll_rm = (LinearLayout) view.findViewById(R.id.ll_rm);


            tv_sj = (TextView) view.findViewById(R.id.tv_sj);
            tv_xj = (TextView) view.findViewById(R.id.tv_xj);
            tv_tzkc = (TextView) view.findViewById(R.id.tv_tzkc);
            tv_rm = (TextView) view.findViewById(R.id.tv_rm);

        }

    }

}
