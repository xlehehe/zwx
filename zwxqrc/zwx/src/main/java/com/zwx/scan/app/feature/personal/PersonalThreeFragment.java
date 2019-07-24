package com.zwx.scan.app.feature.personal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zwx.scan.app.feature.countanalysis.member.MemberAnalysisActivity;
import com.zwx.scan.app.feature.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalThreeFragment extends BaseFragment<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {


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

    //意见反馈
    @BindView(R.id.feedback_msg_tip)
    protected MsgView feedback_msg_tip;
    @BindView(R.id.rl_feedback)
    protected RelativeLayout rl_feedback;

    @BindView(R.id.name)
    protected TextView tvName;

    @BindView(R.id.tv_store_name)
    protected TextView tv_store_name;

    @BindView(R.id.tv_role_name)
    protected TextView tv_role_name;


    //认证
    @BindView(R.id.tv_auth_state)
    protected TextView tv_auth_state;

    protected List<MessageSet> messageSetList = new ArrayList<>();
    public  static int  msgCount = 0;
    private String userId;
    public  static String  feedStatus = "0";
    private MainActivity activity = null;
    private String title;

    public  String storeNames;
    public  String rolesNames;
    public  String  nickName;

    /**
     * 在未完成状态下   process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证，PP 跳个人绑定手机，PB跳个人绑定银行卡，PS  跳企业签订协议
     * */
    public static String process;
    public static String status; //status  N  未认证，A  已认证， NA 未完成
    public static String statusStr;

    public PersonalThreeFragment() {
        // Required empty public constructor
    }
    public static PersonalThreeFragment getInstance(String param) {
        PersonalThreeFragment fragment = new PersonalThreeFragment();
        fragment.title = param;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_personal_three;
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



    }

    @Override
    protected void initData() {

        //我的 资料信息 从缓存中读取，该信息在homeFragment页面调用接口
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
        userId = SPUtils.getInstance().getString("userId");

//        presenter.doFeedFlag(getActivity(),userId,"FRAGMENT");

        status = SPUtils.getInstance().getString("status");
        process = SPUtils.getInstance().getString("process");
        if("N".equals(status)){
            statusStr = "未认证";
            tv_auth_state.setTextColor(getResources().getColor(R.color.font_color_red));
        }else if("A".equals(status)){
            statusStr = "已认证";
            tv_auth_state.setTextColor(getResources().getColor(R.color.font_color_blue));
        }else if("NA".equals(status)){
            statusStr = "未完成";
            tv_auth_state.setTextColor(getResources().getColor(R.color.font_color_red));
        }
        tv_auth_state.setText(statusStr);


      /*  new Thread() {
            @Override
            public void run() {
                super.run();
                //休眠3秒
                try{
                    Thread.sleep(1000);
                    presenter.selectPaymentAuthStatus(getActivity(),userId);
                }catch (InterruptedException ex){

                }

            }
        }.start();*/
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if(msgCount >=1){
            msgView.setText(msgCount +"");
            msgView.setVisibility(View.VISIBLE );
        }else {
            msgView.setVisibility(View.INVISIBLE );
        }



        if("1".equals(feedStatus)){
            feedback_msg_tip.setVisibility(View.VISIBLE );
        }else {
            feedback_msg_tip.setVisibility(View.INVISIBLE );
        }

        if("N".equals(status)){
            statusStr = "未认证";
            process= null;
        }else if("A".equals(status)){
            statusStr = "已认证";
            process= null;
        }else if(" NA".equals(status)){
            statusStr = "未完成";

        }
        tv_auth_state.setText(statusStr);*/

        new Thread() {
            @Override
            public void run() {
                super.run();
                //休眠3秒
                try{
                    Thread.sleep(3000);
                    presenter.selectPaymentAuthStatus(getActivity(),userId);
                }catch (InterruptedException ex){

                }

            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    @OnClick({R.id.ll_right,R.id.rl_msg,R.id.rl_system,R.id.rl_feedback,R.id.rl_auth,R.id.ll_top})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_right:
                intent = new Intent(getActivity(),PersonalAccountManageActivity.class);
                intent.putExtra("isShowPsd","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_system:
                ActivityUtils.startActivity(SystemManageActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_msg:
                ActivityUtils.startActivity(MessageListActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_feedback:
                ActivityUtils.startActivity(FeedBackActivity.class,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.ll_top:
                intent = new Intent(getActivity(),PersonalAccountManageActivity.class);
                intent.putExtra("isShowPsd","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                break;

            case R.id.rl_auth:

                if("N".equals(status)){  //未认证
                    /**
                     * 在未完成状态下
                     *
                     * 在未认证状态status  :N 下  process  ：N 跳选择认证界面   CI  跳企业认证，CP 跳企业绑定手机，CS  跳企业签订协议，PI  跳个人认证第一步信息，PP 跳个人绑定手机 ，PB跳个人绑定银行卡，PS  跳企业签订协议
                     * */
                    if(process != null &&  !"".equals(process)){
                        if("CI".equals(process)||"NCI".equals(process)){  //跳企业认证
                            intent = new Intent(getActivity(),TradeDrawingEnterpriseAuthOneActivity.class);
                        }else if("PI".equals(process)){  //跳个人认证
                            intent = new Intent(getActivity(),TradeDrawingPersonalAuthOneActivity.class);
                        }else if("PP".equals(process)||"CP".equals(process)){//绑定手机号    展示  123 步骤
                            intent = new Intent(getActivity(),TradeDrawingPersonalAuthTwoActivity.class);
                        }else if("PB".equals(process)){//PB跳个人绑定银行卡    展示  123 步骤  企业认证没有
                            intent = new Intent(getActivity(),TradeDrawingPersonalAuthThreeActivity.class);
                        }else if("PS".equals(process)||"CS".equals(process)){//PS跳协议    展示  123 步骤
                            intent = new Intent(getActivity(),PayAuthAgreeActivity.class);
                        }else if("N".equals(process)){ //选项
                            intent = new Intent(getActivity(),PayAuthNoComplateActivity.class);
                        }
                        if(intent !=null){
                            intent.putExtra("process",process);
                        }

                    }
                }else if("A".equals(status)||"NA".equals(status)){ //已认证
                    intent = new Intent(getActivity(),PayAuthComplateActivity.class);
                }
                if(intent !=null){
                    intent.putExtra("status",status);
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }


                break;

        }
    }
}
