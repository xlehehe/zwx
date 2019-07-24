package com.zwx.scan.app.feature.member;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.zwx.library.circleimageview.CircleImageView;
import com.zwx.library.popwindow.PopWindow;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.Brand;
import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.MaterialCard;
import com.zwx.scan.app.data.bean.MaterialGame;
import com.zwx.scan.app.data.bean.Template;
import com.zwx.scan.app.data.bean.TemplateChild;
import com.zwx.scan.app.data.http.HttpUrls;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.cateringinfomanage.adapter.SelectTypeAdapter;
import com.zwx.scan.app.utils.ButtonUtils;
import com.zwx.scan.app.widget.MyGridView;
import com.zwx.scan.app.widget.MyListView;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/04/04
 * desc   :  会员卡管理第二步
 * version: 1.0
 **/
public class MemberCardNewTwoActivity extends BaseActivity<MemberManageContract.Presenter> implements MemberManageContract.View,View.OnClickListener  {
    protected static Logger log = Logger.getLogger(MemberCardNewTwoActivity.class);
    @BindView(R.id.iv_back)
    protected ImageView ivBack;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    //1 - 2步骤
    @BindView(R.id.iv_one)
    protected ImageView ivOne;
    @BindView(R.id.iv_ellipsis_one)
    protected ImageView ivEllipsisOne;
    @BindView(R.id.iv_two)
    protected ImageView ivTwo;


    @BindView(R.id.circle_view)
    protected CircleImageView circle_view;

    @BindView(R.id.ll_image)
    protected LinearLayout ll_image;

    @BindView(R.id.tv_time)
    protected TextView tv_time;

    @BindView(R.id.tv_card_name)
    protected TextView tv_card_name;
    @BindView(R.id.tv_condition)
    protected TextView tv_condition;


    //卡样式
    @BindView(R.id.ll_card_left)
    protected LinearLayout ll_card_left;
    @BindView(R.id.tv_card_style_title_left)
    protected TextView tv_card_style_title_left;

    @BindView(R.id.left_view)
    protected View left_view;

    //卡面内容设置
    @BindView(R.id.ll_card_right)
    protected LinearLayout ll_card_right;
    @BindView(R.id.tv_card_content_title_right)
    protected TextView tv_card_content_title_right;

    @BindView(R.id.right_view)
    protected View right_view;

    @BindView(R.id.ll_member_card_style_left)
    protected LinearLayout ll_member_card_style_left;

    @BindView(R.id.ll_member_card_content_right)
    protected LinearLayout ll_member_card_content_right;


    //卡样式 - 模板 素材
    @BindView(R.id.tv_card_template_left)
    protected TextView tv_card_template_left;

    @BindView(R.id.tv_card_material_right)
    protected TextView tv_card_material_right;

    @BindView(R.id.grid_view1)
    protected MyGridView grid_view1;
    @BindView(R.id.grid_view2)
    protected MyGridView grid_view2;

    @BindView(R.id.list_view_content)
    protected MyListView list_view_content;
    @BindView(R.id.grid_view_font)
    protected MyGridView grid_view_font;


    @BindView(R.id.btn_user)
    protected Button btn_user;
    private String isEditCampaign = "NO";

    private String isCopyCreate = "YES";

    protected CardTempletListAdapter empletAdapter = null;
    protected SelectMaterialCardAdapter materialCardAdapter = null;

    protected String compId;
    protected String userId;
    protected String brandId;
    //模板接口获取的公司id
    protected String companyId;

    //素材id
    protected static String materialId;

    //背景图 素材
    protected static String materialBack;

    //卡面内容设置
    private ArrayList<CateBean> typeList = new ArrayList<>();
    private SelectTypeAdapter myAdapter ;
    private ArrayList<FontBean> fontList = new ArrayList<>();

    protected List<Template> templateList = new ArrayList<>();
    protected List<TemplateChild> templateChildList = new ArrayList<>();
    protected static List<MaterialCard> materialCardList = new ArrayList<>();

    protected SelectFontColorAdapter colorAdapter = null;
    //显示规则
   protected static String displayRule;
   //颜色值
   protected static String colour = "FFFFFF";;
   protected  static String isTemplet = "1";
   protected  MaterialCard materialCard = new MaterialCard();
   protected String compMemberTypeJSON;
   protected String materialCardJSON;
   protected String brandLogo;
    protected static String cardName;
   protected String memtypeStatus  ;
   protected String custTime;
    /**
     * 背景图片缩略图ID  末班
     */
    protected static String background;
    protected static String name;


