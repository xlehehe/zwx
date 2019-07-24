package com.zwx.scan.app.feature.personal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.personal.utils.CropUtils;
import com.zwx.scan.app.feature.personal.utils.DialogPermission;
import com.zwx.scan.app.feature.personal.utils.FileUtil;
import com.zwx.scan.app.feature.personal.utils.PermissionUtil;
import com.zwx.scan.app.feature.personal.utils.SharedPreferenceMark;
import com.zwx.scan.app.widget.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalAccountManageActivity extends BaseActivity<PersonalContract.Presenter> implements PersonalContract.View,View.OnClickListener{

    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


//    @BindView(R.id.circle_view)
//    protected CircleImageView avatar;
    @BindView(R.id.tv_account)
    protected TextView tvAccount;
    @BindView(R.id.edt_name)
    protected EditText edtName;
    @BindView(R.id.tv_psd)
    protected TextView tvPsd;
    @BindView(R.id.list_store)
    protected MyListView listViewStore;
    @BindView(R.id.list_role)
    protected MyListView listRole;

    @BindView(R.id.rl_psd)
    protected RelativeLayout rlPsd;


    PersonalRolesAdapter rolesAdapter;
    PersonalStoresAdapter storesAdapter;
    List<Role>  roleList = new ArrayList<>();
    List<Store>  storeList = new ArrayList<>();
    private String userId;
    private String name;

    protected String isShowPsd = "YES";

    //相册
    private File file;
    private Uri uri;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initView() {
        DaggerPersonalComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);

        tvTitle.setText(getResources().getText(R.string.title_account_manage));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getText(R.string.save));

        edtName.setSelection(edtName.getText().toString().length());
        setEdtNameText();
        setRolesAdapter();
        initFileDirectory();
    }

    @Override
    protected void initData() {
//        setStoresAdapter();
        userId = SPUtils.getInstance().getString("userId");
        isShowPsd = getIntent().getStringExtra("isShowPsd");

        if("YES".equals(isShowPsd)){
            rlPsd.setVisibility(View.VISIBLE);
            edtName.setText("");
            tvAccount.setText("");
            edtName.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_next),null);
            tvRight.setVisibility(View.VISIBLE);
        }else {
            rlPsd.setVisibility(View.GONE);
            edtName.setCompoundDrawables(null,null,null,null);
            tvRight.setVisibility(View.GONE);
        }
        presenter.queryUser(this,userId);

    }

    protected void setStoresAdapter(){


        storesAdapter = new PersonalStoresAdapter(this,storeList);
        listViewStore.setAdapter(storesAdapter);
    }

    protected void setRolesAdapter(){
        rolesAdapter = new PersonalRolesAdapter(this,roleList);
        listRole.setAdapter(rolesAdapter);
    }


    @OnClick({R.id.iv_back,R.id.rl_psd,R.id.ll_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(PersonalAccountManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;

            case R.id.ll_right:

                String name = edtName.getText().toString().trim();
                String operateFlag = "1";
                presenter.updateUser(this,userId,name,operateFlag);
                break;

            case R.id.rl_psd:
                ActivityUtils.startActivity(PasswordManageActivity.class,
                        R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(PersonalAccountManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void setEdtNameText(){
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = edtName.getText().toString().trim();

                if(name.length()>0){
                    String operateFlag = "1";
                    presenter.updateUser(PersonalAccountManageActivity.this,userId,name,operateFlag);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initFileDirectory(){
        file = new File(FileUtil.getCachePath(this), "user-avatar.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要这样的方法跨应用访问)
            uri = FileProvider.getUriForFile(AppContext.getInstance(), "com.yf.useravatar", file);
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
//        intent.putExtra("outputX", 400);//图片输出大小
//        intent.putExtra("outputY", 400);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    private void uploadAvatarFromPhoto() {
//        compressAndUploadAvatar(file.getPath());
        final File cover = FileUtil.getSmallBitmap(this, file.getPath());
        Uri uri = Uri.fromFile(cover);
//        avatar.setBackgroundResource();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

//        Glide.with(this).load(uri).apply(requestOptions).into(avatar);

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
