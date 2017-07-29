package com.xlg.android;


// 各个事件回调函数

public interface RoomHandler {
	// 连接成功
	public void onConnectSuccessed();
	// 连接失败
	public void onConnectFailed();
	// 连接断开
	public void onDisconnected();

	// 连接断开
	public void onLoginRequest(int i);
}
