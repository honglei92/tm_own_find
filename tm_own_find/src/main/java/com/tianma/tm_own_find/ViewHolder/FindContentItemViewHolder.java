package com.tianma.tm_own_find.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianma.tm_own_find.R;

/**
 * =====作者=====
 * 许英俊
 * =====时间=====
 * 2017/10/29.
 */

public class FindContentItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout content_item;
    public ImageView content_item_image;
    public TextView content_item_text;

    public FindContentItemViewHolder(View itemView) {
        super(itemView);
        content_item = (LinearLayout) itemView.findViewById(R.id.content_item);
        content_item_image = (ImageView) itemView.findViewById(R.id.content_item_image);
        content_item_text = (TextView) itemView.findViewById(R.id.content_item_text);
    }
}
