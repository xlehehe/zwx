package com.zwx.scan.app.feature.accountmanage;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.Role;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.User;
import com.zwx.scan.app.data.http.ApiConstants;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.PrizeTempletActivity;
import com.zwx.scan.app.feature.staffmanage.StaffEditActivity;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.widget.ContainsEmojiEditText;
import com.zwx.scan.app.widget.MyListView;
import com.zwx.scan.app.widget.dialog.DialogUIUtils;
import com.zwx.scan.app.widget.dialog.bean.BuildBean;
import com.zwx.scan.app.widget.dialog.bean.TieBean;
import com.zwx.scan.app.widget.dialog.listener.DialogUIItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountNewActivity extends BaseActivity<AccountContract.Presenter> implements AccountContract.View,View.OnClickListener{
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.edt_name)
    protected ContainsEmojiEditText edtName;


    @BindView(R.id.edt_account)
    protected EditText edtAccount;
    @BindView(R.id.edt_psd)
    protected EditText edtPsd;

    @BindView(R.id.store_list)
    protected MyListView lvStore;
    @BindView(R.id.role_list)
    protected MyListView lvRole;
    @BindView(R.id.tv_select_store)
    protected TextView tv_select_store;

    private String userId;
    private String compId;
    protected List<Store> storeList = new ArrayList<>();
    protected List<Role> roleList = new ArrayList<>();

    protected  AccountRoleListAdapter roleListAdapter = null;
    protected  AccountStoreListAdapter storeListAdapter = null;

    protected String name;
    protected String account;
    protected String psd;

    private String title;

    protected String isNew;

    private User user = new User();

    private String page = "1";
    private String limit = "100";

    protected String storeIds;
    protected String rolesIds;
    protected String curUserId;

    protected String authFlag = "0";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_new;
    }

    @Override
    protected void initView() {
        DaggerAccountComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
        title = getIntent().getStringExtra("title");
        isNew = getIntent().getStringExtra("isNew");
//        tvTitle.setText(getResources().getString(R.string.new_account));
        tvTitle.setText(title);

//        initPicker();
        setListener();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        if("NEW".equals(isNew)){
            edtAccount.setEnabled(true);


        }else if("EDIT".equals(isNew)){
            user = (User)getIntent().getSerializableExtra("user");
            edtAccount.setEnabled(false);
            edtAccount.setCompoundDrawables(null,null,null,null);
            if(user != null){
                name = user.getNickname();
                account = user.getUsername();
                psd = user.getPassword();
                storeIds = user.getStoreIds();
                rolesIds = user.getRoleIds();
                curUserId = String.valueOf(user.getId());
                edtName.setText(name != null ?name : "");
                edtAccount.setText(account != null ?account : "");
                edtPsd.setText("******");
//                edtPsd.setText(psd != null ?psd : "");
                authFlag = user.getAuthFlag();

                if("0".equals(authFlag)){
                    tv_select_store.setText("自定义店铺");
                }else if("1".equals(authFlag)){
                    tv_select_store.setText("全部店铺");
                }


            }
        }
        presenter.listTreeByCurrentUser(this,page,limit,userId);



     edtAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             account = edtAccount.getText().toString().trim();
             if(!hasFocus){

                 if(account != null && account.length()>0){
                     presenter.checkUserNameRepeat(AccountNewActivity.this,account,userId);
                 }

             }
         }
     });

    }
    protected OptionsPickerView pvStore = null;
    protected List<CardBean> noDateBeans = null;
    protected void initPicker() {//条件选择器初始化，自定义布局


        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("自定义店铺",0));
        tieBeanList.add(new TieBean("全部店铺",1));

       DialogUIUtils.showMdBottomSheet(AccountNewActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                authFlag = String.valueOf(tieBeanList.get(position).getId());
                if("0".equals(authFlag)){

                    if(storeList != null && storeList.size()>0){
                        for (Store store : storeList){
                            store.setChecked(false);
                        }
                    }

                }else {
                    if(storeList != null && storeList.size()>0){
                        for (Store store : storeList){
                            store.setChecked(true);
                        }
                    }
                }
                storeListAdapter.notifyDataSetChanged();
                tv_select_store.setText(tieBeanList.get(position).getTitle());
            }
        }).show();

        /*noDateBeans = new ArrayList<>();
        CardBean cardBean = new CardBean();
        cardBean.setId("0");
        cardBean.setCardNo("自定义店铺");
        noDateBeans.add(cardBean);
        cardBean = new CardBean();
        cardBean.setId("1");
        cardBean.setCardNo("所有店铺");
        noDateBeans.add(cardBean);


        pvStore = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = noDateBeans.get(options1).getPickerViewText();
                String key = noDateBeans.get(options1).getId();

                if ("0".equals(key)) {
                    authFlag = key;
                    if(storeList != null && storeList.size()>0){
                        for (Store store : storeList){
                            store.setChecked(false);
                        }
                    }
                    storeListAdapter.notifyDataSetChanged();
                } else if ("1".equals(key)) {
                    authFlag = key;

                    if(storeList != null && storeList.size()>0){
                        for (Store store : storeList){
                            store.setChecked(true);
                        }
                    }

                    storeListAdapter.notifyDataSetChanged();
                }
                tv_select_store.setText(value);


            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStore.returnData();
                                pvStore.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStore.dismiss();
                            }
                        });


                    }
                })
                .build();

        pvStore.setPicker(noDateBeans);*/



    }

    @OnClick({R.id.iv_back,R.id.btn_save,R.id.tv_select_store})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(AccountNewActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);

                break;
            case R.id.tv_select_store:

