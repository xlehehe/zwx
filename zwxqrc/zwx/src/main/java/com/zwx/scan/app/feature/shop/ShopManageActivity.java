package com.zwx.scan.app.feature.shop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.KeyboardUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.data.bean.ProductSetNew;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.utils.EmojiInputFilter;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.CustomEditText;
import com.zwx.scan.app.widget.swipelistview.MySwipeMenuListView;
import com.zwx.scan.app.widget.swipelistview.SwipeMenu;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuCreator;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuItem;
import com.zwx.scan.app.widget.swipelistview.SwipeMenuListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城管理
 * version: 1.0
 **/
public class ShopManageActivity extends BaseActivity<ShopContract.Presenter> implements ShopContract.View, View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.edt_search)
    protected CustomEditText edtSearch;


 /*   @BindView(R.id.list_view)
    public SwipeMenuRecyclerView list_view;*/
     @BindView(R.id.list_view)
     public MySwipeMenuListView list_view;
    @BindView(R.id.ptr)
    public PullToRefreshScrollView ptr;
//    @BindView(R.id.ptr)
//    public PtrClassicFrameLayout ptr;

    @BindView(R.id.ll_no_data)
    protected LinearLayout ll_no_data;
    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;
    private String userId;
    private String compId;
    protected static int pageNumber = 1;
    protected int pageSize = 10;

    protected String memberTel = "";
    protected  String memberName = "";
    private String catId;
    protected ProductSetNewAdapter listAdapter = null;
    protected AlertDialog.Builder builder = null;
    protected AlertDialog dialog = null;
    protected String catName;
    protected String salesType;

    protected boolean isAddCateFlag = true;

    public static List<ProductSetNew> productSetNewList = new ArrayList<ProductSetNew>();

    protected int adapterPosition = 0;

    protected static String productId = "";
    protected String productStatu = "";
    protected String operateFlag;
    String isAddStock = "YES";

    protected String curStock;
    protected String updateStock;
    protected String isEditOrNew = "NEW";

    protected String changeState = "add";
    protected String isClickSubmit = "YES";
    protected static String cancelOrSetHot = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_manage;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void initView() {
        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
        tvTitle.setText("商品管理");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("新增");


    }

    @Override
    protected void initData() {
        edtSearch.setFilters(new InputFilter[]{new EmojiInputFilter()});
        EventBus.getDefault().register(this);
        userId = SPUtils.getInstance().getString("userId");

        ll_no_data.setVisibility(View.VISIBLE);
        list_view.setVisibility(View.GONE);


        list_view.setMenuCreator(creator);
        listAdapter = new ProductSetNewAdapter(this,productSetNewList);
        list_view.setAdapter(listAdapter);
        ptr.setOnRefreshListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout   endLabelsr = ptr.getLoadingLayoutProxy(false, true);

        endLabelsr.setPullLabel("上拉可以加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("松开加载更多");// 下来达到一定距离时，显示的提示


        ILoadingLayout startLabelse = ptr.getLoadingLayoutProxy(true,false);
        startLabelse.setPullLabel("下拉可以刷新");// 刚下拉时，显示的提示
        startLabelse.setLastUpdatedLabel("正在刷新");// 刷新时
        startLabelse.setReleaseLabel("松开后刷新");// 下来达到一定距离时，显示的提示

        presenter.queryProductSet(ShopManageActivity.this, userId, memberName, "", pageSize, pageNumber, true);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                memberName = edtSearch.getText().toString().trim();

                if (memberName == null) {
                    memberName = "";
                }
                pageNumber = 1;
                KeyboardUtils.showSoftInput(edtSearch);
                productSetNewList.clear();
                presenter.queryProductSet(ShopManageActivity.this, userId, memberName, "", pageSize, pageNumber, true);

            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (productSetNewList != null && productSetNewList.size() > 0) {
                    ProductSetNew productSetNew = productSetNewList.get(position);
                    if (productSetNew != null) {
                        productStatu = productSetNew.getState();//商品状态  上架 下架 在售 未上架
                    }
                    productId = String.valueOf(productSetNew.getProductId());
                    isEditOrNew = "EDIT";
                    setIntentActivity();

                }
            }
        });
        list_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (menu.getViewType()) {
                    case 3:

                        if (productSetNewList != null && productSetNewList.size() > 0) {
                            ProductSetNew productSetNew = productSetNewList.get(adapterPosition);
                            productId = String.valueOf(productSetNew.getProductId());
                            productStatu = productSetNew.getState();
                            presenter.doDelete(ShopManageActivity.this, productId);
                        }

                        break;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_right:
                isEditOrNew = "NEW";
                productSetNewList.clear();
                setIntentActivity();
                break;
            case R.id.iv_back:
                ActivityUtils.finishActivity(ShopManageActivity.class,
                        R.anim.slide_in_left, R.anim.slide_out_right);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(ShopManageActivity.class,
                R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        productSetNewList.clear();
        pageNumber = 1;
        presenter.queryProductSet(ShopManageActivity.this, userId, memberName, "", pageSize, pageNumber, true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        pageNumber++;
        presenter.queryProductSet(ShopManageActivity.this, userId, memberName, "", pageSize, pageNumber, false);
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // Create different menus depending on the view type
            switch (menu.getViewType()) {
                case 3:
                    createMemuDelete(menu);
                    break;
                case 0:
                    break;

            }
        }

        private void createMemuDelete(SwipeMenu swipeRightMenu) {
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int width = getResources().getDimensionPixelSize(R.dimen.margin_100);
            SwipeMenuItem closeItem = new SwipeMenuItem(getApplicationContext());
            closeItem.setBackground(R.drawable.shape_selector_red);
            closeItem.setWidth(width);
            closeItem.setTitleSize(16);
            closeItem.setTitleColor(getResources().getColor(R.color.white));
            closeItem.setTitle("删除");
            swipeRightMenu.addMenuItem(closeItem);
        }

    };



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    //EventBus 接收事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(ProductEventBus event) {
        if (event != null) {
            productId = event.getProductId();
            operateFlag = event.getOperateFlag();
            curStock = event.getStock();
            if ("adjust".equals(operateFlag)) {  //调整库存
                showStockCate();
            } else if ("stop".equals(operateFlag)) {  //下架
                presenter.doUpdate(this, productId, operateFlag, "");
            } else if ("push".equals(operateFlag)) {  //上架
                presenter.doUpdate(this, productId, operateFlag, "");
            } else{  //取消热卖cancleHot     设为热卖 setHot
                presenter.doUpdate(this, productId, operateFlag, "");
            }

        }
    }

    //调整库存分类
    protected void showStockCate() {

        View customView = View.inflate(this, R.layout.layout_shop_manage_update_stock, null);
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(customView, 0, 0, 0, 0);
        EditText edt_sum = (EditText) customView.findViewById(R.id.edt_sum);
        edt_sum.setSelection(edt_sum.getText().length());
        TextView title = (TextView) customView.findViewById(R.id.title);
        edt_sum.setFilters(new InputFilter[]{new MaxTextLengthFilter(6)});

        TextView tv_cur_stock = (TextView) customView.findViewById(R.id.tv_cur_stock);
        RadioGroup rg_stock = (RadioGroup) customView.findViewById(R.id.rg_stock);
        RadioButton reduce_stock = (RadioButton) customView.findViewById(R.id.reduce_stock);
        RadioButton add_stock = (RadioButton) customView.findViewById(R.id.add_stock);
        TextView tv_update_stock = (TextView) customView.findViewById(R.id.tv_update_stock);
        tv_cur_stock.setText(curStock);
        tv_update_stock.setText(curStock);
        changeState = "add";
        add_stock.setChecked(true);
        reduce_stock.setChecked(false);

        rg_stock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String stockStr = "";
                updateStock = edt_sum.getText().toString().trim();
                curStock = tv_cur_stock.getText().toString().trim();
                switch (checkedId) {

                    case R.id.add_stock:
                        isAddStock = "YES";
                        changeState = "add";
                        add_stock.setChecked(true);
                        reduce_stock.setChecked(false);
                        updateStock = edt_sum.getText().toString().trim();
                        curStock = tv_cur_stock.getText().toString().trim();
                        if (curStock != null && !"".equals(curStock)) {

                            if (updateStock != null && !"".equals(updateStock)) {
                                int curStock2 = Integer.parseInt(curStock);
                                int updateStock2 = Integer.parseInt(updateStock);
                                int stock = curStock2 + updateStock2;
                                stockStr = String.valueOf(stock);
                                tv_update_stock.setText(stockStr);
                                isClickSubmit = "YES";
                            } else {
                                isClickSubmit = "NO";
                                tv_update_stock.setText(curStock);
                            }
                        }
                        break;

                    case R.id.reduce_stock:
                        isAddStock = "NO";
                        changeState = "sub";
                        add_stock.setChecked(false);
                        reduce_stock.setChecked(true);
                        updateStock = edt_sum.getText().toString().trim();
                        curStock = tv_cur_stock.getText().toString().trim();
                        if (curStock != null && !"".equals(curStock)) {
                            if (updateStock != null && !"".equals(updateStock) && !"0".equals(updateStock)) {

                                if (Integer.parseInt(updateStock) > Integer.parseInt(curStock)) {
                                    ToastUtils.showShort("减少数不能大于库存");
                                    isClickSubmit = "NO";
                                    return;
                                }
                                int curStock2 = Integer.parseInt(curStock);
                                int updateStock2 = Integer.parseInt(updateStock);
                                if (curStock2 - updateStock2 >= 0) {
                                    int stock = curStock2 - updateStock2;
                                    stockStr = String.valueOf(stock);
                                    tv_update_stock.setText(stockStr);
                                } else {
                                    isClickSubmit = "NO";
                                    ToastUtils.showShort("库存数不能小于零");
                                    return;
                                }
                            } else {
                                tv_update_stock.setText(curStock);
//                                ToastUtils.showShort("请输入库存数");
                                isClickSubmit = "NO";
                                return;
                            }

                        } else {
                            tv_update_stock.setText("0");
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        edt_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateStock = edt_sum.getText().toString().trim();
                curStock = tv_cur_stock.getText().toString().trim();
                String stockStr = "";

                if ("YES".equals(isAddStock)) {
                    if (curStock != null && !"".equals(curStock)) {

                        if (updateStock != null && !"".equals(updateStock)) {
                            int curStock2 = Integer.parseInt(curStock);
                            int updateStock2 = Integer.parseInt(updateStock);
                            int stock = curStock2 + updateStock2;
                            stockStr = String.valueOf(stock);
                            tv_update_stock.setText(stockStr);
                        } else {
                            tv_update_stock.setText(curStock);
                        }
                    }
                } else {
                    if (curStock != null && !"".equals(curStock)) {
                        if (updateStock != null && !"".equals(updateStock) && !"0".equals(updateStock)) {

                            if (Integer.parseInt(updateStock) > Integer.parseInt(curStock)) {
                                ToastUtils.showShort("减少数不能大于库存");
                                isClickSubmit = "NO";
                                return;
                            } else {
                                isClickSubmit = "YES";
                            }
                            int curStock2 = Integer.parseInt(curStock);
                            int updateStock2 = Integer.parseInt(updateStock);
                            if (curStock2 - updateStock2 >= 0) {
                                int stock = curStock2 - updateStock2;
                                stockStr = String.valueOf(stock);
                                tv_update_stock.setText(stockStr);
                                isClickSubmit = "YES";
                            } else {
                                ToastUtils.showShort("库存数不能小于零");
                                isClickSubmit = "NO";
                                return;
                            }
                        } else {
                            tv_update_stock.setText(curStock);
//                            ToastUtils.showShort("请输入库存数");
                            isClickSubmit = "NO";
                            return;
                        }

                    } else {
                        isClickSubmit = "YES";
                        tv_update_stock.setText("0");
                    }
                }


            }
        });
        Button btn_use = (Button) customView.findViewById(R.id.confirmBtn);
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stock = edt_sum.getText().toString().trim();
                KeyboardUtils.hideSoftInput(ShopManageActivity.this, edt_sum);
                if ("NO".equals(isClickSubmit)) {

                } else {
                    if(stock != null && !"".equals(stock)){

                    }else {
                        stock = "0";
                    }
                    presenter.updateProductStock(ShopManageActivity.this, userId, productId, stock, changeState);
                    dialog.dismiss();
                    isAddStock = "YES";
                }


            }
        });
        Button cancelBtn = (Button) customView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isAddStock = "YES";
            }
        });


        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    protected void setIntentActivity() {
        Intent intent = new Intent(ShopManageActivity.this, ShopManageNewActivity.class);
        if ("NEW".equals(isEditOrNew)) {
            intent.putExtra("productEditStatu", isEditOrNew);  //新建
        } else {
            intent.putExtra("productId", productId);
            intent.putExtra("isCopyCreate", "YES");
            intent.putExtra("productEditStatu", "EDIT");//编辑
            intent.putExtra("productStatu", productStatu);
        }
        ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.slide_out_left);
    }


    protected void remove(){
        if(productSetNewList != null && productSetNewList.size()>0){
            for(int i=0;i<productSetNewList.size();i++){
                for(int j=productSetNewList.size()-1;j>i;j--){
                    if(String.valueOf(productSetNewList.get(j).getSellGoodsId()).equals(String.valueOf(productSetNewList.get(i).getSellGoodsId()))){
                        productSetNewList.remove(i);
                    }
                }
            }
        }

    }



}
