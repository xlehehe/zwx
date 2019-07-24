package com.zwx.scan.app.feature.campaign;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.expandablelayout.ExpandableLayout;
import com.zwx.library.expandablelayout.ExpandableLinearLayout;
import com.zwx.library.expandablelayout.ExpandableUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.adapter.CommonRecyclerViewHolder;

import java.util.List;

public abstract class CouponRecyclerViewAdapter<T> extends RecyclerView.Adapter<CouponRecyclerViewAdapter.ViewHolder> {

    protected   List<T> data;
    protected Context context;
    protected SparseBooleanArray expandState = new SparseBooleanArray();
    protected LayoutInflater layoutInflater;

    protected int layoutId;
    CommonRecyclerViewHolder.OnItemCommonClickListener commonClickListener;
    public CouponRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId  = layoutId;
        this.layoutInflater = LayoutInflater.from(context);
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(getLayoutId(), parent, false));
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       /* final ItemModel item = data.get(position);
        holder.setIsRecyclable(false);
//        holder.textView.setText(item.rule);
        holder.tvCouponName.setText(item.getName());
        holder.tvCouponMoney.setText(item.getMoney());
        holder.tvExpreCouponName.setText(item.getCouponName());
        holder.llCoupon.setBackground(ContextCompat.getDrawable(context,item.getDrawableId()));
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, item.getColorId1()));
        if("yes".equals(item.getIsNew())){
            holder.btnUse.setBackground(ContextCompat.getDrawable(context,R.drawable.style_home_btn_oranage_bg));
        }else {
            holder.btnUse.setBackground(ContextCompat.getDrawable(context,R.drawable.style_home_btn_red_bg));
        }
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, item.getColorId1()));
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });*/
        bindData(holder, data.get(position),position);
    }

    protected void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public abstract void bindData(ViewHolder holder, T data,int position);

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView tvExpreCouponName;
        public ImageView buttonLayout;
        public TextView tvCouponName;
        public TextView tvCouponMoney;

        public ImageView ivCoupon;

        public Button  btnUse;
        
        public ImageView ivUse;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;
        private OnItemCommonClickListener commonClickListener;

        public ViewHolder(View view) {
            super(view);
            tvExpreCouponName = (TextView) view.findViewById(R.id.tv_expre_coupon_name);
            tvCouponName = (TextView) view.findViewById(R.id.tv_coupon_name);
            tvCouponMoney = (TextView) view.findViewById(R.id.tv_expre_money);
            btnUse = (Button) view.findViewById(R.id.btn_use);
            ivCoupon = (ImageView) view.findViewById(R.id.iv_avatar);
            buttonLayout = (ImageView) view.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout);

            ivUse = (ImageView)view.findViewById(R.id.iv_use);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public interface OnItemCommonClickListener {

            void onItemClickListener(int position);

            void onItemLongClickListener(int position);

        }

        public void setCommonClickListener(OnItemCommonClickListener commonClickListener) {
            this.commonClickListener = commonClickListener;
        }

        @Override
        public void onClick(View v) {
            if (commonClickListener != null) {
                commonClickListener.onItemClickListener(getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View v) {

            if (commonClickListener != null) {
                commonClickListener.onItemLongClickListener(getAdapterPosition());
            }
            return false;
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(ExpandableUtils.createInterpolator(ExpandableUtils.LINEAR_INTERPOLATOR));
        return animator;
    }


    protected abstract int getLayoutId() ;

}