package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.HomeLifeAdapter;
import com.fubang.video.adapter.HomeNewAdapter;
import com.fubang.video.adapter.HomeOnlineAdapter;
import com.fubang.video.adapter.HomeTuHaoAdapter;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.HomeEntity;
import com.fubang.video.entity.ReviewEntity;
import com.fubang.video.ui.CircleInfoDetailActivity;
import com.fubang.video.ui.SearUserActivity;
import com.fubang.video.ui.UserInfoActivity;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.DividerItemDecoration;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.skyfishjy.library.RippleBackground;
import com.vmloft.develop.app.demo.call.CallManager;
import com.vmloft.develop.app.demo.call.VideoCallActivity;
import com.vmloft.develop.app.demo.call.VoiceCallActivity;
import com.vmloft.develop.library.tools.utils.VMLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jacky on 2017/7/11.
 */
public class HomeFragment extends BaseFragment {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.sv_home)
    ScrollView svHome;

    @BindView(R.id.layout_root)
    SwipeRefreshLayout SwipeRefreshView;

    Unbinder unbinder;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.tv_home_random_call)
    ImageView tvHomeRandomCall;
    @BindView(R.id.tv_home_online_num)
    TextView tvHomeOnlineNum;
    @BindView(R.id.tv_home_action1)
    TextView tvHomeAction1;
    @BindView(R.id.rv_home_action1)
    RecyclerView rvHomeAction1;
    @BindView(R.id.tv_home_action2)
    TextView tvHomeAction2;
    @BindView(R.id.rv_home_action2)
    RecyclerView rvHomeAction2;
    @BindView(R.id.tv_home_action3)
    TextView tvHomeAction3;
    @BindView(R.id.rv_home_action3)
    RecyclerView rvHomeAction3;
    @BindView(R.id.tv_home_action4)
    TextView tvHomeAction4;
    @BindView(R.id.rv_home_action4)
    RecyclerView rvHomeAction4;
    @BindView(R.id.ripple_view)
    RippleBackground rippleView;

    private String username;
    private String password;
    private String contacts;
    private Context context;
    private BaseQuickAdapter adapter_1, adapter_2, adapter_3, adapter_4;
    private List<HomeEntity.InfoBean.OnlineListBean> list_action1 = new ArrayList<>();
    private List<HomeEntity.InfoBean.TuhaoListBean> list_action2 = new ArrayList<>();
    private List<HomeEntity.InfoBean.NewListBean> list_action3 = new ArrayList<>();
    private List<HomeEntity.InfoBean.LifeListBean> list_action4 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_call, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initdate();
    }

    private void initdate() {
        OkGo.<HomeEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_HOME_DATE)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("ngender", String.valueOf(VMSPUtil.get(context, AppConstant.GENDER, "0")))
                .execute(new JsonCallBack<HomeEntity>(HomeEntity.class) {
                    @Override
                    public void onSuccess(Response<HomeEntity> response) {
                        SwipeRefreshView.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            list_action1 = response.body().getInfo().getOnline_list();
                            list_action2 = response.body().getInfo().getTuhao_list();
                            list_action3 = response.body().getInfo().getNew_list();
                            list_action4 = response.body().getInfo().getLife_list();
                            //列表1 在线======================================================================================
                            adapter_1 = new HomeOnlineAdapter(R.layout.item_home_online, list_action1);
                            rvHomeAction1.setLayoutManager(new GridLayoutManager(context, 3));
                            adapter_1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action1.get(position).getNuserid());
                                    if (list_action1.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                        intent.putExtra(AppConstant.TYPE, 1);
                                    } else {
                                        intent.putExtra(AppConstant.TYPE, 0);
                                    }
                                    startActivity(intent);
                                }
                            });
                            adapter_1.bindToRecyclerView(rvHomeAction1);
                            adapter_1.setEmptyView(R.layout.empty_view);
                            rvHomeAction1.setAdapter(adapter_1);
                            //水平分割线
                            rvHomeAction1.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.white)));

                            //列表2 土豪======================================================================================
                            adapter_2 = new HomeTuHaoAdapter(R.layout.item_home_online, list_action2);
                            rvHomeAction2.setLayoutManager(new GridLayoutManager(context, 3));
                            adapter_2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action2.get(position).getNuserid());
                                    if (list_action1.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                        intent.putExtra(AppConstant.TYPE, 1);
                                    } else {
                                        intent.putExtra(AppConstant.TYPE, 0);
                                    }
                                    startActivity(intent);
                                }
                            });
                            adapter_2.bindToRecyclerView(rvHomeAction2);
                            adapter_2.setEmptyView(R.layout.empty_view);
                            rvHomeAction2.setAdapter(adapter_2);
                            //水平分割线
                            rvHomeAction2.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.white)));
                            //列表3 新人===================================================================================
                            adapter_3 = new HomeNewAdapter(R.layout.item_home_online, list_action3);
                            rvHomeAction3.setLayoutManager(new GridLayoutManager(context, 3));
                            adapter_3.setEnableLoadMore(true);
                            adapter_3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action3.get(position).getNuserid());
                                    if (list_action1.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                        intent.putExtra(AppConstant.TYPE, 1);
                                    } else {
                                        intent.putExtra(AppConstant.TYPE, 0);
                                    }
                                    startActivity(intent);
                                }
                            });
                            adapter_3.bindToRecyclerView(rvHomeAction3);
                            adapter_3.setEmptyView(R.layout.empty_view);
                            rvHomeAction3.setAdapter(adapter_3);
                            //水平分割线
                            rvHomeAction3.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.white)));
                            //列表4 朋友圈===================================================================================
                            adapter_4 = new HomeLifeAdapter(R.layout.item_home_life, list_action4);
                            //设置布局管理器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rvHomeAction4.setLayoutManager(linearLayoutManager);
                            adapter_4.setEnableLoadMore(true);
                            adapter_4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, CircleInfoDetailActivity.class);
                                    intent.putExtra(AppConstant.OBJECT, list_action4.get(position).getNid());
                                    intent.putExtra(AppConstant.TYPE, "0");
                                    startActivity(intent);
                                }
                            });
                            adapter_4.bindToRecyclerView(rvHomeAction4);
                            adapter_4.setEmptyView(R.layout.empty_view);
                            rvHomeAction4.setAdapter(adapter_4);
                            //水平分割线
                            rvHomeAction4.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.VERTICAL_LIST, 10, getResources().getColor(R.color.white)));
                        }
                    }

                    @Override
                    public void onError(Response<HomeEntity> response) {
                        super.onError(response);
                    }
                });
    }

    private void init() {
        contacts = (String) VMSPUtil.get(context, "contacts", "");
        ivAction.setVisibility(View.VISIBLE);
        SwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initdate();
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
        SwipeRefreshView.setProgressViewOffset(true, 150, 250);

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

    private boolean is_call_start = false;

    @OnClick({R.id.tv_home_random_call, R.id.tv_home_action1, R.id.tv_home_action2, R.id.tv_home_action3, R.id.tv_home_action4,
            R.id.iv_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home_random_call:
                if (!is_call_start) {
                    rippleView.startRippleAnimation();
                } else {
                    rippleView.stopRippleAnimation();
                }
                is_call_start = !is_call_start;
                break;
            case R.id.tv_home_action1:
                break;
            case R.id.tv_home_action2:
                break;
            case R.id.tv_home_action3:
                break;
            case R.id.tv_home_action4:
                break;
            case R.id.iv_action:
                startActivity(new Intent(context, SearUserActivity.class));
                break;
        }
    }
}
