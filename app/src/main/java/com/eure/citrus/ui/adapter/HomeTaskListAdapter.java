package com.eure.citrus.ui.adapter;

import com.eure.citrus.R;
import com.eure.citrus.model.entity.Task;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class HomeTaskListAdapter extends RecyclerView.Adapter<HomeTaskListAdapter.ViewHolder> {

    private RealmResults<Task> mTasks;

    public HomeTaskListAdapter(RealmResults<Task> tasks) {
        super();
        mTasks = tasks;
    }

    public void setData(RealmResults<Task> tasks) {
        this.mTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_task_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.taskNameText.setText(task.getName());
        holder.taskGroupText.setText(task.getGroupName());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.home_task_name)
        AppCompatTextView taskNameText;

        @Bind(R.id.home_task_group)
        AppCompatTextView taskGroupText;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
