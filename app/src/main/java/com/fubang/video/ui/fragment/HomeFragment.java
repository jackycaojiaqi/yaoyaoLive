package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.HomeFujinAdapter;
import com.fubang.video.adapter.HomeLifeAdapter;
import com.fubang.video.adapter.HomeNewAdapter;
import com.fubang.video.adapter.HomeNvShengAdapter;
import com.fubang.video.adapter.HomeOnlineAdapter;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.entity.HomeEntity;
import com.fubang.video.ui.CircleHotActivity;
import com.fubang.video.ui.CircleInfoDetailActivity;
import com.fubang.video.ui.HomeListActivity;
import com.fubang.video.ui.HomeListBigPicActivity;
import com.fubang.video.ui.LoginActivity;
import com.fubang.video.ui.MainActivity;
import com.fubang.video.ui.RechargeActivity;
import com.fubang.video.ui.SearUserActivity;
import com.fubang.video.ui.UserInfoActivity;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.skyfishjy.library.RippleBackground;
import com.socks.library.KLog;
import com.vmloft.develop.app.demo.call.CallManager;
import com.vmloft.develop.app.demo.call.VideoCallActivity;
import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.UserLinkInfo;
import com.xlg.android.room.RoomPoolMain;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

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

    //女性开启接听、关闭接听界面
    @BindView(R.id.btn_home_female_stop)
    Button btnHomeFemaleStop;
    @BindView(R.id.btn_home_female_start)
    Button btnHomeFemaleStart;
    @BindView(R.id.ll_home_female_control)
    LinearLayout llHomeFemaleControl;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.iv_home_goto_list_nvshen)
    ImageView ivHomeGotoListNvshen;
    @BindView(R.id.iv_home_goto_list_tuhao)
    ImageView ivHomeGotoListTuhao;
    @BindView(R.id.iv_home_goto_list_song)
    ImageView ivHomeGotoListSong;
    @BindView(R.id.iv_home_goto_list_liao)
    ImageView ivHomeGotoListLiao;
    @BindView(R.id.tv_home_action5)
    TextView tvHomeAction5;
    @BindView(R.id.rv_home_action5)
    RecyclerView rvHomeAction5;
    @BindView(R.id.rll_home_nvsheng)
    RelativeLayout rllHomeNvsheng;
    @BindView(R.id.rll_home_fujin)
    RelativeLayout rllHomeFujin;

    private String username;
    private String password;
    private String contacts;
    private Context context;
    private BaseQuickAdapter adapter_1, adapter_2, adapter_3, adapter_4, adapter_5;
    private List<HomeEntity.InfoBean.OnlineListBean> list_action1 = new ArrayList<>();
    private List<HomeEntity.InfoBean.NvShengListBean> list_action2 = new ArrayList<>();
    private List<HomeEntity.InfoBean.NewListBean> list_action3 = new ArrayList<>();
    private List<HomeEntity.InfoBean.LifeListBean> list_action4 = new ArrayList<>();
    private List<HomeEntity.InfoBean.FuJinListBean> list_action5 = new ArrayList<>();
    private RoomPoolMain roomMain = new RoomPoolMain();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_call, container, false);
        context = getActivity();
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initdate();
    }

    /**
     * 登录回调
     *
     * @param msg
     */
    @Subscriber(tag = "chat_login_pool_msg")
    public void chat_login_pool_msg(LogonResponse msg) {
        if (msg.getErrorid() == 0) {
            KLog.e("POOL登陆成功");
        } else if (msg.getErrorid() == 404) {
            KLog.e("POOL数据库操作失败");
        } else if (msg.getErrorid() == 405) {
            KLog.e("POOL用户名或密码错误");
        }
    }

    private MaterialDialog dialog;

    /**
     * 女性接收到视频请求弹窗，并处理接收还是取消 msgid ==50
     */
    @Subscriber(tag = "onUserLinkRequest")
    public void onUserLinkRequest(final UserLinkInfo msg) {
        if (msg.getType() == 0) {//男主播请求
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                    .title(R.string.pick_call_title)
                    .content(R.string.pick_call_content)
                    .positiveText(R.string.pick_call)
                    .negativeText(R.string.cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            roomMain.getRoom().getChannel().SendFemaleRequest(1, msg.getUserid());//同意
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            roomMain.getRoom().getChannel().SendFemaleRequest(2, msg.getUserid());//拒绝
                            dialog.dismiss();
                        }
                    });
            dialog = builder.build();
            dialog.show();
        }
    }

    /**
     * 男性处理 没有匹配的用户  msgid ==51
     */
    @Subscriber(tag = "onUserLinkResponse")
    public void onUserLinkResponse(UserLinkInfo msg) {
        if (msg.getType() == 3) {//男主播请求
            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                    .title(R.string.no_patch_anchor)
                    .positiveText(R.string.sure)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();

                        }
                    });
            MaterialDialog dialog = builder.build();
            dialog.show();
        }
    }

    /**
     * 广播  msgid ==52
     */
    @Subscriber(tag = "onUserLinkNotify")
    public void onUserLinkNotify(UserLinkInfo msg) {
        if (msg.getType() == 5) {//表示已经抢单成功,男性就是发起人 女性就是女主播
            if (VMSPUtil.get(context, AppConstant.GENDER, "").equals("0")) {//女性
                OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                        .tag(this)
                        .params("nuserid", msg.getUserid())
                        .params("ctoken", String.valueOf(VMSPUtil.get(getActivity(), AppConstant.TOKEN, "")))
                        .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                            @Override
                            public void onSuccess(Response<BaseInfoEntity> response) {
                                if (response.body() != null)
                                    if (response.body().getStatus().equals("success")) {
                                        //配对成功先把自己踢出  并置按钮为取消状态
                                        btnHomeFemaleStart.setBackgroundResource(R.drawable.ic_home_female_bg);
                                        btnHomeFemaleStop.setBackgroundResource(R.drawable.ic_home_female_bg_select);
                                        is_female_control_start = false;
                                        roomMain.getRoom().getChannel().SendKickOut();
                                        roomMain.getRoom().getChannel().Close();
                                        callVideo(response.body().getInfo().getCtel());//获取到手机号码后发起通话
                                    } else {//token失效
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    }
                            }

                            @Override
                            public void onError(Response<BaseInfoEntity> response) {
                                super.onError(response);
                            }
                        });
            } else {//男性
                //置男性为自动接起主播的视频聊天
                MainActivity.is_male_pick_up_auto = true;
                roomMain.getRoom().getChannel().SendKickOut();
                roomMain.getRoom().getChannel().Close();
                //重置按钮动画状态
                rippleView.stopRippleAnimation();
                //等待视频聊天界面的呼起
            }
        } else if (msg.getType() == 4) {//用户取消或者已有人抢单取消
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            if (VMSPUtil.get(context, AppConstant.GENDER, "").equals("0")) {//女性
                ToastUtil.show(context, R.string.this_call_is_picked);
            } else {
                ToastUtil.show(context, R.string.user_giveup_call);
            }
        }
    }

    /**
     * 视频呼叫
     */

    private void callVideo(String contacts) {
        Intent intent = new Intent(context, VideoCallActivity.class);
        intent.putExtra("from", (String) VMSPUtil.get(context, AppConstant.PHONE, ""));
        intent.putExtra("to", contacts);
        VMSPUtil.put(context, AppConstant.CALLFROM, (String) VMSPUtil.get(context, AppConstant.PHONE, ""));
        VMSPUtil.put(context, AppConstant.CALLTO, contacts);
        CallManager.getInstance().setChatId(contacts);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        startActivity(intent);
    }

    /**
     * pool男性用户取消
     */
    @Subscriber(tag = "onUserLinkCancelRequest")
    public void onUserLinkCancelRequest(UserLinkInfo msg) {

    }

    private String gender_post;

    private void initdate() {
        if (String.valueOf(VMSPUtil.get(context, AppConstant.GENDER, "0")).equals("0")) {
            rllHomeNvsheng.setVisibility(View.GONE);
            rllHomeFujin.setVisibility(View.GONE);
            gender_post = "1";
        } else {
            rllHomeNvsheng.setVisibility(View.VISIBLE);
            rllHomeFujin.setVisibility(View.VISIBLE);
            gender_post = "0";
        }
        OkGo.<HomeEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_HOME_DATE)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
