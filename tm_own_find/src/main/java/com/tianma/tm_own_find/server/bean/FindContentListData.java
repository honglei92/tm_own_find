package com.tianma.tm_own_find.server.bean;

/**
 * Created by wcc on 2018/9/10.
 */
public class FindContentListData  {

    public interface TYPE {
        int TYPE_TLTLE = 1;
        int TYPE_ITEM = 2;
    }

    public String id;
    public String price;
    public String pic;
    public String content;
    public int content_type;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String url;
    private String goodsName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}