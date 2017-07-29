package com.xlg.android;

import android.util.Log;

import com.fubang.video.util.StringUtil;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.Hello;
import com.xlg.android.protocol.KeepLiveRepuest;
import com.xlg.android.protocol.LogonRequest;
import com.xlg.android.protocol.Message;
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


    private void sendPack(Header head, Object obj) {
        ByteBuffer buf = new ByteBuffer();
        if (Message.EncodePack(buf, head, obj, false)) {
            byte[] data = new byte[buf.size()];
            buf.getBytes(data, 0, buf.size());
            mSocket.Send(data);
        }
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
        KLog.e("SendLoginRequest");
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
}
