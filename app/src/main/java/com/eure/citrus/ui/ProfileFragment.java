package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.model.repository.TaskRepository;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.realm.Realm;

import static butterknife.ButterKnife.findById;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    // Realm instance for the UI thread
    private Realm mUIThreadRealm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mUIThreadRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Count of uncompleted tasks
        AppCompatTextView uncompletedTaskCount = findById(view, R.id.profile_uncompleted_count_text);
        long uncompletedCount = TaskRepository.countByCompleted(mUIThreadRealm, false);
        uncompletedTaskCount.setText(String.valueOf(uncompletedCount));

        // Count of completed tasks
        AppCompatTextView completedTaskCount = findById(view, R.id.profile_completed_count_text);
        long completedCount = TaskRepository.countByCompleted(mUIThreadRealm, true);
        completedTaskCount.setText(String.valueOf(completedCount));

        // Count of all tasks
        AppCompatTextView allTaskCount = findById(view, R.id.profile_all_count_text);
        long allCount = TaskRepository.count(mUIThreadRealm);
        allTaskCount.setText(String.valueOf(allCount));

        // Description about state of task
        AppCompatTextView state = findById(view, R.id.profile_state_text);
        String stateStr;
        if (completedCount < uncompletedCount) {
            stateStr = getString(R.string.you_can_do_it);
        } else if (completedCount > uncompletedCount) {
            stateStr = getString(R.string.good_job);
        } else {
            stateStr = getString(R.string.keep_it_up);
        }
        state.setText(stateStr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUIThreadRealm.close();
    }
}
