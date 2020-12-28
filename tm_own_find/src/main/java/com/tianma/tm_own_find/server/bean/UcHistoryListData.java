package com.tianma.tm_own_find.server.bean;

/**
 * Created by wcc on 2018/8/26.
 */

public class UcHistoryListData {

    /**
     * footprint_id : 3
     * member_code : 84E9CB4CCA199218D0A240CB8ADD9AA6
     * app_id : 000000
     * article_id : 0
     * title : 很好的一篇文章
     * intro : 这是一篇非常好的文章，不错吧
     * pic : /public/images/logo.png
     * create_time : 1535018871
     * extend : sdfdsfdsfdsf
     * tag : 111
     * type : 1
     */

    private int footprint_id;
    private String member_code;
    private String app_id;
    private int article_id;
    private String title;
    private String intro;
    private String pic;
    private int create_time;
    private String extend;
    private String tag;
    private int type;
    private Boolean is_title;
    private String title_big;

    public int getFootprint_id() {
        return footprint_id;
    }

    public void setFootprint_id(int footprint_id) {
        this.footprint_id = footprint_id;
    }

    public String getMember_code() {
        return member_code;
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getIs_title() {
        return is_title;
    }

    public void setIs_title(Boolean is_title) {
        this.is_title = is_title;
    }

    public String getTitle_big() {
        return title_big;
    }

    public void setTitle_big(String title_big) {
        this.title_big = title_big;
    }

}
