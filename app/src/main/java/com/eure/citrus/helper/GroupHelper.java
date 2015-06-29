package com.eure.citrus.helper;

import com.eure.citrus.R;

import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupHelper {

    public static final String CATEGORY_POPULAR = "Popular";

    public static final String CATEGORY_LATEST = "Latest";

    public static final String WORK = "Work";

    public static final String FOOD = "Food";

    public static final String TRAVEL = "Travel";

    public static final String PRIVATE = "Private";

    /**
     *
     * @param groupName
     * @param imageView
     */
    public static void setupDefaultGroupImage(@NonNull String groupName, ImageView imageView) {
        switch (groupName) {
            case WORK:
                imageView.setImageResource(R.drawable.group_work);
                break;
            case FOOD:
                imageView.setImageResource(R.drawable.group_food);
                break;
            case TRAVEL:
                imageView.setImageResource(R.drawable.group_travel);
                break;
            case PRIVATE:
                imageView.setImageResource(R.drawable.group_private);
                break;
        }
    }
}
