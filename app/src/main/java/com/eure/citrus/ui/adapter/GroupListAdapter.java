package com.eure.citrus.ui.adapter;

import com.eure.citrus.R;
import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.listener.OnRecyclerItemClickListener;
import com.eure.citrus.model.db.Group;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private RealmResults<Group> mGroups;

    private static OnRecyclerItemClickListener sOnRecyclerItemClickListener;

    public GroupListAdapter(Context context, RealmResults<Group> groups,
            OnRecyclerItemClickListener onRecyclerItemClickListener) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        mGroups = groups;
        sOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_group_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.groupNameText.setText(group.getName());
        holder.groupDescriptionText.setText(group.getDescription());

        if (group.isDefaultGroup()) {
            GroupHelper.setupDefaultGroup(group.getName(), holder.groupImageView);
        }
    }


    public Group getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.group_image)
        ImageView groupImageView;

        @InjectView(R.id.group_name)
        AppCompatTextView groupNameText;

        @InjectView(R.id.group_description)
        AppCompatTextView groupDescriptionText;

        public ViewHolder(final View v) {
            super(v);
            v.setOnClickListener(this);
            ButterKnife.inject(this, v);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                sOnRecyclerItemClickListener.onClickRecyclerItem(view, position);
            }
        }
    }
}
