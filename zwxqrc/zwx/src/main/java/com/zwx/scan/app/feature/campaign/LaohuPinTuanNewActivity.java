package com.zwx.scan.app.feature.campaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.KeyBoardHeightUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CampaignCoupon;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.data.http.dialog.HttpUiTips;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.utils.SPObjUtil;
import com.zwx.scan.app.widget.MoreTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class LaohuPinTuanNewActivity extends BaseActivity<CampaignsContract.Presenter> implements CampaignsContract.View, View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.edt_name)
    protected EditText edtName;


    @BindView(R.id.tv_select_store_left_title)
    protected TextView tvSelectStoreLeftTitle;

    @BindView(R.id.tv_select_shop)
    protected TextView tvSelectStore;

    @BindView(R.id.iv_brand)
    protected ImageView ivBrand;
    @BindView(R.id.tv_store_name)
    protected MoreTextView tvStoreNames;
    //日期
    @BindView(R.id.tv_start_time)
    protected TextView tvStartTime;
    @BindView(R.id.ll_start)
    protected LinearLayout llStart;

    @BindView(R.id.tv_end_time)
    protected TextView tvEndTime;
    @BindView(R.id.ll_end)
    protected LinearLayout llEnd;

    @BindView(R.id.btn_next)
    protected Button btnNext;


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

    PopWindow popWindow = null;
    private String title = "";
    protected  List<Store> storeList = new ArrayList<>();

    protected  List<Store> selectedStoreList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    protected CampaignSelectStoreAdapter myAdapter;
    protected static String campaignType;
    private String userId;
    protected static String storeName;
    protected static String storeId;
    private String compId;
    //选择多个店铺的品牌位置
    private int brandPostion;
    protected String brandLogo;
    private String storeIdArray;
    private String storeNameArray;
    protected String brandLogoCampaign;
    private String isShowOneStore;

    protected static String storeIdCampaignType = "A";

    protected TimePickerView pvCustomTime;

    private boolean isStartDate;


    protected String campaignName;
    protected String startDate;
    protected String endDate;

    private boolean isCampaignAndCouponStore = true;

    //活动店铺类型 全部店铺 自营 加盟
    public static String campaignStoreSelectType;

    public static Campaign campaign = new Campaign();

