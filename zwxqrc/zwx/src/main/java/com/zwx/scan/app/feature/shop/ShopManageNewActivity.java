package com.zwx.scan.app.feature.shop;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.banner.view.CustomViewPager;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.pickerview.wheelview.view.WheelView;
import com.zwx.library.popwindow.PopItemAction;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.DateUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ScreenUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.SelectConstantListViewAdapter;
import com.zwx.scan.app.data.bean.CampaignGame;
import com.zwx.scan.app.data.bean.CampaignGamesetreward;
import com.zwx.scan.app.data.bean.CampaignNonrewardPic;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.MoreStoreBean;
import com.zwx.scan.app.data.bean.ProductCategory;
import com.zwx.scan.app.data.bean.ProductExtend;
import com.zwx.scan.app.data.bean.ProductExtendDesc;
import com.zwx.scan.app.data.bean.ProductMedia;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.data.bean.ProductStore;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.UnavailableDate;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.CampaignListActivity;
import com.zwx.scan.app.feature.campaign.CampaignSelectStoreAdapter;
import com.zwx.scan.app.feature.campaign.PrizeTempletActivity;
import com.zwx.scan.app.feature.cateringinfomanage.FullyGridLayoutManager;
import com.zwx.scan.app.feature.countanalysis.MoreBrandListAdapter;
import com.zwx.scan.app.feature.member.CommonConstantBean;
import com.zwx.scan.app.feature.member.MemberCardNewActivity;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.personal.FeedBackActivity;
import com.zwx.scan.app.feature.ptmanage.PtNextTwoActivity;
import com.zwx.scan.app.feature.welcome.ViewPagerAdatper;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.richeditor.SEditorData;
import com.zwx.scan.app.widget.richeditor.SortRichEditor;
import com.zwx.scan.app.widget.webview.ZpImageUtils;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城管理->商品新增
 * version: 1.0
 **/
public class ShopManageNewActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener {
    protected static Logger log = Logger.getLogger(ShopManageNewActivity.class);
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.rv_photo)
    protected RecyclerView rv_photo;

    @BindView(R.id.edt_product_name)
    protected EditText edt_product_name;
    //商品分类
    @BindView(R.id.tv_product_cate)
    protected TextView tv_product_cate;
    //售价
    @BindView(R.id.edt_sale_price)
    protected EditText edt_sale_price;

    @BindView(R.id.edt_market_price)
    protected EditText edt_market_price;

    @BindView(R.id.edt_stock)
    protected EditText edt_stock;

    @BindView(R.id.edt_product_spec)
    protected EditText edt_product_spec;

    //返利红包
    @BindView(R.id.ll_rebate_amt)
    protected LinearLayout ll_rebate_amt;

    //是否返还红包
    @BindView(R.id.tv_rebate_amt_is)
    protected TextView tv_rebate_amt_is;

    @BindView(R.id.edt_rebate_amt)
    protected EditText edt_rebate_amt;
    @BindView(R.id.rl_rebate)
    protected RelativeLayout rl_rebate;


    @BindView(R.id.rebate_amt_tip)
    protected TextView rebate_amt_tip;

    //配送方式 到店自提
//    @BindView(R.id.tv_delivery_type)
//    protected TextView tv_delivery_type;


    //有效时间
    @BindView(R.id.tv_valid_time)
    protected TextView tv_valid_time;
    //有效固定时间
    @BindView(R.id.ll_valid_time)
    protected LinearLayout ll_valid_time;
    @BindView(R.id.tv_valid_start_time)
    protected TextView tv_valid_start_time;
    @BindView(R.id.ll_valid_start)
    protected LinearLayout ll_valid_start;

    @BindView(R.id.tv_valid_end_time)
    protected TextView tv_valid_end_time;
    @BindView(R.id.ll_valid_end)
    protected LinearLayout ll_valid_end;

    //固定时长
    @BindView(R.id.rl_valid_time)
    protected RelativeLayout rl_valid_time;

    @BindView(R.id.edt_lingqu_day)
    protected EditText edt_lingqu_day;

    @BindView(R.id.ll_select_store)
    protected LinearLayout ll_select_store;

    @BindView(R.id.tv_select_shop)
    protected TextView tv_select_shop;

    @BindView(R.id.tv_store_name)
    protected TextView tv_store_name;
    @BindView(R.id.iv_brand)
    protected ImageView iv_brand;

    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;

    //2019-05-07 暂时隐藏不展示，后期若需要可修改显示
    //开售时间
    @BindView(R.id.tv_kai_sale_time)
    protected TextView tv_kai_sale_time;
    @BindView(R.id.ll_kai_sale_custom_time)
    protected LinearLayout ll_kai_sale_custom_time;
    @BindView(R.id.tv_kai_sale_custom_time)
    protected TextView tv_kai_sale_custom_time;

    //停售时间
    @BindView(R.id.tv_stop_sale_time)
    protected TextView tv_stop_sale_time;
    @BindView(R.id.ll_stop_sale_custom_time)
    protected LinearLayout ll_stop_sale_custom_time;
    @BindView(R.id.tv_stop_sale_custom_time)
    protected TextView tv_stop_sale_custom_time;


    @BindView(R.id.edt_qishou_stock)
    protected EditText edt_qishou_stock;
//    @BindView(R.id.tv_product_remark_num)
//    protected TextView tv_product_remark_num;


    @BindView(R.id.edt_buy_know)
    protected EditText edt_buy_know;
    //g购买须知
    @BindView(R.id.tv_buy_know_num)
    protected TextView tv_buy_know_num;
    @BindView(R.id.btn_save)
    protected Button btn_save;
    //上架
    @BindView(R.id.btn_push)
    protected Button btn_push;

    @BindView(R.id.scroll_view)
    protected ScrollView scroll_view;
    @BindView(R.id.rl_bottom)
    protected RelativeLayout rl_bottom;


    //2019 -05-07 改版新增选择配送方式

    @BindView(R.id.tv_select_post_way)
    protected TextView tv_select_post_way;

    //到店自提
    @BindView(R.id.ll_ziti)
    protected LinearLayout ll_ziti;

    @BindView(R.id.tv_ziti_type)
    protected TextView tv_ziti_type;

    @BindView(R.id.iv_ziti_clear)
    protected ImageView iv_ziti_clear;


    //快递邮寄
    @BindView(R.id.ll_youji)
    protected LinearLayout ll_youji;

    @BindView(R.id.tv_youji_type)
    protected TextView tv_youji_type;

    @BindView(R.id.iv_youji_clear)
    protected ImageView iv_youji_clear;


    @BindView(R.id.tv_youji_fee_type)
    protected TextView tv_youji_fee_type;

    @BindView(R.id.rl_youji_fee)
    protected RelativeLayout rl_youji_fee;

    @BindView(R.id.edt_youji_fee)
    protected EditText edt_youji_fee;

    //商品详情
    @BindView(R.id.ll_goods_detail)
    protected LinearLayout ll_goods_detail;


    @BindView(R.id.ll_add_word_image)
    protected LinearLayout ll_add_word_image;

  /*  //2019-7-6 新增销售时间设置

    @BindView(R.id.ll_start_sale_time)
    protected LinearLayout ll_start_sale_time;

    @BindView(R.id.ll_stop_sale_time)
    protected LinearLayout ll_stop_sale_time;

    @BindView(R.id.tv_start_sale_time)
    protected TextView tv_start_sale_time;
    @BindView(R.id.tv_stop_sale_time)
    protected TextView tv_stop_sale_time;

    @BindView( R.id.tv_stop_sale_time_selector)
    protected TextView tv_stop_sale_time_selector;

    @BindView( R.id.tv_start_sale_time_selector)
    protected TextView tv_start_sale_time_selector;*/


    private PopWindow isRebateAmtPop;

    protected List<LocalMedia> selectList = new ArrayList<>();
    protected List<File> fileList = new ArrayList<>();

    protected List<LocalMedia> selectGoodDetailList = new ArrayList<>();

    protected List<File> fileGoodDetailList = new ArrayList<>();
    private int maxSelectNum = 5;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;

    private FullyGridLayoutManager manager;
    protected ShopGridImageAdapter adapter;

    protected String productName;

    protected String productCate;

    protected String salePrice;
    protected String marketPrice;
    protected String surplusStock;
    //上架商品数量
    protected String groundingCount;
    protected String productSpec;
    protected String rebateFlag = "1";  //默认是 否 不显示返利红包输入
    protected String rebateAmt;  //红包

    //分类
    protected String cateId;
    protected String cateName;
    protected String kaiSaleTimeFlag = "1";  //1 立即开售  0 自定义

    protected String stopSaleTimeFlag = "1";//1 立即开售  0 自定义

    protected String userId;
    protected String productId;
    /**
     * 邮寄类型：0-自提，1-邮寄   2 - 自提、邮寄
     */
    protected String deliveryType = "2";

    /**
     * 邮费
     */
    protected String deliveryFee;

    /**
     * 自提时间类型，R1-固定时长，A-固定时间
     */
    protected String deliveryDaytype = "R1";

    /**
     * 自提开始日期
     */
    protected String delivStartdate;

    /**
     * 自提结束日期
     */
    protected String delivEnddate;

    /**
     * 自提有效天数
     */
    protected String delivDay;

    /**
     * 销售开始日期
     */
    protected String salesStartdate;

    /**
     * 销售结束日期
     */
    protected String salesEnddate;

    /**
     * 商品详情
     */
    protected String productDetail;

    /**
     * 买家须知
     */
    protected String remark;

    /**
     * 起售数量
     */
    protected String salesCount;

    //店铺
    protected String storeId;
    protected String storeName;
    //品牌position
    private int brandPostion;
    protected String brandLogo;
    protected PopWindow popWindow;
    protected List<Store> storeList = new ArrayList<>();
    public List<MoreStoreBean.BrandStoreBean> branList = new ArrayList<>();
    protected   CampaignSelectStoreAdapter myAdapter;
    protected String storeType = "A";

    //选择日期时间
    protected TimePickerView pvCustomTime;

    protected String isSelectTime = "";
    //是否推荐给好友返还红利
    List<CommonConstantBean> isRebateAmtList = new ArrayList<>();
    List<CardBean> validTimeList = new ArrayList<>();
    List<CardBean> kaiTimeList = new ArrayList<>();
    List<CardBean> stopTimeList = new ArrayList<>();
    List<CardBean> categoryList = new ArrayList<>();

    List<CardBean> youjiList = new ArrayList<>();
    protected OptionsPickerBuilder builder = null;
    protected OptionsPickerView pvValidTime,pvKaiTime,pvStopTime,pvCategory,pvYoujiFee;

    protected TimePickerView pvValidGuDingTime;
    protected String isValidStartTime;

    protected   String isEditCampaign = "NO";
    private String isCopyCreate = "YES" ;
    /**
     * 状态 未上架——n 已下架——o 销售中——s 不再出售——c
     */
    protected static String productStatu;

    protected String productEditStatu;

    protected ProductSetNew productSetNew = new ProductSetNew();
    //商品图片
    protected List<ProductMedia> productMediaList = new ArrayList<>();
    //商品扩展
    protected ProductExtend productExtend = new ProductExtend();

    protected List<ProductStore> productStoreList = new ArrayList<>();
    protected Map<String,Object> params = new HashMap<>();
    protected String jsonStr = "";
    protected String isSaveAndPush = "";

    protected  List<ImageView> mDotsIV = new ArrayList<>();
    protected LinearLayout llSelectStorePre;
    protected LinearLayout dotsLayout;

    //选择配送方式常量
    protected boolean isSelectZiti = true;
    protected boolean isSelectYouji = true;
    //默认包邮
    protected String youjiFeeIsDisplayFlag = "1";


    //图片上传
    public static final int REQUEST_SELECT_FILE_CODE = 100;
    private static final int REQUEST_FILE_CHOOSER_CODE = 101;
    private static final int REQUEST_FILE_CAMERA_CODE = 102;
    // 默认图片压缩大小（单位：K）
    public static final int IMAGE_COMPRESS_SIZE_DEFAULT = 400;
    // 压缩图片最小高度
    public static final int COMPRESS_MIN_HEIGHT = 900;
    // 压缩图片最小宽度
    public static final int COMPRESS_MIN_WIDTH = 675;

    private ValueCallback<Uri> mUploadMsg;
    private ValueCallback<Uri[]> mUploadMsgs;
    // 相机拍照返回的图片文件
    private File mFileFromCamera;
    private BottomSheetDialog selectPicDialog;

    private List<EditText> editTextList  = new ArrayList<>();
    private List<ImageView> imageViewList  = new ArrayList<>();
    private List<File> imageFileList  = new ArrayList<>();
    private static final int TITLE_WORD_LIMIT_COUNT = 500;
    private List<View> goodDetailViewList  = new ArrayList<>();
    protected List<ProductExtendDesc> productExtendDescList = new ArrayList<>();


    private LinkedList<LinearLayout> mDelList = new LinkedList<LinearLayout>();

    private int curView = 0;

