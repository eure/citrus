package com.eure.citrus.ui.adapter;

import com.eure.citrus.R;
import com.eure.citrus.model.db.Task;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class HomeTaskListAdapter extends RecyclerView.Adapter<HomeTaskListAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private RealmResults<Task> mTasks;

    public HomeTaskListAdapter(Context context, RealmResults<Task> tasks) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        mTasks = tasks;
    }

    public void setDate(RealmResults<Task> tasks) {
        this.mTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_home_task_list, parent, false);
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

        AppCompatTextView taskNameText;

        AppCompatTextView taskGroupText;

        public ViewHolder(View v) {
            super(v);
            taskNameText = (AppCompatTextView) v.findViewById(R.id.home_task_name);
            taskGroupText = (AppCompatTextView) v.findViewById(R.id.home_task_group);
        }
    }
}
