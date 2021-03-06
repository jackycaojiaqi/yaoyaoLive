package com.vmloft.develop.app.demo.call;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.APP;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.GiftItemAdapter;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.db.UserDao;
import com.fubang.video.entity.GiftEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.ui.LoginPasswordActivity;
import com.fubang.video.ui.MainActivity;
import com.fubang.video.ui.RechargeActivity;
import com.fubang.video.ui.RegisterActivity;
import com.fubang.video.util.GiftUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.callback.SelfMessageCallBack;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMCallSurfaceView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.socks.library.KLog;
import com.superrtc.sdk.VideoView;
import com.vmloft.develop.library.tools.utils.VMDimenUtil;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.xlg.android.protocol.VideoConnectRequest;
import com.xlg.android.protocol.VideoDisConnectRequest;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fubang.video.APP.is_FloatWindow;
import static com.fubang.video.ui.MainActivity.roomMain;

/**
 * Created by lzan13 on 2016/10/18.
 * 视频通话界面处理
 */
public class VideoCallActivity extends CallActivity {

    // 视频通话帮助类
    private EMCallManager.EMVideoCallHelper videoCallHelper;
    // SurfaceView 控件状态，-1 表示通话未接通，0 表示本小远大，1 表示远小本大
    private int surfaceState = -1;

    private EMCallSurfaceView localSurface = null;
    private EMCallSurfaceView oppositeSurface = null;
    private RelativeLayout.LayoutParams localParams = null;
    private RelativeLayout.LayoutParams oppositeParams = null;

    // 使用 ButterKnife 注解的方式获取控件
    @BindView(R.id.layout_root)
    View rootView;
    @BindView(R.id.layout_call_control)
    View controlLayout;
    @BindView(R.id.layout_surface_container)
    RelativeLayout surfaceLayout;

    @BindView(R.id.btn_exit_full_screen)
    ImageButton exitFullScreenBtn;
    @BindView(R.id.text_call_state)
    TextView callStateView;
    @BindView(R.id.text_call_time)
    TextView callTimeView;
    @BindView(R.id.btn_mic_switch)
    ImageButton micSwitch;
    @BindView(R.id.btn_camera_switch)
    ImageButton cameraSwitch;
    @BindView(R.id.btn_speaker_switch)
    ImageButton speakerSwitch;
    @BindView(R.id.btn_record_switch)
    ImageButton recordSwitch;
    @BindView(R.id.btn_screenshot)
    ImageButton screenshotSwitch;
    @BindView(R.id.btn_change_camera_switch)
    ImageButton changeCameraSwitch;
    @BindView(R.id.fab_reject_call)
    FloatingActionButton rejectCallFab;
    @BindView(R.id.fab_end_call)
    FloatingActionButton endCallFab;
    @BindView(R.id.fab_answer_call)
    FloatingActionButton answerCallFab;
    @BindView(R.id.iv_video_call_gift)
    ImageView ivVideoCallGift;
    @BindView(R.id.iv_video_call_charge)
    ImageView ivVideoCallCharge;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Context context;
    private String toChatUserPhone, toChatUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        context = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        String is_timer = getIntent().getStringExtra(AppConstant.OBJECT);
        KLog.e(is_timer);
        if (StringUtil.isEmptyandnull(is_timer)) {
            timer.schedule(task, 1000, 1000);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        toChatUserPhone = getIntent().getStringExtra("to");
        if (toChatUserPhone.equals(VMSPUtil.get(context, AppConstant.PHONE, ""))) {
            toChatUserPhone = getIntent().getStringExtra("from");
        }
        KLog.e(toChatUserPhone);
        OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_CHECK_REG)
                .tag(this)
                .params("ctel", toChatUserPhone)
                .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                    @Override
                    public void onSuccess(Response<SendMsgEntity> response) {
                        if (response.body().getStatus().equals("fail")) {//不存在这个手机号码
                            toChatUserId = response.body().getInfo().getNuserid();
                        } else {//存在这个手机号码
                            ToastUtil.show(context, "对方帐号不存在");
                        }
                    }

