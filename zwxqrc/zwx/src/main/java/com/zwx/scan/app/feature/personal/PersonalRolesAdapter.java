package com.zwx.scan.app.feature.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/08
 * desc   :
 * version: 1.0
 **/
public class PersonalRolesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Role> mDatas = new ArrayList();

    public PersonalRolesAdapter(Context context,List<Role> stores) {
        mContext = context;
        mDatas = stores;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Role getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //int index = position;
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_role_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        int i = position+1;
        String label = "角色" +i;
        mViewHolder.tvLabel.setText(label);
        mViewHolder.tvRole.setText(mDatas.get(position).getName());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_role_label)
        TextView tvLabel;
        @BindView(R.id.tv_role_name)
        TextView tvRole;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