    protected String code1 = "1";
    protected String code2 = "1";
    protected String code3 = "1";
    protected String code4 = "1";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_card_new_two;
    }

    @Override
    protected void initView() {
        DaggerMemberManageComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .memberManageModule(new MemberManageModule(this))
                .build()
                .inject(this);
        tvTitle.setText("会员卡设置");
        setSetTep();
        setleftVisible();
        //是否编辑还是新建
        isEditCampaign = getIntent().getStringExtra("isEditCampaign");
        isCopyCreate = getIntent().getStringExtra("isCopyCreate");
        if ("YES".equals(isEditCampaign)) {
            //编辑页面复制并创建
            tvRight.setVisibility(View.VISIBLE);
            // //编辑
            if ("YES".equals(isCopyCreate)) {
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("复制并创建");
            } else {
                tvRight.setVisibility(View.GONE);
            }
        }

        brandId = SPUtils.getInstance().getString("brandId");
        compId = SPUtils.getInstance().getString("compId");
        userId = SPUtils.getInstance().getString("userId");

        initGridData();

        String logo = "";
        brandLogo = SPUtils.getInstance().getString("brandLogo");
        if(MemberCardNewActivity.brand != null){
            logo = MemberCardNewActivity.brand.getLogo();
            if(logo != null && !"".equals(logo)){
                brandLogo = HttpUrls.IMAGE_ULR + logo;
            }
        }
        setBrandLogo();

        if(MemberCardNewActivity.compMemberType !=null){
            cardName = MemberCardNewActivity.compMemberType.getCompMemberGroup();
            if(cardName != null && !"".equals(cardName)){
                tv_card_name.setText(cardName);
            }else {
                tv_card_name.setText("");
            }

            background  = MemberCardNewActivity.compMemberType.getMaterialBack();
            setCardBg();
            isTemplet  = MemberCardNewActivity.compMemberType.getIsTemplet();
            if(isTemplet == null || "".equals(isTemplet)){
                isTemplet = "1";
            }
            if("1".equals(isTemplet)){ //模板
                tv_card_template_left.setTextColor(getResources().getColor(R.color.btn_color_blue));
                tv_card_material_right.setTextColor(getResources().getColor(R.color.color_gray_deep));
                grid_view1.setVisibility(View.VISIBLE);
                grid_view2.setVisibility(View.GONE);
            }else {  //素材

                tv_card_template_left.setTextColor(getResources().getColor(R.color.color_gray_deep));
                tv_card_material_right.setTextColor(getResources().getColor(R.color.btn_color_blue));
                grid_view1.setVisibility(View.GONE);
                grid_view2.setVisibility(View.VISIBLE);
            }
            colour = MemberCardNewActivity.compMemberType.getColour();
            if(colour != null && !"".equals(colour)){

            }else {
                colour = "FFFFFF";
            }
            custTime = MemberCardNewActivity.compMemberType.getCustomTime();
        }
        presenter.doTemplaCard(this,compId);

        if("1".equals(isTemplet)){ //模板

            if(templateChildList != null && templateChildList.size()>0){
                for(TemplateChild templateChild:templateChildList){
                    String backGround = templateChild.getImgUrl();
                    if(background.equals(backGround)){
                        background = backGround;
                        templateChild.setChecked(true);
                    }
                }
                empletAdapter.notifyDataSetChanged();
            }
        }else {  //素材
            if(materialCardList != null && materialCardList.size()>0){
                for(MaterialCard materialCard:materialCardList){
                     String backGround = materialCard.getBackground();
                    String  materialId2 = String.valueOf(materialCard.getId());
                    if(materialId.equals(materialId2)){
                        background = backGround;
                        materialCard.setChecked(true);
                    }
                }
                materialCardAdapter.notifyDataSetChanged();
            }
        }




        setCardBg();

    }

    protected void setCardBg(){
        String bgPath = HttpUrls.IMAGE_ULR + background;

        Glide.with(MemberCardNewTwoActivity.this).load(bgPath).into(new SimpleTarget<Drawable>(100,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ll_image.setBackground(resource);    //设置背景
                }
            }
        });
    }
    private void setBrandLogo(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(circle_view);
    }

    protected void initGridData(){
        initMaterCardData();
        initTemplateData();
        if("1".equals(isTemplet)){
            grid_view1.setVisibility(View.VISIBLE);
            grid_view2.setVisibility(View.GONE);
        }else {
            grid_view1.setVisibility(View.GONE);
            grid_view2.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void initData() {

        CateBean cateBean = new CateBean();
        cateBean.setKey("品牌logo");
        cateBean.setValue("1");
        cateBean.setChecked(true);
        typeList.add(cateBean);

        cateBean = new CateBean();
        cateBean.setKey("会员卡名称");
        cateBean.setValue("2");
        cateBean.setChecked(true);
        typeList.add(cateBean);

        cateBean = new CateBean();
        cateBean.setKey("消费者加入条件");
        cateBean.setValue("3");
        cateBean.setChecked(true);
        typeList.add(cateBean);

        myAdapter = new SelectTypeAdapter(this,typeList);
        list_view_content.setAdapter(myAdapter);
        if("0".equals(MemberCardNewActivity.isDefault)){ //默认卡
            code4 = "0" ;
        }else {
            if("N".equals(custTime)){ //长期有效 为是
                MemberCardNewActivity.wayValue = "";
                code4 = "0";
            }else {//A或R 长期有效 为否
//                MemberCardNewActivity.effectiveValue = "";
                cateBean = new CateBean();
                cateBean.setKey("会员卡有效时间");
                cateBean.setValue("4");
                cateBean.setChecked(true);
                code4 = "1";
                typeList.add(cateBean);
                if("A".equals(MemberCardNewActivity.wayValue)){ //按起止时间日期
                }else if("R".equals(MemberCardNewActivity.wayValue)) { //按开卡日天数

                }

                myAdapter.notifyDataSetChanged();
            }
        }


        list_view_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  for(CateBean cateBean1:typeList){
                    cateBean1.setChecked(false);
                }*/
                if(currentNum == -1){ //选中
                    if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    typeList.get(position).setChecked(true);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    if(typeList.get(position).isChecked()){
                        typeList.get(position).setChecked(false);
                    }else {
                        typeList.get(position).setChecked(true);
                    }
                    currentNum = position;
                }

                myAdapter.notifyDataSetChanged();

                for (CateBean cateBean1 :typeList){
                    boolean isCheck = cateBean1.isChecked();
                    String value = cateBean1.getValue();
                    if(isCheck){
                        if("1".equals(value)){
                            code1 = "1";
                            circle_view.setVisibility(View.VISIBLE);
                        }else if("2".equals(value)){
                            tv_card_name.setVisibility(View.VISIBLE);
                            code2 = "1";
                        }else if("3".equals(value)){
                            code3 = "1";
                            tv_condition.setVisibility(View.VISIBLE);
                        }else if("4".equals(value)){
                            code4 = "1";
                            tv_time.setVisibility(View.VISIBLE);
                        }
                    }else {

                        if("1".equals(value)){
                            code1 = "0";
                            circle_view.setVisibility(View.GONE);
                        }else if("2".equals(value)){
                            code2= "0";
                            tv_card_name.setVisibility(View.GONE);

                        }else if("3".equals(value)){
                            code3 = "0";
                            tv_condition.setVisibility(View.GONE);
                        }else if("4".equals(value)){
                            code4 = "0";
                            tv_time.setVisibility(View.GONE);
                        }
//                        circle_view.setImageResource(R.drawable.ic_logo);
                    }
                }
            }
        });

        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
