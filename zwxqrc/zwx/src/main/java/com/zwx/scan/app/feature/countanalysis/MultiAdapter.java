package com.zwx.scan.app.feature.countanalysis;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.CampaignChartBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描    述：
 * 创建日期：2018/12/9
 * 作    者：lizhilong
 * 邮    箱：
 * 备    注：
 */
public class MultiAdapter extends RecyclerView.Adapter  {

    private List<CampaignChartBean> datas = new ArrayList<>();
    public static HashMap<Integer, Boolean> isSelected;

    private     List<Integer> colors;
    private  Context context;

    public MultiAdapter(Context context,List<CampaignChartBean> datas,List<Integer> colors) {
        this.datas = datas;
        this.context = context;
        this.colors = colors;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        if(datas!=null&&datas.size()>0){
            for (int i = 0; i < datas.size(); i++) {
                CampaignChartBean chartBean = datas.get(i);
                isSelected.put(i, true);
            }
        }
      /*  colors= Arrays.asList(Color.BLUE,
                R.color.orange,
                Color.RED,
                Color.GREEN,
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );*/

    }


    public interface OnMultiItemClickListener {
        public void onItemClick(View view, int postion);
    }
    private OnMultiItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnMultiItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new MultiViewHolder(view,mOnItemClickLitener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MultiViewHolder){
                final MultiViewHolder viewHolder = (MultiViewHolder) holder;
                if(datas !=null && datas.size()>0){
                    CampaignChartBean chartBean = datas.get(position);
                    chartBean.setChecked(isSelected.get(position));
//                    viewHolder.mTvName.setText(chartBean.getStoreName());
                    viewHolder.mCheckBox.setText(chartBean.getStoreName());
                    //为复选框设置颜色

                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                        viewHolder.mCheckBox.setButtonTintList(ColorStateList.valueOf(colors.get(position)));
                        viewHolder.mCheckBox.setTextColor(ColorStateList.valueOf(colors.get(position)));
                    }


                    viewHolder.mCheckBox.setChecked(isSelected.get(position));
                    viewHolder.itemView.setSelected(isSelected.get(position));
                    if (mOnItemClickLitener != null)
                    {
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                            }
                        });
                    }
                }

            }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView mTvName;
        CheckBox mCheckBox;

        private OnMultiItemClickListener mListener;// 声明自定义的接口

        public MultiViewHolder(View itemView,OnMultiItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
//            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mListener.onItemClick(v, getPosition());
        }
    }




}
