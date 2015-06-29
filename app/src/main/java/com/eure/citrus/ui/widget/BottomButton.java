package com.eure.citrus.ui.widget;


import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by katsuyagoto on 15/06/19.
 */
@CoordinatorLayout.DefaultBehavior(BottomButton.Behavior.class)
public class BottomButton extends AppCompatButton {

    public BottomButton(Context context) {
        super(context);
    }

    public BottomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<BottomButton> {

        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;

        static {
            SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;
        }

        private float mTranslationY;

        public Behavior() {
        }

        /**
         *
         * @param parent
         * @param child
         * @param dependency
         * @return
         */
        public boolean layoutDependsOn(CoordinatorLayout parent, BottomButton child, View dependency) {
            return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof Snackbar.SnackbarLayout;
        }

        /**
         *
         * @param parent
         * @param child
         * @param dependency
         * @return
         */
        public boolean onDependentViewChanged(CoordinatorLayout parent, BottomButton child, View dependency) {
            if (dependency instanceof Snackbar.SnackbarLayout) {
                this.updateFabTranslationForSnackbar(parent, child, dependency);
            }
            return false;
        }

        /**
         *
         * @param parent
         * @param bb
         * @param snackbar
         */
        private void updateFabTranslationForSnackbar(CoordinatorLayout parent, BottomButton bb, View snackbar) {
            float translationY = this.getFabTranslationYForSnackbar(parent, bb);
            if (translationY != this.mTranslationY) {
                ViewCompat.animate(bb).cancel();
                if (Math.abs(translationY - this.mTranslationY) == (float) snackbar.getHeight()) {
                    ViewCompat.animate(bb).translationY(translationY).setInterpolator(new FastOutSlowInInterpolator())
                            .setListener((ViewPropertyAnimatorListener) null);
                } else {
                    ViewCompat.setTranslationY(bb, translationY);
                }

                this.mTranslationY = translationY;
            }

        }

        /**
         *
         * @param parent
         * @param bb
         * @return
         */
        private float getFabTranslationYForSnackbar(CoordinatorLayout parent, BottomButton bb) {
            float minOffset = 0.0F;
            List dependencies = parent.getDependencies(bb);
            int i = 0;

            for (int z = dependencies.size(); i < z; ++i) {
                View view = (View) dependencies.get(i);
                if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(bb, view)) {
                    minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - (float) view.getHeight());
                }
            }

            return minOffset;
        }
    }
}
