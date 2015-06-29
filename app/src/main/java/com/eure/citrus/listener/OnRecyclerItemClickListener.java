package com.eure.citrus.listener;

import android.view.View;

/**
 * Created by katsuyagoto on 15/06/19.
 */
public interface OnRecyclerItemClickListener {

    /**
     * To detect click-event on recycler-view.
     */
    void onClickRecyclerItem(View v, int position);
}
