package com.eure.citrus.ui;

import com.eure.citrus.R;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    public enum State {
        HOME, GROUPS, LISTS, PROFILE
    }

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    @InjectView(R.id.main_fab)
    FloatingActionButton mFloatingActionButton;

    private ActionBarDrawerToggle mDrawerToggle;

    private State mCurrentState;

    private HomeFragment mHomeFragment;

    private GroupsFragment mGroupsFragment;

    private ListsFragment mListsFragment;

    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        setNavigationView();
        switchFragment(State.HOME);
    }

    private void switchFragment(State state) {
        mCurrentState = state;
        ActionBar actionBar = getSupportActionBar();
        switch (state) {
            case HOME:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.home);
                }
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                replaceMainFragment(mHomeFragment);
                mFloatingActionButton.setVisibility(View.VISIBLE);
                break;
            case GROUPS:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.groups);
                }
                if (mGroupsFragment == null) {
                    mGroupsFragment = GroupsFragment.newInstance();
                }
                replaceMainFragment(mGroupsFragment);
                mFloatingActionButton.setVisibility(View.GONE);
                break;
            case LISTS:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.lists);
                }
                if (mListsFragment == null) {
                    mListsFragment = ListsFragment.newInstance();
                }
                replaceMainFragment(mListsFragment);
                mFloatingActionButton.setVisibility(View.GONE);
                break;
            case PROFILE:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.profile);
                }
                if (mProfileFragment == null) {
                    mProfileFragment = ProfileFragment.newInstance();
                }
                replaceMainFragment(mProfileFragment);
                mFloatingActionButton.setVisibility(View.GONE);
                break;
        }
    }

    private void setNavigationView() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        switchFragment(State.HOME);
                        break;
                    case R.id.groups:
                        switchFragment(State.GROUPS);
                        break;
                    case R.id.lists:
                        switchFragment(State.LISTS);
                        break;
                    case R.id.profile:
                        switchFragment(State.PROFILE);
                        break;
                    default:
                        break;
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void replaceMainFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showSnackbar(String s) {
        Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.main_fab)
    public void onClickFloatingActionButton() {
        switch (mCurrentState) {
            case HOME:
                if (mHomeFragment != null) {
                    mHomeFragment.onClickFAB();
                }
                break;
        }
    }
}
