package com.fubang.video.callback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.util.DialogFactory;
import com.fubang.video.util.ScreenUtils;
import com.lzy.okgo.callback.StringCallback;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public abstract class StringDialogCallback extends StringCallback {

    private static Dialog mDialog = null;
    private Context context;

    public StringDialogCallback(Activity activity) {
        context = activity;
    }

}
