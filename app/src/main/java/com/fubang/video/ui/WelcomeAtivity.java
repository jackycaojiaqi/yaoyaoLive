package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.entity.LoginEntity;
import com.fubang.video.util.StringUtil;
import com.squareup.haha.perflib.Main;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

/**
 * Created by jacky on 2017/7/17.
 */
public class WelcomeAtivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //结束你的activity
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!StringUtil.isEmptyandnull(String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))) {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
//                    Intent intent = new Intent(context,ForgetPasswordActivity.class);
//                    intent.putExtra(AppConstant.PHONE,"15867083390");
//                    startActivity(intent);
//                    finish();
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }
            }
        }, 2000);
    }
}
