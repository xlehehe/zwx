package com.zwx.scan.app.feature.cateringinfomanage;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.library.expandablelayout.ExpandableRelativeLayout;
import com.zwx.library.pickerview.picker.builder.OptionsPickerBuilder;
import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnOptionsSelectListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.OptionsPickerView;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.adapter.SelectConstantListViewAdapter;
import com.zwx.scan.app.data.bean.Area;
import com.zwx.scan.app.data.bean.Campaign;
import com.zwx.scan.app.data.bean.CardBean;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.data.bean.StoreParamBean;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.Address;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AddressBean;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AreaAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.AreaPickerView;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.CityAdapter;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.ProvinceAdapter;
import com.zwx.scan.app.feature.member.CommonConstantBean;
import com.zwx.scan.app.feature.member.MemberCardNewActivity;
import com.zwx.scan.app.utils.AMapUtil;
import com.zwx.scan.app.widget.ContainsEmojiEditText;
import com.zwx.scan.app.widget.downloadfile.DefaultRetryPolicy;
import com.zwx.scan.app.widget.downloadfile.DownloadRequest;
import com.zwx.scan.app.widget.downloadfile.DownloadStatusListenerV1;
import com.zwx.scan.app.widget.downloadfile.RetryPolicy;
import com.zwx.scan.app.widget.downloadfile.ThinDownloadManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreInfoManageActivity extends BaseActivity<CateringInfoContract.Presenter>  implements CateringInfoContract.View,View.OnClickListener,LocationSource,AMapLocationListener, AMap.OnMapTouchListener {

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;


    @BindView(R.id.tv_store_name)
    protected TextView tvStoreName;
    @BindView(R.id.edt_manager_name)
    protected ContainsEmojiEditText edtManagerName;
    @BindView(R.id.edt_manager_phone)
    protected EditText edtManagerPhone;

    @BindView(R.id.edt_store_phone)
    protected EditText edtStorePhone;
    //经营模式
    @BindView(R.id.bus_mode)
    protected TextView bus_mode;

    @BindView(R.id.tv_province_city_dist)
    protected TextView tvProCityDist;


    @BindView(R.id.rv_photo)
    protected RecyclerView rvPhoto;




    @BindView(R.id.rl_address)
    protected RelativeLayout rlAddress;
    @BindView(R.id.edt_address)
    protected EditText edtAddress;
    @BindView(R.id.map)
    protected MapView mapView;

    @BindView(R.id.rl_mode)
    protected RelativeLayout rl_mode;


    @BindView(R.id.rl_main_categories)
    protected RelativeLayout rl_main_categories;

    @BindView(R.id.main_categories)
    protected TextView main_categories;

    @BindView(R.id.render_services)
    protected TextView render_services;
    @BindView(R.id.rl_service)
    protected RelativeLayout rl_service;
    //宴席
    @BindView(R.id.rl_banquet)
    protected RelativeLayout rl_banquet;

    @BindView(R.id.render_banquet)
    protected TextView render_banquet;

//    @BindView(R.id.rl_time)
//    protected RelativeLayout rl_time;

   /* @BindView(R.id.business_time1)
    protected TextView start_time;

    @BindView(R.id.business_time2)
    protected TextView end_time;*/

    @BindView(R.id.business_time)
    protected EditText edtTime;

    @BindView(R.id.ll_qrc)
    protected LinearLayout ll_qrc;
    @BindView(R.id.iv_qrc)
    protected ImageView ivQrc;

    @BindView(R.id.btn_download)
    protected Button btn_download;
    @BindView(R.id.btn_save)
    protected Button btn_save;


    @BindView(R.id.ll_arrow)
    protected LinearLayout ll_arrow;
    @BindView(R.id.iv_arrow)
    protected ImageView iv_arrow;
    @BindView(R.id.tv_arrow)
    protected TextView tv_arrow;

    private FullyGridLayoutManager manager;
    protected GridImageAdapter adapter;

    protected List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 5;
    private int themeId;
    //图片 模式
    private int chooseMode = PictureMimeType.ofImage();
    //图片长宽比例
    private int aspect_ratio_x, aspect_ratio_y;

//    protected AreaPickerView areaPickerView;
//    protected List<AddressBean> addressBeans;

    protected List<AddressBean> addressBeanList = new ArrayList<>();
    private List<Address> addressList;
    protected int[] i;

    //经营模式
    protected OptionsPickerView pvBusMode;

    //时间
    private TimePickerView  pvCustomTime;
    protected List<CardBean> cardBeans = new ArrayList<>();
    //经营模式
    private String mode ;
    //提供服务
    private  ArrayList<TypeBean> typeList1 = new ArrayList<>();
    //提供宴席
    private  ArrayList<TypeBean> typeList2 = new ArrayList<>();

    //经营范围
    private  ArrayList<TypeBean> typeList3 = new ArrayList<>();

    protected static  int RESULT_CODE = 7777;
    protected   int REQUEST_CODE_ONE = 7001;
    protected   int REQUEST_CODE_TWO = 7002;
    protected   int REQUEST_CODE_THREE = 7003;

    protected String serviceId;
    protected String serviceName;
    protected String banquetId;
    protected String banquetName;

    protected String categoriesId;
    protected String categoriesName;

    private AMap aMap;
    //地图绘制圆形
    private Circle circle;
    //坐标和经纬度转换工具
    Projection projection;
    boolean useMoveToLocationWithMapMode = true;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private int currentPage = 0;// 当前页面，从0开始计数
    protected LatLonPoint lp = new LatLonPoint(39.993743, 116.472995);// 116.472995,39.993743

    protected  LatLng latLng = new LatLng(39.993743, 116.472995);
    private Marker locationMarker; // 选择的点
    protected  double latitude;
    protected double longitude;
    private UiSettings mUiSettings;
    private String storeId;

    protected long provinceId;
    protected long cityId;
    protected long distId;
    protected String provinceName;
    protected String cityName;
    protected String distName;

    protected  String brandName;

    //二维码id
    protected  String qrCodeId;

    protected boolean isStartTime = false;
    protected  String storeName;

    protected String storeType;
    protected  String managerName;

    protected  String managerTel;
    protected  String storeTel;
    protected String address;
    protected  String startTime;
    protected String endTime;

    protected String photos;
    protected String serviceDate; //营业时间

    protected  List<LocalMedia> localMediaList = new ArrayList<>();
    public static List<String> photoList = new ArrayList<>();


    //主营
    protected ArrayList<CateBean>  directoryDataCP = new ArrayList<>();

    //服务
    protected ArrayList<CateBean>  directoryDataCT = new ArrayList<>();

    //宴席
    protected ArrayList<CateBean>  directoryDataYX = new ArrayList<>();
    protected Store store = new Store();
    protected Area province = new Area();
    protected Area dist = new Area();
    protected Area city = new Area();


    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;

    public List<File> fileList = new ArrayList<>();


    private boolean isExpand = true;
    Bundle savedInstanceState;

    LocationManager locationManager;

    GeocodeSearch geocodeSearch = null;
    GeocodeQuery geocodeQuery = null;


    protected int distPostion;
    protected int cityPostion;
    protected int provincePostion;


    //==============================================

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView ivBtn;

    AreaPickerViewCallback areaPickerViewCallback;
    /**
     * View的集合
     */
    List<View> views = new ArrayList<>();
    /**
     * tab的集合
     */
    List<String> strings = new ArrayList<>();
    /**
     * 省
     */
    List<AddressBean> addressBeans = new ArrayList<>();
    /**
     * 市
     */
    List<AddressBean.CityBean> cityBeans = new ArrayList<>();
    /**
     * 区
     */
    List<AddressBean.CityBean.AreaBean> areaBeans = new ArrayList<>();

//    Context context;

    ViewPagerAdapter viewPagerAdapter;
    ProvinceAdapter provinceAdapter;
    CityAdapter cityAdapter;
    AreaAdapter areaAdapter;

    /**
     * 选中的区域下标 默认-1
     */
    int provinceSelected = -1;
    int citySelected = -1;
    int areaSelected = -1;

    /**
     * 历史选中的区域下标 默认-1
     */
    int oldProvinceSelected = -1;
    int oldCitySelected = -1;
    int oldAreaSelected = -1;

    RecyclerView areaRecyclerView;
    RecyclerView cityRecyclerView;

    boolean isCreate;

    PopWindow areaPop = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_info_manage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }



    @Override
    protected void initView() {
        mapView.onCreate(this.savedInstanceState);
        DaggerCateringInfoComponent.builder().applicationComponent(AppContext.getApplicationComponent())
                .cateringInfoModule(new CateringInfoModule(this))
                .build().inject(this);
        tvTitle.setText(getResources().getString(R.string.store_info_manage));
        edtManagerName.setSelection(edtManagerName.getText().toString().length());
        edtStorePhone.setSelection(edtStorePhone.getText().toString().length());
        edtManagerPhone.setSelection(edtManagerPhone.getText().toString().length());
        initPicAdapter();
        initOptionPicker();
        initAddress();
        initMapView();

        edtTime.setFilters(new InputFilter[]{inputFilter});

    }



    //完美解决输入框中不能输入的非法字符

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


    protected void initMapView(){
        geocodeSearch = new GeocodeSearch(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(aMap == null){
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

            aMap.setOnMapTouchListener(this);
//            aMap.setLocationSource(this);
            locationMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_point)))
                    .position(new LatLng(lp.getLatitude(), lp.getLongitude())));