/*    List<CommonConstantBean> startSaleList = new ArrayList<>();
    List<CommonConstantBean> stopSaleList = new ArrayList<>();
    protected TimePickerView pvSaleTime;

    protected boolean isStartAndStopSale = true;
    //startSale
    protected String startSaleValue = "1";
    protected String stopSaleValue = "1";*/


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manage_new;
    }

    @Override
    protected void initView() {

        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);

        tvTitle.setText("商品设置");
        tvRight.setText("预览");
        tvRight.setVisibility(View.VISIBLE);
        storeType = "A";
        storeId = "";
        productStoreList.clear();
        ProductStore productStore = new ProductStore();
        productStore.setStoreId("");
        productStore.setStoreSelectType(storeType);
        productStoreList.add(productStore);
        tv_store_name.setText("#全部店铺");

        initPicAdapter();
        initValidGuDingPicker();
        initKaiAndStopTimePicker();
        //邮寄
        initPickerYoujiWay();
        setListener();
        setupLayoutTransitions(ll_goods_detail);
        ll_goods_detail.removeAllViews();
    }

    @Override
    protected void initData() {
        userId = SPUtils.getInstance().getString("userId");
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        productStatu = getIntent().getStringExtra("productStatu");
        productId = getIntent().getStringExtra("productId");
        productEditStatu = getIntent().getStringExtra("productEditStatu");

        presenter.queryStoreList(this,userId);
        setBrandLogo();
        //领取时间
        edt_lingqu_day.setFilters(new InputFilter[]{new MaxTextLengthFilter(3)});

        if("s".equals(productStatu)){
            edt_product_name.setEnabled(false);
            edt_sale_price.setEnabled(false);
            edt_market_price.setEnabled(false);
            edt_buy_know.setEnabled(false);
            edt_stock.setEnabled(false);
            edt_product_spec.setEnabled(false);
            tv_rebate_amt_is.setEnabled(false);
            edt_rebate_amt.setEnabled(false);
            tv_valid_time.setEnabled(false);
            //邮寄费用
            tv_youji_fee_type.setEnabled(false);
            edt_youji_fee.setEnabled(false);
            edt_lingqu_day.setEnabled(false);
            iv_youji_clear.setVisibility(View.GONE);
            iv_ziti_clear.setVisibility(View.GONE);
            tv_select_shop.setVisibility(View.GONE);
            tv_kai_sale_time.setEnabled(false);
            tv_kai_sale_custom_time.setEnabled(false);
            tv_stop_sale_time.setEnabled(false);
            tv_stop_sale_custom_time.setEnabled(false);
//            edt_product_remark.setEnabled(false);
            ll_goods_detail.setEnabled(false);
            edt_buy_know.setEnabled(false);
            tv_select_post_way.setEnabled(false);
            ll_add_word_image.setVisibility(View.GONE);
            rv_photo.setEnabled(false);

            ll_kai_sale_custom_time.setEnabled(false);
            ll_stop_sale_custom_time.setEnabled(false);
            tv_kai_sale_custom_time.setEnabled(false);
            tv_stop_sale_custom_time.setEnabled(false);
            tv_stop_sale_time.setEnabled(false);
            tv_kai_sale_time.setEnabled(false);
        }else{
            rv_photo.setEnabled(true);
            ll_add_word_image.setVisibility(View.VISIBLE);
            edt_product_name.setEnabled(true);
            edt_sale_price.setEnabled(true);
            edt_market_price.setEnabled(true);
            edt_buy_know.setEnabled(true);
            edt_stock.setEnabled(true);
            edt_product_spec.setEnabled(true);
            tv_rebate_amt_is.setEnabled(true);
            edt_rebate_amt.setEnabled(true);
            tv_valid_time.setEnabled(true);
            edt_lingqu_day.setEnabled(true);
            //邮寄费用
            tv_youji_fee_type.setEnabled(true);
            edt_youji_fee.setEnabled(true);
            iv_youji_clear.setVisibility(View.VISIBLE);
            iv_ziti_clear.setVisibility(View.VISIBLE);
            tv_select_shop.setVisibility(View.VISIBLE);
            tv_kai_sale_time.setEnabled(true);
            tv_kai_sale_custom_time.setEnabled(true);
            tv_stop_sale_time.setEnabled(true);
            tv_stop_sale_custom_time.setEnabled(true);
//            edt_product_remark.setEnabled(true);
            ll_goods_detail.setEnabled(true);
            edt_buy_know.setEnabled(true);

            tv_select_post_way.setEnabled(true);

            ll_kai_sale_custom_time.setEnabled(true);
            ll_stop_sale_custom_time.setEnabled(true);
            tv_kai_sale_custom_time.setEnabled(true);
            tv_stop_sale_custom_time.setEnabled(true);
            tv_stop_sale_time.setEnabled(true);
            tv_kai_sale_time.setEnabled(true);


        }
        if("o".equals(productStatu)){ //已下架
            rl_bottom.setVisibility(View.VISIBLE);
        }else if("s".equals(productStatu)){  //在售
             rl_bottom.setVisibility(View.GONE);
//            rl_bottom.set
//            rl_bottom.setVisibility(View.GONE);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scroll_view.getLayoutParams();
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scroll_view.getLayoutParams();
//            layoutParams.setMargins(0,0,0,0);

//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.height =0;
//            layoutParams.width =0;
//            rl_bottom.setLayoutParams(layoutParams);
        }else if("c".equals(productStatu)){  // 不在出售
            rl_bottom.setVisibility(View.VISIBLE);

        }else if("n".equals(productStatu)){ //未上架
            rl_bottom.setVisibility(View.VISIBLE);
        }

        if("R1".equals(deliveryDaytype)){// 固定时长
            rl_valid_time.setVisibility(View.VISIBLE);
            ll_valid_time.setVisibility(View.GONE);
        }else if("A".equals(deliveryDaytype)){//固定时间
            rl_valid_time.setVisibility(View.GONE);
            ll_valid_time.setVisibility(View.VISIBLE);
        }


        if(isSelectYouji &&  isSelectZiti){
            ll_ziti.setVisibility(View.VISIBLE);
            ll_youji.setVisibility(View.VISIBLE);
            deliveryType = "2";

            tv_select_post_way.setText("到店自提、快递邮寄");
            tv_ziti_type.setText("到店自提");
            tv_youji_type.setText("快递邮寄");
        }

        if("1".equals(youjiFeeIsDisplayFlag)){//包邮
            rl_youji_fee.setVisibility(View.GONE);
        }else if("0".equals(youjiFeeIsDisplayFlag)){//消费支付
            rl_youji_fee.setVisibility(View.VISIBLE);
        }

        //返还红包
        CommonConstantBean commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("是");
        commonConstantBean.setValue("1");
        isRebateAmtList.add(commonConstantBean);

        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("否");
        commonConstantBean.setValue("0");
        isRebateAmtList.add(commonConstantBean);
        //配送有效时间
        CardBean cateBean = new CardBean();
        cateBean.setCardNo("固定时长");
        cateBean.setId("R1");
        validTimeList.add(cateBean);

        cateBean = new CardBean();
        cateBean.setCardNo("固定时间");
        cateBean.setId("A");
        validTimeList.add(cateBean);

        //开售时间
         cateBean = new CardBean();
        cateBean.setCardNo("立即开售");
        cateBean.setId("1");
        kaiTimeList.add(cateBean);

        cateBean = new CardBean();
        cateBean.setCardNo("自定义时间");
        cateBean.setId("0");
        kaiTimeList.add(cateBean);

        //停售时间
        cateBean = new CardBean();
        cateBean.setCardNo("无停用时间");
        cateBean.setId("1");
        stopTimeList.add(cateBean);

        cateBean = new CardBean();
        cateBean.setCardNo("自定义时间");
        cateBean.setId("0");
        stopTimeList.add(cateBean);

        cateBean = new CardBean();
        cateBean.setCardNo("包邮");
        cateBean.setId("1");
        youjiList.add(cateBean);

        cateBean = new CardBean();
        cateBean.setCardNo("消费者支付");
        cateBean.setId("0");
        youjiList.add(cateBean);

      /*  //销售时间设置

        //开始销售时间
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("立即开售");
        commonConstantBean.setValue("1");
        startSaleList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("自定义");
        commonConstantBean.setValue("0");
        startSaleList.add(commonConstantBean);

        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(true);
        commonConstantBean.setKey("无停用时间");
        commonConstantBean.setValue("1");
        stopSaleList.add(commonConstantBean);
        commonConstantBean = new CommonConstantBean();
        commonConstantBean.setChecked(false);
        commonConstantBean.setKey("自定义");
        commonConstantBean.setValue("0");
        stopSaleList.add(commonConstantBean);*/


        initPickerKaiSaleTime();
        initPickerStopSaleTime();
        initPickerValidTime();

    }

    private void setListener(){
        edt_product_name.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_product_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(20)});
        edt_sale_price.setFilters(new InputFilter[]{new MaxTextLengthFilter(14)});
        edt_market_price.setFilters(new InputFilter[]{new MaxTextLengthFilter(14)});
        edt_stock.setFilters(new InputFilter[]{new MaxTextLengthFilter(8)});
        edt_product_spec.setFilters(new InputFilter[]{new MaxTextLengthFilter(20)});
        edt_rebate_amt.setFilters(new InputFilter[]{new MaxTextLengthFilter(14)});
        edt_lingqu_day.setFilters(new InputFilter[]{new MaxTextLengthFilter(3)});

