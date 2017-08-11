package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.vod.upload.common.utils.StringUtil;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.GetPhoneMsgEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.util.CountryUtil;
import com.fubang.video.util.ToastUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.white.countdownbutton.CountDownButton;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    private Context context;
    private String quhao = "86";

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


    List<String> list_country = new ArrayList<>();

    private void initview() {
        setText(tvTitle, R.string.login_phone);
        for (int i = 0; i < CountryUtil.getGifts().size(); i++) {
            list_country.add(CountryUtil.getGifts().get(i).getTotle_content());
        }
        spinner.setItems(list_country);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                quhao = CountryUtil.getGifts().get(position).getQuhao();
                view.setText(CountryUtil.getGifts().get(position).getTotle_content());
            }
        });
    }

    private String phone;

    @OnClick({R.id.btn_login_sign_in, R.id.cdb_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                String keycode = keywordView.getText().toString().trim();
                if (StringUtil.isEmpty(keycode)) {
                    ToastUtil.show(context, context.getString(R.string.please_input_yanzheng));
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
                                    ToastUtil.show(context, context.getString(R.string.yanzheng_not_right));
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
                    ToastUtil.show(context, context.getString(R.string.phone_not_right));
                    return;
                }
                phone = quhao + phone;
                KLog.e(phone);
                OkGo.<GetPhoneMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_SEND_MSG)
                        .tag(this)
                        .params("ctel", phone)
                        .execute(new JsonCallBack<GetPhoneMsgEntity>(GetPhoneMsgEntity.class) {
                            @Override
                            public void onSuccess(Response<GetPhoneMsgEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    ToastUtil.show(context, R.string.send_message_success);
                                } else {
                                    ToastUtil.show(context, response.body().getInfo().getResponseData().getDetail());
                                }
                            }

                            @Override
                            public void onError(Response<GetPhoneMsgEntity> response) {
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
