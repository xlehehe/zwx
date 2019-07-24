package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MemberRedEnvelope;
import com.zwx.scan.app.data.bean.RedEnvelopeDetail;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 红包 列表 详情
 * version: 1.0
 **/
public class RedEnvelopeDetailActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener  {
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tv_right;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.tv_name)
    protected TextView tv_name;
    @BindView(R.id.tv_tel)
    protected TextView tv_tel;

    @BindView(R.id.tv_balance)
    protected TextView tv_balance;
    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;

    private String userId;
    private String compId;
    private String memberTel = "";
    private String memberId;
    private int pageNumber = 1;
    private int pageSize = 15;

    protected  AlertDialog.Builder builder  = null;
    protected AlertDialog dialog = null;

    public RedEnvelopeDetailAdapter listAdapter;
    public RecyclerAdapterWithHF mAdapter;
    protected List<RedEnvelopeDetail> envelopeList = new ArrayList<RedEnvelopeDetail>();

    protected String banlanceAfter = "0";
    protected String balanceChange = "0";
    protected String updateAmt;
    protected String changeState = "in";
    protected String changeReason;
    protected String balanceBefore = "0";

    protected String balance; //红包金额
    protected String memberName ;

    protected String isClickSubmit = "YES";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_evvelope_detail;
    }

    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员红包详情");
        tv_right.setText("修改金额");
        tv_right.setVisibility(View.VISIBLE);
        memberId = getIntent().getStringExtra("memberId");
        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");
        memberName = getIntent().getStringExtra("memberName");
        memberTel = getIntent().getStringExtra("memberTel");
        balance = getIntent().getStringExtra("balance");

        tv_tel.setText(memberTel != null && !"".equals(memberTel)?memberTel:"—");
        tv_name.setText(memberName != null && !"".equals(memberName)?memberName:"—");
        tv_balance.setText(balance != null && !"".equals(balance)?balance:"0");

        initPtr();
    }

    @Override
    protected void initData() {


        userId = SPUtils.getInstance().getString("userId");
        listAdapter = new RedEnvelopeDetailAdapter(RedEnvelopeDetailActivity.this, envelopeList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

     /*   mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {

                Intent intent = new Intent(RedEnvelopeDetailActivity.this, RedEnvelopeDetailActivity.class);
                if (envelopeList != null && envelopeList.size() > 0) {
                    RedEnvelopeDetail envelope = envelopeList.get(position);
//                    intent.putExtra("orderId",envelope.getDetailedId()+"");
//                    intent.putExtra("order",order);

                    ActivityUtils.startActivity(intent,
                            R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });*/





    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(RedEnvelopeActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;

            case R.id.tv_right:
                showCate();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.startActivity(RedEnvelopeActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private String isAdd = "YES";

    protected void showCate() {

        View customView = View.inflate(this, R.layout.layout_red_envelope_detail_dialog, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        EditText edt_amt = (EditText)customView.findViewById(R.id.edt_amt);
        EditText edt_reason = (EditText)customView.findViewById(R.id.edt_reason);
        TextView tv_product_remark_num = (TextView)customView.findViewById(R.id.tv_product_remark_num);

        edt_reason.setSelection(edt_reason.getText().length());
        edt_amt.setFilters(new InputFilter[]{new MaxTextLengthFilter(6)});
        edt_reason.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_reason.setFilters(new InputFilter[]{new MaxTextLengthFilter(30)});
        edt_amt.setSelection(edt_amt.getText().length());
        TextView title = (TextView)customView.findViewById(R.id.title);
        edt_amt.setFilters(new InputFilter[]{new MaxTextLengthFilter(6)});

        TextView tv_cur_amt = (TextView)customView.findViewById(R.id.tv_cur_amt);
        RadioGroup rg_banlance = (RadioGroup)customView.findViewById(R.id.rg_banlance);
        RadioButton reduce_amt = (RadioButton)customView.findViewById(R.id.reduce_amt);
        RadioButton add_amt = (RadioButton)customView.findViewById(R.id.add_amt);
        TextView tv_update_amt = (TextView)customView.findViewById(R.id.tv_update_amt);
        if("-".equals(balance)){
            balance = "0";
        }
        tv_cur_amt.setText(balance);
        tv_update_amt.setText(balance);
        add_amt.setChecked(true);
        reduce_amt.setChecked(false);

        rg_banlance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String amtStr ="";
                switch (checkedId) {

                    case R.id.add_amt:
                        isAdd = "YES";
                        changeState = "in";
                        add_amt.setChecked(true);
                        reduce_amt.setChecked(false);
                        updateAmt = edt_amt.getText().toString().trim();
                        balanceChange = updateAmt;
                        balanceBefore = tv_cur_amt.getText().toString().trim();
                        if(balanceBefore != null && !"".equals(balanceBefore)){


                        }else{
                            balanceBefore = "0";
                        }
                        if(updateAmt != null && !"".equals(updateAmt)){
                            Double curAmt2 = Double.parseDouble(balanceBefore);
                            Double updateAmt2 = Double.parseDouble(updateAmt);
                            Double amt = curAmt2 + updateAmt2;
                            amtStr = RegexUtils.getDoubleString(amt);
                            banlanceAfter = amtStr;
                            balance = banlanceAfter;
                            tv_update_amt.setText(amtStr);
                            isClickSubmit = "YES";
                        }else {
                            isClickSubmit = "NO";
                            tv_update_amt.setText(balanceBefore);
                        }
                        break;

                    case R.id.reduce_amt:
                        isAdd = "NO";
                        changeState = "ou";
                        add_amt.setChecked(false);
                        reduce_amt.setChecked(true);
                        updateAmt = edt_amt.getText().toString().trim();
                        balanceChange = updateAmt;
                        balanceBefore = tv_cur_amt.getText().toString().trim();
                        if(updateAmt != null && !"".equals(updateAmt) ){
                        }else{
                            updateAmt = "0";
                        }
                        if(balanceBefore != null && !"".equals(balanceBefore)){

                        }else {
                            balanceBefore = "0";
                        }

                        if(Double.parseDouble(updateAmt)>Double.parseDouble(balanceBefore)){
                            ToastUtils.showShort("减少数不能大于现有金额");
                            isClickSubmit = "NO";
                            return;
                        }
                        if(updateAmt != null && !"".equals(updateAmt)){
                            Double curAmt2 = Double.parseDouble(balanceBefore);
                            Double updateAmt2 = Double.parseDouble(updateAmt);
                            if(curAmt2 - updateAmt2>=0){
                                Double amt = curAmt2 - updateAmt2;
                                amtStr = RegexUtils.getDoubleString(amt);
                                banlanceAfter = amtStr;
                                balance = banlanceAfter;
                                tv_update_amt.setText(amtStr);
                                isClickSubmit = "YES";
                            }else {
                                ToastUtils.showShort("减少数不能小于零");
                                isClickSubmit = "NO";
                                return;
                            }
                        }else {
                            isClickSubmit = "NO";
                            tv_update_amt.setText(balanceBefore);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        edt_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateAmt = edt_amt.getText().toString().trim();
                balanceChange = updateAmt;
                balanceBefore = tv_cur_amt.getText().toString().trim();
                String amtStr ="";
                if(balanceBefore != null && !"".equals(balanceBefore)){

                }else {
                    balanceBefore = "0";
                }
                if("YES".equals(isAdd)){  //添加

                    if(updateAmt != null && !"".equals(updateAmt)){
                        Double curAmt2 = Double.parseDouble(balanceBefore);
                        Double updateAmt2 = Double.parseDouble(updateAmt);
                        Double amt = curAmt2 + updateAmt2;
                        amtStr = RegexUtils.getDoubleString(amt);
                        banlanceAfter = amtStr;
                        balance = banlanceAfter;
                        tv_update_amt.setText(amtStr);
                    }else {
                        tv_update_amt.setText(balanceBefore);
                    }
                }else {


                    if(updateAmt != null && !"".equals(updateAmt) &&!"0".equals(updateAmt)){

                    }else {
                        updateAmt = "0";
                    }

                    if(Double.parseDouble(updateAmt)>Double.parseDouble(balanceBefore)){
                        ToastUtils.showShort("减少数不能大于现有金额");
                        isClickSubmit = "NO";
                        return;
                    }
                    if(updateAmt != null && !"".equals(updateAmt)){
                        Double curAmt2 = Double.parseDouble(balanceBefore);
                        Double updateAmt2 = Double.parseDouble(updateAmt);
                        if(curAmt2 - updateAmt2>=0){
                            Double amt = curAmt2 - updateAmt2;
                            amtStr = RegexUtils.getDoubleString(amt);
                            banlanceAfter = amtStr;
                            balance = banlanceAfter;
                            tv_update_amt.setText(amtStr);
                            isClickSubmit = "YES";
                        }else {
                            ToastUtils.showShort("减少数不能小于零");
                            isClickSubmit = "NO";
                            return;
                        }
                    }else {
                        isClickSubmit = "NO";
                        tv_update_amt.setText(balanceBefore);
                    }
                }

            }
        });

        edt_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String reason = edt_reason.getText().toString();

                tv_product_remark_num.setText(String.format("%d/%d", reason.length(), 30));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);

        LinearLayout ll_clear = (LinearLayout) customView.findViewById(R.id.ll_clear);
        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(RedEnvelopeDetailActivity.this,edt_amt);
                balanceChange = edt_amt.getText().toString().trim();
                changeReason = edt_reason.getText().toString().trim();
                balanceBefore = tv_cur_amt.getText().toString().trim();

                if(balanceBefore == null || "".equals(balanceBefore) ){
                    balanceBefore = "0";
                }

                if(balanceChange == null || "".equals(balanceChange) ){
                    balanceChange = "0";
                }


                if("NO".equals(isClickSubmit)){

                }else {
                    presenter.updateMemberBal(RedEnvelopeDetailActivity.this,compId,memberId,balanceChange,changeState,changeReason,balanceBefore);
                    dialog.dismiss();
                }

            }
        });
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
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
    }

    private void initPtr() {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptr.autoRefresh(true);
            }
        }, 150);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 1;
                presenter.queryAllMemberRedEnvlopeDeTail(RedEnvelopeDetailActivity.this,compId,memberId,pageSize,pageNumber,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.queryAllMemberRedEnvlopeDeTail(RedEnvelopeDetailActivity.this,compId,memberId,pageSize,pageNumber,false);
            }
        });
    }

}
