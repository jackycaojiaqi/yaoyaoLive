package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class UserinfoEditActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_editinfo_pic1)
    ImageView ivEditinfoPic1;
    @BindView(R.id.iv_editinfo_pic2)
    ImageView ivEditinfoPic2;
    @BindView(R.id.iv_editinfo_pic3)
    ImageView ivEditinfoPic3;
    @BindView(R.id.iv_editinfo_pic4)
    ImageView ivEditinfoPic4;
    @BindView(R.id.iv_editinfo_pic5)
    ImageView ivEditinfoPic5;
    @BindView(R.id.iv_editinfo_video)
    ImageView ivEditinfoVideo;
    @BindView(R.id.tv_editinfo_sign)
    TextView tvEditinfoSign;
    @BindView(R.id.rll_editinfo_sign)
    RelativeLayout rllEditinfoSign;
    @BindView(R.id.tv_editinfo_name)
    TextView tvEditinfoName;
    @BindView(R.id.rll_editinfo_name)
    RelativeLayout rllEditinfoName;
    @BindView(R.id.tv_editinfo_gender)
    TextView tvEditinfoGender;
    @BindView(R.id.rll_editinfo_gender)
    RelativeLayout rllEditinfoGender;
    @BindView(R.id.tv_editinfo_address)
    TextView tvEditinfoAddress;
    @BindView(R.id.rll_editinfo_address)
    RelativeLayout rllEditinfoAddress;
    @BindView(R.id.tv_editinfo_birth)
    TextView tvEditinfoBirth;
    @BindView(R.id.rll_editinfo_birth)
    RelativeLayout rllEditinfoBirth;
    @BindView(R.id.tv_editinfo_tab)
    TextView tvEditinfoTab;
    @BindView(R.id.rll_editinfo_tab)
    RelativeLayout rllEditinfoTab;
    @BindView(R.id.tv_editinfo_weichat)
    TextView tvEditinfoWeichat;
    @BindView(R.id.rll_editinfo_weichat)
    RelativeLayout rllEditinfoWeichat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_userinfo_edit);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,"编辑信息");
    }

    @OnClick({R.id.iv_editinfo_pic1, R.id.iv_editinfo_pic2, R.id.iv_editinfo_pic3, R.id.iv_editinfo_pic4, R.id.iv_editinfo_pic5, R.id.iv_editinfo_video, R.id.rll_editinfo_sign, R.id.rll_editinfo_name, R.id.rll_editinfo_gender, R.id.rll_editinfo_address, R.id.rll_editinfo_birth, R.id.rll_editinfo_tab, R.id.rll_editinfo_weichat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_editinfo_pic1:
                break;
            case R.id.iv_editinfo_pic2:
                break;
            case R.id.iv_editinfo_pic3:
                break;
            case R.id.iv_editinfo_pic4:
                break;
            case R.id.iv_editinfo_pic5:
                break;
            case R.id.iv_editinfo_video:
                break;
            case R.id.rll_editinfo_sign:
                break;
            case R.id.rll_editinfo_name:
                break;
            case R.id.rll_editinfo_gender:
                break;
            case R.id.rll_editinfo_address:
                break;
            case R.id.rll_editinfo_birth:
                break;
            case R.id.rll_editinfo_tab:
                break;
            case R.id.rll_editinfo_weichat:
                break;
        }
    }
}
