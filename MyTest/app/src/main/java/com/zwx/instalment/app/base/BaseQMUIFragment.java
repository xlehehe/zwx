package com.zwx.instalment.app.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.zwx.instalment.app.feature.main.QDMainActivity;
import com.zwx.instalment.app.manager.QDDataManager;
import com.zwx.instalment.app.manager.QDUpgradeManager;
import com.zwx.instalment.app.model.QDItemDescription;

/**
 * author : lizhilong
 * time   : 2019/07/03
 * desc   :
 * version: 1.0
 **/
public abstract class BaseQMUIFragment  extends QMUIFragment {

    public BaseQMUIFragment() {
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        QDUpgradeManager.getInstance(getContext()).runUpgradeTipTaskIfExist(getActivity());

    }

    protected void goToWebExplorer(@NonNull String url, @Nullable String title) {
        Intent intent = QDMainActivity.createWebExplorerIntent(getContext(), url, title);
        startActivity(intent);
    }

    protected void injectDocToTopBar(QMUITopBar topBar) {
        final QDItemDescription description = QDDataManager.getInstance().getDescription(this.getClass());
        if (description != null) {
            topBar.addRightTextButton("DOC", QMUIViewHelper.generateViewId())
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToWebExplorer(description.getDocUrl(), description.getName());
                        }
                    });
        }
    }

    protected void injectDocToTopBar(QMUITopBarLayout topBar){
        final QDItemDescription description = QDDataManager.getInstance().getDescription(this.getClass());
        if (description != null) {
            topBar.addRightTextButton("DOC", QMUIViewHelper.generateViewId())
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToWebExplorer(description.getDocUrl(), description.getName());
                        }
                    });
        }
    }
}
