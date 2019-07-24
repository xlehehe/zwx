package com.zwx.scan.app.feature.staffmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.CardQrcBean;
import com.zwx.scan.app.data.bean.DirectoryData;
import com.zwx.scan.app.data.bean.Member;
import com.zwx.scan.app.data.bean.StaffQRCode;
import com.zwx.scan.app.data.bean.StaffWork;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.user.LoginActivity;
import com.zwx.scan.app.feature.verification.VerificationActivity;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.dialog.DialogUIUtils;
import com.zwx.scan.app.widget.dialog.bean.TieBean;
import com.zwx.scan.app.widget.dialog.listener.DialogUIItemListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author lizhilong
 * @date 2018 -12-7
 * */
public class StaffEditActivity extends BaseActivity<StaffManageContract.Presenter> implements StaffManageContract.View,View.OnClickListener,View.OnFocusChangeListener{

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    @BindView(R.id.edt_name)
    protected EditText edtName;

    @BindView(R.id.edt_phone)
    protected EditText edtPhone;

    @BindView(R.id.tv_sex)
    protected TextView tvSex;
    @BindView(R.id.tv_birthday)
    protected TextView tvBirthday;
    @BindView(R.id.tv_entry)
    protected TextView tvEntry;

    @BindView(R.id.tv_position)
    protected TextView tvPosition;
    @BindView(R.id.tv_leave)
    protected TextView tvLeave;
    @BindView(R.id.tv_qrc)
    protected TextView tvQrc;

    @BindView(R.id.rl_leave)
    protected RelativeLayout rlLeave;
    @BindView(R.id.rl_sex)
    protected RelativeLayout rl_sex;

    @BindView(R.id.rl_birthday)
    protected RelativeLayout rl_birthday;

    @BindView(R.id.rl_entry)
    protected RelativeLayout rl_entry;

    @BindView(R.id.rl_position)
    protected RelativeLayout rl_position;
    @BindView(R.id.rl_quitday)
    protected RelativeLayout rlQuitday;

    @BindView(R.id.rl_qrc)
    protected RelativeLayout rl_qrc;
    @BindView(R.id.ll_iv_qrc)
    protected LinearLayout ll_iv_qrc;


    @BindView(R.id.tv_quitday)
    protected TextView tvQuitday;

    @BindView(R.id.btn_submit)
    protected Button btnSubmit;

    private String name;
    private String phone;

    public String sex;

    private String entry;
    //职位
    private String position;
    //职位id
    private String positionId;
    //二维码id
    private  String pullNewCode = "";
    //生日
    private String birthday;
    //离职日期
    private String quitday = "";

    private String joinday;

    protected String memberId;
    protected String storeId;
    protected String staffId;

    private  boolean isEntryTime = true;
    public Member member = new Member();

    protected List<StaffQRCode> staffQRCodeList = new ArrayList<>();

    protected List<DirectoryData> directoryDataList = new ArrayList<>();
    protected List<CardBean> cardBeans = new ArrayList<>();
    protected List<CardQrcBean> cardQrcBeans = new ArrayList<>();
    protected TimePickerView  pvCustomTime;
    protected OptionsPickerView pvQrc, pvPosition;
    protected TimePickerView  pvLeaveCustomTime;

    private boolean isPhoneEdit = false;
    private boolean newStaff = false;

    //在职状态 1在职 0 离职
    private String status;

    private String staffWorkId;
    private StaffWork staffWork =new StaffWork();

    protected String isMember ;  //是否编辑或新增员工 YES表示新增 NO 表示编辑

