package com.zwx.scan.app.feature.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackDetailActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener  {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tv_right;
    @BindView(R.id.tv_type)
    protected TextView tv_type;

    @BindView(R.id.tv_content)
    protected TextView tv_content;
    @BindView(R.id.tv_time)
    protected TextView tv_time;
    @BindView(R.id.rv_photo)
    protected RecyclerView rv_photo;


    @BindView(R.id.tv_creater)
    protected TextView tv_creater;
    @BindView(R.id.tv_tel)
    protected TextView tv_tel;

    @BindView(R.id.tv_result)
    protected TextView tv_result;

    @BindView(R.id.tv_result_time)
    protected TextView tv_result_time;
    @BindView(R.id.ll_result)
    protected LinearLayout ll_result;


    private String feedbackId;
    protected String content;
    protected String phone;
    protected String imageIds;
    protected String createTime;
    protected String createName;
    //处理结果
    protected String dealResult;
    protected String dealTime;
    protected String name;
    protected FullyGridLayoutManager manager;
    protected FeedDetailImageAdapter adapter;
    protected List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 6;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back_detail;
    }

    @Override
    protected void initView() {

        tvTitle.setText(getResources().getText(R.string.feed_back_msg_detail));
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);

        feedbackId = getIntent().getStringExtra("feedbackId");
        initPicAdapter();
        presenter.doFeedQuery(this,feedbackId);
    }

    @Override
    protected void initData() {


    }

    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
//        manager = new FullyGridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
//        rv_photo.setLayoutManager(manager);
        adapter = new FeedDetailImageAdapter(FeedBackDetailActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        rv_photo.setAdapter(adapter);

        adapter.setOnItemClickListener(new FeedGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(FeedBackDetailActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;

                    }
                }
            }
        });

    }

    private FeedDetailImageAdapter.onAddPicClickListener onAddPicClickListener = new FeedDetailImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
           /* int config = PictureConfig.CHOOSE_REQUEST10;
            themeId = R.style.picture_default_style;
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
            PictureSelectionModel pictureSelector = PictureSelector.create(FeedBackDetailActivity.this)
                    .openGallery(chooseMode).theme(themeId)
                    .minSelectNum(1).imageSpanCount(4).previewImage(true)// 是否可预览图片
                    .previewVideo(false)// 是否可预览视频
                    .enablePreviewAudio(false).isCamera(false)// 是否显示拍照按钮
                    .isZoomAnim(true).enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true).glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList).minimumCompressSize(100);// 小于100kb的图片不压缩

            pictureSelector.forResult(config);*/
        }

    };


    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(FeedBackDetailActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(FeedBackDetailActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
