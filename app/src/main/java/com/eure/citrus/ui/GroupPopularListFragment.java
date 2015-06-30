package com.eure.citrus.ui;

import com.eure.citrus.R;
import com.eure.citrus.Utils;
import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.listener.OnRecyclerItemClickListener;
import com.eure.citrus.model.entity.Group;
import com.eure.citrus.model.repository.GroupRepository;
import com.eure.citrus.ui.adapter.GroupListAdapter;
import com.eure.citrus.ui.widget.DividerItemDecoration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class GroupPopularListFragment extends Fragment implements OnRecyclerItemClickListener {


    public GroupPopularListFragment() {
    }

    public static GroupPopularListFragment newInstance() {
        return new GroupPopularListFragment();
    }

    private GroupListAdapter mGroupListAdapter;

    // Realm instance for the UI thread
    private Realm mUIThreadRealm;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mUIThreadRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String categoryName = GroupHelper.CATEGORY_POPULAR;
        RealmResults<Group> groups = GroupRepository.findAllByCategoryName(mUIThreadRealm, categoryName);
        mGroupListAdapter = new GroupListAdapter(groups, this);

        RecyclerView recyclerView = findById(view, R.id.group_list_recycler_view);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(Utils.getDrawableResource(getActivity(), R.drawable.line)));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mGroupListAdapter);
    }

    @Override
    public void onClickRecyclerItem(View v, int position) {
        Group group = mGroupListAdapter.getItem(position);
        Intent intent = GroupDetailActivity.createIntent(getActivity(), group.getName());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGroupListAdapter.release();
        mUIThreadRealm.close();
    }
}
