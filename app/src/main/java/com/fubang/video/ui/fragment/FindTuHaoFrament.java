package com.fubang.video.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fubang.video.R;
import com.fubang.video.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/7/26.
 */
public class FindTuHaoFrament extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_tuhao, container, false);
        return view;
    }

}
