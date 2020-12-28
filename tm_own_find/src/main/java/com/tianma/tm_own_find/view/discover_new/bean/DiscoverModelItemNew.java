package com.tianma.tm_own_find.view.discover_new.bean;

import com.google.gson.JsonObject;

public class DiscoverModelItemNew {

    /**
     * id : 124
     * image_url : /application/clzjcscscs_i2can/source/image/icon/dy.png
     * url : http://review.360tianma.com/application/ydvote/view/h5/index.html#/pages/search/search?type=app&height=44,32
     * sort : 9999
     * status : 1
     * add_time : 1590564108
     * model_name : 西安投票
     * is_top : 0
     * model_type : 1
     * form : 0
     * param : {"tmHideNavgation":"1"}
     * ios_info :
     * android_info :
     */

    private int id;
    private String image_url;
    private String url;
    private int sort;
    private int status;
    private int add_time;
    private String model_name;
    private int is_top;
    private int model_type;
    private int form;
    private ParamBean param;
    private String ios_info;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getModel_type() {
        return model_type;
    }

    public void setModel_type(int model_type) {
        this.model_type = model_type;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public String getIos_info() {
        return ios_info;
    }

    public void setIos_info(String ios_info) {
        this.ios_info = ios_info;
    }

    public String getAndroid_info() {
        return android_info;
    }

    public void setAndroid_info(String android_info) {
        this.android_info = android_info;
    }

    public static class ParamBean {
        /**
         * tmHideNavgation : 1
         */

        private String tmHideNavgation;

        public String getTmHideNavgation() {
            return tmHideNavgation;
        }

        public void setTmHideNavgation(String tmHideNavgation) {
            this.tmHideNavgation = tmHideNavgation;
        }
    }

}

