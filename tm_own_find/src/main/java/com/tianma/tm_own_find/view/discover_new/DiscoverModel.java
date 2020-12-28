package com.tianma.tm_own_find.view.discover_new;

import com.tianma.tm_own_find.view.discover_new.bean.DiscoverBannerItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelList;

import java.util.ArrayList;
import java.util.List;

public class DiscoverModel {

    private List<DiscoverModelList> modelLists;
    private List<DiscoverModelItem> modelTopList;
    private List<DiscoverBannerItem> bannerItemList;

    public DiscoverModel() {
        modelLists = new ArrayList<>();
        modelTopList = new ArrayList<>();
        bannerItemList = new ArrayList<>();
    }

    public void clear() {
        modelLists.clear();
        modelTopList.clear();
        bannerItemList.clear();
    }

    public void addBannerItem(DiscoverBannerItem discoverBannerItem) {
        bannerItemList.add(discoverBannerItem);
    }

    public void addDiscoverTopModelItem(DiscoverModelItem discoverModelItem) {
        modelTopList.add(discoverModelItem);
    }

    public void addDiscoverModelItem(DiscoverModelList discoverModelList) {
        modelLists.add(discoverModelList);
    }

    public List<DiscoverBannerItem> getBannerItemList() {
        return bannerItemList;
    }

    public List<DiscoverModelItem> getModelTopList() {
        return modelTopList;
    }

    public List<DiscoverModelList> getModelLists() {
        return modelLists;
    }
}
