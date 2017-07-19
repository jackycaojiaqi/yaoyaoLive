package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.LoginEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.ClearableEditText;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/19.
 */
public class LoginPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.et_login_passowrd)
    ClearableEditText etLoginPassowrd;
    @BindView(R.id.btn_login_sign_in)
    Button btnLoginSignIn;
    private String phone, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_loginpassowrd);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "登录");
        phone = getIntent().getStringExtra(AppConstant.USERID);
    }

    @OnClick({R.id.iv_back, R.id.btn_login_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                password = etLoginPassowrd.getText().toString().trim();
                if (StringUtil.isEmptyandnull(password)) {
                    ToastUtil.show(context, "密码不能为空");
                    return;
                }
                OkGo.<LoginEntity>post(AppConstant.BASE_URL + AppConstant.URL_LOGIN)
                        .tag(this)
                        .params("ctel", phone)
                        .params("password", password)
                        .execute(new JsonCallBack<LoginEntity>(LoginEntity.class) {
                            @Override
                            public void onSuccess(Response<LoginEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    VMSPUtil.put(context,AppConstant.USERID,response.body().getInfo().getNuserid());
                                    VMSPUtil.put(context,AppConstant.TOKEN,response.body().getInfo().getCtoken());
                                    loginHX();
                                } else {
                                    ToastUtil.show(context, "密码错误");
                                }
                            }

                            @Override
                            public void onError(Response<LoginEntity> response) {
                                super.onError(response);
                            }
                        });
                break;
        }
    }
    private void loginHX() {
        if (phone.isEmpty() || password.isEmpty()) {
            ToastUtil.show(context, "username or password null");
            return;
        }
        EMClient.getInstance().login(phone, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                VMLog.i("login success");
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                VMSPUtil.put(context, "username", phone);
                VMSPUtil.put(context, "password", password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        KLog.e("登录环信成功");
                    }
                });
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(final int i, final String s) {
                final String str = "login error: " + i + "; " + s;
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