//                    .position(new LatLng(lp.getLatitude(), lp.getLongitude())));
            locationMarker.showInfoWindow();
            aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
//                    setUp(aMap);
                }
            });
            //设置缩放级别  值越大表示缩放越大，反之越小 最小为3 最大级别 19 室内20
            aMap.setMinZoomLevel(Float.valueOf(17));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.valueOf(17)));
//            mUiSettings = aMap.getUiSettings();
            //不显示缩放加减按钮
            aMap.getUiSettings().setZoomControlsEnabled(false);
            //比例尺
            aMap.getUiSettings().setScaleControlsEnabled(false);

        }
    }

    protected void setQrc(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_logo_default)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_logo_default);
        String qrCode = HttpUrls.BRAND_LOGO_ULR + qrCodeId;
        Glide.with(this).load(qrCode).apply(requestOptions).into(ivQrc);
    }
    @Override
    protected void initData() {
        storeId = getIntent().getStringExtra("storeId");
        presenter.doLoad(this,storeId);

        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = edtAddress.getText().toString().trim();


                String edtAddre = provinceName+cityName+distName+address;

                if(cityName != null && !"".equals(cityName)){
                    if(address != null && !"".equals(address)){
                        getLatlon(edtAddre);
                    }
                }

            }
        });


    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        useMoveToLocationWithMapMode = true;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();

        useMoveToLocationWithMapMode = false;
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }


    private void initAddress(){
        Gson gson = new Gson();
//        addressBeans = gson.fromJson(getCityJson(), new TypeToken<List<AddressBean>>() {
//        }.getType());
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

                        List<AddressBean.CityBean.AreaBean> areaBeanList1 = new ArrayList<>();
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
                        }

                    }
                    addressBean.setChildren(cityBeanList1);
                    addressBeans.add(addressBean);
                }

            }
