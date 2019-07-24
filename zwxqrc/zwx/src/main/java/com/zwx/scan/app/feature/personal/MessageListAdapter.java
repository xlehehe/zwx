package com.zwx.scan.app.feature.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.utils.TimeUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.Message;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/07
 * desc   :
 * version: 1.0
 **/
public class MessageListAdapter extends RecyclerView.Adapter {
    private List<MessageSet> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public MessageListAdapter(Context context, List<MessageSet> data) {
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


        MessageSet message = data.get(position);

        String title = message.getTitle();
        String summary = message.getSummary();
        String date = message.getShowExplain();
        String  isNewIcon = message.getIsNewIcon();

        String status = message.getStatus();
        holder.tvTitle.setText(title!=null?title:"");

        holder.tvDate.setText(date!=null?date:"");

        holder.tvContent.setText(summary!=null?summary:"");
        if("1".equals(isNewIcon)){
            holder.ivMsg.setVisibility(View.VISIBLE);
        }else if ("0".equals(isNewIcon)){
            holder.ivMsg.setVisibility(View.GONE);
        }

        if("R".equals(status)){
            holder.ivMsgR.setVisibility(View.GONE);
        }else if ("U".equals(isNewIcon)){
            holder.ivMsgR.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_message_list, viewHolder,false);
        return new ChildViewHolder(view);
    }

}

class ChildViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public ImageView ivMsg;
    public ImageView ivMsgR;
    public TextView tvDate;
    public TextView tvContent;
    public ChildViewHolder(View view) {
        super(view);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivMsg = (ImageView) view.findViewById(R.id.iv_msg);

        ivMsgR = (ImageView) view.findViewById(R.id.iv_msg_r);
        tvDate = (TextView) view.findViewById(R.id.tv_date);

        tvContent = (TextView) view.findViewById(R.id.tv_content);

    }

}
