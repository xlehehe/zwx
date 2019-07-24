package com.zwx.scan.app.feature.countanalysis.campaign;

import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Module;
import dagger.Provides;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
@Module
public class CampaignModule {

    private  CampaignContract.View view;

    public CampaignModule(CampaignContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public CampaignContract.Presenter providePresenter(){
        return new CampaignPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public CampaignContract.View provideCampaignContractView(){
        return view;
    }
}
