package com.xlg.android;


// 各个事件回调函数

import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.TradeGiftNotify;

public interface RoomHandler {
	// 连接成功
	public void onConnectSuccessed();
	// 连接失败
	public void onConnectFailed();
	// 连接断开
	public void onDisconnected();

	// 登录回调
	public void onLoginRequest(int i);

	// 送礼物失败回调
	public void onTradeGiftError(int i);

	// 送礼物成功回调
	public void onTradeGiftNotify(TradeGiftNotify obj);

	//发聊天信息扣金币回调
	public void onUserPayResponse(int i);

	//提出用户回调
	public void onKickOut(KickoutUserInfo obj);
}
