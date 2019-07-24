package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.PosterMaterial;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.MyGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PrizeTempletActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View,View.OnClickListener{


    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.rv_grid)
    protected MyGridView rvGrid;

    private String userId;
    private String compId;
    private String id;  //素材Id
    private String posterId1;
    private String posterId2;
    private String posterId3;
    private String posterId4;
    private String posterId5;
    private String posterId6;
    private String posterId7;
    private String posterId8;
    protected MaterialGameListAdapter mAdapter;

    protected MaterialGame materialGame = new MaterialGame();

    protected List<MaterialGame> materialGameList = new ArrayList<>();

    protected List<MaterialGame> selectMaterialGameList = new ArrayList<>();

    protected  List<LocalMedia> localMediaList = new ArrayList<>();

    protected  List<PrizeDefaultBean> nonList = new ArrayList<>();
    protected  List<PrizeDefaultBean> winList = new ArrayList<>();
    protected  List<PrizeDefaultBean> prizeDefaultBeanList = new ArrayList<>();
    protected  List<PrizeDefaultBean> selectPrizeDefaultBeanList = new ArrayList<>();
    protected PrizeDefaultBean prizeDefaultBean = new PrizeDefaultBean();
    private String isUnPrize;
    private String campaignType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prize_templet;
    }

    @Override
    protected void initView() {
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);

        tvTitle.setText("奖品代表图");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定");
    }

    @Override
    protected void initData() {
        isUnPrize = getIntent().getStringExtra("isUnPrize");


        //模板素材
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");
        campaignType = getIntent().getStringExtra("campaignType");
//        presenter.queryTigerPoster(this,userId,"",id,isUnPrize,LaohuPinTuanNewActivity.campaignType);
        materialGameList = LaohuPinTuanNextTwoActivity.materialGameList;

        if(materialGameList != null && materialGameList.size()>0){
            Object win = materialGameList.get(0).getWin();
            Object non = materialGameList.get(0).getNon();
            List<String> wins = new ArrayList<>();
            List<String> nons = new ArrayList<>();
            if(win != null){
                wins = (List<String>)win;
            }
            if(non != null){
                nons  = (List<String>)non;
            }
            List<PrizeDefaultBean> nonLists = new ArrayList<>();
            List<PrizeDefaultBean> winLists = new ArrayList<>();
            if(nons != null && nons.size()>0){
                for (String id : nons){
                    PrizeDefaultBean bean = new PrizeDefaultBean();
                    bean.setId(id);
                    bean.setChecked(false);
                    nonLists.add(bean);
                }
                nonList = nonLists;
            }

            if(wins != null && !"".equals(wins)){
                for (String id : wins){
                    PrizeDefaultBean bean = new PrizeDefaultBean();
                    bean.setId(id);
                    bean.setChecked(false);
                    winLists.add(bean);
                }
                winList = winLists;
            }


        }

        setPostAdapter();



    }

    protected void setPostAdapter(){

        if("Prize8".equals(isUnPrize)){//未中奖
            prizeDefaultBeanList = nonList;

        }else {
            prizeDefaultBeanList = winList;
        }

        mAdapter = new MaterialGameListAdapter(PrizeTempletActivity.this,prizeDefaultBeanList);
        rvGrid.setAdapter(mAdapter);
        rvGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              if("Prize1".equals(isUnPrize)){ //中奖单选
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId1 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId1 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId1 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize2".equals(isUnPrize)){
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId2 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId2=  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId2 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize3".equals(isUnPrize)){
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId3 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId3 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId3 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize4".equals(isUnPrize)){
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId4 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId4 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId4 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize5".equals(isUnPrize)){
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId5 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId5 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId5 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize6".equals(isUnPrize)){
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId6 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId6 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId6 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize7".equals(isUnPrize)){ //单选
                    if(currentNum == -1){ //选中
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId7 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum == position){ //同一个item选中变未选中
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        currentNum = -1;
                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId7 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                        for(PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                            prizeDefaultBean.setChecked(false);
                        }
                        prizeDefaultBeanList.get(position).setChecked(true);
                        currentNum = position;

                        prizeDefaultBean = prizeDefaultBeanList.get(position);
                        posterId7 =  prizeDefaultBean.getId() !=null ?prizeDefaultBean.getId()+"":"";
                    }
                }else if("Prize8".equals(isUnPrize)){   //多选

                    if(currentNum == -1){ //选中
                        if(prizeDefaultBeanList.get(position).getChecked()){
                            prizeDefaultBeanList.get(position).setChecked(false);
                        }else {
                            prizeDefaultBeanList.get(position).setChecked(true);
                        }

                        currentNum = position;
                    }else if(currentNum == position){ //同一个item选中变未选中
                        prizeDefaultBeanList.get(position).setChecked(false);
                        currentNum = -1;
                    }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                        if(prizeDefaultBeanList.get(position).getChecked()){
                            prizeDefaultBeanList.get(position).setChecked(false);
                        }else {
                            prizeDefaultBeanList.get(position).setChecked(true);
                        }
                        currentNum = position;
                    }
                }


                mAdapter.notifyDataSetChanged();

            }
        });


    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PrizeTempletActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_right:
                 if("Prize8".equals(isUnPrize)){//未中奖
                     if(prizeDefaultBeanList != null && prizeDefaultBeanList.size()>0){
                         for (PrizeDefaultBean prizeDefaultBean : prizeDefaultBeanList){
                             LocalMedia localMedia = new LocalMedia();
                             boolean isCheck = prizeDefaultBean.getChecked();
                             if(isCheck){
                                 selectPrizeDefaultBeanList.add(prizeDefaultBean);
                                 localMedia.setPath(HttpUrls.IMAGE_ULR  + prizeDefaultBean.getId());
                                 localMedia.setCompressPath(HttpUrls.IMAGE_ULR  + prizeDefaultBean.getId());
                                 localMedia.setCompressed(true);
                                 LaohuPinTuanNextTwoActivity.selectList.add(localMedia);
                             }
                         }
                     }
                     EventBus.getDefault().post(new LhPtUnPrizeEventBus("未中奖"));

                }else if("Prize7".equals(isUnPrize)){ //安慰奖
                     if(posterId7 != null && !"".equals(posterId7)){
                         LaohuPinTuanNextTwoActivity.prizePath7 = HttpUrls.IMAGE_ULR + posterId7;
                         EventBus.getDefault().post(new LhGeEventBus(posterId7,"安慰奖"));
                     }

                }else if("Prize1".equals(isUnPrize)){ //奖项1~7
                     if(posterId1 != null&& !"".equals(posterId1)){
                         LaohuPinTuanNextTwoActivity.prizePath1 = HttpUrls.IMAGE_ULR + posterId1;
                         EventBus.getDefault().post(new LhGeEventBus(posterId1,"奖项一"));
                     }

                }else if("Prize2".equals(isUnPrize)){
                     if(posterId2 != null&& !"".equals(posterId2)){
                         LaohuPinTuanNextTwoActivity.prizePath2 = HttpUrls.IMAGE_ULR + posterId2;
                         EventBus.getDefault().post(new LhGeEventBus(posterId2,"奖项二"));
                     }

                }else if("Prize3".equals(isUnPrize)){
                     if(posterId3 != null && !"".equals(posterId3)){
                         LaohuPinTuanNextTwoActivity.prizePath3 = HttpUrls.IMAGE_ULR + posterId3;
                         EventBus.getDefault().post(new LhGeEventBus(posterId3,"奖项三"));
                     }

                }else if("Prize4".equals(isUnPrize)){
                     if(posterId4 != null && !"".equals(posterId4)){
                         LaohuPinTuanNextTwoActivity.prizePath4 = HttpUrls.IMAGE_ULR + posterId4;
                         EventBus.getDefault().post(new LhGeEventBus(posterId4,"奖项四"));
                     }

                }else if("Prize5".equals(isUnPrize)){
                     if(posterId5 != null && !"".equals(posterId5)){
                         LaohuPinTuanNextTwoActivity.prizePath5 = HttpUrls.IMAGE_ULR + posterId5;
                         EventBus.getDefault().post(new LhGeEventBus(posterId5,"奖项五"));
                     }

                }else if("Prize6".equals(isUnPrize)){
                     if(posterId6 != null && !"".equals(posterId6)){
                         LaohuPinTuanNextTwoActivity.prizePath6 = HttpUrls.IMAGE_ULR + posterId6;
                         EventBus.getDefault().post(new LhGeEventBus(posterId6,"奖项六"));
                     }

                }

                ActivityUtils.finishActivity(PrizeTempletActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);

                break;



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PrizeTempletActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
