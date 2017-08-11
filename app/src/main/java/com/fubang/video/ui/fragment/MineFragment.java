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

import com.bumptech.glide.Glide;
import com.fubang.video.APP;
import com.fubang.video.AppConstant;
import com.fubang.video.DemoHelper;
import com.fubang.video.R;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.entity.LoginEntity;
import com.fubang.video.ui.CircleListActivity;
import com.fubang.video.ui.FeedBackActivity;
import com.fubang.video.ui.LoginActivity;
import com.fubang.video.ui.RechargeActivity;
import com.fubang.video.ui.SettingActivity;
import com.fubang.video.ui.UserInfoActivity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

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
    private String user_id;

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
        setText(tvTitle, R.string.mine);
    }

    @Override
    public void onResume() {
        super.onResume();
        initdate();
    }

    private void initdate() {
        OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(getActivity(), AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(getActivity(), AppConstant.TOKEN, "")))
                .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<BaseInfoEntity> response) {
                        if (response.body() != null)
                            if (response.body().getStatus().equals("success")) {
                                user_id = response.body().getInfo().getNuserid();
                                tvMineId.setText("ID:" + VMSPUtil.get(getActivity(), AppConstant.USERID, ""));//ID
                                tvMineName.setText(response.body().getInfo().getCalias() + "");//姓名
                                tvMineNkNum.setText(getString(R.string.nk) + "*" + response.body().getInfo().getNmoney() + " ");//金币
                                ImagUtil.setnoerror(getActivity(), AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto(), ivMinePic);
                                //本地存头像地址
                                VMSPUtil.put(getActivity(), AppConstant.USERPIC, AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto());
                                //本地行呗
                                VMSPUtil.put(getActivity(), AppConstant.GENDER, response.body().getInfo().getNgender());
                                //本地存昵称
                                VMSPUtil.put(getActivity(), AppConstant.USERNAME, response.body().getInfo().getCalias());
                                //本地金币
                                VMSPUtil.put(getActivity(), AppConstant.NKNUM, response.body().getInfo().getNmoney());
                                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(String.valueOf(VMSPUtil.get(getActivity(), AppConstant.USERNAME, "")));
                                DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(String.valueOf(VMSPUtil.get(getActivity(), AppConstant.USERPIC, "")));
                                if (response.body().getInfo().getNgender().equals("0")) {//性别
                                    ivMineGender.setImageResource(R.drawable.ic_register_female_checked);
                                } else if (response.body().getInfo().getNgender().equals("1")) {
                                    ivMineGender.setImageResource(R.drawable.ic_register_male_checked);
                                }
                            } else {//token失效
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                    }

                    @Override
                    public void onError(Response<BaseInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rll_mine_self, R.id.rll_mine_nk, R.id.rll_mine_circle, R.id.rll_mine_vip, R.id.rll_mine_feedback, R.id.rll_mine_setting})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rll_mine_self:
                intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra(AppConstant.TYPE, 1);
                intent.putExtra(AppConstant.USERID, (String) VMSPUtil.get(getActivity(), AppConstant.USERID, ""));
                startActivity(intent);
                break;
            case R.id.rll_mine_nk:
                intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.rll_mine_circle:
                intent = new Intent(getActivity(), CircleListActivity.class);
                intent.putExtra(AppConstant.USERID, user_id);
                startActivity(intent);
                break;
            case R.id.rll_mine_vip:
                break;
            case R.id.rll_mine_feedback:
                intent = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.rll_mine_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
