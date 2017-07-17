package com.fubang.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;

/**
 * Created by jacky on 2017/7/17.
 */
public class WelcomeAtivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
