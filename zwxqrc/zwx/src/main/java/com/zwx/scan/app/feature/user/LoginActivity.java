package com.zwx.scan.app.feature.user;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.StringUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.widget.CustomEditText;

import org.apache.log4j.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.Presenter>  implements LoginContract.View, View.OnClickListener,View.OnFocusChangeListener{

    @BindView(R.id.edt_account)
    protected CustomEditText edtAccount;

    @BindView(R.id.edt_password)
    protected CustomEditText edtPsd;


    @BindView(R.id.tv_account_tip)
    public  TextView tvAccountTip;




    @BindView(R.id.tv_psd_tip)
    public  TextView tvPsdTip;


    @BindView(R.id.ll_visiable)
    protected LinearLayout llVisiable;

    @BindView(R.id.iv_visiable_psd)
    protected ImageView ivVisiablePsd;

    @BindView(R.id.cb_select)
    protected CheckBox cbSelect;

    @BindView(R.id.btn_login)
    protected Button btnLogin;



    //默认不可见
    private boolean  isVisiablePsd= true;

    private String account="";
    private String password="";

    //初始化Logger
    protected static Logger log = Logger.getLogger(LoginActivity.class);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
    /*    OkHttpUtils.getInstance()
                .init(LoginActivity.this)
                .debug(true, "okHttp")
                .timeout(20 * 1000);*/

        LogUtils.e("进入LoginActivity");
        injector();

        setListener();

        edtAccount.setSelection(edtAccount.getText().toString().length());
        edtPsd.setSelection(edtPsd.getText().toString().length());
        SPUtils.getInstance().put("isFirst",false);
        LogUtils.e("进入LoginActivity  -------------保存SPUtils"+SPUtils.getInstance());

        boolean isSave = SPUtils.getInstance().getBoolean("isSave");
        account = SPUtils.getInstance().getString("username");
        password = SPUtils.getInstance().getString("password");
        LogUtils.e("进入LoginActivity  -------------account:"+account+"--password---"+password+"----isSave--"+isSave);
        if(isSave){
            if(account!=null&&account.length()>0){
                edtAccount.setText(account);

            }

            if(password!=null&&password.length()>0){
                edtPsd.setText(password);
//                edtPsd.setFocusable(true);
//                edtPsd.setFocusableInTouchMode(true);

            }
            cbSelect.setChecked(true);

        }else {
            cbSelect.setChecked(false);
            edtAccount.setText(account);
            edtPsd.setText("");
        }

    }

    private void injector(){

        DaggerLoginComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .loginModule(new LoginModule(this)).build().inject(this);


    }


    @Override
    protected void initData() {
//        requestCodeQRCodePermissions();
        presenter.doDownloadVersion(this,"0");
       /* String updateUrl = HttpUrls.BASE_URL+HttpUrls.DOWNLOAD_VERSION;
        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(updateUrl)
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                .build()
                .update();*/
    }




    private void setListener(){
        edtAccount.addTextChangedListener(userTextWatcher);
        edtAccount.setOnFocusChangeListener(this);


        edtPsd.addTextChangedListener(passTextWatcher);
        edtPsd.setOnFocusChangeListener(this);


        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                account = edtAccount.getText().toString().trim();
                if(isChecked){


                    password = edtPsd.getText().toString().trim();
                    if(account.length()>0 ){
                        SPUtils.getInstance().put("username",edtAccount.getText().toString().trim());
                        SPUtils.getInstance().put("password",edtPsd.getText().toString().trim());
                        SPUtils.getInstance().put("isSave",true);
                    }
                    LogUtils.e("进入LoginActivity  -------cbSelect.setOnCheckedChangeListener ------保存username");

                    LogUtils.e("进入LoginActivity  -------------保存isfsfds");
                }else {
//                    SPUtils.getInstance().remove("username");
                    SPUtils.getInstance().put("username",account);
                    SPUtils.getInstance().remove("password");
                    SPUtils.getInstance().put("isSave",false);
                    LogUtils.e("进入LoginActivity  -------cbSelect.setOnCheckedChangeListener else------保存username");

                    LogUtils.e("进入LoginActivity  -------------保存isfsfds");
                }

            }
        });
    }


    @OnClick({R.id.btn_login,R.id.ll_visiable})
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_login:

                if(checkUsername()&&checkPsd()){
                    return;
                }

                account = edtAccount.getText().toString().trim();
                password = edtPsd.getText().toString().trim();
                KeyboardUtils.hideSoftInput(LoginActivity.this,edtPsd);
                presenter.login(this,account,password);
                break;
            case R.id.ll_visiable:

                if (isVisiablePsd){
                    isVisiablePsd = false;
                    ivVisiablePsd.setBackgroundResource(R.drawable.ic_password_invisible);
                    edtPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    isVisiablePsd = true;
                    ivVisiablePsd.setBackgroundResource(R.mipmap.ic_password_visible);
                    edtPsd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      ActivityUtils.finishAllActivities();
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
            if(v.getId() == R.id.edt_account){
                account = edtAccount.getText().toString().trim();
                if(account.length()>0){
                    if(hasFocus){

                    }else {
                        presenter.checkUsername(LoginActivity.this,account);
                        edtPsd.setFocusable(true);
                        edtPsd.setFocusableInTouchMode(true);

                    }
                }

            }

    }

    private boolean checkUsername(){
        account = edtAccount.getText().toString().trim();
        password = edtPsd.getText().toString().trim();

        if(StringUtils.isEmpty(account)){

            tvAccountTip.setText(getResources().getText(R.string.hint_account_empty));
            return true;
        }

        /*if(!RegexUtils.isMobileExact(account)||!RegexUtils.isUsername(account)){
            tvAccountTip.setText(getResources().getText(R.string.hint_account_error));
            return true;
        }*/



        return false;
    }


    private boolean checkPsd(){
        password = edtPsd.getText().toString().trim();




        if(!StringUtils.isEmpty(password)){
            tvPsdTip.setText(getResources().getText(R.string.hint_psd_empty));
            return false;
        }

       /* if(!RegexUtils.isPassword(password)){
            tvPsdTip.setText(getResources().getText(R.string.hint_psd_error));
            return true;
        }*/

        if(password.length()<6 ||password.length()>20){
            tvPsdTip.setText(getResources().getText(R.string.hint_psd_lenth));
            return true;
        }
        return false;
    }


    /**
     * 账号输入框的文字改变监听
     */
    TextWatcher userTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtAccount.setFocusable(true);

            if (s.length() > 0) {
                //有文字，有焦点(显示蓝色,和清除)
                if (edtAccount.hasFocus()) {
//                    EditTextListener.hasTextAndFocus(edtAccount, LoginActivity.this,  R.mipmap.ic_clear);
                    /*if(checkUsername()){

                    }*/
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    edtAccount.setDrawableVisible(false);
                    edtAccount.setSelection(edtAccount.getText().toString().length());

                }
                tvAccountTip.setVisibility(View.VISIBLE);
            } else {

                edtAccount.setDrawableVisible(false);
                tvAccountTip.setText("");
            }
