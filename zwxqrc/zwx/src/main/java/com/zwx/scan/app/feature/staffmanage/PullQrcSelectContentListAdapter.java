package com.zwx.scan.app.feature.staffmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CompMemberType;
import com.zwx.scan.app.data.bean.StaffWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * author : lizhilong
 * time   : 2019/03/27
 * desc   :
 * version: 1.0
 **/
public class PullQrcSelectContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String,Object>> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private PullQrcSelectContentListAdapter.OnItemClickListener mClickListener;

    public PullQrcSelectContentListAdapter(Context context, List data) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(data.size() == 0){
            return;
        }
        ChildViewHolder holder = (ChildViewHolder) viewHolder;
        Map<String,Object> map = ( Map<String,Object> ) data.get(position);

        String name = "";

        String isChecked = "false";
        if(map != null){
            isChecked = (String) map.get("isChecked");
        }

        PullQrcSelectContentActivity activity = (PullQrcSelectContentActivity)context;
      /*  if("3".equals(activity.pushType)){
            name = (String)map.get("memtypeName");
        }else {
            name = (String)map.get("campaignName");
        }*/

        name = (String)map.get("name");
        if(name != null && !"".equals(name)){

        }
        if ("true".equals(isChecked)) {
            holder.cb_checkbox.setChecked(true);
            holder.cb_checkbox.setButtonDrawable(R.drawable.ic_correct);
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.font_color_blue));
        } else {
            holder.cb_checkbox.setChecked(false);
            holder.cb_checkbox.setButtonDrawable(R.color.white);
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.shop_font_color_gray));
        }

        holder.tv_title.setText(name);



    }
    public void setOnItemClickListener(PullQrcSelectContentListAdapter.OnItemClickListener listener) {
        this.mClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_push_content_list, null);
        return new ChildViewHolder(view,mClickListener);
    }
    interface OnItemClickListener {
        public void onItemClick(View view, int postion);
    }

    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.cb_checkbox)
        CheckBox cb_checkbox;

        private PullQrcSelectContentListAdapter.OnItemClickListener mListener;// 声明自定义的接口

        public ChildViewHolder(View view, PullQrcSelectContentListAdapter.OnItemClickListener listener) {
            super(view);

            mListener = listener;
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            cb_checkbox = (CheckBox) view.findViewById(R.id.cb_checkbox);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getPosition());
        }
    }
}




