package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CateringinfoManageActivity extends BaseActivity<CateringInfoContract.Presenter> implements CateringInfoContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;
    @BindView(R.id.tv_brand_name)
    protected TextView tvName;


    @BindView(R.id.tv_story)
    protected TextView tvStory;

    @BindView(R.id.btn_set)
    public Button btnSet;


    @BindView(R.id.rv_list)
    public RecyclerView rvList;


    @BindView(R.id.ptr)
    public PtrClassicFrameLayout ptr;



    private String userId;
    private String compId;
    private int pageNumber = 1;
    private int pageSize = 10;
    protected List<CateringinfBean> cateringinfBeanList = new ArrayList<>();

    protected CateringinfoManageAdapter listAdapter = null;
    protected RecyclerAdapterWithHF mAdapter = null;

    protected List<Store> storeList = new ArrayList<>();
    protected static  int RESULT_CODE = 6666;
    protected static  int REQUEST_CODE = 6666;

    private  String brandId ;
    protected String brandLogo;

    protected String brandName;
    protected String brandStory;

    protected Brand brand;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cateringinfo_manage;
    }

    @Override
    protected void initView() {

        DaggerCateringInfoComponent.builder().applicationComponent(AppContext.getApplicationComponent())
                .cateringInfoModule(new CateringInfoModule(this))
                .build().inject(this);
        tvTitle.setText("企业信息管理");

        initPtr();
    }

    @Override
    protected void initData() {

      /*  CateringinfBean cateringinfBean = new CateringinfBean();
        cateringinfBean.setName("张无忌");
        cateringinfBean.setPhone("19999996666");
        cateringinfBean.setStatus("z");
        cateringinfBean.setStoreName("济南市董小姐经十路世贸广场店");
        cateringinfBeanList.add(cateringinfBean);
        cateringinfBean = new CateringinfBean();
        cateringinfBean.setName("张无忌");
        cateringinfBean.setPhone("19999996666");
        cateringinfBean.setStatus("j");
        cateringinfBean.setStoreName("济南市董小姐经十路世贸广场店");
        cateringinfBeanList.add(cateringinfBean);
        cateringinfBean = new CateringinfBean();
        cateringinfBean.setName("张无忌");
        cateringinfBean.setPhone("19999996666");
        cateringinfBean.setStatus("z");
        cateringinfBean.setStoreName("济南市董小姐经十路世贸广场店");
        cateringinfBeanList.add(cateringinfBean);*/

        brandId = SPUtils.getInstance().getString("brandId");
//        presenter.doQuery(this,brandId,pageNumber,pageSize,true);
//        presenter.doQuery(this,brandId,pageNumber,pageSize,true);
        setCaterAdapter();

    }
    private void setCaterAdapter(){
        listAdapter = new CateringinfoManageAdapter(CateringinfoManageActivity.this, storeList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
         rvList.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new RecyclerAdapterWithHF(listAdapter);
        rvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                StoreInfoManageActivity  activity = new StoreInfoManageActivity();
                activity.photoList = new ArrayList<>();
                if(storeList != null && storeList.size()>0){
                    Store store = storeList.get(position);
                    SPUtils.getInstance().put("storeInfoId",String.valueOf(store.getId()));
                    Intent intent = new Intent(CateringinfoManageActivity.this,StoreInfoManageActivity.class);
                    intent.putExtra("storeId",String.valueOf(store.getId()));
                    ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
                }

            }
        });
    }


    protected void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(CateringinfoManageActivity.this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }

    @OnClick({R.id.iv_back,R.id.btn_set})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(CateringinfoManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.btn_set:
                intent = new Intent(CateringinfoManageActivity.this,BrandSettingActivity.class);
                intent.putExtra("brand",brand);
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);
//                ActivityUtils.startActivityForResult(CateringinfoManageActivity.this,intent,REQUEST_CODE,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(CateringinfoManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
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
                presenter.doQuery(CateringinfoManageActivity.this,brandId,pageNumber,pageSize,true);
            }
        });


        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNumber++;
                presenter.doQuery(CateringinfoManageActivity.this,brandId,pageNumber,pageSize,false);

            }
        });
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_CODE){
            if(requestCode == REQUEST_CODE){


            }
        }
    }*/
}
