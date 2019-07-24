package com.zwx.scan.app.feature.campaign;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.utils.MaxTextLengthFilter;
import com.zwx.scan.app.widget.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class CampaignTitleContentActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.edt_content)
    protected ContainsEmojiEditText editText;
    @BindView(R.id.tv_tip)
    protected TextView tvTip;
    String title;
    String forwardTitle;
    String tip;
    String content = "";
    int contetnLenth = 0 ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_campaign_title_content;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        forwardTitle = getIntent().getStringExtra("forwardTitle");
        content = getIntent().getStringExtra("content");

        editText.setText(content != null && !"".equals(content)?content:"");

        tvTitle.setText(title);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("保存");
    }

    @Override
    protected void initData() {
        if(content != null && !"".equals(content)){
            contetnLenth = content.length();
        }
        if("title".equals(forwardTitle)){
            editText.setHint(getResources().getString(R.string.forward_title));
            editText.setFilters(new InputFilter[]{new MaxTextLengthFilter(30)});

            tvTip.setText(contetnLenth+"/30");
        }else {
            editText.setHint(getResources().getString(R.string.forward_content));
            editText.setFilters(new InputFilter[]{new MaxTextLengthFilter(45)});
            tvTip.setText(contetnLenth+"/45");
        }
        editText.setSelection(editText.getText().toString().trim().length());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
//                content = editText.getText().toString().trim();
                contetnLenth = s.length();
                if("title".equals(forwardTitle)){

                    tvTip.setText(contetnLenth+"/30");
                }else {

                    tvTip.setText(contetnLenth+"/45");
                }
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.tv_right})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                ActivityUtils.finishActivity(CampaignTitleContentActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_right:
                content = editText.getText().toString().trim();

                if("title".equals(forwardTitle)){
                    SPUtils.getInstance().put("forwardTitle",content);
                    intent = new Intent(CampaignTitleContentActivity.this,PosterListActivity.class);
                    intent.putExtra("forward",content);

                    setResult(4000,intent);
                    finish();
                }else {
                    intent = new Intent(CampaignTitleContentActivity.this,PosterListActivity.class);
                    intent.putExtra("forward",content);
                    SPUtils.getInstance().put("forwardContent",content);
                    setResult(5000,intent);

                    finish();
                }
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.finishActivity(CampaignTitleContentActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
