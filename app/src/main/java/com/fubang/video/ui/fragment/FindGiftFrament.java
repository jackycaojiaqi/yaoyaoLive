package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.RankGiftAdapter;
import com.fubang.video.adapter.RankListAdapter;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.ChaoSongEntity;
import com.fubang.video.entity.GiftRankEntity;
import com.fubang.video.ui.LoginActivity;
import com.fubang.video.ui.UserInfoActivity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jacky on 2017/7/26.
 */
public class FindGiftFrament extends BaseFragment {
    @BindView(R.id.rv_home_list_rank)
    RecyclerView rvHomeListRank;
    @BindView(R.id.srl_rank)
    SwipeRefreshLayout srlRank;
    Unbinder unbinder;
    ImageView ivRankOnePic;
    TextView tvRankOneName;
    ImageView tvRankOneGender;
    TextView tvRankOneAge;
    TextView tvRankOneCity;
    RelativeLayout rllRankOne;
    ImageView ivRankTwoPic;
    TextView tvRankTwoName;
    ImageView tvRankTwoGender;
    TextView tvRankTwoAge;
    TextView tvRankTwoCity;
    RelativeLayout rllRankTwo;
    ImageView ivRankThreePic;
    TextView tvRankThreeName;
    ImageView tvRankThreeGender;
    TextView tvRankThreeAge;
    TextView tvRankThreeCity;
    RelativeLayout rllRankThree;
    private List<GiftRankEntity.InfoBean.DataListBean> list_circle = new ArrayList<>();
    private List<GiftRankEntity.InfoBean.DataListBean> list_circle_top_three = new ArrayList<>();
    private int page = 1;
    private int count = 10;
    private int date_type = 0;//0 首次加载数据  1、下拉刷新  2、上拉加载
    private BaseQuickAdapter circleAdapter;
    private String url;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_gift, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initview();
        initdate();
    }


    private void initview() {
        srlRank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                date_type = 1;
                page = 1;
                initdate();
            }
        });
        srlRank.setProgressViewOffset(true, 50, 150);
        //=========================recycleview配置
        circleAdapter = new RankGiftAdapter(R.layout.item_home_list_rank_gift, list_circle);
        rvHomeListRank.setLayoutManager(new GridLayoutManager(context, 1));
        circleAdapter.openLoadAnimation();
        circleAdapter.bindToRecyclerView(rvHomeListRank);
        circleAdapter.setEmptyView(R.layout.empty_view);
        rvHomeListRank.setAdapter(circleAdapter);
        //水平分割线
        rvHomeListRank.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 2, getResources().getColor(R.color.gray_c)));
        rvHomeListRank.smoothScrollToPosition(0);
        circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent;
                switch (view.getId()){
                    case R.id.iv_gift_rank_sned_pic:
                         intent = new Intent(context, UserInfoActivity.class);
                        intent.putExtra(AppConstant.USERID, list_circle.get(position).getNuserid());
                        if (list_circle.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                            intent.putExtra(AppConstant.TYPE, 1);
                        } else {
                            intent.putExtra(AppConstant.TYPE, 0);
                        }
                        startActivity(intent);
                        break;
                    case R.id.iv_gift_rank_to_pic:
                         intent = new Intent(context, UserInfoActivity.class);
                        intent.putExtra(AppConstant.USERID, list_circle.get(position).getTonuserid());
                        if (list_circle.get(position).getTonuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                            intent.putExtra(AppConstant.TYPE, 1);
                        } else {
                            intent.putExtra(AppConstant.TYPE, 0);
                        }
                        startActivity(intent);
                        break;
                }
            }
        });
        circleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                date_type = 2;
                initdate();
            }
        }); //=========================recycleview配置结束
    }


    private void initdate() {
        url = AppConstant.BASE_URL + AppConstant.URL_LIWU;
        OkGo.<GiftRankEntity>post(url)
                .tag(getActivity())
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .execute(new JsonCallBack<GiftRankEntity>(GiftRankEntity.class) {
                    @Override
                    public void onSuccess(Response<GiftRankEntity> response) {
                        srlRank.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            if (date_type == 0) {
                                circleAdapter.setEnableLoadMore(true);
                                list_circle.clear();
                                list_circle_top_three.clear();
                                list_circle = response.body().getInfo().getData_list();


                                circleAdapter.setNewData(list_circle);
                            } else if (date_type == 1) {
                                list_circle.clear();
                                list_circle_top_three.clear();
                                circleAdapter.setEnableLoadMore(true);
                                list_circle = response.body().getInfo().getData_list();
                                circleAdapter.setNewData(list_circle);
                            } else if (date_type == 2) {
                                if (response.body().getInfo().getData_list().size() < 10) {//最后一页
                                    list_circle.addAll(response.body().getInfo().getData_list());
                                    circleAdapter.notifyDataSetChanged();
                                    circleAdapter.loadMoreComplete();
                                    circleAdapter.loadMoreEnd();
                                } else if (response.body().getInfo().getData_list().size() >= 10) {//不是最后一页
                                    list_circle.addAll(response.body().getInfo().getData_list());
                                    circleAdapter.notifyDataSetChanged();
                                    circleAdapter.loadMoreComplete();
                                }
                            }
                        } else {//token失效
                            startActivity(new Intent(context, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onError(Response<GiftRankEntity> response) {
                        super.onError(response);
                        srlRank.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
