package com.tianma.tm_own_find.view.discover_new.bean;

import com.google.gson.JsonObject;

public class DiscoverBannerItem {
    private int id;
    private String imageUrl;
    private String url;
    private String color;
    private int form = 0;
    private String info;

    private JsonObject param;
    private String iosInfo;
    private String androidInfo;

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getColor() {
        return color;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfo(JsonObject jsonObject) {
        if (jsonObject.has("id") && !jsonObject.get("id").isJsonNull()) {
            id = jsonObject.get("id").getAsInt();
        }
        if (jsonObject.has("image_url") && !jsonObject.get("image_url").isJsonNull()) {
            imageUrl = jsonObject.get("image_url").getAsString();
        }
        if (jsonObject.has("url") && !jsonObject.get("url").isJsonNull()) {
            url = jsonObject.get("url").getAsString();
        }
        if (jsonObject.has("bg_color") && !jsonObject.get("bg_color").isJsonNull()) {
            color = jsonObject.get("bg_color").getAsString();
        }
        if (jsonObject.has("form") && !jsonObject.get("form").isJsonNull()) {
            form = jsonObject.get("form").getAsInt();
        }
        if (jsonObject.has("android_info") && !jsonObject.get("android_info").isJsonNull()) {
            info = jsonObject.get("android_info").getAsString();
            androidInfo = jsonObject.get("android_info").getAsString();
        }
    }

    public JsonObject getParam() {
        return param;
    }

    public void setParam(JsonObject param) {
        this.param = param;
    }

    public String getIosInfo() {
        return iosInfo;
    }

    public void setIosInfo(String iosInfo) {
        this.iosInfo = iosInfo;
    }

    public String getAndroidInfo() {
        return androidInfo;
    }

    public void setAndroidInfo(String androidInfo) {
        this.androidInfo = androidInfo;
    }
}
