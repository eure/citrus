package com.eure.citrus.model.repository;

import com.eure.citrus.model.entity.Task;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/25.
 */
public class TaskRepository {

    /**
     *
     * @param realm
     * @param name
     * @param groupName
     * @return
     */
    public static Task create(@NonNull Realm realm, @NonNull String name, String groupName) {
        realm.beginTransaction();
        Task task = realm.createObject(Task.class);
        task.setName(name);
        if (groupName != null) {
            task.setGroupName(groupName);
        }
        realm.commitTransaction();
        return task;
    }

    /**
     *
     * @param realm
     * @param task
     */
    public static void delete(@NonNull Realm realm, Task task) {
        realm.beginTransaction();
        task.removeFromRealm();
        realm.commitTransaction();
    }

    /**
     *
     * @param realm
     * @param task
     * @param completed
     * @return
     */
    public static Task updateByCompleted(@NonNull Realm realm, Task task, boolean completed) {
        realm.beginTransaction();
        task.setCompleted(completed);
        realm.commitTransaction();
        return task;
    }

    /**
     *
     * @param realm
     * @return
     */
    public static long count(@NonNull Realm realm) {
        return realm.where(Task.class)
                .count();
    }

    /**
     *
     * @param realm
     * @param completed
     * @return
     */
    public static long countByCompleted(@NonNull Realm realm, boolean completed) {
        return realm.where(Task.class)
                .equalTo("completed", completed)
                .count();
    }

    /**
     *
     * @param realm
     * @return
     */
    public static RealmResults<Task> findAll(@NonNull Realm realm) {
        return realm.where(Task.class)
                .findAll();
    }

    /**
     *
     * @param realm
     * @param completed
     * @return
     */
    public static RealmResults<Task> findAllByCompleted(@NonNull Realm realm, boolean completed) {
        return realm.where(Task.class)
                .equalTo("completed", completed)
                .findAll();
    }

    /**
     *
     * @param realm
     * @param groupName
     * @return
     */
    public static RealmResults<Task> findAllByGroupName(@NonNull Realm realm,
            String groupName) {
        return realm.where(Task.class)
                .equalTo("groupName", groupName)
                .findAll();
    }
}
