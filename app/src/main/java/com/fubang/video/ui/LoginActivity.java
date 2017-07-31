package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
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
        setText(tvTitle, "登录");
    }

    private String phone;

    @OnClick({R.id.btn_login_sign_in, R.id.cdb_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                String keycode = keywordView.getText().toString().trim();
                if (StringUtil.isEmpty(keycode)) {
                    ToastUtil.show(context, "请输入验证码");
                    return;
                }
                OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_CHECK_MSG)
                        .tag(this)
                        .params("msgcode", keycode)
                        .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                            @Override
                            public void onSuccess(Response<SendMsgEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_CHECK_REG)
                                            .tag(this)
                                            .params("ctel", phone)
                                            .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                                                @Override
                                                public void onSuccess(Response<SendMsgEntity> response) {
                                                    if (response.body().getStatus().equals("success")) {//不存在这个手机号码
                                                        Intent intent = new Intent(context, RegisterActivity.class);
                                                        intent.putExtra(AppConstant.USERID, phone);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {//存在这个手机号码
                                                        Intent intent = new Intent(context, LoginPasswordActivity.class);
                                                        intent.putExtra(AppConstant.USERID, phone);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onError(Response<SendMsgEntity> response) {
                                                    super.onError(response);
                                                }
                                            });
                                } else {
                                    ToastUtil.show(context,"验证码不正确");
                                }
                            }

                            @Override
                            public void onError(Response<SendMsgEntity> response) {
                                super.onError(response);
                            }
                        });
                break;
            case R.id.cdb_time://获取验证码
                phone = usernameView.getText().toString().trim();
                if (StringUtil.isEmpty(phone)) {
                    ToastUtil.show(context, "手机号格式不正确");
                    return;
                }
                if (phone.length() != 11) {
                    ToastUtil.show(context, "手机号格式不正确");
                    return;
                }
                OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_SEND_MSG)
                        .tag(this)
                        .params("ctel", phone)
                        .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                            @Override
                            public void onSuccess(Response<SendMsgEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    ToastUtil.show(context, "发送短信成功，请查收");
                                }else {
                                    ToastUtil.show(context, "接受短信失败");
                                }
                            }

                            @Override
                            public void onError(Response<SendMsgEntity> response) {
                                super.onError(response);
                            }
                        });
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
    }


}