//    public static CampaignGame campaignGame = new CampaignGame();
    public static String storeStr = "";

    private String isEditCampaign = "NO";

    private String isCopyCreate = "YES";
    private String campaignId;
    protected static String posterId;
    protected static String backGroundId;

    protected static String miniLinkIcon; //小程序小图标

    protected static String intentTemplet;  //从选择模板开始进入

    protected static String compaignStatus;   //活动状态

    protected static ArrayList<CampaignCoupon> campaignCouponList1 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList2 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList3 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList4 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList5 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList6 = new ArrayList<>();
    protected static ArrayList<CampaignCoupon> campaignCouponList7 = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_laohu_pin_tuan;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");

        //活动类型
        campaignType = getIntent().getStringExtra("campaignType");
        //是否编辑还是新建
        isEditCampaign = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");


        compaignStatus = getIntent().getStringExtra("compaignStatus");
        intentTemplet = getIntent().getStringExtra("intentTemplet");
        if("YES".equals(intentTemplet)){  //新建选择模板跳进来
            intentTemplet = getIntent().getStringExtra("intentTemplet");
            posterId = getIntent().getStringExtra("posterId");
            backGroundId = getIntent().getStringExtra("backGroundId");
        }

        tvTitle.setText(title);
        edtName.setSelection(edtName.getText().toString().trim().length());
        edtName.setFilters(new InputFilter[]{inputFilter});
        tvSelectStoreLeftTitle.setText(getResources().getString(R.string.select_join_campaign_store));
        tvSelectStore.setText("选择参与店铺");
        DaggerCampaignsComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .campaignsModule(new CampaignsModule(this))
                .build()
                .inject(this);
    }


    InputFilter inputFilter=new InputFilter() {

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher=  pattern.matcher(charSequence);
            if(!matcher.find()){
                return null;
            }else{
//                Toast.makeText(MyApplication.context, "非法字符！", Toast.LENGTH_SHORT).show();
                return "";
            }

        }
    };

    @Override
    protected void initData() {

        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");
//        storeIdArray = SPUtils.getInstance().getString("storeIdArrayLHPTOne");
//        storeNameArray = SPUtils.getInstance().getString("storeNameArrayLHPTOne");

//        brandLogoCampaign = SPUtils.getInstance().getString("brandLogoLHPTOne");

//        brandPostion = SPUtils.getInstance().getInt("brandPostionLHPTOne");
        if ("YES".equals(isEditCampaign)) {
        //编辑页面复制并创建
            tvRight.setVisibility(View.VISIBLE);
          /*  storeIdCampaignType = SPUtils.getInstance().getString("storeIdTypeLHPTOne");
            if(storeIdCampaignType == null  || "".equals(storeIdCampaignType)){
                storeIdCampaignType = "H";
            }*/
            // //编辑
            if ("YES".equals(isCopyCreate)) {
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            } else {
                tvRight.setVisibility(View.GONE);
            }
            campaignId = getIntent().getStringExtra("campaignId");
            if (compaignStatus != null && !"".equals(compaignStatus)) {
                if ("S".equals(compaignStatus) || "NEW".equals(compaignStatus)) {
                    tvSelectStore.setVisibility(View.VISIBLE);
                    edtName.setEnabled(true);
                    llStart.setEnabled(true);
                    llEnd.setEnabled(true);
                } else {
                    tvSelectStore.setVisibility(View.GONE);
                    edtName.setEnabled(false);
                    llStart.setEnabled(false);
                    llEnd.setEnabled(false);
                }
            }


        } else { //新建
            tvRight.setVisibility(View.GONE);

        }

        //复制并创建缓存获取
        if (campaign != null) {
//                campaign =  (Campaign)SPUtils.getInstance().getData("campaign",new Campaign());
            campaignName = campaign.getCampaignName();
            startDate = campaign.getBeginDate();
            endDate = campaign.getEndDate();

            edtName.setText(campaignName != null ? campaignName : "");
            if(startDate!=null && !"".equals(startDate)){
                tvStartTime.setText(startDate.replace("-",".").substring(0,16));
            }else {
                tvStartTime.setText("");
            }
            if(endDate!=null && !"".equals(endDate)){
//                    edtEnd.setText(TimeUtils.date2String(TimeUtils.string2Date(endDate),new SimpleDateFormat("yyyy-MM-dd HH:mm")).replace("-","."));
                tvEndTime.setText(endDate.replace("-",".").substring(0,16));
            }else {
                tvEndTime.setText("");
            }

        }
        if(!"NEW".equals(compaignStatus)){
            presenter.doEditForGame(this, campaignId);
        }
        /*if (storeNameArray != null && storeNameArray.length() > 0) {
            storeName = storeNameArray;

        } else {
            storeIdCampaignType = "A";
            storeName = "#全部店铺";
        }*/

        if ("A".equals(storeIdCampaignType)) {
            storeName = "#全部店铺";
        } else if ("D".equals(storeIdCampaignType)) {
            storeName = "#全部自营";
        } else if ("J".equals(storeIdCampaignType)) {
            storeName = "#全部加盟";
        } else{

            if(storeName != null && !"".equals(storeName)){
//                tvStoreNames.setText(storeName);
                storeStr = storeId;
            }else {
                storeIdCampaignType = "A";
                storeName = "#全部店铺";
            }

        }
        tvStoreNames.setText(storeName);
        tvStoreNames.refreshText();
        //设置品牌logo

        brandLogo = SPUtils.getInstance().getString("brandLogo");
        setBrandLogo();
        presenter.queryStoreList(this, userId, true, true);

        //初始化日期
        initDatePicker();
        //监听活动名称内容变化 光标在最后位置
        setEditName();

        setSetTep();


    }


    private void setSetTep() {

        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivTwo.setBackgroundResource(R.drawable.ic_two_untclick);
        ivEllipsisTwo.setBackgroundResource(R.drawable.ic_ellipsis_gray);
        ivThree.setBackgroundResource(R.drawable.ic_three_untclick);
    }


    private void setEditName() {
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                campaignName = edtName.getText().toString().trim();

                if (campaignName.length() > 0) {
                    edtName.setSelection(campaignName.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    /*    edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    KeyboardUtils.showSoftInput(edtName);
                }else {
                    KeyboardUtils.hideSoftInput(edtName);
                }
            }
        });*/
    }

    @OnClick({R.id.iv_back, R.id.tv_right, R.id.tv_select_shop, R.id.ll_start, R.id.ll_end, R.id.btn_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:


                ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;
            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));
                campaignName = edtName.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();
                edtName.setEnabled(true);
                llStart.setEnabled(true);
                llEnd.setEnabled(true);
                tvSelectStore.setVisibility(View.VISIBLE);
                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "NO";
                compaignStatus = "NEW";

                edtName.setText("");

                campaign.setCampaignId(null);
                campaign.setClient(2);
                campaign.setCampaignType(campaignType);
                campaign.setCompId(Long.parseLong(compId));
                campaign.setCampaignName("");  //置空
                campaign.setBeginDate(startDate!=null&&!"".equals(startDate)?startDate.replace(".","-")+ ":00":"");
                campaign.setEndDate(endDate!=null&&!"".equals(endDate)?endDate.replace(".","-")+":59":"");
                campaignStoreSelectType = storeIdCampaignType;
                //活动
                storeStr = storeId;
                tvRight.setVisibility(View.GONE);

                break;

            case R.id.tv_select_shop:
                if(storeList != null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryStoreList(this, userId, false, true);
                }


                break;
            case R.id.ll_start:  //开始时间
                KeyboardUtils.hideSoftInput(edtName);
                isStartDate = true;
                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                String startDate = tvStartTime.getText().toString().trim();
                if(startDate != null && !"".equals(startDate)){
                    startDate= startDate + ":00";
                    Date date = TimeUtils.stringDateYMDHMS(startDate.replace(".","-"));
                    selectedDate.setTime(date);
                    String h = startDate.substring(11,13);
                    String m = startDate.substring(14,16);
                    int  hour = 0;
                    int mi = 0;
                    if(h != null && !"".equals(h)){
                        hour = Integer.parseInt(h);
                    }
                    if(m != null && !"".equals(m)){
                        mi = Integer.parseInt(m);
                    }

                    selectedDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH), hour, mi, 0);
                }else {
                    selectedDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                }
                pvCustomTime.setDate(selectedDate);
                pvCustomTime.show();
                break;
            case R.id.ll_end: //结束时间
                KeyboardUtils.hideSoftInput(edtName);
                isStartDate = false;
                Calendar  selectedEndDate = Calendar.getInstance();//系统当前时间
                String endDate = tvEndTime.getText().toString().trim();
                if(endDate != null && !"".equals(endDate)){
                    endDate= endDate + ":00";
                    Date date = TimeUtils.stringDateYMDHMS(endDate.replace(".","-"));
                    selectedEndDate.setTime(date);
                    String h = endDate.substring(11,13);
                    String m = endDate.substring(14,16);
                    int  hour = 23;
                    int mi = 59;
                    if(h != null && !"".equals(h)){
                        hour = Integer.parseInt(h);
                    }
                    if(m != null && !"".equals(m)){
                        mi = Integer.parseInt(m);
                    }

                    selectedEndDate.set(selectedEndDate.get(Calendar.YEAR), selectedEndDate.get(Calendar.MONTH), selectedEndDate.get(Calendar.DAY_OF_MONTH), hour, mi, 00);
                }else {
                    selectedEndDate.set(selectedEndDate.get(Calendar.YEAR), selectedEndDate.get(Calendar.MONTH), selectedEndDate.get(Calendar.DAY_OF_MONTH), 23, 59, 00);
                }


                pvCustomTime.setDate(selectedEndDate);
                pvCustomTime.show();
                break;
            case R.id.btn_next: //下一步
                Intent intent = new Intent(LaohuPinTuanNewActivity.this, LaohuPinTuanNextTwoActivity.class);
                campaignName = edtName.getText().toString().trim();
                startDate = tvStartTime.getText().toString().trim();
                endDate = tvEndTime.getText().toString().trim();

                campaign.setClient(2);
                campaign.setCampaignType(campaignType);
                campaign.setCompId(Long.parseLong(compId));
                campaign.setCampaignName(campaignName);
                campaign.setBeginDate(startDate != null && startDate.length() > 0 ? startDate.replace(".", "-") + ":00": "");
                campaign.setEndDate(endDate != null && endDate.length() > 0 ? endDate.replace(".", "-") +  ":59" : "");
                campaignStoreSelectType = storeIdCampaignType;  //活动店铺类型
                storeStr = storeId;

                intent.putExtra("isCopyCreate", isCopyCreate);
                intent.putExtra("isEditCampaign", isEditCampaign);
                //缓存本地
                SPUtils.getInstance().putData("campaign", campaign);
