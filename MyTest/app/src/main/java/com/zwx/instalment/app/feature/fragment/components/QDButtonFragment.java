/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zwx.instalment.app.feature.fragment.components;

import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmuidemo.lib.annotation.Widget;
import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.BaseQMUIFragment;
import com.zwx.instalment.app.manager.QDDataManager;
import com.zwx.instalment.app.model.QDItemDescription;

import butterknife.BindView;
import butterknife.ButterKnife;

@Widget(name = "RoundButton", iconRes = R.drawable.icon_grid_button)
public class QDButtonFragment extends BaseQMUIFragment {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    private QDItemDescription mQDItemDescription;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_button, null);
        ButterKnife.bind(this, view);
        mQDItemDescription = QDDataManager.getInstance().getDescription(this.getClass());

        initTopBar();

        return view;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mTopBar.setTitle(mQDItemDescription.getName());
    }
}
