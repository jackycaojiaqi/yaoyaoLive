package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.umeng.analytics.MobclickAgent;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.white.countdownbutton.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/14.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_username)
    EditText usernameView;
    @BindView(R.id.et_login_keyword)
    EditText keywordView;
    @BindView(R.id.btn_login_sign_in)
    Button btnLoginSignIn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cdb_time)
    CountDownButton cdbTime;
    private Context context;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        setText(tvTitle,"登录");
    }

    @OnClick({R.id.btn_login_sign_in, R.id.cdb_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                startActivity(new Intent(context,RegisterActivity.class));
                finish();
//                signIn();
                break;
            case R.id.cdb_time://获取验证码

                break;
        }
    }

    private String username;
    private String keyword;

    /**
     * 登录  环信登录统一到mainactivity进行登录
     */
    private void signIn() {
        username = usernameView.getText().toString().trim();
        keyword = "";//接口获取的密码进行
        if (username.isEmpty() || keyword.isEmpty()) {
            ToastUtil.show(context, "username or password null");
            return;
        }
       VMSPUtil.put(context,AppConstant.USERNAME,username);
    }



}
