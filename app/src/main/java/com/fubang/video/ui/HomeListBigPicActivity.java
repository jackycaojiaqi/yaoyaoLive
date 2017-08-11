package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.RankListAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.ChaoSongEntity;
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
import butterknife.OnClick;

/**
 * Created by jacky on 2017/8/7.
 */
public class HomeListBigPicActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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


    @BindView(R.id.rv_home_list_rank)
    RecyclerView rvHomeListRank;
//    @BindView(R.id.sv_rank)
//    ScrollView scrollView;

    @BindView(R.id.srl_rank)
    SwipeRefreshLayout srlRank;

    private String type;
    private List<ChaoSongEntity.InfoBean.DataListBean> list_circle = new ArrayList<>();
    private List<ChaoSongEntity.InfoBean.DataListBean> list_circle_top_three = new ArrayList<>();
    private int page = 1;
    private int count = 10;
    private int date_type = 0;//0 首次加载数据  1、下拉刷新  2、上拉加载
    private BaseQuickAdapter circleAdapter;
    private String type_name;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_homelist_rank);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra(AppConstant.TYPE);
        if (type.equals("土豪")) {
            type_name = "tu_hao";
            url = AppConstant.BASE_URL + AppConstant.URL_LIST_MAIN;
        } else if (type.equals("女神")) {
            type_name = "nv_sheng";
            url = AppConstant.BASE_URL + AppConstant.URL_LIST_MAIN;
        } else if (type.equals("超能送")) {
            type_name = "song";
            url = AppConstant.BASE_URL + AppConstant.URL_LIST_SONG;
        } else if (type.equals("超能聊")) {
            type_name = "liao";
            url = AppConstant.BASE_URL + AppConstant.URL_LIST_LIAO;
        }
        initview();
        initdate();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, type);
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
        RelativeLayout rootView = new RelativeLayout(this);
        View view_head = LayoutInflater.from(this).inflate(R.layout.layout_rank_head, rootView);
        circleAdapter = new RankListAdapter(R.layout.item_home_list_rank, list_circle);
        rvHomeListRank.setLayoutManager(new GridLayoutManager(context, 1));
        circleAdapter.openLoadAnimation();
        circleAdapter.bindToRecyclerView(rvHomeListRank);
        circleAdapter.setEmptyView(R.layout.empty_view);
        rvHomeListRank.setAdapter(circleAdapter);
        initHeadView(view_head);
        circleAdapter.addHeaderView(view_head);
        //水平分割线
        rvHomeListRank.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL_LIST, 10, getResources().getColor(R.color.gray_c)));
        rvHomeListRank.smoothScrollToPosition(0);
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

    private void initHeadView(View view_head) {
        ivRankOnePic = (ImageView) view_head.findViewById(R.id.iv_rank_one_pic);
        ivRankTwoPic = (ImageView) view_head.findViewById(R.id.iv_rank_two_pic);
        ivRankThreePic = (ImageView) view_head.findViewById(R.id.iv_rank_three_pic);

        tvRankOneName = (TextView) view_head.findViewById(R.id.tv_rank_one_name);
        tvRankTwoName = (TextView) view_head.findViewById(R.id.tv_rank_two_name);
        tvRankThreeName = (TextView) view_head.findViewById(R.id.tv_rank_three_name);

        tvRankOneGender = (ImageView) view_head.findViewById(R.id.tv_rank_one_gender);
        tvRankTwoGender = (ImageView) view_head.findViewById(R.id.tv_rank_two_gender);
        tvRankThreeGender = (ImageView) view_head.findViewById(R.id.tv_rank_three_gender);

        tvRankOneCity = (TextView) view_head.findViewById(R.id.tv_rank_one_city);
        tvRankTwoCity = (TextView) view_head.findViewById(R.id.tv_rank_two_city);
        tvRankThreeCity = (TextView) view_head.findViewById(R.id.tv_rank_three_city);

        tvRankOneAge = (TextView) view_head.findViewById(R.id.tv_rank_one_age);
        tvRankTwoAge = (TextView) view_head.findViewById(R.id.tv_rank_two_age);
        tvRankThreeAge = (TextView) view_head.findViewById(R.id.tv_rank_three_age);

        rllRankOne = (RelativeLayout) view_head.findViewById(R.id.rll_rank_one);
        rllRankTwo = (RelativeLayout) view_head.findViewById(R.id.rll_rank_two);
        rllRankThree = (RelativeLayout) view_head.findViewById(R.id.rll_rank_three);
        rllRankThree.setOnClickListener(this);
        rllRankTwo.setOnClickListener(this);
        rllRankOne.setOnClickListener(this);
    }

    private void initdate() {
        String gender = (String) VMSPUtil.get(context, AppConstant.GENDER, "");
        Map<String, String> params = new HashMap<>();
        if (type_name.equals("song") || type_name.equals("liao")) {
//            params.put("ngender", gender.equals("0") ? "1" : "0");
        } else {
            params.put("type", type_name);
        }
        OkGo.<ChaoSongEntity>post(url)
                .tag(this)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("page", page)
                .params("count", count)
                .params(params)
                .execute(new JsonCallBack<ChaoSongEntity>(ChaoSongEntity.class) {
                    @Override
                    public void onSuccess(Response<ChaoSongEntity> response) {
                        srlRank.setRefreshing(false);
                        if (response.body().getStatus().equals("success")) {
                            if (date_type == 0) {
                                circleAdapter.setEnableLoadMore(true);
                                list_circle.clear();
                                list_circle_top_three.clear();
                                list_circle = response.body().getInfo().getData_list();
                                if (list_circle.size() > 0) {
                                    setTopOneView(0);
                                }
                                if (list_circle.size() > 1) {
                                    setTopTwoView(1);
                                }
                                if (list_circle.size() > 2) {
                                    setTopThreeView(2);
                                }
                                if (list_circle.size() > 3) {
                                    //将前三保存在另一个list
                                    list_circle_top_three.add(list_circle.get(0));
                                    list_circle_top_three.add(list_circle.get(1));
                                    list_circle_top_three.add(list_circle.get(2));
                                    //去掉前3
                                    list_circle.remove(0);
                                    list_circle.remove(0);
                                    list_circle.remove(0);
                                }

                                circleAdapter.setNewData(list_circle);
                            } else if (date_type == 1) {
                                list_circle.clear();
                                list_circle_top_three.clear();
                                circleAdapter.setEnableLoadMore(true);
                                list_circle = response.body().getInfo().getData_list();
                                if (list_circle.size() > 0) {
                                    setTopOneView(0);
                                }
                                if (list_circle.size() > 1) {
                                    setTopTwoView(1);
                                }
                                if (list_circle.size() > 2) {
                                    setTopThreeView(2);
                                }
                                if (list_circle.size() > 3) {
                                    list_circle_top_three.add(list_circle.get(0));
                                    list_circle_top_three.add(list_circle.get(1));
                                    list_circle_top_three.add(list_circle.get(2));
                                    list_circle.remove(0);
                                    list_circle.remove(0);
                                    list_circle.remove(0);
                                }
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
                    public void onError(Response<ChaoSongEntity> response) {
                        super.onError(response);
                        srlRank.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        KLog.e(v.getId() + "--");
        Intent intent;
        switch (v.getId()) {
            case R.id.rll_rank_one:
                if (list_circle_top_three.size() > 0) {
                    intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra(AppConstant.USERID, list_circle_top_three.get(0).getNuserid());
                    if (list_circle_top_three.get(0).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                        intent.putExtra(AppConstant.TYPE, 1);
                    } else {
                        intent.putExtra(AppConstant.TYPE, 0);
                    }
                    startActivity(intent);
                }
                break;
            case R.id.rll_rank_two:
                if (list_circle_top_three.size() > 1) {
                    intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra(AppConstant.USERID, list_circle_top_three.get(1).getNuserid());
                    if (list_circle_top_three.get(1).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                        intent.putExtra(AppConstant.TYPE, 1);
                    } else {
                        intent.putExtra(AppConstant.TYPE, 0);
                    }
                    startActivity(intent);
                }
                break;
            case R.id.rll_rank_three:
                if (list_circle_top_three.size() > 2) {
                    intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra(AppConstant.USERID, list_circle_top_three.get(2).getNuserid());
                    if (list_circle_top_three.get(2).getNuserid().equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                        intent.putExtra(AppConstant.TYPE, 1);
                    } else {
                        intent.putExtra(AppConstant.TYPE, 0);
                    }
                    startActivity(intent);
                }
                break;
        }
    }

    private void setTopOneView(int position) {
        if (!StringUtil.isEmptyandnull(list_circle.get(position).getCphoto())) {
            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + list_circle.get(position).getCphoto(), ivRankOnePic);
        }
        tvRankOneName.setText(list_circle.get(position).getCalias());
        tvRankOneAge.setText(list_circle.get(position).getNage() != null ? list_circle.get(position).getNage() : context.getString(R.string.null_string));
        tvRankOneCity.setText(list_circle.get(position).getCcity() != null ? list_circle.get(position).getCcity() :context.getString(R.string.null_string));
        if (list_circle.get(position).getNgender().equals("0")) {
            tvRankOneGender.setImageResource(R.drawable.ic_home_female);
        } else {
            tvRankOneGender.setImageResource(R.drawable.ic_home_male);
        }
    }

    private void setTopTwoView(int position) {
        if (!StringUtil.isEmptyandnull(list_circle.get(position).getCphoto())) {
            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + list_circle.get(position).getCphoto(), ivRankTwoPic);
        }
        tvRankTwoName.setText(list_circle.get(position).getCalias());
        tvRankTwoAge.setText(list_circle.get(position).getNage() != null ? list_circle.get(position).getNage() :context.getString(R.string.null_string));
        tvRankTwoCity.setText(list_circle.get(position).getCcity() != null ? list_circle.get(position).getCcity() : context.getString(R.string.null_string));
        if (list_circle.get(position).getNgender().equals("0")) {
            tvRankTwoGender.setImageResource(R.drawable.ic_home_female);
        } else {
            tvRankTwoGender.setImageResource(R.drawable.ic_home_male);
        }
    }

    private void setTopThreeView(int position) {
        if (!StringUtil.isEmptyandnull(list_circle.get(position).getCphoto())) {
            ImagUtil.setnoerror(context, AppConstant.BASE_IMG_URL + list_circle.get(position).getCphoto(), ivRankThreePic);
        }
        tvRankThreeName.setText(list_circle.get(position).getCalias());
        tvRankThreeAge.setText(list_circle.get(position).getNage() != null ? list_circle.get(position).getNage() : context.getString(R.string.null_string));
        tvRankThreeCity.setText(list_circle.get(position).getCcity() != null ? list_circle.get(position).getCcity() :context.getString(R.string.null_string));
        if (list_circle.get(position).getNgender().equals("0")) {
            tvRankThreeGender.setImageResource(R.drawable.ic_home_female);
        } else {
            tvRankThreeGender.setImageResource(R.drawable.ic_home_male);
        }
    }
}