    public   String catId = "37861829885000";
    private String sexType ;
    private String isEditStaff;
    protected String newPhoneMemberId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_staff_edit;
    }

    @Override
    protected void initView() {
        DaggerStaffManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .staffManageModule(new StaffManageModule(this))
                .build()
                .inject(this);


        memberId = getIntent().getStringExtra("memberId");
        storeId = SPUtils.getInstance().getString("storeId");
        //从列表进入编辑
        newStaff = getIntent().getBooleanExtra("isNewStaff",false);
        staffWork = (StaffWork)getIntent().getSerializableExtra("staffWork");


        edtPhone.addTextChangedListener(phoneTextWatcher);
        edtName.addTextChangedListener(userTextWatcher);

        edtName.setSelection(edtName.getText().toString().length());
        edtPhone.setSelection(edtPhone.getText().toString().length());
        edtPhone.setOnFocusChangeListener(this);
         isEditStaff = getIntent().getStringExtra("isEditStaff");
        presenter.doPosition(this,catId);
        if(newStaff){
            tvTitle.setText("员工资料");

        }else {

            //若是会员
            isMember = getIntent().getStringExtra("isMember");
            if(isMember!=null&& "YES".contains(isMember)){
                tvTitle.setText("完善员工资料");
                edtPhone.setEnabled(false);
                edtPhone.setFocusable(false);
                edtPhone.setKeyListener(null);
                edtPhone.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                tvBirthday.setText(DateUtils.formatDate(System.currentTimeMillis()));
                tvQuitday.setText(DateUtils.formatDate(System.currentTimeMillis()));
                tvEntry.setText(com.zwx.library.utils.DateUtils.formatDate(System.currentTimeMillis()));
                presenter.loadMemberById(this,memberId);
            }else if(isMember!=null&& "NO".contains(isMember)) {
                tvTitle.setText("新建员工资料");
            }else {
                edtPhone.setEnabled(true);
                tvTitle.setText("编辑员工资料");
                if(isEditStaff!=null && isEditStaff.length()>0){
                    staffId =String.valueOf( staffWork.getStaff().getId());
                    staffWorkId = String.valueOf(staffWork.getId());

                    rlLeave.setVisibility(View.VISIBLE);
                    edtPhone.setEnabled(true);
                    edtName.setText(staffWork.getStaff().getName()!= null ?staffWork.getStaff().getName():"");
                    edtPhone.setText(staffWork.getStaff().getTel()!= null ?staffWork.getStaff().getTel():"");
                    if(staffWork.getStaff().getSex()!=null && staffWork.getStaff().getSex().intValue() == 1){
                        tvSex.setText("男");
                        sex  = String.valueOf(staffWork.getStaff().getSex());
                    }else  if(staffWork.getStaff().getSex()!=null && staffWork.getStaff().getSex().intValue() == 0){
                        tvSex.setText("女");
                        sex  = String.valueOf(staffWork.getStaff().getSex());
                    }else {
                        tvSex.setText("");
                        sex = "";
                    }
                    String birts =staffWork.getStaff().getBirthday()!= null ?staffWork.getStaff().getBirthday():"";
                    if(birts!=null && !"".equals(birts)){
                        Date date = DateUtils.parse(birts,"yyyy-MM-dd");

                        birthday = DateUtils.formatDate(date,"yyyy-MM-dd");

                        tvBirthday.setText(birthday);
                    }else {
                        tvBirthday.setText(DateUtils.formatDate(System.currentTimeMillis()));
                    }

                    String join= staffWork.getJoinTime()!= null ?staffWork.getJoinTime():"";
                    if(join!=null&& !"".equals(join)){
                        Date joinDate = DateUtils.parse(join,"yyyy-MM-dd");
                        joinday =DateUtils.format(joinDate,"yyyy-MM-dd");

                        tvEntry.setText(joinday);
                    }else {
                        tvEntry.setText(DateUtils.formatDate(System.currentTimeMillis()));
                    }



                    String quit = staffWork.getQuitTime()!=null?staffWork.getQuitTime():"";

                    if(staffWork.getQuitTime()!=null && !"".equals(staffWork.getQuitTime())){
                        Date quitDate = DateUtils.parse(staffWork.getQuitTime(),"yyyy-MM-dd");
                        quitday = DateUtils.formatDate(quitDate,"yyyy-MM-dd");
                        tvQuitday.setText(quitday);

                    }else {

                        tvQuitday.setText(DateUtils.formatDate(System.currentTimeMillis()));
                    }

                    if(staffWork.getPosition()!=null){
                        if(staffWork.getPosition().getId() !=null ){
                            positionId = String.valueOf(staffWork.getPosition().getId());
                        }
                        tvPosition.setText(staffWork.getPosition().getKey()!= null ?staffWork.getPosition().getKey():"");
                    }

                    if(staffWork.getQrCode() !=null &&!"".equals(staffWork.getQrCode())){
                        if(staffWork.getQrCode().getId()!=null){
                            pullNewCode = String.valueOf(staffWork.getQrCode().getId());
                        }else {
                            pullNewCode = "";
                        }

                        tvQrc.setText(staffWork.getQrCode().getNos()!=null?staffWork.getQrCode().getNos().intValue()+"号二维码":"");
                    }else {
                        tvQrc.setText("");
                    }

                    if(staffWork.getStatus()!=null){
                         status =   String.valueOf(staffWork.getStatus());
                        if( staffWork.getStatus().intValue() == 0){
                            tvLeave.setText("是");
                            rlQuitday.setVisibility(View.VISIBLE);
                            pullNewCode = "";
                            tvQrc.setText("");
                        }else if (staffWork.getStatus().intValue() == 1){
                            tvLeave.setText("否");
                            rlQuitday.setVisibility(View.GONE);
                        }
                    }else {
                        rlQuitday.setVisibility(View.GONE);
                    }

                }

            }

        }

        initCustomTimePicker();
        initLunarPicker();
        initPositionOptionPicker();
        initQrcOptionPicker();
    }

    @Override
    protected void initData() {

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
            edtPhone.setFocusable(true);
            edtPhone.setSelection(edtPhone.getText().toString().length());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //手机号
    TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtName.setFocusable(true);
            edtName.setSelection(edtName.getText().toString().length());


        }

        @Override
        public void afterTextChanged(Editable s) {

           /* if (s.length()>11){
                int selectionEnd = edtPhone.getSelectionEnd();
                s.delete(11, selectionEnd);
            }*//*else {
                edtPhone.setEnabled(true);
            }*/

        }
    };



