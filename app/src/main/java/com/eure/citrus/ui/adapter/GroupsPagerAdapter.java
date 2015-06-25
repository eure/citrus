package com.eure.citrus.ui.adapter;

import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.ui.GroupPopularListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupsPagerAdapter extends FragmentPagerAdapter {

    private final List<String> mCategoryNames = new ArrayList<>();

    public GroupsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addCategory(String categoryName) {
        mCategoryNames.add(categoryName);
    }

    @Override
    public Fragment getItem(int position) {
        String categoryName = mCategoryNames.get(position);
        switch (categoryName) {
            case GroupHelper.CATEGORY_POPULAR:
                return GroupPopularListFragment.newInstance();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return mCategoryNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryNames.get(position);
    }
}