//        edt_qishou_stock.setFilters(new InputFilter[]{new MaxTextLengthFilter(4)});

        edt_qishou_stock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String stock = edt_qishou_stock.getText().toString().trim();
                int st = 0;
                if(stock != null && !"".equals(stock)){
                    st = Integer.parseInt(stock);
                    if(st >=1){

                    }else {
                        edt_qishou_stock.setText("1");
                        edt_qishou_stock.setSelection(edt_qishou_stock.getText().toString().trim().length());
                    }
                }else {
                    ToastUtils.showShort("数量至少为1");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    /*    edt_product_remark.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_product_remark.setFilters(new InputFilter[]{new MaxTextLengthFilter(500)});
        edt_product_remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if(edt_product_remark.getText().toString().trim().length()>0){
                    edt_product_remark.setSelection(edt_product_remark.getText().toString().trim().length());
                    tv_product_remark_num.setText(length+"/500");
                }
            }
        });*/
        edt_buy_know.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_buy_know.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_buy_know.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int remarkLenth = s.length();
                if(edt_buy_know.getText().toString().trim().length()>0){
                    edt_buy_know.setSelection(edt_buy_know.getText().toString().trim().length());
                    tv_buy_know_num.setText(remarkLenth+"/500");
                }
            }
        });


    }

    @OnClick({R.id.iv_back,R.id.tv_right,R.id.tv_select_shop,R.id.iv_arrow,R.id.tv_rebate_amt_is,R.id.tv_product_cate,
            R.id.tv_valid_time,R.id.tv_valid_start_time,R.id.tv_valid_end_time,
            R.id.tv_kai_sale_time,R.id.tv_stop_sale_time,R.id.tv_kai_sale_custom_time,R.id.tv_stop_sale_custom_time,
            R.id.btn_save, R.id.btn_push,R.id.tv_select_post_way,R.id.iv_ziti_clear,R.id.iv_youji_clear,R.id.tv_youji_fee_type,
            R.id.btn_add_word,R.id.btn_add_pic})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                EventBus.getDefault().post(new ProductEventBus(productId,"listFlush",""));
                ActivityUtils.startActivity(ShopManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                ActivityUtils.finishActivity(ShopManageNewActivity.class);
                break;

            case R.id.tv_right:  //预览
                //预览
                setPopDialog();
                break;
            case R.id.iv_arrow:  //店铺扩展箭头


                break;
            case R.id.tv_select_shop:  //开始时间

                if(storeList != null && storeList.size()>0){
                    showPopView(this);
                }else {
                    presenter.queryStoreList(this,userId);
                }

                break;

            case R.id.tv_product_cate:  //商品分类
                pvCategory.show();
                break;
            case R.id.tv_rebate_amt_is:  //是否返利红包
                showIsReListView();
                break;
            case R.id.tv_select_post_way:
                showPostWay();
                break;
            case R.id.tv_youji_fee_type:
                pvYoujiFee.show();
                break;
            case R.id.iv_ziti_clear:  //是否隐藏自提显示  2019-05-07改版新增
                if(isSelectZiti){
                    isSelectZiti = false;
                }

                ll_ziti.setVisibility(View.GONE);

                iv_youji_clear.setVisibility(View.GONE);
                ll_youji.setVisibility(View.VISIBLE);
                deliveryType = "1";
                tv_select_post_way.setText("快递邮寄");
                break;
            case R.id.iv_youji_clear:  //是否隐藏邮寄显示
                if(isSelectYouji){
                    isSelectYouji = false;
                }
                ll_ziti.setVisibility(View.VISIBLE);
                iv_ziti_clear.setVisibility(View.GONE);
                ll_youji.setVisibility(View.GONE);

                deliveryType = "0";
                tv_select_post_way.setText("到店自提");
                break;
            case R.id.tv_valid_time:  //有效时间
                pvValidTime.show();
                break;
            case R.id.tv_valid_start_time:  // 固定时间
                isValidStartTime = "YES";
                pvValidGuDingTime.show();
                break;
            case R.id.tv_valid_end_time:  //固定时间
                isValidStartTime = "NO";
                pvValidGuDingTime.show();
                break;
            case R.id.tv_kai_sale_time:  //开售时间、自定义选项
                pvKaiTime.show();

                break;
            case R.id.tv_stop_sale_time:  //停售时间、自定义选项
                pvStopTime.show();

                break;
            case R.id.tv_kai_sale_custom_time:  //开售日期时间

                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                String startDate = tv_kai_sale_custom_time.getText().toString().trim();
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
                isSelectTime = "KAI";
                pvCustomTime.show();
                break;
            case R.id.tv_stop_sale_custom_time:  //停售日期时间
                Calendar  selectedEndDate = Calendar.getInstance();//系统当前时间
                String endDate = tv_stop_sale_custom_time.getText().toString().trim();
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

                    selectedEndDate.set(selectedEndDate.get(Calendar.YEAR), selectedEndDate.get(Calendar.MONTH), selectedEndDate.get(Calendar.DAY_OF_MONTH), hour, mi, 59);
                }else {
                    selectedEndDate.set(selectedEndDate.get(Calendar.YEAR), selectedEndDate.get(Calendar.MONTH), selectedEndDate.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
                }


                pvCustomTime.setDate(selectedEndDate);
                isSelectTime = "STOP";
                pvCustomTime.show();

                break;


            case R.id.btn_save:  // 保存
                isSaveAndPush = "save";
                setDataValue();
                setProductExtendDesc();
                if(check()){
                    return;
                }

                if (!ButtonUtils.isFastDoubleClick()) {
                    if(fileList != null && fileList.size()>0){

                        presenter.uploadFiles(ShopManageNewActivity.this,fileList);
                    }else {
                        if(productExtendDescList != null &&productExtendDescList.size()>0){
                            presenter.uploadGoodDetailImageFiles(ShopManageNewActivity.this,productExtendDescList);
                        }else {
                            setJson();
                            presenter.saveProductSetInfo(ShopManageNewActivity.this,params,userId,isSaveAndPush);
                        }

                    }

                }
                break;
            case R.id.btn_push:  // 上架

                isSaveAndPush = "publish";
                setDataValue();
                setProductExtendDesc();
                if(check()){
                    return;
                }
                if (!ButtonUtils.isFastDoubleClick()) {
                    if(fileList != null && fileList.size()>0){

                        presenter.uploadFiles(ShopManageNewActivity.this,fileList);
                    }else {

                        if(productExtendDescList != null &&productExtendDescList.size()>0){
                            presenter.uploadGoodDetailImageFiles(ShopManageNewActivity.this,productExtendDescList);
                        }else {
                            setJson();
                            presenter.saveProductSetInfo(ShopManageNewActivity.this,params,userId,isSaveAndPush);
                        }
                    }
                }
//                btn_push.setEnabled(false);

                break;
            case R.id.btn_add_word:

                addGoodsEdit("");
                break;
            case R.id.btn_add_pic:

                selectGoodDetailList = new ArrayList<>();
                int config = PictureConfig.CHOOSE_REQUEST;

                themeId = R.style.picture_default_style;
                aspect_ratio_x = 1;
                aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
                PictureSelectionModel pictureSelector = PictureSelector.create(ShopManageNewActivity.this)
                        .openGallery(chooseMode).theme(themeId)
                        .minSelectNum(1).imageSpanCount(4).previewImage(true)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .enablePreviewAudio(false).isCamera(false)// 是否显示拍照按钮
                        .isZoomAnim(true).enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true).glideOverride(160, 300)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectGoodDetailList).minimumCompressSize(100);// 小于100kb的图片不压缩

                pictureSelector .maxSelectNum(1);// 最大图片选择数量
                pictureSelector.selectionMode(PictureConfig.SINGLE);
                pictureSelector.forResult(config);

