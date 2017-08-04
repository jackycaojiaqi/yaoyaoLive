package com.xlg.android;


// 各个事件回调函数

import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayResponse;

public interface RoomHandler {
	// 连接成功
	public void onConnectSuccessed();
	// 连接失败
	public void onConnectFailed();
	// 连接断开
	public void onDisconnected();

	// 登录回调
	public void onLoginRequest(LogonResponse obj);

	// 送礼物失败回调
	public void onTradeGiftError(TradeGiftError obj);

	// 送礼物成功回调
	public void onTradeGiftNotify(TradeGiftNotify obj);

	//发聊天信息扣金币回调
	public void onUserPayResponse(UserPayResponse obj);

	//发聊天信息扣金币失败回调
	public void onUserPayError(UserPayError obj);

	//提出用户回调
	public void onKickOut(KickoutUserInfo obj);
}
