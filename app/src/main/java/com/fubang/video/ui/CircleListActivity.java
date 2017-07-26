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
import com.fubang.video.adapter.CircleListAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.CircleListEntity;
import com.fubang.video.entity.ReviewEntity;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/7/26.
 */
public class CircleListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_circle_list)
    RecyclerView rvCircleList;
    @BindView(R.id.srl_circle_list)
    SwipeRefreshLayout srlCircleListList;
    private String userid;
    private int page = 1;
    private int count = 10;
    private BaseQuickAdapter circleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_circle_list);
        ButterKnife.bind(this);
        userid = getIntent().getStringExtra(AppConstant.USERID);
        initview();
        initdate();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "朋友圈列表");
    }

    private List<CircleListEntity.InfoBean> list_circle = new ArrayList<>();
    private void initdate() {
        OkGo.<CircleListEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_LIST)
                .tag(this)
                .params("nuserid", userid)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .execute(new JsonCallBack<CircleListEntity>(CircleListEntity.class) {
                    @Override
                    public void onSuccess(Response<CircleListEntity> response) {
                        srlCircleListList.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            list_circle = response.body().getInfo();
                            //=========================recycleview
                            circleAdapter = new CircleListAdapter(R.layout.item_circle_list, list_circle);
                            rvCircleList.setLayoutManager(new GridLayoutManager(context, 1));
                            circleAdapter.openLoadAnimation();
                            circleAdapter.setAutoLoadMoreSize(5);
                            circleAdapter.setEnableLoadMore(true);
                            circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(context, CircleInfoDetailActivity.class);
                                    intent.putExtra(AppConstant.OBJECT, list_circle.get(position).getNid());
                                    startActivity(intent);
                                }
                            });
                            circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    switch (view.getId()) {
                                        case R.id.ll_circle_list_send_flower:
                                            send_flower(list_circle.get(position).getNid(), list_circle.get(position).getNuserid());
                                            break;
                                        case R.id.iv_circle_list_pic:
                                            Intent intent = new Intent(context, UserInfoActivity.class);
                                            if (list_circle.get(position).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                                intent.putExtra(AppConstant.TYPE, 1);
                                            } else {
                                                intent.putExtra(AppConstant.TYPE, 0);
                                            }
                                            intent.putExtra(AppConstant.USERID, list_circle.get(position).getNuserid());
                                            startActivity(intent);
                                            break;
                                    }

                                }
                            });
                            circleAdapter.bindToRecyclerView(rvCircleList);
                            circleAdapter.setEmptyView(R.layout.empty_view);
                            rvCircleList.setAdapter(circleAdapter);
                            //水平分割线
                            rvCircleList.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.gray_c)));
                            rvCircleList.smoothScrollToPosition(0);
                        }
                    }

                    @Override
                    public void onError(Response<CircleListEntity> response) {
                        super.onError(response);
                        srlCircleListList.setRefreshing(false);
                    }
                });
    }

    /**
     * 送鲜花
     *
     * @param nid    朋友圈id
     * @param sendid 朋友圈主人（发送者）
     */
    private void send_flower(String nid, String sendid) {
        OkGo.<ReviewEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_FLOWER)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("nid", nid)
                .params("nbuddyid", sendid)
                .execute(new JsonCallBack<ReviewEntity>(ReviewEntity.class) {
                    @Override
                    public void onSuccess(Response<ReviewEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            initdate();
                            ToastUtil.show(context, "送鲜花成功");
                        }
                    }

                    @Override
                    public void onError(Response<ReviewEntity> response) {
                        super.onError(response);
                    }
                });
    }
}
