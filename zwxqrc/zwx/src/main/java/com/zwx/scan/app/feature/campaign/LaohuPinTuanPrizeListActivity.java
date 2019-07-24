package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.widget.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class LaohuPinTuanPrizeListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.list_view)
    protected MyListView list_view;

    @BindView(R.id.is_rg)
    protected RadioGroup is_rg;

    @BindView(R.id.yes_rb)
    protected RadioButton yes_rb;
    @BindView(R.id.no_rb)
    protected RadioButton no_rb;


    private  SelectPrizeAdapter adapter;
    private String isPrizeSelected = "YES";
    private ArrayList<PrizeBean> prizeBeanList = new ArrayList<>();

    private ArrayList<PrizeBean> selectedList = new ArrayList<>();


    protected  int currentNum = -1;

    private ArrayList<PrizeBean> resetSelectedList = new ArrayList<>();
    PrizeBean consolePrizeBean  = new PrizeBean();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_laohu_pin_tuan_prize_list;
    }

    @Override
    protected void initView() {

        tvTitle.setText("条件编辑");

        isPrizeSelected = SPUtils.getInstance().getString("isPrizeSelected");

        selectedList =  (ArrayList<PrizeBean>) getIntent().getSerializableExtra("prizeBeanList");

        if("YES".equals(isPrizeSelected)){
            yes_rb.setChecked(true);

        }else if("NO".equals(isPrizeSelected)){
            no_rb.setChecked(true);
        }else {
            yes_rb.setChecked(false);
            no_rb.setChecked(false);
        }
        is_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.yes_rb:
                        isPrizeSelected = "YES";
                        SPUtils.getInstance().put("isPrizeSelected",isPrizeSelected);

                       consolePrizeBean.setChecked(true);
                       consolePrizeBean.setName("安慰奖");
                        break;

                    case R.id.no_rb:
                        isPrizeSelected ="NO";
                        SPUtils.getInstance().put("isPrizeSelected",isPrizeSelected);

                        consolePrizeBean.setChecked(true);
                        consolePrizeBean.setName("未中奖");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    protected void initData() {
        initPrizeBeanListData();
        if(selectedList != null && selectedList.size()>0){

            for (PrizeBean prizeBean : selectedList){
                String selectName = prizeBean.getName();
                if(prizeBean.isChecked()){


                    if(selectName != null && !"".equals(selectName)){
                        if("未中奖".equals(selectName)){
                            isPrizeSelected = "NO";
                            no_rb.setChecked(true);
                            consolePrizeBean.setChecked(true);
                            consolePrizeBean.setName("未中奖");
                        }else if("安慰奖".equals(selectName)){
                            isPrizeSelected = "YES";
                            yes_rb.setChecked(true);
                            consolePrizeBean.setChecked(true);
                            consolePrizeBean.setName("安慰奖");
                        }else {
                            for (PrizeBean prizeBean1 : prizeBeanList){
                                String unSelectName = prizeBean1.getName();

                                if(selectName.equals(unSelectName)){
                                    prizeBean1.setChecked(prizeBean.isChecked());
                                }
                            }
                        }
                    }else {
                        no_rb.setChecked(true);
                        consolePrizeBean.setChecked(true);
                        consolePrizeBean.setName("未中奖");
                    }


                }
            }

           adapter.notifyDataSetChanged();

       }


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中

                    if(position == 0){
                        prizeBeanList.get(position).setChecked(true);
                    }else {
                        if(prizeBeanList.get(position).isChecked()){
                            for (int i = 0;i<=position;i++){
                                prizeBeanList.get(i).setChecked(false);
                            }
                            for (PrizeBean prizeBean : prizeBeanList){
                                prizeBean.setChecked(false);
                            }
                        }else {
                            for (int i = 0;i<=position;i++){
                                prizeBeanList.get(i).setChecked(true);
                            }


                        }

                    }

                    for (PrizeBean prizeBean : prizeBeanList){
                        prizeBean.setChecked(false);
                    }
                    for (int i = 0;i<=position;i++){
                        prizeBeanList.get(i).setChecked(true);
                    }
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                   /* for (PrizeBean prizeBean : prizeBeanList){
                        prizeBean.setChecked(false);
                    }*/
                   if(position == 0){
                       prizeBeanList.get(position).setChecked(true);
                   }

                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                 /*   if(prizeBeanList.get(position).isChecked()){
                        for (int i = 0;i<=position;i++){
                            prizeBeanList.get(i).setChecked(false);
                        }
                        for (PrizeBean prizeBean : prizeBeanList){
                            prizeBean.setChecked(false);
                        }
                    }else {
                        for (int i = 0;i<=position;i++){
                            prizeBeanList.get(i).setChecked(true);
                        }

                    }*/
                    for (PrizeBean prizeBean : prizeBeanList){
                        prizeBean.setChecked(false);
                    }
                    for (int i = 0;i<=position;i++){
                        prizeBeanList.get(i).setChecked(true);
                    }
                    currentNum = position;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initPrizeBeanListData(){
        PrizeBean prizeBean = new PrizeBean();
        prizeBean.setName("奖项一");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setName("奖项二");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setName("奖项三");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setName("奖项四");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setName("奖项五");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        prizeBean = new PrizeBean();
        prizeBean.setName("奖项六");
        prizeBean.setChecked(false);
        prizeBeanList.add(prizeBean);
        setBeanAdapter();
    }

    private void setBeanAdapter(){
        adapter = new SelectPrizeAdapter(this,prizeBeanList);
        list_view.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back,R.id.tv_right,R.id.cancel,R.id.confirm})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(LaohuPinTuanPrizeListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.confirm:
                selectedList.clear();
                if(prizeBeanList != null){
                    for (PrizeBean prizeBean : prizeBeanList){
                        boolean isChecked = prizeBean.isChecked();
                        if(isChecked){
                            selectedList.add(prizeBean);
                        }

                    }
                }

                //添加未中奖或安慰奖
                selectedList.add(consolePrizeBean);

                intent = new Intent(LaohuPinTuanPrizeListActivity.this,LaohuPinTuanNextTwoActivity.class);
                intent.putExtra("prizeBeanList",selectedList);
                setResult(5001,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.cancel:

                ActivityUtils.finishActivity(LaohuPinTuanPrizeListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.tv_right:

                ActivityUtils.finishActivity(LaohuPinTuanPrizeListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(LaohuPinTuanPrizeListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
