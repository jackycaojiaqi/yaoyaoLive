package com.xlg.android.room;


import com.fubang.video.util.ToastUtil;
import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.socks.library.KLog;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.VideoConnectRequest;
import com.xlg.android.protocol.VideoDisConnectRequest;

import org.simple.eventbus.EventBus;


public class MyRoom implements RoomHandler {
    private RoomChannel channel = new RoomChannel(this);
    private boolean isConnected = false;

    public String videoIP;
    public int videoPort;
    public int videoRand;
    public int videoUID;


    public final int FT_ROOMUSER_STATUS_PUBLIC_MIC = 0x1;

    public MyRoom() {

    }

    public RoomChannel getChannel() {
        return channel;
    }

    public boolean isOK() {
        return isConnected;
    }

    @Override
    public void onConnectSuccessed() {

        isConnected = true;
        // 连接成功
        KLog.e("onConnectSuccessed: 连接成功");
        // 加入房间
        channel.SendHello();
        channel.SendLoginRequest();
    }

    @Override
    public void onConnectFailed() {
        EventBus.getDefault().post(false, "ConnectFailed");
        KLog.e("onConnectFailed: 连接失败");
        isConnected = false;
    }


    @Override
    public void onDisconnected() {
        KLog.e("onConnectFailed: 断开连接");
        isConnected = false;
    }
    //登陆成功回调
    @Override
    public void onLoginRequest(LogonResponse obj) {
        EventBus.getDefault().post(obj, "chat_login_msg");
    }

    @Override
    public void onTradeGiftError(TradeGiftError obj) {
        EventBus.getDefault().post(obj, "onTradeGiftError");
    }


    @Override
    public void onTradeGiftNotify(TradeGiftNotify obj) {
        KLog.e("onTradeGiftNotify");
        EventBus.getDefault().post(obj, "onTradeGiftNotify");
    }

    @Override
    public void onUserPayResponse(UserPayResponse obj) {
        KLog.e("onUserPayResponse" + obj.getType());
        EventBus.getDefault().post(obj, "onUserPayResponse");
    }

    @Override
    public void onUserPayError(UserPayError obj) {
        KLog.e("UserPayError" + obj.getErrorid());
        EventBus.getDefault().post(obj, "onUserPayError");
    }


    @Override
    public void onKickOut(KickoutUserInfo obj) {
        KLog.e("onKickOut" + obj.getReasonid());
        EventBus.getDefault().post(obj, "onKickOut");
    }

    @Override
    public void VideoConnectResponse(VideoConnectRequest obj) {
        KLog.e("VideoConnectResponse" + obj.getUserid());
        EventBus.getDefault().post(obj, "VideoConnectResponse");
    }

    @Override
    public void VideoDisConnectResponse(VideoDisConnectRequest obj) {
        KLog.e("VideoDisConnectResponse" + obj.getUserid());
        EventBus.getDefault().post(obj, "VideoDisConnectResponse");
    }
}
