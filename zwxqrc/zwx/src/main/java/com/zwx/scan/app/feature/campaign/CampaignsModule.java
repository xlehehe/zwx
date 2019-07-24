package com.zwx.scan.app.feature.campaign;

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
public class CampaignsModule {

    private  CampaignsContract.View view;

    public CampaignsModule(CampaignsContract.View view) {
        this.view = view;
    }

    @ActivtiyScoped
    @Provides
    public CampaignsContract.Presenter providePresenter(){
        return new CampaignsPresenter(view);
    }

    @ActivtiyScoped
    @Provides
    public CampaignsContract.View provideCampaignsContractView(){
        return view;
    }
}
