package com.xlg.android;

import android.util.Log;

import com.fubang.video.util.StringUtil;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.Hello;
import com.xlg.android.protocol.KeepLiveRepuest;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonRequest;
import com.xlg.android.protocol.Message;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.TradeGiftRecord;
import com.xlg.android.protocol.UserPayRequest;
import com.xlg.android.room.RoomMain;
import com.xlg.android.utils.ByteBuffer;
import com.socks.library.KLog;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RoomChannel implements ClientSocketHandler {
    private int mUserID; // 用户ID
    private int mBbddyId; // 连麦用户ID
    private String mUserPwd; // 用户密码

    // 客户端回调
    private RoomHandler mHandler;
    // 新的socket对像
    private ClientSocket mSocket = new ClientSocket(this);
    // 接收缓冲区
    private ByteBuffer mBuffer = new ByteBuffer();


    public RoomChannel(RoomHandler handler) {
        mHandler = handler;
    }

    public int getmBbddyId() {
        return mBbddyId;
    }

    public void setmBbddyId(int mBbddyId) {
        this.mBbddyId = mBbddyId;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public void setUserPwd(String pwd) {
        mUserPwd = pwd;
    }

    // 连接服务器
    public int Connect(String ip, int port) {
        return mSocket.Connect(ip, port);
    }

    // 关闭
    public void Close() {
        mSocket.Close();
    }

    @Override
    public void onConnected(int ret) {
        if (0 == ret) {
            mHandler.onConnectSuccessed();
        } else {
            KLog.e("error code :" + ret);
            mHandler.onConnectFailed();
        }
    }

    @Override
    public void onRecv(byte[] data, int size) {
        // 加入缓冲区
        mBuffer.addBytes(data, 0, size);
        // 解析数据
        Header head = new Header();
        int len;
        while (true) {
            len = Message.DecodeHeader(mBuffer, head);
            if (len <= 0) {
                break;
            }

            switch (head.getCmd1()) {
                case Header.MessageType_mxpLogonResponse: {
                    ByteBuffer obj = Message.DecodeBody(mBuffer);
                    mHandler.onLoginRequest(obj.getInt(0));
                }
                break;
                case Header.MessageType_mxpTradeGiftError:
                    ByteBuffer obj = Message.DecodeBody(mBuffer);
                    mHandler.onTradeGiftError(obj.getInt(0));
                    break;
                case Header.MessageType_mxpTradeGiftNotify:
                    TradeGiftNotify tradeGiftNotify = new TradeGiftNotify();
                    Message.DecodeObject(mBuffer, tradeGiftNotify);
                    mHandler.onTradeGiftNotify(tradeGiftNotify);
                    break;
                case Header.MessageType_mxpUserPayResponse:
                    ByteBuffer obj_pay = Message.DecodeBody(mBuffer);
                    mHandler.onUserPayResponse(obj_pay.getInt(0));
                    break;
                case Header.MessageType_mxpKickoutUserNotify:
                    KickoutUserInfo kickoutUserInfo = new KickoutUserInfo();
                    Message.DecodeObject(mBuffer, kickoutUserInfo);
                    mHandler.onKickOut(kickoutUserInfo);
                    break;
                default:
                    break;
            }

            // 移除一个包
            mBuffer.rdarin(head.getLength());
        }
    }

    @Override
    public void onDisconnected() {
        mHandler.onDisconnected();
        Log.d("123", "断开连接");
    }


    private void sendPack(final Header head, final Object obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buf = new ByteBuffer();
                if (Message.EncodePack(buf, head, obj, false)) {
                    byte[] data = new byte[buf.size()];
                    buf.getBytes(data, 0, buf.size());
                    mSocket.Send(data);
                }
            }
        }).start();

    }

    // 发送Hello
    public void SendHello() {
        Header head = new Header();
        Hello obj = new Hello();
        KLog.e("SendHello");
        // 构建消息头
        head.setCmd1(Header.MessageType_mxpClientHelloCommand);
        // 构建消息体
        // 构建消息体
        obj.setParam0((int) (Math.random() * 0xffff));
        obj.setParam1(obj.getParam0() + (obj.getParam0() & 0xffff));
        obj.setParam2(obj.getParam0() - 2 * (obj.getParam0() & 0xffff));
        sendPack(head, obj);
    }

    // 发起心跳
    public void SendKeepAliveReq() {
        Header head = new Header();
        head.setVersion((byte) 1);
        KeepLiveRepuest obj = new KeepLiveRepuest();
        KLog.e("发起心跳");
        // 构建消息头
        head.setCmd1(Header.MessageType_mxpClientPingCommand);
        sendPack(head, obj);
    }

    // 发起登录房间请求
    public void SendLoginRequest() {
        Header head = new Header();
        LogonRequest obj = new LogonRequest();
        obj.setDeviceid(1);
        obj.setUserid(mUserID);
        obj.setCuserpwd(mUserPwd);
        KLog.e(mUserPwd);
        obj.setBuddyid(mBbddyId);
        obj.setNconnid(1);
        // 构建消息头
        head.setVersion((byte) 1);
        head.setCmd1(Header.MessageType_mxpLogonRequest);
        sendPack(head, obj);
    }

    // 发送礼物请求
    public void SendGift(int gift_id, int num) {
        Header head = new Header();
        head.setVersion((byte) 1);
        TradeGiftRecord obj = new TradeGiftRecord();
        obj.setUserid(mUserID);
        obj.setBuddyid(mBbddyId);
        obj.setGiftid(gift_id);
        obj.setAmount(num);
        head.setCmd1(Header.MessageType_mxpTradeGiftRequest);
        sendPack(head, obj);
    }

    // 发起预扣币消息
    public void SendUserPayRequest(int money, int type) {
        Header head = new Header();
        head.setVersion((byte) 1);
        UserPayRequest obj = new UserPayRequest();
        obj.setUserid(mUserID);
        obj.setBuddyid(mBbddyId);
        obj.setType(type);//3代表聊天
        obj.setMoney(money);//一条一毛钱
        head.setCmd1(Header.MessageType_mxpUserPayRequest);
        sendPack(head, obj);
    }

    // 退出登录消息
    public void SendKickOut() {
        Header head = new Header();
        head.setVersion((byte) 1);
        KickoutUserInfo obj = new KickoutUserInfo();
        obj.setUserid(mUserID);
        obj.setBuddyid(mUserID);
        obj.setReasonid((short) 103);//101-重复登录被请出;102-超时;103-自己退出
        head.setCmd1(Header.MessageType_mxpKickoutUserRequest);
        sendPack(head, obj);
    }
}
