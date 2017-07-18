package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.ClearableEditText;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jacky on 2017/7/18.
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_register_pic)
    CircleImageView ivRegisterPic;
    @BindView(R.id.et_register_nickname)
    ClearableEditText etRegisterNickname;
    @BindView(R.id.cb_register_pick_male)
    ImageView cbRegisterPickMale;
    @BindView(R.id.iv_register_pick_male)
    ImageView ivRegisterPickMale;
    @BindView(R.id.ll_register_pick_male)
    LinearLayout llRegisterPickMale;
    @BindView(R.id.cb_register_pick_female)
    ImageView cbRegisterPickFemale;
    @BindView(R.id.iv_register_pick_female)
    ImageView ivRegisterPickFemale;
    @BindView(R.id.ll_register_pick_female)
    LinearLayout llRegisterPickFemale;
    @BindView(R.id.et_register_password)
    ClearableEditText etRegisterPassword;
    @BindView(R.id.btn_register_submit)
    Button btnRegisterSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_register_info);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "注册");
    }

    @OnClick({R.id.ll_register_pick_male, R.id.ll_register_pick_female, R.id.btn_register_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_register_pick_male:
                break;
            case R.id.ll_register_pick_female:
                break;
            case R.id.btn_register_submit:
                VMSPUtil.put(context, AppConstant.TOKEN, "");
                VMSPUtil.put(context, AppConstant.PASSWORD, "");
                VMSPUtil.put(context, AppConstant.USERID, "");
//                signUpServer();//自己服务器注册
                startActivity(new Intent(context, MainActivity.class));
                finish();
                break;
        }
    }


    private String username, password;


    /**
     * 自己服务器注册
     */
    private void signUpServer() {
        signUp();//环信注册
    }

    /**
     * 环信注册账户
     */
    private void signUp() {
        if (StringUtil.isEmptyandnull(username) || StringUtil.isEmptyandnull(password)) {
            ToastUtil.show(context, "帐号密码不能为空");
            return;
        }
        username = (String) VMSPUtil.get(context, AppConstant.USERNAME, "");
        password = etRegisterPassword.getText().toString().trim();
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
}
