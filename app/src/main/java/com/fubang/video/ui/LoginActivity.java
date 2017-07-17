package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    @BindView(R.id.et_login_password)
    EditText passwordView;
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
    }

    @OnClick({R.id.btn_login_sign_in, R.id.cdb_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                signIn();
                break;
            case R.id.cdb_time:

                break;
        }
    }

    private String username;
    private String password;

    /**
     * 登录
     */
    private void signIn() {
        username = "555";
        password = "555";
        if (username.isEmpty() || password.isEmpty()) {
            ToastUtil.show(context, "username or password null");
            return;
        }
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                VMLog.i("login success");
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                VMSPUtil.put(context, "username", username);
                VMSPUtil.put(context, "password", password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(context, "登录成功");
                    }
                });
                startActivity(new Intent(context, MainActivity.class));
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

    /**
     * 注册账户
     */
    private void signUp() {
        username = usernameView.getText().toString().trim();
        password = passwordView.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            ToastUtil.show(context, "帐号密码不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(context, "注册成功");
                        }
                    });
                } catch (HyphenateException e) {
                    final String str = "sign up error " + e.getErrorCode() + "; " + e.getMessage();
                    VMLog.d(str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(context, str);
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 退出登录
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
