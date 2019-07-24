package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.circleimageview.CircleImageView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignTitleContentActivity;
import com.zwx.scan.app.feature.campaign.PosterListActivity;
import com.zwx.scan.app.feature.personal.utils.CropUtils;
import com.zwx.scan.app.feature.personal.utils.DialogPermission;
import com.zwx.scan.app.feature.personal.utils.FileUtil;
import com.zwx.scan.app.feature.personal.utils.PermissionUtil;
import com.zwx.scan.app.feature.personal.utils.SharedPreferenceMark;
import com.zwx.scan.app.utils.MaxTextLengthFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BrandSettingActivity extends BaseActivity<CateringInfoContract.Presenter> implements CateringInfoContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.rl_avatar)
    protected RelativeLayout rlAvatar;
    @BindView(R.id.circle_view)
    protected CircleImageView avatarView;

    @BindView(R.id.tv_name)
    protected TextView tvName;
    @BindView(R.id.edt_content)
    protected EditText edtContent;

    @BindView(R.id.btn_submit)
    protected Button btnSubmit;

    @BindView(R.id.tv_tip)
    protected TextView tvTip;

    private String brandName;
    private String content;
    private String brandLogo;
    int contetnLenth = 0;

    //相册
    private File file;
    private Uri uri;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;

    protected  String imageId ;

    protected Brand brand;
    private int count = 0;

    private List<String> imageUrlList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand_setting;
    }

    @Override
    protected void initView() {
        tvTitle.setText("品牌信息管理");
        DaggerCateringInfoComponent.builder().applicationComponent(AppContext.getApplicationComponent())
                .cateringInfoModule(new CateringInfoModule(this))
                .build().inject(this);


        initFileDirectory();
    }

    protected void setBrandLogo(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(avatarView);
    }

    @Override
    protected void initData() {

        brand = (Brand) getIntent().getSerializableExtra("brand");
        edtContent.setSelection(edtContent.getText().toString().length());
        if(brand != null){
            tvName.setText(brand.getName()!=null?brand.getName():"");

            brandLogo = HttpUrls.BRAND_LOGO_ULR+brand.getLogo();
            edtContent.setText(brand.getStory() != null ? brand.getStory() : "");
            if(brand.getStory()!= null && brand.getStory().length()>0){
                contetnLenth = brand.getStory().length();

                tvTip.setText(contetnLenth+"/500");
            }


            setBrandLogo();
        }
//        edtName.setSelection(edtName.getText().toString().trim().length());
        edtContent.setSelection(edtContent.getText().toString().trim().length());
        edtContent.setFilters(new InputFilter[]{new MaxTextLengthFilter(500)});
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                content = edtContent.getText().toString();
                if(content.length()>500){
                    ToastUtils.showCustomShort(R.layout.layout_custom_dialog_text,getResources().getString(R.string.more_500));

                    return;
                }
                count = content.length();
                tvTip.setText(count+"/500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    @OnClick({R.id.iv_back,R.id.btn_submit,R.id.rl_avatar})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.iv_back:
                ActivityUtils.finishActivity(BrandSettingActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.btn_submit:

                brandName = tvName.getText().toString().trim();
                content = edtContent.getText().toString().trim();
                brandLogo = file.getPath();
                if(brand == null){
                    brand = new Brand();
                }

                if(imageId != null && imageId.length()>0){
                    brand.setLogo(imageId);
                }

                brand.setStory(content);
                brand.setName(brandName);
//                intent = new Intent(BrandSettingActivity.this,CateringinfoManageActivity.class);
                presenter.saveBrand(this,brand);
              /*  setResult(CateringinfoManageActivity.RESULT_CODE,intent);
                finish();*/

                break;

            case R.id.rl_avatar:
                uploadAvatarFromAlbumRequest();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(BrandSettingActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void initFileDirectory(){
        file = new File(FileUtil.getCachePath(this), "android-brand-logo.jpg");


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if(file.exists()){
                uri = Uri.fromFile(file);
            }

        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要这样的方法跨应用访问)
            if(file.exists()){
                uri = FileProvider.getUriForFile(AppContext.getInstance(), "com.yf.useravatar", file);
            }

        }

    }


    private void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri newUri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                startPhotoZoom(newUri);
            } else {
                ToastUtils.showShort("没有得到相册图片");
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            startPhotoZoom(uri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            uploadAvatarFromPhoto();
        }
    }

    /**
     * 裁剪拍照裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 400);//图片输出大小
        intent.putExtra("outputY", 400);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    private void uploadAvatarFromPhoto() {
//        compressAndUploadAvatar(file.getPath());
        final File cover = FileUtil.getSmallBitmap(this, file.getPath());
        String flag = "brandEdit";
        presenter.upload(this,file,flag);
        Uri uri = Uri.fromFile(cover);
//        avatar.setBackgroundResource();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(uri).apply(requestOptions).into(avatarView);

    }

    /*private void compressAndUploadAvatar(String fileSrc) {
        final File cover = FileUtil.getSmallBitmap(this, fileSrc);
//        String mimeType = "image/*";
//        requestBody = RequestBody.create(MediaType.parse(mimeType), file);
//        String fileName = cover.getName();
//        photo = MultipartBody.Part.createFormData("portrait", fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()), requestBody);
        //Fresco设置圆形头像
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setDesiredAspectRatio(1.0f)
                .setFailureImage(R.mipmap.ic_launcher)
                .setRoundingParams(RoundingParams.fromCornersRadius(100f))
                .build();

        //加载本地图片
        Uri uri = Uri.fromFile(cover);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mSimpleDraweeView.getController())
                .setUri(uri)
                .build();
        mSimpleDraweeView.setHierarchy(hierarchy);
        mSimpleDraweeView.setController(controller);

    }*/
    /**
     * photo
     */
    private void uploadAvatarFromPhotoRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PermissionUtil.REQUEST_SHOWCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    uploadAvatarFromPhotoRequest();

                } else {
                    if (!SharedPreferenceMark.getHasShowCamera()) {
                        SharedPreferenceMark.setHasShowCamera(true);
                        new DialogPermission(this, "关闭摄像头权限影响扫描功能");

                    } else {
                        ToastUtils.showShort("未获取摄像头权限");
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
