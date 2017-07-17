package com.fubang.video.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.ui.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jacky on 2017/7/11.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    Unbinder unbinder;
    @BindView(R.id.iv_mine_pic)
    CircleImageView ivMinePic;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_gender)
    ImageView ivMineGender;
    @BindView(R.id.tv_mine_id)
    TextView tvMineId;
    @BindView(R.id.rll_mine_self)
    RelativeLayout rllMineSelf;
    @BindView(R.id.tv_mine_nk_num)
    TextView tvMineNkNum;
    @BindView(R.id.rll_mine_nk)
    RelativeLayout rllMineNk;
    @BindView(R.id.rll_mine_circle)
    RelativeLayout rllMineCircle;
    @BindView(R.id.rll_mine_vip)
    RelativeLayout rllMineVip;
    @BindView(R.id.rll_mine_feedback)
    RelativeLayout rllMineFeedback;
    @BindView(R.id.rll_mine_setting)
    RelativeLayout rllMineSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setText(tvTitle, "我的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rll_mine_self, R.id.rll_mine_nk, R.id.rll_mine_circle, R.id.rll_mine_vip, R.id.rll_mine_feedback, R.id.rll_mine_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rll_mine_self:
                break;
            case R.id.rll_mine_nk:
                break;
            case R.id.rll_mine_circle:
                break;
            case R.id.rll_mine_vip:
                break;
            case R.id.rll_mine_feedback:
                break;
            case R.id.rll_mine_setting:
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
        }
    }
}
