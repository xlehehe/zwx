package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.SelectTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreInfoParameterSelectorActivity extends BaseActivity  implements View.OnClickListener{
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.ll_right)
    protected LinearLayout llRight;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.list_view)
    protected ListView listView;
    private ArrayList<CateBean> typeList = new ArrayList<>();
    private ArrayList<CateBean> selectTypeList= new ArrayList<>();
    private SelectTypeAdapter myAdapter ;
    private String title;
    protected String serviceId;
    protected String serviceName;
    protected String banquetId;
    protected String banquetName;

    protected String categoriesId;
    protected String categoriesName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_info_parameter_selector;

    }

    @Override
    protected void initView() {

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.save));
        typeList = (ArrayList<CateBean>) getIntent().getSerializableExtra("typeList");
        title = getIntent().getStringExtra("title");



        tvTitle.setText(title);
        myAdapter = new SelectTypeAdapter(this,typeList);
        listView.setAdapter(myAdapter);
        if(getResources().getString(R.string.tigongfuwu).equals(title)){  //提供服务
            serviceId = getIntent().getStringExtra("serviceId");

            if(typeList != null && typeList.size()>0){

                if(serviceId != null && !"".equals(serviceId)){
                    String[] serviceIds = serviceId.split(",");
                    for (int i = 0;i<serviceIds.length;i++){
                        String serId = serviceIds[i];
                        for (CateBean cateBean : typeList){
                            String serIdTwo = cateBean.getValue();

                            if(serId.equals(serIdTwo)){
                                cateBean.setChecked(true);
                                break;
                            }
                        }
                    }
                }


            }
        }else if(getResources().getString(R.string.tigongyanxi).equals(title)){   //宴席
            banquetId = getIntent().getStringExtra("banquetId");
            if(typeList != null && typeList.size()>0){
                if(banquetId != null && !"".equals(banquetId)){
                    String[] banquetIds = banquetId.split(",");
                    for (int i = 0;i<banquetIds.length;i++){
                        String banId = banquetIds[i];
                        for (CateBean cateBean : typeList){
                            String banIdTwo = cateBean.getValue();

                            if(banId.equals(banIdTwo)){
                                cateBean.setChecked(true);
                                break;
                            }
                        }
                    }

                }
            }
        }else if(getResources().getString(R.string.zhuyingleibie).equals(title)){  //主营类别
            categoriesId = getIntent().getStringExtra("categoriesId");

            if(typeList != null && typeList.size()>0){
                if(categoriesId != null && !"".equals(categoriesId)){
                    String[] categoriesIds = categoriesId.split(",");
                    for (int i = 0;i<categoriesIds.length;i++){
                        String catId = categoriesIds[i];
                        for (CateBean cateBean : typeList){
                            String catIdTwo = cateBean.getValue();

                            if(catId.equals(catIdTwo)){
                                cateBean.setChecked(true);
                                break;
                            }
                        }
                    }

                }
            }
        }
        myAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentNum == -1){ //选中
                    if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    typeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void selectData(){
        for (CateBean typeBean :typeList){
            boolean isChecked = typeBean.isChecked();
            if(isChecked){
                selectTypeList.add(typeBean);
            }
        }


    }


    @OnClick({R.id.iv_back,R.id.ll_right})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(StoreInfoParameterSelectorActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.ll_right:
                selectData();
                intent = new Intent(StoreInfoParameterSelectorActivity.this,StoreInfoManageActivity.class);
                intent.putExtra("selectTypeList",selectTypeList);
                setResult(StoreInfoManageActivity.RESULT_CODE,intent);

                finish();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(StoreInfoParameterSelectorActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
