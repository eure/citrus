package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.Utils;
import com.eure.citrus.listener.OnCanSetLayoutParamsListener;
import com.eure.citrus.listener.OnMakeSnackbar;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static butterknife.ButterKnife.findById;

public class MainActivity extends AppCompatActivity implements OnMakeSnackbar {

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.main_fab)
    FloatingActionButton mFloatingActionButton;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentMenu;

    private HomeFragment mHomeFragment;

    private GroupsFragment mGroupsFragment;

    private ListsFragment mListsFragment;

    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        setupNavigationView(toolbar);
        setupFloatingActionButton();
        switchFragment(R.id.home);
    }

    private void setupFloatingActionButton() {
        Utils.setFabLayoutParams(mFloatingActionButton, new OnCanSetLayoutParamsListener() {
            @Override
            public void onCanSetLayoutParams() {
                // setMargins to fix floating action button's layout bug
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mFloatingActionButton
                        .getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                mFloatingActionButton.setLayoutParams(params);
            }
        });
    }

    private void switchFragment(int menuId) {
        mCurrentMenu = menuId;
        ActionBar actionBar = getSupportActionBar();
        switch (menuId) {
            case R.id.home:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.home);
                }
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.VISIBLE);
                replaceMainFragment(mHomeFragment);
                break;
            case R.id.groups:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.groups);
                }
                if (mGroupsFragment == null) {
                    mGroupsFragment = GroupsFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mGroupsFragment);
                break;
            case R.id.lists:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.lists);
                }
                if (mListsFragment == null) {
                    mListsFragment = ListsFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mListsFragment);
                break;
            case R.id.profile:
                if (actionBar != null) {
                    actionBar.setTitle(R.string.profile);
                }
                if (mProfileFragment == null) {
                    mProfileFragment = ProfileFragment.newInstance();
                }
                mFloatingActionButton.setVisibility(View.GONE);
                replaceMainFragment(mProfileFragment);
                break;
            default:
                break;
        }
    }

    private void setupNavigationView(Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView navigationView = findById(this, R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switchFragment(menuItem.getItemId());
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

    @OnClick(R.id.main_fab)
    public void onClickFAB() {
        switch (mCurrentMenu) {
            case R.id.home:
                if (mHomeFragment != null) {
                    mHomeFragment.onClickMainFAB();
                }
                break;
        }
    }

    @Override
    public Snackbar onMakeSnackbar(CharSequence text, int duration) {
        return Snackbar.make(mCoordinatorLayout, text, duration);
    }

}