//                selectPrizeImg();
                break;
           /* case R.id.btn_posts:
                List<SEditorData> editList = editor.buildEditData();
                // 下面的代码可以上传、或者保存，请自行实现
                dealEditData(editList);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        EventBus.getDefault().post(new ProductEventBus(productId,"listFlush",""));
//        ActivityUtils.finishActivity(ShopManageNewActivity.class,
//                R.anim.slide_in_left,R.anim.slide_out_right);

        ActivityUtils.startActivity(ShopManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
        ActivityUtils.finishActivity(ShopManageNewActivity.class);
    }






    protected void setImageFileList(){
        fileList = new ArrayList<>();
        if(selectList != null && selectList.size()>0){
            for(LocalMedia localMedia1 : selectList){
                boolean compress = localMedia1.isCompressed();
                String unPrizePath = "";
                if(compress){
                     unPrizePath = localMedia1.getCompressPath();

                }else {
                    unPrizePath = localMedia1.getPath();
                }
                File file = new File(unPrizePath);
                if(!unPrizePath.contains("get.do?id=")) {
                    file = new File(unPrizePath);
                    fileList.add(file);
                }


            }

        }
    }

    private boolean check(){
        String saveAndPush = "";
        if("save".equals(isSaveAndPush)){
            saveAndPush = "保存";
        }else if("publish".equals(isSaveAndPush)){
            saveAndPush = "上架";
        }

        if(productName == null || "".equals(productName)){

            setDialog("商品名称未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }else {

            if(productName.contains(" ")){
                setDialog("商品名不能包含空格符");
                return true;
            }
        }

        if(salePrice == null || "".equals(salePrice)){
            setDialog("售价未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }


        /*if(marketPrice == null || "".equals(marketPrice)){
            setDialog("市场价未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }*/

        //商品数量
        if(groundingCount == null || "".equals(groundingCount)){
            setDialog("商品数量未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }

        //分类id
        if(cateId == null || "".equals(cateId)){
            setDialog("商品分类未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }

        if("1".equals(rebateFlag)){  //是 推荐返利
            if(rebateAmt == null || "".equals(rebateAmt)){
                setDialog("返利红包未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }
            if( "0".equals(rebateAmt)){
                setDialog("返利红包必须大于零。");
                return true;
            }
        }else { //否
            rebateAmt = "";
        }



        productSpec   = edt_product_spec.getText().toString().trim();
        if(productSpec == null || "".equals(productSpec)){
            setDialog("商品规格未填写，无法"+saveAndPush+"，请检查后进行操作。");
            return true;
        }


        delivDay = edt_lingqu_day.getText().toString().trim();


//        deliveryType  = "0";  //目前默认自提，后期增加其他类型
        if ("0".equals(deliveryType)) {  //到店自提
            if("R1".equals(deliveryDaytype)){ //固定时长
                delivEnddate = null;
                delivStartdate = null;
                if(delivDay == null || "".equals(delivDay) ){
                    setDialog("领取时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;

                }

                if("0".equals(delivDay)){
                    setDialog("领取时间不能为空");
                    return true;
                }
            }else if("A".equals(deliveryDaytype)){ //固定时间
                delivDay = "";
                if(delivStartdate == null || "".equals(delivStartdate)){
                    setDialog("开始时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }

                if(delivEnddate == null || "".equals(delivEnddate)){
                    setDialog("结束时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }
            }
            if(productStoreList == null || productStoreList.size() <=0){
                setDialog("店铺未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }
        }else if ("1".equals(deliveryType)) {// 快递邮寄

            if("1".equals(youjiFeeIsDisplayFlag)){  //包邮
                deliveryFee = "";
            }else if("0".equals(youjiFeeIsDisplayFlag)){  //消费支付

                if(deliveryFee == null || "".equals(deliveryFee)){
                    setDialog("邮费未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }else {
                    double fee = Double.parseDouble(deliveryFee);
                    if(fee <= 0){
                        setDialog("邮费金额必须大于零");
                        return true;
                    }
                }
            }
        }else if("2".equals(deliveryType)){   //自提、邮寄
            if("R1".equals(deliveryDaytype)){ //固定时长
                delivEnddate = null;
                delivStartdate = null;
                if(delivDay == null || "".equals(delivDay) ){
                    setDialog("领取时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;

                }

                if("0".equals(delivDay)){
                    setDialog("领取时间不能为空");
                    return true;
                }
            }else if("A".equals(deliveryDaytype)){ //固定时间
                delivDay = "";
                if(delivStartdate == null || "".equals(delivStartdate)){
                    setDialog("开始时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }

                if(delivEnddate == null || "".equals(delivEnddate)){
                    setDialog("结束时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }
            }


            if(productStoreList == null || productStoreList.size() <=0){
                setDialog("店铺未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }


            if("1".equals(youjiFeeIsDisplayFlag)){  //包邮
                deliveryFee = "";
            }else if("0".equals(youjiFeeIsDisplayFlag)){  //消费支付

                if(deliveryFee == null || "".equals(deliveryFee)){
                    setDialog("邮费未填写，无法"+saveAndPush+"，请检查后进行操作。");
                    return true;
                }else {
                    double fee = Double.parseDouble(deliveryFee);
                    if(fee <= 0){
                        setDialog("邮费金额必须大于零");
                        return true;
                    }
                }

            }
        }


        //2019-07-08 取消注释掉 前期销售时间暂不展示用，所以隐藏

        if(productExtend != null){
            salesStartdate = productExtend.getSalesStartdate();
            salesEnddate = productExtend.getSalesEnddate();

            Integer salesCount1= productExtend.getSalesCount();

            if(salesCount1 != null){

            }else {
                setDialog("起售数量未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }


        }
        if("0".equals(kaiSaleTimeFlag)){
            if(salesStartdate == null || "".equals(salesStartdate)){
                setDialog("停售时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }else {

            }
        }
        if("0".equals(stopSaleTimeFlag)){
            if(salesEnddate == null || "".equals(salesEnddate)){
                setDialog("停售时间未填写，无法"+saveAndPush+"，请检查后进行操作。");
                return true;
            }else {

            }
        }

        return false;
    }

    public void setDialog(String message){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
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

    protected void setJson(){
        params = new HashMap<String,Object>();
        try{
            String productSetNewJSON=new Gson().toJson(productSetNew);
            params.put("productSet",productSetNewJSON);
            String productExtendJSON=new Gson().toJson(productExtend);
            params.put("productExtend",productExtendJSON);

            params.put("productMedia",productMediaList);
            params.put("productStore",productStoreList);
            params.put("operateFlag",isSaveAndPush);
            params.put("productExtendDesc",productExtendDescList);
            jsonStr = JSON.toJSONString(params);
        }catch (Exception e){

        }
    }
    protected void setDataValue(){
        setImageFileList();

        String name = edt_product_name.getText().toString().trim();

        productCate = tv_product_cate.getText().toString().trim();
        salePrice = edt_sale_price.getText().toString().trim();

        marketPrice = edt_market_price.getText().toString().trim();
        //上架商品数量
        groundingCount = edt_stock.getText().toString().trim();

        productSpec = edt_product_spec.getText().toString().trim();
        rebateAmt = edt_rebate_amt.getText().toString().trim();
        deliveryFee  = edt_youji_fee.getText().toString().trim();  //快递邮寄  消费支付

        //开售时间
        salesStartdate = tv_kai_sale_custom_time.getText().toString().trim();
//        //停售时间
         salesEnddate = tv_stop_sale_custom_time.getText().toString().trim();

        //起售数量
        salesCount = edt_qishou_stock.getText().toString().trim();

        if(name.contains(" ")){
            productName = name.replace(" ","");
        }else {
            productName = name;
        }
        productSetNew.setProductName(productName);

        if(salePrice != null && !"".equals(salePrice)){
            productSetNew.setUnitPrice(new BigDecimal(salePrice));
        }


        if(marketPrice != null && !"".equals(marketPrice)){
            productSetNew.setMarketPrice(new BigDecimal(marketPrice));
        }

        //商品数量
        if(groundingCount != null && !"".equals(groundingCount)){
            productSetNew.setGroundingCount(Long.parseLong(groundingCount));
        }

        //分类id
        if(cateId != null && !"".equals(cateId)){
            productSetNew.setCatId(Long.parseLong(cateId));
        }
        //支付方式 默认现金
        productSetNew.setPayWay("cash");
        productSetNew.setCatName(productCate);

        productSetNew.setRebateFlag(rebateFlag);
        if("1".equals(rebateFlag)){  //是 推荐返利
            if(rebateAmt != null && !"".equals(rebateAmt)){
                productSetNew.setRebateAmt(new BigDecimal(rebateAmt));
            }
        }else { //否
            rebateAmt = "";
            productSetNew.setRebateAmt(null);
        }


        productSpec   = edt_product_spec.getText().toString().trim();
        if(productSpec != null && !"".equals(productSpec)){
            productExtend.setProductSpec(productSpec);
        }
//        deliveryType  = "0";  //目前默认自提，后期增加其他类型

        productExtend.setDeliveryType(deliveryType);


        String startdate= tv_valid_start_time.getText().toString().trim();
        String enddate = tv_valid_end_time.getText().toString().trim();
        if ("0".equals(deliveryType)) {  //到店自提
            deliveryFee = null;
            delivDay = edt_lingqu_day.getText().toString().trim();
            productExtend.setDeliveryDaytype(deliveryDaytype);
            if("R1".equals(deliveryDaytype)){ //固定时长
                delivEnddate = null;
                delivStartdate = null;
                if(delivDay != null && !"".equals(delivDay) && !"0".equals(delivDay)){
                    productExtend.setDelivDay(Integer.parseInt(delivDay));
                }
            }else if("A".equals(deliveryDaytype)){ //固定时间
                delivDay = "";
                productExtend.setDelivDay(null);
                delivStartdate  = startdate.replace(".","-")+" 00:00:00";
                delivEnddate = enddate.replace(".","-")+" 23:59:59";
            }
            productExtend.setDelivEnddate(delivEnddate);
            productExtend.setDelivStartdate(delivStartdate);
        }else if ("1".equals(deliveryType)) {// 快递邮寄

            if("1".equals(youjiFeeIsDisplayFlag)){  //包邮
                deliveryFee = "";
            }else if("0".equals(youjiFeeIsDisplayFlag)){  //消费支付
            }
        }else if("2".equals(deliveryType)){

            delivDay = edt_lingqu_day.getText().toString().trim();
            productExtend.setDeliveryDaytype(deliveryDaytype);
            if("R1".equals(deliveryDaytype)){ //固定时长
                delivEnddate = null;
                delivStartdate = null;
                if(delivDay != null && !"".equals(delivDay) && !"0".equals(delivDay)){
                    productExtend.setDelivDay(Integer.parseInt(delivDay));
                }
            }else if("A".equals(deliveryDaytype)){ //固定时间
                delivDay = "";
                productExtend.setDelivDay(null);
                delivStartdate  = startdate.replace(".","-")+" 00:00:00";
                delivEnddate = enddate.replace(".","-")+" 23:59:59";
            }
            productExtend.setDelivEnddate(delivEnddate);
            productExtend.setDelivStartdate(delivStartdate);

            if("1".equals(youjiFeeIsDisplayFlag)){  //包邮
                deliveryFee = "0";
            }else if("0".equals(youjiFeeIsDisplayFlag)){  //消费支付
            }
        }


        if(deliveryFee != null && !"".equals(deliveryFee)){
            productExtend.setDeliveryFee(new BigDecimal(deliveryFee));
        }else {
            productExtend.setDeliveryFee(new BigDecimal("0"));
        }


        //临时注释  前期暂不展示用 所以这块校验去掉  2019-05-07

        if("1".equals(kaiSaleTimeFlag)){
            productExtend.setSalesStartdate("");
        }else {
            if(salesStartdate != "" && !"".equals(salesStartdate)){
                productExtend.setSalesStartdate(salesStartdate.replace(".","-")+":00");
            }else {
                productExtend.setSalesStartdate("");
            }

        }

        if("1".equals(stopSaleTimeFlag)){
            productExtend.setSalesEnddate("");
        }else {

            if(salesEnddate != "" && !"".equals(salesEnddate)){
                productExtend.setSalesEnddate(salesEnddate.replace(".","-")+":59");
            }else {
                productExtend.setSalesEnddate("");
            }
        }
        //起售数量
        if(salesCount != null && !"".equals(salesCount)){
            productExtend.setSalesCount(Integer.parseInt(salesCount));
        }else {
            productExtend.setSalesCount(null);
        }
       /* if(kaiStartdate != null && !"".equals(kaiStartdate)){
            salesStartdate = kaiStartdate.replace(".","-")+" 00:00:00";

        }else {
            salesStartdate = null;
        }
        productExtend.setSalesStartdate(salesStartdate);
        if(stopStartdate != null && !"".equals(stopStartdate)){
            salesEnddate = stopStartdate.replace(".","-")+" 23:59:59";
        }else {
            salesEnddate = null;

        }
        productExtend.setSalesEnddate(salesEnddate);*/

        remark = edt_buy_know.getText().toString().trim();
        productExtend.setRemark(remark);
//        productDetail= edt_product_remark.getText().toString().trim();

        productExtend.setProductDetail(productDetail);
    }


    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
        manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rv_photo.setLayoutManager(manager);
        adapter = new ShopGridImageAdapter(ShopManageNewActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        rv_photo.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShopGridImageAdapter.OnItemClickListener() {
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
                            PictureSelector.create(ShopManageNewActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;

                    }
                }
            }
        });

    }

    private ShopGridImageAdapter.onAddPicClickListener onAddPicClickListener = new ShopGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            int config = PictureConfig.CHOOSE_REQUEST10;
            themeId = R.style.picture_default_style;
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
            PictureSelectionModel pictureSelector = PictureSelector.create(ShopManageNewActivity.this)
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

            pictureSelector.forResult(config);
        }

    };


    //选择多个店铺
    protected void  showPopView(Context context){


        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_campaign_select_more_store,null);
        popWindow= new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopDown)
                .setView(contentView)
                .show(tvTitle);


        RelativeLayout rlAllStore = contentView.findViewById(R.id.rl_all);
        CheckBox cbAllStore = contentView.findViewById(R.id.cb__all);
        TextView tvAllStore =contentView.findViewById(R.id.tv_all);

        RelativeLayout rlSelfStore = contentView.findViewById(R.id.rl_self_bus);
        CheckBox cbSelfStore = contentView.findViewById(R.id.cb_self_bus);
        TextView tvSelfStore =contentView.findViewById(R.id.tv_self_bus);

        RelativeLayout rlJoinStore = contentView.findViewById(R.id.rl_all_join);
        CheckBox cbJoinStore = contentView.findViewById(R.id.cb_all_join);
        TextView tvJoinStore =contentView.findViewById(R.id.tv_all_join);

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
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";

                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb.toString()!=null && !"".equals(storeIdSb.toString())){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);
                    }
                    storeId = storeIdArr;
                    storeName = "#全部店铺";
                    tv_store_name.setText(storeNameSb.toString());
                    storeType = "A";


                }
                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });

        rlSelfStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){
                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    myAdapter.notifyDataSetChanged();
                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);

                    }

                    storeId = storeIdArr;
                    storeName = "#全部自营";
                    storeType = "D";


                }
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            }
        });
        rlJoinStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                StringBuilder storeIdSb = new StringBuilder();
                StringBuilder storeNameSb = new StringBuilder();

                String storeIdArr = "";
                if(storeList!=null&& storeList.size()>0){
                    for (Store store:storeList){

                        store.setChecked(false);
                        String storeId = store.getStoreId();
                        String storeName = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                        storeIdSb.append(storeId).append("-");
                        storeNameSb.append(storeName);

                    }

                    if(storeIdSb!=null ||!"".equals(storeIdSb)){
                        storeIdArr = storeIdSb.substring(0,storeIdSb.length() - 1);

                    }
                    storeType  = "J";
                    storeName = "#全部加盟";
                    storeId = storeIdArr;
                    myAdapter.notifyDataSetChanged();



                }

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                //自定义默认不选中
                if(storeList!=null&& storeList.size()>0){

                    for (Store store:storeList){
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
                productStoreList.clear();
                if(storeList!=null&& storeList.size()>0){
                    if(storeType != null && !"".equals(storeType)){
                        if("A".equals(storeType)||"D".equals(storeType)||"J".equals(storeType)){   //全部店铺  自营  加盟
                            //storeName 为全部店铺  自营  加盟

                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                SPUtils.getInstance().put("brandLogo",brandLogo);
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }

                            ProductStore productStore = new ProductStore();
                            productStore.setStoreId("");
                            productStore.setStoreSelectType(storeType);
                            productStoreList.add(productStore);
                            tv_store_name.setText(storeName);


                        }else if("H".equals(storeType)){  //H为自定义店铺
                            for (Store store: storeList){
                                if(store!=null){
                                    if(store.isChecked()){
                                        String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                        storeNameSb.append(name);
                                        storeIdSb.append(store.getStoreId()).append("-");
                                        ProductStore productStore = new ProductStore();
                                        productStore.setStoreId(store.getStoreId());
                                        productStore.setStoreSelectType("");
                                        productStoreList.add(productStore);
                                    }

                                }
                            }

                            String storeIdA= storeIdSb.toString();
                            if(storeIdA!=null && !"".equals(storeIdA)){
                                storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                                storeId = storeIdArr;
                                storeName = storeNameSb.toString();
                            }else {
                                storeId = "";
                                storeName = "#全部店铺";
                                storeType = "A";
                            }


                            tv_store_name.setText(storeNameSb.toString());
                            if(branList !=null && branList.size()>0){
                                brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                                SPUtils.getInstance().put("brandLogo",brandLogo);
                                if(brandLogo!=null){
                                    setBrandLogo();
                                }
                            }

                            SPUtils.getInstance().put("brandLogoPt",brandLogo);
                        }
                        popWindow.dismiss();
                    }else {
                        storeType = "H";
                        for (Store store: storeList){
                            if(store!=null){
                                if(store.isChecked()){
                                    String name = store.getStoreName()!=null?"#"+store.getStoreName()+ "    ":"";
                                    storeNameSb.append(name);
                                    storeIdSb.append(store.getStoreId()).append("-");
                                    ProductStore productStore = new ProductStore();
                                    productStore.setStoreId(store.getStoreId());
                                    productStore.setStoreSelectType("");
                                    productStoreList.add(productStore);
                                }

                            }
                        }

                        String storeIdA= storeIdSb.toString();
                        if(storeIdA!=null && !"".equals(storeIdA)){
                            storeIdArr = storeIdA.substring(0,storeIdA.length() - 1);
                            storeId = storeIdArr;
                            storeName = storeNameSb.toString();
                            storeType = "";
                        }else {
                            storeId = "";
                            storeName = "#全部店铺";
                            storeType = "A";
                        }


                        tv_store_name.setText(storeName);
                        storeType = "H";
                        if(branList !=null && branList.size()>0){
                            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(brandPostion).getBrandLogo();
                            if(brandLogo!=null){
                                setBrandLogo();
                            }
                        }


                        popWindow.dismiss();
                    }

                }
            }
        });


        MoreBrandListAdapter brandListAdapter = new MoreBrandListAdapter(this,branList);
        lv_left.setAdapter(brandListAdapter);

        if(brandPostion == -1){
            brandPostion = 0;
        }
        brandListAdapter.setSelectItem(brandPostion);

        myAdapter = new CampaignSelectStoreAdapter(context);
        int selectItem = brandListAdapter.getSelectItem();
        if(branList!=null&&branList.size()>0){
            storeList = branList.get(selectItem).getStoreList();
            brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(selectItem).getBrandLogo();
            SPUtils.getInstance().put("brandLogoGive",brandLogo);
            myAdapter.setDatas(storeList);
        }

        lv_right.setAdapter(myAdapter);
        //判断默认选择店铺
        String storeNameStr = tv_store_name.getText().toString().trim();

//        storeIdCouponType = SPUtils.getInstance().getString("storeIdCouponType");

        if(storeType!=null && !"".equals(storeType)){
            if("A".equals(storeType)){

                cbAllStore.setChecked(true);
                cbAllStore.setButtonDrawable(R.drawable.ic_correct);
                tvAllStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
                storeId = "";

            }else if("D".equals(storeType)){

                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(true);
                cbSelfStore.setButtonDrawable(R.drawable.ic_correct);
                tvSelfStore.setTextColor(getResources().getColor(R.color.font_color_blue));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
            }else if("J".equals(storeType)){
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(true);
                cbJoinStore.setButtonDrawable(R.drawable.ic_correct);
                tvJoinStore.setTextColor(getResources().getColor(R.color.font_color_blue));
                if(storeList!=null&& storeList.size()>0){  //自定义默认不选中
                    for (Store store:storeList){
                        if(store!=null){
                            store.setChecked(false);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
            }else if("H".equals(storeType)){
                //分类默认不选中，自定义选中
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
                if(storeList!=null&&storeList.size()>0){

                    for (int i = 0;i<storeList.size();i++){
                        Store store = storeList.get(i);

                        if(storeNameStr!=null){
                            if(storeNameStr.contains(store.getStoreName())){
                                store.setChecked(true);
                            }else {
                                store.setChecked(false);
                            }
                        }

                    }
                }
            }

        }else {
            //分类默认不选中，自定义选中
            cbAllStore.setChecked(false);
            cbAllStore.setButtonDrawable(R.color.white);
            tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbSelfStore.setChecked(false);
            cbSelfStore.setButtonDrawable(R.color.white);
            tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            cbJoinStore.setChecked(false);
            cbJoinStore.setButtonDrawable(R.color.white);
            tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));
            if(storeList!=null&&storeList.size()>0){

                for (int i = 0;i<storeList.size();i++){
                    Store store = storeList.get(i);

                    if(storeNameStr!=null){
                        if(storeNameStr.contains(store.getStoreName())){
                            store.setChecked(true);
                        }else {
                            store.setChecked(false);
                        }
                    }

                }
            }
        }



        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(branList!=null&&branList.size()>0){
                    if(branList.get(position)!=null){
                        brandLogo = HttpUrls.BRAND_LOGO_ULR+branList.get(position).getBrandLogo();


                        SPUtils.getInstance().put("brandLogoPt",brandLogo);
                        SPUtils.getInstance().put("brandPositionPt",brandPostion);
                        storeList = branList.get(position).getStoreList();
                        brandPostion = position;
                        brandListAdapter.setSelectItem(position);
                        brandListAdapter.notifyDataSetInvalidated();
                        myAdapter = new CampaignSelectStoreAdapter(context);
                        myAdapter.setDatas(storeList);
                        lv_right.setAdapter(myAdapter);
                    }
                }

            }
        });


        myAdapter.notifyDataSetChanged();
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(currentNum == -1){ //选中
                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    storeList.get(position).setChecked(false);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的

                    if(storeList.get(position).isChecked()){
                        storeList.get(position).setChecked(false);
                    }else {
                        storeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }


                myAdapter.notifyDataSetChanged();
                storeType = "H";
                cbAllStore.setChecked(false);
                cbAllStore.setButtonDrawable(R.color.white);
                tvAllStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbSelfStore.setChecked(false);
                cbSelfStore.setButtonDrawable(R.color.white);
                tvSelfStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

                cbJoinStore.setChecked(false);
                cbJoinStore.setButtonDrawable(R.color.white);
                tvJoinStore.setTextColor(getResources().getColor(R.color.color_gray_dark));

            }
        });
    }


    private void setBrandLogo(){
        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions = new RequestOptions()
                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(iv_brand);
    }


    private void initValidGuDingPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvValidGuDingTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if("YES".equals(isValidStartTime)){
                    String startTime = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
                    tv_valid_start_time.setText(startTime.replace("-","."));

                }else {
                    String endTime = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
                    tv_valid_end_time.setText(endTime.replace("-","."));
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
                                pvValidGuDingTime.returnData();
                                String startDate  = tv_valid_start_time.getText().toString().trim();
                                String endDate = tv_valid_end_time.getText().toString().trim();
                                if("YES".equals(isValidStartTime)){
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            tv_valid_start_time.setText("");
                                            ToastUtils.showToast("开始时间不可大于结束时间！");
                                        }else {
                                            pvValidGuDingTime.dismiss();
                                        }


                                    }else {
                                        pvValidGuDingTime.dismiss();
                                    }
                                }else {
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            ToastUtils.showToast("结束时间不可小于开始时间！");
                                            tv_valid_end_time.setText("");
                                        }else {
                                            pvValidGuDingTime.dismiss();
                                        }


                                    }else {
                                        pvValidGuDingTime.dismiss();
                                    }

                                }


                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvValidGuDingTime.dismiss();
                            }
                        });

                    }



                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private void showIsReListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(ShopManageNewActivity.this);
        adapter.setDatas(isRebateAmtList);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        isRebateAmtPop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_rebate_amt_is);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:isRebateAmtList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = isRebateAmtList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                isRebateAmtPop.dismiss();

                tv_rebate_amt_is.setText(commonConstantBean.getKey());
                rebateFlag = commonConstantBean.getValue();

                if("1".equals(rebateFlag)){
                    rl_rebate.setVisibility(View.VISIBLE);
                    rebate_amt_tip.setVisibility(View.VISIBLE);
                }else {
                    rl_rebate.setVisibility(View.GONE);
                    rebate_amt_tip.setVisibility(View.GONE);
                }

            }
        });
    }
    //有效时间
    protected void initPickerValidTime() {//条件选择器初始化，自定义布局

        pvValidTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = validTimeList.get(options1).getPickerViewText();
                String key = validTimeList.get(options1).getId();

                deliveryDaytype = key;
                if("R1".equals(deliveryDaytype)){
                    rl_valid_time.setVisibility(View.VISIBLE);
                    ll_valid_time.setVisibility(View.GONE);
                }else  if("A".equals(deliveryDaytype)){
                    rl_valid_time.setVisibility(View.GONE);
                    ll_valid_time.setVisibility(View.VISIBLE);
                }

                tv_valid_time.setText(value);
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView iv_submit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView iv_cancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        iv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pvValidTime.returnData();
                                pvValidTime.dismiss();
                            }
                        });

                        iv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                pvValidTime.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvValidTime.setPicker(validTimeList);

    }
    protected void initPickerCate() {//条件选择器初始化，自定义布局
    builder = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = categoryList.get(options1).getPickerViewText();
                String key = categoryList.get(options1).getId();

                cateId = key;
                cateName = value;

                tv_product_cate.setText(value);
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position_category, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final Button btn_add = (Button) v.findViewById(R.id.btn_add);
                        Button btn_submit = (Button) v.findViewById(R.id.btn_submit);

                        btn_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pvCategory.returnData();
                                pvCategory.dismiss();
                            }
                        });

                        btn_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


