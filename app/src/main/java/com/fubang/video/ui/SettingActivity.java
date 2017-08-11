package com.fubang.video.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fubang.video.APP;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.DownloadAppUtils;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/17.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;
    @BindView(R.id.rll_setting_version_update)
    RelativeLayout rllSettingVersionUpdate;
    @BindView(R.id.rll_setting_role)
    RelativeLayout rllSettingRole;
    @BindView(R.id.rll_setting_about)
    RelativeLayout rllSettingAbout;
    @BindView(R.id.rll_setting_quit)
    RelativeLayout rllSettingQuit;
    private PackageManager manager;
    private PackageInfo info = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,R.string.setting_center );
    }


    @OnClick({R.id.rll_setting_version_update, R.id.rll_setting_role, R.id.rll_setting_about, R.id.rll_setting_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rll_setting_version_update:
                //======================版本更新操作
                try {
                    manager = this.getPackageManager();
                    try {
                        info = manager.getPackageInfo(this.getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    String versionName = info.versionName;
                    KLog.e("me:" + versionName + " server:" + VMSPUtil.get(context, AppConstant.VERSION, ""));
                    if (!versionName.equals(VMSPUtil.get(context, AppConstant.VERSION, ""))) {//当前版本号名称和服务器版本号不一致
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                                .title(R.string.has_new_apk)
                                .content(R.string.has_new_download_or_not)
                                .positiveText(R.string.download)
                                .negativeText(R.string.cancel)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (Build.VERSION.SDK_INT < 23) {
                                            DownloadAppUtils.downloadForAutoInstall(context, AppConstant.DOWNLOAD_URL, "yaoyao", "downloading");
                                        } else {
                                            String url = AppConstant.DOWNLOAD_URL;
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(url));
                                            context.startActivity(intent);
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                });
                        MaterialDialog dialog = builder.build();
                        dialog.show();
                    } else {
                        ToastUtil.show(context, getString(R.string.almost_new_version));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rll_setting_role:
                break;
            case R.id.rll_setting_about:
                break;
            case R.id.rll_setting_quit:
                signOut();
                VMSPUtil.clear(context);//清除用户信息缓存
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 环信退出登录
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
