package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.CircleListSelfAdapter;
import com.fubang.video.adapter.NotifyAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.CircleListEntity;
import com.fubang.video.entity.NoticeEntity;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/7/25.
 */
public class NotifyActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rv_home_list_rank)
    RecyclerView rvHomeListRank;
    @BindView(R.id.srl_rank)
    SwipeRefreshLayout srlRank;
    private BaseQuickAdapter circleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);
        initview();
        initdate();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, R.string.notify_list);
        srlRank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                date_type = 1;
                page = 1;
                initdate();
            }
        });
        //=========================recycleview
        circleAdapter = new NotifyAdapter(R.layout.item_notify_list, list_circle);
        rvHomeListRank.setLayoutManager(new GridLayoutManager(context, 1));
        circleAdapter.openLoadAnimation();
        circleAdapter.bindToRecyclerView(rvHomeListRank);
        circleAdapter.setEmptyView(R.layout.empty_view);
        rvHomeListRank.setAdapter(circleAdapter);
        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        circleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                date_type = 2;
                initdate();
            }
        });

//        //水平分割线
//        rvHomeListRank.addItemDecoration(new DividerItemDecoration(
//                context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.gray_c)));
        rvHomeListRank.smoothScrollToPosition(0);
        //=========================recycleview配置结束
    }

    private List<NoticeEntity.InfoBean.DataListBean> list_circle = new ArrayList<>();
    private int page = 1;
    private int count = 10;
    private int date_type = 0;//0 首次加载数据  1、下拉刷新  2、上拉加载

    private void initdate() {
        OkGo.<NoticeEntity>post(AppConstant.BASE_URL + AppConstant.URL_NOTICE)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .execute(new JsonCallBack<NoticeEntity>(NoticeEntity.class) {
                    @Override
                    public void onSuccess(Response<NoticeEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            srlRank.setRefreshing(false);
                            if (date_type == 0) {
                                KLog.e("111");
                                circleAdapter.setEnableLoadMore(true);
                                list_circle.clear();
                                list_circle = response.body().getInfo().getData_list();
                                circleAdapter.setNewData(list_circle);
                                if (list_circle.size() < 10)
                                    circleAdapter.loadMoreEnd();
                            } else if (date_type == 1) {
                                circleAdapter.setEnableLoadMore(true);
                                list_circle.clear();
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
                        }
                    }

                    @Override
                    public void onError(Response<NoticeEntity> response) {
                        super.onError(response);
                        srlRank.setRefreshing(false);
                        circleAdapter.loadMoreComplete();
                        circleAdapter.loadMoreEnd();
                    }
                });
    }

}
