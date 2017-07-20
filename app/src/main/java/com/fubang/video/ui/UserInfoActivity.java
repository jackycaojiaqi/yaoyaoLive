package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.util.GlideImageLoader;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
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
    private Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initview();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initdate();
    }

    private void initview() {
        back(ivUserinfoBack);
        type = getIntent().getIntExtra(AppConstant.TYPE, 0);
        if (type == 1) {
            btnUserinfoAction.setText("编辑信息");
        } else if (type == 0) {
            btnUserinfoAction.setText("视频通话");
        }
        banner = (Banner) findViewById(R.id.banner_userinfo);

    }

    private String picwall_name;

    private void initdate() {
        OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<BaseInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            tvUserinfoId.setText("ID:" + VMSPUtil.get(context, AppConstant.USERID, ""));//ID
                            tvUserinfoName.setText(response.body().getInfo().getCalias() + "");//姓名
                            imags.clear();
                            imags.add(AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto());
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphotowall())) {
                                picwall_name = response.body().getInfo().getCphotowall();
                                if (picwall_name.contains(";")) {//显示照片墙 并吧string数组赋值成list方便替换和增加
                                    String[] imagwall = picwall_name.split(";");
                                    for (int i = 0; i < imagwall.length; i++) {
                                        imags.add(AppConstant.BASE_IMG_URL+imagwall[i]);
                                    }
                                }
                            }
                            banner.setImages(imags).setImageLoader(new GlideImageLoader()).start();//头像照片墙
                            if (response.body().getInfo().getNgender().equals("0")) {//性别
                                ivUserinfoGender.setImageResource(R.drawable.ic_register_female_checked);
                            } else if (response.body().getInfo().getNgender().equals("1")) {
                                ivUserinfoGender.setImageResource(R.drawable.ic_register_male_checked);
                            }
                            tvUserinfoAddress.setText(response.body().getInfo().getCcity() + " ");//定位地址
                            tvUserinfoAge.setText(response.body().getInfo().getNage() + " ");//年龄
                            if (response.body().getInfo().getNstatus().equals("0")) {//状态
                                ivUserinfoState.setImageResource(R.drawable.ic_userinfo_leisure);
                            } else if (response.body().getInfo().getNstatus().equals("1")) {//繁忙
                                ivUserinfoState.setImageResource(R.drawable.ic_userinfo_busy);
                            } else if (response.body().getInfo().getNstatus().equals("2")) {//勿扰

                            } else if (response.body().getInfo().getNstatus().equals("10")) {//冻结

                            }
                            tvUserinfoPrice.setText(response.body().getInfo().getNprice() + "金币/分钟");//每分钟价格
                            ImagUtil.set(context, AppConstant.BASE_IMG_URL + response.body().getInfo().getCvideophoto(), ivUserinfoSelfVideo);//个人介绍视频图片
                            tvUserinfoSelfSign.setText(response.body().getInfo().getCidiograph() + " ");
                        } else {
                            startActivity(new Intent(context, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onError(Response<BaseInfoEntity> response) {
                        super.onError(response);
                    }
                });
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
                    startActivity(new Intent(context, UserinfoEditActivity.class));
                } else if (type == 0) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("userId", "15867083398");
                    startActivity(intent);
                }
                break;
        }
    }
}