//                                pvCategory.dismiss();
                                showAddCate();
                            }
                        });


                    }
                })
                ;

        if(categoryList != null &&categoryList.size()>0){
            for (int i =0;i<categoryList.size();i++){
                CardBean cardBean = categoryList.get(i);
                if(cateId != null && !"".equals(cateId)){
                    if(cateId.equals(cardBean.getId())){
                        if(i == 0){
                            builder.setSelectOptions(i+1);
                        }else {
                            builder.setSelectOptions(i);
                        }

                        break;
                    }

                }

            }
        }
        pvCategory = builder.build();
        pvCategory.setPicker(categoryList);




    }

    protected void showAddCate() {

        View customView = View.inflate(ShopManageNewActivity.this, R.layout.layout_shop_add_category, null);

        AlertDialog.Builder builder  = new AlertDialog.Builder(ShopManageNewActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edt_name = (EditText)customView.findViewById(R.id.edt_name);
        edt_name.setSelection(edt_name.getText().length());
        TextView title = (TextView)customView.findViewById(R.id.title);
        edt_name.setFilters(new InputFilter[]{new EmojiInputFilter()});
        edt_name.setFilters(new InputFilter[]{new MaxTextLengthFilter(10)});

        edt_name.setText("");
        title.setText("新建分类");
        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                String salesType = "";
                if(name != null && !"".equals(name)){
                    presenter.addCategory(ShopManageNewActivity.this,name,salesType,userId);
                    pvCategory.dismiss();
                }else {
                    ToastUtils.showShort("不可为空");
                    return;
                }

                dialog.dismiss();
            }
        });
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 开售时间选项
     * */
    protected void initPickerKaiSaleTime() {//条件选择器初始化，自定义布局

        pvKaiTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = kaiTimeList.get(options1).getPickerViewText();
                String key = kaiTimeList.get(options1).getId();

                kaiSaleTimeFlag = key;
                tv_kai_sale_time.setText(value);

                if("1".equals(kaiSaleTimeFlag)){
                    salesStartdate = null;
                    ll_kai_sale_custom_time.setVisibility(View.GONE);
                    tv_kai_sale_custom_time.setText("");
                }else {
                    tv_kai_sale_time.setText("自定义");
                    ll_kai_sale_custom_time.setVisibility(View.VISIBLE);

                }
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView iv_submit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView iv_cancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        iv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvKaiTime.returnData();
                                pvKaiTime.dismiss();
                            }
                        });

                        iv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                pvKaiTime.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvKaiTime.setPicker(kaiTimeList);

    }

    /**
     * 停售时间选项
     * */
    protected void initPickerStopSaleTime() {//条件选择器初始化，自定义布局

        pvStopTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = stopTimeList.get(options1).getPickerViewText();
                String key = stopTimeList.get(options1).getId();

                stopSaleTimeFlag = key;
                tv_stop_sale_time.setText(value);


                if("1".equals(stopSaleTimeFlag)){
                    salesEnddate = null;
                    tv_stop_sale_custom_time.setText("");
                    ll_stop_sale_custom_time.setVisibility(View.GONE);
                }else {
                    tv_stop_sale_time.setText("自定义");
                    ll_stop_sale_custom_time.setVisibility(View.VISIBLE);
                }
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView iv_submit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView iv_cancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        iv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pvStopTime.returnData();
                                pvStopTime.dismiss();

                            }
                        });

                        iv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                pvStopTime.dismiss();
                            }
                        });



                    }
                })
                .build();


        pvStopTime.setPicker(stopTimeList);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST10: //
                    selectList = PictureSelector.obtainMultipleResult(data);

                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;

                case PictureConfig.CHOOSE_REQUEST: //商品详情 相册选中
                    selectGoodDetailList = PictureSelector.obtainMultipleResult(data);

                    if(selectGoodDetailList != null && selectGoodDetailList.size()>0){
                        LocalMedia localMedia = selectGoodDetailList.get(0);
                        if(localMedia != null){
                            String path = localMedia.getCompressPath();
                            addGoodsImage(path);
                        }
                    }
                    break;

                case REQUEST_FILE_CAMERA_CODE: //商品详情 拍摄

                    takePictureFromCamera();

                    break;
            }
        }
    }


    private void initKaiAndStopTimePicker() {
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
//                String dateTime = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
                if("KAI".equals(isSelectTime)){
                    tv_kai_sale_custom_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd HH:mm")).replace("-","."));
                }else if("STOP".equals(isSelectTime)){
                    tv_stop_sale_custom_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd HH:mm")).replace("-","."));
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
                                String startDate  = tv_kai_sale_custom_time.getText().toString().trim();
                                String endDate = tv_stop_sale_custom_time.getText().toString().trim();
                                String stDate = "";
                                String enDate = "";
                                if("KAI".equals(isSelectTime)){
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        stDate = startDate+":00";
                                        enDate = endDate +":59";
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            tv_kai_sale_custom_time.setText("");
                                            ToastUtils.showToast("开售自定义时间不可大于停售自定义时间！");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
                                        pvCustomTime.dismiss();
                                    }

                                }else {
                                    if(!startDate.isEmpty() && !endDate.isEmpty()){
                                        Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                        Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                        if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                            ToastUtils.showToast("停售自定义时间不可小于开售自定义时间！");
                                            tv_stop_sale_custom_time.setText("");
                                        }else {
                                            pvCustomTime.dismiss();
                                        }


                                    }else {
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


    /**
     * 设置销售时间设置
     * */
   /* private void showStartSaleListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);


        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(startSaleList);

        listView.setAdapter(adapter);

        for(CommonConstantBean constantBean:startSaleList){
            if(startSaleValue.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }
        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        startSalePop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:startSaleList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = startSaleList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                startSalePop.dismiss();

                tv_start_sale_time_selector.setText(commonConstantBean.getKey());
                startSaleValue = commonConstantBean.getValue();
                if("1".equals(startSaleValue)){ // 立即开售
                    tv_start_sale_time.setText("");
                    ll_start_sale_time.setVisibility(View.GONE);
                }else if("0".equals(startSaleValue)){  // 自定义
                    ll_start_sale_time.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void showStopSaleListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_consume_join,null);
        //处理popWindow 显示内容
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);


        SelectConstantListViewAdapter adapter = new SelectConstantListViewAdapter(MemberCardNewActivity.this);
        adapter.setDatas(stopSaleList);

        listView.setAdapter(adapter);

        for(CommonConstantBean constantBean:stopSaleList){
            if(stopSaleValue.equals(constantBean.getValue())){
                constantBean.setChecked(true);
            }

        }
        adapter.notifyDataSetChanged();
        //创建并显示popWindow
        stopSalePop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tv_consume_join);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(CommonConstantBean constantBean:stopSaleList){
                    constantBean.setChecked(false);
                }
                CommonConstantBean commonConstantBean = stopSaleList.get(position);
                commonConstantBean.setChecked(true);
                adapter.notifyDataSetChanged();
                stopSalePop.dismiss();

                tv_stop_sale_time_selector.setText(commonConstantBean.getKey());
                stopSaleValue = commonConstantBean.getValue();
                if("1".equals(stopSaleValue)){ // 无停用时间
                    tv_stop_sale_time.setText("");
                    ll_stop_sale_time.setVisibility(View.GONE);
                }else if("0".equals(stopSaleValue)){  // 自定义
                    ll_stop_sale_time.setVisibility(View.VISIBLE);
                }

            }
        });
    }
*/



    private void setPopDialog() {

        View customView = View.inflate(this, R.layout.layout_shop_manage_preview_dialog, null);


        AlertDialog.Builder builder  = new AlertDialog.Builder(ShopManageNewActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        CustomViewPager viewPager =  (CustomViewPager)customView.findViewById(R.id.viewPager);
        dotsLayout = (LinearLayout) customView.findViewById(R.id.dots);
        TextView tv_product_name  = (TextView)customView.findViewById(R.id.tv_product_name);
        TextView tv_sale_price  = (TextView)customView.findViewById(R.id.tv_sale_price);
        TextView tv_market_price  = (TextView)customView.findViewById(R.id.tv_market_price);
        tv_market_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        LinearLayout ll_rebate_amt = (LinearLayout) customView.findViewById(R.id.ll_rebate_amt);
        TextView tv_rebate_amt  = (TextView)customView.findViewById(R.id.tv_rebate_amt);
        TextView tv_product_spec  = (TextView)customView.findViewById(R.id.tv_product_spec);
        //可自提
        LinearLayout ll_ziti= (LinearLayout) customView.findViewById(R.id.ll_ziti);



        //领取方式
        TextView tv_delivery_way  = (TextView)customView.findViewById(R.id.tv_delivery_way);
        //可邮寄
        View view_line  = (View)customView.findViewById(R.id.view_line);
        LinearLayout ll_youji= (LinearLayout) customView.findViewById(R.id.ll_youji);
        TextView tv_post_youji  = (TextView)customView.findViewById(R.id.tv_post_youji);

        //选择店铺
        llSelectStorePre = (LinearLayout) customView.findViewById(R.id.ll_select_store);

        LinearLayout ll_goods_detail  = (LinearLayout)customView.findViewById(R.id.ll_goods_detail);
        setProductExtendDesc();
        if(productExtendDescList != null && productExtendDescList.size()>0){
            for (ProductExtendDesc productExtendDesc : productExtendDescList){
                String type = productExtendDesc.getMediaType();
                String content = productExtendDesc.getProductContent();
                if("1".equals(type)){
                    addGoodsImageDialog(HttpUrls.IMAGE_ULR + content,ll_goods_detail);
                }else if("2".equals(type)){
                    addGoodsEditDialog(content,ll_goods_detail);
                }
            }
        }

        TextView tv_remark  = (TextView)customView.findViewById(R.id.tv_remark);

        List<View> dataList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < selectList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setImageResource(images[i]);
            String imgUrl = selectList.get(i).getPath();
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_logo_default)
                    .error(R.drawable.ic_logo_default)
                    .fallback(R.drawable.ic_logo_default);

            Glide.with(this).load(imgUrl).apply(requestOptions).into(imageView);

            dataList.add(imageView);
        }


        setDotLayout();
        viewPager.setAdapter(new ViewPagerAdatper(dataList));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                setMoveDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        String productName = edt_product_name.getText().toString().trim();
        String productCate = tv_product_cate.getText().toString().trim();
        String salePrice = edt_sale_price.getText().toString().trim();
        String marketPrice = edt_market_price.getText().toString().trim();

        String rebateAmt = edt_rebate_amt.getText().toString().trim();
        String productSpec = edt_product_spec.getText().toString().trim();
        tv_product_name.setText(productName != null && !"".equals(productName)?productName:"");
        tv_product_cate.setText(productCate != null && !"".equals(productCate)?productCate:"");
        tv_sale_price.setText(salePrice != null && !"".equals(salePrice)?salePrice+"元":" 元");
        tv_market_price.setText(marketPrice != null && !"".equals(marketPrice)?marketPrice+"元":" 元");
        if("1".equals(rebateFlag)){  //是
            ll_rebate_amt.setVisibility(View.VISIBLE);
            tv_rebate_amt.setText(rebateAmt != null && !"".equals(rebateAmt)?rebateAmt+"元":" 元");
        }else {
            ll_rebate_amt.setVisibility(View.GONE);
        }

        tv_product_spec.setText(productSpec != null && !"".equals(productSpec)?productSpec:" ");

        String deliveryDay = edt_lingqu_day.getText().toString().trim(); //下单多少天领取

        String startDate = tv_valid_start_time.getText().toString().trim();
        String endDate = tv_valid_end_time.getText().toString().trim();

        if("0".equals(deliveryType)){  //自提
            ll_ziti.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.GONE);
            ll_youji.setVisibility(View.GONE);
        }else if("1".equals(deliveryType)){ //邮寄
            ll_ziti.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
            ll_youji.setVisibility(View.VISIBLE);

        }else {  //自提、邮寄
            ll_ziti.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            ll_youji.setVisibility(View.VISIBLE);
        }
        if("R1".equals(deliveryDaytype)){ //固定时长
            if(deliveryDay != null && !"".equals(deliveryDay)){
                tv_delivery_way.setText("可在下单后"+deliveryDay+"天内领取。");
            }else {
                tv_delivery_way.setText("可在下单后 天内领取。");
            }

        }else if("A".equals(deliveryDaytype)){  //固定时间
            if(startDate != null && !"".equals(startDate)){
                if(endDate != null && !"".equals(endDate)){
                    tv_delivery_way.setText(startDate +"至"+endDate);
                }else {
                    tv_delivery_way.setText(startDate +" 至       ");
                }
            }else {
                if(endDate != null && !"".equals(endDate)){
                    tv_delivery_way.setText("       至" + endDate);
                }else{
                    tv_delivery_way.setText("       至         ");
                }
            }

        }
        deliveryFee = edt_youji_fee.getText().toString().trim();
        if(deliveryFee != null && !"".equals(deliveryFee)){
            tv_post_youji.setText("邮费"+deliveryFee+"元每件");
        }else{
            tv_post_youji.setText("显示(店铺包邮)");
        }

//        String productDetail = edt_product_remark.getText().toString().trim();
        String remark = edt_buy_know.getText().toString().trim();
//        tv_product_detail.setText(productDetail != null && !"".equals(productDetail)?productDetail:" ");
        tv_remark.setText(remark != null && !"".equals(remark)?remark:" ");


        llSelectStorePre.removeAllViews();
        if("A".equals(storeType)){   //全部店铺  自营  加盟
            //storeName 为全部店铺  自营  加盟
            for (Store store : storeList) {
                View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_manage_new_select_store, null);
                Button btn = (Button)view.findViewById(R.id.btn_store_name);
                String name = store.getStoreName();
                if (store != null) {
                    btn.setText(name);
                }
                llSelectStorePre.addView(view);
            }




        }else if("D".equals(storeType)) {//自营
            for (Store store : storeList) {
                String statusQuo = "";
                if(store.getStatusQuo()!= null && store.getStatusQuo().intValue()>0){
                    statusQuo = String.valueOf(store.getStatusQuo());
                    if("1".equals(statusQuo)){
                        View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_manage_new_select_store, null);
                        Button btn = (Button)view.findViewById(R.id.btn_store_name);
                        String name = store.getStoreName();
                        if (store != null) {
                            btn.setText(name);
                        }
                        llSelectStorePre.addView(view);
                    }
                }

            }
        }else if("J".equals(storeType)) {//加盟
            for (Store store : storeList) {
                String statusQuo = "";
                if(store.getStatusQuo()!= null && store.getStatusQuo().intValue()>0){
                    statusQuo = String.valueOf(store.getStatusQuo());
                    if("2".equals(statusQuo)){
                        View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_manage_new_select_store, null);
                        Button btn = (Button)view.findViewById(R.id.btn_store_name);
                        String name = store.getStoreName();
                        if (store != null) {
                            btn.setText(name);
                        }
                        llSelectStorePre.addView(view);
                    }
                }

            }
        }else if("H".equals(storeType)) {  //H为自定义店铺
            for (Store store : storeList) {
                if (store != null) {
                    if (store.isChecked()) {
                        View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_manage_new_select_store, null);
                        Button btn = (Button)view.findViewById(R.id.btn_store_name);
                        String name = store.getStoreName();
                        if (store != null) {
                            btn.setText(name);
                        }
                        llSelectStorePre.addView(view);
                    }

                }
            }
        }


        Button btn_use = (Button) customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        LinearLayout ll_right = (LinearLayout)customView.findViewById(R.id.ll_right);
        ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }

    private void setMoveDot(int position){
        for (int i = 0; i < mDotsIV.size(); i++) {
            if (i == position) {
                mDotsIV.get(i).setImageResource(R.drawable.ic_dot_red);
            } else {
                mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
    }

    protected void setDotLayout(){
        //先删除
        mDotsIV.clear();
        dotsLayout.removeAllViews();
        for (int i = 0; i < selectList.size(); i++) {
            ImageView dotIV = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 4;
            dotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.ic_dot_red);
            } else {
                dotIV.setImageResource(R.drawable.dot_blur);
            }
            mDotsIV.add(dotIV);
        }

    }


    protected void removeMediaList(List<ProductMedia> productMediaList){
        if(productMediaList != null && productMediaList.size()>0){
            for(int i=0;i<productMediaList.size();i++){
                for(int j=productMediaList.size()-1;j>i;j--){
                    if((productMediaList.get(i).getImgUrl().equals(productMediaList.get(j).getImgUrl()))){
                        productMediaList.remove(j);
                    }
                }
            }
        }

    }


    //选择配送方式
    protected void showPostWay() {
        View customView = View.inflate(this, R.layout.layout_goods_manage_select_post_way, null);
        AlertDialog.Builder builder  = new AlertDialog.Builder(ShopManageNewActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        //自提
        ImageView iv_ziti = (ImageView) customView.findViewById(R.id.iv_ziti);

        //统计文字个数
        ImageView iv_youji = (ImageView) customView.findViewById(R.id.iv_youji);
        Button btn_use = (Button)customView.findViewById(R.id.confirmBtn);
        Button cancelBtn = (Button)customView.findViewById(R.id.cancelBtn);



        if(isSelectYouji &&  isSelectZiti){
           /* ll_ziti.setVisibility(View.VISIBLE);
            ll_youji.setVisibility(View.VISIBLE);
            iv_youji_clear.setVisibility(View.VISIBLE);
            iv_ziti_clear.setVisibility(View.VISIBLE);
            deliveryType = "2";
            tv_select_post_way.setText("到店自提、快递邮寄");*/
            iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_selected);
            iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_selected);

        }else {
            if(isSelectYouji && !isSelectZiti){
                /*ll_ziti.setVisibility(View.GONE);
                ll_youji.setVisibility(View.VISIBLE);
                iv_youji_clear.setVisibility(View.GONE);
                deliveryType = "1";
                tv_select_post_way.setText("快递邮寄");*/
                iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_unselect);
                iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_selected);

            }else if(!isSelectYouji && isSelectZiti){
               /* ll_ziti.setVisibility(View.VISIBLE);
                iv_ziti_clear.setVisibility(View.GONE);
                ll_youji.setVisibility(View.GONE);
                deliveryType = "0";
                tv_select_post_way.setText("到店自提");*/
                iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_selected);
                iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_unselect);

            }
        }

        iv_ziti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSelectZiti){
                    isSelectZiti = false;
                    iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_unselect);
