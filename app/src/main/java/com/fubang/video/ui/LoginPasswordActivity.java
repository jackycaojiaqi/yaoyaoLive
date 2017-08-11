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
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.ClearableEditText;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
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
    @BindView(R.id.btn_login_forget_password)
    Button btnLoginForgetPassword;
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
        setText(tvTitle, R.string.login_phone);
        phone = getIntent().getStringExtra(AppConstant.USERID);
    }

    @OnClick({R.id.iv_back, R.id.btn_login_sign_in, R.id.btn_login_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                password = etLoginPassowrd.getText().toString().trim();
                if (StringUtil.isEmptyandnull(password)) {
                    ToastUtil.show(context, R.string.pwd_not_null);
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
                                    VMSPUtil.put(context, AppConstant.USERID, response.body().getInfo().getNuserid());
                                    VMSPUtil.put(context, AppConstant.TOKEN, response.body().getInfo().getCtoken());
                                    VMSPUtil.put(context, AppConstant.GENDER, response.body().getInfo().getNgender());
                                    VMSPUtil.put(context, AppConstant.USERNAME, response.body().getInfo().getCalias());
                                    VMSPUtil.put(context, AppConstant.USERPIC, AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto());

                                    VMSPUtil.put(context, AppConstant.PHONE, phone);
                                    VMSPUtil.put(context, AppConstant.PASSWORD, password);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    ToastUtil.show(context,R.string.pwd_error );
                                }
                            }

                            @Override
                            public void onError(Response<LoginEntity> response) {
                                super.onError(response);
                            }
                        });
                break;
            case R.id.btn_login_forget_password:
                Intent intent = new Intent(context, ForgetPasswordActivity.class);
                intent.putExtra(AppConstant.PHONE, phone);
                startActivity(intent);
                break;
        }
    }


}
