package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.LaohuPinTuanNextTwoActivity;
import com.zwx.scan.app.feature.campaign.PrizeGridImageAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.Address;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AddressBean;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AreaPickerView;
import com.zwx.scan.app.widget.AreaTwoPickerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : lizhilong
 * time   : 2019/05/13
 * desc   : 企业认证信息
 * version: 1.0
 **/

public class TradeDrawingEnterpriseAuthOneActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener  {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;
    @BindView(R.id.iv_ellipsis_two)
    protected ImageView ivEllipsisTwo;
    @BindView(R.id.iv_three)
    protected ImageView ivThree;

    @BindView(R.id.ll_bound_card)
    protected LinearLayout ll_bound_card;

    @BindView(R.id.edt_enterprise_name)
    protected EditText edt_enterprise_name;

    //营业执照
    @BindView(R.id.iv_business_license)
    protected ImageView iv_business_license;


    @BindView(R.id.edt_credit_code)
    protected EditText edt_credit_code;

    @BindView(R.id.edt_legal_person)
    protected EditText edt_legal_person;

    @BindView(R.id.edt_legal_phone)
    protected EditText edt_legal_phone;


    @BindView(R.id.edt_legal_id_num)
    protected EditText edt_legal_id_num;


    //身份证反
    @BindView(R.id.iv_id_num_fan)
    protected ImageView iv_id_num_fan;

    //身份证正
    @BindView(R.id.iv_id_num_zheng)
    protected ImageView iv_id_num_zheng;
    //对公账户
    @BindView(R.id.edt_duigong_account)
    protected EditText edt_duigong_account;

    //开户行名称
    @BindView(R.id.edt_bank_name)
    protected EditText edt_bank_name;
    @BindView(R.id.tv_province_city_dist)
    protected TextView tvProCityDist;


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

    private  String imgType = ""; //1表示营业执照，2 表示身份证正面 3 表示身份证反面

    protected String zzPath ; //营业执照路径

    protected String idCardZPath;//身份证正面路径
    protected String idCardFPath; //身份证反面路径


    protected List<LocalMedia> zzList = new ArrayList<>();
    protected List<LocalMedia> idCardZList = new ArrayList<>();
    protected List<LocalMedia> idCardFList = new ArrayList<>();

    protected String userId ; //
    protected String companyName ;//企业名称
    protected String businessLicenseImg ;//企业营业执照
    protected String uniCredit ;//统一社会信用代码
    protected String legalName ;//法人姓名
    protected String legalPhone ;//法人手机号

    protected String legalIds ;//法人身份证
    protected String legalIdcard ;//身份证正面
    protected String legalIdcardBack ;//身份证反面
    protected String accountNo ;//对公账户
    protected String parentBankName ;//开户行名称
    protected String province ;//开户行所在省

    protected String city ;//city开户行所在市
    protected String status ;
    protected String process ;
    protected String type;  //1 企业认证类型
    protected String dialogTitle;

