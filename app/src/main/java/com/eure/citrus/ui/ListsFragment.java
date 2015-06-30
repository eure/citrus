package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.Utils;
import com.eure.citrus.listener.OnRecyclerItemClickListener;
import com.eure.citrus.model.entity.Task;
import com.eure.citrus.model.repository.TaskRepository;
import com.eure.citrus.ui.adapter.ListsTaskListAdapter;
import com.eure.citrus.ui.widget.DividerItemDecoration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static butterknife.ButterKnife.findById;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class ListsFragment extends Fragment implements OnRecyclerItemClickListener {

    private static final String KEY_GROUP_NAME = "key_group_name";

    public ListsFragment() {
    }

    public static ListsFragment newInstance() {
        return new ListsFragment();
    }

    public static ListsFragment newInstance(String groupName) {
        ListsFragment listsFragment = new ListsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_GROUP_NAME, groupName);
        listsFragment.setArguments(args);
        return listsFragment;
    }

    private ListsTaskListAdapter mListsTaskListAdapter;

    // Realm instance for the UI thread
    private Realm mUIThreadRealm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mUIThreadRealm = Realm.getDefaultInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void refreshListByGroupName(String groupName) {
        RealmResults<Task> tasks = TaskRepository.findAllByGroupName(mUIThreadRealm, groupName);
        mListsTaskListAdapter.setData(tasks);
        mListsTaskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RealmResults<Task> tasks = null;
        boolean showGroupName = false;

        if (getArguments() != null) {
            String groupName = getArguments().getString(KEY_GROUP_NAME);
            tasks = TaskRepository.findAllByGroupName(mUIThreadRealm, groupName);
        } else {
            tasks = TaskRepository.findAll(mUIThreadRealm);
            showGroupName = true;
        }

        mListsTaskListAdapter = new ListsTaskListAdapter(getActivity(), tasks, this, showGroupName);

        RecyclerView recyclerView = findById(view, R.id.lists_recycler_view);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(Utils.getDrawableResource(getActivity(), R.drawable.line)));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mListsTaskListAdapter);
    }

    @Override
    public void onClickRecyclerItem(View v, int position) {
        Task task = mListsTaskListAdapter.getItem(position);
        AppCompatCheckedTextView taskNameText = (AppCompatCheckedTextView) v.findViewById(R.id.lists_task_name);
        mListsTaskListAdapter.changeTaskNameState(v, taskNameText, !task.isCompleted(), getResources());

        TaskRepository.updateByCompleted(mUIThreadRealm, task, !task.isCompleted());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListsTaskListAdapter.release();
        mUIThreadRealm.close();
    }

}
