package com.tianma.tm_own_find.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianma.tm_own_find.R;

/**
 * Created by wcc on 2018/9/10.
 */

public class FindContentTitleViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout content_title;
    public TextView content_title_diliver;
    public TextView content_title_text;

    public FindContentTitleViewHolder(View itemView) {
        super(itemView);
        content_title = (LinearLayout) itemView.findViewById(R.id.content_title);
        content_title_diliver = (TextView) itemView.findViewById(R.id.content_title_diliver);
        content_title_text = (TextView) itemView.findViewById(R.id.content_title_text);
    }
}
