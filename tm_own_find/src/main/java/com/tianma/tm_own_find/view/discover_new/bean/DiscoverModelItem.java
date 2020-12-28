package com.tianma.tm_own_find.view.discover_new.bean;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;

public class DiscoverModelItem implements Serializable {
    private int id;
    private String imageUrl;
    private String url;
    private int sort;
    private int status;
    private long addTime;
    private String modelName;
    private int isTop;
    private int modelType;
    private int form;
    private JsonObject param;
    private String iosInfo;
    private String androidInfo;

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getSort() {
        return sort;
    }

    public int getStatus() {
        return status;
    }

    public long getAddTime() {
        return addTime;
    }

    public String getModelName() {
        return modelName;
    }

    public int getIsTop() {
        return isTop;
    }

    public int getModelType() {
        return modelType;
    }

    public int getForm() {
        return form;
    }

    public JsonObject getParam() {
        return param;
    }

    public String getAndroidInfo() {
        return androidInfo;
    }

    public String getIosInfo() {
        return iosInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public void setParam(JsonObject param) {
        this.param = param;
    }

    public void setIosInfo(String iosInfo) {
        this.iosInfo = iosInfo;
    }

    public void setAndroidInfo(String androidInfo) {
        this.androidInfo = androidInfo;
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
        if (jsonObject.has("sort") && !jsonObject.get("sort").isJsonNull()) {
            sort = jsonObject.get("sort").getAsInt();
        }
        if (jsonObject.has("status") && !jsonObject.get("status").isJsonNull()) {
            status = jsonObject.get("status").getAsInt();
        }
        if (jsonObject.has("add_time") && !jsonObject.get("add_time").isJsonNull()) {
            addTime = jsonObject.get("add_time").getAsLong();
        }
        if (jsonObject.has("model_name") && !jsonObject.get("model_name").isJsonNull()) {
            modelName = jsonObject.get("model_name").getAsString();
        }
        if (jsonObject.has("is_top") && !jsonObject.get("is_top").isJsonNull()) {
            isTop = jsonObject.get("is_top").getAsInt();
        }
        if (jsonObject.has("model_type") && !jsonObject.get("model_type").isJsonNull()) {
            modelType = jsonObject.get("model_type").getAsInt();
        }
        if (jsonObject.has("form") && !jsonObject.get("form").isJsonNull()) {
            form = jsonObject.get("form").getAsInt();
        }
        if (jsonObject.has("param") && !jsonObject.get("param").isJsonNull()) {
            param = jsonObject.get("param").getAsJsonObject();
        }
        if (jsonObject.has("ios_info") && !jsonObject.get("ios_info").isJsonNull()) {
            iosInfo = jsonObject.get("ios_info").getAsString();
        }
        if (jsonObject.has("android_info") && !jsonObject.get("android_info").isJsonNull()) {
            androidInfo = jsonObject.get("android_info").getAsString();
        }
    }
}
