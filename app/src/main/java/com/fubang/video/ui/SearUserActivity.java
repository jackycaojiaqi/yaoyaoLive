package com.fubang.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.fubang.video.widget.ClearableEditText;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/24.
 */
public class SearUserActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    ClearableEditText etSearch;
    @BindView(R.id.btn_search_commit)
    Button btnSearchCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "ID搜索");
    }


    @OnClick({R.id.iv_back, R.id.btn_search_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search_commit:
                String content = etSearch.getText().toString().trim();
                if (StringUtil.isEmptyandnull(content)) {
                    ToastUtil.show(context, "请输入ID");
                    return;
                }
                initdate(content);
                break;
        }
    }

    private void initdate(final String content) {
        OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                .tag(this)
                .params("nuserid", content)
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<BaseInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            String Userid = response.body().getInfo().getNuserid();
                            Intent intent = new Intent(context, UserInfoActivity.class);
                            if (Userid.equals(VMSPUtil.get(context, AppConstant.USERID, ""))) {
                                intent.putExtra(AppConstant.TYPE, 1);
                            } else {
                                intent.putExtra(AppConstant.TYPE, 0);
                            }
                            intent.putExtra(AppConstant.USERID, Userid);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.show(context, "您所搜索的用户不存在");
                        }
                    }

                    @Override
                    public void onError(Response<BaseInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }
}
