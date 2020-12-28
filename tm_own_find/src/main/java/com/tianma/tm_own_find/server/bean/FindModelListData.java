package com.tianma.tm_own_find.server.bean;

/**
 * Created by wcc on 2018/9/10.
 */

public class FindModelListData {

    /**
     * id : 6
     * image_url : /application/tmgc_discover/source/image/icon/lukuang.png
     * url : /#/Site/Member
     * sort : 123
     * status : 1
     * add_time : 1536563552
     * model_name : 4456
     * is_top : 1
     * model_type : 1
     * form : 1
     * param : null
     */
    public interface TYPE {
        int TYPE_TLTLE = 1;
        int TYPE_ITEM = 2;
    }

    private String android_info;
    private String model_type_name;
    public int content_type;
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

    public String getAndroid_info() {
        return android_info;
    }

    public void setAndroid_info(String android_info) {
        this.android_info = android_info;
    }

    public String getModel_type_name() {
        return model_type_name;
    }

    public void setModel_type_name(String model_type_name) {
        this.model_type_name = model_type_name;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

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
}
