package com.fubang.video.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fubang.video.AppConstant;
import com.fubang.video.Constant;
import com.fubang.video.DemoHelper;
import com.fubang.video.R;
import com.fubang.video.adapter.FragmentTabAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.receive.NetworkConnectChangedReceiver;
import com.fubang.video.ui.fragment.CircleFragment;
import com.fubang.video.ui.fragment.FindFragment;
import com.fubang.video.ui.fragment.HomeFragment;
import com.fubang.video.ui.fragment.MessageFragment;
import com.fubang.video.ui.fragment.MineFragment;
import com.fubang.video.util.DownloadAppUtils;
import com.fubang.video.util.LocationUtil;
import com.fubang.video.util.StartUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.room.RoomMain;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.iv_main_home_page)
    ImageView ivMainHomePage;
    @BindView(R.id.tv_main_home_page)
    TextView tvMainHomePage;
    @BindView(R.id.ll_main_home_page)
    LinearLayout llMainHomePage;
    @BindView(R.id.iv_main_circle_page)
    ImageView ivMainCirclePage;
    @BindView(R.id.tv_main_circle_page)
    TextView tvMainCirclePage;
    @BindView(R.id.ll_main_circle_page)
    LinearLayout llMainCirclePage;
    @BindView(R.id.iv_main_home_message)
    ImageView ivMainHomeMessage;
    @BindView(R.id.tv_main_home_message)
    TextView tvMainHomeMessage;
    @BindView(R.id.ll_main_home_message)
    LinearLayout llMainHomeMessage;
    @BindView(R.id.iv_main_home_find)
    ImageView ivMainHomeFind;
    @BindView(R.id.tv_main_home_find)
    TextView tvMainHomeFind;
    @BindView(R.id.ll_main_home_find)
    LinearLayout llMainHomeFind;
    @BindView(R.id.iv_main_home_mine)
    ImageView ivMainHomeMine;
    @BindView(R.id.tv_main_home_mine)
    TextView tvMainHomeMine;
    @BindView(R.id.ll_main_home_mine)
    LinearLayout llMainHomeMine;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction ft;
    private FragmentTabAdapter tabAdapter;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private Context context;
    private String phone;
    private String password;
    public static RoomMain roomMain = new RoomMain();
    public static boolean is_video_call = false;
    public static boolean is_male_pick_up_auto = false;
    private PackageManager manager;
    private PackageInfo info = null;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
        quitRoom();
        EventBus.getDefault().unregister(this);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        EventBus.getDefault().register(this);
        initview();
        joinRoom();
        //注册环信消息监听回调
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        //环信登录
        loginHX();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }

    /**
     * 网络变化，重新加入房间
     */
    @Subscriber(tag = "reconncet")
    public void reconncet(String msg) {
        joinRoom();
    }

    /**
     * 网络变化，断开连接加
     */
    @Subscriber(tag = "disconncet")
    public void disconncet(String msg) {
        roomMain.getRoom().getChannel().SendKickOut();
        roomMain.getRoom().getChannel().Close();
    }

    /**
     * 用户被踢出回调
     *
     * @param msg
     */
    @Subscriber(tag = "onKickOut")
    public void onKickOut(KickoutUserInfo msg) {
        if (!roomMain.getRoom().isOK()) {
            joinRoom();
        }
        if (msg.getReasonid() == 101) {
            KLog.e("重复登录被请出");
        } else if (msg.getReasonid() == 102) {
            KLog.e("提出超时");
        } else if (msg.getReasonid() == 103) {
            KLog.e("自己离开房间");
        } else if (msg.getReasonid() == 104) {
            KLog.e("对方已经离开");
        }
    }

    /**
     * 登录回调
     *
     * @param msg
     */
    @Subscriber(tag = "chat_login_msg")
    public void chat_login_msg(LogonResponse msg) {
        if (msg.getErrorid() == 0) {
            KLog.e("登陆成功");
            VMSPUtil.put(context, AppConstant.NKNUM, msg.getKmoney()); //每次登录存一遍金币
            VMSPUtil.put(context, AppConstant.VERSION, msg.getVerison()); //每次登录存一遍金币
            //======================版本更新操作
            try {
                manager = this.getPackageManager();
                try {
                    info = manager.getPackageInfo(this.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String versionName = info.versionName;
                KLog.e("me:" + versionName + " server:" + msg.getVerison());
                if (!versionName.equals(msg.getVerison())) {//当前版本号名称和服务器版本号不一致
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                            .title(R.string.has_new_apk)
                            .content(R.string.has_new_download_or_not)
                            .positiveText(R.string.download)
                            .negativeText(R.string.cancle)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (Build.VERSION.SDK_INT < 23) {
                                        KLog.e("<23");
                                        DownloadAppUtils.downloadForAutoInstall(context, AppConstant.DOWNLOAD_URL, "yaoyao", "downloading");
                                    } else {
                                        KLog.e(">=23");
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (msg.getErrorid() == 404) {
            KLog.e("数据库操作失败");
        } else if (msg.getErrorid() == 405) {
            KLog.e("用户名或密码错误");
        }
    }

    private void joinRoom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(Integer.parseInt((String) VMSPUtil.get(getApplicationContext(), AppConstant.USERID, "0")),
                        StringUtil.getMD5((String) VMSPUtil.get(getApplicationContext(), AppConstant.PASSWORD, "0")), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_CONNECT_PORT);
//                roomMain.Start(13, StringUtil.encodeMD5("123456"), 12, "42.121.57.170", 11444);
            }
        }).start();
    }

    private void quitRoom() {
        if (roomMain.getRoom().isOK()) {
            roomMain.getRoom().getChannel().SendKickOut();
            roomMain.getRoom().getChannel().Close();
        }
    }

    private void loginHX() {
        phone = String.valueOf(VMSPUtil.get(context, AppConstant.PHONE, ""));
        password = String.valueOf(VMSPUtil.get(context, AppConstant.PASSWORD, ""));
        KLog.e(phone + " " + password);
        if (phone.isEmpty() || password.isEmpty()) {
            ToastUtil.show(context, "username or password null");
            return;
        }
        EMClient.getInstance().login(phone, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(String.valueOf(VMSPUtil.get(context, AppConstant.USERNAME, "")));
                DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(String.valueOf(VMSPUtil.get(context, AppConstant.USERPIC, "")));
//                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName("小新");
//                DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar("https://imgsa.baidu.com/news/q%3D100/sign=0e6bf447c9cec3fd8d3ea375e689d4b6/0b55b319ebc4b745f4d0be9dc5fc1e178b8215f0.jpg");
                DemoHelper.getInstance().getUserProfileManager().getCurrentUserInfo().setUserid((String) VMSPUtil.get(context, AppConstant.USERID, ""));
                DemoHelper.getInstance().setCurrentUserName(String.valueOf(VMSPUtil.get(context, AppConstant.PHONE, "")));
                KLog.e(String.valueOf(VMSPUtil.get(context, AppConstant.USERNAME, "")));
                KLog.e(String.valueOf(VMSPUtil.get(context, AppConstant.USERPIC, "")));
                KLog.e(String.valueOf(VMSPUtil.get(context, AppConstant.PHONE, "")));
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        KLog.e("登录环信成功");
                    }
                });
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

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            if (messages.size() > 0) {
                for (EMMessage message : messages) {//分发消息
                    DemoHelper.getInstance().getNotifier().onNewMsg(message);
                    KLog.e(message.toString());
                    EventBus.getDefault().post("refresh_content_list", "refresh_content_list");//去更新消息联系人列表
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            if (messages.size() > 0) {
                for (EMMessage message : messages) {
                    KLog.e(message.toString());
                }
            }
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            if (messages.size() > 0) {
                for (EMMessage message : messages) {
                    KLog.e(message.toString());
                }
            }
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    //定位权限请求成功
    @PermissionSuccess(requestCode = 200)
    public void doSomething() {
        initlocation();
    }

    private void initview() {
        //获取权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(MainActivity.this)
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
            initlocation();
        }
        fragments.add(0, new HomeFragment());
        fragments.add(1, new CircleFragment());
        fragments.add(2, new MessageFragment());
        fragments.add(3, new FindFragment());
        fragments.add(4, new MineFragment());
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.content, null);
        //默认显示首页
        toSelectFm(0);
    }

    /**
     * 跳转到对应的fragment页面
     *
     * @param i
     */
    private void toSelectFm(int i) {
        Fragment fragment = fragments.get(i);
        ft = tabAdapter.obtainFragmentTransaction(i);
        tabAdapter.getCurrentFragment().onPause(); // 暂停当前tab

        if (fragment.isAdded()) {
            fragment.onResume(); // 启动目标tab的onResume()
        } else {
            ft.add(R.id.content, fragment);
        }
        tabAdapter.showTab(i); // 显示目标tab
        ft.commitAllowingStateLoss();
    }

    private void initlocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @OnClick({R.id.ll_main_home_page, R.id.ll_main_circle_page, R.id.ll_main_home_message, R.id.ll_main_home_find
            , R.id.ll_main_home_mine})
    void clicks(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home_page:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home_select);
                ivMainCirclePage.setImageResource(R.drawable.ic_main_circle);
                ivMainHomeMessage.setImageResource(R.drawable.ic_main_message);
                ivMainHomeFind.setImageResource(R.drawable.ic_main_find);
                ivMainHomeMine.setImageResource(R.drawable.ic_main_mine);
                toSelectFm(0);
                break;
            case R.id.ll_main_circle_page:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home);
                ivMainCirclePage.setImageResource(R.drawable.ic_main_cicle_select);
                ivMainHomeMessage.setImageResource(R.drawable.ic_main_message);
                ivMainHomeFind.setImageResource(R.drawable.ic_main_find);
                ivMainHomeMine.setImageResource(R.drawable.ic_main_mine);
                toSelectFm(1);
                break;
            case R.id.ll_main_home_message:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home);
                ivMainCirclePage.setImageResource(R.drawable.ic_main_circle);
                ivMainHomeMessage.setImageResource(R.drawable.ic_main_messgae_selected);
                ivMainHomeFind.setImageResource(R.drawable.ic_main_find);
                ivMainHomeMine.setImageResource(R.drawable.ic_main_mine);
                toSelectFm(2);
                break;
            case R.id.ll_main_home_find:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home);
                ivMainCirclePage.setImageResource(R.drawable.ic_main_circle);
                ivMainHomeMessage.setImageResource(R.drawable.ic_main_message);
                ivMainHomeFind.setImageResource(R.drawable.ic_main_find_selcet);
                ivMainHomeMine.setImageResource(R.drawable.ic_main_mine);
                toSelectFm(3);
                break;
            case R.id.ll_main_home_mine:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home);
                ivMainCirclePage.setImageResource(R.drawable.ic_main_circle);
                ivMainHomeMessage.setImageResource(R.drawable.ic_main_message);
                ivMainHomeFind.setImageResource(R.drawable.ic_main_find);
                ivMainHomeMine.setImageResource(R.drawable.ic_main_mine_select);
                toSelectFm(4);
                break;
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, R.string.enter_twice_quite, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (StringUtil.isEmptyandnull(StartUtil.getCity(context))) {//为空表示用户没有手动设置区域，则定位填充数据，如果有数据，则不填充
                StartUtil.putCity(context, aMapLocation.getCity());
            }
            VMSPUtil.put(context, AppConstant.CITY, aMapLocation.getCity());
            VMSPUtil.put(context, AppConstant.PRIVINCE, aMapLocation.getProvince());
            VMSPUtil.put(context, AppConstant.ADDRDETAIL, aMapLocation.getAddress());
            VMSPUtil.put(context, AppConstant.LAT, aMapLocation.getLatitude());
            VMSPUtil.put(context, AppConstant.LON, aMapLocation.getLongitude());
            update_et_to_server("");
        }
    }

    /**
     * 更新用户扩展
     */
    private void update_et_to_server(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("ccity", String.valueOf(VMSPUtil.get(context, AppConstant.CITY, "")));
        map.put("clocation", String.valueOf(VMSPUtil.get(context, AppConstant.ADDRDETAIL, "")));
        map.put("nlongitude", String.valueOf(VMSPUtil.get(context, AppConstant.LON, "")));
        map.put("nlatitude", String.valueOf(VMSPUtil.get(context, AppConstant.LAT, "")));

        OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_EXTINFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params(map)
                .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                    @Override
                    public void onSuccess(Response<PublishUpLoadEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            KLog.e("上传位置信息成功");
                        }
                    }

                    @Override
                    public void onError(Response<PublishUpLoadEntity> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


}
