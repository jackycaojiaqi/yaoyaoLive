package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fubang.video.R;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.util.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.vmloft.develop.app.demo.call.CallManager;
import com.vmloft.develop.app.demo.call.VideoCallActivity;
import com.vmloft.develop.app.demo.call.VoiceCallActivity;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jacky on 2017/7/11.
 */
public class HomeFragment extends BaseFragment {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.btn_call_video)
    Button btnCallVideo;
    @BindView(R.id.sv_home)
    ScrollView svHome;
    @BindView(R.id.edit_contacts_username)
    EditText contactsView;

    @BindView(R.id.layout_root)
    SwipeRefreshLayout SwipeRefreshView;

    Unbinder unbinder;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    private String username;
    private String password;
    private String contacts;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_call, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        contacts = (String) VMSPUtil.get(context, "contacts", "");
        contactsView.setText(contacts);
        ivAction.setVisibility(View.VISIBLE);
        SwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshView.setRefreshing(false);
            }
        });
        svHome.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (SwipeRefreshView != null) {
                    SwipeRefreshView.setEnabled(svHome.getScrollY() == 0);
                }
            }
        });
        SwipeRefreshView.setProgressViewOffset(true,150,250);
    }


    @OnClick({
            R.id.btn_send,
            R.id.btn_call_voice, R.id.btn_call_video
    })
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendTextMessage();
                break;
            case R.id.btn_call_voice:
                callVoice();
                break;
            case R.id.btn_call_video:
                callVideo();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendTextMessage() {
        checkContacts();
        EMMessage message = EMMessage.createTxtSendMessage("测试发送消息，主要是为了测试是否在线", contacts);
        // 设置强制推送
        message.setAttribute("em_force_notification", "true");
        message.setTo("2");
        // 设置自定义推送提示
        JSONObject extObj = new JSONObject();
        try {
            extObj.put("em_push_title", "测试消息推送，这里是推送内容，一般这里直接写上消息详情");
            extObj.put("extern", "定义推送扩展内容");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        message.setAttribute("em_apns_ext", extObj);
        sendMessage(message);
    }

    /**
     * 视频呼叫
     */
    private void callVideo() {
        checkContacts();
        Intent intent = new Intent(context, VideoCallActivity.class);
        CallManager.getInstance().setChatId(contacts);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        startActivity(intent);
    }

    /**
     * 语音呼叫
     */
    private void callVoice() {
        checkContacts();
        Intent intent = new Intent(context, VoiceCallActivity.class);
        CallManager.getInstance().setChatId(contacts);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VOICE);
        startActivity(intent);
    }

    private void checkContacts() {
        contacts = contactsView.getText().toString().trim();
        if (contacts.isEmpty()) {
            Toast.makeText(context, "constact user not null", Toast.LENGTH_LONG).show();
            return;
        }
        VMSPUtil.put(context, "contacts", contacts);
    }

    /**
     * 最终调用发送信息方法
     *
     * @param message 需要发送的消息
     */
    private void sendMessage(final EMMessage message) {
        /**
         *  调用sdk的消息发送方法发送消息，发送消息时要尽早的设置消息监听，防止消息状态已经回调，
         *  但是自己没有注册监听，导致检测不到消息状态的变化
         *  所以这里在发送之前先设置消息的状态回调
         */
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                String str = String.format("消息发送成功 msgId %s, content %s", message.getMsgId(),
                        message.getBody());
                ToastUtil.show(context, str);
                VMLog.i(str);
            }

            @Override
            public void onError(final int i, final String s) {
                String str = String.format("消息发送失败 code: %d, error: %s", i, s);
                ToastUtil.show(context, str);
                VMLog.i(str);
            }

            @Override
            public void onProgress(int i, String s) {
                // TODO 消息发送进度，这里不处理，留给消息Item自己去更新
                VMLog.i("消息发送中 progress: %d, %s", i, s);
            }
        });
        // 发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