//                    ll_ziti.setVisibility(View.GONE);
                    //若到店自提隐藏则固定时长默认值清空在编辑详情页面
                    edt_lingqu_day.setText("");
                    delivDay = null;
                }else {
                    isSelectZiti = true;
                    iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_selected);
                }

            }
        });
        iv_youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSelectYouji){
                    isSelectYouji = false;
                    iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_unselect);
                }else {
                    isSelectYouji = true;
                    iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_selected);
                }

            }
        });

        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isSelectYouji &&  isSelectZiti){
                    ll_ziti.setVisibility(View.VISIBLE);
                    ll_youji.setVisibility(View.VISIBLE);
                    iv_youji_clear.setVisibility(View.VISIBLE);
                    iv_ziti_clear.setVisibility(View.VISIBLE);
                    deliveryType = "2";
                    tv_select_post_way.setText("到店自提、快递邮寄");
                }else {
                    if(isSelectYouji && !isSelectZiti){
                        ll_ziti.setVisibility(View.GONE);
                        ll_youji.setVisibility(View.VISIBLE);
                        iv_youji_clear.setVisibility(View.GONE);
                        iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_unselect);
                        iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_selected);
                        deliveryType = "1";
                        tv_select_post_way.setText("快递邮寄");
                    }else if(!isSelectYouji && isSelectZiti){
                        ll_ziti.setVisibility(View.VISIBLE);
                        iv_ziti_clear.setVisibility(View.GONE);
                        ll_youji.setVisibility(View.GONE);

                        iv_ziti.setImageResource(R.drawable.ic_goods_post_ziti_bg_selected);
                        iv_youji.setImageResource(R.drawable.ic_goods_post_youji_bg_unselect);
                        deliveryType = "0";
                        tv_select_post_way.setText("到店自提");
                    }else if(!isSelectYouji && !isSelectZiti){
                        ToastUtils.showShort("至少任选一个");
                        return;
                    }
                }

                dialog.dismiss();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
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
    }

    //快递邮寄方式： 包邮、消费者支付
    protected void initPickerYoujiWay() {//条件选择器初始化，自定义布局

        pvYoujiFee = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String value = youjiList.get(options1).getPickerViewText();
                String key = youjiList.get(options1).getId();

                youjiFeeIsDisplayFlag = key;
                tv_youji_fee_type.setText(value);
                if("1".equals(youjiFeeIsDisplayFlag)){
                    rl_youji_fee.setVisibility(View.GONE);
                }else {
                    rl_youji_fee.setVisibility(View.VISIBLE);
                }
            }
        })
                .setLayoutRes(R.layout.layout_pickerview_custom_position, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final ImageView iv_submit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView iv_cancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        iv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                pvYoujiFee.returnData();
                                pvYoujiFee.dismiss();

                            }
                        });

                        iv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                pvYoujiFee.dismiss();
                            }
                        });



                    }
                })
                .build();


        pvYoujiFee.setPicker(youjiList);

    }


    private void selectPrizeImg(){
        PopWindow popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction("从相机中选择",PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {

//                        Intent intent1  = new Intent(ShopManageNewActivity.this, PrizeTempletActivity.class);
//
//
//                        ActivityUtils.startActivity(intent1,R.anim.slide_in_right,R.anim.slide_out_left);

                        selectGoodDetailList = new ArrayList<>();
                        int config = PictureConfig.CHOOSE_REQUEST;

                        themeId = R.style.picture_default_style;
                        aspect_ratio_x = 1;
                        aspect_ratio_y = 1;
//                                PictureSelector.create(getActivity()).themeStyle(themeId).openExternalPreview(1, selectList);
                        PictureSelectionModel pictureSelector = PictureSelector.create(ShopManageNewActivity.this)
                                .openGallery(chooseMode).theme(themeId)
                                .minSelectNum(1).imageSpanCount(4).previewImage(true)// 是否可预览图片
                                .previewVideo(false)// 是否可预览视频
                                .enablePreviewAudio(false).isCamera(false)// 是否显示拍照按钮
                                .isZoomAnim(true).enableCrop(false)// 是否裁剪
                                .compress(true)// 是否压缩
                                .synOrAsy(true).glideOverride(160, 300)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(false)
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                                .isGif(false)// 是否显示gif图片
                                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                                .circleDimmedLayer(false)// 是否圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                .openClickSound(false)// 是否开启点击声音
                                .selectionMedia(selectGoodDetailList).minimumCompressSize(100);// 小于100kb的图片不压缩

                        pictureSelector .maxSelectNum(1);// 最大图片选择数量
                        pictureSelector.selectionMode(PictureConfig.SINGLE);
                        pictureSelector.forResult(config);

                    }
                }))
                .addItemAction(new PopItemAction("拍摄", PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {

                        takeCameraPhoto();
                    }
                }))

                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                .create();
        popWindow.show();
    }


    public void takeCameraPhoto() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Toast.makeText(this, "设备无摄像头", Toast.LENGTH_SHORT).show();
            return;
        }

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