//            initAreaPickerView();


        }

    }
    /*protected void initAreaPickerView(){
        areaPickerView = new AreaPickerView(this, R.style.Dialog, addressBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                address = edtAddress.getText().toString().trim();
                String edtAddre = "";
                i=value;
                if (value.length == 3) {
                    provinceId = Long.parseLong(addressBeans.get(value[0]).getValue());
                    cityId = Long.parseLong(addressBeans.get(value[0]).getChildren().get(value[1]).getValue());
                    distId =  Long.parseLong(addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getValue());
                    provinceName = addressBeans.get(value[0]).getLabel();
                    cityName = addressBeans.get(value[0]).getChildren().get(value[1]).getLabel();
                    distName = addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel();
                    province.setId(provinceId);
                    city.setId(cityId);
                    dist.setId(distId);
                    store.setProvince(province);
                    store.setCity(city);
                    store.setDist(dist);
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());


                    edtAddre= addressBeans.get(value[0]).getLabel() + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel()+address;


                }else {
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
                    edtAddre= addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel()+address;
                }

                getLatlon(edtAddre);
            }
        });
    }*/

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getCityJson2() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region2.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    private void initPicAdapter(){
        themeId = R.style.picture_default_style;
        manager = new FullyGridLayoutManager(StoreInfoManageActivity.this, 3, GridLayoutManager.VERTICAL, false);
        rvPhoto.setLayoutManager(manager);
        adapter = new GridImageAdapter(StoreInfoManageActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        rvPhoto.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
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
                            PictureSelector.create(StoreInfoManageActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;

                    }
                }
            }
        });
    }


    protected void initOptionPicker() {//条件选择器初始化，自定义布局
        CardBean cardBean = new CardBean();
        cardBean.setId("1");
        cardBean.setCardNo("直营");
        cardBeans.add(cardBean);

        cardBean = new CardBean();
        cardBean.setId("2");
        cardBean.setCardNo("加盟");
        cardBeans.add(cardBean);
        pvBusMode = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = cardBeans.get(options1).getPickerViewText();

                if("直营".equals(tx)){
                    storeType = "1";
                }else {
                    storeType = "2";
                }

                bus_mode.setText(tx);
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
                                pvBusMode.returnData();
                                pvBusMode.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBusMode.dismiss();
                            }
                        });


                    }
                })
                .build();


        pvBusMode.setPicker(cardBeans);

    }



    private ThinDownloadManager downloadManager;
    MyDownloadDownloadStatusListenerV1
            myDownloadStatusListener = new MyDownloadDownloadStatusListenerV1();
    DownloadRequest downloadRequest;
    //R.id.business_time1,R.id.business_time2,
    @OnClick({R.id.iv_back,R.id.rl_address,R.id.rl_mode,R.id.rl_service,R.id.rl_main_categories,R.id.rl_banquet
    ,R.id.btn_download,R.id.btn_save})
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(StoreInfoManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.rl_address:

//                areaPickerView.setSelect(i);
//                areaPickerView.show();
                showAreaListView();
                break;
            case R.id.rl_mode:  //经营模式
                pvBusMode.show();
                break;
            case R.id.rl_service:  //提供服务
                intent = new Intent(StoreInfoManageActivity.this,StoreInfoParameterSelectorActivity.class);
                intent.putExtra("serviceId",serviceId);
                intent.putExtra("typeList",directoryDataCT);
                intent.putExtra("title",getResources().getString(R.string.tigongfuwu));
                ActivityUtils.startActivityForResult(StoreInfoManageActivity.this,intent,REQUEST_CODE_ONE,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_banquet:  //提供宴席
                intent = new Intent(StoreInfoManageActivity.this,StoreInfoParameterSelectorActivity.class);
                intent.putExtra("banquetId",banquetId);
                intent.putExtra("typeList",directoryDataYX);
                intent.putExtra("title",getResources().getString(R.string.tigongyanxi));
                ActivityUtils.startActivityForResult(StoreInfoManageActivity.this,intent,REQUEST_CODE_TWO,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.rl_main_categories:  //主营类别
                intent = new Intent(StoreInfoManageActivity.this,StoreInfoParameterSelectorActivity.class);
                intent.putExtra("categoriesId",categoriesId);
                intent.putExtra("typeList",directoryDataCP);
                intent.putExtra("title",getResources().getString(R.string.zhuyingleibie));
                ActivityUtils.startActivityForResult(StoreInfoManageActivity.this,intent,REQUEST_CODE_THREE,R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.btn_download:
                int ids = R.id.btn_download;
                downloadManager.add(downloadRequest);
                break;
            case R.id.btn_save:  //主营类别

                StoreParamBean storeParamBean = new StoreParamBean();
                storeName = tvStoreName.getText().toString().trim();
                managerName = edtManagerName.getText().toString().trim();
                managerTel = edtManagerPhone.getText().toString().trim();
                storeTel = edtStorePhone.getText().toString().trim();
//                startTime = start_time.getText().toString().trim();
//                endTime = end_time.getText().toString().trim();
                serviceDate = edtTime.getText().toString().trim();

                Area province = new Area();
                province.setId(provinceId);
                Area city = new Area();
                city.setId(cityId);
                Area dist = new Area();
                dist.setId(distId);

             /*   if(serviceDate == null || "".equals(serviceDate)){
                    ToastUtils.showShort("请输入营业时间！");
                    return;
                }

                if(managerName == null || "".equals(managerName)){
                    ToastUtils.showShort("请输入店长姓名！");
                    return;
                }
                if(managerTel == null || "".equals(managerTel)){
                    ToastUtils.showShort("请输入店长联系方式！");
                    return;
                }

                if(!RegexUtils.isMobileSimple(managerTel)){
                    ToastUtils.showShort("请输入正确店长联系方式！");
                    return;
                }
                if(storeTel == null || "".equals(storeTel)){
                    ToastUtils.showShort("请输入店铺联系方式！");
                    return;
                }
                if(provinceId == 0 ){
                    ToastUtils.showShort("请选择省市区！");
                    return;
                }
                if(cityId == 0 ){
                    ToastUtils.showShort("请选择省市区！");
                    return;
                }
                if(distId == 0 ){
                    ToastUtils.showShort("请选择省市区！");
                    return;
                }*/
                address = edtAddress.getText().toString().trim();
               /* if(address == null || "".equals(address)){
                    ToastUtils.showShort("请输入详细地址！");
                    return;
                }*/

               /* if(categoriesId == null || "".equals(categoriesId)){
                    ToastUtils.showShort("请选择主营类别！");
                    return;
                }

                if(serviceId == null || "".equals(serviceId)){
                    ToastUtils.showShort("请选择服务！");
                    return;
                }
                if(banquetId == null || "".equals(banquetId)){
                    ToastUtils.showShort("请选择宴席！");
                    return;
                }*/

               storeParamBean.setStoreId(store.getId());
                storeParamBean.setStoreName(storeName);
                storeParamBean.setManagerName(managerName);
                storeParamBean.setManagerTel(managerTel);
                storeParamBean.setTeleNos(storeTel);
                storeParamBean.setStatusQuo(storeType);
//                storeParamBean.setServiceStartTime(startTime);   //新需求
//                storeParamBean.setServiceEndTime(endTime);

                storeParamBean.setServiceDate(serviceDate);
                storeParamBean.setProvinceCode(provinceId);
                storeParamBean.setCityCode(cityId);
                storeParamBean.setDistCode(distId);
                storeParamBean.setStoreAddress(address);
                storeParamBean.setDishContents(categoriesId);
                storeParamBean.setServiceContents(serviceId);
                storeParamBean.setBanquetTypes(banquetId);
                remove(photoList); //去重复
                if(photoList != null && photoList.size()>0){
                    StringBuilder idSb = new StringBuilder();
                    for (String photo : photoList){
                        idSb.append(photo).append(",");
                    }
                    if(idSb.toString().length()>0){
                        photos = idSb.toString().substring(0,idSb.toString().length() - 1);
                        storeParamBean.setPhotos(photos);
                    }
                }else {
                    ToastUtils.showShort("请选择店铺环境！");
                    return;
                }


                presenter.saveStore(this,storeParamBean);



                break;

        }
    }

    protected void initDownLoad(){
        downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        RetryPolicy retryPolicy = new DefaultRetryPolicy();


        Uri downloadUri = Uri.parse(HttpUrls.BRAND_LOGO_ULR+qrCodeId);
//        File filesDir = getExternalFilesDir("");
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

//        String fileName = qrCodeId+".jpg";
       /* if((brandName != null && !"".equals(brandName)) && (storeName != null && !"".equals(storeName))){

        }*/

       if(brandName != null && "".equals(brandName)){

       }else {
           brandName = SPUtils.getInstance().getString("brandName");
       }
        String fileName = brandName+storeName+".jpg";
        File filesDir = new File(Environment.getExternalStorageDirectory().getPath() +"/DCIM/Camera/");
        Uri destinationUri = Uri.parse(filesDir.getAbsolutePath()+fileName);
//        Uri destinationUri = Uri.fromFile(filesDir);
        downloadRequest = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                .setRetryPolicy(retryPolicy)
                .setDownloadContext("Download1")
                .setStatusListener(myDownloadStatusListener);
    }

    class MyDownloadDownloadStatusListenerV1 implements DownloadStatusListenerV1 {

        @Override
        public void onDownloadComplete(DownloadRequest request) {
            final int id = request.getDownloadId();
           /* if (id == downloadId1) {
                mProgress1Txt.setText(request.getDownloadContext() + " id: "+id+" Completed");
            }*/
            ToastUtils.showCustomShortBottom("已下载到相册");
//            btn_download.setText("下载完成！");
        }

        @Override
        public void onDownloadFailed(DownloadRequest request, int errorCode, String errorMessage) {
            final int id = request.getDownloadId();

//            ToastUtils.showCustomShortBottom(errorMessage);
        }

        @Override
        public void onProgress(DownloadRequest request, long totalBytes, long downloadedBytes, int progress) {
            int id = request.getDownloadId();

       /*     System.out.println("######## onProgress ###### "+id+" : "+totalBytes+" : "+downloadedBytes+" : "+progress);
            if (id == downloadId1) {
                mProgress1Txt.setText("Download1 id: "+id+", "+progress+"%"+"  "+getBytesDownloaded(progress,totalBytes));
                mProgress1.setProgress(progress);

            }*/
//            ToastUtils.showCustomShortBottom( progress+"%"+"  "+getBytesDownloaded(progress,totalBytes));
        }
    }

    private String getBytesDownloaded(int progress, long totalBytes) {
        //Greater than 1 MB
        long bytesCompleted = (totalBytes)/100;
        if (totalBytes >= 1000000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000000)) + "MB");
        } if (totalBytes >= 1000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000)) + "Kb");

        } else {
            return ( ""+bytesCompleted+"/"+totalBytes );
        }
    }

    private void remove(List<String> photoList){
        for(int i=0;i<photoList.size();i++){
            for(int j=photoList.size()-1;j>i;j--){
                if((photoList.get(i).equals(photoList.get(j)))){
                    photoList.remove(j);
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.finishActivity(StoreInfoManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(StoreInfoManageActivity.this)
                        .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE )
                       /* .selectionMode(cb_choose_mode.isChecked() ?
                                PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选*/
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
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                /*PictureSelector.create(StoreInfoManageActivity.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(cb_choose_mode.isChecked() ?
                                PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(cb_preview_img.isChecked())// 是否可预览图片
                        .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
                        .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                        .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
                        .enableCrop(cb_crop.isChecked())// 是否裁剪
                        .compress(cb_compress.isChecked())// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
                        .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code*/
            }
        }

    };

    protected void deleteImage(String imageId,Integer deletePosition){
        presenter.delete(this,imageId,deletePosition);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    String flag = "storeEdit";
                    /*for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        if(media.getCompressPath()!=null && media.getCompressPath().length()>0){
                            if(media.getCompressPath().contains("get.do?id=")){

                            }else {
                                File file = new File(media.getCompressPath());
                                presenter.upload(this,file,flag);
                            }
                        }

                    }*/
                    for (int i = 0;i <selectList.size();i++){
                        LocalMedia media = selectList.get(i);
                        if(!media.isCompressed()){
                            String compressPath = media.getCompressPath();
                            if(compressPath!=null && compressPath.length()>0){
                                if(compressPath.contains("get.do?id=")){

                                }else {
                                    File file = new File(compressPath);
                                    presenter.upload(this,file,flag);
                                }
                            }else {
                                String path = media.getPath();
                                if(path != null && path.length()>0){
                                    File file = new File(path);
                                    if(path.indexOf("/storage/emulated/") != -1){
                                        presenter.upload(this,file,flag);
                                    }

                                }
                            }
                        }else {
                            String path = media.getPath();
                            if(path != null && path.length()>0){
                                File file = new File(path);
                                if(path.indexOf("/storage/emulated/") != -1){
                                    presenter.upload(this,file,flag);
                                }

                            }
                        }

                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }else if(resultCode == RESULT_CODE){
            ArrayList<CateBean> selectTypeList = new ArrayList<>();
            selectTypeList =  (ArrayList<CateBean>)data.getSerializableExtra("selectTypeList");
            StringBuilder nameSb = new StringBuilder();
            StringBuilder idSb = new StringBuilder();
            if(requestCode == REQUEST_CODE_ONE){
                if(selectTypeList!=null && selectTypeList.size()>0 ){
                    for (CateBean typeBean :selectTypeList){
                        if(typeBean.isChecked()){
                            String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                            nameSb.append(name);
                            idSb.append(typeBean.getValue()).append(",");
                        }
                    }
                    if(idSb.toString() != null && idSb.toString().length()>0){
                        serviceId = idSb.toString().substring(0,idSb.toString().length() - 1);
                    }

                    render_services.setText(nameSb.toString().substring(0,nameSb.toString().length() - 1));

                }

            }else if(requestCode == REQUEST_CODE_TWO){

                if(selectTypeList!=null && selectTypeList.size()>0 ){
                    for (CateBean typeBean :selectTypeList){
                        if(typeBean.isChecked()){
                            String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                            nameSb.append(name);
                            idSb.append(typeBean.getValue()).append(",");
                        }
                    }
                    if(idSb.toString() != null && idSb.toString().length()>0){
                        banquetId = idSb.toString().substring(0,idSb.toString().length() - 1);
                    }

                    if(nameSb.toString() != null && nameSb.toString().length()>0){
                        render_banquet.setText(nameSb.toString().substring(0,nameSb.toString().length() - 1));
                    }


                }
            }else if(requestCode == REQUEST_CODE_THREE){
                if(selectTypeList!=null && selectTypeList.size()>0 ){
                    for (CateBean typeBean :selectTypeList){
                        if(typeBean.isChecked()){
                            String name = typeBean.getKey()!=null?typeBean.getKey()+"/":"";
                            nameSb.append(name);
                            idSb.append(typeBean.getValue()).append(",");
                        }
                    }

                    if(idSb.toString() != null && idSb.toString().length()>0){
                        categoriesId = idSb.toString().substring(0,idSb.toString().length() - 1);
                    }

                    main_categories.setText(nameSb.toString().substring(0,nameSb.toString().length() - 1));

                }

            }
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                //展示自定义定位小蓝点
                if(locationMarker == null) {
                    //首次定位
                    locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point))
                            .anchor(0.5f, 0.5f));
                    locationMarker.showInfoWindow();
                    //首次定位,选择移动到地图中心点并修改级别到15级
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                } else {

                    if(useMoveToLocationWithMapMode) {
                        //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                        startMoveLocationAndMap(latLng);
                    } else {
                        startChangeLocation(latLng);
                    }

                }


            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }


    /**
     * 修改自定义定位小蓝点的位置
     * @param latLng
     */
    private void startChangeLocation(LatLng latLng) {

        if(locationMarker != null) {
            LatLng curLatlng = locationMarker.getPosition();
            if(curLatlng == null || !curLatlng.equals(latLng)) {
                locationMarker.setPosition(latLng);
            }
        }
    }


    /**
     * 同时修改自定义定位小蓝点和地图的位置
     * @param latLng
     */
    protected void startMoveLocationAndMap(LatLng latLng) {

        //将小蓝点提取到屏幕上
        if(projection == null) {
            projection = aMap.getProjection();
        }
        if(locationMarker != null && projection != null) {
            LatLng markerLocation = locationMarker.getPosition();
            Point screenPosition = aMap.getProjection().toScreenLocation(markerLocation);
            locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

        }else{
            locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point))
                    .anchor(0.5f, 0.5f));
            locationMarker.showInfoWindow();
        }

        //移动地图，移动结束后，将小蓝点放到放到地图上
        myCancelCallback.setTargetLatlng(latLng);
        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng),1000,myCancelCallback);
