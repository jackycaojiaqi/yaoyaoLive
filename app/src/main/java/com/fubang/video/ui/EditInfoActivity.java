package com.fubang.video.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.PublishUpLoadEntity;
import com.fubang.video.util.StringUtil;
import com.fubang.video.util.ToastUtil;
import com.hanks.library.AnimateCheckBox;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/21.
 */
public class EditInfoActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.et_edit_content)
    EditText etEditContent;
    @BindView(R.id.tv_edit_tips)
    TextView tvEditTips;
    @BindView(R.id.ll_edit_type1_2)
    LinearLayout llEditType12;
    @BindView(R.id.cb_lable_1)
    AnimateCheckBox cbLable1;
    @BindView(R.id.cb_lable_2)
    AnimateCheckBox cbLable2;
    @BindView(R.id.cb_lable_3)
    AnimateCheckBox cbLable3;
    @BindView(R.id.cb_lable_4)
    AnimateCheckBox cbLable4;
    @BindView(R.id.ll_edit_type3)
    LinearLayout llEditType3;
    private int type = -1; //1 签名  2、名字  3、标签
    private String lable1 = null;
    private String lable2 = null;
    private String lable3 = null;
    private String lable4 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_editinfo);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(AppConstant.OBJECT, -1);
        initview();

    }

    private void initview() {
        back(ivBack);
        setText(tvTitle,context.getString(R.string.edit));
        setText(tvSubmit, context.getString(R.string.submit));
        if (type == 3) {
            llEditType12.setVisibility(View.GONE);
            llEditType3.setVisibility(View.VISIBLE);
            cbLable1.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        lable1 =  context.getString(R.string.has_much_money);
                    } else {
                        lable1 = null;
                    }

                }
            });
            cbLable2.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        lable2 = context.getString(R.string.liao_wuwu);
                    } else {
                        lable2 = null;
                    }

                }
            });
            cbLable3.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        lable3 = context.getString(R.string.loneliness);
                    } else {
                        lable3 = null;
                    }

                }
            });
            cbLable4.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        lable4 = context.getString(R.string.voice_kong);
                    } else {
                        lable4 = null;
                    }
                }
            });
        } else {
            llEditType12.setVisibility(View.VISIBLE);
            llEditType3.setVisibility(View.GONE);
            if (type == 1) {
                tvEditTips.setText(R.string.edit_sign);
                setText(tvTitle, R.string.edit);
            } else if (type == 2) {
                tvEditTips.setText(R.string.edit_name);
                setText(tvTitle,  R.string.edit);
            }
        }
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (type == 1 || type == 2) {
                    String content = etEditContent.getText().toString().trim();
                    if (!StringUtil.isEmptyandnull(content)) {
                        update_imag_to_server(content);
                    } else {
                        ToastUtil.show(context, context.getString(R.string.content_not_null));
                    }
                } else if (type == 3) {
                    String content = (lable1 == null ? "" : lable1 + ";") + (lable2 == null ? "" : lable2 + ";") + (lable3 == null ? "" : lable3 + ";") + (lable4 == null ? "" : lable4);
                    if (content.endsWith(";")) {
                        content = content.substring(0, content.length() - 1);
                    }
                    KLog.e(content);
                    update_imag_to_server(content);
                }

                break;
        }
    }

    /**
     * 将返回的名字、签名、标签更新到个人信息中
     */
    private void update_imag_to_server(String content) {
        Map<String, String> map = new HashMap<>();
        if (type == 1) {
            map.put("cidiograph", content);
        } else if (type == 2) {
            map.put("calias", content);
        } else if (type == 3) {
            map.put("clabel", content);
        }
        OkGo.<PublishUpLoadEntity>post(AppConstant.BASE_URL + AppConstant.URL_UPDATE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params(map)
                .execute(new JsonCallBack<PublishUpLoadEntity>(PublishUpLoadEntity.class) {
                    @Override
                    public void onSuccess(Response<PublishUpLoadEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            ToastUtil.show(context,context.getString(R.string.success));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<PublishUpLoadEntity> response) {
                        super.onError(response);
                    }
                });
    }

}
