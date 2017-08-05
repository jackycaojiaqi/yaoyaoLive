package com.xlg.android.room;


import com.socks.library.KLog;
import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.xlg.android.RoomPoolChannel;
import com.xlg.android.RoomPoolHandler;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.UserLinkInfo;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.VideoConnectRequest;
import com.xlg.android.protocol.VideoDisConnectRequest;

import org.simple.eventbus.EventBus;


public class MyPoolRoom implements RoomPoolHandler {
    private RoomPoolChannel channel = new RoomPoolChannel(this);
    private boolean isConnected = false;

    public String videoIP;
    public int videoPort;
    public int videoRand;
    public int videoUID;


    public final int FT_ROOMUSER_STATUS_PUBLIC_MIC = 0x1;

    public MyPoolRoom() {

    }

    public RoomPoolChannel getChannel() {
        return channel;
    }

    public boolean isOK() {
        return isConnected;
    }

    @Override
    public void onConnectSuccessed() {

        isConnected = true;
        // 连接成功
        KLog.e("onConnectSuccessed: pool连接成功");
        // 加入房间
        channel.SendHello();
        channel.SendLoginRequest();
    }

    @Override
    public void onConnectFailed() {
        EventBus.getDefault().post(false, "ConnectFailed");
        KLog.e("onConnectFailed: pool连接失败");
        isConnected = false;
    }


    @Override
    public void onDisconnected() {
        KLog.e("onConnectFailed: pool断开连接");
        isConnected = false;
    }

    //登陆成功回调
    @Override
    public void onLoginRequest(LogonResponse obj) {
        EventBus.getDefault().post(obj, "chat_login_pool_msg");
    }

    @Override
    public void onKickOut(KickoutUserInfo obj) {
        KLog.e("onKickOut" + obj.getReasonid());
        EventBus.getDefault().post(obj, "onKickOut_pool");
    }

    @Override
    public void onUserLinkRequest(UserLinkInfo obj) {
        KLog.e("onUserLinkRequest" + obj.getUserid() + " " + obj.getBuddyid() + " " + obj.getType());
        EventBus.getDefault().post(obj, "onUserLinkRequest");
    }

    @Override
    public void onUserLinkResponse(UserLinkInfo obj) {
        KLog.e("onUserLinkResponse" + obj.getUserid() + " " + obj.getBuddyid() + " " + obj.getType());
        EventBus.getDefault().post(obj, "onUserLinkResponse");
    }

    @Override
    public void onUserLinkNotify(UserLinkInfo obj) {
        KLog.e("onUserLinkNotify" + obj.getUserid() + " " + obj.getBuddyid() + " " + obj.getType());
        EventBus.getDefault().post(obj, "onUserLinkNotify");
    }
}
