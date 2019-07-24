package com.zwx.scan.app.feature.personal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.tablayout.SlidingTabLayout;
import com.zwx.library.tablayout.listener.OnTabSelectListener;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Message;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignCouponListActivity;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.widget.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageListActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_right)
    protected ImageView ivRight;
    @BindView(R.id.ll_right)
    protected LinearLayout llRight;

    @BindView(R.id.tab_layout)
    protected SlidingTabLayout tabLayout;

    @BindView(R.id.view_pager)
    protected CustomViewPager viewPager;
 /*   @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;*/

    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;

    protected String status;

    protected  List<MessageSet> messageSetList = new ArrayList<>();



    CustomPopWindow mCustomPopWindow = null;

    protected String type = "SN";
    public TypePagerAdapter pagerAdapter = null;

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;

    protected  String isUpdate = "NO";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initView() {

        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);

        tvTitle.setText(getResources().getText(R.string.title_message_manage));
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setBackgroundResource(R.drawable.ic_ellipsis);
//        initPtr();
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                MessageSet messageSet = messageSetList.get(position);

                if(messageSet != null){
                    type = messageSet.getValue();

                    viewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


    }

    @Override
    protected void initData() {

        /*Message message = new Message();
        message.setTitle("系统升级通知");
        message.setContent("济南市，简称“济”，别称“泉城”，是山东省省会、 Ώ]  全国\n" +
                "十五个副省级城市之一，环渤海地区南翼的中心城市，山…");
        message.setDate("下午 01:36");
        messageList.add(message);

        message = new Message();
        message.setTitle("系统升级通知");
        message.setContent("济南市，简称“济”，别称“泉城”，是山东省省会、 Ώ]  全国\n" +
                "十五个副省级城市之一，环渤海地区南翼的中心城市，山…");
        message.setDate("上午 10:32");
        messageList.add(message);

        message = new Message();
        message.setTitle("系统升级通知");
        message.setContent("");
        message.setDate("19/01/02");
        messageList.add(message);

        message = new Message();
        message.setTitle("系统升级通知");
        message.setContent("济南市，简称“济”，别称“泉城”，是山东省省会、 Ώ]  全国\n" +
                "十五个副省级城市之一，环渤海地区南翼的中心城市，山…");
        message.setDate("18/12/02");
        messageList.add(message);

        setMessageAdapter();*/

//        setPagerAdapter();
        userId = SPUtils.getInstance().getString("userId");
        presenter.queryMessageType(this,userId,"messageListActivity");
    }


   /* private void setMessageAdapter(){
        listAdapter = new MessageListAdapter(MessageListActivity.this, messageList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
         rvList.addItemDecoration(new SpacesItemDecoration(1));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

        ptr.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    private void setListener(){
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(MessageListActivity.this,MessageListDetailActivity.class);
                intent.putExtra("messageId","");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }*/

    public void setPagerAdapter(){
        pagerAdapter = new TypePagerAdapter(getSupportFragmentManager(),messageSetList);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);

        if("SN".equals(type)){
            viewPager.setCurrentItem(0);
        }else if("UN".equals(type)){
            viewPager.setCurrentItem(1);
        }else if("GN".equals(type)){
            viewPager.setCurrentItem(2);
        }else if("TN".equals(type)){
            viewPager.setCurrentItem(3);
        }

        MessageListFragment.getInstance(type);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(messageSetList != null && messageSetList.size() > 0){
                    type = messageSetList.get(position).getValue();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @OnClick({R.id.iv_back,R.id.ll_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(MessageListActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.ll_right:
                setPopWindow();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(MessageListActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void setPopWindow(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_msg_list_pop,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAsDropDown(llRight,0,5);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.tv_msg_readed:
//                        showContent = "已读";
                        status = "R";
                        setDialog();
                        isUpdate = "YES";
                        break;
                    case R.id.tv_msg_clear:
//                        showContent = "清空";
                        status = "D";
                        setDialog();
                        isUpdate = "YES";
                        break;
                }
                ToastUtils.showShort(showContent);
            }
        };
        contentView.findViewById(R.id.tv_msg_readed).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_msg_clear).setOnClickListener(listener);
    }







    public   class TypePagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragmentList;
        private FragmentManager mFragmentManager;
        private Bundle bundle  = new Bundle();
        private  List<MessageSet> messageSetList = null;
        public TypePagerAdapter(FragmentManager fm, List<MessageSet> typeList) {
            super(fm);
            this.mFragmentManager = fm;
            this.messageSetList = typeList;
            mFragmentList = new ArrayList<>();
            if(messageSetList !=null && messageSetList.size()>0){
                for (MessageSet messageSet : messageSetList){
                    String type = messageSet.getValue();
                    Fragment fragment = MessageListFragment.getInstance(type);
                    mFragmentList.add(fragment);
                }
            }

            setFragments(mFragmentList);
        }

        public void updateData(List<MessageSet> messageSetList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            if(messageSetList !=null && messageSetList.size()>0){
                for (MessageSet messageSet : messageSetList){
                    String type = messageSet.getValue();
                    Fragment fragment = MessageListFragment.getInstance(type);
                    mFragmentList.add(fragment);
                }
            }
            setFragments(fragments);
        }


        private void setFragments(ArrayList<Fragment> mFragmentList) {
            if(this.mFragmentList != null){
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                for(Fragment f:this.mFragmentList){
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
            }
            this.mFragmentList = mFragmentList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return messageSetList.get(position).getKey();
        }

    }

    private void setDialog(){
        String tip = "";

        if("R".equals(status)){
            tip = "确定要已读消息吗？";
        }else if("D".equals(status)){
            tip = "确定要清空消息吗？";
        }
        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.doStatus(MessageListActivity.this,userId,status,type);
                dialog.dismiss();

            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);
    }
}
