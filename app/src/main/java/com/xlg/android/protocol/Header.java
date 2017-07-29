package com.xlg.android.protocol;

public class Header {
    public static final int SIZE_HEADER = 16;

    public static final int OFFSET_LENGTH = 0;
    public static final int OFFSET_VERSION = 4;
    public static final int OFFSET_RESERVE = 4;
    public static final int OFFSET_CMD1 = 5;
    public static final int OFFSET_CMD2 = 6;
    public static final int OFFSET_CMD3 = 7;
    public static final int OFFSET_TIMER = 8;

    public static final byte MessageType_mxpClientHelloCommand = (byte) 1; // 1;
    public static final byte MessageType_mxpClientPingCommand = (byte) 2; // 2;
    public static final byte MessageType_mxpLogonRequest = (byte) 10; // 10;
    public static final byte MessageType_mxpLogonResponse = (byte) 11; // 11;
    public static final byte MessageType_mxpTradeGiftRequest = (byte) 12; // 12;用户A赠送礼物给用户B。若成功，则返回具体信息。若失败，则返回错误。
    public static final byte MessageType_mxpTradeGiftError = (byte) 13; // 13;赠送礼物失败返回
    public static final byte MessageType_mxpTradeGiftNotify = (byte) 14; // 14;赠送礼物失败返回
    public static final byte MessageType_mxpUserPayRequest = (byte) 15; // 15;预扣币消息 客户端发送扣币请求。
    public static final byte MessageType_mxpUserPayResponse = (byte) 16; // 16;预扣币返回


    public static final byte MessageType_mxpKickoutUserRequest = (byte) 20; // 20;系统踢用户，或者用户自己退出
    public static final byte MessageType_mxpKickoutUserNotify = (byte) 21; // 21;系统踢用户，或者用户自己退出返回


    public static final byte MessageType_mxpLogonError = (byte) 3; // 3;
    public static final byte MessageType_mxpJoinRoomRequest = (byte) 4; // 4
    public static final byte MessageType_mxpJoinRoomError = (byte) 5; // 5;
    public static final byte MessageType_mxpJoinRoomResponse = (byte) 6; // 6


    private int length;        //数据包总长
    private byte version = 1;    //版本号 0->旧版本  1->本文件定义版本
    private byte reserve;    //保留
    private byte cmd1;        //消息类型，一级消息
    private byte cmd2;        //消息类型，二级消息
    private byte cmd3;        //保留。可以放随机数用于安全
    private long timer;        //随机种子.用于安全。
    private byte[] content = new byte[0]; //后继数据

    public byte getReserve() {
        return reserve;
    }

    public void setReserve(byte reserve) {
        this.reserve = reserve;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

//	public byte getReserve() {
//		return reserve;
//	}
//	
//	public void setReserve(byte reserve) {
//		this.reserve = reserve;
//	}

    public byte getCmd1() {
        return cmd1;
    }

    public void setCmd1(byte cmd1) {
        this.cmd1 = cmd1;
    }

    public byte getCmd2() {
        return cmd2;
    }

    public void setCmd2(byte cmd2) {
        this.cmd2 = cmd2;
    }

    public byte getCmd3() {
        return cmd3;
    }

    public void setCmd3(byte cmd3) {
        this.cmd3 = cmd3;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

}