//        File storageDir = new File(filePath);
//        storageDir.mkdirs();
        mFileFromCamera = new File(filePath, System.nanoTime() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUrl;
        if (getApplicationInfo().targetSdkVersion > Build.VERSION_CODES.M) {  //19系统版本
            String authority = "com.zwx.scan.app.widget.webview.UploadFileProvider";
            imgUrl = FileProvider.getUriForFile(this, authority, mFileFromCamera);
        } else {
            imgUrl = Uri.fromFile(mFileFromCamera);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);
        startActivityForResult(intent, REQUEST_FILE_CAMERA_CODE);
    }


    /**
     * 处理相机返回的图片
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void takePictureFromCamera() {
        if (mFileFromCamera != null && mFileFromCamera.exists()) {
            String filePath = mFileFromCamera.getAbsolutePath();
            // 压缩图片到指定大小
//            File imgFile = ZpImageUtils.compressImage(this, filePath, COMPRESS_MIN_WIDTH, COMPRESS_MIN_HEIGHT, IMAGE_COMPRESS_SIZE_DEFAULT);
            addGoodsImage(filePath);
           /* Uri localUri = Uri.fromFile(imgFile);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            this.sendBroadcast(localIntent);
            Uri result = Uri.fromFile(imgFile);*/

            //Uri.parse(filePath)、new Uri[]{result}


            /*if (editor.isSort()) tvSort.setText("排序");
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                String[] photoPaths = data.getStringArrayExtra(PhotoPickerActivity.INTENT_PHOTO_PATHS);
                editor.addImageArray(photoPaths);
            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                editor.addImage(mCurrentPhotoFile.getAbsolutePath());
            }*/



        }
    }


    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private void dealEditData(List<SEditorData> editList) {
        for (SEditorData itemData : editList) {
            if (itemData.getInputStr() != null) {
                Log.d("RichEditor", "commit inputStr=" + itemData.getInputStr());
            } else if (itemData.getImagePath() != null) {
                Log.d("RichEditor", "commit imgePath=" + itemData.getImagePath());
            }
        }
    }


    protected void addGoodsEdit(String content){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_good_detail_content_edit, null);

        EditText editText = (EditText) view.findViewById(R.id.edt_content);
        editText.setText(content);
