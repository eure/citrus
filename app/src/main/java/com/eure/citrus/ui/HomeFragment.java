package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.Utils;
import com.eure.citrus.listener.OnClickFABListener;
import com.eure.citrus.listener.SwipeableRecyclerViewTouchListener;
import com.eure.citrus.model.RealmRepository;
import com.eure.citrus.model.db.Task;
import com.eure.citrus.ui.adapter.HomeTaskListAdapter;
import com.eure.citrus.ui.widget.DividerItemDecoration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

import static butterknife.ButterKnife.findById;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class HomeFragment extends Fragment implements OnClickFABListener {

    private static final int REQUEST_CREATE_TASK_ACTIVITY = 1000;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @InjectView(R.id.home_task_count)
    AppCompatTextView mHomeTaskCountTextView;

    private HomeTaskListAdapter mHomeTaskListAdapter;

    // Realm instance for the UI thread
    private Realm mUIThreadRealm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mUIThreadRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RealmResults<Task> uncompletedTasks = RealmRepository.TaskObject
                .findAllByCompleted(mUIThreadRealm, false);
        mHomeTaskCountTextView.setText(String.valueOf(uncompletedTasks.size()));
        mHomeTaskListAdapter = new HomeTaskListAdapter(getActivity(), uncompletedTasks);

        RecyclerView recyclerView = findById(view, R.id.home_recycle_view);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(Utils.getDrawableResource(getActivity(), R.drawable.line)));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mHomeTaskListAdapter);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView,
                                    int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    com.eure.citrus.model.db.Task task = uncompletedTasks.get(position);
                                    RealmRepository.TaskObject.updateByCompleted(mUIThreadRealm, task, true);
                                    mHomeTaskListAdapter.notifyItemRemoved(position);
                                    showSnackbar(getString(R.string.complete_task, task.getName()));
                                }
                                mHomeTaskListAdapter.notifyDataSetChanged();
                                mHomeTaskCountTextView.setText(String.valueOf(mHomeTaskListAdapter.getItemCount()));
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView,
                                    int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    com.eure.citrus.model.db.Task task = uncompletedTasks.get(position);
                                    RealmRepository.TaskObject.updateByCompleted(mUIThreadRealm, task, true);
                                    mHomeTaskListAdapter.notifyItemRemoved(position);
                                    showSnackbar(getString(R.string.complete_task, task.getName()));
                                }
                                mHomeTaskListAdapter.notifyDataSetChanged();
                                mHomeTaskCountTextView.setText(String.valueOf(mHomeTaskListAdapter.getItemCount()));
                            }
                        });
        recyclerView.addOnItemTouchListener(swipeTouchListener);

        AppCompatTextView homeDayOfWeekTextView = findById(view, R.id.home_dayOfWeek);
        homeDayOfWeekTextView.setText(Utils.getDayOfWeekString());

        AppCompatTextView homeDateTextView = findById(view, R.id.home_date);
        homeDateTextView.setText(Utils.getDateString().toUpperCase());
    }

    private void showSnackbar(String s) {
        ((MainActivity) getActivity()).showSnackbar(s);
    }

    @Override
    public void onClickFAB() {
        Intent intent = new Intent(getActivity(), CreateNewTaskActivity.class);
        startActivityForResult(intent, REQUEST_CREATE_TASK_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CREATE_TASK_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    final RealmResults<com.eure.citrus.model.db.Task> uncompletedTasks = RealmRepository.TaskObject
                            .findAllByCompleted(mUIThreadRealm, false);
                    mHomeTaskListAdapter.setDate(uncompletedTasks);
                    mHomeTaskListAdapter.notifyDataSetChanged();
                    mHomeTaskCountTextView.setText(String.valueOf(uncompletedTasks.size()));
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUIThreadRealm.close();
    }
}
