package com.fubang.video.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.base.BaseFragment;
import com.fubang.video.ui.PublishCircleActivity;

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
        tvTitle.setText("生活圈");
        setText(tvSubmit,"规则");
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