//        goodDetailViewList.add(editText);
        TextView textView = (TextView) view.findViewById(R.id.tv_content_num);

        LinearLayout ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);

        if (curView >= 0) {
            mDelList.add(curView, ll_delete);
            curView++;
        }
        if("s".equals(productStatu)){
            editText.setEnabled(false);
            ll_delete.setVisibility(View.GONE);
            if(content != null && !"".equals(content)){
                textView.setText(String.format("%d/%d", content.length(), TITLE_WORD_LIMIT_COUNT));
            }
        }else {
            editText.setEnabled(true);
            ll_delete.setVisibility(View.VISIBLE);
        }
        if(content != null && !"".equals(content)){
            textView.setText(String.format("%d/%d", content.length(), TITLE_WORD_LIMIT_COUNT));
        }else {
            textView.setText("0/500");
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String titleStr = editText.getText().toString();

                if(titleStr != null &&  !"".equals(titleStr)){

                }else {
                    titleStr = content;
                }
                textView.setText(String.format("%d/%d", titleStr.length(), TITLE_WORD_LIMIT_COUNT));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RelativeLayout parentView = (RelativeLayout) v.getParent();
//                onImageDeleteClick(parentView);
                delItem(v);
            }
        });

        ll_goods_detail.addView(view);
    }

    protected void addGoodsImage(String path){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_good_detail_image, null);

        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
        TextView tv_path = (TextView) view.findViewById(R.id.tv_path);
        tv_path.setText(path);
//        goodDetailViewList.add(iv_image);
        LinearLayout ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
        if (curView >= 0) {
            mDelList.add(curView, ll_delete);
            curView++;
        }
        if("s".equals(productStatu)){
            view.setEnabled(false);
            ll_delete.setVisibility(View.GONE);
        }else {
            view.setEnabled(true);
            ll_delete.setVisibility(View.VISIBLE);
        }
        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delItem(v);

            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels- ScreenUtils.dip2px(this,40);
        int screenWidth = width;

        ViewGroup.LayoutParams lp = iv_image.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        iv_image.setLayoutParams(lp);

        iv_image.setMaxWidth(screenWidth);
        iv_image.setMaxHeight((int) (screenWidth * 3));
//        RoundedCorners roundedCorners= new RoundedCorners(8);
        RequestOptions requestOptions2 = new RequestOptions()
//                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(path).apply(requestOptions2).into(iv_image);
        /*Glide.with(this).load(path).into(new SimpleTarget<Drawable>(50,50) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_image.setBackground(resource);
                }
            }
        });*/
        ll_goods_detail.addView(view);
    }

    protected void addGoodsEditDialog(String content,LinearLayout ll_goods_detail){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_good_detail_content_edit_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText(content);

        ll_goods_detail.addView(view);
    }

    protected void addGoodsImageDialog(String content,LinearLayout ll_goods_detail){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_good_detail_image_dialog, null);

        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int   width = ScreenUtils.dip2px(this,219);
        int screenWidth = width;

        ViewGroup.LayoutParams lp = iv_image.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        iv_image.setLayoutParams(lp);

        iv_image.setMaxWidth(screenWidth);
        iv_image.setMaxHeight((int) (screenWidth * 2));
        RequestOptions requestOptions2 = new RequestOptions()
//                .bitmapTransform(roundedCorners)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(content).apply(requestOptions2).into(iv_image);

        ll_goods_detail.addView(view);
    }
    /**
     * 添加或者删除图片View时的Transition动画
     */
    private LayoutTransition mTransitioner;

    /**
     * 初始化transition动画
     */
    private void setupLayoutTransitions(LinearLayout containerLayout) {
        mTransitioner = new LayoutTransition();
        ll_goods_detail.setLayoutTransition(mTransitioner);
        mTransitioner.setDuration(300);
    }

    /**
     * @param v 删除一个新的EditText条目
     */
    private void delItem(View v) {
        if (v == null) {
            return;
        }

        int curViewTwo = -1;
        for (int i = 0; i < mDelList.size(); i++) {
            if (mDelList.get(i) == v) {
                curViewTwo = i;
                break;
            }
        }

        if (curViewTwo >= 0) {
            if(productExtendDescList != null && productExtendDescList.size()>0){

                productExtendDescList.remove(curViewTwo);
            }

            mDelList.remove(curViewTwo);
            ll_goods_detail.removeViewAt(curViewTwo);
            curView--;

            if(curView<= -1){
                curView = 0;
            }
        }
    }



    private void deleteProductExtendDesc(View v){
        if(goodDetailViewList != null && goodDetailViewList.size()>0){
            for (int i = 0; i < goodDetailViewList.size(); i++) {
                if (goodDetailViewList.get(i) == v) {
                    if(productExtendDescList != null && productExtendDescList.size()>0){
                        productExtendDescList.remove(i);
                    }
                    i--;
                    break;
                }
            }
        }
    }


    private void setProductExtendDesc() {

        if("NEW".equals(productEditStatu) ){
            productExtendDescList = new ArrayList<>();
            for (int i = 0; i < ll_goods_detail.getChildCount(); i++) {
                View childAt = ll_goods_detail.getChildAt(i);

                ProductExtendDesc  productExtendDesc = new ProductExtendDesc();
                if(childAt instanceof FrameLayout ){
                    FrameLayout fl_image = childAt.findViewById(R.id.fl_image);
                    ImageView imageView = (ImageView)fl_image.findViewById(R.id.iv_image);
                    TextView textView = (TextView) fl_image.findViewById(R.id.tv_path);
                    productExtendDesc.setMediaType("1");
                    String path = textView.getText().toString().trim();
                    productExtendDesc.setProductContent(path);
                    productExtendDescList.add(productExtendDesc);
                }else if(childAt instanceof RelativeLayout){
                    RelativeLayout rl_edit = childAt.findViewById(R.id.rl_edit);
                    EditText editText =(EditText)rl_edit.findViewById(R.id.edt_content);
                    String content = editText.getText().toString().trim();
                    if(content != null && !"".equals(content)){
                        productExtendDesc.setMediaType("2");
                        productExtendDesc.setProductContent(content);
                        productExtendDescList.add(productExtendDesc);
                    }
                }
            }
        }else if("EDIT".equals(productEditStatu)){
            productExtendDescList = new ArrayList<>();
            for (int i = 0; i < ll_goods_detail.getChildCount(); i++) {
                View childAt = ll_goods_detail.getChildAt(i);


                ProductExtendDesc  productExtendDesc = new ProductExtendDesc();
//                if(childAt == fl_image ||childAt instanceof FrameLayout ){
                if(childAt instanceof FrameLayout ){
                    FrameLayout fl_image = childAt.findViewById(R.id.fl_image);
                    ImageView imageView = (ImageView)fl_image.findViewById(R.id.iv_image);
                    TextView textView = (TextView) fl_image.findViewById(R.id.tv_path);
                    String path = textView.getText().toString().trim();
                    if(path.contains("get.do")){
                        String picPictureId = path.substring(path.indexOf("get.do?id=")+10,path.length());
                        productExtendDesc.setProductContent(picPictureId);
                    }else {
                        productExtendDesc.setProductContent(path);
                    }
                    productExtendDesc.setMediaType("1");
                    productExtendDescList.add(productExtendDesc);
//                }else if( childAt== rl_edit ||childAt instanceof RelativeLayout ){
                }else if(childAt instanceof RelativeLayout){
                    RelativeLayout rl_edit = childAt.findViewById(R.id.rl_edit);
                    EditText editText =(EditText)rl_edit.findViewById(R.id.edt_content);
                    String content = editText.getText().toString().trim();
                    if(content != null && !"".equals(content)){
                        productExtendDesc.setMediaType("2");
                        productExtendDesc.setProductContent(content);
                        productExtendDescList.add(productExtendDesc);
                    }
                }

            }
        }

    }

}
