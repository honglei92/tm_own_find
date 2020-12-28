package com.tianma.tm_own_find.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tenma.ventures.config.TMServerConfig;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.view.discover_new.DiscoverContract;
import com.tianma.tm_own_find.view.discover_new.FindDefaultMoreActivity;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelList;

import java.util.ArrayList;
import java.util.List;

public class DefaultModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private DiscoverContract.DiscoverItemClickListener discoverItemClickListener;
    private List<DiscoverModelItem> contentItems = new ArrayList<>();
    private DiscoverModelList discoverModelList = new DiscoverModelList();
    private Gson gson = new Gson();

    public DefaultModelAdapter(Context context, boolean b) {
        mContext = context;
    }

    public void setDiscoverItemClickListener(DiscoverContract.DiscoverItemClickListener discoverItemClickListener) {
        this.discoverItemClickListener = discoverItemClickListener;
    }

    public void setContent(String modelTyeName, List<DiscoverModelItem> list) {
        contentItems.clear();
        contentItems.addAll(list);
        discoverModelList.setModelTyeName(modelTyeName);
        discoverModelList.setDiscoverModelItems(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_discover_content_item, parent, false);
            return new ContentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_discover_content_item, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).title.setText(contentItems.get(position).getModelName());
            ((ContentViewHolder) holder).title.setTextColor(Color.BLACK);
            String url = contentItems.get(position).getImageUrl();
            if (!url.contains("http") && !url.equals("more")) {
                url = TMServerConfig.BASE_URL + url;
            }
            if (url.equals("more")) {
                Glide.with(mContext).load(R.drawable.find_default_more).into(((ContentViewHolder) holder).image);
            } else {
                Glide.with(mContext).load(url).into(((ContentViewHolder) holder).image);
            }
            final String finalUrl = url;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!finalUrl.equals("more")) {
                        if (discoverItemClickListener != null) {
                            discoverItemClickListener.onItemClick(contentItems.get(position));
                        }
                    } else {
                        Intent intent = new Intent(mContext, FindDefaultMoreActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("data", gson.toJson(discoverModelList));
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }

                }
            });
            if (position == contentItems.size() - 1) {
                ((ContentViewHolder) holder).lastView.setVisibility(View.GONE);
            } else {
                ((ContentViewHolder) holder).lastView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return contentItems.size();
    }

    public void setContent(String modelTyeName, List<DiscoverModelItem> list, List<DiscoverModelItem> discoverModelItems) {
        contentItems.clear();
        contentItems.addAll(list);
        discoverModelList.setModelTyeName(modelTyeName);
        discoverModelList.setDiscoverModelItems(discoverModelItems);
        notifyDataSetChanged();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        View lastView;

        public ContentViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.content_item_text);
            image = view.findViewById(R.id.content_item_image);
            lastView = view.findViewById(R.id.item_last_view);
        }
    }
}
