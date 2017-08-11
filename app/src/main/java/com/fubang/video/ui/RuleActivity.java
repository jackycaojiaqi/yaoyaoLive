package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/7/24.
 */
public class RuleActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_rule);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, R.string.rule);
    }
}
