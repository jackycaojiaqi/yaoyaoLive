package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.banner_userinfo)
    Banner bannerUserinfo;
    @BindView(R.id.tv_userinfo_id)
    TextView tvUserinfoId;
    @BindView(R.id.tv_userinfo_name)
    TextView tvUserinfoName;
    @BindView(R.id.iv_userinfo_gender)
    ImageView ivUserinfoGender;
    @BindView(R.id.tv_userinfo_age)
    TextView tvUserinfoAge;
    @BindView(R.id.tv_userinfo_address)
    TextView tvUserinfoAddress;
    @BindView(R.id.iv_userinfo_state)
    ImageView ivUserinfoState;
    @BindView(R.id.tv_userinfo_price)
    TextView tvUserinfoPrice;
    @BindView(R.id.tv_userinfo_pickup_rate)
    TextView tvUserinfoPickupRate;
    @BindView(R.id.tv_userinfo_complain_rate)
    TextView tvUserinfoComplainRate;
    @BindView(R.id.tv_userinfo_time_length)
    TextView tvUserinfoTimeLength;
    @BindView(R.id.iv_userinfo_self_video)
    ImageView ivUserinfoSelfVideo;
    @BindView(R.id.iv_userinfo_self_video_play)
    ImageView ivUserinfoSelfVideoPlay;
    @BindView(R.id.tv_userinfo_self_sign)
    TextView tvUserinfoSelfSign;
    @BindView(R.id.rv_userinfo)
    RecyclerView rvUserinfo;
    @BindView(R.id.iv_userinfo_back)
    ImageView ivUserinfoBack;
    @BindView(R.id.iv_userinfo_setting)
    ImageView ivUserinfoSetting;
    @BindView(R.id.btn_userinfo_action)
    Button btnUserinfoAction;
    private List<String> imags = new ArrayList<>();
    private int type = 0;// 0其他用户信息   1、自己的信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        type = getIntent().getIntExtra(AppConstant.TYPE, 0);
        if (type == 1) {
            btnUserinfoAction.setText("编辑信息");
        } else if (type == 0) {
            btnUserinfoAction.setText("视频通话");
        }
        Banner banner = (Banner) findViewById(R.id.banner_userinfo);
        banner.setImages(imags).setImageLoader(new GlideImageLoader()).start();
    }

    @OnClick({R.id.iv_userinfo_self_video_play, R.id.iv_userinfo_back, R.id.iv_userinfo_setting, R.id.btn_userinfo_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_userinfo_self_video_play:
                break;
            case R.id.iv_userinfo_back:
                break;
            case R.id.iv_userinfo_setting:
                break;
            case R.id.btn_userinfo_action:
                if (type == 1) {
                   startActivity(new Intent(context,UserinfoEditActivity.class));
                } else if (type == 0) {
                   //通话操作
                }
                break;
        }
    }
}