                    @Override
                    public void onError(Response<SendMsgEntity> response) {
                        super.onError(response);
                    }
                });

//        if (toChatUserPhone != null) {
//            if (toChatUserPhone.length() >= 11) {
//                Log.e(TAG, toChatUserPhone);
//                UserDao dao = new UserDao(APP.getContext());
//                Map<String, EaseUser> users = dao.getContactList();
//                final EaseUser user = users.get(toChatUserPhone);
//                toChatUserId = user.getUserid();
//                KLog.e("toChatUserId:" + toChatUserId);
//                if (toChatUserPhone.equals(VMSPUtil.get(context, AppConstant.PHONE, ""))) {//获取视频两天两个对象环信id   主要是为了根据环信id查询出appid
//                    KLog.e(toChatUserPhone);
//                    toChatUserPhone = getIntent().getStringExtra("from");
//                } else {
//                    KLog.e(toChatUserPhone);
//                }
//            } else {
//                toChatUserId = getIntent().getStringExtra("to");
//            }
//        }
    }

    Timer timer = new Timer();
    private int recLen = 100;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen = recLen - 5;
                    progressBar.setProgress(recLen);
                    if (recLen < 0) {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        progressBar.setVisibility(View.GONE);
                        KLog.e("test_timer");
                        endCall();
                    }
                }
            });
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 开始视频连接的请求
     */
    @Subscriber(tag = "VideoConnectResponse")
    public void VideoConnectResponse(VideoConnectRequest msg) {
        KLog.e("开始视频连接的请求,后台开始计费");
    }

    /**
     * 断开视频连接的请求
     */
    @Subscriber(tag = "VideoDisConnectResponse")
    public void VideoDisConnectResponse(VideoDisConnectRequest msg) {
        KLog.e("对方已经离开,停止计费");
        endCall();
    }

    /**
     * 送礼物失败回调
     *
     * @param msg
     */
    @Subscriber(tag = "onTradeGiftError")
    public void onTradeGiftError(TradeGiftError msg) {
        if (msg.getErrorid() == 504) {
            ToastUtil.show(context, "用户金币不足");
        } else if (msg.getErrorid() == 501) {
            ToastUtil.show(context, "礼物未维护");
        } else if (msg.getErrorid() == 404) {
            ToastUtil.show(context, "数据库操作失败");
        }
    }

    /**
     * 送礼物成功
     */
    @Subscriber(tag = "onTradeGiftNotify")
    public void onTradeGiftNotify(TradeGiftNotify obj) {
        giftControl.loadGift(new GiftModel(String.valueOf(gift_id), "送出礼物：", 1,
                String.valueOf(obj.getGiftid()), String.valueOf(obj.getUserid()), obj.getAlias(),
                obj.getPhoto(), System.currentTimeMillis()));
    }

    /**
     * 重载父类方法,实现一些当前通话的操作，
     */
    @Override
    protected void initView() {
        super.initView();
        //礼物连击
        giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
        giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);
        if (CallManager.getInstance().isInComingCall()) {
            endCallFab.setVisibility(View.GONE);
            answerCallFab.setVisibility(View.VISIBLE);
            rejectCallFab.setVisibility(View.VISIBLE);
            callStateView.setText(R.string.call_connected_is_incoming);
        } else {
            endCallFab.setVisibility(View.VISIBLE);
            answerCallFab.setVisibility(View.GONE);
            rejectCallFab.setVisibility(View.GONE);
            callStateView.setText(R.string.call_connecting);
        }

        micSwitch.setActivated(!CallManager.getInstance().isOpenMic());
        cameraSwitch.setActivated(!CallManager.getInstance().isOpenCamera());
        speakerSwitch.setActivated(CallManager.getInstance().isOpenSpeaker());
        recordSwitch.setActivated(CallManager.getInstance().isOpenRecord());

        // 初始化视频通话帮助类
        videoCallHelper = EMClient.getInstance().callManager().getVideoCallHelper();

        // 初始化显示通话画面
        initCallSurface();
        // 判断当前通话时刚开始，还是从后台恢复已经存在的通话
        if (CallManager.getInstance().getCallState() == CallManager.CallState.ACCEPTED) {
            endCallFab.setVisibility(View.VISIBLE);
            answerCallFab.setVisibility(View.GONE);
            rejectCallFab.setVisibility(View.GONE);
            callStateView.setText(R.string.call_accepted);
            refreshCallTime();
            // 通话已接通，修改画面显示
            onCallSurface();
        }
        try {
            // 设置默认摄像头为前置
            EMClient.getInstance().callManager().setCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        CallManager.getInstance().setCallCameraDataProcessor();
    }

    /**
     * 界面控件点击监听器
     */
    @OnClick({
            R.id.layout_call_control, R.id.btn_exit_full_screen, R.id.btn_change_camera_switch, R.id.btn_mic_switch,
            R.id.btn_camera_switch, R.id.btn_speaker_switch, R.id.btn_record_switch, R.id.btn_screenshot, R.id.fab_reject_call,
            R.id.fab_end_call, R.id.fab_answer_call, R.id.iv_video_call_gift, R.id.iv_video_call_charge
    })
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_call_control:
                onControlLayout();
                break;
            case R.id.btn_exit_full_screen:
                // 最小化通话界面
                exitFullScreen();
                break;
            case R.id.btn_change_camera_switch:
                // 切换摄像头
                changeCamera();
                break;
            case R.id.btn_mic_switch:
                // 麦克风开关
                onMicrophone();
                break;
            case R.id.btn_camera_switch:
                // 摄像头开关
                onCamera();
                break;
            case R.id.btn_speaker_switch:
                // 扬声器开关
                onSpeaker();
                break;
            case R.id.btn_screenshot:
                // 保存通话截图
                onScreenShot();
                break;
            case R.id.btn_record_switch:
                // 录制开关
                onRecordCall();
                break;
            case R.id.fab_end_call:

                // 结束通话
                endCall();
                break;
            case R.id.fab_reject_call:
                updateSuccessRate(1);//更新接通率0-接受，1-拒绝
                // 拒绝接听通话
                break;
            case R.id.fab_answer_call:
                updateSuccessRate(0);//更新接通率0-接受，1-拒绝
                // 接听通话
                answerCall();
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                progressBar.setVisibility(View.GONE);
                break;
            case R.id.iv_video_call_gift:
                showPopupWindow(giftFrameLayout1);
                break;
            case R.id.iv_video_call_charge:
                exitFullScreen();
                startActivity(new Intent(context, RechargeActivity.class));
                break;
        }
    }

    private void updateSuccessRate(final int type) {
        OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_CESSESS_RATE)
                .tag(this)
                .params("nuserid", (String) VMSPUtil.get(context, AppConstant.USERID, ""))
                .params("ctoken", (String) VMSPUtil.get(context, AppConstant.TOKEN, ""))
                .params("type", type)
                .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                    @Override
                    public void onSuccess(Response<SendMsgEntity> response) {
                        if (response.body().getStatus().equals("success")) {//不存在这个手机号码
                            if (type == 1) {
                                rejectCall();//拒绝后 关闭
                            }
                        }
                    }

                    @Override
                    public void onError(Response<SendMsgEntity> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 控制界面的显示与隐藏
     */
    private void onControlLayout() {
        if (controlLayout.isShown()) {
            controlLayout.setVisibility(View.GONE);
        } else {
            controlLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 退出全屏通话界面
     */
    private void exitFullScreen() {
        is_FloatWindow = true;//开启悬浮窗时，设置true值
        CallManager.getInstance().addFloatWindow();
        // 结束当前界面
        onFinish();
    }

    /**
     * 切换摄像头
     */
    private void changeCamera() {
        // 根据切换摄像头开关是否被激活确定当前是前置还是后置摄像头
        try {
            if (EMClient.getInstance().callManager().getCameraFacing() == 1) {
                EMClient.getInstance().callManager().switchCamera();
                EMClient.getInstance().callManager().setCameraFacing(0);
                // 设置按钮图标
                changeCameraSwitch.setImageResource(R.drawable.ic_camera_rear_white_24dp);
            } else {
                EMClient.getInstance().callManager().switchCamera();
                EMClient.getInstance().callManager().setCameraFacing(1);
                // 设置按钮图标
                changeCameraSwitch.setImageResource(R.drawable.ic_camera_front_white_24dp);
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 麦克风开关，主要调用环信语音数据传输方法
     */
    private void onMicrophone() {
        try {
            // 根据麦克风开关是否被激活来进行判断麦克风状态，然后进行下一步操作
            if (micSwitch.isActivated()) {
                // 设置按钮状态
                micSwitch.setActivated(false);
                // 暂停语音数据的传输
                EMClient.getInstance().callManager().resumeVoiceTransfer();
                CallManager.getInstance().setOpenMic(true);
            } else {
                // 设置按钮状态
                micSwitch.setActivated(true);
                // 恢复语音数据的传输
                EMClient.getInstance().callManager().pauseVoiceTransfer();
                CallManager.getInstance().setOpenMic(false);
            }
        } catch (HyphenateException e) {
            VMLog.e("exception code: %d, %s", e.getErrorCode(), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 摄像头开关
     */
    private void onCamera() {
        try {
            // 根据摄像头开关按钮状态判断摄像头状态，然后进行下一步操作
            if (cameraSwitch.isActivated()) {
                // 设置按钮状态
                cameraSwitch.setActivated(false);
                // 暂停视频数据的传输
                EMClient.getInstance().callManager().resumeVideoTransfer();
                CallManager.getInstance().setOpenCamera(true);
            } else {
                // 设置按钮状态
                cameraSwitch.setActivated(true);
                // 恢复视频数据的传输
                EMClient.getInstance().callManager().pauseVideoTransfer();
                CallManager.getInstance().setOpenCamera(false);
            }
        } catch (HyphenateException e) {
            VMLog.e("exception code: %d, %s", e.getErrorCode(), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 扬声器开关
     */
    private void onSpeaker() {
        // 根据按钮状态决定打开还是关闭扬声器
        if (speakerSwitch.isActivated()) {
            // 设置按钮状态
            speakerSwitch.setActivated(false);
            CallManager.getInstance().closeSpeaker();
            CallManager.getInstance().setOpenSpeaker(false);
        } else {
            // 设置按钮状态
            speakerSwitch.setActivated(true);
            CallManager.getInstance().openSpeaker();
            CallManager.getInstance().setOpenSpeaker(true);
        }
    }

    /**
     * 录制视屏通话内容
     */
    private void onRecordCall() {
        // 根据开关状态决定是否开启录制
        if (recordSwitch.isActivated()) {
            // 设置按钮状态
            recordSwitch.setActivated(false);
            String path = videoCallHelper.stopVideoRecord();
            CallManager.getInstance().setOpenRecord(false);
            File file = new File(path);
            if (file.exists()) {
                Toast.makeText(activity, "录制视频成功 " + path, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "录制失败/(ㄒoㄒ)/~~", Toast.LENGTH_LONG).show();
            }
        } else {
            // 设置按钮状态
            recordSwitch.setActivated(true);
            // 先创建文件夹
            String dirPath = getExternalFilesDir("").getAbsolutePath() + "/videos";
            File dir = new File(dirPath);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            videoCallHelper.startVideoRecord(dirPath);
            VMLog.d("开始录制视频");
            Toast.makeText(activity, "开始录制", Toast.LENGTH_LONG).show();
            CallManager.getInstance().setOpenRecord(true);
        }
    }

    /**
     * 保存通话截图
     */
    private void onScreenShot() {
        String dirPath = getExternalFilesDir("").getAbsolutePath() + "/videos/";
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        String path = dirPath + "video_" + System.currentTimeMillis() + ".jpg";
        boolean result = videoCallHelper.takePicture(path);
        Toast.makeText(activity, "截图保存成功 " + path, Toast.LENGTH_LONG).show();
    }

    /**
     * 接听通话
     */
    @Override
    protected void answerCall() {
        super.answerCall();
        endCallFab.setVisibility(View.VISIBLE);
        rejectCallFab.setVisibility(View.GONE);
        answerCallFab.setVisibility(View.GONE);
    }

    /**
     * 初始化通话界面控件
     */
    private void initCallSurface() {
        // 初始化显示远端画面控件
        oppositeSurface = new EMCallSurfaceView(activity);
        oppositeParams = new RelativeLayout.LayoutParams(0, 0);
        oppositeParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        oppositeParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        oppositeSurface.setLayoutParams(oppositeParams);
        surfaceLayout.addView(oppositeSurface);

        // 初始化显示本地画面控件
        localSurface = new EMCallSurfaceView(activity);
        localParams = new RelativeLayout.LayoutParams(0, 0);
        localParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        localParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        localParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        localSurface.setLayoutParams(localParams);
        surfaceLayout.addView(localSurface);

        localSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlLayout();
            }
        });

        localSurface.setZOrderOnTop(false);
        localSurface.setZOrderMediaOverlay(true);

        // 设置本地和远端画面的显示方式，是填充，还是居中
        localSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        // 设置通话画面显示控件
        EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
    }

    /**
     * 接通通话，这个时候要做的只是改变本地画面 view 大小，不需要做其他操作
     */
    private void onCallSurface() {
        // 更新通话界面控件状态
        surfaceState = 0;
        int width = VMDimenUtil.dp2px(activity, 96);
        int height = VMDimenUtil.dp2px(activity, 128);
        int rightMargin = VMDimenUtil.dp2px(activity, 16);
        int topMargin = VMDimenUtil.dp2px(activity, 96);

        localParams = new RelativeLayout.LayoutParams(width, height);
        localParams.width = width;
        localParams.height = height;
        localParams.rightMargin = rightMargin;
        localParams.topMargin = topMargin;
        localParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        localSurface.setLayoutParams(localParams);

        localSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCallSurface();
            }
        });

        oppositeSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onControlLayout();
            }
        });
    }

    /**
     * 切换通话界面，这里就是交换本地和远端画面控件设置，以达到通话大小画面的切换
     */
    private void changeCallSurface() {
        if (surfaceState == 0) {
            surfaceState = 1;
            EMClient.getInstance().callManager().setSurfaceView(oppositeSurface, localSurface);
        } else {
            surfaceState = 0;
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(CallEvent event) {
        if (event.isState()) {
            refreshCallView(event);
        }
        if (event.isTime()) {
            // 不论什么情况都检查下当前时间
            refreshCallTime();
        }
    }

    /**
     * 刷新通话界面
     */
    private void refreshCallView(CallEvent event) {
        EMCallStateChangeListener.CallError callError = event.getCallError();
        EMCallStateChangeListener.CallState callState = event.getCallState();
        switch (callState) {
            case CONNECTING: // 正在呼叫对方，
                VMLog.i("正在呼叫对方" + callError);
                break;
            case CONNECTED: // 正在等待对方接受呼叫申请（对方申请与你进行通话）
                VMLog.i("正在连接" + callError);
                if (CallManager.getInstance().isInComingCall()) {
                    callStateView.setText(R.string.call_connected_is_incoming);
                } else {
                    callStateView.setText(R.string.call_connected);
                }
                if (MainActivity.is_male_pick_up_auto) {//如果男性和女主播通话链接成功则自动接起视屏聊天
                    answerCall();
                }
                MainActivity.is_male_pick_up_auto = false;//将自动接听状态重置
                break;
            case ACCEPTED: // 通话已接通
                VMLog.i("通话已接通");

                callStateView.setText(R.string.call_accepted);
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                MainActivity.is_video_call = true;
                roomMain.getRoom().getChannel().SendVideoConnect(Integer.parseInt(toChatUserId));
                progressBar.setVisibility(View.GONE);
                // 通话接通，更新界面 UI 显示
                onCallSurface();
                break;
            case DISCONNECTED: // 通话已中断
                VMLog.i("通话已结束" + callError);
                onFinish();
                break;
            case NETWORK_DISCONNECTED:
                Toast.makeText(activity, "对方网络断开", Toast.LENGTH_SHORT).show();
                VMLog.i("对方网络断开");
                break;
            case NETWORK_NORMAL:
                VMLog.i("网络正常");
                break;
            case NETWORK_UNSTABLE:
                if (callError == EMCallStateChangeListener.CallError.ERROR_NO_DATA) {
                    VMLog.i("没有通话数据" + callError);
                } else {
                    VMLog.i("网络不稳定" + callError);
                }
                break;
            case VIDEO_PAUSE:
                Toast.makeText(activity, "对方已暂停视频传输", Toast.LENGTH_SHORT).show();
                VMLog.i("对方已暂停视频传输");
                break;
            case VIDEO_RESUME:
                Toast.makeText(activity, "对方已恢复视频传输", Toast.LENGTH_SHORT).show();
                VMLog.i("对方已恢复视频传输");
                break;
            case VOICE_PAUSE:
                Toast.makeText(activity, "对方已暂停语音传输", Toast.LENGTH_SHORT).show();
                VMLog.i("对方已暂停语音传输");
                break;
            case VOICE_RESUME:
                Toast.makeText(activity, "对方已恢复语音传输", Toast.LENGTH_SHORT).show();
                VMLog.i("对方已恢复语音传输");
                break;
            default:
                break;
        }
    }

    private int gift_id = -1;
    private BaseQuickAdapter adapter_gift;
    private List<GiftEntity> list_gift = new ArrayList<>();
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
    private GiftControl giftControl;

    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_window_gift, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.transparent_50));
        RelativeLayout rll_gift_bg = (RelativeLayout) contentView.findViewById(R.id.rll_gift_bg);
        rll_gift_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView tv_send = (TextView) contentView.findViewById(R.id.tv_send_gift);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gift_id == -1) {
                    ToastUtil.show(context, "请先选择一种礼物");
                    return;
                }
                roomMain.getRoom().getChannel().SendGift(Integer.parseInt(toChatUserId), gift_id, 1,
                        (String) VMSPUtil.get(context, AppConstant.USERNAME, ""), (String) VMSPUtil.get(context, AppConstant.USERPIC, ""));
                gift_id = -1;
                popupWindow.dismiss();
            }
        });
        RecyclerView rv_gift = (RecyclerView) contentView.findViewById(R.id.rv_gift);
        list_gift = GiftUtil.getGifts();
        adapter_gift = new GiftItemAdapter(R.layout.pop_item_gift, list_gift);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_gift.setLayoutManager(linearLayoutManager);
        adapter_gift.setEnableLoadMore(true);
        adapter_gift.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gift_id = list_gift.get(position).getGiftId();
            }
        });
        adapter_gift.bindToRecyclerView(rv_gift);
        adapter_gift.setEmptyView(R.layout.empty_view);
        rv_gift.setAdapter(adapter_gift);
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 刷新通话时间显示
     */
    private void refreshCallTime() {
        int t = CallManager.getInstance().getCallTime();
        int h = t / 60 / 60;
        int m = t / 60 % 60;
        int s = t % 60 % 60;
        String time = "";
        if (h > 9) {
            time = "" + h;
        } else {
            time = "0" + h;
        }
        if (m > 9) {
            time += ":" + m;
        } else {
            time += ":0" + m;
        }
        if (s > 9) {
            time += ":" + s;
        } else {
            time += ":0" + s;
        }
        if (!callTimeView.isShown()) {
            callTimeView.setVisibility(View.VISIBLE);
        }
        callTimeView.setText(time);
    }

    /**
     * 屏幕方向改变回调方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onUserLeaveHint() {
//        super.onUserLeaveHint();
        exitFullScreen();
    }

    /**
     * 通话界面拦截 Back 按键，不能返回
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitFullScreen();
    }

    @Override
    protected void onFinish() {
        // release surface view
        if (localSurface != null) {
            if (localSurface.getRenderer() != null) {
                localSurface.getRenderer().dispose();
            }
            localSurface.release();
            localSurface = null;
        }
        if (oppositeSurface != null) {
            if (oppositeSurface.getRenderer() != null) {
                oppositeSurface.getRenderer().dispose();
            }
            oppositeSurface.release();
            oppositeSurface = null;
        }
        super.onFinish();
    }
}