//            edtAccount.setSelection(edtAccount.getText().toString().length());


        }

        @Override
        public void afterTextChanged(Editable s) {
            /*if (s.length() > 0) {
                //有文字，有焦点(显示蓝色,和清除)
                if (edtAccount.hasFocus()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            account = edtAccount.getText().toString().trim();
                            if(checkUsername()){
                                return;
                            }
                            presenter.checkUsername(LoginActivity.this,account);
                        }
                    },3000);
                }

            }*/

        }
    };



    /**
     * 密码输入框的文字改变监听
     */
    private TextWatcher passTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                //有文字，有焦点(显示蓝色,和清除)
                if (edtPsd.hasFocus()) {
//                    EditTextListener.hasTextAndFocus(edtPsd, LoginActivity.this,R.mipmap.ic_clear);
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    edtPsd.setDrawableVisible(false);
                }
                llVisiable.setVisibility(View.VISIBLE);
            } else {
                edtPsd.setDrawableVisible(false);
                llVisiable.setVisibility(View.GONE);
            }

            edtPsd.setSelection(edtPsd.getText().toString().length());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

   /* private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final int REQUEST_PERMISSION = 0;
    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//                PushManager.getInstance().initialize(getActivity().getApplicationContext(), GeTuiPushService.class);
            } else {
                LogUtils.e(TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
//                PushManager.getInstance().initialize(getActivity().getApplicationContext(), GeTuiPushService.class);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
*/


}
