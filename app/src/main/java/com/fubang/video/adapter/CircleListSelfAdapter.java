package com.fubang.video.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.entity.CircleListEntity;
import com.fubang.video.entity.CircleReviewEntity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.util.dataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/10.
 */
public class CircleListSelfAdapter extends BaseQuickAdapter<CircleListEntity.InfoBean, BaseViewHolder> {
    private List<CircleListEntity.InfoBean> list;
    private Bitmap bitmap;
    private JCVideoPlayerStandard videoPlayerStandard;
    private BaseQuickAdapter reviewAdapter;
    private List<CircleReviewEntity> list_review = new ArrayList<>();
    private boolean is_self = false;

    public CircleListSelfAdapter(int layoutResId, List data, boolean is_self) {
        super(layoutResId, data);
        list = data;
        this.is_self = is_self;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CircleListEntity.InfoBean item) {

        if (!StringUtil.isEmptyandnull(item.getCphoto()))//头像
            ImagUtil.set(mContext.getApplicationContext(), AppConstant.BASE_IMG_URL + item.getCphoto(), helper.getView(R.id.iv_circle_list_pic));
        if (!StringUtil.isEmptyandnull(item.getNgender()))//性别
        {
            if (item.getNgender().endsWith("1")) {//男性
                helper.setImageResource(R.id.iv_circle_list_gender, R.drawable.ic_register_male_checked);
            } else {//女性
                helper.setImageResource(R.id.iv_circle_list_gender, R.drawable.ic_register_female_checked);
            }
        }

        //计算多久前发布
        String interval = dataUtils.getInterval(Long.parseLong(item.getDtime1()), System.currentTimeMillis() / 1000);
        //是否限时免费
        if (is_self) {
            helper.setText(R.id.tv_circle_list_state, mContext.getString(R.string.time_limit_open));
            helper.getView(R.id.btn_circle_list_state).setVisibility(View.GONE);
        } else if (interval.contains("天")) {
            helper.setText(R.id.tv_circle_list_state, mContext.getString(R.string.send_flow_to_see_video));
            if (item.getNumber().equals("0")) {  //没有送过花
                helper.getView(R.id.btn_circle_list_state).setVisibility(View.VISIBLE);
                helper.getView(R.id.btn_circle_list_state).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(mContext, mContext.getString(R.string.you_should_send_flower_first));
                    }
                });
            } else {//送过花
                helper.getView(R.id.btn_circle_list_state).setVisibility(View.GONE);
            }
        } else {
            helper.setText(R.id.tv_circle_list_state,  mContext.getString(R.string.time_limit_open));
            helper.getView(R.id.btn_circle_list_state).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_circle_list_name, item.getCalias() + "")//名字
                .setText(R.id.tv_circle_list_age, StringUtil.isEmptyandnull(item.getNage()) ? mContext.getString(R.string.null_string) : item.getNage() + "")//年龄
                .setText(R.id.tv_circle_list_timeslong, interval + mContext.getString(R.string.time_before))//多久前发布
                .setText(R.id.tv_circle_list_city, StringUtil.isEmptyandnull(item.getCcity()) ?  mContext.getString(R.string.null_string) : item.getCcity() + "")//城市
                .setText(R.id.tv_circle_list_see_num,  mContext.getString(R.string.see_times) + item.getNscan())//浏览次数
                .setText(R.id.tv_circle_list_content, item.getCcontent() + "")//内容
                .setText(R.id.tv_circlr_review_num, item.getNreview() + "")//评论次数
                .setText(R.id.tv_circle_flower_num, item.getNflowercount() + "")//收到鲜花量
                .addOnClickListener(R.id.iv_circle_list_pic)
                .addOnClickListener(R.id.ll_circle_list_send_flower)
        ;
        videoPlayerStandard = helper.getView(R.id.videoplayer);
        videoPlayerStandard.ACTION_BAR_EXIST = false;
        videoPlayerStandard.TOOL_BAR_EXIST = false;
        videoPlayerStandard.setUp(item.getCvideo_mp4()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, item.getCalias());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = Glide.with(mContext)
                            .load(AppConstant.BASE_IMG_URL + item.getCvideophoto())
                            .asBitmap() //必须
                            .centerCrop()
                            .into(400, 300)
                            .get();
                    mHandler.sendEmptyMessage(MSG_SUCCESS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //评论列表只有3条---自己拼接数据
        //=========================recycleview
        list_review.clear();
        if (item.getReview_list().getCreview().size() > 0) {
            for (int i = 0; i < item.getReview_list().getCreview().size(); i++) {
                CircleReviewEntity review_entity = new CircleReviewEntity();
                review_entity.setCreview(item.getReview_list().getCreview().get(i));
                if (item.getReview_list().getPcalias().size() >= i && item.getReview_list().getPcalias().size() > 0)
                    review_entity.setPcalias(item.getReview_list().getPcalias().get(i));

                if (item.getReview_list().getPnuserid().size() >= i && item.getReview_list().getPnuserid().size() > 0)
                    review_entity.setPnuserid(item.getReview_list().getPnuserid().get(i));

                if (item.getReview_list().getHcalias().size() >= i && item.getReview_list().getHcalias().size() > 0)
                    review_entity.setHcalias(item.getReview_list().getHcalias().get(i));

                if (item.getReview_list().getHnuserid().size() >= i && item.getReview_list().getHnuserid().size() > 0)
                    review_entity.setHnuserid(item.getReview_list().getHnuserid().get(i));
                list_review.add(review_entity);
            }
        }
        RecyclerView rv_review = helper.getView(R.id.rv_circle_review);
        reviewAdapter = new CircleReviewAdapter(R.layout.item_circle_list_review, list_review);
        rv_review.setLayoutManager(new GridLayoutManager(mContext, 1));
        reviewAdapter.setEnableLoadMore(false);
        reviewAdapter.bindToRecyclerView(rv_review);
        reviewAdapter.setEmptyView(R.layout.empty_view);
        rv_review.setAdapter(reviewAdapter);
    }

    private final int MSG_SUCCESS = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case MSG_SUCCESS:
                    videoPlayerStandard.thumbImageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };
}