//                .params("ngender", gender_post)//要传和该账号性别相反的参数
                .execute(new JsonCallBack<HomeEntity>(HomeEntity.class) {
                    @Override
                    public void onSuccess(Response<HomeEntity> response) {
                        SwipeRefreshView.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            list_action1 = response.body().getInfo().getOnline_list();
                            list_action2 = response.body().getInfo().getNv_sheng_list();
                            list_action3 = response.body().getInfo().getNew_list();
                            list_action4 = response.body().getInfo().getLife_list();
                            list_action5 = response.body().getInfo().getFu_jin_list();
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

                            //列表2 女神======================================================================================
                            adapter_2 = new HomeNvShengAdapter(R.layout.item_home_online, list_action2);
                            rvHomeAction2.setLayoutManager(new GridLayoutManager(context, 3));
                            adapter_2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action2.get(position).getNuserid());
                                    if (list_action2.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
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
                            adapter_3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action3.get(position).getNuserid());
                                    if (list_action3.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
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
                            //列表5 附近===================================================================================
                            adapter_5 = new HomeFujinAdapter(R.layout.item_home_online, list_action5);
                            rvHomeAction5.setLayoutManager(new GridLayoutManager(context, 3));
                            adapter_5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, UserInfoActivity.class);
                                    intent.putExtra(AppConstant.USERID, list_action5.get(position).getNuserid());
                                    if (list_action5.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                        intent.putExtra(AppConstant.TYPE, 1);
                                    } else {
                                        intent.putExtra(AppConstant.TYPE, 0);
                                    }
                                    startActivity(intent);
                                }
                            });
                            adapter_5.bindToRecyclerView(rvHomeAction5);
                            adapter_5.setEmptyView(R.layout.empty_view);
                            rvHomeAction5.setAdapter(adapter_5);
                            //水平分割线
                            rvHomeAction5.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.white)));
                            //列表4 朋友圈===================================================================================
                            adapter_4 = new HomeLifeAdapter(R.layout.item_home_life, list_action4);
                            //设置布局管理器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rvHomeAction4.setLayoutManager(linearLayoutManager);
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
        if (VMSPUtil.get(context, AppConstant.GENDER, "").equals("0")) {
            llHomeFemaleControl.setVisibility(View.VISIBLE);
        } else {
            llHomeFemaleControl.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private boolean is_call_start = false;
    private boolean is_female_control_start = false;

    @OnClick({R.id.tv_home_random_call, R.id.tv_home_action1, R.id.tv_home_action2, R.id.tv_home_action3, R.id.tv_home_action4, R.id.tv_home_action5,
            R.id.iv_action, R.id.btn_home_female_start, R.id.btn_home_female_stop, R.id.iv_home_goto_list_nvshen, R.id.iv_home_goto_list_tuhao,
            R.id.iv_home_goto_list_song, R.id.iv_home_goto_list_liao})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_home_random_call:
                if (VMSPUtil.get(context, AppConstant.GENDER, "").equals("0")) {//女性不能发起一对多通话
                    return;
                }
                if ((long) VMSPUtil.get(context, AppConstant.NKNUM, (long) 0) < 20) {
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                            .title(R.string.nk_not_enough)
                            .positiveText(R.string.nk_recharge)
                            .negativeText(R.string.cancel)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    startActivity(new Intent(context, RechargeActivity.class));
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            });
                    MaterialDialog dialog = builder.build();
                    dialog.show();
                    return;
                }
                if (!is_call_start) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            roomMain.Start(Integer.parseInt((String) VMSPUtil.get(getActivity(), AppConstant.USERID, "0")),
                                    StringUtil.getMD5((String) VMSPUtil.get(getActivity(), AppConstant.PASSWORD, "0")), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_POOL_CONNECT_PORT);
                        }
                    }).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            roomMain.getRoom().getChannel().SendUserLinkRequest();//男性的话  开启 服务 直接默认发起配对请求
                        }
                    }, 1000);
                    //在服务中开启配对
                    rippleView.startRippleAnimation();
                } else {
                    roomMain.getRoom().getChannel().SendUserDisLinkRequest();
                    roomMain.getRoom().getChannel().SendKickOut();
                    roomMain.getRoom().getChannel().Close();
                    rippleView.stopRippleAnimation();
                }
                is_call_start = !is_call_start;
                break;
            case R.id.tv_home_action1:
                intent = new Intent(context, HomeListActivity.class);
                intent.putExtra(AppConstant.TYPE, "在线");
                startActivity(intent);
                break;
            case R.id.tv_home_action2:
                intent = new Intent(context, HomeListBigPicActivity.class);
                intent.putExtra(AppConstant.TYPE, "女神");
                startActivity(intent);
                break;
            case R.id.tv_home_action3:
                intent = new Intent(context, HomeListActivity.class);
                intent.putExtra(AppConstant.TYPE, "新人");
                startActivity(intent);
                break;
            case R.id.tv_home_action4:
                intent = new Intent(context, CircleHotActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_action5:
                intent = new Intent(context, HomeListActivity.class);
                intent.putExtra(AppConstant.TYPE, "附近");
                startActivity(intent);
                break;
            case R.id.iv_action:
                startActivity(new Intent(context, SearUserActivity.class));
                break;
            case R.id.btn_home_female_start:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.Start(Integer.parseInt((String) VMSPUtil.get(getActivity(), AppConstant.USERID, "0")),
                                StringUtil.getMD5((String) VMSPUtil.get(getActivity(), AppConstant.PASSWORD, "0")), AppConstant.BASE_CONNECT_IP, AppConstant.BASE_POOL_CONNECT_PORT);
                    }
                }).start();
                btnHomeFemaleStart.setBackgroundResource(R.drawable.ic_home_female_bg_select);
                btnHomeFemaleStop.setBackgroundResource(R.drawable.ic_home_female_bg);
                is_female_control_start = true;
                break;
            case R.id.btn_home_female_stop:
                btnHomeFemaleStart.setBackgroundResource(R.drawable.ic_home_female_bg);
                btnHomeFemaleStop.setBackgroundResource(R.drawable.ic_home_female_bg_select);
                is_female_control_start = false;
                roomMain.getRoom().getChannel().SendKickOut();
                roomMain.getRoom().getChannel().Close();
                break;
            case R.id.iv_home_goto_list_nvshen:
                intent = new Intent(context, HomeListBigPicActivity.class);
                intent.putExtra(AppConstant.TYPE, "女神");
                startActivity(intent);
                break;
            case R.id.iv_home_goto_list_tuhao:
                intent = new Intent(context, HomeListBigPicActivity.class);
                intent.putExtra(AppConstant.TYPE, "土豪");
                startActivity(intent);
                break;
            case R.id.iv_home_goto_list_song:
                intent = new Intent(context, HomeListBigPicActivity.class);
                intent.putExtra(AppConstant.TYPE, "超能送");
                startActivity(intent);
                break;
            case R.id.iv_home_goto_list_liao:
                intent = new Intent(context, HomeListBigPicActivity.class);
                intent.putExtra(AppConstant.TYPE, "超能聊");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (roomMain.getRoom() != null) {
            roomMain.getRoom().getChannel().SendKickOut();
            roomMain.getRoom().getChannel().Close();
        }
        super.onDestroy();
    }


}
