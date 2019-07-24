package com.zwx.scan.app.feature.member;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zwx.library.circleimageview.CircleImageView;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecycleAdapter;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;
import com.zwx.scan.app.data.bean.MemberConsumeBean;
import com.zwx.scan.app.data.bean.MemberInfoDetailBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.countanalysis.CountAnalysis;
import com.zwx.scan.app.feature.countanalysis.CountAnalysisHomeActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemberInfoListDetailAdapter extends CommonRecycleAdapter<MemberInfoDetailBean.MemberInfoDetail> {

    private CountAnalysisHomeActivity activity;
    private CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
    private  Context context;
    public MemberInfoListDetailAdapter(Context context, List<MemberInfoDetailBean.MemberInfoDetail> dataList) {
        super(context, dataList, R.layout.item_member_type_info_list);
        this.context = context;
    }


  /*  public MemberInfoListDetailAdapter(Context context, List<MemberInfoDetailBean.MemberInfoDetail> dataList, CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener) {
        super(context, dataList, R.layout.item_member_type_info_list);
        this.commonClickListener = commonClickListener;
    }*/
    @Override
    public void bindData(CommonRecyclerViewHolder holder, MemberInfoDetailBean.MemberInfoDetail data) {

        String amount = data.getCompEatTotalAmt();
        String amt = "";
        if(amount != null && !"".equals(amount)){
            if("0".equals(amount)){
                amt = "-";
            }else {
                amt  = amount+"元";
            }
        }
        String qnt = "-";

        if( data.getCompEatTotalQnt()!= null && data.getCompEatTotalQnt().doubleValue()>0){
            qnt = String.valueOf(data.getCompEatTotalQnt().intValue());
        }else {
            qnt = "-";
        }
        //TimeUtils.date2String(TimeUtils.string2Date(data.getJoinSysTime(),new SimpleDateFormat("yyyy年mm月dd日")))

        String time = data.getJoinSysTime();

        if(time != null && !"".equals(time)){
            time = data.getJoinSysTime().replace("-",".");
        }else {
            time  = "-";
        }
        String whatType = data.getWhatType();
        String campaignName = "";
        if(whatType!=null && !"".equals(whatType)){
        }else {
            whatType = "-";
        }
        String displayRule = data.getDisplayRule().trim();  //0 表示不显示， 1 表示显示





        String brandLogo = "";

        String colour = data.getColour();



        LinearLayout llImage = holder.getView(R.id.ll_image);
        String bgPath = HttpUrls.IMAGE_ULR + data.getBackground();

        Glide.with(context).load(bgPath).into(new SimpleTarget<Drawable>(200,200) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    llImage.setBackground(resource);
                }
            }
        });

        String joinStoreName = "-";
        String joinTime = data.getJoinSysTime();

        String memberTypeName = data.getMemTypeName();
        String joinCondition = data.getJoinCondition();
        String quitTime = "";
        if(joinCondition != null && !"".equals(joinCondition)){

        }else {
            joinCondition = "";
        }

        CircleImageView circleImageView = holder.getView(R.id.circle_view);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);
        String condition = "";
        quitTime = data.getQuitTime();
        joinStoreName = data.getJoinStoreName();
        if(joinStoreName != null && !"".equals(joinStoreName)){
        }else {
            joinStoreName = "-";
        }
        String dateTime = "";
        if(quitTime != null && !"".equals(quitTime)){
            if(joinTime != null && !"".equals(joinTime)){
                dateTime= TimeUtils.date2String(TimeUtils.string2Date(joinTime)).replace("-",".") +"-"+ TimeUtils.date2String(TimeUtils.string2Date(quitTime)).replace("-",".");
            }
        }else {
            dateTime = "";
        }
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        Glide.with(context).load(brandLogo).apply(requestOptions).into(circleImageView);
        TextView tv_card_name = holder.getView(R.id.tv_card_name);
        if(memberTypeName != null && !"".equals(memberTypeName)){

            if(colour != null && !"".equals(colour)){
                colour = data.getColour();
                tv_card_name.setText(Html.fromHtml("<font  color = \'#"+colour+"\' >"+memberTypeName+"</font>"));
            }else {
                tv_card_name.setText(memberTypeName);
            }
        } else {
            tv_card_name.setText("");
        }

        if(displayRule != null && !"".equals(displayRule)){
            String[] rules = displayRule.split("");
            for (int i = 0;i<rules.length;i++){

                if(i == 1){
                    String displayRule1 = rules[i];
                    if("0".equals(displayRule1)){
                        circleImageView.setVisibility(View.INVISIBLE);

                    }else if("1".equals(displayRule1)){
                        circleImageView.setVisibility(View.VISIBLE);

                    }

                }else if(i == 2){
                    String displayRule1 = rules[i];
                    if("0".equals(displayRule1)){
                        memberTypeName = "";
                        tv_card_name.setVisibility(View.INVISIBLE);
                    }else if("1".equals(displayRule1)){
                        tv_card_name.setVisibility(View.VISIBLE);
                    }
                }else if(i == 3){
                    condition = "";
                }else if(i == 4){
                    String displayRule1 = rules[i];
                    if("0".equals(displayRule1)){
                        dateTime = "";
                    }else if("1".equals(displayRule1)){

                    }
                }
            }
        }else {

        }


        TextView tv_condition = holder.getView(R.id.tv_condition);
        if("0".equals(joinCondition) ){
            condition = "直接领取";
        }else if("1".equals(joinCondition) ){
            condition = "在线购买";
        }
        //购买方式直接隐藏
        tv_condition.setVisibility(View.INVISIBLE);

        holder.setText(R.id.tv_count, qnt)
                .setText(R.id.tv_amount, amt)
                .setText(R.id.tv_from_store, joinStoreName)
                .setText(R.id.tv_from_way,whatType)
                .setText(R.id.tv_join_time,time)
//                .setText(R.id.tv_card_name,memberTypeName)
                .setText(R.id.tv_time,dateTime)
                .setText(R.id.tv_condition,condition)
                .setCommonClickListener(commonClickListener);




    }
}
