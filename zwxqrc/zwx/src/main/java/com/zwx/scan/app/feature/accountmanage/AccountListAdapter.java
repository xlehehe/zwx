package com.zwx.scan.app.feature.accountmanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/10
 * desc   :
 * version: 1.0
 **/
public class AccountListAdapter extends RecyclerView.Adapter {

    private List<User> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public AccountListAdapter(Context context, List<User> data) {
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


        User user = data.get(position);

        String roleName = user.getRoleNames();
        String name = user.getNickname();
        String storeName = user.getStoreNames();
        String account =user.getUsername();
        holder.tvName.setText(name!=null?name:"");
        holder.tvRoleName.setText(roleName!=null?roleName:"");
        holder.tvAccount.setText(account!=null?account:"");
        holder.tvStoreName.setText(storeName!=null?storeName:"");



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.item_account_manage_list, viewHolder,false);
        return new ChildViewHolder(view);
    }

}

class ChildViewHolder extends RecyclerView.ViewHolder {
    public TextView tvAccount;
    public TextView tvName;
    public TextView tvStoreName;
    public TextView tvRoleName;
    public ChildViewHolder(View view) {
        super(view);
        tvAccount = (TextView) view.findViewById(R.id.tv_account);
        tvName = (TextView) view.findViewById(R.id.tv_nickname);
        tvStoreName = (TextView) view.findViewById(R.id.tv_store_name);
        tvRoleName = (TextView) view.findViewById(R.id.tv_role_name);

    }
    
}
