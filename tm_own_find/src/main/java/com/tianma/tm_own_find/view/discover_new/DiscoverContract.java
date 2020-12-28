package com.tianma.tm_own_find.view.discover_new;

import com.tianma.tm_own_find.Adapter.ContentAdapter;
import com.tianma.tm_own_find.Adapter.TopAdapter;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.youth.banner.Banner;

public interface DiscoverContract {
    interface View {
        void refresh(boolean topMaxLine);

        Banner getBanner();

        void setAdapter(TopAdapter topAdapter, ContentAdapter contentAdapter);

        void refreshBackground(String color);

        void refreshBackground(int color);
    }

    interface Presenter {
        void attachView(DiscoverContract.View view);

        void detachView();

        boolean isViewAttached();

        void start();

        void getData();
    }

    interface DiscoverItemClickListener {
        void onItemClick(DiscoverModelItem discoverModelItem);
    }
}
