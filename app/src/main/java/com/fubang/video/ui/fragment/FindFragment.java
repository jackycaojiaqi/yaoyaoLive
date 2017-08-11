package com.fubang.video.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fubang.video.R;
import com.fubang.video.adapter.FindViewpagerAdapter;
import com.fubang.video.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jacky on 2017/7/11.
 */
public class FindFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_find)
    TabLayout tlFind;
    @BindView(R.id.vp_find)
    ViewPager vpFind;
    Unbinder unbinder;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setText(tvTitle,R.string.find );
        titles.add(getString(R.string.rank_of_rich));
        titles.add(getString(R.string.rank_of_meili));
        titles.add(getString(R.string.rank_of_gift));
        fragments.add(new FindTuHaoFrament());
        fragments.add(new FindMeiliFrament());
        fragments.add(new FindGiftFrament());
        FindViewpagerAdapter adapter = new FindViewpagerAdapter(getChildFragmentManager(), fragments, titles);
        vpFind.setAdapter(adapter);
        vpFind.setOffscreenPageLimit(2);
        tlFind.setupWithViewPager(vpFind);
        tlFind.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
