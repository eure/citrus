package com.eure.citrus.model.repository;

import com.eure.citrus.helper.GroupHelper;
import com.eure.citrus.model.entity.Group;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/25.
 */
public class GroupRepository {

    /**
     * Check whether group data is empty or not
     */
    public static boolean isEmpty(@NonNull Realm realm) {
        return realm.where(Group.class).findFirst() == null;
    }

    /**
     * Firstly, create default groups if isEmpty is true.
     *
     * @see #isEmpty(Realm)
     */
    public static void createDefaultGroups(@NonNull Realm realm) {
        if (!isEmpty(realm)) {
            return;
        }

        realm.beginTransaction();

        Group workGroup = realm.createObject(Group.class);
        workGroup.setName(GroupHelper.WORK);
        workGroup.setDescription("Great Projects");
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
        privateGroup.setDescription("Private tasks");
        privateGroup.setCategoryName(GroupHelper.CATEGORY_POPULAR);
        privateGroup.setDefaultGroup(true);

        realm.commitTransaction();
    }

    /**
     *
     * @param realm
     * @param categoryName
     * @return
     */
    public static RealmResults<Group> findAllByCategoryName(@NonNull Realm realm, String categoryName) {
        return realm
                .where(Group.class)
                .equalTo("categoryName", categoryName)
                .findAll();
    }

}
