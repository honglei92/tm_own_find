package com.tianma.tm_own_find.view.discover_new.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoverModelList implements Serializable {
    public void setDiscoverModelItems(List<DiscoverModelItem> discoverModelItems) {
        this.discoverModelItems = discoverModelItems;
    }

    private List<DiscoverModelItem> discoverModelItems = new ArrayList<>();
    private String modelTyeName = "";
    private int key = 0;
    private int style = 1;

    public List<DiscoverModelItem> getDiscoverModelItems() {
        return discoverModelItems;
    }

    public void setModelTyeName(String modelTyeName) {
        this.modelTyeName = modelTyeName;
    }

    public String getModelTyeName() {
        return modelTyeName;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