//                pvStore.show();
                initPicker();
                break;
            case R.id.btn_save:

                name = edtName.getText().toString().trim();
                account = edtAccount.getText().toString().trim();
                psd = edtPsd.getText().toString().trim();

                if(name.isEmpty()){

                    ToastUtils.showShort("请输入用户姓名");
                    return;
                }


                if(account.isEmpty()){

                    ToastUtils.showShort("请输入账号");
                    return;
                }



                if("NEW".equals(isNew)){
                    if(psd.isEmpty()){

                        ToastUtils.showShort("请输入用户密码");
                        return;
                    }
                }


                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder roleIdSb = new StringBuilder();

                if(storeList != null && storeList.size()>0){
                    for (Store store:storeList){
                        if(store != null){
                            if(store.isChecked()){
                                String storeId = String.valueOf(store.getId());
                                storeIdSb.append(storeId).append("-");
                            }
                        }
                    }

                    if(storeIdSb.toString() != null && !"".equals(storeIdSb.toString())){
                       storeIds  = storeIdSb.toString().substring(0,storeIdSb.toString().length() - 1);
                    }
                }

                if(roleList != null && roleList.size()>0){
                    for (Role role:roleList){
                        if(role != null){
                            if(role.isChecked()){
                                String roleId = String.valueOf(role.getId());
                                roleIdSb.append(roleId).append("-");
                            }
                        }
                    }

                    if(roleIdSb.toString() != null && !"".equals(roleIdSb.toString())){
                        rolesIds  = roleIdSb.toString().substring(0,roleIdSb.toString().length() - 1);
                    }
                }


                if(storeIds == null || storeIds.length() == 0){
                    ToastUtils.showShort("请选择店铺");
                    return;

                }

                if(rolesIds == null || rolesIds.length() == 0){
                    ToastUtils.showShort("请选择角色");
                    return;

                }

                if("NEW".equals(isNew)){
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                        presenter.insertCateingUser(this,name,account,psd,rolesIds,storeIds,authFlag);
                    }

                }else {


                    if("******".equals(psd)){
                        psd = "";
                    }
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {
                        presenter.editCateingUser(this,curUserId,account,name,psd,rolesIds,storeIds,authFlag);
                    }

                }


                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(AccountNewActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private  void setListener(){

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = edtName.getText().toString().trim();

                edtName.setSelection(name.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                account = edtAccount.getText().toString().trim();

                edtAccount.setSelection(account.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                psd = edtPsd.getText().toString().trim();

                edtPsd.setSelection(psd.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        lvRole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    if(roleList.get(position).isChecked()){
                        roleList.get(position).setChecked(false);
                    }else {
                        roleList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    roleList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(roleList.get(position).isChecked()){
                        roleList.get(position).setChecked(false);
                    }else {
                        roleList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                roleListAdapter.notifyDataSetChanged();
            }
        });

        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        if("1".equals(authFlag)){
                            ToastUtils.showShort("权限范围，已选择所有店铺，无法取消");
                        }else {
                            storeList.get(position).setChecked(false);
                        }

                    }else {


                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    if("1".equals(authFlag)){
                        ToastUtils.showShort("权限范围，已选择所有店铺，无法取消");
                    }else {
                        storeList.get(position).setChecked(false);
                    }
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(storeList.get(position).isChecked()){
                        if("1".equals(authFlag)){
                            ToastUtils.showShort("权限范围，已选择所有店铺，无法取消");
                        }else {
                            storeList.get(position).setChecked(false);
                        }
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                storeListAdapter.notifyDataSetChanged();
            }
        });
    }

}
