package com.xlg.android;


// 各个事件回调函数

import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.UserLinkInfo;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.VideoConnectRequest;
import com.xlg.android.protocol.VideoDisConnectRequest;

public interface RoomPoolHandler {
    // 连接成功
    public void onConnectSuccessed();

    // 连接失败
    public void onConnectFailed();

    // 连接断开
    public void onDisconnected();

    // 登录回调
    public void onLoginRequest(LogonResponse obj);


    //提出用户回调
    public void onKickOut(KickoutUserInfo obj);


    //msgid ==50  女性接收到 男性的发起  然后弹窗
    public void onUserLinkRequest(UserLinkInfo obj);

    //msgid ==51  男性处理  没有匹配的用户
    public void onUserLinkResponse(UserLinkInfo obj);

    //msgid ==52 若有用户抢单,男性用户收到和抢单女性用户收到msgid=52,type=5的消息,其他女性用户收到msgid=52,type=4的消息。男性用户取消请求msgid=53,待响应女性用户收到msgid=52,type=4的消息
    public void onUserLinkNotify(UserLinkInfo obj);
}
