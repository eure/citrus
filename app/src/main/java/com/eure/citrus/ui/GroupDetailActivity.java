package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.helper.GroupHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class GroupDetailActivity extends AppCompatActivity {

    private static final String KEY_GROUP_NAME = "key_group_name";

    private static final int REQUEST_CREATE_TASK_ACTIVITY = 1000;

    private String mGroupName;

    private ListsFragment mListsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.inject(this);
        Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mGroupName = intent.getStringExtra(KEY_GROUP_NAME);

        CollapsingToolbarLayout collapsingToolbar = findById(this, R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mGroupName);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        ImageView headerImageView = findById(this, R.id.backdrop);
        GroupHelper.setupDefaultGroup(mGroupName, headerImageView);

        mListsFragment = ListsFragment.newInstance(mGroupName);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.group_detail_container, mListsFragment)
                .commit();
    }

    @OnClick(R.id.group_detail_fab)
    public void onClickFloatingActionButton() {
        Intent intent = CreateNewTaskActivity.createIntent(this, mGroupName);
        startActivityForResult(intent, REQUEST_CREATE_TASK_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CREATE_TASK_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    mListsFragment.refreshListByGroupName(mGroupName);
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createIntent(Context context, String groupName) {
        Intent intent = new Intent(context, GroupDetailActivity.class);
        intent.putExtra(KEY_GROUP_NAME, groupName);
        return intent;
    }
}