    //省市区 区域
    private AreaTwoPickerView areaPickerView;
    protected List<AddressBean> addressBeans;
    private List<Address> addressList;
    private int[] i;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_drawing_enterprise_auth_one;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
        tvTitle.setText("认证信息");
        // 1 - 2 - 3步骤
        setSetTep();
        //地址
        initAddress();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        status = getIntent().getStringExtra("status");
    }

    /*
    * 步骤显示
    * **/
    private void setSetTep() {

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivTwo.setBackgroundResource(R.drawable.ic_two_untclick);
//        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
//        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);
        ivEllipsisTwo.setVisibility(View.GONE);
        ll_bound_card.setVisibility(View.GONE);
    }


    protected void setImage(String selectImgType,String path){
//        RoundedCorners roundedCorners= new RoundedCorners(8);

        RequestOptions requestOptions = new RequestOptions()
//                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_load_fail)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);

        if("1".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_business_license);
        }else if("2".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_id_num_zheng);
        }else if("3".equals(selectImgType)){
            Glide.with(this).load(path).apply(requestOptions).into(iv_id_num_fan);
        }

    }
    @OnClick({R.id.iv_back,R.id.btn_next,R.id.iv_business_license,R.id.iv_id_num_zheng,R.id.iv_id_num_fan,R.id.tv_province_city_dist})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(TradeDrawingEnterpriseAuthOneActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.iv_business_license:
                imgType ="1";
                selectImg();
                break;
            case R.id.iv_id_num_zheng:
                imgType ="2";
                selectImg();
                break;
            case R.id.iv_id_num_fan:
                imgType ="3";
                selectImg();
                break;
            case R.id.tv_province_city_dist:
                areaPickerView.setSelect(i);
                areaPickerView.show();

                break;
            case R.id.btn_next:

                companyName = edt_enterprise_name.getText().toString().trim();
//                businessLicenseImg = zzPath;

                uniCredit = edt_credit_code.getText().toString().trim();
                //法人姓名
                legalName = edt_credit_code.getText().toString().trim();
                //法人身份手机号
                legalPhone = edt_credit_code.getText().toString().trim();
                //法人身份证
                legalIds = edt_legal_person.getText().toString().trim();
                legalPhone = edt_legal_phone.getText().toString().trim();
//                legalIdcard = idCardZPath;
//                legalIdcardBack = idCardFPath;

                accountNo = edt_duigong_account.getText().toString().trim();
                parentBankName = edt_bank_name.getText().toString().trim();
//                String ss = tvProCityDist.getText().toString().trim();

//                province = "山东省";
//                city = "济南市";
                if(check()){
                    return;
                }
                presenter.setCompanyAuthInfo(TradeDrawingEnterpriseAuthOneActivity.this,userId,companyName,businessLicenseImg,uniCredit,legalName,legalIds,legalPhone,
                        legalIdcard,legalIdcardBack,accountNo,parentBankName,province,city,status);

//                ActivityUtils.startActivity(TradeDrawingPersonalAuthOneActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    protected boolean check(){

        /***
         *
         *    protected String uniCredit ;//统一社会信用代码
         protected String legalName ;//法人姓名
         protected String legalPhone ;//法人手机号

         protected String legalIds ;//法人身份证
         protected String legalIdcard ;//身份证正面
         protected String legalIdcardBack ;//身份证反面
         protected String accountNo ;//对公账户
         protected String parentBankName ;//开户行名称
         protected String province ;//开户行所在省

         protected String city ;//city开户行所在市
         */
        dialogTitle = "提示";
        if(companyName ==null || "".equals(companyName)){
            setDialog("请输入企业名称");
            return true;
        }
        if( businessLicenseImg==null || "".equals(businessLicenseImg)){
            setDialog("请选择营业执照");
            return true;
        }
        if(uniCredit ==null || "".equals(uniCredit)){
            setDialog("请输入统一社会信用代码");
            return true;
        }
        if(legalName ==null || "".equals(legalName)){
            setDialog("请输入法人姓名");
            return true;
        }
        if(legalPhone ==null || "".equals(legalPhone)){
            ToastUtils.showShort("");
            setDialog("请输入法人手机号");
            return true;
        }
        if(legalIds ==null || "".equals(legalIds)){
            setDialog("请输入法人身份证");
            return true;
        }
        if(legalIdcard ==null || "".equals(legalIdcard)){
            setDialog("请选择身份证正面");
            return true;
        }

        if(legalIdcardBack ==null || "".equals(legalIdcardBack)){
            setDialog("请选择身份证反面");
            return true;
        }
        if(accountNo ==null || "".equals(accountNo)){
            setDialog("请输入对公账户");
            return true;
        }
        if(parentBankName ==null || "".equals(parentBankName)){
            setDialog("请输入开户行名称");
            return true;
        }

        if(province ==null || "".equals(province)){
            setDialog("请选择开户行地址");
            return true;
        }
        if(city ==null || "".equals(city)){
            setDialog("请选择开户行地址");
            return true;
        }
        return false;
    }


    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText(dialogTitle);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(message);
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
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

    protected void selectImg(){

        LocalMedia localMedia = new LocalMedia();

        int config = PictureConfig.CHOOSE_REQUEST;
        if("1".equals(imgType)){
            localMedia.setPath(zzPath);
            localMedia.setCompressPath(zzPath);
            localMedia.setCompressed(true);
            zzList.add(localMedia);
            config = PictureConfig.CHOOSE_REQUEST1;
        }else if("2".equals(imgType)){
            localMedia.setPath(idCardZPath);
            localMedia.setCompressPath(idCardZPath);
            localMedia.setCompressed(true);
            idCardZList.add(localMedia);
            config = PictureConfig.CHOOSE_REQUEST2;
        }else if("3".equals(imgType)){
            localMedia.setPath(idCardFPath);
            localMedia.setCompressPath(idCardFPath);
            localMedia.setCompressed(true);
            config = PictureConfig.CHOOSE_REQUEST3;
            idCardFList.add(localMedia);
        }
        themeId = R.style.picture_default_style;
        aspect_ratio_x = 1;
        aspect_ratio_y = 1;
//     PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
        PictureSelectionModel pictureSelector = PictureSelector.create(this)
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

        pictureSelector .maxSelectNum(1);// 最大图片选择数量
        pictureSelector.selectionMode(PictureConfig.SINGLE);
        pictureSelector.forResult(config);
    }


    private void initAddress(){
        Gson gson = new Gson();

        addressList = gson.fromJson(getCityJson2(), new TypeToken<List<Address>>() {
        }.getType());

        addressBeans  = new ArrayList<>();
        if(addressList != null && addressList.size()>0){
            for (Address address : addressList){
                AddressBean addressBean = new AddressBean();

                addressBean.setLabel(address.getName());
                addressBean.setValue(address.getId());
                List<AddressBean.CityBean> cityBeanList1 = new ArrayList<>();
                List<Address.CityBean> cityBeanList2 = address.getList();
                if(cityBeanList2 != null && cityBeanList2.size()>0){
                    for (Address.CityBean cityBean2 : cityBeanList2){
                        AddressBean.CityBean cityBean1 = new AddressBean.CityBean();
                        cityBean1.setValue(cityBean2.getId());
                        cityBean1.setLabel(cityBean2.getName());

                 /*       List<AddressBean.CityBean.AreaBean> areaBeanList1 = new ArrayList<>();
                        List<Address.CityBean.AreaBean> areaBeanList2 = cityBean2.getList();
                        if(areaBeanList2 != null && areaBeanList2.size()>0){
                            for (Address.CityBean.AreaBean areaBean2 : areaBeanList2){
                                AddressBean.CityBean.AreaBean areaBean1 = new AddressBean.CityBean.AreaBean();
                                areaBean1.setLabel(areaBean2.getName());
                                areaBean1.setValue(areaBean2.getId());
                                areaBeanList1.add(areaBean1);
                            }
                            cityBean1.setChildren(areaBeanList1);
                            cityBeanList1.add(cityBean1);
                        }*/
                        cityBeanList1.add(cityBean1);
                    }
                    addressBean.setChildren(cityBeanList1);
                    addressBeans.add(addressBean);
                }

            }
        }
        areaPickerView = new AreaTwoPickerView(this, R.style.Dialog, addressBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaTwoPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i= value;
                if (value.length == 3) {
                    province = addressBeans.get(value[0]).getLabel();
//                    city = addressBeans.get(value[0]).getChildren().get(value[1]).getLabel();
                   // distName = addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel();

//                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
                    city = addressBeans.get(value[0]).getChildren().get(value[1]).getLabel();
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() );
                }else {
                    province = addressBeans.get(value[0]).getLabel();
                    city = addressBeans.get(value[0]).getChildren().get(value[1]).getLabel();
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() +  addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
                }
            }
        });
    }


    private String getCityJson2() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("auth_region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(TradeDrawingPersonalAuthOneActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {   //自定义选择图片
            LocalMedia localMedia = new LocalMedia();
            List<File> fileList1 = new ArrayList<>();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST1: //营业执照
                    // 图片选择结果回调
                    zzList = PictureSelector.obtainMultipleResult(data);
                    if (zzList != null && zzList.size() > 0) {
                        localMedia = zzList.get(0);
                        zzPath = localMedia.getCompressPath();
                        File file = new File(zzPath);
                        fileList1.add(file);
                    }
//                    pagerAdapter.updateData(prizeBeanList);
                    if (fileList1 != null && fileList1.size() > 0) {
                        presenter.uploadFiles(TradeDrawingEnterpriseAuthOneActivity.this, fileList1, imgType);
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST2:  //身份证 正面
                    // 图片选择结果回调
                    idCardZList = PictureSelector.obtainMultipleResult(data);
                    if (idCardZList != null && idCardZList.size() > 0) {
                        localMedia = idCardZList.get(0);
                        idCardZPath = localMedia.getCompressPath();
                        File file = new File(idCardZPath);
                        fileList1.add(file);
                    }
                    if (fileList1 != null && fileList1.size() > 0) {
                        presenter.uploadFiles(TradeDrawingEnterpriseAuthOneActivity.this, fileList1, imgType);
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST3:  //身份证 反面
                    // 图片选择结果回调
                    idCardFList = PictureSelector.obtainMultipleResult(data);
                    if (idCardFList != null && idCardFList.size() > 0) {
                        localMedia = idCardFList.get(0);
                        idCardFPath = localMedia.getCompressPath();
                        File file = new File(idCardFPath);
                        fileList1.add(file);
                    }
                    if (fileList1 != null && fileList1.size() > 0) {
                        presenter.uploadFiles(TradeDrawingEnterpriseAuthOneActivity.this, fileList1, imgType);
                    }
                    break;


            }
        }
    }

}
