package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,R.string.feed_back);
        setText(tvSubmit,R.string.submit);
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_submit:
                String content = etFeedbackContent.getText().toString().trim();
                if (StringUtil.isEmptyandnull(content)){
                    ToastUtil.show(context,R.string.no_content);
                    return;
                }
                //反馈信息
                OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_FEEDBACK)
                        .tag(this)
                        .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                        .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                        .params("creason",content)
                        .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                            @Override
                            public void onSuccess(Response<PublishUpLoadEntity> response) {
                                if (response.body().getStatus().equals("success")) {
                                    ToastUtil.show(context, context.getString(R.string.success));
                                    finish();
                                }else {
                                    ToastUtil.show(context, context.getString(R.string.fail));
                                }
                            }

                            @Override
                            public void onError(Response<PublishUpLoadEntity> response) {
                                super.onError(response);
                            }
                        });
                break;
        }
    }
}
