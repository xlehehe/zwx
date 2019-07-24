package com.zwx.scan.app.feature.shop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zwx.library.swipe.SwipeMenuRecyclerView;
import com.zwx.library.utils.RegexUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.data.bean.Store;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.campaign.LhGeEventBus;
import com.zwx.scan.app.feature.campaign.LhPtEventBus;
import com.zwx.scan.app.widget.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 店铺联系电话
 * version: 1.0
 **/
public class ShopTelFragment extends BaseFragment<ShopContract.Presenter> implements ShopContract.View ,View.OnClickListener,ShopTelListAdapter.UpdaTel{


    @BindView(R.id.list_view)
    protected MyListView listView;

    protected static List<Store> storeList = new ArrayList<>();

    protected  ShopTelListAdapter adapter = null;

    protected String userId;

    public ShopTelFragment() {
        // Required empty public constructor
    }

    public static ShopTelFragment getInstance(String param) {
        ShopTelFragment instance = new ShopTelFragment();
        return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LhGeEventBus event) {

        if (event != null) {
            String storeId = event.getImageId();
            String tel = event.getName();
            if(tel != null && !"".equals(tel)){

                if(RegexUtils.isMobileSimple(tel)||RegexUtils.isTel(tel)){
                    presenter.updateShopTel(getActivity(),storeId,tel);
                }else {
                    ToastUtils.showShort("请输入正确联系电话");
                }

            }

        }
    }
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_shop_tel;
    }

    @Override
    protected void initInjector() {

        DaggerShopComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        userId = SPUtils.getInstance().getString("userId");
      /*  Store store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);
        store = new Store();
        store.setName("高新万达超级大的话哦话的店…");
        store.setTelphone("18615269385");
        storeList.add(store);*/





    }

    @Override
    protected void initData() {
        adapter = new ShopTelListAdapter(getActivity(),storeList);
//        adapter.setDatas(storeList);
        listView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        userId = SPUtils.getInstance().getString("userId");
        presenter.queryStoreByCompId(getActivity(),userId);

    }

    @Override
    public void onClick(View v) {

    }

    protected void updateTel(){

    }

    @Override
    public void updateTel(String storeId,String tel) {
//        userId = SPUtils.getInstance().getString("userId");
//        presenter.updateShopTel(getActivity(),storeId,tel);
    }



}