//        addPolylinescircle(latLng,100);

        // 绘制一个圆形
//        circle = aMap.addCircle(new CircleOptions().center(latLng)
//                .radius(100).strokeColor(Color.argb(20, 40, 110, 210))
//                .fillColor(Color.argb(20, 40, 110, 210)).strokeWidth(0));

    }


    MyCancelCallback myCancelCallback = new MyCancelCallback();

    @Override
    public void onTouch(MotionEvent motionEvent) {
        Log.i("amap","onTouch 关闭地图和小蓝点一起移动的模式");
        useMoveToLocationWithMapMode = false;
    }

    /**
     * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
     */
    class MyCancelCallback implements AMap.CancelableCallback {

        LatLng targetLatlng;
        public void setTargetLatlng(LatLng latlng) {
            this.targetLatlng = latlng;
        }

        @Override
        public void onFinish() {
            if(locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }

        @Override
        public void onCancel() {
            if(locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }
    };



    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     *
     * @param centerpoint 中心点坐标
     * @param radius      半径 米
     */
    public void addPolylinescircle(LatLng centerpoint, int radius) {
        double r = 6371000.79;
        PolylineOptions options = new PolylineOptions();
        int numpoints = 360;
        double phase = 2 * Math.PI / numpoints;

        //画图
        for (int i = 0; i < numpoints; i++) {
            double dx = (radius * Math.cos(i * phase));
            double dy = (radius * Math.sin(i * phase));//乘以1.6 椭圆比例

            double dlng = dx / (r * Math.cos(centerpoint.latitude * Math.PI / 180) * Math.PI / 180);
            double dlat = dy / (r * Math.PI / 180);
            double newlng = centerpoint.longitude + dlng;
            options.add(new LatLng(centerpoint.latitude + dlat, newlng));
        }

        aMap.addPolyline(options.width(10).useGradient(true).setDottedLine(true));

    }


    protected void getLatlon(String cityName){
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {

                if (rCode == AMapException.CODE_AMAP_SUCCESS){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){

                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                AMapUtil.convertToLatLng(geocodeAddress.getLatLonPoint()), 15));
                        latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        longitude= geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode= geocodeAddress.getAdcode();//区域编码

                        Log.e("lgq地理编码", geocodeAddress.getAdcode()+"");
                        Log.e("lgq纬度latitude",latitude+"");
                        Log.e("lgq经度longititude",longitude+"");
                        latLng = new LatLng(latitude,longitude);
                        locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point))
                                .anchor(0.5f, 0.5f));
                        locationMarker.showInfoWindow();
                        myCancelCallback.setTargetLatlng(latLng);
                        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
                        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
                        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng),1000,myCancelCallback);

                    }else {
//                        ToastUtils.showShort("对不起，没有搜索到相关数据！");
//                        ToastUtils.show(context,"地址名出错");
                    }
                }
            }
        });
        String cityCode = "";
         if(cityId != 0L){
             cityCode = String.valueOf(cityId);
         }
         geocodeQuery=new GeocodeQuery(cityName.trim(),cityCode);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);


    }


    private void showAreaListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_area_pickerview,null);
        //创建并显示popWindow
         areaPop = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView(contentView)
                .show(tvProCityDist);
        /**
         * 位于底部
         */
       /* Window window = this.getWindow();
        isCreate = true;

        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置弹出动画
        window.setWindowAnimations(R.style.PickerAnim);*/

        tabLayout = contentView.findViewById(R.id.tablayout);
        viewPager = contentView.findViewById(R.id.viewpager);
        ivBtn = contentView.findViewById(R.id.iv_btn);
        ivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaPop.dismiss();
            }
        });

        View provinceView = LayoutInflater.from(this)
                .inflate(R.layout.layout_recyclerview, null, false);
        View cityView = LayoutInflater.from(this)
                .inflate(R.layout.layout_recyclerview, null, false);
        View areaView = LayoutInflater.from(this)
                .inflate(R.layout.layout_recyclerview, null, false);

        final RecyclerView provinceRecyclerView = provinceView.findViewById(R.id.recyclerview);
        cityRecyclerView = cityView.findViewById(R.id.recyclerview);
        areaRecyclerView = areaView.findViewById(R.id.recyclerview);

        views = new ArrayList<>();
        views.add(provinceView);
        views.add(cityView);
        views.add(areaView);

        /**
         * 配置adapter
         */
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /**
         * 这句话设置了过后，假如又3个tab 删除第三个 刷新过后 第二个划第三个会有弹性
         * viewPager.setOffscreenPageLimit(2);
         */

        provinceAdapter = new ProvinceAdapter(R.layout.item_address, addressBeans);
        provinceRecyclerView.setAdapter(provinceAdapter);
        LinearLayoutManager provinceManager = new LinearLayoutManager(this);
        provinceRecyclerView.setLayoutManager(provinceManager);
        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("AreaPickerView", oldProvinceSelected + "~~~" + oldCitySelected + "~~~" + oldAreaSelected);
                cityBeans.clear();
                areaBeans.clear();
                addressBeans.get(position).setStatus(true);
                provinceSelected = position;
                if (oldProvinceSelected != -1 && oldProvinceSelected != provinceSelected) {
                    addressBeans.get(oldProvinceSelected).setStatus(false);
                    Log.e("AreaPickerView", "清空");
                }
                if (position != oldProvinceSelected) {
                    if (oldCitySelected != -1) {
                        addressBeans.get(oldProvinceSelected).getChildren().get(oldCitySelected).setStatus(false);
                    }
                    if (oldAreaSelected != -1) {
                        addressBeans.get(oldProvinceSelected).getChildren().get(oldCitySelected).getChildren().get(oldAreaSelected).setStatus(false);
                    }
                    oldCitySelected = -1;
                    oldAreaSelected = -1;
                }
                cityBeans.addAll(addressBeans.get(position).getChildren());
                provinceAdapter.notifyDataSetChanged();
                cityAdapter.notifyDataSetChanged();
                areaAdapter.notifyDataSetChanged();
                strings.set(0, addressBeans.get(position).getLabel());
                if (strings.size() == 1) {
                    strings.add("请选择");
                } else if (strings.size() > 1) {
                    if (position != oldProvinceSelected) {
                        strings.set(1, "请选择");
                        if (strings.size() == 3) {
                            strings.remove(2);
                        }
                    }
                }
                tabLayout.setupWithViewPager(viewPager);
                viewPagerAdapter.notifyDataSetChanged();
                tabLayout.getTabAt(1).select();
                oldProvinceSelected = provinceSelected;
            }
        });

        cityBeans = new ArrayList<>();
        cityAdapter = new CityAdapter(R.layout.item_address, cityBeans);
        LinearLayoutManager cityListManager = new LinearLayoutManager(this);
        cityRecyclerView.setLayoutManager(cityListManager);
        cityRecyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                areaBeans.clear();
                cityBeans.get(position).setStatus(true);
                citySelected = position;
                if (oldCitySelected != -1 && oldCitySelected != citySelected) {
                    addressBeans.get(oldProvinceSelected).getChildren().get(oldCitySelected).setStatus(false);
                }
                if (position != oldCitySelected) {
                    if (oldAreaSelected != -1 && cityBeans.get(position).getChildren() != null) {
                        addressBeans.get(oldProvinceSelected).getChildren().get(oldCitySelected).getChildren().get(oldAreaSelected).setStatus(false);
                    }
                    oldAreaSelected = -1;
                }
                oldCitySelected = citySelected;
                if (cityBeans.get(position).getChildren() != null) {
                    areaBeans.addAll(cityBeans.get(position).getChildren());
                    cityAdapter.notifyDataSetChanged();
                    areaAdapter.notifyDataSetChanged();
                    strings.set(1, cityBeans.get(position).getLabel());
                    if (strings.size() == 2) {
                        strings.add("请选择");
                    } else if (strings.size() == 3) {
                        strings.set(2, "请选择");
                    }
                    tabLayout.setupWithViewPager(viewPager);
                    viewPagerAdapter.notifyDataSetChanged();
                    tabLayout.getTabAt(2).select();
                } else {
                    oldAreaSelected = -1;
                    cityAdapter.notifyDataSetChanged();
                    areaAdapter.notifyDataSetChanged();
                    strings.set(1, cityBeans.get(position).getLabel());
                    tabLayout.setupWithViewPager(viewPager);
                    viewPagerAdapter.notifyDataSetChanged();
                    areaPop.dismiss();
                    areaPickerViewCallback.callback(provinceSelected, citySelected);
                }
            }
        });

        areaBeans = new ArrayList<>();
        areaAdapter = new AreaAdapter(R.layout.item_address, areaBeans);
        LinearLayoutManager areaListManager = new LinearLayoutManager(this);
        areaRecyclerView.setLayoutManager(areaListManager);
        areaRecyclerView.setAdapter(areaAdapter);
        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                strings.set(2, areaBeans.get(position).getLabel());
                tabLayout.setupWithViewPager(viewPager);
                viewPagerAdapter.notifyDataSetChanged();
                areaBeans.get(position).setStatus(true);
                areaSelected = position;
                if (oldAreaSelected != -1 && oldAreaSelected != position) {
                    areaBeans.get(oldAreaSelected).setStatus(false);
                }


                oldAreaSelected = areaSelected;
                areaAdapter.notifyDataSetChanged();
                areaPop.dismiss();
                areaPickerViewCallback.callback(provinceSelected, citySelected, areaSelected);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        provinceRecyclerView.scrollToPosition(oldProvinceSelected == -1 ? 0 : oldProvinceSelected);
                        break;
                    case 1:
                        cityRecyclerView.scrollToPosition(oldCitySelected == -1 ? 0 : oldCitySelected);
                        break;
                    case 2:
                        areaRecyclerView.scrollToPosition(oldAreaSelected == -1 ? 0 : oldAreaSelected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        areaPickerViewCallback = new AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                address = edtAddress.getText().toString().trim();
                String edtAddre = "";
                i=value;
                if (value.length == 3) {
                    provinceId = Long.parseLong(addressBeans.get(value[0]).getValue());
                    cityId = Long.parseLong(addressBeans.get(value[0]).getChildren().get(value[1]).getValue());
                    distId =  Long.parseLong(addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getValue());
                    provinceName = addressBeans.get(value[0]).getLabel();
                    cityName = addressBeans.get(value[0]).getChildren().get(value[1]).getLabel();
                    distName = addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel();
                    province.setId(provinceId);
                    city.setId(cityId);
                    dist.setId(distId);
                    store.setProvince(province);
                    store.setCity(city);
                    store.setDist(dist);
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());


                    edtAddre= addressBeans.get(value[0]).getLabel() + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel()+address;


                }else {
                    tvProCityDist.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
                    edtAddre= addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel()+address;
                }

                getLatlon(edtAddre);
            }
        };
        if(i != null && i.length>0){
            setSelect(i);
        }



    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            Log.e("AreaPickView", "------------instantiateItem");
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
            Log.e("AreaPickView", "------------destroyItem");
        }

    }

    public interface AreaPickerViewCallback {
        void callback(int... value);
    }

    public void setAreaPickerViewCallback(AreaPickerViewCallback areaPickerViewCallback) {
        this.areaPickerViewCallback = areaPickerViewCallback;
    }

    public void setSelect(int... value) {
        strings = new ArrayList<>();


        if (value == null) {
            strings.add("请选择");
            if (isCreate) {
                tabLayout.setupWithViewPager(viewPager);
                viewPagerAdapter.notifyDataSetChanged();
                tabLayout.getTabAt(0).select();
                if (provinceSelected != -1)
                    addressBeans.get(provinceSelected).setStatus(false);
                if (citySelected != -1)
                    addressBeans.get(provinceSelected).getChildren().get(citySelected).setStatus(false);
                cityBeans.clear();
                areaBeans.clear();
                provinceAdapter.notifyDataSetChanged();
                cityAdapter.notifyDataSetChanged();
                areaAdapter.notifyDataSetChanged();
            }
            return;
        }
        if (value.length == 3) {
            strings.add(addressBeans.get(value[0]).getLabel());
            strings.add(addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
            strings.add(addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            tabLayout.getTabAt(value.length - 1).select();
            if (provinceSelected != -1)
                addressBeans.get(provinceSelected).setStatus(false);
            if (citySelected != -1)
                addressBeans.get(provinceSelected).getChildren().get(citySelected).setStatus(false);
            addressBeans.get(value[0]).setStatus(true);
            addressBeans.get(value[0]).getChildren().get(value[1]).setStatus(true);
            addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).setStatus(true);
            cityBeans.clear();
            cityBeans.addAll(addressBeans.get(value[0]).getChildren());
            areaBeans.clear();
            areaBeans.addAll(addressBeans.get(value[0]).getChildren().get(value[1]).getChildren());
            provinceAdapter.notifyDataSetChanged();
            cityAdapter.notifyDataSetChanged();
            areaAdapter.notifyDataSetChanged();
            oldProvinceSelected = value[0];
            oldCitySelected = value[1];
            oldAreaSelected = value[2];

            provinceSelected = value[0];
            citySelected = value[1];
            areaSelected = value[2];
            areaRecyclerView.scrollToPosition(oldAreaSelected == -1 ? 0 : oldAreaSelected);
        }

        if (value.length == 2) {
            strings.add(addressBeans.get(value[0]).getLabel());
            strings.add(addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            tabLayout.getTabAt(value.length - 1).select();
            addressBeans.get(provinceSelected).setStatus(false);
            addressBeans.get(provinceSelected).getChildren().get(citySelected).setStatus(false);
            addressBeans.get(value[0]).setStatus(true);
            addressBeans.get(value[0]).getChildren().get(value[1]).setStatus(true);
            cityBeans.clear();
            cityBeans.addAll(addressBeans.get(value[0]).getChildren());
            provinceAdapter.notifyDataSetChanged();
            cityAdapter.notifyDataSetChanged();
            oldProvinceSelected = value[0];
            oldCitySelected = value[1];
            oldAreaSelected = -1;

            provinceSelected = value[0];
            citySelected = value[1];
            areaSelected = -1;
            cityRecyclerView.scrollToPosition(oldCitySelected == -1 ? 0 : oldCitySelected);
        }

    }



}
