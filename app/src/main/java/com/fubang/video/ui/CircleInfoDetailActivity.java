package com.fubang.video.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.adapter.CircleInfoReviewAdapter;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.CircleInfoEntity;
import com.fubang.video.entity.ReviewEntity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.util.dataUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by jacky on 2017/7/21.
 */
public class CircleInfoDetailActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_circle_info_pic)
    CircleImageView ivCircleInfoPic;
    @BindView(R.id.tv_circle_info_name)
    TextView tvCircleInfoName;
    @BindView(R.id.iv_circle_info_gender)
    ImageView ivCircleInfoGender;
    @BindView(R.id.tv_circle_info_age)
    TextView tvCircleInfoAge;
    @BindView(R.id.tv_circle_info_city)
    TextView tvCircleInfoCity;
    @BindView(R.id.tv_circle_info_timeslong)
    TextView tvCircleInfoTimeslong;
    @BindView(R.id.tv_circle_info_see_num)
    TextView tvCircleInfoSeeNum;
    @BindView(R.id.tv_circle_info_content)
    TextView tvCircleInfoContent;
    @BindView(R.id.tv_circlr_review_num)
    TextView tvCirclrReviewNum;
    @BindView(R.id.tv_circle_flower_num)
    TextView tvCircleFlowerNum;
    @BindView(R.id.ll_circle_send_flower)
    LinearLayout llCircleSendFlower;

    @BindView(R.id.rv_circle_info_review)
    RecyclerView rvCircleInfoReview;
    @BindView(R.id.et_circle_info)
    EditText etCircleInfo;
    @BindView(R.id.tv_circle_detail_state)
    TextView tvCircleDetailState;
    @BindView(R.id.btn_circle_detail_state)
    Button btnCircleDetailState;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private Bitmap bitmap;
    private int page = 1;
    private int count = 10;
    private String nid;//该条朋友圈的id
    private String sendid;//发朋友圈的人id
    private BaseQuickAdapter reviewAdapter;
    private List<CircleInfoEntity.InfoBean.ReviewListBean> list_review = new ArrayList<>();
    private String nreplyid;//被回复人id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleinfodetail);
        ButterKnife.bind(this);
        nid = getIntent().getStringExtra(AppConstant.OBJECT);
        initview();
        initdate();
    }

    private void initdate() {
        OkGo.<CircleInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_DETAIL)
                .tag(this)
                .params("self_nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("nid", nid)
                .params("page", page)
                .params("count", count)
                .execute(new JsonCallBack<CircleInfoEntity>(CircleInfoEntity.class) {
                    @Override
                    public void onSuccess(final Response<CircleInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getCphoto()))//头像
                                ImagUtil.set(getApplicationContext(), AppConstant.BASE_IMG_URL + response.body().getInfo().getCphoto(), ivCircleInfoPic);
                            if (!StringUtil.isEmptyandnull(response.body().getInfo().getNgender()))//性别
                            {
                                if (response.body().getInfo().getNgender().endsWith("0")) {//男性
                                    ivCircleInfoGender.setImageResource(R.drawable.ic_register_male_checked);
                                } else {//女性
                                    ivCircleInfoGender.setImageResource(R.drawable.ic_register_female_checked);
                                }
                            }
                            //计算多久前发布
                            String interval = dataUtils.getInterval(Long.parseLong(response.body().getInfo().getDtime1()), System.currentTimeMillis() / 1000);
                            //是否限时免费
                            if (interval.contains("天")) {
                                tvCircleDetailState.setText("送花看视频");
                                if (response.body().getInfo().getNumber().equals("0")) {  //没有送过花
                                    btnCircleDetailState.setVisibility(View.VISIBLE);
                                    btnCircleDetailState.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ToastUtil.show(context, "需要先送花再观看！");
                                        }
                                    });
                                } else {//送过花
                                    btnCircleDetailState.setVisibility(View.GONE);
                                }
                            } else {
                                tvCircleDetailState.setText("限时公开中");
                                btnCircleDetailState.setVisibility(View.GONE);
                            }
                            tvCircleInfoName.setText(response.body().getInfo().getCalias() + "");//名字
                            tvCircleInfoAge.setText(StringUtil.isEmptyandnull(response.body().getInfo().getNage()) ? "未知" : response.body().getInfo().getNage() + "");//年龄
                            tvCircleInfoTimeslong.setText(interval + "前发布");//多久前发布
                            tvCircleInfoCity.setText(StringUtil.isEmptyandnull(response.body().getInfo().getCcity()) ? "未知" : response.body().getInfo().getCcity() + "");//城市
                            tvCircleInfoSeeNum.setText("浏览次数" + response.body().getInfo().getNscan());//浏览次数
                            tvCircleInfoContent.setText(response.body().getInfo().getCcontent() + "");//内容
                            tvCirclrReviewNum.setText(response.body().getInfo().getNreview() + "");//评论次数
                            tvCircleFlowerNum.setText(response.body().getInfo().getNflowercount() + "");//收到鲜花量
                            nreplyid = response.body().getInfo().getNuserid();
                            sendid = response.body().getInfo().getNuserid();
                            nid = response.body().getInfo().getNid();
                            //评论列表==============
                            list_review = response.body().getInfo().getReview_list();
                            reviewAdapter = new CircleInfoReviewAdapter(R.layout.item_circle_list_review, list_review);
                            rvCircleInfoReview.setLayoutManager(new GridLayoutManager(context, 1));
                            reviewAdapter.setEnableLoadMore(true);
                            reviewAdapter.bindToRecyclerView(rvCircleInfoReview);
                            reviewAdapter.setEmptyView(R.layout.empty_view);
                            rvCircleInfoReview.setAdapter(reviewAdapter);
                            reviewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    etCircleInfo.setHint("回复：" + list_review.get(position).getPcalias());
                                    //不是回复其他人
                                    if (!list_review.get(position).getNbuddyid().equals("0") && !StringUtil.isEmptyandnull(list_review.get(position).getNbuddyid())) {
                                        nreplyid = list_review.get(position).getNbuddyid();
                                    }

                                    showKeyboard(etCircleInfo);
                                }
                            });

                            jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
                            jcVideoPlayerStandard.setUp(response.body().getInfo().getCvideo_mp4()
                                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, response.body().getInfo().getCalias());
                            jcVideoPlayerStandard.ACTION_BAR_EXIST = false;
                            jcVideoPlayerStandard.TOOL_BAR_EXIST = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        bitmap = Glide.with(context)
                                                .load(AppConstant.BASE_IMG_URL + response.body().getInfo().getCvideophoto())
                                                .asBitmap() //必须
                                                .centerCrop()
                                                .into(500, 500)
                                                .get();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                jcVideoPlayerStandard.thumbImageView.setImageBitmap(bitmap);
                                            }
                                        });
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }

                    @Override
                    public void onError(Response<CircleInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "详情");
        etCircleInfo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(etCircleInfo.getText().toString().trim())) {
                        KLog.e("发送");
                        send_review_content(etCircleInfo.getText().toString().trim());
                    } else {
                        ToastUtil.show(context, "内容不能为空");
                    }
                    closeKeyboard();
                    etCircleInfo.setText("");
                    etCircleInfo.setHint("请输入评论");
                    return true;
                }
                return false;
            }
        });


    }

    /**
     * 发送回复
     *
     * @param content
     */
    private void send_review_content(final String content) {
        OkGo.<ReviewEntity>post(AppConstant.BASE_URL + AppConstant.URL_LIFE_REVIEW)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("nid", nid)
                .params("nbuddyid", nreplyid)
                .params("creview", content)
                .execute(new JsonCallBack<ReviewEntity>(ReviewEntity.class) {
                    @Override
                    public void onSuccess(Response<ReviewEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            initdate();
                            ToastUtil.show(context, "评论成功");
                        }
                    }

                    @Override
                    public void onError(Response<ReviewEntity> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 送鲜花
     *
     * @param
     */
    private void send_flower() {
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

    @OnClick({R.id.iv_back, R.id.ll_circle_send_flower, R.id.iv_circle_info_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.ll_circle_send_flower:
                send_flower();
                break;
            case R.id.iv_circle_info_pic:
                Intent intent = new Intent(context, UserInfoActivity.class);
                if (sendid.equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                    intent.putExtra(AppConstant.TYPE, 1);
                } else {
                    intent.putExtra(AppConstant.TYPE, 0);
                }
                intent.putExtra(AppConstant.USERID, sendid);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
