package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.GiftItemAdapter;
import com.fubang.video.adapter.HomeLifeAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.entity.GiftEntity;
import com.fubang.video.ui.fragment.ChatFragment;
import com.fubang.video.util.GiftUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.DividerItemDecoration;
import com.hyphenate.easeui.callback.SelfMessageCallBack;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.util.EasyUtils;
import com.socks.library.KLog;
import com.vmloft.develop.app.demo.call.CallManager;
import com.vmloft.develop.app.demo.call.VideoCallActivity;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2017/7/18.
 */
public class ChatActivity extends BaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
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
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");

        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        frameLayout = (FrameLayout) findViewById(R.id.container);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        EventBus.getDefault().register(this);
        //礼物连击
        giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
        giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "showPop")
    public void showPop(String msg) {
        KLog.e("showPop");
        showPopupWindow(giftFrameLayout2);
    }
    @Subscriber(tag = "callVideo")
    public void callVideo(String msg) {
        KLog.e("callVideo");
        callVideo();

    }
    @Subscriber(tag = "sendTextMessage")
    public void sendTextMessage(SelfMessageCallBack callBack) {
        callBack.fail("金币不足");
        KLog.e("sendTextMessage");
    }

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
        if (toChatUsername.equals(username))
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
                giftControl.loadGift(new GiftModel(String.valueOf(gift_id), "送出礼物：", 1,
                        String.valueOf(gift_id), String.valueOf(toChatUsername), toChatUsername,
                        AppConstant.BASE_IMG_URL + VMSPUtil.get(context, AppConstant.USERPIC, ""), System.currentTimeMillis()));
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
        KLog.e(toChatUsername);
        Intent intent = new Intent(context, VideoCallActivity.class);
        CallManager.getInstance().setChatId(toChatUsername);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        startActivity(intent);
    }

}