//                intent.putExtra("campaign",campaign);
                intent.putExtra("title", title);
                ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if("NEW".equals(compaignStatus) && "YES".equals(intentTemplet)){
            ActivityUtils.startActivity(LaoHuTempletActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        }else {
            ActivityUtils.startActivity(CampaignListActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        }*/
        ActivityUtils.startActivity(CampaignListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


    //选择多个店铺
    protected void showPopView(Context context) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store, null);
        //创建并显示popWindow
        popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(tvTitle);

        RelativeLayout rlAllStore = contentView.findViewById(R.id.rl_all);
        CheckBox cbAllStore = contentView.findViewById(R.id.cb__all);
        TextView tvAllStore = contentView.findViewById(R.id.tv_all);

        RelativeLayout rlSelfStore = contentView.findViewById(R.id.rl_self_bus);
        CheckBox cbSelfStore = contentView.findViewById(R.id.cb_self_bus);
        TextView tvSelfStore = contentView.findViewById(R.id.tv_self_bus);

        RelativeLayout rlJoinStore = contentView.findViewById(R.id.rl_all_join);
        CheckBox cbJoinStore = contentView.findViewById(R.id.cb_all_join);
        TextView tvJoinStore = contentView.findViewById(R.id.tv_all_join);

        ListView lv_left = contentView.findViewById(R.id.lv_menu);
        ListView lv_right = contentView.findViewById(R.id.lv_home);
        Button reset = contentView.findViewById(R.id.reset);
        Button confirm = contentView.findViewById(R.id.confirm);


        rlAllStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();
                String storeIdArr = "";
                if (storeList != null && storeList.size() > 0) {
                    for (Store store : storeList) {
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";

                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }
                    storeStr = "";
                    storeId = "";
                    storeName = "# 全部店铺";
                    storeIdCampaignType = "A";
                    myAdapter.notifyDataSetChanged();

                    tvStoreNames.setText(storeName);

                }
                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            }
        });

        rlSelfStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if (storeList != null && storeList.size() > 0) {
                    for (Store store : storeList) {
                        store.setChecked(false);
                        Integer statusQuo = store.getStatusQuo();
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";
                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }
                    storeStr = "";
                    storeId = "";
                    storeName = "#全部自营";
                    storeIdCampaignType = "D";
                    myAdapter.notifyDataSetChanged();

                    tvStoreNames.setText(storeName);

                }
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            }
        });
        rlJoinStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                storeStr = "";
                storeId = "";
                storeIdCampaignType = "J";
                storeName = "#全部加盟";
                myAdapter.notifyDataSetChanged();

                tvStoreNames.setText(storeName);


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storeStr = "";
                storeId = "";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                //自定义默认不选中
                if (storeList != null && storeList.size() > 0) {

                    for (Store store : storeList) {
                        store.setChecked(false);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder storeNameSb = new StringBuilder();
                StringBuilder storeNameSb2 = new StringBuilder();
                StringBuilder storeIdSb = new StringBuilder();
                String storeIdArr = "";
                String storeNameArr = "";

                if (storeList != null && storeList.size() > 0) {
                    selectedStoreList.clear();
                    if ("A".equals(storeIdCampaignType) || "D".equals(storeIdCampaignType) || "J".equals(storeIdCampaignType)) {   //全部店铺  自营  加盟
                        storeStr = "";
                        storeId = "";
                        if (branList != null && branList.size() > 0) {

                            brandLogo = HttpUrls.BRAND_LOGO_ULR + branList.get(brandPostion).getBrandLogo();
                            if (brandLogo != null) {
                                setBrandLogo();
                            }
                        }
                        popWindow.dismiss();
                    } else{
                        for (Store store : storeList) {
                            if (store != null) {
                                if (store.isChecked()) {
                                    String name = store.getStoreName() != null ? "#" + store.getStoreName() + "    " : "";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append("-");
                                    selectedStoreList.add(store);
                                }

                            }
                        }
                        tvStoreNames.setText(storeNameSb.toString());
                        tvStoreNames.refreshText();
                        String storeIdA = storeIdSb.toString();
                        if (storeIdSb != null && !"".equals(storeIdA)) {
                            storeIdArr = storeIdA.substring(0, storeIdA.length() - 1);
                        }

                        storeId = storeIdArr;
                        storeStr = storeIdArr;
                        storeName = storeNameSb.toString();
                        SPUtils.getInstance().put("storeIdTypeLHPTOne", storeIdCampaignType);
                        SPUtils.getInstance().put("storeIdArrayLHPTOne", storeIdArr);
                        SPUtils.getInstance().put("storeNameArrayLHPTOne", storeNameSb.toString());
                        if (branList != null && branList.size() > 0) {

                            brandLogo = HttpUrls.BRAND_LOGO_ULR + branList.get(brandPostion).getBrandLogo();
                            if (brandLogo != null) {
                                setBrandLogo();
                            }
                            SPUtils.getInstance().put("brandLogoLHPTOne", brandLogo);
                        }
                    }
                    popWindow.dismiss();

                }
            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this, branList);
        lv_left.setAdapter(brandListAdapter);

        if (brandPostion == -1) {
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        myAdapter = new CampaignSelectStoreAdapter(context);
        int selectItem = brandListAdapter.getSelectItem();
        if (branList != null && branList.size() > 0) {
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR + branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogoLHPTOne", brandLogo);
            myAdapter.setDatas(storeList);
        }

        lv_right.setAdapter(myAdapter);
        //判断默认选择店铺
        String storeNameStr = tvStoreNames.getTextView().getText().toString().trim();

//         storeIdCampaignType = SPUtils.getInstance().getString("storeIdCampaignType");

        if ("A".equals(storeIdCampaignType)) {
            storeStr = "";
            storeId = "";
            cbAllStore.setChecked(true);
            cbAllStore.setButtonDrawable(R.drawable.ic_correct);
            tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            if (storeList != null && storeList.size() > 0) {  //自定义默认不选中
                for (Store store : storeList) {
                    if (store != null) {
                        store.setChecked(false);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
        } else if ("D".equals(storeIdCampaignType)) {
            storeStr = "";
            storeId = "";
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbSelfStore.setChecked(true);
            cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
            tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            if (storeList != null && storeList.size() > 0) {  //自定义默认不选中
                for (Store store : storeList) {
                    if (store != null) {
                        store.setChecked(false);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
        } else if ("J".equals(storeIdCampaignType)) {
            storeStr = "";
            storeId = "";
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbJoinStore.setChecked(true);
            cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
            tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
            if (storeList != null && storeList.size() > 0) {  //自定义默认不选中
                for (Store store : storeList) {
                    if (store != null) {
                        store.setChecked(false);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
        } else {
            //分类默认不选中，自定义选中
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));
            if (storeList != null && storeList.size() > 0) {

                for (int i = 0; i < storeList.size(); i++) {
                    Store store = storeList.get(i);

                    if (storeNameStr != null) {
                        if (storeNameStr.contains(store.getStoreName())) {
                            store.setChecked(true);
                        } else {
                            store.setChecked(false);
                        }
                    }

                }
            }
        }


        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (branList != null && branList.size() > 0) {
                    if (branList.get(position) != null) {
                        brandLogo = HttpUrls.BRAND_LOGO_ULR + branList.get(position).getBrandLogo();
                        SPUtils.getInstance().put("brandLogoLHPTOne", brandLogo);
                        setBrandLogo();
                        SPUtils.getInstance().put("brandPositionLHPTOne", brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new CampaignSelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
//                        myAdapter.setDatas(storeList);
//                        myAdapter.notifyDataSetChanged();
                    }
                }

            }
        });


        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentNum == -1) { //选中
                    if (storeList.get(position).isChecked()) {
                        storeList.get(position).setChecked(false);
                    } else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                } else if (currentNum == position) { //同一个item选中变未选中
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                } else if (currentNum != position) { //不是同一个item选中当前的，去除上一个选中的

                    if (storeList.get(position).isChecked()) {
                        storeList.get(position).setChecked(false);
                    } else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }


                myAdapter.notifyDataSetChanged();
                storeIdCampaignType = "";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_deep));

            }
        });
    }


    protected void setBrandLogo() {
        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(LaohuPinTuanNewActivity.this).load(brandLogo).apply(requestOptions).into(ivBrand);
    }


    private void initDatePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        selectedDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Toast.makeText(StaffEditActivity.this, TimeUtils.date2String(date), Toast.LENGTH_SHORT).show();
                if (isStartDate) {
                    tvStartTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd HH:mm")).replace("-", "."));

                } else {
                    tvEndTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd HH:mm")).replace("-", "."));
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
                                String startDate = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();
                                if (isStartDate) {
                                    if (!startDate.isEmpty() && !endDate.isEmpty()) {
                                        Date start = TimeUtils.string2Date(startDate.replace(".", "-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".", "-"));

                                        if (start.after(end)) {  //start 大于end时 返回true; 反之 小于等于返回false
                                            tvStartTime.setText("");
                                            ToastUtils.showToast("开始时间不可大于结束时间！");
                                        } else {
                                            pvCustomTime.dismiss();
                                        }


                                    } else {
                                        pvCustomTime.dismiss();
                                    }
                                } else {


                                    if (!startDate.isEmpty() && !endDate.isEmpty()) {
                                        Date start = TimeUtils.string2Date(startDate.replace(".", "-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".", "-"));

                                        if (end.before(start)) {  //start 大于end时 返回true; 反之 小于等于返回false
                                            ToastUtils.showToast("结束时间不可小于开始时间！");
                                            tvEndTime.setText("");
                                        } else {
                                            pvCustomTime.dismiss();
                                        }


                                    } else {
                                        pvCustomTime.dismiss();
                                    }

                                }


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
                .setType(new boolean[]{true, true, true, true, true, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

}
