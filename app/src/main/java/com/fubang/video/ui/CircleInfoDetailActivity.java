package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.util.AndroidBug5497Workaround;
import com.fubang.video.util.ToastUtil;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.video_circle_info)
    AliyunVodPlayerView videoCircleInfo;
    @BindView(R.id.tv_circlr_review_num)
    TextView tvCirclrReviewNum;
    @BindView(R.id.tv_circle_flower_num)
    TextView tvCircleFlowerNum;
    @BindView(R.id.rv_circle_info_review)
    RecyclerView rvCircleInfoReview;
    @BindView(R.id.et_circle_info)
    EditText etCircleInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleinfodetail);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,"详情");
        etCircleInfo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(etCircleInfo.getText().toString().trim())) {
                        KLog.e("发送");
                    } else {
                        ToastUtil.show(context, "内容不能为空");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_circle_flower_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_circle_flower_num:
                break;
        }
    }
}