//        colour = "FFFFFF";
        FontBean fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("000000");
        fontBean.setFont(R.drawable.ic_member_card_style_color_black);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("FFFFFF");
        fontBean.setFont(R.drawable.ic_member_card_style_color_white);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("FF2727");
        fontBean.setFont(R.drawable.ic_member_card_style_color_red);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("F86000");
        fontBean.setFont(R.drawable.ic_member_card_style_color_orange);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("FFD534");
        fontBean.setFont(R.drawable.ic_member_card_style_color_yellow);
        fontList.add(fontBean);
        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("0486FE");
        fontBean.setFont(R.drawable.ic_member_card_style_color_blue);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("51D7B9");
        fontBean.setFont(R.drawable.ic_member_card_style_color_green);
        fontList.add(fontBean);

        fontBean = new FontBean();
        fontBean.setChecked(false);
        fontBean.setColour("9E4DEE");
        fontBean.setFont(R.drawable.ic_member_card_style_color_purple);
        fontList.add(fontBean);

        for (FontBean fontBean1:fontList){
            String colour1 = fontBean1.getColour();
            if(colour1.equals(colour)){
                fontBean1.setChecked(true);
            }
        }
        colorAdapter = new SelectFontColorAdapter(this,fontList);
        grid_view_font.setAdapter(colorAdapter);
        grid_view_font.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(FontBean fontBean1:fontList){
                    fontBean1.setChecked(false);
                }
                colorAdapter.notifyDataSetChanged();
                if(currentNum == -1){ //选中
                    fontList.get(position).setChecked(true);

                    if(position == 0){
                        colour = "000000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                    }else if(position == 1){
                        colour = "FFFFFF";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                    }else if(position == 2){
                        colour = "FF2727";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                    }else if(position == 3){
                        colour = "F86000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                    }else if(position == 4){
                        colour = "FFD534";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                    }else if(position == 5){
                        colour = "0486FE";

                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                    }else if(position == 6){
                        colour = "51D7B9";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                    }else if(position == 7){
                        colour = "9E4DEE";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                    }

                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
//                    fontList.get(position).setChecked(false);
                    currentNum = -1;
                    fontList.get(position).setChecked(true);
                    if(position == 0){
                        colour = "000000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                    }else if(position == 1){
                        colour = "FFFFFF";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                    }else if(position == 2){
                        colour = "FF2727";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                    }else if(position == 3){
                        colour = "F86000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                    }else if(position == 4){
                        colour = "FFD534";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                    }else if(position == 5){
                        colour = "0486FE";

                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                    }else if(position == 6){
                        colour = "51D7B9";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                    }else if(position == 7){
                        colour = "9E4DEE";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                    }
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    fontList.get(position).setChecked(true);
                    currentNum = position;
                    if(position == 0){
                        colour = "000000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
                    }else if(position == 1){
                        colour = "FFFFFF";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
                    }else if(position == 2){
                        colour = "FF2727";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
                    }else if(position == 3){
                        colour = "F86000";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
                    }else if(position == 4){
                        colour = "FFD534";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
                    }else if(position == 5){
                        colour = "0486FE";

                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
                    }else if(position == 6){
                        colour = "51D7B9";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
                    }else if(position == 7){
                        colour = "9E4DEE";
                        tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                        tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
                    }

                }

                colorAdapter.notifyDataSetChanged();
            }
        });


        if( MemberCardNewActivity.compMemberType!= null ){
            String isDefault =  MemberCardNewActivity.compMemberType.getIsDefault();

            //默认会员卡 不显示有效期时间
            if("0".equals(isDefault)){
                tv_time.setVisibility(View.GONE);
                code4 = "0";
            }else {  //不是默认展示有效期

                String custTime =  MemberCardNewActivity.compMemberType.getCustomTime();

                //长期有效
                if("N".equals(custTime)){  //如果是长期有效 不展示固定起止时间或按天数日
                    tv_time.setText("");
                    tv_time.setVisibility(View.GONE);
                    code4 = "0";
                }else {  //否则固定起止时间或按天数日 展示
                    code4 = "1";
                    tv_time.setVisibility(View.VISIBLE);
                    if("A".equals(custTime)){ //绝对时间(固定起止时间)
                        String absoluteStartime =MemberCardNewActivity.compMemberType.getAbsoluteStartime();
                        String absoluteEndtime = MemberCardNewActivity.compMemberType.getAbsoluteEndtime();
                        String startTime = "";
                        String endTime = "";
                        if(absoluteStartime != null && !"".equals(absoluteStartime)){
                            startTime= absoluteStartime.replace("-",".").substring(0,10);
                        }
                        if(absoluteEndtime != null && !"".equals(absoluteEndtime)){
                            endTime= absoluteEndtime.replace("-",".").substring(0,10);
                        }

                        tv_time.setText(startTime+ " - "+endTime);
                    }else {

                        if(MemberCardNewActivity.compMemberType.getRelativeTime()!= null && !"".equals(MemberCardNewActivity.compMemberType.getRelativeTime())){
                            String relativeTime =  String.valueOf(MemberCardNewActivity.compMemberType.getRelativeTime().intValue());
                            tv_time.setText("有效期："+relativeTime+"个月");
                        }else {
                            tv_time.setText("");
                        }

                    }
                }
            }
            //卡面样式展示规则
            displayRule =  MemberCardNewActivity.compMemberType.getDisplayRule();
            if(displayRule != null && !"".equals(displayRule)){
                //空字符串为分割 判断样式规则 例子 1111或 0000   1 表示选中，0表示未选
                String[] rules = displayRule.trim().split("");
                for (int i = 0;i<rules.length;i++){
                    String disRule  = rules[i];
                    if(disRule != null && !"".equals(disRule)){
                        int h = i-1;
                        if("1".equals(disRule)){
                            for (int j = 0;j<typeList.size();j++){
                                CateBean cateBean1 = typeList.get(j);
                                if(h ==j){
                                    cateBean1.setChecked(true);
                                }
                            }
                            if(h == 0){
                                code1 = "1";
                                circle_view.setVisibility(View.VISIBLE);
//                                setBrandLogo();
                            }else if(h == 1){
                                code2 = "1";
                                tv_card_name.setVisibility(View.VISIBLE);

                            }else if(h == 2){
                                code3 = "1";
                                tv_condition.setVisibility(View.VISIBLE);
                            }else if(h == 3){
                                code4 = "1";
                                tv_time.setVisibility(View.VISIBLE);
                            }
                        }else {
                            for (int j = 0;j<typeList.size();j++){
                                CateBean cateBean1 = typeList.get(j);
                                if(h ==j){
                                    if(!"N".equals(custTime)){
                                        cateBean1.setChecked(true);
                                        tv_time.setVisibility(View.VISIBLE);
                                    }else {
                                        cateBean1.setChecked(false);
                                        tv_time.setVisibility(View.GONE);
                                    }

                                }
                            }
                            if(h == 0){
                                code1 = "0";
                                circle_view.setVisibility(View.GONE);
//                                setBrandLogo();
                            }else if(h == 1){
                                code2 = "0";
                                tv_card_name.setVisibility(View.GONE);
                            }else if(h == 2){
                                code3 = "0";
                                tv_condition.setVisibility(View.GONE);
                            }else if(h == 3){

                                if(!"N".equals(custTime)){
                                    code4= "1";
                                    tv_time.setVisibility(View.VISIBLE);
                                }else {
                                    code4= "0";
                                    tv_time.setVisibility(View.GONE);
                                }


                            }
                        }
                    }

                }
                myAdapter.notifyDataSetChanged();
            }else {
                displayRule = code1+code2+code3+code4;
            }

            String joinCondition = MemberCardNewActivity.compMemberType.getJoinCondition();
            if("1".equals(joinCondition)){ //在线购买

               tv_condition.setText("在线购买");

            }else if("0".equals(joinCondition)){ //免费领取
                tv_condition.setText("在线领取");
            }

        }
        colour = MemberCardNewActivity.compMemberType.getColour();

        if(colour != null && !"".equals(colour)){

        }else {
            colour = "FFFFFF";
        }

        if("000000".equals(colour)){
            colour = "000000";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
        }else if("FFFFFF".equals(colour)){
            colour = "FFFFFF";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
        }else if("FF2727".equals(colour)){
            colour = "FF2727";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
        }else if("F86000".equals(colour)){
            colour = "F86000";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
        }else if("FFD534".equals(colour)){
            colour = "FFD534";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
        }else if("0486FE".equals(colour)){
            colour = "0486FE";

            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
        }else if("51D7B9".equals(colour)){
            colour = "51D7B9";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
        }else if("9E4DEE".equals(colour)){
            colour = "9E4DEE";
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
        }
    }

    private void setSetTep(){
        ivOne.setBackgroundResource(R.drawable.ic_first_clicked);
        ivEllipsisOne.setBackgroundResource(R.drawable.ic_ellipsis_blue);
        ivTwo.setBackgroundResource(R.drawable.ic_two_clicked);
    }

    protected void initTemplateData(){
        empletAdapter = new CardTempletListAdapter(this,templateChildList);
        grid_view1.setAdapter(empletAdapter);
        grid_view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long pid) {



                if(materialCardList != null && materialCardList.size()>0){
                    for (MaterialCard materialCard : materialCardList){
                        materialCard.setChecked(false);
                    }
                    materialCardAdapter.notifyDataSetChanged();
                }

                isTemplet = "1";
                if(currentNum == -1){ //选中
                    for(TemplateChild templateChild : templateChildList){
                        templateChild.setChecked(false);
                    }
                    templateChildList.get(position).setChecked(true);
                    currentNum = position;


                }else if(currentNum == position){ //同一个item选中变未选中
                    for(TemplateChild templateChild : templateChildList){
                        templateChild.setChecked(false);
                    }
                    materialCardList.get(position).setChecked(true);
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(TemplateChild templateChild : templateChildList){
                        templateChild.setChecked(false);
                    }
                    templateChildList.get(position).setChecked(true);
                    currentNum = position;

                }
                TemplateChild templateChild = templateChildList.get(position);
                String bgPath = HttpUrls.IMAGE_ULR + templateChild.getImgUrl();

                Glide.with(MemberCardNewTwoActivity.this).load(bgPath).into(new SimpleTarget<Drawable>(100,220) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll_image.setBackground(resource);    //设置背景
                        }
                    }
                });
                materialId = String.valueOf(templateChild.getId());
                background = templateChild.getImgUrl();
                name = templateChild.getName();
                companyId = compId;
                empletAdapter.notifyDataSetChanged();

            }
        });
    }
    protected void initMaterCardData(){
        materialCardAdapter = new SelectMaterialCardAdapter(this,materialCardList);
        grid_view2.setAdapter(materialCardAdapter);
        grid_view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (templateChildList != null && templateChildList.size() > 0) {
                    for (TemplateChild templateChild : templateChildList) {
                        templateChild.setChecked(false);
                    }
                    empletAdapter.notifyDataSetChanged();
                }
                isTemplet = "0";
                if(currentNum == -1){ //选中
                    for(MaterialCard materialCard : materialCardList){
                        materialCard.setChecked(false);
                    }
                    materialCardList.get(position).setChecked(true);
                    currentNum = position;


                }else if(currentNum == position){ //同一个item选中变未选中
                    for(MaterialCard materialCard : materialCardList){
                        materialCard.setChecked(false);
                    }
                    materialCardList.get(position).setChecked(true);
                    currentNum = position;
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(MaterialCard materialCard : materialCardList){
                        materialCard.setChecked(false);
                    }
                    materialCardList.get(position).setChecked(true);
                    currentNum = position;

                }
                materialCard = materialCardList.get(position);
                materialId = String.valueOf(materialCard.getId());
                String bgPath = HttpUrls.IMAGE_ULR + materialCard.getBackground();
                background = materialCard.getBackground();
                Glide.with(MemberCardNewTwoActivity.this).load(bgPath).into(new SimpleTarget<Drawable>(100,220) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll_image.setBackground(resource);    //设置背景
                        }
                    }
                });
                materialCardAdapter.notifyDataSetChanged();

            }
        });
    }


    private void setleftVisible(){
        tv_card_style_title_left.setTextColor(getResources().getColor(R.color.btn_color_blue));
        left_view.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        tv_card_content_title_right.setTextColor(getResources().getColor(R.color.color_gray_deep));
        right_view.setBackground(getResources().getDrawable(R.color.color_gray_deep));
        left_view.setVisibility(View.VISIBLE);
        right_view.setVisibility(View.INVISIBLE);

        tv_card_template_left.setTextColor(getResources().getColor(R.color.btn_color_blue));
        tv_card_material_right.setTextColor(getResources().getColor(R.color.color_gray_deep));
    }

    private void setRightVisible(){
        tv_card_style_title_left.setTextColor(getResources().getColor(R.color.color_gray_deep));
        left_view.setBackground(getResources().getDrawable(R.color.color_gray_deep));
        tv_card_content_title_right.setTextColor(getResources().getColor(R.color.btn_color_blue));
        right_view.setBackground(getResources().getDrawable(R.color.btn_color_blue));
        left_view.setVisibility(View.INVISIBLE);
        right_view.setVisibility(View.VISIBLE);

        tv_card_template_left.setTextColor(getResources().getColor(R.color.color_gray_deep));
        tv_card_material_right.setTextColor(getResources().getColor(R.color.btn_color_blue));
    }

    @OnClick({R.id.iv_back,R.id.tv_right,
            R.id.ll_card_right,R.id.ll_card_left,R.id.ll_card_template,R.id.ll_card_material,
            R.id.btn_up,R.id.btn_save,R.id.btn_pre,
            R.id.btn_user})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.startActivity(MemberCardManageActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
                break;

            case R.id.tv_right:
                ToastUtils.showCustomShortBottom(getResources().getString(R.string.create_success));


                setValue();

                //复制并创建
                isCopyCreate = "NO";
                isEditCampaign = "YES";
//                compaignStatus = "NEW";
                MemberCardNewActivity.memberCardStatusNew = "NEW";
                MemberCardNewActivity.compMemberType.setMemtypeIdArray(null);
                MemberCardNewActivity.compMemberType.setCompMemtypeId(null);
                MemberCardNewActivity.compMemberType.setCompMemberGroup("");
                Intent intent1 = new Intent(MemberCardNewTwoActivity.this,MemberCardNewActivity.class);

                intent1.putExtra("isCopyCreate",isCopyCreate);
                intent1.putExtra("isEditCampaign",isEditCampaign);
                intent1.putExtra("compMemTypeId",MemberCardNewActivity.compMemtypeId);
                intent1.putExtra("memberCardStatusNew",MemberCardNewActivity.memberCardStatusNew);  //新建
                ActivityUtils.startActivity(intent1,
                        R.anim.slide_in_left, R.anim.slide_out_right);


                break;
            case R.id.ll_card_left:
                setleftVisible();
                ll_member_card_style_left.setVisibility(View.VISIBLE);
                ll_member_card_content_right.setVisibility(View.GONE);



                break;
            case R.id.ll_card_right:
                setRightVisible();
                ll_member_card_style_left.setVisibility(View.GONE);
                ll_member_card_content_right.setVisibility(View.VISIBLE);


                break;
            case R.id.ll_card_template:

                tv_card_template_left.setTextColor(getResources().getColor(R.color.btn_color_blue));
                tv_card_material_right.setTextColor(getResources().getColor(R.color.color_gray_deep));
                isTemplet = "1";
                grid_view1.setVisibility(View.VISIBLE);
                grid_view2.setVisibility(View.GONE);

                break;
            case R.id.ll_card_material:

                tv_card_template_left.setTextColor(getResources().getColor(R.color.color_gray_deep));
                tv_card_material_right.setTextColor(getResources().getColor(R.color.btn_color_blue));
                isTemplet = "0";
                grid_view1.setVisibility(View.GONE);
                grid_view2.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_up: //上一步
                setValue();
                ActivityUtils.finishActivity(MemberCardNewTwoActivity.class,R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.btn_pre:  //预览
                setValue();
                setPopDialog();
                break;
            case R.id.btn_save: //保存
                if(isCheck()){
                    return;
                }
                memtypeStatus = "0";
                setValue();
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_user)) {

                    if("EDIT".equals(MemberCardNewActivity.memberCardStatusNew)){
                        presenter.updateMemberType(this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                    }else {
                        presenter.insertMemberType(this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                    }
                }

                break;

            case R.id.btn_user: //启用

                if(isCheck()){
                    return;
                }
                memtypeStatus = "1";
                setValue();
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_save)) {

//                    presenter.isReceivingAccount(this,userId,memtypeStatus);
                    if("EDIT".equals(MemberCardNewActivity.memberCardStatusNew)){
                        presenter.updateMemberType(this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                    }else {
                        presenter.insertMemberType(this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                    }

                }


                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(MemberCardManageActivity.class,
                R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }


    private  boolean isCheck(){

        if(MemberCardNewActivity.compMemberType!= null){
            String cardName = MemberCardNewActivity.compMemberType.getCompMemberGroup();

            if(cardName == null || "".equals(cardName)){
                setDialog("第一步中的,会员卡名称未填写");
                return true;
            }


            String memtypeNotes = MemberCardNewActivity.compMemberType.getMemtypeNotes();
            if(memtypeNotes == null || "".equals(memtypeNotes)){
                setDialog("第一步中的，会员权益未选择");
                return true;
            }

         /*   String posId = MemberCardNewActivity.compMemberType.getPosId();
            if(posId == null || "".equals(posId)){
                setDialog("第一步中的，请输入长度在15位以内的id");
                return true;
            }
*/


            String joinCondition = MemberCardNewActivity.compMemberType.getJoinCondition();
            if("1".equals(joinCondition)){ //在线购买

                if(MemberCardNewActivity.compMemberType.getPurchasingPrice() != null ){
                    double purchasingPrice  = MemberCardNewActivity.compMemberType.getPurchasingPrice().doubleValue();
                    if(purchasingPrice<= 0){
                        setDialog("第一步中的，购买金额未填写");
                        return true;
                    }
                }else {
                    setDialog("第一步中的，购买金额未填写");
                    return true;
                }

            }else if("0".equals(joinCondition)){ //免费领取

            }

            String customTime1 = MemberCardNewActivity.compMemberType.getCustomTime();

            if("N".equals(MemberCardNewActivity.effectiveValue)){  //长期有效否   否则为长期有效

                String startTime= MemberCardNewActivity.compMemberType.getAbsoluteStartime();
                String endTime= MemberCardNewActivity.compMemberType.getAbsoluteEndtime();
                Integer promptTime = MemberCardNewActivity.compMemberType.getPromptTime();
                if("A".equals(MemberCardNewActivity.wayValue)){
                    if(startTime == null || "".equals(startTime)){
                        setDialog("第一步中的，请选择开始时间");
                        return true;
                    }
                    if(endTime == null || "".equals(endTime)){
                        setDialog("第一步中的，请选择结束时间");
                        return true;
                    }

                    String reminder = MemberCardNewActivity.compMemberType.getReminder();
                    if(reminder == null || "".equals(reminder)){
                        setDialog("第一步中的，过期提示语未填写");
                        return true;
                    }
                }else if("R".equals(MemberCardNewActivity.wayValue)){

                }


            }
        }



        if("1".equals(isTemplet)){  //模板

            if(background == null ||"".equals(background)){
                setDialog("第二步中的，卡面样式素材未选择");
                return true;
            }
        }else {//素材
            if(materialId == null || "".equals(materialId)){
                setDialog("第二步中的，卡面样式素材未选择");
                return true;
            }
        }
        if(colour != null && !"".equals(colour)){
        }else {
            setDialog("第二步中的，卡面样式颜色未选择");
            return true;
        }



        return false;
    }
    private void setValue(){
        //样式规则 品牌logo
        displayRule = code1+code2+code3+code4;
        MemberCardNewActivity.compMemberType.setDisplayRule(displayRule);
        MemberCardNewActivity.compMemberType.setColour(colour);

        if("1".equals(isTemplet)){
            MemberCardNewActivity.compMemberType.setBackground(background);
            MemberCardNewActivity.compMemberType.setName(name);
            MemberCardNewActivity.compMemberType.setCompId(Long.parseLong(compId));

            //需要将模板的id和图片指给素材
            if(materialId != null && !"".equals(materialId)){
                MemberCardNewActivity.compMemberType.setMaterialId(Long.parseLong(materialId));
            }else {
                MemberCardNewActivity.compMemberType.setMaterialId(null);
            }

            MemberCardNewActivity.compMemberType.setBackground(background);
            MemberCardNewActivity.compMemberType.setMaterialBack(background);
        }else {
            if(materialId != null && !"".equals(materialId)){
                MemberCardNewActivity.compMemberType.setMaterialId(Long.parseLong(materialId));
            }else {
                MemberCardNewActivity.compMemberType.setMaterialId(null);
            }

            MemberCardNewActivity.compMemberType.setBackground(background);
            MemberCardNewActivity.compMemberType.setMaterialBack(background);

        }
        MemberCardNewActivity.compMemberType.setIsTemplet(isTemplet);
        if("0".equals(memtypeStatus)){
            MemberCardNewActivity.compMemberType.setMemtypeStatus(memtypeStatus);  //保存
        }else {
            MemberCardNewActivity.compMemberType.setMemtypeStatus(memtypeStatus);  //启用
        }

        compMemberTypeJSON=new Gson().toJson(MemberCardNewActivity.compMemberType);
        materialCardJSON=new Gson().toJson(materialCard);
    }

    public void setDialog(String tip){

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

    public void setQiYongDialog(String tip){

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        String ss =  "您当前设置的出售会员卡，在"+"<font size = '16'color = \'#0486FE\' weight = 'bolder'>"+tip+"</font>"+"还未添加收款账号，是否确认启用。";
        textView.setText(Html.fromHtml(ss));
        Button confirmBtn= rootView.findViewById(R.id.confirmBtn);
        confirmBtn.setBackground(getResources().getDrawable(R.drawable.dialog_confirm_submit_util_right_btn_bg_white));
        Button cancelBtn=rootView.findViewById(R.id.cancelBtn);
        confirmBtn.setText("确定启用");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if("EDIT".equals(MemberCardNewActivity.memberCardStatusNew)){
                    presenter.updateMemberType(MemberCardNewTwoActivity.this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                }else {
                    presenter.insertMemberType(MemberCardNewTwoActivity.this,userId,compMemberTypeJSON,brandId,memtypeStatus);
                }
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

    private void setPopDialog() {

        View customView = View.inflate(this, R.layout.layout_member_card_new_pre, null);

        PopWindow popAlertView = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopAlert)
                .setView(customView)
                .show();

        LinearLayout ll_content = (LinearLayout) customView.findViewById(R.id.ll_content);
        LinearLayout ll_image = (LinearLayout)customView.findViewById(R.id.ll_image);

        Glide.with(MemberCardNewTwoActivity.this).load(HttpUrls.IMAGE_ULR+background).into(new SimpleTarget<Drawable>(100,220) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ll_image.setBackground(resource);    //设置背景
                }
            }
        });
        CircleImageView circle_view = (CircleImageView)customView.findViewById(R.id.circle_view);
        TextView tv_card_name = (TextView)customView.findViewById(R.id.tv_card_name);
        TextView tv_time = (TextView)customView.findViewById(R.id.tv_time);
        TextView tv_brand_use = (TextView)customView.findViewById(R.id.tv_brand_use);

        TextView tv_condition = (TextView)customView.findViewById(R.id.tv_condition);
        LinearLayout ll_share = (LinearLayout) customView.findViewById(R.id.ll_share);

        TextView tv_share = (TextView)customView.findViewById(R.id.tv_share);

        TextView tv_rights_content = (TextView)customView.findViewById(R.id.tv_rights_content);
        TextView tv_remark = (TextView)customView.findViewById(R.id.tv_remark);
        TextView tv_date = (TextView)customView.findViewById(R.id.tv_date);

        Button btn_use = (Button) customView.findViewById(R.id.btn_use);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popAlertView.dismiss();
            }
        });

        LinearLayout ll_quit = (LinearLayout)customView.findViewById(R.id.ll_quit);
        ll_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });
        LinearLayout ll_right = (LinearLayout)customView.findViewById(R.id.ll_right);
        ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAlertView.dismiss();
            }
        });

        //会员权益
        String memtypeNotes = "";

        Integer month = 0;
        String relativeTime = "";
        String remark = "";
        String joinCondition = "";
        if( MemberCardNewActivity.compMemberType!= null ){
            String cardName  =MemberCardNewActivity.compMemberType.getMemtypeName();
            String isDefault =  MemberCardNewActivity.compMemberType.getIsDefault();
            month =  MemberCardNewActivity.compMemberType.getRelativeTime();
            remark = MemberCardNewActivity.compMemberType.getNotes();
            joinCondition = MemberCardNewActivity.compMemberType.getJoinCondition();

            if(cardName != null && !"".equals(cardName)){
                tv_card_name.setText(cardName);
            }else {
                tv_card_name.setText(cardName);
            }
            if("0".equals(joinCondition)){
                tv_condition.setText("在线领取");
            }else {
                tv_condition.setText("在线购买");
            }
            //默认会员卡 不显示有效期时间
            if("0".equals(isDefault)){
                tv_time.setVisibility(View.GONE);
            }else {  //不是默认展示有效期
                tv_time.setVisibility(View.VISIBLE);
                String custTime =  MemberCardNewActivity.compMemberType.getCustomTime();
                //长期有效
                if("N".equals(custTime)){  //如果是长期有效 不展示有效期时间
                    tv_time.setVisibility(View.GONE);
                    tv_time.setText("");
                    tv_date.setText("本会员卡使用期限：可无限期使用");
                }else {//固定起止时间 展示

                    tv_time.setText("");
                    if("A".equals(custTime)){ //绝对时间(固定起止时间)
                        String absoluteStartime =MemberCardNewActivity.compMemberType.getAbsoluteStartime();
                        String absoluteEndtime = MemberCardNewActivity.compMemberType.getAbsoluteEndtime();
                        String startTime = "";
                        String endTime = "";
                        if(absoluteStartime != null && !"".equals(absoluteStartime)){
                            startTime= absoluteStartime.replace("-",".").substring(0,10);
                        }
                        if(absoluteEndtime != null && !"".equals(absoluteEndtime)){
                            endTime= absoluteEndtime.replace("-",".").substring(0,10);
                        }

                        tv_time.setText(startTime+ " - "+endTime);
                        tv_date.setText("本会员卡使用期限："+startTime+ " - "+endTime);
                    }else {

                        if(MemberCardNewActivity.compMemberType.getRelativeTime()!= null && !"".equals(MemberCardNewActivity.compMemberType.getRelativeTime())){
                             relativeTime =  String.valueOf(MemberCardNewActivity.compMemberType.getRelativeTime().intValue());
                            tv_time.setText(relativeTime+"个月");
                            tv_date.setText("本会员卡使用期限：获得会员卡"+relativeTime+ "个月可使用");
                        }else {
                            tv_time.setText("");
                            tv_date.setText("本会员卡使用期限：获得会员卡 个月可使用");
                        }

                    }
                }
            }
            //卡面样式展示规则
            displayRule =  code1+code2+code3+code4;
            if(displayRule != null && !"".equals(displayRule)){
                //空字符串为分割 判断样式规则 例子 1111或 0000   1 表示选中，0表示未选
                String[] rules = displayRule.trim().split("");
                for (int i = 0;i<rules.length;i++){
                    String disRule  = rules[i];
                    if(disRule != null && !"".equals(disRule)){
                        int h = i-1;
                        if("1".equals(disRule)){

                            if(h == 0){
//                                setBrandLogo();
                                circle_view.setVisibility(View.VISIBLE);

                            }else if(h == 1){
                                tv_card_name.setVisibility(View.VISIBLE);

                            }else if(h == 2){
                                tv_condition.setVisibility(View.VISIBLE);
                            }else if(h == 3){
                                tv_time.setVisibility(View.VISIBLE);
                            }
                        }else if("0".equals(disRule)){

                            if(h == 0){
//                                setBrandLogo();
                                circle_view.setVisibility(View.GONE);

                            }else if(h == 1){
                                tv_card_name.setVisibility(View.GONE);
                            }else if(h == 2){
                                tv_condition.setVisibility(View.GONE);
                            }else if(h == 3){
                                tv_time.setVisibility(View.GONE);
                            }
                        }
                    }

                }
            }
            if(colour == null || "".equals(colour)){
                colour = MemberCardNewActivity.compMemberType.getColour();
            }

            memtypeNotes = MemberCardNewActivity.compMemberType.getMemtypeNotes();
        }

        if(memtypeNotes != null  && !"".equals(memtypeNotes)){
            tv_rights_content.setText(memtypeNotes);
        }else {
            tv_rights_content.setText("");
        }

        if(remark != null  && !"".equals(remark)){
            tv_remark.setText(remark);
        }else {
            tv_remark.setText("");
        }

        if("000000".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_black));
        }else if("FFFFFF".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_white));
        }else if("FF2727".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_red));
        }else if("F86000".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_orange));
        }else if("FFD534".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_yellow));
        }else if("0486FE".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_blue));
        }else if("51D7B9".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_green));
        }else if("9E4DEE".equals(colour)){
            tv_card_name.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
            tv_time.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
            tv_condition.setTextColor(getResources().getColor(R.color.member_card_style_color_purple));
        }
        String brandName = "";
       String brandLogo =SPUtils.getInstance().getString("brandLogo");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_logo_default)
                .fallback(R.drawable.ic_image_loading);

        Glide.with(this).load(brandLogo).apply(requestOptions).into(circle_view);


        if(MemberCardNewActivity.brand != null){
            brandName = MemberCardNewActivity.brand.getName();
            if(brandName == null || "".equals(brandName)){
                brandName = SPUtils.getInstance().getString("brandName");
            }
        }else {
            brandName = SPUtils.getInstance().getString("brandName");
        }

        if(brandName != null && !"".equals(brandName)){
            tv_brand_use.setText(brandName+"旗下店铺可用");
        }else {
            tv_brand_use.setText("");
        }

        if("0".equals(MemberCardNewActivity.isShareright)){ // 不可共享


        }else if("1".equals(MemberCardNewActivity.isShareright)){ //可共享
            ll_share.setVisibility(View.GONE);
        }else {
            ll_share.setVisibility(View.VISIBLE);
        }





    }
}
