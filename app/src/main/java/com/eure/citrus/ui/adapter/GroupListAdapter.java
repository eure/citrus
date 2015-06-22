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

import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private RealmResults<Group> mGroups;

    private static OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public GroupListAdapter(Context context, RealmResults<Group> groups,
            OnRecyclerItemClickListener onRecyclerItemClickListener) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        mGroups = groups;
        mOnRecyclerItemClickListener = onRecyclerItemClickListener;
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

        ImageView groupImageView;

        AppCompatTextView groupNameText;

        AppCompatTextView groupDescriptionText;

        public ViewHolder(final View v) {
            super(v);
            v.setOnClickListener(this);
            groupImageView = (ImageView) v.findViewById(R.id.group_image);
            groupNameText = (AppCompatTextView) v.findViewById(R.id.group_name);
            groupDescriptionText = (AppCompatTextView) v.findViewById(R.id.group_description);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnRecyclerItemClickListener.onClickRecyclerItem(view, position);
            }
        }
    }
}
