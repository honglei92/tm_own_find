package com.tianma.tm_own_find.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tenma.ventures.config.TMServerConfig;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.view.discover_new.DiscoverContract;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;

import java.util.ArrayList;
import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    private Context mContext;
    private DiscoverContract.DiscoverItemClickListener discoverItemClickListener;
    private List<DiscoverModelItem> discoverModelItems = new ArrayList<>();

    public TopAdapter(Context context) {
        mContext = context;
    }

    public void setDiscoverItemClickListener(DiscoverContract.DiscoverItemClickListener discoverItemClickListener) {
        this.discoverItemClickListener = discoverItemClickListener;
    }

    public void setContent(List<DiscoverModelItem> list) {
        discoverModelItems.clear();
        discoverModelItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public TopAdapter.TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_discover_top_item, parent, false);
        TopAdapter.TopViewHolder viewHolder = new TopAdapter.TopViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopAdapter.TopViewHolder holder, final int position) {
        holder.title.setText(discoverModelItems.get(position).getModelName());
        String url = discoverModelItems.get(position).getImageUrl();
        if (!url.contains("http")) {
            url = TMServerConfig.BASE_URL + url;
        }
        Glide.with(mContext).load(url).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discoverItemClickListener != null) {
                    discoverItemClickListener.onItemClick(discoverModelItems.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return discoverModelItems.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public TopViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.content_item_text);
            image = view.findViewById(R.id.content_item_image);
        }
    }
}
