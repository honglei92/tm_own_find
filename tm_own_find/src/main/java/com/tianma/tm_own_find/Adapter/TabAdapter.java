package com.tianma.tm_own_find.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tianma.tm_own_find.Listener.OnItemClickListener;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.server.bean.FindModelTypeNameListData;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {
    private Context mContext;
    private int selectPosition = 0;
    private OnItemClickListener onItemClickListener;
    private List<FindModelTypeNameListData> mList = new ArrayList<>();
    private int themeColor;

    public TabAdapter(Context context, List<FindModelTypeNameListData> list) {
        mContext = context;
        themeColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(mContext));
        mList.clear();
        mList.addAll(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_find_tab_item, parent, false);
        TabViewHolder viewHolder = new TabViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).getType_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(position);
                    if (position != selectPosition) {
                        selectPosition = position;
                        notifyDataSetChanged();
                    }
                }
            }
        });
        if (position == selectPosition) {
            holder.title.setTextColor(themeColor);
            holder.line.setBackgroundColor(themeColor);
        } else {
            holder.title.setTextColor(mContext.getResources().getColor(R.color.find_black));
            holder.line.setBackgroundColor(mContext.getResources().getColor(R.color.find_transparent));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View line;

        public TabViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            line = view.findViewById(R.id.button_line);
        }

    }
}
