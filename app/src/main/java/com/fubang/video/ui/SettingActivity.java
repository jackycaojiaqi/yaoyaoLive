package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/17.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;
    @BindView(R.id.rll_setting_version_update)
    RelativeLayout rllSettingVersionUpdate;
    @BindView(R.id.rll_setting_role)
    RelativeLayout rllSettingRole;
    @BindView(R.id.rll_setting_about)
    RelativeLayout rllSettingAbout;
    @BindView(R.id.rll_setting_quit)
    RelativeLayout rllSettingQuit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rll_setting_version_update, R.id.rll_setting_role, R.id.rll_setting_about, R.id.rll_setting_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rll_setting_version_update:
                break;
            case R.id.rll_setting_role:
                break;
            case R.id.rll_setting_about:
                break;
            case R.id.rll_setting_quit:
                signOut();
                VMSPUtil.clear(context);//清除用户信息缓存
                startActivity(new Intent(context,LoginActivity.class));
                break;
        }
    }

    /**
     * 环信退出登录
     */
    private void signOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                VMLog.i("logout success");
            }

            @Override
            public void onError(int i, String s) {
                final String str = "logout error: " + i + "; " + s;
                VMLog.i(str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(context, str);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
