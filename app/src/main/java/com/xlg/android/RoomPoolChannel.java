package com.xlg.android;

import android.util.Log;

import com.socks.library.KLog;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.Hello;
import com.xlg.android.protocol.KeepLiveRepuest;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonRequest;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.Message;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.TradeGiftRecord;
import com.xlg.android.protocol.UserLinkInfo;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayRequest;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.VideoConnectRequest;
import com.xlg.android.protocol.VideoDisConnectRequest;
import com.xlg.android.utils.ByteBuffer;

public class RoomPoolChannel implements ClientSocketHandler {
    private int mUserID; // 用户ID
    private String mUserPwd; // 用户密码

    // 客户端回调
    private RoomPoolHandler mHandler;
    // 新的socket对像
    private ClientPoolSocket mSocket = new ClientPoolSocket(this);
    // 接收缓冲区
    private ByteBuffer mBuffer = new ByteBuffer();


    public RoomPoolChannel(RoomPoolHandler handler) {
        mHandler = handler;
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
                case Header.MessageType_mxpLogonResponse:
                    LogonResponse logonResponse = new LogonResponse();
                    Message.DecodeObject(mBuffer, logonResponse);
                    mHandler.onLoginRequest(logonResponse);
                    break;
                case Header.MessageType_mxpKickoutUserNotify:
                    KickoutUserInfo kickoutUserInfo = new KickoutUserInfo();
                    Message.DecodeObject(mBuffer, kickoutUserInfo);
                    mHandler.onKickOut(kickoutUserInfo);
                    break;
                case Header.MessageType_mxpUserLinkRequest://女性被安排到 需要弹窗  让女性处理
                    UserLinkInfo userLinkInfo = new UserLinkInfo();
                    Message.DecodeObject(mBuffer, userLinkInfo);
                    mHandler.onUserLinkRequest(userLinkInfo);
                    break;
                case Header.MessageType_mxpUserLinkResponse://男性处理  3无可选用户
                    UserLinkInfo userLinkInfo2 = new UserLinkInfo();
                    Message.DecodeObject(mBuffer, userLinkInfo2);
                    mHandler.onUserLinkResponse(userLinkInfo2);
                    break;
                case Header.MessageType_mxpUserLinkNotify://广播连接
                    UserLinkInfo userLinkInfo1 = new UserLinkInfo();
                    Message.DecodeObject(mBuffer, userLinkInfo1);
                    mHandler.onUserLinkNotify(userLinkInfo1);
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
    private long before = 0;
    private long after = 0;

    // 发起心跳
    public void SendKeepAliveReq() {
        before = System.currentTimeMillis();
        Header head = new Header();
        head.setVersion((byte) 1);
        KeepLiveRepuest obj = new KeepLiveRepuest();

        // 构建消息头
        head.setCmd1(Header.MessageType_mxpClientPingCommand);
        if (before - after < 3000) {
            return;
        }
        KLog.e("发起心跳");
        after = before;
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
        // 构建消息头
        head.setVersion((byte) 1);
        head.setCmd1(Header.MessageType_mxpLogonRequest);
        sendPack(head, obj);
    }

    // 发送礼物请求
    public void SendGift(int toid, int gift_id, int num, String alias, String photo) {
        KLog.e("SendGift " + toid + " " + gift_id + " " + num + " " + alias + " " + photo);
        Header head = new Header();
        head.setVersion((byte) 1);
        TradeGiftRecord obj = new TradeGiftRecord();
        obj.setUserid(mUserID);
        obj.setToid(toid);
        obj.setGiftid(gift_id);
        obj.setAmount(num);
        obj.setAlias(alias);
        obj.setPhoto(photo);
        head.setCmd1(Header.MessageType_mxpTradeGiftRequest);
        sendPack(head, obj);
    }

    // 发起预扣币消息
    public void SendUserPayRequest(int toid, int money, int type) {
        Header head = new Header();
        head.setVersion((byte) 1);
        UserPayRequest obj = new UserPayRequest();
        obj.setUserid(mUserID);
        obj.setBuddyid(toid);
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

    // 开始视频连接的请求
    public void SendVideoConnect(int buddyid) {
        Header head = new Header();
        head.setVersion((byte) 1);
        VideoConnectRequest obj = new VideoConnectRequest();
        obj.setUserid(mUserID);
        obj.setBuddyid(buddyid);
        head.setCmd1(Header.MessageType_mxpVideoConnectRequest);
        sendPack(head, obj);
    }

    // 断开视频连接的请求
    public void SendVideoDisConnect(int buddyid) {
        Header head = new Header();
        head.setVersion((byte) 1);
        VideoDisConnectRequest obj = new VideoDisConnectRequest();
        obj.setUserid(mUserID);
        head.setCmd1(Header.MessageType_mxpVideoDisConnectRequest);
        sendPack(head, obj);
    }

    // 男性用户发起链接请求
    public void SendUserLinkRequest() {
        KLog.e("SendUserLinkRequest");
        Header head = new Header();
        head.setVersion((byte) 1);
        UserLinkInfo obj = new UserLinkInfo();
        obj.setUserid(mUserID);
        obj.setType(0);//男的发起请求 0
        head.setCmd1(Header.MessageType_mxpUserLinkRequest);
        sendPack(head, obj);
    }

    // 男性用户取消链接请求
    public void SendUserDisLinkRequest() {
        Header head = new Header();
        head.setVersion((byte) 1);
        UserLinkInfo obj = new UserLinkInfo();
        obj.setUserid(mUserID);
        obj.setType(4);//男的发起请求 4
        head.setCmd1(Header.MessageType_mxpUserLinkCancelRequest);
        sendPack(head, obj);
    }

    //女性用户响应接受还是取消请求
    public void SendFemaleRequest(int type,int male_id) {
        Header head = new Header();
        head.setVersion((byte) 1);
        UserLinkInfo obj = new UserLinkInfo();
        obj.setUserid(male_id);
        obj.setBuddyid(mUserID);
        obj.setType(type);//由女性发起  1、用户同意  2、用户拒绝
        head.setCmd1(Header.MessageType_mxpUserLinkResponse);
        sendPack(head, obj);
    }
}
