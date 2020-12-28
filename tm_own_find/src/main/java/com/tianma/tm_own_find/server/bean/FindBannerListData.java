package com.tianma.tm_own_find.server.bean;

/**
 * Created by wcc on 2018/9/10.
 */

public class FindBannerListData {

    /**
     * id : 5
     * image_url : http://tmfac.com:4321/uploads/default/20180907\cff03255136ed250bba3c866da5b8f07.jpg
     * url : 12345678911111111111123323
     */

    private int id;
    private String image_url;
    private String url;
    private int form = 0;
    private String android_info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public String getAndroid_info() {
        return android_info;
    }

    public void setAndroid_info(String android_info) {
        this.android_info = android_info;
    }
}
