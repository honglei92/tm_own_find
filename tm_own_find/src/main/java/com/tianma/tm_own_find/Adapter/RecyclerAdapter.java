package com.tianma.tm_own_find.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tenma.ventures.config.TMServerConfig;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.ViewHolder.FindContentItemViewHolder;
import com.tianma.tm_own_find.ViewHolder.FindContentTitleViewHolder;
import com.tianma.tm_own_find.server.bean.FindModelListData;
import com.tianma.tm_own_find.Listener.OnItemClickListener;

import java.util.List;

/**
 * =====作者=====
 * 许英俊
 * =====时间=====
 * 2017/10/26.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    
    private List<FindModelListData> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int lastH;

    /**
     * 点击事件
     */
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public RecyclerAdapter(Context context, List<FindModelListData> list, int lastH) {
        this.mList = list;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.lastH = lastH;
    }
    /*LinearLayout model_top_layout_1;
    ImageView model_top_img_1;
    TextView model_top_text_1;

    LinearLayout model_top_layout_2;
    ImageView model_top_img_2;
    TextView model_top_text_2;
    model_top_layout_1 = (LinearLayout)view.findViewById(R.id.model_top_layout_1);
    model_top_img_1 = (ImageView)view.findViewById(R.id.model_top_img_1);
    model_top_text_1 = (TextView)view.findViewById(R.id.model_top_text_1);

    model_top_layout_2 = (LinearLayout)view.findViewById(R.id.model_top_layout_2);
    model_top_img_2 = (ImageView)view.findViewById(R.id.model_top_img_2);
    model_top_text_2 = (TextView)view.findViewById(R.id.model_top_text_2);*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder mViewHolder = null;
        if (viewType == FindModelListData.TYPE.TYPE_TLTLE) {
            view = mInflater.inflate(R.layout.find_content_title, parent, false);
            mViewHolder = new FindContentTitleViewHolder(view);
        } else if (viewType == FindModelListData.TYPE.TYPE_ITEM) {
            view = mInflater.inflate(R.layout.find_content_item, parent, false);
            mViewHolder = new FindContentItemViewHolder(view);
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case FindModelListData.TYPE.TYPE_TLTLE:
                FindContentTitleViewHolder findContentTitle = (FindContentTitleViewHolder) holder;
                findContentTitle.content_title_text.setText(mList.get(position).getModel_type_name());
                if (0 == position){
                    findContentTitle.content_title_diliver.setVisibility(View.GONE);
                }else {
                    findContentTitle.content_title_diliver.setVisibility(View.VISIBLE);
                }
                //判断最后一个view
               /* if (position == mList.size() - 1) {
                    if (findContentTitle.content_title_text.getHeight() < lastH) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.height = lastH;
                        findContentTitle.content_title_text.setLayoutParams(params);
                    }
                }*/
                break;
            case FindModelListData.TYPE.TYPE_ITEM:
                FindContentItemViewHolder findContentItem = (FindContentItemViewHolder) holder;
                findContentItem.content_item_text.setText(mList.get(position).getModel_name());
                if (mList.get(position).getImage_url().indexOf("http") != -1) {
                    Glide.with(mContext).load(mList.get(position).getImage_url()).into(findContentItem.content_item_image);
                } else {
                    Glide.with(mContext).load(TMServerConfig.BASE_URL + mList.get(position).getImage_url()).into(findContentItem.content_item_image);
                }
                //判断最后一个view
               /* if (position == mList.size() - 1) {
                    if (findContentItem.content_item_text.getHeight() < lastH) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.height = lastH;
                        findContentItem.content_item_text.setLayoutParams(params);
                    }
                }*/

                findContentItem.content_item.setOnClickListener(this);
                findContentItem.content_item.setTag(position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).content_type;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            int position = (int) v.getTag();
            mOnItemClickListener.OnItemClick(position);
        }
    }
}
