package com.zwx.scan.app.feature.shop;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zwx.library.utils.RegexUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.http.HttpUrls;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/04/30
 * desc   :
 * version: 1.0
 **/
public class ProductSetNewAdapter extends BaseAdapter {

    private List<ProductSetNew> productSetNewList = new ArrayList<ProductSetNew>();
    private LayoutInflater inflater;
    private Context context;
    public ProductSetNewAdapter(Context context, List<ProductSetNew> productSetNewList) {
        this.productSetNewList = productSetNewList;
        this.context = context;
    }

  /*  public void setDatas(List datas) {
        data.addAll(datas);
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return productSetNewList.size();
    }

    @Override
    public Object getItem(int position) {
        return productSetNewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 1;
    }
    @Override
    public int getItemViewType(int position) {
        ProductSetNew good = productSetNewList.get(position);
        // state 状态 未上架——n 已下架——o 销售中——s 不再出售——c
        String state = good.getState();
        if("n".equals(state)){
            return   3 ;
        }else {
            return 0;
        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_manage_list, parent, false);
            holder = new ViewHolder();

            holder.tv_good_name = (TextView) convertView.findViewById(R.id.tv_good_name);
            holder.tv_cate_name = (TextView) convertView.findViewById(R.id.tv_cate_name);
            holder. tv_good_num = (TextView) convertView.findViewById(R.id.tv_good_num);
            holder.tv_sale_price = (TextView) convertView.findViewById(R.id.tv_sale_price);
            holder.tv_sale_num = (TextView) convertView.findViewById(R.id.tv_sale_num);

            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);
            holder.iv_status = (ImageView) convertView.findViewById(R.id.iv_status);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.iv_hot = (ImageView) convertView.findViewById(R.id.iv_hot);

            holder.ll_sj = (LinearLayout) convertView.findViewById(R.id.ll_sj);
            holder.ll_xj = (LinearLayout) convertView.findViewById(R.id.ll_xj);
            holder.ll_tzkc = (LinearLayout) convertView.findViewById(R.id.ll_tzkc);
            holder.ll_rm = (LinearLayout) convertView.findViewById(R.id.ll_rm);

            holder.tv_sj = (TextView) convertView.findViewById(R.id.tv_sj);
            holder.tv_xj = (TextView) convertView.findViewById(R.id.tv_xj);
            holder.tv_tzkc = (TextView) convertView.findViewById(R.id.tv_tzkc);
            holder.tv_rm = (TextView) convertView.findViewById(R.id.tv_rm);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductSetNew good  = productSetNewList.get(position);

        String thumbnailUrl = HttpUrls.IMAGE_ULR + good.getThumbnailUrl();

        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(context).load(thumbnailUrl).apply(requestOptions).into(holder.iv_img);
        // Put asBitmap() right after Glide.with(context)  for glide 4.x

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
        String stateName = good.getStateName();

        String state = good.getState();

        if(stateName != null && !"".equals(stateName)){
            if("已下架".equals(stateName)){ //已下架
                holder.iv_status.setImageResource(R.drawable.ic_good_yxj);
                holder.ll_sj.setVisibility(View.VISIBLE);
                holder.ll_rm.setVisibility(View.GONE);
                holder.ll_xj.setVisibility(View.GONE);
                holder.ll_tzkc.setVisibility(View.VISIBLE);
            }else if("在售".equals(stateName)){  //在售
                holder.iv_status.setImageResource(R.drawable.ic_good_zx);
                holder.ll_sj.setVisibility(View.GONE);
                holder.ll_rm.setVisibility(View.VISIBLE);
                holder.ll_xj.setVisibility(View.VISIBLE);
                holder.ll_tzkc.setVisibility(View.VISIBLE);


            }else if("停售".equals(stateName)){  //停售售
                holder.iv_status.setImageResource(R.drawable.ic_stop_sale);
                holder.ll_sj.setVisibility(View.GONE);
                holder.ll_rm.setVisibility(View.VISIBLE);
                holder.ll_xj.setVisibility(View.VISIBLE);
                holder.ll_tzkc.setVisibility(View.VISIBLE);


            }else if("预售".equals(stateName)){  //在售
                holder.iv_status.setImageResource(R.drawable.ic_pre_sale);
                holder.ll_sj.setVisibility(View.GONE);
                holder.ll_rm.setVisibility(View.VISIBLE);
                holder.ll_xj.setVisibility(View.VISIBLE);
                holder.ll_tzkc.setVisibility(View.VISIBLE);


            }else if("不在出售".equals(stateName)){  // 不在出售
                holder.ll_sj.setVisibility(View.GONE);
                holder.ll_rm.setVisibility(View.GONE);
                holder.ll_xj.setVisibility(View.GONE);
                holder.ll_tzkc.setVisibility(View.GONE);


            }else if("暂存".equals(stateName)){ //未上架
                holder.iv_status.setImageResource(R.drawable.ic_good_zc);
                holder.ll_sj.setVisibility(View.GONE);
                holder.ll_rm.setVisibility(View.GONE);
                holder.ll_xj.setVisibility(View.GONE);
                holder.ll_tzkc.setVisibility(View.GONE);

            }
        }

        /*if("o".equals(state)){ //已下架
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

        }*/
        //上架
        holder.ll_sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(productSetNewList.get(position).getProductId()),"push",""));
            }
        });
        //下架
        holder.ll_xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(productSetNewList.get(position).getProductId()),"stop",""));
            }
        });
        //热卖 setHot/ 取消热卖cancleHot
        holder.ll_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("r".equals(productSetNewList.get(position).getMarketingType())){ //取消热卖  则可以设置成为热卖
                    EventBus.getDefault().post(new ProductEventBus(String.valueOf(productSetNewList.get(position).getProductId()),"setHot",""));

                }else if("p".equals(productSetNewList.get(position).getMarketingType())){ //热卖则可以设置成为取消热卖
                    EventBus.getDefault().post(new ProductEventBus(String.valueOf(productSetNewList.get(position).getProductId()),"cancleHot",""));
                }



            }
        });

        //调整库存
        holder.ll_tzkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ProductEventBus(String.valueOf(productSetNewList.get(position).getProductId()),"adjust",String.valueOf(productSetNewList.get(position).getSurplusStock())));
            }
        });



        return convertView;
    }

    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的listview
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }

    }

    static class ViewHolder {
//        @BindView(R.id.tv_good_name)
        public TextView tv_good_name;
//        @BindView(R.id.tv_cate_name)
        public TextView tv_cate_name;

//        @BindView(R.id.iv_hot)
        public ImageView iv_hot;
//        @BindView(R.id.tv_good_num)
        public TextView tv_good_num;
//        @BindView(R.id.iv_img)
        public ImageView iv_img;
//        @BindView(R.id.tv_sale_price)
        public TextView tv_sale_price;
//        @BindView(R.id.tv_stock)
        public TextView tv_stock;
//        @BindView(R.id.iv_status)
        public ImageView iv_status;
//        @BindView(R.id.tv_sale_num)
        public TextView tv_sale_num;
//        @BindView(R.id.ll_sj)
        public LinearLayout ll_sj;
//        @BindView(R.id.tv_sj)
        public TextView tv_sj;
//        @BindView(R.id.ll_xj)
        public LinearLayout ll_xj;
//        @BindView(R.id.tv_xj)
        public TextView tv_xj;
//        @BindView(R.id.ll_tzkc)
        public LinearLayout ll_tzkc;
//        @BindView(R.id.tv_tzkc)
        public TextView tv_tzkc;
//        @BindView(R.id.ll_rm)
        public LinearLayout ll_rm;
//        @BindView(R.id.tv_rm)
        public TextView tv_rm;




//         ViewHolder(View view) {
//            ButterKnife.bind(this,view);
//        }


    }
}
