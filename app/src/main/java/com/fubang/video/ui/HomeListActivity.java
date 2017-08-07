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
import com.fubang.video.adapter.HomeListAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.HomeListEntity;
import com.fubang.video.widget.DividerGridItemDecoration;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/8/7.
 */
public class HomeListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_home_list)
    RecyclerView rvHomeList;
    @BindView(R.id.srl_home_list)
    SwipeRefreshLayout srlHomeList;
    private String type;
    private List<HomeListEntity.InfoBean.DataListBean> list_circle = new ArrayList<>();
    private int page = 1;
    private int count = 10;
    private int date_type = 0;//0 首次加载数据  1、下拉刷新  2、上拉加载
    private BaseQuickAdapter circleAdapter;
    private String type_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_homelist);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra(AppConstant.TYPE);
        if (type.equals("在线")) {
            type_name = "on_line";
        } else if (type.equals("新人")) {
            type_name = "new_line";
        } else if (type.equals("土豪")) {
            type_name = "tu_hao";
        } else if (type.equals("女神")) {
            type_name = "nv_sheng";
        } else if (type.equals("话痨")) {
            type_name = "hua_lao";
        } else if (type.equals("老司机")) {
            type_name = "lao_siji";
        } else if (type.equals("附近")) {
            type_name = "fu_jin";
        }
        initview();
        initdate();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, type);
        srlHomeList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                date_type = 1;
                page = 1;
                initdate();
            }
        });
        srlHomeList.setProgressViewOffset(true, 150, 250);
        //=========================recycleview配置
        circleAdapter = new HomeListAdapter(R.layout.item_home_list, list_circle);
        rvHomeList.setLayoutManager(new GridLayoutManager(context, 2));
        circleAdapter.openLoadAnimation();
        circleAdapter.bindToRecyclerView(rvHomeList);
        circleAdapter.setEmptyView(R.layout.empty_view);
        rvHomeList.setAdapter(circleAdapter);
        //水平分割线
        rvHomeList.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.BOTH_SET, 10, getResources().getColor(R.color.gray_c)));
        rvHomeList.smoothScrollToPosition(0);
        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra(AppConstant.USERID, list_circle.get(position).getNuserid());
                if (list_circle.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                    intent.putExtra(AppConstant.TYPE, 1);
                } else {
                    intent.putExtra(AppConstant.TYPE, 0);
                }
                startActivity(intent);
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
        String gender = (String) VMSPUtil.get(context, AppConstant.GENDER, "");
        Map<String, String> params = new HashMap<>();
        if (type_name.equals("fu_jin")) {
            params.put("type", type_name);
            params.put("nlatitude", (String) VMSPUtil.get(context, AppConstant.LAT, ""));
            params.put("nlongitude", (String) VMSPUtil.get(context, AppConstant.LON, ""));
        } else {
            params.put("type", type_name);
        }

        OkGo.<HomeListEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIST_MAIN)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .params(params)
                .params("ngender", gender.equals("0") ? "1" : "0")
                .execute(new JsonCallBack<HomeListEntity>(HomeListEntity.class) {
                    @Override
                    public void onSuccess(Response<HomeListEntity> response) {
                        srlHomeList.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            if (date_type == 0) {
                                circleAdapter.setEnableLoadMore(true);
                                list_circle.clear();
                                list_circle = response.body().getInfo().getData_list();
                                circleAdapter.setNewData(list_circle);
                            } else if (date_type == 1) {
                                list_circle.clear();
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
                    public void onError(Response<HomeListEntity> response) {
                        super.onError(response);
                        srlHomeList.setRefreshing(false);
                    }
                });
    }
}
