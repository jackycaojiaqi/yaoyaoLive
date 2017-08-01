package com.xlg.android.room;


import com.fubang.video.util.ToastUtil;
import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.socks.library.KLog;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.TradeGiftNotify;

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

    @Override
    public void onLoginRequest(int i) {

        EventBus.getDefault().post(i, "chat_login_msg");
    }

    @Override
    public void onTradeGiftError(int i) {
        KLog.e("onTradeGiftError" + i);
        EventBus.getDefault().post(i, "onTradeGiftError");
    }

    @Override
    public void onTradeGiftNotify(TradeGiftNotify obj) {
        KLog.e("onTradeGiftNotify");
        EventBus.getDefault().post(obj, "onTradeGiftNotify");
    }

    @Override
    public void onUserPayResponse(int i) {
        KLog.e("onUserPayResponse" + i);
        EventBus.getDefault().post(i, "onUserPayResponse");
    }

    @Override
    public void onKickOut(KickoutUserInfo obj) {
        KLog.e("onKickOut" + obj.getReasonid());
        EventBus.getDefault().post(obj, "onKickOut");
    }
}
