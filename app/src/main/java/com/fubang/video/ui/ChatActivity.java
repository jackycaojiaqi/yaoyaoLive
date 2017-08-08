package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.APP;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.GiftItemAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.db.UserDao;
import com.fubang.video.entity.GiftEntity;
import com.fubang.video.entity.SendMsgEntity;
import com.fubang.video.ui.fragment.ChatFragment;
import com.fubang.video.util.GiftUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.easeui.callback.SelfMessageCallBack;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.app.demo.call.CallManager;
import com.vmloft.develop.app.demo.call.VideoCallActivity;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.xlg.android.protocol.KickoutUserInfo;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.TradeGiftError;
import com.xlg.android.protocol.TradeGiftNotify;
import com.xlg.android.protocol.UserPayError;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.room.RoomMain;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fubang.video.ui.MainActivity.roomMain;

/**
 * Created by jacky on 2017/7/18.
 */
public class ChatActivity extends BaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    private String toChatUserPhone;
    private String toChatUserId;
    private String toChatUserNick;
    private PopupWindow pop_gift;
    private FrameLayout frameLayout;
    private BaseQuickAdapter adapter_gift;
    private List<GiftEntity> list_gift = new ArrayList<>();
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
    private GiftControl giftControl;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        EventBus.getDefault().register(this);
        toChatUserPhone = getIntent().getExtras().getString("userId");

        OkGo.<SendMsgEntity>post(AppConstant.BASE_URL + AppConstant.URL_CHECK_REG)
                .tag(this)
                .params("ctel", toChatUserPhone)
                .execute(new JsonCallBack<SendMsgEntity>(SendMsgEntity.class) {
                    @Override
                    public void onSuccess(Response<SendMsgEntity> response) {
                        if (response.body().getStatus().equals("fail")) {//不存在这个手机号码
                            toChatUserId = response.body().getInfo().getNuserid();
                        } else {//存在这个手机号码
                            ToastUtil.show(context, "对方帐号不存在");
                        }
                    }

                    @Override
                    public void onError(Response<SendMsgEntity> response) {
                        super.onError(response);
                    }
                });
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        frameLayout = (FrameLayout) findViewById(R.id.container);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        //礼物连击
        giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
        giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                roomMain.Start(Integer.parseInt((String) VMSPUtil.get(context, AppConstant.USERID, "0")),
//                        StringUtil.getMD5((String) VMSPUtil.get(context, AppConstant.PASSWORD, "0")),
//                        Integer.parseInt(user.getUserid()), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_CONNECT_PORT);
////                roomMain.Start(13, StringUtil.encodeMD5("123456"), 12, "42.121.57.170", 11444);
//            }
//        }).start();
    }



    @Override
    protected void onDestroy() {
        activityInstance = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 登录回调
     *
     * @param msg
     */
    @Subscriber(tag = "onKickOut")
    public void onKickOut(KickoutUserInfo msg) {
        if (msg.getReasonid() == 101) {
            KLog.e("重复登录被请出");
        } else if (msg.getReasonid() == 102) {
            ToastUtil.show(getApplicationContext(), "提出超时");
        } else if (msg.getReasonid() == 103) {
            KLog.e("自己离开房间");
        } else if (msg.getReasonid() == 104) {
            KLog.e("对方已经离开");
        }
    }

    /**
     * 登录回调
     *
     * @param msg
     */
    @Subscriber(tag = "chat_login_msg")
    public void chat_login_msg(LogonResponse msg) {
        if (msg.getErrorid() == 0) {
            KLog.e("登陆成功");
        } else if (msg.getErrorid() == 404) {
            KLog.e("数据库操作失败");
        } else if (msg.getErrorid() == 405) {
            KLog.e("用户名或密码错误");
        }
    }

    /**
     * 礼物弹窗回调
     *
     * @param msg
     */
    @Subscriber(tag = "showPop")
    public void showPop(String msg) {
        KLog.e("showPop");
        showPopupWindow(giftFrameLayout2);
    }

    /**
     * 送礼物失败回调
     *
     * @param msg
     */
    @Subscriber(tag = "onTradeGiftError")
    public void onTradeGiftError(TradeGiftError msg) {
        if (msg.getErrorid() == 504) {
            ToastUtil.show(context, "用户金币不足");
        } else if (msg.getErrorid() == 501) {
            ToastUtil.show(context, "礼物未维护");
        } else if (msg.getErrorid() == 404) {
            ToastUtil.show(context, "数据库操作失败");
        }
    }

    /**
     * 送礼物成功
     */
    @Subscriber(tag = "onTradeGiftNotify")
    public void onTradeGiftNotify(TradeGiftNotify obj) {
        KLog.e(obj.getPhoto());
        giftControl.loadGift(new GiftModel(String.valueOf(gift_id), "送出礼物：", 1,
                String.valueOf(obj.getGiftid()), String.valueOf(obj.getUserid()), obj.getAlias(),
                obj.getPhoto(), System.currentTimeMillis()));
    }

    @Subscriber(tag = "callVideo")
    public void callVideo(String msg) {
        KLog.e("callVideo");
        callVideo();
    }

    //========================================文字聊天回调处理
    private SelfMessageCallBack callBack;

    @Subscriber(tag = "sendTextMessage")
    public void sendTextMessage(SelfMessageCallBack callBack) {
        this.callBack = callBack;
        if ((VMSPUtil.get(context, AppConstant.GENDER, "")).equals("0")) {//女性不用扣币
            callBack.success("成功");
        } else {//男性先扣币再发送消息
            roomMain.getRoom().getChannel().SendUserPayRequest(Integer.parseInt(toChatUserId), 1, 3);
        }
        KLog.e("sendTextMessage");
    }

    //扣币成功
    @Subscriber(tag = "onUserPayResponse")
    public void onUserPayResponse(UserPayResponse msg) {
        if (callBack != null) {//放置礼物扣费 也走这个回调
            if (msg.getType() == 1) {
                callBack.success("成功");
            } else {
                ToastUtil.show(context, "扣币操作失败");
            }
            callBack = null;//用完之后重置
        }
    }

    //扣币失败
    @Subscriber(tag = "onUserPayError")
    public void onUserPayError(UserPayError msg) {
        if (msg.getErrorid() == 504) {
            callBack.fail("金币不足");
        } else if (msg.getErrorid() == 404) {
            callBack.fail("数据库操作失败");
        }
    }

    //========================================文字聊天回调处理结束
    @Subscriber(tag = "sendBigExpressionMessage")
    public void sendBigExpressionMessage(SelfMessageCallBack callBack) {
        callBack.success("success");
        callBack.fail("fail");
        KLog.e("sendBigExpressionMessage");
    }

    @Subscriber(tag = "sendVoiceMessage")
    public void sendVoiceMessage(SelfMessageCallBack callBack) {
        callBack.success("success");
        callBack.fail("fail");
        KLog.e("sendVoiceMessage");
    }

    @Subscriber(tag = "sendImageMessage")
    public void sendImageMessage(SelfMessageCallBack callBack) {
        callBack.success("success");
        callBack.fail("fail");
        KLog.e("sendImageMessage");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUserPhone.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private int gift_id = -1;

    private void showPopupWindow(View view) {
        KLog.e("123");
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_window_gift, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.transparent_50));
        RelativeLayout rll_gift_bg = (RelativeLayout) contentView.findViewById(R.id.rll_gift_bg);
        rll_gift_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView tv_send = (TextView) contentView.findViewById(R.id.tv_send_gift);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gift_id == -1) {
                    ToastUtil.show(context, "请先选择一种礼物");
                    return;
                }
                roomMain.getRoom().getChannel().SendGift(Integer.parseInt(toChatUserId), gift_id, 1,
                        (String) VMSPUtil.get(context, AppConstant.USERNAME, ""), (String) VMSPUtil.get(context, AppConstant.USERPIC, ""));
                gift_id = -1;
                popupWindow.dismiss();
            }
        });
        RecyclerView rv_gift = (RecyclerView) contentView.findViewById(R.id.rv_gift);
        list_gift = GiftUtil.getGifts();
        adapter_gift = new GiftItemAdapter(R.layout.pop_item_gift, list_gift);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_gift.setLayoutManager(linearLayoutManager);
        adapter_gift.setEnableLoadMore(true);
        adapter_gift.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gift_id = list_gift.get(position).getGiftId();
            }
        });
        adapter_gift.bindToRecyclerView(rv_gift);
        adapter_gift.setEmptyView(R.layout.empty_view);
        rv_gift.setAdapter(adapter_gift);
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 视频呼叫
     */
    private void callVideo() {
        KLog.e(toChatUserPhone);
        Intent intent = new Intent(context, VideoCallActivity.class);
        intent.putExtra("from", (String) VMSPUtil.get(context, AppConstant.PHONE, ""));
        intent.putExtra("to", toChatUserPhone);
        VMSPUtil.put(context, AppConstant.CALLFROM, (String) VMSPUtil.get(context, AppConstant.PHONE, ""));
        VMSPUtil.put(context, AppConstant.CALLTO, toChatUserPhone);
        CallManager.getInstance().setChatId(toChatUserPhone);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        startActivity(intent);
        finish();
    }

}
