package com.tianma.tm_own_find.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tenma.ventures.config.TMServerConfig;
import com.tenma.ventures.tools.TMAndroid;
import com.tenma.ventures.tools.TMDensity;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.view.discover_new.DiscoverContract;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发现item的内容
 */
public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private DiscoverContract.DiscoverItemClickListener discoverItemClickListener;
    private List<DiscoverModelList> discoverModelLists = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private Map<Integer, Integer> currentPositionMap = new HashMap<>();

    public ContentAdapter(Context context) {
        mContext = context;
    }

    public void setDiscoverItemClickListener(DiscoverContract.DiscoverItemClickListener discoverItemClickListener) {
        this.discoverItemClickListener = discoverItemClickListener;
    }

    public void setContent(List<DiscoverModelList> list) {
        discoverModelLists.clear();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDiscoverModelItems().size() > 0) {
//                    if (list.get(i).getStyle() == 2) {
//                        currentPositionMap.put(list.get(i).getKey(), 0);
//                    }
                    discoverModelLists.add(list.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            //默认
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_default_style_item, parent, false);
            return new DefaultViewHolder(view);
        } else if (viewType == 2) {
            //圆扁图
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_horient_scroll_item, parent, false);
            return new HorizonScrollViewHolder(view);
        } else if (viewType == 3) {
            //左大右三图
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_multi_image_item, parent, false);
            return new MultiImageViewHolder(view);
        } else {
            //默认
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_default_style_item, parent, false);
            return new DefaultViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("style1:", discoverModelLists.get(position).getStyle() + "");
        return discoverModelLists.get(position).getStyle();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MultiImageViewHolder) {
            RequestOptions options = new RequestOptions().transform(new MultiTransformation(new CenterCrop(),
                    new RoundedCorners(TMDensity.dipToPx(mContext, 5))));//图片圆角为30
            if (discoverModelLists.get(position).getDiscoverModelItems().size() > 0) {
                String url = discoverModelLists.get(position).getDiscoverModelItems().get(0).getImageUrl();
                if (!url.contains("http")) {
                    url = TMServerConfig.BASE_URL + url;
                }
                Glide.with(mContext).load(url) //图片地址
                        .apply(options)
                        .into(((MultiImageViewHolder) holder).image1);
                ((MultiImageViewHolder) holder).image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (discoverItemClickListener != null) {
                            discoverItemClickListener.onItemClick(discoverModelLists.get(position).getDiscoverModelItems().get(0));
                        }
                    }
                });
            }
            if (discoverModelLists.get(position).getDiscoverModelItems().size() > 1) {
                ((MultiImageViewHolder) holder).image2.setVisibility(View.VISIBLE);
                String url = discoverModelLists.get(position).getDiscoverModelItems().get(1).getImageUrl();
                if (!url.contains("http")) {
                    url = TMServerConfig.BASE_URL + url;
                }
                Glide.with(mContext).load(url) //图片地址
                        .apply(options)
                        .into(((MultiImageViewHolder) holder).image2);
                ((MultiImageViewHolder) holder).image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (discoverItemClickListener != null) {
                            discoverItemClickListener.onItemClick(discoverModelLists.get(position).getDiscoverModelItems().get(1));
                        }
                    }
                });

            }
            if (discoverModelLists.get(position).getDiscoverModelItems().size() > 2) {
                ((MultiImageViewHolder) holder).image3.setVisibility(View.VISIBLE);

                String url = discoverModelLists.get(position).getDiscoverModelItems().get(2).getImageUrl();
                if (!url.contains("http")) {
                    url = TMServerConfig.BASE_URL + url;
                }
                Glide.with(mContext).load(url) //图片地址
                        .apply(options)
                        .into(((MultiImageViewHolder) holder).image3);
                ((MultiImageViewHolder) holder).image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (discoverItemClickListener != null) {
                            discoverItemClickListener.onItemClick(discoverModelLists.get(position).getDiscoverModelItems().get(2));
                        }
                    }
                });

            }
            if (discoverModelLists.get(position).getDiscoverModelItems().size() > 3) {
                ((MultiImageViewHolder) holder).image4.setVisibility(View.VISIBLE);
                String url = discoverModelLists.get(position).getDiscoverModelItems().get(3).getImageUrl();
                if (!url.contains("http")) {
                    url = TMServerConfig.BASE_URL + url;
                }
                Glide.with(mContext).load(url) //图片地址
                        .apply(options)
                        .into(((MultiImageViewHolder) holder).image4);
                ((MultiImageViewHolder) holder).image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (discoverItemClickListener != null) {
                            discoverItemClickListener.onItemClick(discoverModelLists.get(position).getDiscoverModelItems().get(3));
                        }
                    }
                });
            }
        }
        //默认样式
        if (holder instanceof DefaultViewHolder) {
            ((DefaultViewHolder) holder).title.setText(discoverModelLists.get(position).getModelTyeName());
            if (discoverModelLists.get(position).getDiscoverModelItems().size() <= 12) {
                ((DefaultViewHolder) holder).defaultModelAdapter.setContent(discoverModelLists.get(position).getModelTyeName(), discoverModelLists.get(position).getDiscoverModelItems());
            } else {
                List<DiscoverModelItem> list = new ArrayList<DiscoverModelItem>();
                for (int i = 0; i < 11; i++) {
                    list.add(discoverModelLists.get(position).getDiscoverModelItems().get(i));
                }
                DiscoverModelItem discoverModelItem = new DiscoverModelItem();
                discoverModelItem.setModelName("更多");
                discoverModelItem.setImageUrl("more");
                list.add(discoverModelItem);
                ((DefaultViewHolder) holder).defaultModelAdapter.setContent(discoverModelLists.get(position).getModelTyeName(), list, discoverModelLists.get(position).getDiscoverModelItems());
            }
            ((DefaultViewHolder) holder).setDiscoverItemClickListener(discoverItemClickListener);
        }
        //圆扁图
        if (holder instanceof HorizonScrollViewHolder) {
            final List<String> bannerImages = new ArrayList<String>();
            final DiscoverModelList discoverModelList = discoverModelLists.get(position);
            for (int i = 0; i < discoverModelList.getDiscoverModelItems().size(); i++) {
                if (i < 10) {
                    bannerImages.add(discoverModelList.getDiscoverModelItems().get(i).getImageUrl());
                }
            }
            bannerImages.add(discoverModelList.getDiscoverModelItems().get(0).getImageUrl());
            if (bannerImages.size() == 11) {
                bannerImages.add(0, discoverModelList.getDiscoverModelItems().get(9).getImageUrl());
            } else {
                bannerImages.add(0, discoverModelList.getDiscoverModelItems().get(discoverModelList.getDiscoverModelItems().size() - 1).getImageUrl());
            }

            final List<DiscoverModelItem> tempDiscoverModelItem = new ArrayList<>();
            if (discoverModelList.getDiscoverModelItems().size() <= 10) {
                tempDiscoverModelItem.addAll(discoverModelList.getDiscoverModelItems());
            } else {
                for (int i = 0; i < 10; i++) {
                    //只取前10
                    tempDiscoverModelItem.add(discoverModelList.getDiscoverModelItems().get(i));
                }
            }
            tempDiscoverModelItem.add(discoverModelList.getDiscoverModelItems().get(0));
            if (tempDiscoverModelItem.size() == 11) {
                tempDiscoverModelItem.add(0, discoverModelList.getDiscoverModelItems().get(9));
            } else {
                tempDiscoverModelItem.add(0, discoverModelList.getDiscoverModelItems().get(discoverModelList.getDiscoverModelItems().size() - 1));
            }
            ((HorizonScrollViewHolder) holder).mViewPager.setOffscreenPageLimit(11);
            ((HorizonScrollViewHolder) holder).mViewPager.setPageMargin(TMDensity.dipToPx(mContext, 10));
            ((HorizonScrollViewHolder) holder).mViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return bannerImages.size();
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    RequestOptions options = new RequestOptions().transform(new MultiTransformation(new CenterCrop(),
                            new RoundedCorners(TMDensity.dipToPx(mContext, 33))));//图片圆角为33
                    ImageView imageView = new ImageView(mContext);
                    String url = bannerImages.get(position);
                    if (!url.contains("http")) {
                        url = TMServerConfig.BASE_URL + url;
                    }
                    Glide.with(mContext).load(url) //图片地址
                            .apply(options)
                            .into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            discoverItemClickListener.onItemClick(tempDiscoverModelItem.get(position));
                        }
                    });
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }
            });
            ((HorizonScrollViewHolder) holder).mViewPager.setCurrentItem(1, false);
            ((HorizonScrollViewHolder) holder).mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int currentPosition;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