//    R.id.tv_sex,R.id.tv_birthday,
//    R.id.tv_entry,R.id.tv_position,R.id.tv_qrc,R.id.iv_qrc,R.id.tv_leave,R.id.tv_quitday,
    @OnClick({R.id.iv_back,R.id.edt_name,R.id.edt_phone,
            R.id.rl_sex,R.id.rl_birthday,
            R.id.rl_entry,R.id.rl_position,R.id.rl_qrc,R.id.iv_qrc,R.id.rl_leave,R.id.rl_quitday,
            R.id.btn_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

                ActivityUtils.finishActivity(StaffEditActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.rl_sex:
                setdialog();
                break;
            case R.id.rl_entry:
                isEntryTime =true;
                if (isEntryTime){
                    pvCustomTime.show();
                }

                break;
            case R.id.rl_birthday:
                isEntryTime = false;
                if (!isEntryTime){
                    pvCustomTime.show();
                }
                break;
            case R.id.rl_position:

                pvPosition.show();
                break;

            case R.id.rl_qrc:
                pvQrc.show();
                break;
          /*  case R.id.iv_qrc:
                Intent intent = new Intent(StaffEditActivity.this,VerificationActivity.class);
                intent.putExtra("ss","");
                ActivityUtils.startActivityForResult(StaffEditActivity.this,intent,1111,R.anim.slide_in_right,R.anim.slide_out_left);
                break;*/
            case R.id.rl_leave:
                setLeave();
                break;
            case R.id.rl_quitday:
                pvLeaveCustomTime.show();
                break;
            case R.id.btn_submit:

                name = edtName.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();
                birthday = tvBirthday.getText().toString().trim();
                joinday = tvEntry.getText().toString().trim();

                quitday = tvQuitday.getText().toString().trim();

                if(quitday !=null && "".equals(quitday)){

                }else {
                    quitday = "";
                }

                String sexName = tvSex.getText().toString().trim();
                position = tvPosition.getText().toString().trim();
                sexName = tvSex.getText().toString().trim();

         //韩国华 137119998935040   2029-12-11  15668468673  129077055389696  2018-12-09    4  dz

                if(TextUtils.isEmpty(name)){
                    ToastUtils.showShort("请输入姓名");
                    return;
                }

                if (TextUtils.isEmpty(birthday)){
                    ToastUtils.showShort("请选择日期");
                    return;
                }

                if(TextUtils.isEmpty(joinday)){
                    ToastUtils.showShort("请选择日期");
                    return;
                }
                if(TextUtils.isEmpty(position) && TextUtils.isEmpty(positionId)){
                    ToastUtils.showShort("请选择职位");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    ToastUtils.showShort("请输入手机号");
                    return;
                }

                if(phone.length()!=11){
                    ToastUtils.showShort("手机号长度为11位");
                    return;
                }

               /* if(!RegexUtils.isMobileExact(phone)){
                    ToastUtils.showShort("请输入正确手机号");
                    return;
                }*/
                if(TextUtils.isEmpty(sexName)&&TextUtils.isEmpty(sex)){
                    ToastUtils.showShort("请选择性别");
                    return;
                }


                if(newStaff){

                }else {


                    if(isEditStaff!=null){
                        presenter.doUpdate(this,staffWorkId,staffId,status,name,phone,positionId,sex,birthday,joinday,quitday,pullNewCode);
                    }else {

                        presenter.doInsertStaff(this,name,phone,memberId,positionId,sex,birthday,joinday,pullNewCode,storeId);


                    }
                }

                break;

        }

    }

     @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(StaffEditActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

   private  void setdialog(){
        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("男",1));
       tieBeanList.add(new TieBean("女",0));

       DialogUIUtils.showMdBottomSheet(StaffEditActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
           @Override
           public void onItemClick(CharSequence text, int position) {
                if("男".equals(tieBeanList.get(position).getTitle())){
                    sex = "1";
                }else {
                    sex = "0";
                }
                tvSex.setText(tieBeanList.get(position).getTitle());
//               ToastUtils.showShort(tieBeanList.get(position).getTitle());
           }
       }).show();

   }
    private  void setLeave(){
        List<TieBean> tieBeanList = new ArrayList<>();
        tieBeanList.add(new TieBean("是",0));
        tieBeanList.add(new TieBean("否",1));

        DialogUIUtils.showMdBottomSheet(StaffEditActivity.this, true, "", tieBeanList, 1, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                if("是".equals(tieBeanList.get(position).getTitle())){
                    status = "0";
                    tvQrc.setText("");
                    pullNewCode = "";
                    rlQuitday.setVisibility(View.VISIBLE);
                }else {
                    status = "1";
                    rlQuitday.setVisibility(View.GONE);
                }

                tvLeave.setText(tieBeanList.get(position).getTitle());
            }
        }).show();

    }




    protected void initCustomTimePicker() {

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvLeaveCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Toast.makeText(StaffEditActivity.this, TimeUtils.date2String(date), Toast.LENGTH_SHORT).show();
                tvQuitday.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvLeaveCustomTime.returnData();
                                pvLeaveCustomTime.dismiss();

                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvLeaveCustomTime.dismiss();
                            }
                        });

                    }



                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


    protected void initPositionOptionPicker() {//条件选择器初始化，自定义布局

        pvPosition = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = cardBeans.get(options1).getPickerViewText();
                positionId = cardBeans.get(options1).getId();
                tvPosition.setText(tx);
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final  ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvPosition.returnData();
                                pvPosition.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvPosition.dismiss();
                            }
                        });


                    }
                })

                .build();

        pvPosition.setPicker(cardBeans);

    }


    protected void initQrcOptionPicker() {//条件选择器初始化，自定义布局
        cardQrcBeans  = new ArrayList<>();

        pvQrc = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = cardQrcBeans.get(options1).getPickerViewText();
                pullNewCode = cardQrcBeans.get(options1).getId();

                if("0".equals(pullNewCode)){
                    pullNewCode = "";
                    tvQrc.setText("");
                }else {
                    tvQrc.setText(tx);
                }

            }
        })

                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final  ImageView ivSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ivSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvQrc.returnData();
                                pvQrc.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvQrc.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvQrc.setPicker(cardQrcBeans);

    }


    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Toast.makeText(StaffEditActivity.this, TimeUtils.date2String(date), Toast.LENGTH_SHORT).show();
                if(isEntryTime){
                    tvEntry.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));
                }else {
                    tvBirthday.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));
                }

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();

                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });

                    }



                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }



    public void setFailDialog(){
        String storeName = SPUtils.getInstance().getString("storeName");
        String tip = "该消费者，还未成为 "+storeName+" 所在品牌的会员，请加入后再进行操作";

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(tip);
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

    public void setSuccessDialog(String memberId){
        String storeName = SPUtils.getInstance().getString("storeName");
        String tip = "是否确认要将该会员添加到 "+storeName+" 的员工中";

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
//        final Dialog dialog = DialogUIUtils.showCustomAlert(this, rootView, Gravity.CENTER, true, false).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(tip);
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("加入提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            /*    Intent intent = new Intent(VerificationActivity.this,StaffEditActivity.class);
                intent.putExtra("isMember","YES");
                intent.putExtra("memberId",memberId);
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);*/


            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUIUtils.dismiss(dialog);
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);



    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = edtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入手机号");
        }

        if(!RegexUtils.isMobileExact(phone)){
            ToastUtils.showShort("请输入正确手机号");
            return;
        }
        if(v.getId() == R.id.edt_account){

            if(phone.length()>0){
                if(hasFocus){

                }else {

//                    presenter.checkMobile(StaffEditActivity.this,storeId,phone);
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean  isPull = false ;

        if(resultCode == 1111){
            if(requestCode == 1111){

                if(data != null){
                    String  newPullNewCodeId = data.getStringExtra("pullNewCodeId"); //newStaffMemberId
                    if(newPullNewCodeId!=null && !"".equals(newPullNewCodeId) ){

                        if(newPullNewCodeId!=null && !"".equals(newPullNewCodeId) ){
                            if(cardQrcBeans!=null){

                                for(CardQrcBean cardQrcBean : cardQrcBeans){
                                    if(cardQrcBean!=null){
                                        String pullNewCodeId =cardQrcBean.getId();
                                        String cardNo = cardQrcBean.getCardNo();
                                        if(pullNewCodeId !=null && !"".equals(pullNewCodeId)){
                                            if(newPullNewCodeId.equals(pullNewCodeId)) {
                                                isPull = true;
                                                pullNewCode = newPullNewCodeId;
                                                if (cardNo != null && !"".equals(cardNo)) {
                                                    tvQrc.setText(cardNo);
                                                }
                                                break;
                                            }else {
                                                pullNewCode = "";
                                                isPull = false;
                                            }

                                        }else {
                                            pullNewCode = "";
                                        }
                                    }else {
                                        pullNewCode = "";
                                    }
                                }
                            }else {
                                pullNewCode = "";
                            }

                        }else {
                            pullNewCode = "";
                        }
                        if(!isPull){
                            setQrcDialog("此二维码已被使用");
                        }
                    }else {
                        pullNewCode = "";
                    }
                }

            }
        }else {
            pullNewCode = "";

        }
    }



    public void setQrcDialog(String tip){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(tip);
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

}
