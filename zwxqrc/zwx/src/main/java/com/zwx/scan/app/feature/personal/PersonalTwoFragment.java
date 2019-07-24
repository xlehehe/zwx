package com.zwx.scan.app.feature.personal;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.tablayout.widget.MsgView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.home.HomeFragment;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.feature.member.MemberConsumeListDetailActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalTwoFragment extends BaseFragment<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{

    @BindView(R.id.ll_back)
    protected LinearLayout llBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.ll_top)
    protected LinearLayout ll_top;

    @BindView(R.id.ll_right)
    protected LinearLayout llRight;

    @BindView(R.id.tv_right)
    protected TextView tv_right;

    @BindView(R.id.iv_right)
    protected ImageView ivRight;

    @BindView(R.id.rl_msg)
    protected RelativeLayout rlMsg;

    @BindView(R.id.mv_msg_tip)
    protected MsgView msgView;
    @BindView(R.id.rl_system)
    protected RelativeLayout rlSystem;



    @BindView(R.id.name)
    protected TextView tvName;

    @BindView(R.id.tv_store_name)
    protected TextView tv_store_name;

    @BindView(R.id.tv_role_name)
    protected TextView tv_role_name;

    protected List<MessageSet>  messageSetList = new ArrayList<>();
    public  static int  msgCount = 0;
    private String userId;

    private MainActivity activity = null;
    String title;

    public  String storeNames;
    public  String rolesNames;
    public  String  nickName;
    public PersonalTwoFragment() {
        // Required empty public constructor
    }
    public static PersonalTwoFragment getInstance(String param) {
        PersonalTwoFragment fragment = new PersonalTwoFragment();
        fragment.title = param;


        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_personal_two;
    }

    @Override
    protected void initInjector() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        activity = (MainActivity)getActivity();
    }

    @Override
    protected void initView() {
        llBack.setVisibility(View.GONE);
        tvTitle.setText("我的");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setBackgroundResource(R.drawable.ic_setting);

        if(msgCount >=1){
            msgView.setText(msgCount +"");
            msgView.setVisibility(View.VISIBLE );
        }else {
            msgView.setVisibility(View.INVISIBLE );
        }

    }

    @Override
    protected void initData() {

        storeNames = SPUtils.getInstance().getString("storeNames");

        rolesNames = SPUtils.getInstance().getString("rolesNames");

        nickName = SPUtils.getInstance().getString("nickName");

//        rolesNames = SPUtils.getInstance().getString("rolesNames");

//        activity.nickName = SPUtils.getInstance().getString("nickName");
//        activity.rolesNames = SPUtils.getInstance().getString("rolesNames");
//        activity.storeNames = SPUtils.getInstance().getString("storeNames");
        tv_role_name.setText(rolesNames != null && !"".equals(rolesNames)?"("+rolesNames+")":"");
        tv_store_name.setText(storeNames != null && !"".equals(storeNames)?("所属店铺：")+storeNames:"所属店铺：");

        tvName.setText(nickName !=null && !"".equals(nickName)?nickName:"");
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = SPUtils.getInstance().getString("userId");



//        presenter.queryMessageType(getActivity(),userId,true);
    }

    @OnClick({R.id.ll_right,R.id.rl_msg,R.id.rl_system,R.id.ll_top})
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),PersonalAccountManageActivity.class);
        switch (v.getId()) {
            case R.id.ll_right:
//                ActivityUtils.startActivity(PersonalAccountManageActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                intent.putExtra("isShowPsd","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_system:
                ActivityUtils.startActivity(SystemManageActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_msg:
                ActivityUtils.startActivity(MessageListActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.ll_top:
                intent.putExtra("isShowPsd","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

        }
    }
}