//                    currentPositionMap.put(discoverModelList.getKey(), position);
                    currentPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                       /* if (currentPositionMap.get(discoverModelList.getKey()) == 0) {
                            ((HorizonScrollViewHolder) holder).mViewPager.setCurrentItem(bannerImages.size() - 2, false);
                        } else if (currentPositionMap.get(discoverModelList.getKey()) == bannerImages.size() - 1) {
                            ((HorizonScrollViewHolder) holder).mViewPager.setCurrentItem(1, false);
                        }*/
                        if (currentPosition == 0) {
                            ((HorizonScrollViewHolder) holder).mViewPager.setCurrentItem(bannerImages.size() - 2, false);
                        } else if (currentPosition == bannerImages.size() - 1) {
                            ((HorizonScrollViewHolder) holder).mViewPager.setCurrentItem(1, false);
                        }
                    }
                    enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
                }
            });
        }
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public int getItemCount() {
        Log.i("style1:", discoverModelLists.size() + "size");
        return discoverModelLists.size();
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    //默认样式
    class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;
        DefaultModelAdapter defaultModelAdapter;
        DiscoverContract.DiscoverItemClickListener mDiscoverItemClickListener;

        public DefaultViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.content_title_text);
            recyclerView = view.findViewById(R.id.default_content_rv);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
            recyclerView.setLayoutManager(gridLayoutManager);
            defaultModelAdapter = new DefaultModelAdapter(mContext, true);
            recyclerView.setAdapter(defaultModelAdapter);
        }

        public void setDiscoverItemClickListener(DiscoverContract.DiscoverItemClickListener discoverItemClickListener) {
            mDiscoverItemClickListener = discoverItemClickListener;
            if (defaultModelAdapter != null) {
                defaultModelAdapter.setDiscoverItemClickListener(mDiscoverItemClickListener);
            }
        }
    }

    /**
     * 圆扁图
     */
    class HorizonScrollViewHolder extends RecyclerView.ViewHolder {
        private ViewPager mViewPager;

        public HorizonScrollViewHolder(View view) {
            super(view);
            mViewPager = view.findViewById(R.id.view_pager);
        }
    }

    /**
     * 左大右三
     */
    class MultiImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image4;

        public MultiImageViewHolder(View view) {
            super(view);
            image1 = view.findViewById(R.id.multi_image1);
            image2 = view.findViewById(R.id.multi_image2);
            image3 = view.findViewById(R.id.multi_image3);
            image4 = view.findViewById(R.id.multi_image4);
        }
    }

}
