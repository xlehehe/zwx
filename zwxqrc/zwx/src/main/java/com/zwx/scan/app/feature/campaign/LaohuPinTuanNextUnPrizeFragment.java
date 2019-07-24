package com.zwx.scan.app.feature.campaign;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.Coupon;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.cateringinfomanage.GridImageAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.StoreInfoManageActivity;
import com.zwx.scan.app.widget.downloadfile.DefaultRetryPolicy;
import com.zwx.scan.app.widget.downloadfile.DownloadRequest;
import com.zwx.scan.app.widget.downloadfile.DownloadStatusListenerV1;
import com.zwx.scan.app.widget.downloadfile.RetryPolicy;
import com.zwx.scan.app.widget.downloadfile.ThinDownloadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaohuPinTuanNextUnPrizeFragment extends BaseFragment implements View.OnClickListener,LaohuPinTuanCouponFragment.ChanageLhPtTwoLayoutLisenter {


    @BindView(R.id.rv_photo)
    protected RecyclerView rvPhoto;

    @BindView(R.id.tv_photo_num)
    protected TextView tv_photo_num;

    int selectNum = 1;
    private FullyGridLayoutManager manager;
    protected PrizeGridImageAdapter adapter;

    protected List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 6;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;


    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;

    public List<File> fileList = new ArrayList<>();


    private  CampaignNonrewardPic campaignNonrewardPic = new CampaignNonrewardPic();

    private String name;
    private String prizePath;

    private String userId;
    private String compId;
    private String id;  //素材Id

    private String isUnPrize;
    public LaohuPinTuanNextUnPrizeFragment() {
        // Required empty public constructor
    }


    public static LaohuPinTuanNextUnPrizeFragment getInstance(String name,ArrayList<Coupon> couponList,String prizePath){
        LaohuPinTuanNextUnPrizeFragment nextTwoFragment = new LaohuPinTuanNextUnPrizeFragment();
        nextTwoFragment.name = name;
        nextTwoFragment.prizePath = prizePath;
        return nextTwoFragment;

    }


  /*  @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销注册
        EventBus.getDefault().unregister(this);
    }*/

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_laohu_pin_tuan_next_un_prize;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        //注册订阅者
        EventBus.getDefault().register(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        //模板素材 选择之后返回经过onresume方法重新赋值
        userId = SPUtils.getInstance().getString("userId");
        compId = SPUtils.getInstance().getString("compId");

        if("未中奖".equals(name)){
            prizePath = LaohuPinTuanNextTwoActivity.prizePath8;
            isUnPrize = "Prize8";
        }
       /* if(!"未中奖".equals(name)){
            setImage();
        }*/


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销注册
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initData() {
        selectList = LaohuPinTuanNextTwoActivity.selectList;
        if(selectList != null && selectList.size()>0){
            selectNum = selectList.size();
        }
        tv_photo_num.setText(selectNum+"/6");
        initPicAdapter();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhPtUnPrizeEventBus event){

        if(event != null){
            name = event.getName();
            if("未中奖".equals(name)){
                prizePath = LaohuPinTuanNextTwoActivity.prizePath8;
                isUnPrize = "Prize8";
            }

            initPicAdapter();

        }
    }
   /*
    private PrizeGridImageAdapter.onAddPicClickListener onAddPicClickListener = new PrizeGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(getActivity())
                    .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE )
                    *//* .selectionMode(cb_choose_mode.isChecked() ?
                             PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选*//*
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(false)// 是否可预览视频
                    .enablePreviewAudio(false) // 是否可播放音频
                    .isCamera(false)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST8);//结果回调onActivityResult code
        }

    };
*/

    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
        manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvPhoto.setLayoutManager(manager);
        adapter = new PrizeGridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(LaohuPinTuanNextTwoActivity.selectList);
        adapter.setSelectMax(maxSelectNum);
        rvPhoto.setAdapter(adapter);

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (LaohuPinTuanNextTwoActivity.selectList.size() > 0) {
                    LocalMedia media = LaohuPinTuanNextTwoActivity.selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(position, LaohuPinTuanNextTwoActivity.selectList);
                            break;

                    }
                }
            }
        });

    }

    private PrizeGridImageAdapter.onAddPicClickListener onAddPicClickListener = new PrizeGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            selectPrizeImg();
        }

    };

    private void selectPrizeImg(){
        PopWindow popWindow = new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction("从模版中选择",PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {

                        Intent intent1  = new Intent(getActivity(),PrizeTempletActivity.class);
                        if("未中奖".equals(name)){
                            intent1.putExtra("isUnPrize","Prize8");
                        }

                        ActivityUtils.startActivity(intent1,R.anim.slide_in_right,R.anim.slide_out_left);

                    }
                }))
                .addItemAction(new PopItemAction("自定义上传", PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        LocalMedia localMedia = new LocalMedia();
                        if(prizePath != null && !"".equals(prizePath)){

                        }else {
//                            prizePath = HttpUrls.IMAGE_ULR + ApiConstants.PRIZE_DEFAULT;
                            prizePath = HttpUrls.IMAGE_ULR ;
                        }

                        selectList = new ArrayList<>();
                        int config = PictureConfig.CHOOSE_REQUEST8;
                        if("未中奖".equals(name)){
                            selectList = LaohuPinTuanNextTwoActivity.selectList;
                            ;
                        }

                        themeId = R.style.picture_default_style;
                        aspect_ratio_x = 1;
                        aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
                        PictureSelectionModel pictureSelector = PictureSelector.create(getActivity())
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

                        if("未中奖".equals(name)){
                            pictureSelector .maxSelectNum(6);// 最大图片选择数量
                            pictureSelector.selectionMode(PictureConfig.MULTIPLE);
                        }
                        pictureSelector.forResult(config);

                    }
                }))

                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                .create();
        popWindow.show();
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public void chanageLayout(ArrayList<Coupon> couponList, String prizeName) {

    }
}
