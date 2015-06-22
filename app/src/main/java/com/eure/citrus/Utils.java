package com.eure.citrus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class Utils {
    
    public static Drawable getDrawableResource(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static String getDayOfWeekString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    public static String getDateString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String date = dayFormat.format(calendar.getTime());
        return date;
    }
}
