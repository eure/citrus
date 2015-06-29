package com.eure.citrus;

import com.eure.citrus.listener.OnCanSetLayoutParamsListener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewTreeObserver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class Utils {

    /**
     *
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawableResource(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * Get "Monday", "Tuesday", ...
     */
    public static String getDayOfWeekString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    /**
     *
     * @return
     */
    public static String getDateString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String date = dayFormat.format(calendar.getTime());
        return date;
    }

    /**
     * Fix floating action button's layout bug.
     */
    public static void setFabLayoutParams(final FloatingActionButton floatingActionButton,
            final OnCanSetLayoutParamsListener onCanSetLayoutParamsListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewTreeObserver viewTreeObserver = floatingActionButton.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            floatingActionButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            floatingActionButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        onCanSetLayoutParamsListener.onCanSetLayoutParams();
                    }
                });
            }
        }
    }

}