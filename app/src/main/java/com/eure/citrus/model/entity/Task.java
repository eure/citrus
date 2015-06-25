package com.eure.citrus.model.entity;

import io.realm.RealmObject;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class Task extends RealmObject {

    private String name;

    private boolean completed;

    private String groupName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
