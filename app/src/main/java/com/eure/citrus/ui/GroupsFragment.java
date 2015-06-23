package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.ui.adapter.GroupPagerAdapter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupsFragment extends Fragment {

    public GroupsFragment() {
    }

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;

    @InjectView(R.id.groups_viewpager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        final GroupPagerAdapter adapter = new GroupPagerAdapter(getChildFragmentManager());
        adapter.addCategory(GroupHelper.CATEGORY_POPULAR);
        adapter.addCategory(GroupHelper.CATEGORY_LATEST);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
