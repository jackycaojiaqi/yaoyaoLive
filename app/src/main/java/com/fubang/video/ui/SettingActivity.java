package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;

/**
 * Created by jacky on 2017/7/17.
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_setting);
    }
}
