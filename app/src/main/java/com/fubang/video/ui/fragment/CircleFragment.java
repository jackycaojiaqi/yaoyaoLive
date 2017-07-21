package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.CircleListAdapter;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.CircleListEntity;
import com.fubang.video.ui.CircleInfoDetailActivity;
import com.fubang.video.ui.PublishCircleActivity;
import com.fubang.video.widget.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jacky on 2017/7/11.
 */
public class CircleFragment extends BaseFragment {
    @BindView(R.id.btn_goto_publish_circle)
    Button btnGotoPublishCircle;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rv_circle)
    RecyclerView rvCircle;
    private Context context;
    private BaseQuickAdapter circleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
        initdate();
    }

    private void initview() {
        tvTitle.setText("生活圈");
        setText(tvSubmit, "规则");
    }

    private List<CircleListEntity.InfoBean> list_circle = new ArrayList<>();
    private int page = 1;
    private int count = 10;

    private void initdate() {
        OkGo.<CircleListEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_LIST)
                .tag(this)
//                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .execute(new JsonCallBack<CircleListEntity>(CircleListEntity.class) {
                    @Override
                    public void onSuccess(Response<CircleListEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            list_circle = response.body().getInfo();
                            //=========================recycleview
                            circleAdapter = new CircleListAdapter(R.layout.item_circle_list, list_circle);
                            rvCircle.setLayoutManager(new GridLayoutManager(context, 1));
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
                            circleAdapter.bindToRecyclerView(rvCircle);
                            circleAdapter.setEmptyView(R.layout.empty_view);
                            rvCircle.setAdapter(circleAdapter);
                            //水平分割线
                            rvCircle.addItemDecoration(new DividerItemDecoration(
                                    context, DividerItemDecoration.HORIZONTAL_LIST, 10, getResources().getColor(R.color.gray_c)));
                        }
                    }

                    @Override
                    public void onError(Response<CircleListEntity> response) {
                        super.onError(response);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_goto_publish_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_goto_publish_circle:
                context.startActivity(new Intent(context, PublishCircleActivity.class));
                break;
        }
    }
}
