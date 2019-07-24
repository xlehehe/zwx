package com.zwx.scan.app.feature.accountmanage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/01/10
 * desc   :
 * version: 1.0
 **/
public class AccountRoleListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Role> mDatas = new ArrayList();

    public AccountRoleListAdapter(Context context,List<Role> datas) {
        mContext = context;
        this.mDatas = datas;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_account_new_role_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        Role role = mDatas.get(position);
        if (mDatas.get(position).isChecked()) {
            mViewHolder.mCbCheckbox.setChecked(true);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_blue_selected);
        } else {
            mViewHolder.mCbCheckbox.setChecked(false);
            mViewHolder.mCbCheckbox.setButtonDrawable(R.drawable.ic_gray_unselect);
        }
        mViewHolder.mCbCheckbox.setText(role.getName());

        mViewHolder.tvAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent((AccountNewActivity)mContext,AccountRoleAuthActivity.class);
                intent.putExtra("title",role.getName());
                intent.putExtra("id",String.valueOf(role.getId()));
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




        return convertView;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Person person = (Person) buttonView.getTag();
//        person.setCheckStatus(isChecked);
//    }

    static class ViewHolder {
        @BindView(R.id.select_auth)
        TextView tvAuth;
        @BindView(R.id.cb_checkbox)
        CheckBox mCbCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
