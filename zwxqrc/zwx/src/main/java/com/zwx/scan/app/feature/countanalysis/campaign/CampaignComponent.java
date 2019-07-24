package com.zwx.scan.app.feature.countanalysis.campaign;

import com.zwx.scan.app.injector.component.ApplicationComponent;
import com.zwx.scan.app.injector.scope.ActivtiyScoped;

import dagger.Component;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
@ActivtiyScoped
@Component(modules = CampaignModule.class,dependencies = ApplicationComponent.class)
public interface CampaignComponent {

    void inject(CampaignAnalysisActivity activity);

    void inject(CampaignAnalysisDetailActivity activity);

    void inject(GiveAnalysisActivity activity);
    void inject(GiveAnalysisDetailActivity activity);

    void inject(PinTuanAnalysisActivity activity);
    void inject(PinTuanAnalysisDetailActivity activity);
}
