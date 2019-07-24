package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.tablayout.widget.MsgView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Feedback;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.cateringinfomanage.GridImageAdapter;
import com.zwx.scan.app.feature.main.MainActivity;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.ContainsEmojiEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_right)
    protected TextView tv_right;
    @BindView(R.id.feedback_msg_tip)
    protected MsgView feedback_msg_tip;

    @BindView(R.id.btn_1)
    protected Button btn_1;

    @BindView(R.id.btn_2)
    protected Button btn_2;

    @BindView(R.id.btn_3)
    protected Button btn_3;

    @BindView(R.id.btn_submit)
    protected Button btnSubmit;


    @BindView(R.id.edt_des)
    protected EditText edt_des;

    @BindView(R.id.edt_phone)
    protected EditText edt_phone;

    @BindView(R.id.rv_photo)
    protected RecyclerView rvPhoto;

    @BindView(R.id.tv_photo_num)
    protected TextView tv_photo_num;

    protected List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 5;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;

    private FullyGridLayoutManager manager;
    protected FeedGridImageAdapter adapter;

    protected String desc = "";
    protected String phone = "";

    protected Feedback  feedback = null;

    protected String type = "功能建议";

    protected String userId;

    protected String imageId = "";
//    protected List<String>  images = new ArrayList<>();


    protected Intent intent = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getResources().getText(R.string.feed_back));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("意见反馈记录");
        edt_des.setSelection(edt_des.getText().toString().trim().length());
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);


        btn_1.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
        btn_1.setTextColor(getResources().getColor(R.color.white));
        userId = SPUtils.getInstance().getString("userId");

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.doFeedFlag(this,userId,"ACTIVITY");
    }

    @Override
    protected void initData() {

        initPicAdapter();
        setEditListener();
    }


    private void setEditListener(){
        edt_des.setFilters(new InputFilter[]{new MaxTextLengthFilter(200)});
        edt_des.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int descriptionLength = s.length();
                if(edt_des.getText().toString().trim().length()>0){

                }
            }
        });
    }


    @OnClick({R.id.iv_back,R.id.btn_submit,R.id.tv_right,R.id.btn_1,R.id.btn_2,R.id.btn_3})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                intent = new Intent(FeedBackActivity.this, MainActivity.class);
                intent.putExtra("isIntentFragment","PersonalFragment");
                ActivityUtils.startActivity(intent,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                ActivityUtils.finishActivity(FeedBackActivity.class);

                break;

            case R.id.btn_submit:
                phone = edt_phone.getText().toString().trim();
                desc =  edt_des.getText().toString().trim();
                feedback = new Feedback();
                List<File> fileList = new ArrayList<>();
                if(selectList != null && selectList.size()>0){
                    for(LocalMedia localMedia1 : selectList){
                        String unPrizePath = localMedia1.getPath();
                        File file = new File(unPrizePath);
                        fileList.add(file);
                    }

                }

                if(desc ==null || "".equals(desc)){
                    ToastUtils.showShort("请输入描述您的问题");
                    return;
                }

                if(!RegexUtils.isMobileSimple(phone)){
                    ToastUtils.showShort("请输入正确联系方式");
                    return;
                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save_and_public)) {
                    if(fileList != null && fileList.size()>0){
                        presenter.uploadFiles(FeedBackActivity.this,fileList);

                    }else {

                        presenter.doFeedSave(this,type,desc,imageId,phone,userId);
                    }
                }

                break;
            case R.id.btn_1:
                btn_1.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btn_1.setTextColor(getResources().getColor(R.color.white));
                btn_2.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_2.setTextColor(getResources().getColor(R.color.color_gray_deep));
                btn_3.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_3.setTextColor(getResources().getColor(R.color.color_gray_deep));

                type = "功能建议";
                break;

            case R.id.btn_2:
                btn_1.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_1.setTextColor(getResources().getColor(R.color.color_gray_deep));
                btn_2.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btn_2.setTextColor(getResources().getColor(R.color.white));
                btn_3.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_3.setTextColor(getResources().getColor(R.color.color_gray_deep));
                type = "性能问题";
                break;

            case R.id.btn_3:
                type = "其他问题";
                btn_1.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_1.setTextColor(getResources().getColor(R.color.color_gray_deep));
                btn_2.setBackground(getResources().getDrawable(R.drawable.ic_btn_gray_unselect));
                btn_2.setTextColor(getResources().getColor(R.color.color_gray_deep));
                btn_3.setBackground(getResources().getDrawable(R.drawable.ic_btn_blue_selected));
                btn_3.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.tv_right:
                ActivityUtils.startActivity(FeedBackListActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);

                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(FeedBackActivity.this, MainActivity.class);
        intent.putExtra("isIntentFragment","PersonalFragment");
        ActivityUtils.startActivity(intent,
                R.anim.slide_in_left, R.anim.slide_out_right);
        ActivityUtils.finishActivity(FeedBackActivity.class);
    }


    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
        manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvPhoto.setLayoutManager(manager);
        adapter = new FeedGridImageAdapter(FeedBackActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        rvPhoto.setAdapter(adapter);

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
                            PictureSelector.create(FeedBackActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;

                    }
                }
            }
        });

    }

    private FeedGridImageAdapter.onAddPicClickListener onAddPicClickListener = new FeedGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            int config = PictureConfig.CHOOSE_REQUEST10;
            themeId = R.style.picture_default_style;
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
            PictureSelectionModel pictureSelector = PictureSelector.create(FeedBackActivity.this)
                    .openGallery(chooseMode).theme(themeId)
                    .maxSelectNum(maxSelectNum)
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
            pictureSelector.maxSelectNum(5);
            pictureSelector.forResult(config);
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST10: //奖项一
                    selectList = PictureSelector.obtainMultipleResult(data);

                /*    if(selectList != null && selectList.size()>0){
                        for(LocalMedia localMedia1 : selectList){

                            String unPrizePath = localMedia1.getPath();
                            if(!unPrizePath.contains("get.do?id=")){
                                if(unPrizePath != null && !"".equals(unPrizePath)){
                                    File file = new File(unPrizePath);
                                    fileList.add(file);
                                }
                            }else {
                                String id = unPrizePath.substring(unPrizePath.indexOf("get.do?id=")+10,unPrizePath.length());
                            }
                        }
                    }*/
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    tv_photo_num.setText(selectList.size()+"/5");
                    break;
            }
        }
    }
}
