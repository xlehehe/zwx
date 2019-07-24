package com.zwx.scan.app.feature.personal;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.MessageSet;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignCouponListFragment;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageListFragment extends BaseFragment<PersonalContract.Presenter> implements PersonalContract.View {

    @BindView(R.id.recycler_view_frame)
    public PtrClassicFrameLayout ptr;

    @BindView(R.id.rv_list)
    public RecyclerView rvList;


    private String userId;
    private int pageNumber = 1;
    private int pageSize = 10;
    private  String type;


    MessageListAdapter listAdapter = null;
    RecyclerAdapterWithHF mAdapter = null;

    List<MessageSet> messageSetList = new ArrayList<>();

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;
    public MessageListFragment() {
        // Required empty public constructor
    }

    public static MessageListFragment getInstance(String type) {
        MessageListFragment fragment = new MessageListFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected void initInjector() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        initPtr();
    }

    @Override
    protected void initView() {

        setMessageAdapter();
        setListener();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        presenter.queryMessageList(getActivity(),userId,pageNumber,pageSize,type,true);

    }

    private void setMessageAdapter(){
        listAdapter = new MessageListAdapter(getActivity(), messageSetList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new SpacesItemDecoration(1));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

    }

    private void setListener(){
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {

                MessageSet messageSet = messageSetList.get(position);
                presenter.doMessageStatus(getActivity(),String.valueOf(messageSet.getId()),userId);
            }
        });
    }


    private void initPtr() {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {

                if("SN".equals(type)){
                    ptr.autoRefresh(true);
                }else {
                    ptr.autoRefresh(false);
                }

            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                pageNumber = 1;
                presenter.queryMessageList(getActivity(),userId,pageNumber,pageSize,type,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.queryMessageList(getActivity(),userId,pageNumber,pageSize,type,false);

            }
        });
    }


    protected void setDialog(){
        String tip = "";

        tip = "此消息已被管理员撤回？";
        View rootView = View.inflate(getActivity(), R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(getActivity());
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);
    }
}
