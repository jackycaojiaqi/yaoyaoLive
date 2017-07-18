package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.tv_recharge_nk_num)
    TextView tvRechargeNkNum;
    @BindView(R.id.btn_recharge_1)
    Button btnRecharge1;
    @BindView(R.id.btn_recharge_2)
    Button btnRecharge2;
    @BindView(R.id.btn_recharge_3)
    Button btnRecharge3;
    @BindView(R.id.btn_recharge_4)
    Button btnRecharge4;
    @BindView(R.id.btn_recharge_5)
    Button btnRecharge5;
    @BindView(R.id.btn_recharge_6)
    Button btnRecharge6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,"金币充值");
    }

    @OnClick({R.id.btn_recharge_1, R.id.btn_recharge_2, R.id.btn_recharge_3, R.id.btn_recharge_4, R.id.btn_recharge_5, R.id.btn_recharge_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_1:
                break;
            case R.id.btn_recharge_2:
                break;
            case R.id.btn_recharge_3:
                break;
            case R.id.btn_recharge_4:
                break;
            case R.id.btn_recharge_5:
                break;
            case R.id.btn_recharge_6:
                break;
        }
    }
}
