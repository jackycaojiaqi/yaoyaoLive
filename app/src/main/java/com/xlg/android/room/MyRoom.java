package com.xlg.android.room;


import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.socks.library.KLog;

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
    }


    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLoginRequest(int i) {
        KLog.e(i);
    }
}