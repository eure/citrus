package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.model.entity.Task;
import com.eure.citrus.model.repository.TaskRepository;
import com.eure.citrus.ui.widget.BottomButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static butterknife.ButterKnife.findById;

/**
 * Created by katsuyagoto on 15/06/18.
 */

public class CreateNewTaskActivity extends AppCompatActivity {

    private static final String KEY_GROUP_NAME = "key_group_name";

    @Bind(R.id.create_task_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.create_task_text_input_layout)
    TextInputLayout mTextInputLayout;

    @Bind(R.id.create_task_edit_text)
    AppCompatEditText mTaskNameEditText;

    @Bind(R.id.create_task_button)
    BottomButton mCreateTaskButton;

    // If true, it call setResult(RESULT_OK) before finish.
    private boolean mCreatedTask = false;

    // Realm instance for the UI thread
    private Realm mUIThreadRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        ButterKnife.bind(this);
        Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTaskNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        mUIThreadRealm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.create_task_button)
    public void onClickCreateTaskButton() {
        String name = mTaskNameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mTextInputLayout.setError(getString(R.string.error_empty_name));
            return;
        }

        mCreateTaskButton.setEnabled(false);

        String groupName = getIntent().getStringExtra(KEY_GROUP_NAME);
        if (groupName == null) {
            groupName = GroupHelper.PRIVATE;
        }

        final Task task = TaskRepository.create(mUIThreadRealm, name, groupName);

        mCreateTaskButton.setEnabled(true);
        Snackbar.make(mCoordinatorLayout, getString(R.string.create_successfully, name), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(android.R.color.white))
                .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TaskRepository.delete(mUIThreadRealm, task);
                                mCreatedTask = false;
                            }
                        }
                )
                .show();
        mTaskNameEditText.setText("");
        mTextInputLayout.setError("");
        mCreatedTask = true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (mCreatedTask) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (mCreatedTask) {
                    setResult(RESULT_OK);
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createIntent(Context context, String groupName) {
        Intent intent = new Intent(context, CreateNewTaskActivity.class);
        intent.putExtra(KEY_GROUP_NAME, groupName);
        return intent;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUIThreadRealm.close();
    }
}