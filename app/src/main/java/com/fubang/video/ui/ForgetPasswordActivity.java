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
import com.fubang.video.entity.ForgetPassEntity;
import com.fubang.video.entity.LoginEntity;
import com.fubang.video.entity.SendMsgEntity;
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
 * Created by jacky on 2017/7/29.
 */
public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_change_passowrd_new)
    ClearableEditText etChangePassowrdNew;
    @BindView(R.id.et_change_passowrd_new_sure)
    ClearableEditText etChangePassowrdNewSure;
    @BindView(R.id.btn_login_sign_in)
    Button btnLoginSignIn;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra(AppConstant.PHONE);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, R.string.change_pwd);
    }

    @OnClick({R.id.btn_login_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sign_in:
                final String et_pass = etChangePassowrdNew.getText().toString().trim();
                final String et_pass_sure = etChangePassowrdNewSure.getText().toString().trim();
                if (StringUtil.isEmptyandnull(et_pass) || StringUtil.isEmptyandnull(et_pass_sure)) {
                    ToastUtil.show(context, context.getString(R.string.pwd_not_null));
                    return;
                }
                if (!et_pass.equals(et_pass_sure)) {
                    ToastUtil.show(context, context.getString(R.string.pwd_twice_not_same));
                    return;
                }
                OkGo.<LoginEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_PASSWORD)
                        .tag(this)
                        .params("ctel", phone)
                        .params("cpassword", et_pass_sure)
                        .execute(new JsonCallBack<LoginEntity>(LoginEntity.class) {
                            @Override
                            public void onSuccess(Response<LoginEntity> response) {
                                if (response.body().getStatus().equals("success")) {//不存在这个手机号码
                                    VMSPUtil.put(context, AppConstant.TOKEN, response.body().getInfo().getCtoken());
                                    VMSPUtil.put(context, AppConstant.USERID, response.body().getInfo().getNuserid());
                                    VMSPUtil.put(context, AppConstant.GENDER, response.body().getInfo().getNgender());
                                    VMSPUtil.put(context, AppConstant.USERNAME, response.body().getInfo().getCalias());
                                    VMSPUtil.put(context, AppConstant.USERPIC, AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto());
                                    VMSPUtil.put(context, AppConstant.PASSWORD, et_pass_sure);
                                    VMSPUtil.put(context, AppConstant.PHONE, phone);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    ToastUtil.show(context,context.getString(R.string.fail));
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
}
