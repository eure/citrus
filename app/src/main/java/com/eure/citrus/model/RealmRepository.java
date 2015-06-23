package com.eure.citrus.model;

import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.model.db.Group;
import com.eure.citrus.model.db.Task;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/21.
 */
public class RealmRepository {

    public static class TaskObject {

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

        public static void delete(@NonNull Realm realm, Task task) {
            realm.beginTransaction();
            task.removeFromRealm();
            realm.commitTransaction();
        }

        public static Task updateByCompleted(@NonNull Realm realm, Task task, boolean completed) {
            realm.beginTransaction();
            task.setCompleted(completed);
            realm.commitTransaction();
            return task;
        }

        public static long count(@NonNull Realm realm) {
            return realm.where(Task.class)
                    .count();
        }

        public static long countByCompleted(@NonNull Realm realm, boolean completed) {
            return realm.where(Task.class)
                    .equalTo("completed", completed)
                    .count();
        }

        public static RealmResults<Task> findAll(@NonNull Realm realm) {
            return realm.where(Task.class)
                    .findAll();
        }

        public static RealmResults<Task> findAllByCompleted(@NonNull Realm realm, boolean completed) {
            return realm.where(Task.class)
                    .equalTo("completed", completed)
                    .findAll();
        }

        public static RealmResults<Task> findAllByGroupName(@NonNull Realm realm,
                String groupName) {
            return realm.where(Task.class)
                    .equalTo("groupName", groupName)
                    .findAll();
        }

    }

    public static class GroupObject {

        public static boolean isEmpty(@NonNull Realm realm) {
            return realm.where(Group.class).findFirst() == null;
        }

        public static void createDefaultGroups(@NonNull Realm realm) {
            if (!isEmpty(realm)) {
                return;
            }

            realm.beginTransaction();

            Group workGroup = realm.createObject(Group.class);
            workGroup.setName(GroupHelper.WORK);
            workGroup.setDescription("Freelance Projects");
            workGroup.setCategoryName(GroupHelper.CATEGORY_POPULAR);
            workGroup.setDefaultGroup(true);

            Group travelGroup = realm.createObject(Group.class);
            travelGroup.setName(GroupHelper.TRAVEL);
            travelGroup.setDescription("Super Plans");
            travelGroup.setCategoryName(GroupHelper.CATEGORY_POPULAR);
            travelGroup.setDefaultGroup(true);

            Group foodGroup = realm.createObject(Group.class);
            foodGroup.setName(GroupHelper.FOOD);
            foodGroup.setDescription("Need to buy");
            foodGroup.setCategoryName(GroupHelper.CATEGORY_POPULAR);
            foodGroup.setDefaultGroup(true);

            Group privateGroup = realm.createObject(Group.class);
            privateGroup.setName(GroupHelper.PRIVATE);
            privateGroup.setDescription("Free space");
            privateGroup.setCategoryName(GroupHelper.CATEGORY_POPULAR);
            privateGroup.setDefaultGroup(true);

            realm.commitTransaction();
        }

        public static RealmResults<Group> findAllByCategoryName(@NonNull Realm realm, String categoryName) {
            return realm
                    .where(Group.class)
                    .equalTo("categoryName", categoryName)
                    .findAll();
        }

    }

}
